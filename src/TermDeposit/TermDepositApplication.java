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
import javax.swing.border.LineBorder;
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
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;
	
	
	
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
		frame.setSize(599,711);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		panel = new JPanel();
		panel.setBackground(new Color(143, 188, 143));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(143, 188, 143));
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Account Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 11, 573, 146);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		lblAccountNo = new JLabel("Account No");
		lblAccountNo.setBounds(10, 56, 96, 14);
		panel_1.add(lblAccountNo);
		
		accountNoField = new JTextField();
		accountNoField.setBounds(102, 53, 188, 20);
		panel_1.add(accountNoField);
		accountNoField.setEditable(false);
		accountNoField.setColumns(10);
		
		lblBranchCode = new JLabel("Branch Code");
		lblBranchCode.setBounds(314, 93, 96, 14);
		panel_1.add(lblBranchCode);
		
		lblAccountTitle = new JLabel("Account Title");
		lblAccountTitle.setBounds(10, 22, 96, 14);
		panel_1.add(lblAccountTitle);
		
		branchCodeField = new JTextField();
		branchCodeField.setBounds(401, 90, 130, 20);
		panel_1.add(branchCodeField);
		branchCodeField.setEditable(false);
		branchCodeField.setColumns(10);
		
		lblBranchName = new JLabel("Branch Name");
		lblBranchName.setBounds(10, 93, 100, 14);
		panel_1.add(lblBranchName);
		
		branchNameField = new JTextField();
		branchNameField.setBounds(102, 90, 188, 20);
		panel_1.add(branchNameField);
		branchNameField.setEditable(false);
		branchNameField.setColumns(10);
		
		lblCurrency = new JLabel("Currency");
		lblCurrency.setBounds(314, 56, 100, 14);
		panel_1.add(lblCurrency);
		
		currencyField = new JTextField();
		currencyField.setBounds(401, 53, 130, 20);
		panel_1.add(currencyField);
		currencyField.setEditable(false);
		currencyField.setColumns(10);
		
		accountTitleField = new JTextField();
		accountTitleField.setBounds(102, 19, 429, 20);
		panel_1.add(accountTitleField);
		accountTitleField.setEditable(false);
		accountTitleField.setColumns(10);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Term Deposit Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBackground(new Color(143, 188, 143));
		panel_2.setBounds(10, 175, 573, 231);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		lblModeOfFund = new JLabel("Mode of Fund");
		lblModeOfFund.setBounds(282, 59, 96, 14);
		panel_2.add(lblModeOfFund);
		
		modeOfFundComboBox = new JComboBox<ComboItem>();
		modeOfFundComboBox.setBounds(378, 56, 170, 20);
		panel_2.add(modeOfFundComboBox);

		
		
		lblTotalAmount = new JLabel("Total Amount");
		lblTotalAmount.setBounds(10, 59, 96, 14);
		panel_2.add(lblTotalAmount);
		
		totalAmountField = new MaxLengthAmountField(16);
		totalAmountField.setBounds(96, 56, 161, 20);
		panel_2.add(totalAmountField);
		totalAmountField.setColumns(10);
		
		tdrRate = new JLabel("TDR Rate");
		tdrRate.setBounds(282, 87, 96, 14);
		panel_2.add(tdrRate);
		
		lblTenure = new JLabel("Tenure");
		lblTenure.setBounds(10, 87, 96, 14);
		panel_2.add(lblTenure);
		
		tenureComboBox = new JComboBox<ComboItem>();
		tenureComboBox.setBounds(96, 84, 161, 20);
		panel_2.add(tenureComboBox);
		
		tdrRateField = new JTextField();
		tdrRateField.setBounds(378, 84, 170, 20);
		panel_2.add(tdrRateField);
		tdrRateField.setColumns(10);
		
		lblActionAtMaturity = new JLabel("Action at Maturity");
		lblActionAtMaturity.setBounds(10, 124, 137, 14);
		panel_2.add(lblActionAtMaturity);
		
		actionAtMaturityComboBox = new JComboBox<ComboItem>();
		actionAtMaturityComboBox.setBounds(150, 121, 260, 20);
		panel_2.add(actionAtMaturityComboBox);
		
		lblProfitNomAccount = new JLabel("Profit Nom Account");
		lblProfitNomAccount.setBounds(10, 152, 137, 14);
		panel_2.add(lblProfitNomAccount);
		
		profitNomAccountField = new JTextField();
		profitNomAccountField.setBounds(150, 149, 260, 20);
		panel_2.add(profitNomAccountField);
		profitNomAccountField.setColumns(10);
		profitNomAccountField.setEditable(false);
		
		lblPrincipalFundCr = new JLabel("Principal Fund Account");
		lblPrincipalFundCr.setBounds(10, 183, 156, 14);
		panel_2.add(lblPrincipalFundCr);
		
		principalFundCrField = new JTextField();
		principalFundCrField.setBounds(150, 180, 260, 20);
		panel_2.add(principalFundCrField);
		principalFundCrField.setColumns(10);
		principalFundCrField.setEditable(false);
		
		lblDate = new JLabel("Date");
		lblDate.setBounds(10, 31, 100, 14);
		panel_2.add(lblDate);
		
		dateField = new JTextField();
		dateField.setBounds(97, 25, 160, 20);
		panel_2.add(dateField);
		dateField.setEditable(false);
		dateField.setColumns(10);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Upload File", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBackground(new Color(143, 188, 143));
		panel_3.setBounds(10, 417, 573, 85);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		
		selectFileButton = new JButton("Select File");
		selectFileButton.setBounds(10, 26, 114, 23);
		panel_3.add(selectFileButton);
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
		
		
		lblFileName = new JLabel("File Name");
		lblFileName.setBounds(134, 30, 177, 14);
		panel_3.add(lblFileName);
		
		JLabel lblMaxMbjpgjpegpngpdf = new JLabel("Max(5 MB) *JPG,*JPEG,*PNG,*PDF");
		lblMaxMbjpgjpegpngpdf.setBounds(10, 60, 198, 14);
		panel_3.add(lblMaxMbjpgjpegpngpdf);
		lblMaxMbjpgjpegpngpdf.setLabelFor(selectFileButton);
		
		actionAtMaturityComboBox.addItem(new ComboItem(0,"Select Action"));
		tenureComboBox.addItem(new ComboItem(0,"Select Tenure"));
		modeOfFundComboBox.addItem(new ComboItem(0,"Select Mode"));
		
		
		ResultSet modeOfFundRs = tdrService.GetModeofFunds();
		try 
		{
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
		panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBackground(new Color(143, 188, 143));
		panel_4.setBounds(10, 513, 573, 45);
		panel.add(panel_4);
		panel_4.setLayout(null);
		
		updateButton = new JButton("Update");
		updateButton.setBounds(431, 11, 132, 23);
		panel_4.add(updateButton);
		panel.repaint();

		final AccountDTO accdto=TDRAppDto.GetAccountDTO();
		
		btnViewFile = new JButton("View File");
		btnViewFile.setBounds(321, 26, 89, 23);
		panel_3.add(btnViewFile);
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
		panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBackground(new Color(143, 188, 143));
		panel_4.setBounds(10, 513, 573, 45);
		panel.add(panel_4);
		panel_4.setLayout(null);
		
		saveButton = new JButton("Save");
		saveButton.setBounds(431, 11, 132, 23);
		panel_4.add(saveButton);
		
		btnViewFile = new JButton("View File");
		btnViewFile.setBounds(321, 26, 89, 23);
		panel_3.add(btnViewFile);
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
		panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBackground(new Color(143, 188, 143));
		panel_4.setBounds(10, 513, 573, 45);
		panel.add(panel_4);
		panel_4.setLayout(null);
		
		final AccountDTO accdto=TDRAppDto.GetAccountDTO();
		authorizeButton = new JButton("Authorize");
		authorizeButton.setBounds(101, 11, 118, 23);
		panel_4.add(authorizeButton);
		
		rejectButton = new JButton("Reject");
		rejectButton.setBounds(349, 11, 118, 23);
		panel_4.add(rejectButton);
		
		authorizeButton.setEnabled(false);
		rejectButton.setEnabled(false);
		btnViewFile = new JButton("View File");
		btnViewFile.setBounds(321, 26, 89, 23);
		panel_3.add(btnViewFile);
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
		btnOpenTDR = new JButton("Open TDR");
		btnOpenTDR.setBounds(431, 11, 132, 23);
		panel_4.add(btnOpenTDR);
		
		btnViewFile = new JButton("View File");
		btnViewFile.setBounds(321, 26, 89, 23);
		panel_3.add(btnViewFile);
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
					panel.setBackground(new Color(143, 188, 143));
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
		btnViewFile.setBounds(321, 26, 89, 23);
		panel_3.add(btnViewFile);
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
		
		panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBackground(new Color(143, 188, 143));
		panel_5.setBounds(10, 513, 573, 85);
		panel.add(panel_5);
		panel_5.setLayout(null);
		
		JLabel lblProfitPaid = new JLabel("Profit Paid");
		lblProfitPaid.setBounds(10, 11, 86, 14);
		panel_5.add(lblProfitPaid);
		
		MaxLengthAmountField profitPaidField = new MaxLengthAmountField(16);
		profitPaidField.setBounds(119, 8, 157, 20);
		panel_5.add(profitPaidField);
		profitPaidField.setColumns(10);
		
		JLabel lblProfitPayable = new JLabel("Profit Payable");
		lblProfitPayable.setBounds(298, 11, 110, 14);
		panel_5.add(lblProfitPayable);
		
		MaxLengthAmountField profitPayableField = new MaxLengthAmountField(16);
		profitPayableField.setBounds(406, 8, 157, 20);
		panel_5.add(profitPayableField);
		profitPayableField.setColumns(10);
		
		JLabel lblPayableAmount = new JLabel("Payable Amount");
		lblPayableAmount.setBounds(10, 57, 96, 14);
		panel_5.add(lblPayableAmount);
		
		JTextField payableAmountField = new JTextField();
		payableAmountField.setBounds(119, 54, 158, 20);
		panel_5.add(payableAmountField);
		payableAmountField.setColumns(10);
		
		panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.setBackground(new Color(143, 188, 143));
		panel_6.setBounds(10, 609, 573, 50);
		panel.add(panel_6);
		panel_6.setLayout(null);
	
		
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
		Object [] dateDiff = utility.calculateDateDifference(TDRAppDto.GetApplicationDate(),tenure,Session.GetBranchDate());
				///calculate the payable profit as per the previous tenure
		payableProfit+=(tdrRate/100) * TDRAppDto.GetTDRAmount();
		//saving account Rate on remaing duration. 
		payableProfit+= (float)(5/100.0)*TDRAppDto.GetTDRAmount()* Float.parseFloat(dateDiff[0].toString());
		payableProfit+= (float)((5/100.0)* TDRAppDto.GetTDRAmount() * Float.parseFloat(dateDiff[1].toString())/30);;
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
			prematureEncashmentButton.setBounds(188, 11, 197, 23);
			panel_6.add(prematureEncashmentButton);
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
			prematureEncashmentButton.setBounds(188, 11, 197, 23);
			panel_6.add(prematureEncashmentButton);
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
