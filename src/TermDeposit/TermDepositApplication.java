package TermDeposit;
import Utilities.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
public class TermDepositApplication {
	private JFrame frame;
	private JPanel panel;
	private JTextField accountTitleField;
	private JTextField branchNameField;
	private JTextField accountNoField;
	private JTextField currencyField;
	private JTextField branchCodeField;
	private JTextField dateField;
	private MaxLengthAmountField totalAmountField;
	private JTextField profitNomAccountField;
	private JTextField principalFundCrField;
	private JLabel lblAccountTitle;
	private JLabel lblBranchName;
	private JLabel lblAccountNo;
	private JLabel lblCurrency;
	private JLabel lblBranchCode;
	private JLabel lblDate;
	private JLabel lblModeOfFund;
	private JComboBox<ComboItem> modeOfFundComboBox;
	private JLabel lblTotalAmount;
	private JLabel lblTenure;
	private JComboBox<ComboItem> tenureComboBox;
	private JLabel lblActionAtMaturity;
	private JComboBox<ComboItem> actionAtMaturityComboBox;
	private JLabel lblProfitNomAccount;
	private JLabel lblPrincipalFundCr;
	private JButton saveButton;
	private JButton rejectButton;
	private JButton authorizeButton;
	private JButton updateButton;
	private JButton selectFileButton;
	private JLabel lblFileName;
	private JButton btnViewFile;
	private JButton btnOpenTDR;
	private UploadFile filehandler;
	private JTable tdrOpeningVoucher;
	JTextField tdrRateField;
	private JLabel tdrRate;
	TermDepositApplicationService tdrService;
	
	
	
	public TermDepositApplication()
	{
		tdrService = new TermDepositApplicationService();
		CreateWindow();
		
	}
	public void NewTermDepositApplication(AccountDTO accountDetails)
	{
		filehandler=new UploadFile();
	
		NewTDA(accountDetails);
		
	}
	public void UpdateTermDepositApplication(TermDepositApplicationDTO TDRApplication)
	{
		filehandler=new UploadFile();
		UpdateTDA(TDRApplication);
	}
	public void AuthorizeTDRApplication(TermDepositApplicationDTO TDRApplication)
	{
		filehandler=new UploadFile();
		AuthorizeTDA(TDRApplication);
	}
	public void OpenTDRApplication(TermDepositApplicationDTO TDRApplication)
	{
		filehandler=new UploadFile();
		OpenTDR(TDRApplication);
	}
	public void TermDepositDealPreMatureEncashment(TermDepositApplicationDTO TDRApplication)
	{
		
		
	}
	public void CreateWindow()
	{
		frame = new JFrame("Term Deposit Application");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(675,656);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		panel = new JPanel();
		panel.setBackground(new Color(0, 128, 128));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		accountTitleField = new JTextField();
		accountTitleField.setEditable(false);
		accountTitleField.setBounds(113, 30, 202, 20);
		panel.add(accountTitleField);
		accountTitleField.setColumns(10);
		
		branchNameField = new JTextField();
		branchNameField.setEditable(false);
		branchNameField.setBounds(436, 30, 210, 20);
		panel.add(branchNameField);
		branchNameField.setColumns(10);
		
		lblAccountTitle = new JLabel("Account Title");
		lblAccountTitle.setBounds(28, 33, 96, 14);
		panel.add(lblAccountTitle);
		
		lblBranchName = new JLabel("Branch Name");
		lblBranchName.setBounds(340, 33, 100, 14);
		panel.add(lblBranchName);
		
		lblAccountNo = new JLabel("Account No");
		lblAccountNo.setBounds(28, 78, 96, 14);
		panel.add(lblAccountNo);
		
		accountNoField = new JTextField();
		accountNoField.setEditable(false);
		accountNoField.setColumns(10);
		accountNoField.setBounds(113, 75, 202, 20);
		panel.add(accountNoField);
		
		lblCurrency = new JLabel("Currency");
		lblCurrency.setBounds(340, 78, 100, 14);
		panel.add(lblCurrency);
		
		currencyField = new JTextField();
		currencyField.setEditable(false);
		currencyField.setColumns(10);
		currencyField.setBounds(436, 75, 210, 20);
		panel.add(currencyField);
		
		lblBranchCode = new JLabel("Branch Code");
		lblBranchCode.setBounds(28, 128, 96, 14);
		panel.add(lblBranchCode);
		
		branchCodeField = new JTextField();
		branchCodeField.setEditable(false);
		branchCodeField.setColumns(10);
		branchCodeField.setBounds(113, 125, 130, 20);
		panel.add(branchCodeField);
		
		lblDate = new JLabel("Date");
		lblDate.setBounds(340, 128, 100, 14);
		panel.add(lblDate);
		
		dateField = new JTextField();
		dateField.setEditable(false);
		dateField.setColumns(10);
		dateField.setBounds(436, 125, 153, 20);
		panel.add(dateField);
		
		lblModeOfFund = new JLabel("Mode of Fund");
		lblModeOfFund.setBounds(28, 175, 96, 14);
		panel.add(lblModeOfFund);
		
		modeOfFundComboBox = new JComboBox<ComboItem>();
		modeOfFundComboBox.setBounds(194, 172, 191, 20);
		panel.add(modeOfFundComboBox);
		
		
		lblTotalAmount = new JLabel("Total Amount");
		lblTotalAmount.setBounds(28, 221, 96, 14);
		panel.add(lblTotalAmount);
		
		totalAmountField = new MaxLengthAmountField(16);
		totalAmountField.setColumns(10);
		totalAmountField.setBounds(194, 218, 191, 20);
		panel.add(totalAmountField);
		
		lblTenure = new JLabel("Tenure");
		lblTenure.setBounds(28, 273, 96, 14);
		panel.add(lblTenure);
		
		tenureComboBox = new JComboBox<ComboItem>();
		tenureComboBox.setBounds(194, 270, 191, 20);
		panel.add(tenureComboBox);
		
		lblActionAtMaturity = new JLabel("Action at Maturity");
		lblActionAtMaturity.setBounds(28, 330, 137, 14);
		panel.add(lblActionAtMaturity);
		
		actionAtMaturityComboBox = new JComboBox<ComboItem>();
		actionAtMaturityComboBox.setBounds(194, 327, 304, 20);
		panel.add(actionAtMaturityComboBox);
		
		lblProfitNomAccount = new JLabel("Profit Nom Account");
		lblProfitNomAccount.setBounds(28, 387, 137, 14);
		panel.add(lblProfitNomAccount);
		
		profitNomAccountField = new JTextField();
		profitNomAccountField.setColumns(10);
		profitNomAccountField.setBounds(194, 384, 304, 20);
		profitNomAccountField.setEditable(false);
		panel.add(profitNomAccountField);
		
		lblPrincipalFundCr = new JLabel("Principal Fund Cr Account");
		lblPrincipalFundCr.setBounds(28, 447, 156, 14);
		panel.add(lblPrincipalFundCr);
		
		principalFundCrField = new JTextField();
		principalFundCrField.setColumns(10);
		principalFundCrField.setBounds(194, 447, 304, 20);
		principalFundCrField.setEditable(false);
		panel.add(principalFundCrField);
		
		
		lblFileName = new JLabel("File Name");
		lblFileName.setBounds(138, 505, 164, 14);
		
		selectFileButton = new JButton("Select File");
		selectFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String Filename=filehandler.selectFile();
					
					if(Filename != "")
					{
						lblFileName.setText(Filename);
						btnViewFile.setVisible(true);
					}
					
					
				}
				catch(Exception e){
					//System.out.println(e.getMessage());
					e.printStackTrace();
				}

			}
		});
		selectFileButton.setBounds(28, 501, 89, 23);
		panel.add(selectFileButton);
		
		JLabel lblMaxMbjpgjpegpngpdf = new JLabel("Max(5 MB) *JPG,*JPEG,*PNG,*PDF");
		lblMaxMbjpgjpegpngpdf.setLabelFor(selectFileButton);
		lblMaxMbjpgjpegpngpdf.setBounds(28, 535, 177, 14);
		panel.add(lblMaxMbjpgjpegpngpdf);
		

		panel.add(lblFileName);
		
		tdrRate = new JLabel("TDR Rate");
		tdrRate.setBounds(409, 273, 96, 14);
		panel.add(tdrRate);
		
		tdrRateField = new JTextField();
		tdrRateField.setColumns(10);
		tdrRateField.setBounds(468, 270, 89, 20);
		panel.add(tdrRateField);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 128, 128));
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Account Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 11, 646, 146);
		panel.add(panel_1);
		
		
		
		
		ResultSet modeOfFundRs = tdrService.GetModeofFunds();
		try 
		{
			modeOfFundComboBox.addItem(new ComboItem(0,"Select Mode"));
			while(modeOfFundRs.next())
			{
				modeOfFundComboBox.addItem(new ComboItem(modeOfFundRs.getInt("ID"),modeOfFundRs.getString("Desc")));
			}
		} 
		catch (SQLException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ResultSet tdrProductRs = tdrService.GetTenure();
		try 
		{
			tenureComboBox.addItem(new ComboItem(0,"Select Tenure"));
			while(tdrProductRs.next())
			{
				tenureComboBox.addItem(new ComboItem(tdrProductRs.getInt("ID"),tdrProductRs.getString("Desc")));
			}
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet actionAtMaturityRs = tdrService.GetActionatMaturity();
		try 
		{
			actionAtMaturityComboBox.addItem(new ComboItem(0,"Select Action"));
			while(actionAtMaturityRs.next())
			{
				actionAtMaturityComboBox.addItem(new ComboItem(actionAtMaturityRs.getInt("ID"),actionAtMaturityRs.getString("Desc")));
			}
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		panel.repaint();
	}

	
	public void UpdateTDA(final TermDepositApplicationDTO TDRAppDto)
	{
		updateButton = new JButton("Update");
		updateButton.setBounds(471, 501, 117, 23);
		panel.add(updateButton);
		panel.repaint();

		final AccountDTO accdto=TDRAppDto.GetAccountDTO();
		
		btnViewFile = new JButton("View File");
		btnViewFile.setBounds(249, 501, 89, 23);
		panel.add(btnViewFile);
		btnViewFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(filehandler.path != null)
				{
					File file = new File(filehandler.path);	
					try {
						filehandler.viewFile(filehandler.readFileData(file), file.getName());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					filehandler.viewFile(TDRAppDto.GetFileData(), TDRAppDto.GetFileName());
				}
		}});
		accountNoField.setText(TDRAppDto.GetAccountNo());
		accountTitleField.setText(TDRAppDto.GetAccountTitle());
		branchCodeField.setText(accdto.GetBranchCode());
		branchNameField.setText(accdto.GetBranchName());
		dateField.setText(TDRAppDto.GetApplicationDate());
		currencyField.setText(accdto.GetCurrency());
		
		int i=0;
		while(i < modeOfFundComboBox.getItemCount())
		{
			if(modeOfFundComboBox.getItemAt(i).getId()== TDRAppDto.GetSelectedMOF().getId())
			{
				modeOfFundComboBox.setSelectedItem(modeOfFundComboBox.getItemAt(i));
			}
			i++;
		}
		totalAmountField.setText(String.valueOf(TDRAppDto.GetTDRAmount()));
		i=0;
		while(i < tenureComboBox.getItemCount())
		{
			if(tenureComboBox.getItemAt(i).getId()== TDRAppDto.GetSelectedTenure().getId())
			{
				tenureComboBox.setSelectedItem(tenureComboBox.getItemAt(i));
			}
			i++;
		}
		i=0;
		while(i < actionAtMaturityComboBox.getItemCount())
		{
			if(actionAtMaturityComboBox.getItemAt(i).getId()== TDRAppDto.GetSelectedMaturityAction().getId())
			{
				actionAtMaturityComboBox.setSelectedItem(actionAtMaturityComboBox.getItemAt(i));
			}
			i++;
		}
		profitNomAccountField.setText(TDRAppDto.GetProfitNomAccount());
		principalFundCrField.setText(TDRAppDto.GetPrincipalFundCrAccount());
		lblFileName.setText(TDRAppDto.GetFileName());
		tdrRateField.setText(String.valueOf(TDRAppDto.GetTDRRate()));
		tdrRateField.setEditable(false);
		tenureComboBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ComboItem selectedTenure= (ComboItem) tenureComboBox.getSelectedItem();
				tdrRateField.setText(tdrService.GetTDRRate(selectedTenure.getId()).toString() + " %");
			}
		});
		updateButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent updatebtnClicked) {
			ComboItem tenureComboItem=(ComboItem) tenureComboBox.getSelectedItem();
			ComboItem actionComboItem=(ComboItem) actionAtMaturityComboBox.getSelectedItem();
			ComboItem modeOfFundComboItem=(ComboItem) modeOfFundComboBox.getSelectedItem();
			if(totalAmountField.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(frame, "Amount is required!","Enter Amount",JOptionPane.ERROR_MESSAGE);
			}
			else if((actionAtMaturityComboBox.getSelectedItem().equals("Credit Principal & Profit to Account (No Rollover)") ||
			   actionAtMaturityComboBox.getSelectedItem().equals("Rollover Principal"))
			   && (profitNomAccountField.getText().isEmpty()))
			{
					JOptionPane.showMessageDialog(frame, "Profit Nomination Account is reuired!","Enter Profit Nomination Account",JOptionPane.ERROR_MESSAGE);
			}
			else if((actionAtMaturityComboBox.getSelectedItem().equals("Credit Principal & Profit to Account (No Rollover)")) && principalFundCrField.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(frame, "Credit Principal Account is reuired!","Enter Credit Principal Account",JOptionPane.ERROR_MESSAGE);
			}
			
			else if(modeOfFundComboItem.getId()== 0)
			{
				JOptionPane.showMessageDialog(frame, "Mode of Fund not Selected","Select Mode of Fund",JOptionPane.ERROR_MESSAGE);
			}
			else if(tenureComboItem.getId()== 0)
			{
				JOptionPane.showMessageDialog(frame, "Tenure not Selected","Select Tenure",JOptionPane.ERROR_MESSAGE);
			}
			else if(actionComboItem.getId()== 0)
			{
				JOptionPane.showMessageDialog(frame, "Action at Maturity not Selected","Select Action at Maturity",JOptionPane.ERROR_MESSAGE);
			}
			
			else if(Float.parseFloat(totalAmountField.getText()) > accdto.GetBalance() )
			{
				JOptionPane.showMessageDialog(frame, "Insufficient amount in account","Insufficient Amount",JOptionPane.ERROR_MESSAGE);
			}
			
			else if(Float.parseFloat(totalAmountField.getText()) < 10000 )
			{
				JOptionPane.showMessageDialog(frame, "Enter TDR Amount greater than 10000 (Min Limit)"," Invalid TDR Amount",JOptionPane.ERROR_MESSAGE);
			}
			
			else{
			TermDepositApplicationDTO TDADTO = new TermDepositApplicationDTO();
			TDADTO.SetApplicationDate(dateField.getText());
			TDADTO.SetSelectedTenure((ComboItem) tenureComboBox.getSelectedItem());
			TDADTO.SetSelectedMOF((ComboItem) modeOfFundComboBox.getSelectedItem());
			TDADTO.SetSelectedMaturityAction((ComboItem) actionAtMaturityComboBox.getSelectedItem());
			TDADTO.SetAccountTitle(accountTitleField.getText());
			TDADTO.SetTDRAmount(Float.parseFloat(totalAmountField.getText()));
			TDADTO.SetPricipalFundCrAccount(principalFundCrField.getText());
			TDADTO.SetProfitNomAccount(profitNomAccountField.getText());
			TDADTO.SetAccountNo(accdto.GetAccountNo());
			TDADTO.SetAccountID(accdto.GetAccountID());
			TDADTO.SetApplicationNo(TDRAppDto.GetApplicationNo());
			if(filehandler.path != null)
			{
				File file = new File(filehandler.path);	
				try {
					TDADTO.SetFiledata(filehandler.readFileData(file), file.getName());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				try {
					TDADTO.SetFiledata(TDRAppDto.GetFileData(), TDRAppDto.GetFileName());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			int status= tdrService.updateTDRApplication(TDADTO,TDRAppDto.GetTDRAmount());
			if(status==-1)
			{
				JOptionPane.showMessageDialog(frame, "Updated unsuccessful","Unsuccessful",JOptionPane.ERROR_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(frame, "Application Updated Successfully \n Application ID = "+TDRAppDto.GetApplicationNo(),"Successful",JOptionPane.INFORMATION_MESSAGE);
				frame.dispose();
			}
			}
		}
		});
	}
	
	public void NewTDA(final AccountDTO accountDTO)
	{
		saveButton = new JButton("Save");
		saveButton.setBounds(453, 580, 118, 23);
		panel.add(saveButton);
		
		btnViewFile = new JButton("View File");
		btnViewFile.setBounds(249, 501, 89, 23);
		panel.add(btnViewFile);
		btnViewFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(filehandler.path != null)
				{
					File file = new File(filehandler.path);	
					try {
						filehandler.viewFile(filehandler.readFileData(file), file.getName());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					JOptionPane.showMessageDialog(frame, "File Not Selected","Select File First",JOptionPane.ERROR_MESSAGE);
				}
		}});	
		
		btnViewFile.setVisible(false);
		panel.repaint();
		accountNoField.setText(accountDTO.GetAccountNo());
		accountTitleField.setText(accountDTO.GetAccountTitle());
		branchCodeField.setText(accountDTO.GetBranchCode());
		branchNameField.setText(accountDTO.GetBranchName());
		dateField.setText(accountDTO.GetBranchDate());
		currencyField.setText(accountDTO.GetCurrency());
		profitNomAccountField.setText(accountDTO.GetAccountNo());
		principalFundCrField.setText(accountDTO.GetAccountNo());
		tenureComboBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ComboItem selectedTenure= (ComboItem) tenureComboBox.getSelectedItem();
				tdrRateField.setText(tdrService.GetTDRRate(selectedTenure.getId()).toString() + " %");
			}
		});
		tdrRateField.setEditable(false);
		saveButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent saveButtonClicked) {
			ComboItem tenureComboItem=(ComboItem) tenureComboBox.getSelectedItem();
			ComboItem actionComboItem=(ComboItem) actionAtMaturityComboBox.getSelectedItem();
			ComboItem modeOfFundComboItem=(ComboItem) modeOfFundComboBox.getSelectedItem();
			if(totalAmountField.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(frame, "Amount is required!","Enter Amount",JOptionPane.ERROR_MESSAGE);
			}
			else if((actionAtMaturityComboBox.getSelectedItem().equals("Credit Principal & Profit to Account (No Rollover)") ||
			   actionAtMaturityComboBox.getSelectedItem().equals("Rollover Principal"))
			   && (profitNomAccountField.getText().isEmpty()))
			{
					JOptionPane.showMessageDialog(frame, "Profit Nomination Account is reuired!","Enter Profit Nomination Account",JOptionPane.ERROR_MESSAGE);
			}
			else if((actionAtMaturityComboBox.getSelectedItem().equals("Credit Principal & Profit to Account (No Rollover)")) && principalFundCrField.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(frame, "Credit Principal Account is reuired!","Enter Credit Principal Account",JOptionPane.ERROR_MESSAGE);
			}
			
			else if(modeOfFundComboItem.getId()== 0)
			{
				JOptionPane.showMessageDialog(frame, "Mode of Fund not Selected","Select Mode of Fund",JOptionPane.ERROR_MESSAGE);
			}
			else if(tenureComboItem.getId()== 0)
			{
				JOptionPane.showMessageDialog(frame, "Tenure not Selected","Select Tenure",JOptionPane.ERROR_MESSAGE);
			}
			else if(actionComboItem.getId()== 0)
			{
				JOptionPane.showMessageDialog(frame, "Action at Maturity not Selected","Select Action at Maturity",JOptionPane.ERROR_MESSAGE);
			}

			
			
			else if(Float.parseFloat(totalAmountField.getText()) > accountDTO.GetBalance())
			{
				JOptionPane.showMessageDialog(frame, "Insufficient amount in account","Insufficient Amount",JOptionPane.ERROR_MESSAGE);
			}
			else if (filehandler.path == null || filehandler.path.isEmpty()) {
				
	            JOptionPane.showMessageDialog(frame, "Please select a file.");
	        }
			else if(Float.parseFloat(totalAmountField.getText()) < 10000 )
			{
				JOptionPane.showMessageDialog(frame, "Enter TDR Amount greater than 10000 (Min Limit)"," Invalid TDR Amount",JOptionPane.ERROR_MESSAGE);
			}
			else
			{
			File file = new File(filehandler.path);
			
			TermDepositApplicationDTO TDADTO = new TermDepositApplicationDTO();
			TDADTO.SetApplicationDate(dateField.getText());
			TDADTO.SetSelectedTenure((ComboItem) tenureComboBox.getSelectedItem());
			TDADTO.SetSelectedMOF((ComboItem) modeOfFundComboBox.getSelectedItem());
			TDADTO.SetSelectedMaturityAction((ComboItem) actionAtMaturityComboBox.getSelectedItem());
			TDADTO.SetAccountTitle(accountTitleField.getText());
			TDADTO.SetTDRAmount(Float.parseFloat(totalAmountField.getText()));
			TDADTO.SetPricipalFundCrAccount(principalFundCrField.getText());
			TDADTO.SetProfitNomAccount(profitNomAccountField.getText());
			TDADTO.SetAccountNo(accountDTO.GetAccountNo());
			TDADTO.SetAccountID(accountDTO.GetAccountID());
			try {
				TDADTO.SetFiledata(filehandler.readFileData(file), file.getName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String applicationNo= tdrService.insertTDRApplication(TDADTO);
			if(applicationNo == null)
			{
				JOptionPane.showMessageDialog(frame, "Insert unsuccessful","Unsuccessful",JOptionPane.ERROR_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(frame, "Application Created Successfully \n Application No = "+applicationNo,"Successful",JOptionPane.INFORMATION_MESSAGE);
				frame.dispose();
			}
			}
		}
		});
	}

	public void AuthorizeTDA(final TermDepositApplicationDTO TDRAppDto)
	{
		final AccountDTO accdto=TDRAppDto.GetAccountDTO();
		rejectButton = new JButton("Reject");
		rejectButton.setBounds(453, 580, 118, 23);
		panel.add(rejectButton);
		
		authorizeButton = new JButton("Authorize");
		authorizeButton.setBounds(126, 580, 118, 23);
		panel.add(authorizeButton);
		
		authorizeButton.setEnabled(false);
		rejectButton.setEnabled(false);
		btnViewFile = new JButton("View File");
		btnViewFile.setBounds(249, 501, 89, 23);
		panel.add(btnViewFile);
		btnViewFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			filehandler.viewFile(TDRAppDto.GetFileData(), TDRAppDto.GetFileName());
			authorizeButton.setEnabled(true);
			rejectButton.setEnabled(true);
		}});

		accountNoField.setText(TDRAppDto.GetAccountNo());
		accountTitleField.setText(TDRAppDto.GetAccountTitle());
		branchCodeField.setText(accdto.GetBranchCode());
		branchNameField.setText(accdto.GetBranchName());
		dateField.setText(TDRAppDto.GetApplicationDate());
		currencyField.setText(accdto.GetCurrency());
		int i=0;
		while(i < modeOfFundComboBox.getItemCount())
		{
			if(modeOfFundComboBox.getItemAt(i).getId()== TDRAppDto.GetSelectedMOF().getId())
			{
				modeOfFundComboBox.setSelectedItem(modeOfFundComboBox.getItemAt(i));
			}
			i++;
		}
		totalAmountField.setText(String.valueOf(TDRAppDto.GetTDRAmount()));
		i=0;
		while(i < tenureComboBox.getItemCount())
		{
			if(tenureComboBox.getItemAt(i).getId()== TDRAppDto.GetSelectedTenure().getId())
			{
				tenureComboBox.setSelectedItem(tenureComboBox.getItemAt(i));
			}
			i++;
		}
		i=0;
		while(i < actionAtMaturityComboBox.getItemCount())
		{
			if(actionAtMaturityComboBox.getItemAt(i).getId()== TDRAppDto.GetSelectedMaturityAction().getId())
			{
				actionAtMaturityComboBox.setSelectedItem(actionAtMaturityComboBox.getItemAt(i));
			}
			i++;
		}
		profitNomAccountField.setText(TDRAppDto.GetProfitNomAccount());
		principalFundCrField.setText(TDRAppDto.GetPrincipalFundCrAccount());
		lblFileName.setText(TDRAppDto.GetFileName());
		
		modeOfFundComboBox.setEnabled(false);
		totalAmountField.setEditable(false);
		tenureComboBox.setEnabled(false);
		actionAtMaturityComboBox.setEnabled(false);
		selectFileButton.setEnabled(false);
		
		tdrRateField.setText(String.valueOf(TDRAppDto.GetTDRRate()));
		tdrRateField.setEditable(false);

		authorizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent authorizebtnClicked) {
				String dealNo = tdrService.AuthorizeTDRApplication(TDRAppDto);
				if(dealNo != null)
				{
					JOptionPane.showMessageDialog(frame, "Application Authorized Successfully \n Application ID = "+TDRAppDto.GetApplicationNo()+"\n Deal No = "+dealNo,"Successful",JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "Application Authorized UnSuccessfully \n Application ID = "+TDRAppDto.GetApplicationNo(),"Successful",JOptionPane.INFORMATION_MESSAGE);
				}
				frame.dispose();
			}
		});
		rejectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent authorizebtnClicked) {
				String dealNo = tdrService.RejectTDRApplication(TDRAppDto);
				if(dealNo !=null)
				{
					JOptionPane.showMessageDialog(frame, "Application Rejected Successful \n Application ID = "+TDRAppDto.GetApplicationNo()+"\n Deal No = "+dealNo,"Successful",JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "Application Rejected UnSuccessful \n Application ID = "+TDRAppDto.GetApplicationNo(),"Successful",JOptionPane.INFORMATION_MESSAGE);
				}
				frame.dispose();
			}
		});
	}
	public void OpenTDR(final TermDepositApplicationDTO TDRAppDto)
	{
		final AccountDTO accdto=TDRAppDto.GetAccountDTO();
		btnOpenTDR = new JButton("Save");
		btnOpenTDR.setBounds(453, 580, 118, 23);
		panel.add(btnOpenTDR);
		
		btnViewFile = new JButton("View File");
		btnViewFile.setBounds(249, 501, 89, 23);
		panel.add(btnViewFile);
		btnViewFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			filehandler.viewFile(TDRAppDto.GetFileData(), TDRAppDto.GetFileName());
		}});

		accountNoField.setText(TDRAppDto.GetAccountNo());
		accountTitleField.setText(TDRAppDto.GetAccountTitle());
		branchCodeField.setText(accdto.GetBranchCode());
		branchNameField.setText(accdto.GetBranchName());
		dateField.setText(TDRAppDto.GetApplicationDate());
		currencyField.setText(accdto.GetCurrency());
		int i=0;
		while(i < modeOfFundComboBox.getItemCount())
		{
			if(modeOfFundComboBox.getItemAt(i).getId()== TDRAppDto.GetSelectedMOF().getId())
			{
				modeOfFundComboBox.setSelectedItem(modeOfFundComboBox.getItemAt(i));
			}
			i++;
		}
		totalAmountField.setText(String.valueOf(TDRAppDto.GetTDRAmount()));
		i=0;
		while(i < tenureComboBox.getItemCount())
		{
			if(tenureComboBox.getItemAt(i).getId()== TDRAppDto.GetSelectedTenure().getId())
			{
				tenureComboBox.setSelectedItem(tenureComboBox.getItemAt(i));
			}
			i++;
		}
		i=0;
		while(i < actionAtMaturityComboBox.getItemCount())
		{
			if(actionAtMaturityComboBox.getItemAt(i).getId()== TDRAppDto.GetSelectedMaturityAction().getId())
			{
				actionAtMaturityComboBox.setSelectedItem(actionAtMaturityComboBox.getItemAt(i));
			}
			i++;
		}
		profitNomAccountField.setText(TDRAppDto.GetProfitNomAccount());
		principalFundCrField.setText(TDRAppDto.GetPrincipalFundCrAccount());
		lblFileName.setText(TDRAppDto.GetFileName());
		
		modeOfFundComboBox.setEnabled(false);
		totalAmountField.setEditable(false);
		tenureComboBox.setEnabled(false);
		actionAtMaturityComboBox.setEnabled(false);
		selectFileButton.setEnabled(false);
		tdrRateField.setText(String.valueOf(TDRAppDto.GetTDRRate()));
		tdrRateField.setEditable(false);
		btnOpenTDR.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent authorizebtnClicked) {
				
				
				final String dealNo = tdrService.OpenTDRApplication(TDRAppDto);
				
				if(dealNo != null)
				{
					final JFrame frame = new JFrame("TDR Opening Voucher");
					frame.setResizable(false);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setSize(834,362);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					
					JPanel panel = new JPanel();
					panel.setBackground(new Color(0, 128, 128));
					frame.getContentPane().add(panel, BorderLayout.CENTER);
					panel.setLayout(null);

					String[] columnNames = {"S.No", "Account No", "Account Title", "Debit", "Credit"};
				

					Object[][] data = tdrService.GetTellerTDROpeningVoucher(TDRAppDto);
						
					
					DefaultTableModel model = new DefaultTableModel(data, columnNames){
						 public boolean isCellEditable(int row, int column)
						 {
						     return false;
						 }
						};
					
					JTable TDROpeningVoucherTable = new JTable(model);
					JScrollPane jScrollPane = new JScrollPane(TDROpeningVoucherTable);
					jScrollPane.setForeground(Color.BLACK);
					jScrollPane.setLocation(31, 44);
					//termDepositTable.setBounds(142, 196, 202, -91);
					panel.add(jScrollPane);
					jScrollPane.setSize(771,173);
					JButton btnOk = new JButton("Ok");
					btnOk.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							frame.dispose();
							JOptionPane.showMessageDialog(frame, "TDR Opened Successfully \n Application ID = "+TDRAppDto.GetApplicationNo() + "\n Deal Number = "+dealNo,"Successful",JOptionPane.INFORMATION_MESSAGE);
						}
					});
					btnOk.setBounds(690, 268, 89, 23);
					panel.add(btnOk);
					jScrollPane.setVisible(true);
					panel.repaint();
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "TDR Opened UnSuccessfully \n Application ID = "+TDRAppDto.GetApplicationNo(),"Successful",JOptionPane.INFORMATION_MESSAGE);
				}
				frame.dispose();
			}
		});
		
	}

	public void PrematureEncashment(final TermDepositApplicationDTO TDRAppDto)
	{
		final AccountDTO accdto=TDRAppDto.GetAccountDTO();
		JButton prematureEncashmentButton;
		filehandler=new UploadFile();
		btnViewFile = new JButton("View File");
		btnViewFile.setBounds(249, 501, 89, 23);
		panel.add(btnViewFile);
		btnViewFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			filehandler.viewFile(TDRAppDto.GetFileData(), TDRAppDto.GetFileName());
		}});

		accountNoField.setText(TDRAppDto.GetAccountNo());
		accountTitleField.setText(TDRAppDto.GetAccountTitle());
		branchCodeField.setText(accdto.GetBranchCode());
		branchNameField.setText(accdto.GetBranchName());
		dateField.setText(TDRAppDto.GetApplicationDate());
		currencyField.setText(accdto.GetCurrency());
		int i=0;
		while(i < modeOfFundComboBox.getItemCount())
		{
			if(modeOfFundComboBox.getItemAt(i).getId()== TDRAppDto.GetSelectedMOF().getId())
			{
				modeOfFundComboBox.setSelectedItem(modeOfFundComboBox.getItemAt(i));
			}
			i++;
		}
		totalAmountField.setText(String.valueOf(TDRAppDto.GetTDRAmount()));
		i=0;
		while(i < tenureComboBox.getItemCount())
		{
			if(tenureComboBox.getItemAt(i).getId()== TDRAppDto.GetSelectedTenure().getId())
			{
				tenureComboBox.setSelectedItem(tenureComboBox.getItemAt(i));
			}
			i++;
		}
		i=0;
		while(i < actionAtMaturityComboBox.getItemCount())
		{
			if(actionAtMaturityComboBox.getItemAt(i).getId()== TDRAppDto.GetSelectedMaturityAction().getId())
			{
				actionAtMaturityComboBox.setSelectedItem(actionAtMaturityComboBox.getItemAt(i));
			}
			i++;
		}
		profitNomAccountField.setText(TDRAppDto.GetProfitNomAccount());
		principalFundCrField.setText(TDRAppDto.GetPrincipalFundCrAccount());
		lblFileName.setText(TDRAppDto.GetFileName());
		tdrRateField.setText(String.valueOf(TDRAppDto.GetTDRRate()));
		tdrRateField.setEditable(false);
		modeOfFundComboBox.setEnabled(false);
		totalAmountField.setEditable(false);
		tenureComboBox.setEnabled(false);
		actionAtMaturityComboBox.setEnabled(false);
		selectFileButton.setEnabled(false);
		
		frame.setSize(662,819);
		JLabel lblProfitPaid = new JLabel("Profit Paid");
		lblProfitPaid.setBounds(28, 583, 96, 14);
		panel.add(lblProfitPaid);
		
		MaxLengthAmountField profitPaidField = new MaxLengthAmountField(16);
		profitPaidField.setColumns(10);
		profitPaidField.setBounds(194, 580, 191, 20);
		panel.add(profitPaidField);
		
		JLabel lblProfitPayable = new JLabel("Profit Payable");
		lblProfitPayable.setBounds(28, 630, 96, 14);
		panel.add(lblProfitPayable);
		
		MaxLengthAmountField profitPayableField = new MaxLengthAmountField(16);
		profitPayableField.setColumns(10);
		profitPayableField.setBounds(194, 627, 191, 20);
		panel.add(profitPayableField);
		
		JLabel lblPayableAmount = new JLabel("Payable Amount");
		lblPayableAmount.setBounds(28, 678, 96, 14);
		panel.add(lblPayableAmount);
		
		JTextField payableAmountField = new JTextField();
		payableAmountField.setColumns(10);
		payableAmountField.setBounds(194, 675, 191, 20);
		panel.add(payableAmountField);
		
		
		
		Object[][] data=tdrService.GetDealTransactions(utility.lpad(TDRAppDto.GetTDRDealId(), '0', 6));
		float totalProfitPaid=0;
		
		for (int row = 0; row < data.length; row++) {
			if(data[row][8].toString().equals(TDRAppDto.GetAccountNo())  && Integer.parseInt(data[row][5].toString()) == 1  )
			{
				totalProfitPaid+= Float.parseFloat(data[row][4].toString());
			}
	    }
		Object [] applicableTenure= tdrService.getSpecialRates(TDRAppDto);
		int tenure=Integer.parseInt(applicableTenure[0].toString());
		Float tdrRate=Float.parseFloat(applicableTenure[1].toString());
		//calculating actual profit to be paid
		float payableProfit=0;
		float actual_profit_tobe_paid=0;
		long savingDays=0;
		try {
			savingDays = utility.daysBetween(TDRAppDto.GetApplicationDate(),tenure,Session.GetBranchDate());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				///calculate the payable profit as per the previous tenure
		float savingRate= (float)tdrService.getSavingRate();
		
		payableProfit+=(tdrRate/100) * TDRAppDto.GetTDRAmount();
		//saving account Rate on remaing duration. 
		payableProfit+= (float)((savingRate/100.0)*TDRAppDto.GetTDRAmount()*(savingDays/365.0));
//		payableProfit+= (float)((savingRate/100.0)* TDRAppDto.GetTDRAmount() * Float.parseFloat(dateDiff[1].toString())/30);;
		actual_profit_tobe_paid=payableProfit-totalProfitPaid;
		
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		String payableProfitString = decimalFormat.format(payableProfit);
		String totalProfitPaidString = decimalFormat.format(totalProfitPaid);
		String actual_profit_tobe_paid_String = decimalFormat.format(actual_profit_tobe_paid);
		
		profitPaidField.setText(totalProfitPaidString);
		profitPayableField.setText(payableProfitString);
		payableAmountField.setText(actual_profit_tobe_paid_String);
		
		profitPaidField.setText(totalProfitPaidString);
		profitPayableField.setText(payableProfitString);
		payableAmountField.setText(actual_profit_tobe_paid_String);
		
		profitPaidField.setEditable(false);
		profitPayableField.setEditable(false);
		payableAmountField.setEditable(false);
		if(Session.getUserRoleId()==1)
		{

			prematureEncashmentButton = new JButton("Premature Encashment");
			prematureEncashmentButton.setBounds(274, 733, 143, 23);
			panel.add(prematureEncashmentButton);
			prematureEncashmentButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					String dealno= tdrService.UpdateTDRPreEncashment(TDRAppDto);
					if(dealno != null)
					{
						JOptionPane.showMessageDialog(frame, "TDR Pre Encashment Transaction generated Successfully \n Application ID = "+TDRAppDto.GetApplicationNo() + "\n Deal No = "+dealno,"Successful",JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						JOptionPane.showMessageDialog(frame, "TDR Pre Encashment Transaction UnSuccessful \n Application ID = "+TDRAppDto.GetApplicationNo(),"Successful",JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		else if(Session.getUserRoleId()==2)
		{
			final float paidprofit=totalProfitPaid;
			final float actualProfit=payableProfit;
			prematureEncashmentButton = new JButton("Authorize Pre Mature");
			prematureEncashmentButton.setBounds(274, 733, 143, 23);
			panel.add(prematureEncashmentButton);
			prematureEncashmentButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					
					String dealno= tdrService.PrematureEncashmentTransaction(TDRAppDto,paidprofit,actualProfit);
					if(dealno != null)
					{
						final JFrame frame = new JFrame("TDR PreEncashment Voucher");
						frame.setResizable(false);
						frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						frame.setSize(834,362);
						frame.setLocationRelativeTo(null);
						frame.setVisible(true);
						
						JPanel panel = new JPanel();
						panel.setBackground(new Color(0, 128, 128));
						frame.getContentPane().add(panel, BorderLayout.CENTER);
						panel.setLayout(null);

						String[] columnNames = {"S.No", "Account No", "Account Title", "Debit", "Credit"};
					

						Object[][] data = tdrService.GetTDRPreMatureVoucher(TDRAppDto);
							
						
						DefaultTableModel model = new DefaultTableModel(data, columnNames){
							 public boolean isCellEditable(int row, int column)
							 {
							     return false;
							 }
							};
						
						JTable TDROpeningVoucherTable = new JTable(model);
						JScrollPane jScrollPane = new JScrollPane(TDROpeningVoucherTable);
						jScrollPane.setForeground(Color.BLACK);
						jScrollPane.setLocation(31, 44);
						//termDepositTable.setBounds(142, 196, 202, -91);
						panel.add(jScrollPane);
						jScrollPane.setSize(771,173);
						JButton btnOk = new JButton("Ok");
						btnOk.setBounds(690, 268, 89, 23);
						panel.add(btnOk);
						jScrollPane.setVisible(true);
						panel.repaint();
						btnOk.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								frame.dispose();
							}
							});
						JOptionPane.showMessageDialog(frame, "TDR Pre Encashment Transaction generated Successfully \n Application ID = "+TDRAppDto.GetApplicationNo() + "\n Deal No = "+dealno,"Successful",JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						JOptionPane.showMessageDialog(frame, "TDR Pre Encashment Transaction UnSuccessful \n Application ID = "+TDRAppDto.GetApplicationNo(),"Successful",JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		
	}
//	
//	public void SearchDeal()
//	{
//		String DealNo;
//		final JFrame frame = new JFrame("Search Deal");
//		frame.setResizable(false);
//		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		frame.setSize(368,161);
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
//		
//		JPanel panel = new JPanel();
//		panel.setBackground(new Color(0, 128, 128));
//		frame.getContentPane().add(panel, BorderLayout.CENTER);
//		panel.setLayout(null);
//		
//		final JTextField dealNo = new JTextField(); 
//		dealNo.setBounds(120, 30, 197, 20);
//		panel.add(dealNo);
//		dealNo.setColumns(10);
//		
//		JLabel lblDealNo = new JLabel("Deal No:");
//		lblDealNo.setFont(new Font("Tahoma", Font.PLAIN, 14));
//		lblDealNo.setBounds(31, 31, 100, 14);
//		panel.add(lblDealNo);
//		
//		JButton searchButton = new JButton("Search");
//		searchButton.setBounds(28, 76, 89, 23);
//		panel.add(searchButton);
//		
//		JButton exitButton = new JButton("Exit");
//		exitButton.setBounds(228, 76, 89, 23);
//		panel.add(exitButton);
//		panel.repaint();
//		
//		searchButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent searchButtonClicked) {
//				String DealNo = dealNo.getText().toString();
//				if(DealNo.isEmpty())
//				{
//					JOptionPane.showMessageDialog(frame, "Field Deal NO is required!","Validation Error",JOptionPane.ERROR_MESSAGE);
//				}
//				else
//				{
//					SearchAccountService searchAccountService = new SearchAccountService();
//					AccountDTO accountDTO = searchAccountService.ValidateAccount(accountNo);
//					if(accountDTO.GetResult() == false)
//					{
//						JOptionPane.showMessageDialog(frame, "Deal Not Exist","Invalid Deal No",JOptionPane.ERROR_MESSAGE);
//					}
//					else if(!(accountDTO.GetAccountStatus().equals("Active")))
//					{
//						JOptionPane.showMessageDialog(frame, "Deal is " + accountDTO.GetAccountStatus() + "!","Account Not Active",JOptionPane.ERROR_MESSAGE);
//					}
//					else
//					{
//						frame.dispose();
//						TermDepositApplication tdr = new TermDepositApplication();
//						tdr.NewTermDepositApplication(accountDTO);
//					}
//				}
//			}
//		});
//		
//		exitButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent exitButtonPressed) {
//				frame.dispose();
//			}
//		});
//	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AccountDTO accountDTO = new AccountDTO();
		TermDepositApplication tda = new TermDepositApplication();
	}
}
