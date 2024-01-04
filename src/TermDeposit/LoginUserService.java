package TermDeposit;
import Utilities.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginUserService {
	
	public boolean ValidateBranch(String branchCode)
	{
		String loginQuery = "Select lpad(brn_cd,4,'0') from branch_tl where lpad(brn_cd,4,'0') = '" + branchCode + "'";
		java.sql.Statement lcl_stmt;
		ResultSet rs =null;
		Connection lcl_conn_dt = utility.db_conn();
		try {
			 lcl_stmt= lcl_conn_dt.createStatement();
			 rs = lcl_stmt.executeQuery(loginQuery);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try 
		{
			if(rs.next())
			{
				return true;
			}
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public LoginUserDTO ValidateUser(String branchCode, String userID, String password) 
	{
		LoginUserDTO loginUserDTO = new LoginUserDTO();
		String loginQuery = "Select ut.*, bt.today_date FROM Users_TL UT INNER JOIN Branch_Tl BT ON UT.Brn_Cd = BT.BRN_Cd WHERE LOGIN_ID = '" + userID + "' AND lpad(UT.brn_cd,4,'0') = '"+ branchCode +"'" ;
		java.sql.Statement lcl_stmt;
		ResultSet rs =null;
		Connection lcl_conn_dt = utility.db_conn();
		try {
			 lcl_stmt= lcl_conn_dt.createStatement();
			 rs = lcl_stmt.executeQuery(loginQuery);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try 
		{
			if(rs.next())
			{
				String actual_branch = (String)rs.getString("BRN_CD");
				actual_branch=utility.lpad(actual_branch,'0',4);
				String actual_password = (String)rs.getString("PASSWORD");
				//Checking
				if(branchCode.equals(actual_branch) && password.equals(actual_password))
				{	
					String lastSignOn= rs.getString("Last_signon");
					Session.SetUserName(userID);
					Session.SetUserRoleId(rs.getInt("User_role_ID"));
					Session.SetBranchCode(branchCode);
					Session.SetBranchDate(rs.getString("TODAY_DATE"));
					loginUserDTO.SetResult(true);
					loginUserDTO.SetLastSignOn(lastSignOn);
					return loginUserDTO;
				}
			}
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loginUserDTO.SetResult(false);
		return loginUserDTO;
	}

	public void SetLastSignOn(String userID, String branchCode)
	{
		String setLastSignOnQuery = "UPDATE USERS_TL SET LAST_SIGNON = (Select TODAY_DATE FROM Branch_TL WHERE BRN_CD = '" + branchCode + "') WHERE LOGIN_ID = '" + userID + "'";
		//String getDateQuery = "Select TODAY_DATE FROM Branch_TL WHERE BRN_CD = '" + branchCode + "'";
		java.sql.Statement lcl_stmt;
		Connection lcl_conn_dt = utility.db_conn();
		try {
			 lcl_stmt= lcl_conn_dt.createStatement();
			 lcl_stmt.executeUpdate(setLastSignOnQuery);
		} 
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
