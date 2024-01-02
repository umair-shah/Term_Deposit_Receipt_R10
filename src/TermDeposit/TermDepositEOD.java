package TermDeposit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import Utilities.utility;

public class TermDepositEOD {
	
	public TermDepositApplicationService TDRSS;
	public TermDepositEOD()
	{
		
	}
	
	public int CheckTDRAuthPendingTask()
	{
		String TDRAuthQueue= "select Count(*) AS AuthCount from TDR_Application tdr inner join account_tl acc on tdr.account_id = acc.account_id inner join branch_tl brn on acc.brn_id = brn.brn_id where TDR_App_Status = 2 and brn.brn_cd='"+Session.GetBranchCode()+"'";
		Connection lcl_conn_dt= utility.db_conn();
		java.sql.Statement lcl_stmt;
		ResultSet authQueueCount= null;
		int count=0;
		try {
			 lcl_stmt= lcl_conn_dt.createStatement();
			 authQueueCount = lcl_stmt.executeQuery(TDRAuthQueue);
			 if(authQueueCount.next())
			 {
				 count=authQueueCount.getInt("AuthCount");
			 }
			 
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return count;
	}
	public int CheckTDRPreMatureAuth()
	{
		String TDRAuthQueue= "select Count(*) AS MatureAuthCount from TDR_Application tdr inner join account_tl acc on tdr.account_id = acc.account_id inner join branch_tl brn on acc.brn_id = brn.brn_id where TDR_App_Status = 6 and brn.brn_cd='"+Session.GetBranchCode()+"'";
		Connection lcl_conn_dt= utility.db_conn();
		java.sql.Statement lcl_stmt;
		ResultSet authQueueCount= null;
		int count=0;
		try {
			 lcl_stmt= lcl_conn_dt.createStatement();
			 authQueueCount = lcl_stmt.executeQuery(TDRAuthQueue);
			 if(authQueueCount.next())
			 {
				 count=authQueueCount.getInt("MatureAuthCount");
			 }
			 
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return count;
	}
	public int CheckTDROpeningQueue()
	{
		String TDRAuthQueue= "select Count(*) AS SubmittedCount from TDR_Application tdr inner join account_tl acc on tdr.account_id = acc.account_id inner join branch_tl brn on acc.brn_id = brn.brn_id where TDR_App_Status = 1 and brn.brn_cd='"+Session.GetBranchCode()+"'";
		Connection lcl_conn_dt= utility.db_conn();
		java.sql.Statement lcl_stmt;
		ResultSet authQueueCount= null;
		int count=0;
		try {
			 lcl_stmt= lcl_conn_dt.createStatement();
			 authQueueCount = lcl_stmt.executeQuery(TDRAuthQueue);
			 if(authQueueCount.next())
			 {
				 count=authQueueCount.getInt("SubmittedCount");
			 }
			 
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return count;
	}
	public void TDRMonthlyPayoutProcess(String PrevDate, String SetDate)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        
        String startDateString = PrevDate;
        String endDateString = SetDate;
        Date endDate = null;
        Date startDate = null;
		try {
			startDate = dateFormat.parse(startDateString);
			endDate = dateFormat.parse(endDateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        while (!startDate.after(endDate)) {
        	
        	System.out.println(dateFormat.format(startDate));
            cal.setTime(startDate);
            cal.add(Calendar.DATE, 1);
            startDate = cal.getTime();
            
            String GetTDRData="select * from tdr_application tdr inner join tdr_deal td on td.tdr_app_id= tdr.application_id inner join " +
            		"account_no an on an.account_id = tdr.account_id inner join tdr_product tp on tdr.product_id = tp.id where tdr.tdr_app_status= 3 " +
            		"and tdr.application_date = '"+dateFormat.format(startDate)+"' or tdr.last_payout_date='"+dateFormat.format(startDate)+"'";
            Connection lcl_conn_dt= utility.db_conn();
    		java.sql.Statement lcl_stmt;
    		ResultSet TDRs= null;
    		try{
        		lcl_stmt= lcl_conn_dt.createStatement();
        		TDRs = lcl_stmt.executeQuery(GetTDRData);
        		while(TDRs.next())
        		{
        			if(TDRs.getString("Maturity_date").equals(dateFormat.format(startDate)))
        			{
        				int action= TDRs.getInt("Maturity_Action");
        				if(action == 1)
        				{
        					CreditPrincipalOnMaturity(TDRs);
        				}
        				else if(action == 2)
        				{
        					RolloverPrincipalOnMaturity(TDRs);
        				}
        				else if(action == 3)
        				{
        					
        				}
        			}
        			if(TDRs.getString("Last_payout_date").equals(dateFormat.format(startDate)))
        			{
        				
        			}
        		}
    		}
    		
    		catch(Exception E )
    		{
    			
    		}
	}
	}

    		



    
	public boolean RolloverPrincipalOnMaturity (ResultSet TDRs)
	{
		TermDepositApplicationDTO TDADTO= new TermDepositApplicationDTO();
		return true;
		//TDRSS.insertTDRApplication();
	}
	public boolean CreditPrincipalOnMaturity(ResultSet TDRs) throws SQLException 
	{
		Connection lcl_conn_dt= utility.db_conn();
		java.sql.Statement lcl_stmt;
		String CreateVoucherquery = "Select voucher_id from Final Table (insert into Voucher (brn_id,voucher_type_id,voucher_date) values ((select brn_cd from Branch_tl where brn_cd= ?), 1 ,? ))";
		String dealVoucherQuery="insert into tdr_deal_voucher(deal_id,voucher_id,approved_by) values ((select deal_id from tdr_deal where tdr_app_id= ?),?,?)";
		String TransactionQuery="insert into transaction_tl (Amount,Cus_Account_ID,Voucher_ID,Trans_type_id) values (? ,? ,? ,?  ) ";
		String updateCustAccountQuery= "Update account_tl set balance=balance+ ? where account_id =? ";
		String updateTdrAccountQuery= "Update account_tl set balance=balance+ ?  where account_id =?";
//		String TDRAppAuthorizeQuery="update tdr_application set tdr_app_status=3 where application_id = ?";
		
		lcl_conn_dt.setAutoCommit(false);
		lcl_conn_dt.setTransactionIsolation(lcl_conn_dt.TRANSACTION_READ_COMMITTED);
		PreparedStatement preparedStatement = lcl_conn_dt.prepareStatement(CreateVoucherquery);
		
		preparedStatement.setString(1,Session.GetBranchCode());
		preparedStatement.setString(2,Session.GetBranchDate());
		ResultSet voucher= preparedStatement.executeQuery();

		long voucherID=0;
		int debitstatus=0;
		int crdstatus=0;
		int dealVoucherStatus=0;
		int updateappstatus=0;
		int updateCustAccountStatus=0;
		int updateTdrAccountstatus=0;
		while(voucher.next())
		{
			voucherID=voucher.getLong(1);
		}
		if(voucherID > 0 )
		{
			PreparedStatement preparedStatement1 = lcl_conn_dt.prepareStatement(dealVoucherQuery);
			preparedStatement1.setLong(1,TDRs.getLong("application_ID"));
			preparedStatement1.setLong(2, voucherID);
			preparedStatement1.setString(3,Session.GetUserName());
			dealVoucherStatus=preparedStatement1.executeUpdate();
			
			PreparedStatement preparedStatement2 = lcl_conn_dt.prepareStatement(TransactionQuery);
			preparedStatement2.setDouble(1,TDRs.getDouble("Amount") );
			preparedStatement2.setLong(2, TDRs.getLong("account_id"));
			preparedStatement2.setLong(3, voucherID);
			preparedStatement2.setInt(4, 2);
			debitstatus=preparedStatement2.executeUpdate();
			
			preparedStatement2.setDouble(1, TDRs.getDouble("Amount"));
			long tdrAccountID= Long.parseLong(TDRSS.GetTDRAccountID(TDRs.getString("account_no")));
			preparedStatement2.setLong(2,   TDRs.getLong("account_id"));
			preparedStatement2.setLong(3, voucherID);
			preparedStatement2.setInt(4, 1);
			crdstatus=preparedStatement2.executeUpdate();
			
			PreparedStatement preparedStatement3 = lcl_conn_dt.prepareStatement(updateCustAccountQuery);
			preparedStatement3.setDouble(1, TDRs.getDouble("Amount"));
			preparedStatement3.setLong(2,TDRs.getLong("account_id"));
			updateCustAccountStatus=preparedStatement3.executeUpdate();
			
			PreparedStatement preparedStatement4 = lcl_conn_dt.prepareStatement(updateTdrAccountQuery);
			preparedStatement4.setDouble(1, TDRs.getDouble("Amount"));
			preparedStatement4.setLong(2, TDRs.getLong("account_id"));
			updateTdrAccountstatus=	preparedStatement4.executeUpdate();
			
			if(debitstatus >0 && crdstatus >0 && updateCustAccountStatus> 0 && updateTdrAccountstatus >0 )
			{
				if(updateappstatus > 0)
				{
					lcl_conn_dt.commit();
					return true;
				}
				else{
					lcl_conn_dt.rollback();
					return false;
				}
			}
			else{
				return false;
			}
	}
		return false;
	}
	}

