package TermDeposit;
import Utilities.utility;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Utilities.ComboItem;
import Utilities.utility;

public class TermDepositApplicationService {
	TermDepositSearchService TDRsearcservice = null;
	public void TermDepositApplicationService()
	{
		TDRsearcservice= new TermDepositSearchService();
	}
	
	public ResultSet GetModeofFunds()
	{
		Connection lcl_conn_dt = utility.db_conn();
		ResultSet modeOfFundRs=null;
		
		String modeOfFundQuery = "SELECT * FROM MODE_OF_FUND";
		java.sql.Statement lcl_stmt;
		try {
			 lcl_stmt= lcl_conn_dt.createStatement();
			 modeOfFundRs = lcl_stmt.executeQuery(modeOfFundQuery);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return modeOfFundRs;
	}
	
	public ResultSet GetTenure()
	{
		Connection lcl_conn_dt = utility.db_conn();
		String tdrProductQuery = "SELECT * FROM tdr_Product";
		ResultSet tdrProductRs=null;
		java.sql.Statement lcl_stmt;
		try {
			 lcl_stmt= lcl_conn_dt.createStatement();
			 tdrProductRs = lcl_stmt.executeQuery(tdrProductQuery);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return tdrProductRs;
	}
	
	public ResultSet GetActionatMaturity()
	{
		Connection lcl_conn_dt = utility.db_conn();
		String actionAtMaturityQuery = "SELECT * FROM Maturity_Action";
		ResultSet actionAtMaturityRs=null;
		java.sql.Statement lcl_stmt;
		try {
			 lcl_stmt= lcl_conn_dt.createStatement();
			 actionAtMaturityRs = lcl_stmt.executeQuery(actionAtMaturityQuery);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return actionAtMaturityRs;
	}
	
	public String GetTDRAccountID(String accountNo)
	{
		String brnCd=accountNo.substring(0,4);
		String accType="0010";
		String customerNo=accountNo.substring(8,14);
		String runNo="01";
		String chkDigit="1";
		String accountSearchQuery = "Select A.Account_ID from Account_tl A inner join Branch_tl B on A.brn_ID= B.brn_ID inner join Customer Cus on A.Customer_ID= Cus.Customer_ID inner join Account_type At on A.Acc_type_ID = At.Acc_type_ID " +
				" WHERE B.Brn_cd = '"+ brnCd +"' and lpad(At.Acc_Type_CD,4,'0')= '"+accType+"' and lpad(Cus.Customer_No,6,'0')='"+customerNo+"' and A.Check_Digit='"+chkDigit+"' and lpad(A.run_no,2,'0')='"+runNo+"' -- and B.Brn_cd='"+Session.GetBranchCode()+"'";
		Connection lcl_conn_dt = utility.db_conn();
		java.sql.Statement lcl_stmt;
		try 
		{
			 lcl_stmt= lcl_conn_dt.createStatement();
			 ResultSet accountRs = lcl_stmt.executeQuery(accountSearchQuery);
			 if(accountRs.next())
			 {
				 return accountRs.getString("Account_ID");
			 }
		} 
		catch (SQLException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	public String TDRAccountExist(String accountNo)
	{
		String tdrAccId=null;
		String brnCd=accountNo.substring(0,4);
		String accType="0010";
		String customerNo=accountNo.substring(8,14);
		String runNo="01";
		String chkDigit="1";
		String accountSearchQuery = "Select A.Account_ID from Account_tl A inner join Branch_tl B on A.brn_ID= B.brn_ID inner join Customer Cus on A.Customer_ID= Cus.Customer_ID inner join Account_type At on A.Acc_type_ID = At.Acc_type_ID " +
				" WHERE B.Brn_cd = '"+ brnCd +"' and lpad(At.Acc_Type_CD,4,'0')= '"+accType+"' and lpad(Cus.Customer_No,6,'0')='"+customerNo+"' and A.Check_Digit='"+chkDigit+"' and lpad(A.run_no,2,'0')='"+runNo+"' -- and B.Brn_cd='"+Session.GetBranchCode()+"'";
		Connection lcl_conn_dt = utility.db_conn();
		java.sql.Statement lcl_stmt;
		try 
		{
			 lcl_stmt= lcl_conn_dt.createStatement();
			 ResultSet accountRs = lcl_stmt.executeQuery(accountSearchQuery);
			 if(accountRs.next())
			 {
				 tdrAccId=accountRs.getString(1);
			 }
			 
		} 
		catch (SQLException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return tdrAccId;
	}

	public String CreateTDRAccount(String accountNo,Connection lcl_conn_dt)
	{
		String brnCd=accountNo.substring(0,4);
		String accType=accountNo.substring(4,8);
		String customerNo=accountNo.substring(8,14);
		String runNo=accountNo.substring(14,16);
		String chkDigit=accountNo.substring(16,17);
		String accountSearchQuery = "Select * from Account_tl A inner join Branch_tl B on A.brn_ID= B.brn_ID inner join Customer Cus on A.Customer_ID= Cus.Customer_ID inner join Account_type At on A.Acc_type_ID = At.Acc_type_ID " +
				" WHERE B.Brn_cd = '"+ brnCd +"' and lpad(At.Acc_Type_CD,4,'0')= '"+accType+"' and lpad(Cus.Customer_No,6,'0')='"+customerNo+"' and A.Check_Digit='"+chkDigit+"' and lpad(A.run_no,2,'0')='"+runNo+"' -- and B.Brn_cd='"+Session.GetBranchCode()+"'";
		
		java.sql.Statement lcl_stmt;
		ResultSet accountRs = null;
		try 
		{
			 lcl_stmt= lcl_conn_dt.createStatement();
			 accountRs = lcl_stmt.executeQuery(accountSearchQuery);
			 accountRs.next();
		} 
		catch (SQLException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String query = "Select Account_id FROM FINAL TABLE (INSERT INTO ACCOUNT_TL(Brn_Id, Acc_Type_Id, Customer_Id, Run_No, Check_Digit, Title, Balance, Block_Amnt, Curr_Cd_Id, Status_Id)" +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?))";
		String tdrAccId = null;
		try 
		{
			PreparedStatement preparedStatement = lcl_conn_dt.prepareStatement(query);
			preparedStatement.setInt(1,accountRs.getInt("Brn_Id"));
			preparedStatement.setInt(2,2);
			preparedStatement.setInt(3,accountRs.getInt("Customer_Id"));
			preparedStatement.setInt(4,01);
			preparedStatement.setInt(5,1);
			preparedStatement.setString(6,accountRs.getString("Title")+ " TDR Account");
			preparedStatement.setFloat(7, (float) 0.00);
			preparedStatement.setFloat(8, (float) 0.00);
			preparedStatement.setInt(9, accountRs.getInt("Curr_Cd_Id"));
			preparedStatement.setInt(10, 1);
			ResultSet tdrAcc= preparedStatement.executeQuery();
			if(tdrAcc.next())
			{
				tdrAccId=tdrAcc.getString(1);
			}
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tdrAccId;
	}
	
	public String CreateDeal(TermDepositApplicationDTO tdaDTO,Connection lcl_conn_dt)
	{
		
		String query = "Select Deal_id From final Table (INSERT INTO TDR_DEAL(Deal_Date, Deal_Status, TDR_APP_ID, TDR_ACCOUNT_ID, BRN_ID)" +
				"VALUES (?, ?, ?, ?, (SELECT BRN_ID FROM BRANCH_TL WHERE Brn_Cd = ?)))";
		String dealNo=null;
		try 
		{
			PreparedStatement preparedStatement = lcl_conn_dt.prepareStatement(query);
			preparedStatement.setString(1,tdaDTO.GetApplicationDate());
			preparedStatement.setInt(2,1);
			preparedStatement.setInt(3,Integer.parseInt(tdaDTO.GetApplicationNo().substring(0,5)));
			preparedStatement.setInt(4,Integer.parseInt(tdaDTO.GetTDRAccountID()));
			preparedStatement.setString(5,Session.GetBranchCode());
			ResultSet dealrs=preparedStatement.executeQuery();
			if(dealrs.next())
			{
				dealNo=dealrs.getString(1);
			}
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dealNo;
	}
	
	public Date getMaturityDate(Date date, ComboItem selectedTenure)
	{
		String maturityDateQuery = "SELECT Add_Months('"+date+"',p.tenure) maturity_date FROM tdr_Product p where p.ID = '"+selectedTenure.getId()+"'";
		Connection lcl_conn_dt = utility.db_conn();
		ResultSet maturityDate =null;
		java.sql.Statement lcl_stmt;
		try {
			 lcl_stmt= lcl_conn_dt.createStatement();
			 maturityDate = lcl_stmt.executeQuery(maturityDateQuery);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date mdate=null;
		try {
			while(maturityDate.next())
			{
				 mdate= maturityDate.getDate("maturity_date");
			}
			return mdate;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String insertTDRApplication(TermDepositApplicationDTO TDADTO)
	{
		String todayDate = TDADTO.GetApplicationDate();
		java.sql.Date startDate=null;
		try {
			startDate= utility.toDate("yyyy-MM-dd", todayDate);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		ComboItem selectedTenure = TDADTO.GetSelectedTenure();
		Date mdate = getMaturityDate(startDate, selectedTenure);
		ComboItem selectedMOF = TDADTO.GetSelectedMOF();
		ComboItem selectedMaturityAction= TDADTO.GetSelectedMaturityAction();

		
		String applicationSno;
		String year;
		String applicationNo=null;
		Connection lcl_conn_dt = utility.db_conn();

        String insertQuery = "SELECT lpad(APPLICATION_ID,5,'0'),Year(Application_date) FROM FINAL TABLE (INSERT INTO TDR_Application (Holder_name,Amount,Input_by,Maturity_date,Application_date,TDR_Request_DOC,TDR_App_status,Product_Id,Maturity_Action,Mode_of_fund,Principal_Fund_Crd_Acc_ID,Prof_Nom_Acc_ID,TDR_Request_Doc_Name,Account_ID ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?))";
        
        try {
    		
        	PreparedStatement preparedStatement1 = lcl_conn_dt.prepareStatement(insertQuery);
        	
        	preparedStatement1.setString(1, TDADTO.GetAccountTitle());
        	preparedStatement1.setFloat(2, TDADTO.GetTDRAmount());
        	preparedStatement1.setString(3,Session.GetUserName().toString());
        	preparedStatement1.setDate(4, mdate);
        	preparedStatement1.setDate(5, startDate);
        	preparedStatement1.setBytes(6, TDADTO.GetFileData());
        	preparedStatement1.setInt(7,1);
        	preparedStatement1.setInt(8,selectedTenure.getId());
        	preparedStatement1.setInt(9,selectedMaturityAction.getId());
        	preparedStatement1.setInt(10,selectedMOF.getId());
        	preparedStatement1.setLong(11, Long.parseLong(TDADTO.GetAccountID()));
        	preparedStatement1.setLong(12, Long.parseLong(TDADTO.GetAccountID()));
        	preparedStatement1.setString(13,TDADTO.GetFileName());
        	preparedStatement1.setString(14,TDADTO.GetAccountID());
            
        	

            ResultSet rs = preparedStatement1.executeQuery();
            
     		//ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
     		if(rs.next())
     		{
     			lcl_conn_dt.commit();
     			applicationSno =rs.getString(1);
     			year = rs.getString(2);
     			applicationNo=applicationSno + "/" +  TDADTO.GetAccountNo().substring(8, 14)+"/"+year;
     			
     		}
     		else{
     			lcl_conn_dt.rollback();
     		}
           
        }catch(Exception exp)
        {
        	System.out.println(exp.getMessage());
        	exp.printStackTrace();
        }
        return applicationNo;
	}

	public int updateTDRApplication(TermDepositApplicationDTO TDADTO,float prevAmnt)
	{
		String todayDate = TDADTO.GetApplicationDate();
		java.sql.Date startDate=null;
		try {
			startDate= utility.toDate("yyyy-MM-dd", todayDate);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		int rs=-1;
		float diff= TDADTO.GetTDRAmount() - prevAmnt;
		ComboItem selectedTenure = TDADTO.GetSelectedTenure();
		Date mdate = getMaturityDate(startDate, selectedTenure);
		ComboItem selectedMOF = TDADTO.GetSelectedMOF();
		ComboItem selectedMaturityAction= TDADTO.GetSelectedMaturityAction();
		Connection lcl_conn_dt = utility.db_conn();

        String query = "update TDR_Application set Amount= ? ,Input_by= ?,Maturity_date= ?,Application_date= ?,TDR_Request_DOC= ? ,Product_Id =? ,Maturity_Action =? ,Mode_of_fund =? ,Principal_Fund_Crd_Acc_Id =? ,Prof_Nom_Acc_Id =? ,TDR_Request_Doc_Name =?  where application_id= ? ";
        String updateBlockAmnt="Update Account_tl set block_Amnt=block_Amnt+ ? where account_id= ? and balance >= ? ";
      
        try {
    		lcl_conn_dt.setAutoCommit(false);
    		lcl_conn_dt.setTransactionIsolation(lcl_conn_dt.TRANSACTION_READ_COMMITTED);
    		
        	PreparedStatement preparedStatement = lcl_conn_dt.prepareStatement(query);
        	PreparedStatement preparedStatement1 = lcl_conn_dt.prepareStatement(updateBlockAmnt);

            preparedStatement.setFloat(1, TDADTO.GetTDRAmount());
            preparedStatement.setString(2,Session.GetUserName().toString());
            preparedStatement.setDate(3, mdate);
            preparedStatement.setDate(4, startDate);
            preparedStatement.setBytes(5, TDADTO.GetFileData());
            preparedStatement.setInt(6,selectedTenure.getId());
            preparedStatement.setInt(7,selectedMaturityAction.getId());
            preparedStatement.setInt(8,selectedMOF.getId());
            preparedStatement.setLong(9,Long.parseLong(TDADTO.GetAccountID()));
            preparedStatement.setLong(10,Long.parseLong(TDADTO.GetAccountID()));
            preparedStatement.setString(11,TDADTO.GetFileName());
            preparedStatement.setInt(12,Integer.parseInt(TDADTO.GetApplicationNo().substring(0,5)));
            
        	preparedStatement1.setFloat(1, diff);
        	preparedStatement1.setInt(2, Integer.parseInt(TDADTO.GetAccountID()));
        	preparedStatement1.setFloat(3, TDADTO.GetTDRAmount());
            
        	
            rs = preparedStatement.executeUpdate();
            int updatecheck=preparedStatement1.executeUpdate();
            
     		if(rs >=1  && updatecheck >=1 )
     		{
     			lcl_conn_dt.commit();
     			return 1;
     		}
     		else{
     			lcl_conn_dt.rollback();
     		}
     		//ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        }catch(Exception exp)
        {
        	System.out.println(exp.getMessage());
        	exp.printStackTrace();
        }
        return -1;
	}
	
	public int AuthorizeTDRApplication(TermDepositApplicationDTO TDADTO)
	{
		String todayDate = Session.GetBranchDate();
		java.sql.Date tdate=null;
		try {
			tdate= utility.toDate("yyyy-MM-dd", todayDate);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		String CreateVoucherquery = "Select voucher_id from Final Table (insert into Voucher (brn_id,voucher_type_id,voucher_date) values ((select brn_cd from Branch_tl where brn_cd= ?), 1 ,? ))";
		String dealVoucherQuery="insert into tdr_deal_voucher(deal_id,voucher_id,approved_by) values ((select deal_id from tdr_deal where tdr_app_id= ?),?,?)";
		String TransactionQuery="insert into transaction_tl (Amount,Cus_Account_ID,Voucher_ID,Trans_type_id) values (? ,? ,? ,?  ) ";
		String updateCustAccountQuery= "Update account_tl set balance=balance- ? , block_amnt= block_amnt - ? where account_id =? ";
		String updateTdrAccountQuery= "Update account_tl set balance=balance+ ?  where account_id =?";
		String TDRAppAuthorizeQuery="update tdr_application set tdr_app_status=3 where application_id = ?";
		
		Connection lcl_conn_dt = utility.db_conn();
		int debitstatus=0;
		int crdstatus=0;
		long voucherID= 0;
		int dealVoucherStatus=0;
		int updateappstatus=0;
		int updateCustAccountStatus=0;
		int updateTdrAccountstatus=0;
		try 
		{
			lcl_conn_dt.setAutoCommit(false);
			lcl_conn_dt.setTransactionIsolation(lcl_conn_dt.TRANSACTION_READ_COMMITTED);
			PreparedStatement preparedStatement = lcl_conn_dt.prepareStatement(CreateVoucherquery);
			
			preparedStatement.setString(1,Session.GetBranchCode());
			preparedStatement.setDate(2,tdate);
			ResultSet voucher= preparedStatement.executeQuery();


			
			while(voucher.next())
			{
				voucherID=voucher.getLong(1);
			}
			if(voucherID > 0 )
			{
				PreparedStatement preparedStatement1 = lcl_conn_dt.prepareStatement(dealVoucherQuery);
				preparedStatement1.setLong(1,Long.parseLong(TDADTO.GetApplicationNo().substring(0, 5)));
				preparedStatement1.setLong(2, voucherID);
				preparedStatement1.setString(3,Session.GetUserName());
				dealVoucherStatus=preparedStatement1.executeUpdate();
				
				PreparedStatement preparedStatement2 = lcl_conn_dt.prepareStatement(TransactionQuery);
				preparedStatement2.setDouble(1, TDADTO.GetTDRAmount());
				preparedStatement2.setLong(2, Long.parseLong( TDADTO.GetAccountID()));
				preparedStatement2.setLong(3, voucherID);
				preparedStatement2.setInt(4, 2);
				debitstatus=preparedStatement2.executeUpdate();
				
				preparedStatement2.setDouble(1, TDADTO.GetTDRAmount());
				preparedStatement2.setLong(2, Long.parseLong( TDADTO.GetTDRAccountID()));
				preparedStatement2.setLong(3, voucherID);
				preparedStatement2.setInt(4, 1);
				crdstatus=preparedStatement2.executeUpdate();
				
				PreparedStatement preparedStatement3 = lcl_conn_dt.prepareStatement(updateCustAccountQuery);
				preparedStatement3.setDouble(1, TDADTO.GetTDRAmount());
				preparedStatement3.setDouble(2,TDADTO.GetTDRAmount());
				preparedStatement3.setLong(3,Long.parseLong(TDADTO.GetAccountID()));
				updateCustAccountStatus=preparedStatement3.executeUpdate();
				
				
				PreparedStatement preparedStatement4 = lcl_conn_dt.prepareStatement(updateTdrAccountQuery);
				preparedStatement4.setDouble(1, TDADTO.GetTDRAmount());
				preparedStatement4.setLong(2, Long.parseLong(TDADTO.GetTDRAccountID()));
				updateTdrAccountstatus=		preparedStatement4.executeUpdate();
				if(debitstatus >0 && crdstatus >0 && updateCustAccountStatus> 0 && updateTdrAccountstatus >0 )
				{
					PreparedStatement preparedStatement5 = lcl_conn_dt.prepareStatement(TDRAppAuthorizeQuery);
					preparedStatement5.setLong(1, Long.parseLong( TDADTO.GetApplicationNo().substring(0, 5)));
					updateappstatus=preparedStatement5.executeUpdate();		
					if(updateappstatus > 0)
					{
						lcl_conn_dt.commit();
						return 1;
					}
				}
				
			}
			lcl_conn_dt.rollback();
		} 
		catch (SQLException e) 
		{
			try {
				lcl_conn_dt.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
//	
	public String RejectTDRApplication(TermDepositApplicationDTO TDADTO)
	{
		Connection lcl_conn_dt = utility.db_conn();
		String updateCustAccountQuery= "Update account_tl set  block_amnt= block_amnt + ? where account_id =? ";
		String TDRAppAuthorizeQuery="update tdr_application set tdr_app_status=4 where application_id = ?";
		String TDRDealStatusQuery="SELECT lpad(deal_id,6,'0') As deal_id,Day(deal_date) AS day, year(deal_date) as Year  FROM FINAL TABLE "
				+ "(update tdr_deal set deal_Status = (Select ID from tdr_deal_status where Desc ='Closed') where tdr_app_id = ?)";
		int updateCustAccountStatus=0;
		int TDRAppAuthorizeStatus=0;
		ResultSet dealIdRs;
		String dealno =null;
		
		try {
			lcl_conn_dt.setAutoCommit(false);
			lcl_conn_dt.setTransactionIsolation(lcl_conn_dt.TRANSACTION_READ_COMMITTED);
			PreparedStatement preparedStatement = lcl_conn_dt.prepareStatement(updateCustAccountQuery);
			preparedStatement.setDouble(1, TDADTO.GetTDRAmount());
			preparedStatement.setLong(2, Long.parseLong( TDADTO.GetAccountID()));
			updateCustAccountStatus=preparedStatement.executeUpdate();
			
			PreparedStatement preparedStatement1 = lcl_conn_dt.prepareStatement(TDRAppAuthorizeQuery);
			preparedStatement1.setLong(1, Long.parseLong( TDADTO.GetAccountID()));
			TDRAppAuthorizeStatus=preparedStatement1.executeUpdate();
			
			PreparedStatement preparedStatement2 = lcl_conn_dt.prepareStatement(TDRDealStatusQuery);
			preparedStatement2.setLong(1, Long.parseLong(TDADTO.GetApplicationNo().substring(0, 5)));
			dealIdRs=preparedStatement2.executeQuery();
			
			if(updateCustAccountStatus > 0 && TDRAppAuthorizeStatus >0 && TDRAppAuthorizeStatus >0)
			{
				lcl_conn_dt.commit();
				while(dealIdRs.next())
				{
					dealno=dealIdRs.getString("deal_id")+"/"+Session.GetBranchCode()+"/"+dealIdRs.getString("day")+"/"+dealIdRs.getString("year");
				}
				return dealno;
			}
			else {
				lcl_conn_dt.rollback();
			}
		}catch(Exception e)
		{
			try {
				lcl_conn_dt.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return dealno;
	}
	public int BlockTDRAmount(TermDepositApplicationDTO tdaDTO,Connection lcl_conn_dt)
	{
		
		String updateBlockAmnt="Update Account_tl set block_Amnt=block_Amnt+ ? where account_id= ? and balance >= ? ";
		int updatecheck=0;
		try{
			PreparedStatement preparedStatement2 = lcl_conn_dt.prepareStatement(updateBlockAmnt);
			preparedStatement2.setFloat(1, tdaDTO.GetTDRAmount());
	    	preparedStatement2.setInt(2, Integer.parseInt(tdaDTO.GetAccountID()));
	    	preparedStatement2.setFloat(3, tdaDTO.GetTDRAmount());
	    	updatecheck=preparedStatement2.executeUpdate();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		if(updatecheck >=1)
		{
			return 1;
		}
		else{
			return 0;
		}
	}
	public int OpenTDRApplication(TermDepositApplicationDTO TDADTO)
	{
		Connection lcl_conn_dt = utility.db_conn();
		String dealId=null;
		String tdrAccId=null;
		int appUpdateStatus=0;
		try {
			lcl_conn_dt.setAutoCommit(false);
			lcl_conn_dt.setTransactionIsolation(lcl_conn_dt.TRANSACTION_READ_COMMITTED);
			int blckAmnt=BlockTDRAmount(TDADTO,lcl_conn_dt);
			if(blckAmnt == 1)
			{
				tdrAccId=TDRAccountExist(TDADTO.GetAccountNo());
				if(tdrAccId == null)
				{
					tdrAccId = CreateTDRAccount(TDADTO.GetAccountNo(),lcl_conn_dt);
				}
				if(tdrAccId != null )
				{
					TDADTO.SetTDRAccountID(tdrAccId);
					dealId=CreateDeal(TDADTO,lcl_conn_dt);
				}
				if(dealId != null)
				{
					appUpdateStatus=tdrApplicationStatus(TDADTO,lcl_conn_dt);
				}
				
			}
			if(blckAmnt ==1 && tdrAccId!=null && dealId!=null && appUpdateStatus!=0)
			{
				
				lcl_conn_dt.commit();
				
			}
			else{
				lcl_conn_dt.rollback();
				return 0;
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		return 1;
	}
	
	public  Object[][] GetTDROpeningVoucher(TermDepositApplicationDTO TDADTO)
	{
		String accountSearchQuery = "Select B.brn_cd||lpad(At.acc_type_cd,4,'0')||Cus.Customer_no ||lpad(acc.run_no,2,'0') || acc.Check_digit As AccountNo, At.acc_type_cd from Account_tl acc inner join Branch_tl B on acc.brn_ID= B.brn_ID inner join Customer Cus on acc.Customer_ID= Cus.Customer_ID  inner join Account_type At on acc.Acc_type_ID = At.Acc_type_ID " +
				" WHERE acc.Account_ID in ('"+TDADTO.GetAccountID()+"','"+TDADTO.GetTDRAccountID()+"')";
		Connection lcl_conn_dt = utility.db_conn();
		ResultSet TDRAccounts=null;
		java.sql.Statement lcl_stmt;
		try {
			 lcl_stmt= lcl_conn_dt.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			 TDRAccounts = lcl_stmt.executeQuery(accountSearchQuery);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Object[][] data = new Object[2][4];
		int rowIndex=0;
		try{
		while (TDRAccounts.next()) {   
       	 data[rowIndex][0] = rowIndex+1;
       	 data[rowIndex][1] = TDRAccounts.getString("AccountNo");
       	 data[rowIndex][2] = TDADTO.GetTDRAmount();
       	 if(TDRAccounts.getInt("acc_type_cd")==10)
       	 {
       		data[rowIndex][3]="Cr.";
       	 }
       	 else{
       		data[rowIndex][3]="Dr.";
       	 }
            rowIndex++;
        }
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
//	public long CreateVoucher(TermDepositApplicationDTO TDADTO)
//	{
//		String CreateVoucher="insert into Voucher (brn_id,voucher_type_id,voucher_date) values ((select brn_cd from Branch_tl where brn_cd= ?), 1, ? )";
//		
//	}
//	public void preMatureEncashment(TermDepositApplicationDTO TDADTO)
//	{
//		String =""
//	}
	public Float GetTDRRate(int prodID)
	{
		Connection lcl_conn_dt = utility.db_conn();
		ResultSet TDRRateRS=null;
		
		String tdrAppQuery = "SELECT tdr_rate from tdr_product where ID = " + prodID;
		
		java.sql.Statement lcl_stmt;
		try {
			//lcl_stmt= lcl_conn_dt.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			 lcl_stmt= lcl_conn_dt.createStatement();
			 TDRRateRS = lcl_stmt.executeQuery(tdrAppQuery);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			if(TDRRateRS.next())
			{
				float TDRRate = TDRRateRS.getFloat("tdr_rate");
				return TDRRate;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (float) 0.0;
	}
	public Object[][] GetDealTransactions(String DealId)
	{
		String tdrDealTransQuery = "select td.deal_id, v.voucher_id, trn.transaction_id , v.voucher_date, trn.amount ,trn.trans_type_id, vt.voucher_desc "
				+ ", vt.voucher_type_ID  ,acc.accountno,acc.DESC As Account_Type " +
				"from tdr_deal td inner join tdr_application tdr on tdr.application_id = td.tdr_app_id inner join  account_no acc on " +
				"acc.account_id = tdr.account_id inner join tdr_deal_voucher tdv on td.Deal_ID= tdv.deal_id inner join voucher v on " +
				"tdv.voucher_id = v.voucher_id inner join transaction_tl trn on trn.voucher_id=v.voucher_id inner join voucher_type vt " +
				"on v.voucher_type_id = vt.voucher_type_id  inner join branch_tl b on  b.brn_id = td.brn_ID where  v.voucher_type_id = 2 and " +
				"td.deal_id = '"+DealId.substring(0,6)+"' and b.brn_cd= '"+Session.GetBranchCode()+"' order by v.voucher_id,trn.transaction_id ";
		Connection lcl_conn_dt = utility.db_conn();
		ResultSet tdrDealTrans=null;
		java.sql.Statement lcl_stmt;
		try {
			 lcl_stmt= lcl_conn_dt.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			 tdrDealTrans = lcl_stmt.executeQuery(tdrDealTransQuery);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Object[][] data=null;
		try{
			int rowCount = tdrDealTrans.getRow();
			tdrDealTrans.beforeFirst();

	        // Assuming two columns: name and age
	        int columnCount = 10;
			data = new Object[rowCount][columnCount];
			int rowIndex=0;
		while (tdrDealTrans.next()) {   
			data[rowIndex][0]=tdrDealTrans.getLong("Deal_id");
			data[rowIndex][1]=tdrDealTrans.getLong("voucher_id");
			data[rowIndex][2]=tdrDealTrans.getLong("transaction_id");
			data[rowIndex][3]=tdrDealTrans.getString("voucher_date");
			data[rowIndex][4]=tdrDealTrans.getDouble("amount");
			data[rowIndex][5]=tdrDealTrans.getInt("trans_type_id");
			data[rowIndex][6]=tdrDealTrans.getString("voucher_desc");
			data[rowIndex][7]=tdrDealTrans.getInt("voucher_type_ID");
			data[rowIndex][8]=tdrDealTrans.getString("accountno");
			data[rowIndex][9]=tdrDealTrans.getInt("Account_Type");
            rowIndex++;
        }
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
	public Object[] getSpecialRates(TermDepositApplicationDTO TDADTO)
	{
		String getSpecialRatesQuery="select tenure,tdr_rate from tdr_product where tenure < "
				+ "(select ABS(MONTHS_BETWEEN('"+Session.GetBranchDate()+"','"+TDADTO.GetApplicationDate()+"')) from tdr_Application tdr "
				+ "where tdr.application_id= '"+TDADTO.GetApplicationNo().substring(0,6)+"') order by tenure FETCH FIRST ROW ONLY";
		Connection lcl_conn_dt= utility.db_conn();
		float special_rate =0 ;
		int tenure =0 ;
		java.sql.Statement lcl_stmt;
		
		ResultSet specRateRS= null;
		try {
			 lcl_stmt= lcl_conn_dt.createStatement();
			 specRateRS = lcl_stmt.executeQuery(getSpecialRatesQuery);
			 if(specRateRS.next())
			 {
				 special_rate=specRateRS.getFloat("tdr_rate");
				 tenure=specRateRS.getInt("Tenure");
			 }
			 
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Object [] specialRate=new Object[] {tenure,special_rate};
		return specialRate;
	}
	public int tdrApplicationStatus(TermDepositApplicationDTO TDADTO, Connection lcl_conn_dt )
	{
		String updateTDRQuery = "Update TDR_Application set TDR_APP_STATUS = 2 where APPLICATION_ID = ?";
		int status=0;
		try 
		{
			PreparedStatement preparedStatement = lcl_conn_dt.prepareStatement(updateTDRQuery);
			preparedStatement.setInt(1,Integer.parseInt(TDADTO.GetApplicationNo().substring(0,5)));
			status=preparedStatement.executeUpdate();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}
	public Boolean PrematureEncashmentTransaction(TermDepositApplicationDTO TDADTO,float profitPaid, float actualProfit)
	{
		String todayDate = Session.GetBranchDate();
		java.sql.Date tdate=null;
		try {
			tdate= utility.toDate("yyyy-MM-dd", todayDate);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		//if actual profit < profitpaid than princiapl_fund =princiapl_fund - profitdiff 
		String PrincipalFundVoucher = "Select voucher_id from Final Table (insert into Voucher (brn_id,voucher_type_id,voucher_date) values ((select brn_cd from Branch_tl where brn_cd= ?), (Select voucher_type_Id from voucher_type where voucher_desc = 'TDR Premature Principal Credit') ,? ))";
		String PrincipalFundDealVoucher="insert into tdr_deal_voucher(deal_id,voucher_id,approved_by) values ((select deal_id from tdr_deal where tdr_app_id= ?),?,?)";
		String PrincipalFundTransactionQuery="insert into transaction_tl (Amount,Cus_Account_ID,Voucher_ID,Trans_type_id) values (? ,? ,? ,?  ) ";
		String updateCustAccountQuery= "Update account_tl set balance=balance+ ?  where account_id =? ";
		String updateTdrAccountQuery= "Update account_tl set balance=balance- ?  where account_id =?";
		
		//if actual profit >= profitpaid 
		String ProfitFundVoucher = "Select voucher_id from Final Table (insert into Voucher (brn_id,voucher_type_id,voucher_date) values ((select brn_cd from Branch_tl where brn_cd= ?), (Select voucher_type_Id from voucher_type where voucher_desc = 'TDR Premature Profit Credit') ,? ))";
		String ProfitFundDealVoucher="insert into tdr_deal_voucher(deal_id,voucher_id,approved_by) values ((select deal_id from tdr_deal where tdr_app_id= ?),?,?)";
		//Query to be maid 
		String InternalExpenseAccount="select account_id from internal_account where account_type = 'Expense'";
		String ProfitCrdTransactionQuery="insert into transaction_tl (Amount,Cus_Account_ID,Voucher_ID,Trans_type_id) values (? ,? ,? ,?  ) ";
		String ProfitDbtTransactionQuery="insert into transaction_tl (Amount,?,Voucher_ID,Trans_type_id) values (? ,? ,? ,?  ) ";
		String ProfitUpdateCustAccount= "Update account_tl set balance=balance+ ?  where account_id =? ";
		String ProfitUpdateExpAccount= "Update account_tl set balance=balance- ?  where account_id =?";
		
		// with hodling tax 
		String withHoldingTaxVoucher = "Select voucher_id from Final Table (insert into Voucher (brn_id,voucher_type_id,voucher_date) values ((select brn_cd from Branch_tl where brn_cd= ?), (Select voucher_type_Id from voucher_type where voucher_desc = 'TDR Premature Profit Credit') ,? ))";
		String withHoldingTaxDealVoucher="insert into tdr_deal_voucher(deal_id,voucher_id,approved_by) values ((select deal_id from tdr_deal where tdr_app_id= ?),?,?)";
		// Query to be maid
		String withHoldingTaxaccount = "Select account_id from internal_account where account_Type= 'with holding tax'";
		String withHoldingTaxDebitTransaction="insert into transaction_tl (Amount,Cus_Account_ID,Voucher_ID,Trans_type_id) values (? ,? ,? ,?  )"; //TDR profit WHT transaction_type_id
		String withHoldingTaxCreditTransaction="insert into transaction_tl (Amount,internal_account_id,Voucher_ID,Trans_type_id) values (? ,? ,? ,?  )"; //TDR profit WHT transaction_type_id
		String TaxUpdateCustAccount="Update account_tl set balance=balance- ?  where account_id =?";
		String TaxUpdateTaxAccount="Update account_tl set balance=balance+ ?  where account_id =?";
		
		String TDRAppAuthorizeQuery="update tdr_application set tdr_app_status=(Select ID from tdr_app_status where DESC='Closed Premature') where application_id = ?";
		String TDRDealStatusUpdateQuery="update tdr_deal set deal_status =(Select ID from tdr_deal_status where DESC='Premature Closed') where application_id = ?";
		
		Connection lcl_conn_dt = utility.db_conn();

		try 
		{
			lcl_conn_dt.setAutoCommit(false);
			lcl_conn_dt.setTransactionIsolation(lcl_conn_dt.TRANSACTION_READ_COMMITTED);
			PreparedStatement preparedStatement = lcl_conn_dt.prepareStatement(CreateVoucherquery);
					
				
		return true;
	}
	public Boolean UpdateTDRPreEncashment(TermDepositApplicationDTO TDADTO) 
	{
		Connection lcl_conn_dt= utility.db_conn();
		String updateApplicationQuery ="update tdr_deal set deal_status = 3 where TDR_app_id='"+TDADTO.GetApplicationNo().substring(0,5)+"'";
		java.sql.Statement lcl_stmt; 
		int tdrPreencashStatus=0;
		try {
			lcl_stmt= lcl_conn_dt.createStatement();
			tdrPreencashStatus = lcl_stmt.executeUpdate(updateApplicationQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(tdrPreencashStatus > 0)
		{
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
