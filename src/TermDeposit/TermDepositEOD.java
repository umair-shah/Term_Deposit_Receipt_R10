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
		String TDRAuthQueue= "select Count(*) AS AuthCount from TDR_Application tdr inner join account_tl acc on tdr.account_id = acc.account_id inner join branch_tl brn on acc.brn_id = brn.brn_id where TDR_App_Status = 2 and tdr.application_date= '"+Session.GetBranchDate()+"' and  brn.brn_cd='"+Session.GetBranchCode()+"'";
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
		String TDRAuthQueue= "select Count(*) AS MatureAuthCount from TDR_Application tdr inner join account_tl acc on tdr.account_id = acc.account_id inner join branch_tl brn on acc.brn_id = brn.brn_id where TDR_App_Status = 6 and tdr.application_date= '"+Session.GetBranchDate()+"' and brn.brn_cd='"+Session.GetBranchCode()+"'";
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
		String TDRAuthQueue= "select Count(*) AS SubmittedCount from TDR_Application tdr inner join account_tl acc on tdr.account_id = acc.account_id inner join branch_tl brn on acc.brn_id = brn.brn_id where TDR_App_Status = 1 and tdr.application_date= '"+Session.GetBranchDate()+"' and brn.brn_cd='"+Session.GetBranchCode()+"'";
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
        String endDateString =null;
		try {
			endDateString = utility.addDayToDate(SetDate, -1);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
            		"account_no an on an.account_id = tdr.account_id inner join tdr_product tp on tdr.product_id = tp.id inner join Branch_tl brn on brn.brn_id = td.brn_id where tdr.tdr_app_status= 3 " +
            		"and brn.brn_cd = '"+Session.GetBranchCode()+"' and tdr.Maturity_date = '"+dateFormat.format(startDate)+"' or tdr.last_payout_date='"+dateFormat.format(startDate)+"'";
            
            Connection lcl_conn_dt= utility.db_conn();
    		java.sql.Statement lcl_stmt;
    		ResultSet TDRs= null;
    		boolean rolloverPrincipalonMaturityStatus=false;
    		boolean CreditPrincipalOnMaturityStatus=false;
    		boolean RolloverPrincipalandProfitStatus=false;
    		try{
        		lcl_stmt= lcl_conn_dt.createStatement();
        		TDRs = lcl_stmt.executeQuery(GetTDRData);
        		int action= 0;
        		while(TDRs.next())
        		{
        			action= TDRs.getInt("Maturity_Action");
        			if( TDRs.getString("Last_payout_date").equals(dateFormat.format(startDate)) && action != 3)
        			{
        				payoutTransaction(TDRs,dateFormat.format(startDate));
        			}
        			if(TDRs.getString("Maturity_date").equals(dateFormat.format(startDate)))
        			{
        				
        				if(action == 1)
        				{
        					TDRSS = new TermDepositApplicationService();
        					CreditPrincipalOnMaturityStatus= CreditPrincipalOnMaturity(TDRs, dateFormat.format(startDate));
        				}
        				else if(action == 2)
        				{
        					TDRSS = new TermDepositApplicationService();
        					rolloverPrincipalonMaturityStatus = RolloverPrincipalOnMaturity(TDRs,dateFormat.format(startDate));
        				}
        				else if(action == 3)
        				{
        					TDRSS = new TermDepositApplicationService();
            				RolloverPrincipalandProfitStatus=RolloverPrincipalandProfit(TDRs,dateFormat.format(startDate));
        				}
        			}
        			
        		}
    		}
    		catch(Exception E )
    		{
    			E.printStackTrace();
    		}
	}
}
	
	public boolean payoutTransaction(ResultSet TDRs, String StartDate)throws SQLException
	{
		Connection lcl_conn_dt = utility.db_conn();
		lcl_conn_dt.setAutoCommit(false);
		lcl_conn_dt.setTransactionIsolation(lcl_conn_dt.TRANSACTION_READ_COMMITTED);
		int voucherId=0;

		String ProfitFundVoucher = "Select voucher_id from Final Table (insert into Voucher (brn_id,voucher_type_id,voucher_date) values ((select brn_id from Branch_tl where brn_cd= ?), (Select voucher_type_Id from voucher_type where voucher_desc = 'Monthly Payout') ,? ))";
		String ProfitFundDealVoucher="insert into tdr_deal_voucher(deal_id,voucher_id,approved_by) values ((select deal_id from tdr_deal where tdr_app_id= ?),?,?)";
		//Query to be maid 
		//String InternalExpenseAccount="select Internal_Account_ID from internal_account where TITLE = 'Expense Account'";
		String ProfitCrdTransactionQuery="insert into transaction_tl (Amount,Cus_Account_ID,Voucher_ID,Trans_type_id) values (? ,? ,? ,2  ) ";
		String ProfitDbtTransactionQuery="insert into transaction_tl (Amount,Internal_Account_Id,Voucher_ID,Trans_type_id) values (? ,1 ,? ,1  ) ";
		String ProfitUpdateCustAccount= "Update account_tl set balance=balance+ ?  where account_id =? ";
		String ProfitUpdateExpAccount= "Update internal_account_tl set balance=balance- ?  where internal_account_id =1";
		
		String withHoldingTaxCreditTransaction="insert into transaction_tl (Amount,internal_account_id,Voucher_ID,Trans_type_id) values (? ,2 ,? ,2  )"; //TDR profit WHT transaction_type_id
		//String TaxUpdateCustAccount="Update account_tl set balance=balance- ?  where account_id =?";
		String TaxUpdateTaxAccount="Update internal_account_tl set balance=balance+ ?  where internal_account_id =2";
		float taxRate= TDRSS.getTaxRate();
		
		float profitToBePaid = (float)(((TDRs.getFloat("amount") * TDRs.getFloat("TDR_RATE") / 100.0)*(1.0 / TDRs.getFloat("Tenure"))));
		float Tax = (float) (profitToBePaid * taxRate);
		
		PreparedStatement preparedStatement7 = lcl_conn_dt.prepareStatement(ProfitFundVoucher);
		preparedStatement7.setString(1, Session.GetBranchCode());
		preparedStatement7.setString(2,StartDate);
		ResultSet voucherIdRs = preparedStatement7.executeQuery();
		
		if (voucherIdRs.next())
		{
			voucherId = voucherIdRs.getInt("voucher_id");
		}
		
		PreparedStatement preparedStatement8 = lcl_conn_dt.prepareStatement(ProfitFundDealVoucher);
		preparedStatement8.setString(1, TDRs.getString("Application_id"));
		preparedStatement8.setInt(2, voucherId);
		preparedStatement8.setString(3, Session.GetUserName());
		preparedStatement8.executeUpdate();
		
		//Amount (Profit), Voucher ID
		PreparedStatement preparedStatement9 = lcl_conn_dt.prepareStatement(ProfitDbtTransactionQuery);
		preparedStatement9.setFloat(1, profitToBePaid);
		preparedStatement9.setInt(2, voucherId);
		preparedStatement9.executeUpdate();
		
		
		PreparedStatement preparedStatement10 = lcl_conn_dt.prepareStatement(withHoldingTaxCreditTransaction);
		preparedStatement10.setFloat(1, Tax);
		preparedStatement10.setInt(2, voucherId);
		preparedStatement10.executeUpdate();
		
		//Amount, Customer Account Id, Voucher Id
		PreparedStatement preparedStatement11 = lcl_conn_dt.prepareStatement(ProfitCrdTransactionQuery);
		preparedStatement11.setFloat(1, profitToBePaid - Tax);
		preparedStatement11.setString(2, TDRs.getString("Account_ID"));
		preparedStatement11.setInt(3, voucherId);
		preparedStatement11.executeUpdate();
		
		//ProfitUpdateExpAccount
		PreparedStatement preparedStatement12 = lcl_conn_dt.prepareStatement(ProfitUpdateExpAccount);
		preparedStatement12.setFloat(1, profitToBePaid);
		preparedStatement12.executeUpdate();
		
		//TaxUpdateTaxAccount
		PreparedStatement preparedStatement13 = lcl_conn_dt.prepareStatement(TaxUpdateTaxAccount);
		preparedStatement13.setFloat(1, Tax);
		preparedStatement13.executeUpdate();
		
		//ProfitUpdateCustAccount
		PreparedStatement preparedStatement14 = lcl_conn_dt.prepareStatement(ProfitUpdateCustAccount);
		preparedStatement14.setFloat(1, profitToBePaid - Tax);
		preparedStatement14.setString(2,TDRs.getString("Account_ID"));
		preparedStatement14.executeUpdate();
		
		String LastPayoutQuery="Update tdr_application set last_payout_date = add_months('"+StartDate+"',1) where application_id = '"+TDRs.getString("application_ID")+"'";
		
		java.sql.Statement lcl_stmt;
		 lcl_stmt= lcl_conn_dt.createStatement();
		 int TDRAppDetailStatus = lcl_stmt.executeUpdate(LastPayoutQuery);
		 if(TDRAppDetailStatus > 0 )
		 {
			 lcl_conn_dt.commit();
			 return true;
		 }
		 else{
			 lcl_conn_dt.rollback();
		 }
		 return false;
	}
	public boolean RolloverPrincipalandProfit(ResultSet TDRs, String StartDate) throws SQLException
	{
		
		Connection lcl_conn_dt = utility.db_conn();
		lcl_conn_dt.setAutoCommit(false);
		lcl_conn_dt.setTransactionIsolation(lcl_conn_dt.TRANSACTION_READ_COMMITTED);
		int voucherId=0;

		String ProfitFundVoucher = "Select voucher_id from Final Table (insert into Voucher (brn_id,voucher_type_id,voucher_date) values ((select brn_id from Branch_tl where brn_cd= ?), (Select voucher_type_Id from voucher_type where voucher_desc = 'Rollover Profit') ,? ))";
		String ProfitFundDealVoucher="insert into tdr_deal_voucher(deal_id,voucher_id,approved_by) values ((select deal_id from tdr_deal where tdr_app_id= ?),?,?)";
		//Query to be maid 
		//String InternalExpenseAccount="select Internal_Account_ID from internal_account where TITLE = 'Expense Account'";
		String ProfitCrdTransactionQuery="insert into transaction_tl (Amount,Cus_Account_ID,Voucher_ID,Trans_type_id) values (? ,? ,? ,2  ) ";
		String ProfitDbtTransactionQuery="insert into transaction_tl (Amount,Internal_Account_Id,Voucher_ID,Trans_type_id) values (? ,1 ,? ,1  ) ";
		String ProfitUpdateCustAccount= "Update account_tl set balance=balance+ ?  where account_id =? ";
		String ProfitUpdateExpAccount= "Update internal_account_tl set balance=balance- ?  where internal_account_id =1";
		
		String withHoldingTaxCreditTransaction="insert into transaction_tl (Amount,internal_account_id,Voucher_ID,Trans_type_id) values (? ,2 ,? ,2  )"; //TDR profit WHT transaction_type_id
		//String TaxUpdateCustAccount="Update account_tl set balance=balance- ?  where account_id =?";
		String TaxUpdateTaxAccount="Update internal_account_tl set balance=balance+ ?  where internal_account_id =2";
		
		String TDRAppStatusUpdateQuery="update tdr_application set tdr_app_status=(Select ID from tdr_status where DESC='Maturity Closed') where application_id = ?";
		String TDRDealStatusUpdateQuery="update tdr_deal set deal_status =(Select ID from tdr_deal_status where DESC='Maturity Closed') where tdr_app_id = ?";

		float profitToBePaid = (float)(TDRs.getFloat("amount") *  TDRs.getFloat("TDR_RATE") / 100.0);
		float taxRate= TDRSS.getTaxRate();
		float Tax = (float) (profitToBePaid * taxRate);
		
		PreparedStatement preparedStatement7 = lcl_conn_dt.prepareStatement(ProfitFundVoucher);
		preparedStatement7.setString(1, Session.GetBranchCode());
		preparedStatement7.setString(2,StartDate);
		ResultSet voucherIdRs = preparedStatement7.executeQuery();
		
		if (voucherIdRs.next())
		{
			voucherId = voucherIdRs.getInt("voucher_id");
		}
		
		PreparedStatement preparedStatement8 = lcl_conn_dt.prepareStatement(ProfitFundDealVoucher);
		preparedStatement8.setString(1, TDRs.getString("Application_id"));
		preparedStatement8.setInt(2, voucherId);
		preparedStatement8.setString(3, Session.GetUserName());
		preparedStatement8.executeUpdate();
		
		//Amount (Profit), Voucher ID
		PreparedStatement preparedStatement9 = lcl_conn_dt.prepareStatement(ProfitDbtTransactionQuery);
		preparedStatement9.setFloat(1, profitToBePaid);
		preparedStatement9.setInt(2, voucherId);
		preparedStatement9.executeUpdate();
		
		
		PreparedStatement preparedStatement10 = lcl_conn_dt.prepareStatement(withHoldingTaxCreditTransaction);
		preparedStatement10.setFloat(1, Tax);
		preparedStatement10.setInt(2, voucherId);
		preparedStatement10.executeUpdate();
		
		//Amount, Customer Account Id, Voucher Id
		PreparedStatement preparedStatement11 = lcl_conn_dt.prepareStatement(ProfitCrdTransactionQuery);
		preparedStatement11.setFloat(1, profitToBePaid - Tax);
		preparedStatement11.setString(2, TDRs.getString("tdr_account_id"));
		preparedStatement11.setInt(3, voucherId);
		preparedStatement11.executeUpdate();
		
		//ProfitUpdateExpAccount
		PreparedStatement preparedStatement12 = lcl_conn_dt.prepareStatement(ProfitUpdateExpAccount);
		preparedStatement12.setFloat(1, profitToBePaid);
		preparedStatement12.executeUpdate();
		
		//TaxUpdateTaxAccount
		PreparedStatement preparedStatement13 = lcl_conn_dt.prepareStatement(TaxUpdateTaxAccount);
		preparedStatement13.setFloat(1, Tax);
		preparedStatement13.executeUpdate();
		
		//ProfitUpdateCustAccount
		PreparedStatement preparedStatement14 = lcl_conn_dt.prepareStatement(ProfitUpdateCustAccount);
		preparedStatement14.setFloat(1, profitToBePaid - Tax);
		preparedStatement14.setString(2,TDRs.getString("tdr_account_id"));
		preparedStatement14.executeUpdate();
		
		PreparedStatement preparedStatement15 = lcl_conn_dt.prepareStatement(TDRAppStatusUpdateQuery);
		preparedStatement15.setLong(1,TDRs.getLong("Application_id"));
		int updateappstatus=preparedStatement15.executeUpdate();		
		
		PreparedStatement preparedStatement16 = lcl_conn_dt.prepareStatement(TDRDealStatusUpdateQuery);
		preparedStatement16.setLong(1,TDRs.getLong("Application_id") );
		int updateDealStatus=preparedStatement16.executeUpdate();
		
		
		
		String RollOverTDRApp = "SELECT lpad(APPLICATION_ID,5,'0'),Year(Application_date) FROM FINAL TABLE " +
				"(INSERT INTO TDR_Application (Holder_name,Amount,Input_by,Maturity_date,Application_date,TDR_Request_DOC, " +
				"TDR_App_status,Product_Id,Maturity_Action,Mode_of_fund,Principal_Fund_Crd_Acc_ID,Prof_Nom_Acc_ID,TDR_Request_Doc_Name, " +
				"Account_ID,last_payout_date, APPROVED_BY ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?))";
		
		
		String applicationDate= null;
		try {
			applicationDate = utility.addDayToDate(StartDate,1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date mdate = utility.getMaturityDate(StartDate, TDRs.getInt("Product_Id"));
		String applicationSno=null;
		String year =null;
		String applicationNo =null;
		String dealNo=null;
		try {
    		
        	PreparedStatement preparedStatement1 = lcl_conn_dt.prepareStatement(RollOverTDRApp);
//        	lcl_conn_dt.setAutoCommit(false);
//    		lcl_conn_dt.setTransactionIsolation(lcl_conn_dt.TRANSACTION_READ_COMMITTED);

        	preparedStatement1.setString(1,TDRs.getString("Holder_name"));
        	preparedStatement1.setFloat(2, TDRs.getFloat("Amount")+ profitToBePaid - Tax);
        	preparedStatement1.setString(3,TDRs.getString("Input_by"));
        	preparedStatement1.setDate(4, (java.sql.Date) mdate);
        	preparedStatement1.setString(5, StartDate);
        	preparedStatement1.setBytes(6, TDRs.getBytes("TDR_Request_DOC"));
        	preparedStatement1.setInt(7,3);
        	preparedStatement1.setInt(8,TDRs.getInt("Product_ID"));
        	preparedStatement1.setInt(9,TDRs.getInt("Maturity_Action"));
        	preparedStatement1.setInt(10,TDRs.getInt("Mode_Of_Fund"));
        	preparedStatement1.setLong(11,TDRs.getLong("Account_ID"));
        	preparedStatement1.setLong(12, TDRs.getLong("Account_ID"));
        	preparedStatement1.setString(13,TDRs.getString("TDR_Request_DOC_Name"));
        	preparedStatement1.setLong(14,TDRs.getLong("Account_ID"));
        	preparedStatement1.setString(15,utility.addMonthToDate(StartDate, 1));
        	preparedStatement1.setString(16,TDRs.getString("Approved_By"));
            ResultSet rs = preparedStatement1.executeQuery();
            if(rs.next())
     		{
     			lcl_conn_dt.commit();
     			applicationSno =rs.getString(1);
     			year = rs.getString(2);
     			applicationNo=applicationSno + "/" +  TDRs.getString("accountno").substring(8, 14)+"/"+year;
     			
     		}
            if(applicationNo != null)
            {
            	String query = "Select Deal_id From final Table (INSERT INTO TDR_DEAL(Deal_Date, Deal_Status, TDR_APP_ID, TDR_ACCOUNT_ID, BRN_ID)" +
        				"VALUES (?, ?, ?, ?, (SELECT BRN_ID FROM BRANCH_TL WHERE Brn_Cd = ?)))";

            	PreparedStatement preparedStatement2 = lcl_conn_dt.prepareStatement(query);
            	preparedStatement2.setString(1,StartDate);
            	preparedStatement2.setInt(2,1);
            	preparedStatement2.setInt(3,Integer.parseInt(applicationNo.substring(0,5)));
    			preparedStatement2.setInt(4,Integer.parseInt(TDRSS.GetTDRAccountID(TDRs.getString("accountno"))));
    			preparedStatement2.setString(5,Session.GetBranchCode());
    			ResultSet dealrs=preparedStatement2.executeQuery();
    			if(dealrs.next())
    			{
    				dealNo=dealrs.getString(1);
    			}
    			if(dealNo != null)
    			{
    				lcl_conn_dt.commit();
    				return true;
    			}
            }
     		else{
     			lcl_conn_dt.rollback();
     		}
		}
            catch(Exception E)
            {
            	lcl_conn_dt.rollback();
            	E.printStackTrace();
            }
		return false;
	}
	public boolean RolloverPrincipalOnMaturity (ResultSet TDRs, String StartDate) throws SQLException 
	{	
		String TDRAppClosedQuery="update tdr_application set tdr_app_status=5 where application_id = ?";
		String TDRDealClosedQuery="update tdr_deal set deal_status = 5 where tdr_app_id = ?";	
		
		String RollOverTDRApp = "SELECT lpad(APPLICATION_ID,5,'0'),Year(Application_date) FROM FINAL TABLE " +
				"(INSERT INTO TDR_Application (Holder_name,Amount,Input_by,Maturity_date,Application_date,TDR_Request_DOC, " +
				"TDR_App_status,Product_Id,Maturity_Action,Mode_of_fund,Principal_Fund_Crd_Acc_ID,Prof_Nom_Acc_ID,TDR_Request_Doc_Name, " +
				"Account_ID,last_payout_date, APPROVED_BY ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?,?))";
		
		
		Connection lcl_conn_dt = utility.db_conn();
		PreparedStatement preparedStatement3 = lcl_conn_dt.prepareStatement(TDRAppClosedQuery);
		preparedStatement3.setLong(1,TDRs.getLong("Application_id"));
		int updateappstatus=preparedStatement3.executeUpdate();		
		
		PreparedStatement preparedStatement4 = lcl_conn_dt.prepareStatement(TDRDealClosedQuery);
		preparedStatement4.setLong(1,TDRs.getLong("Application_id") );
		int updateDealStatus=preparedStatement4.executeUpdate();	
		
		String applicationDate= null;
		try {
			applicationDate = utility.addDayToDate(StartDate,1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date mdate = utility.getMaturityDate(StartDate, TDRs.getInt("Product_Id"));
		String applicationSno=null;
		String year =null;
		String applicationNo =null;
		String dealNo=null;
		try {
    		
        	PreparedStatement preparedStatement1 = lcl_conn_dt.prepareStatement(RollOverTDRApp);
        	lcl_conn_dt.setAutoCommit(false);
    		lcl_conn_dt.setTransactionIsolation(lcl_conn_dt.TRANSACTION_READ_COMMITTED);

        	preparedStatement1.setString(1,TDRs.getString("Holder_name"));
        	preparedStatement1.setFloat(2, TDRs.getFloat("Amount"));
        	preparedStatement1.setString(3,TDRs.getString("Input_by"));
        	preparedStatement1.setDate(4, (java.sql.Date) mdate);
        	preparedStatement1.setString(5, StartDate);
        	preparedStatement1.setBytes(6, TDRs.getBytes("TDR_Request_DOC"));
        	preparedStatement1.setInt(7,3);
        	preparedStatement1.setInt(8,TDRs.getInt("Product_ID"));
        	preparedStatement1.setInt(9,TDRs.getInt("Maturity_Action"));
        	preparedStatement1.setInt(10,TDRs.getInt("Mode_Of_Fund"));
        	preparedStatement1.setLong(11,TDRs.getLong("Account_ID"));
        	preparedStatement1.setLong(12, TDRs.getLong("Account_ID"));
        	preparedStatement1.setString(13,TDRs.getString("TDR_Request_DOC_Name"));
        	preparedStatement1.setLong(14,TDRs.getLong("Account_ID"));
        	preparedStatement1.setString(15,utility.addMonthToDate(StartDate, 1));
        	preparedStatement1.setString(16,TDRs.getString("Approved_By"));
            ResultSet rs = preparedStatement1.executeQuery();
            
        
            if(rs.next())
     		{
     			lcl_conn_dt.commit();
     			applicationSno =rs.getString(1);
     			year = rs.getString(2);
     			applicationNo=applicationSno + "/" +  TDRs.getString("accountno").substring(8, 14)+"/"+year;
     			
     		}
            if(applicationNo != null)
            {
            	String query = "Select Deal_id From final Table (INSERT INTO TDR_DEAL(Deal_Date, Deal_Status, TDR_APP_ID, TDR_ACCOUNT_ID, BRN_ID)" +
        				"VALUES (?, ?, ?, ?, (SELECT BRN_ID FROM BRANCH_TL WHERE Brn_Cd = ?)))";
            	
            	PreparedStatement preparedStatement2 = lcl_conn_dt.prepareStatement(query);
            	preparedStatement2.setString(1,StartDate);
            	preparedStatement2.setInt(2,1);
            	preparedStatement2.setInt(3,Integer.parseInt(applicationNo.substring(0,5)));
    			preparedStatement2.setInt(4,Integer.parseInt(TDRSS.GetTDRAccountID(TDRs.getString("accountno"))));
    			preparedStatement2.setString(5,Session.GetBranchCode());
    			
    			ResultSet dealrs=preparedStatement2.executeQuery();
    			if(dealrs.next())
    			{
    				dealNo=dealrs.getString(1);
    			}
    			if(dealNo != null)
    			{
    				lcl_conn_dt.commit();
    				return true;
    			}
            }
     		else{
     			lcl_conn_dt.rollback();
     		}
		}
            catch(Exception E)
            {
            	lcl_conn_dt.rollback();
            	E.printStackTrace();
            }
		return false;
	}
	public boolean CreditPrincipalOnMaturity(ResultSet TDRs, String StartDate) throws SQLException 
	{
		Connection lcl_conn_dt= utility.db_conn();
		java.sql.Statement lcl_stmt;
		String CreateVoucherquery = "Select voucher_id from Final Table (insert into Voucher (brn_id,voucher_type_id,voucher_date) values ((select brn_id from Branch_tl where brn_cd= ?), 1 ,? ))";
		String dealVoucherQuery="insert into tdr_deal_voucher(deal_id,voucher_id,approved_by) values ((select deal_id from tdr_deal where tdr_app_id= ?),?,?)";
		String TransactionQuery="insert into transaction_tl (Amount,Cus_Account_ID,Voucher_ID,Trans_type_id) values (? ,? ,? ,?  ) ";
		String updateCustAccountQuery= "Update account_tl set balance=balance+ ? where account_id =? ";
		String updateTdrAccountQuery= "Update account_tl set balance=balance- ?  where account_id =?";
		String TDRAppClosedQuery="update tdr_application set tdr_app_status=5 where application_id = ?";
		String TDRDealClosedQuery="update tdr_deal set deal_status = 5 where tdr_app_id = ?";
		
		lcl_conn_dt.setAutoCommit(false);
		lcl_conn_dt.setTransactionIsolation(lcl_conn_dt.TRANSACTION_READ_COMMITTED);
		PreparedStatement preparedStatement = lcl_conn_dt.prepareStatement(CreateVoucherquery);
		
		preparedStatement.setString(1,Session.GetBranchCode());
		preparedStatement.setString(2,StartDate);
		ResultSet voucher= preparedStatement.executeQuery();

		long voucherID=0;
		int debitstatus=0;
		int crdstatus=0;
		int dealVoucherStatus=0;
		int updateappstatus=0;
		int updateCustAccountStatus=0;
		int updateTdrAccountstatus=0;
		int updateDealStatus=0;
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
			long tdrAccountID= Long.parseLong(TDRSS.GetTDRAccountID(TDRs.getString("accountno")));
			preparedStatement2.setLong(2,   tdrAccountID);
			preparedStatement2.setLong(3, voucherID);
			preparedStatement2.setInt(4, 1);
			crdstatus=preparedStatement2.executeUpdate();
			
			PreparedStatement preparedStatement3 = lcl_conn_dt.prepareStatement(updateCustAccountQuery);
			preparedStatement3.setDouble(1, TDRs.getDouble("Amount"));
			preparedStatement3.setLong(2,TDRs.getLong("account_id"));
			updateCustAccountStatus=preparedStatement3.executeUpdate();
			
			PreparedStatement preparedStatement4 = lcl_conn_dt.prepareStatement(updateTdrAccountQuery);
			preparedStatement4.setDouble(1, TDRs.getDouble("Amount"));
			preparedStatement4.setLong(2, tdrAccountID);
			updateTdrAccountstatus=	preparedStatement4.executeUpdate();
			
			if(debitstatus >0 && crdstatus >0 && updateCustAccountStatus> 0 && updateTdrAccountstatus >0 )
			{
				PreparedStatement preparedStatement5 = lcl_conn_dt.prepareStatement(TDRAppClosedQuery);
				preparedStatement5.setLong(1,TDRs.getLong("Application_id"));
				updateappstatus=preparedStatement5.executeUpdate();		
				
				PreparedStatement preparedStatement6 = lcl_conn_dt.prepareStatement(TDRDealClosedQuery);
				preparedStatement6.setLong(1,TDRs.getLong("Application_id") );
				updateDealStatus=preparedStatement6.executeUpdate();	
				if(updateappstatus > 0 && updateDealStatus >0 )
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

