package TermDeposit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Utilities.MaxLengthNumericField;
import Utilities.utility;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TermDepositSearch {
	private JTable table;
	TermDepositSearchService TDRSS =null;
	JFrame frame =null;
	public TermDepositSearch()
	{
		TDRSS= new TermDepositSearchService();
	}
	public void SearchDeal()
	{
		final JFrame frmSearchDeal = new JFrame("Search Account");
		frmSearchDeal.setTitle("Search Deal");
		frmSearchDeal.setResizable(false);
		frmSearchDeal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSearchDeal.setSize(495,161);
		frmSearchDeal.setLocationRelativeTo(null);
		frmSearchDeal.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(143, 188, 143));
		frmSearchDeal.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		MaxLengthNumericField branchCodeField = new MaxLengthNumericField(4);
		branchCodeField.setText(Session.GetBranchCode());
		branchCodeField.setEditable(false);
		branchCodeField.setBounds(227, 30, 59, 20);
		panel.add(branchCodeField);
		branchCodeField.setColumns(10);
		
		JLabel lblDealNo = new JLabel("Deal No:");
		lblDealNo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDealNo.setBounds(31, 31, 100, 14);
		panel.add(lblDealNo);
		
		JButton searchButton = new JButton("Search");
		searchButton.setBounds(128, 85, 89, 23);
		panel.add(searchButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.setBounds(258, 85, 89, 23);
		panel.add(exitButton);
		
		final MaxLengthNumericField maxDealNoField = new MaxLengthNumericField(6);
		maxDealNoField.setColumns(10);
		maxDealNoField.setBounds(104, 30, 89, 20);
		panel.add(maxDealNoField);
		
		final MaxLengthNumericField dateField = new MaxLengthNumericField(2);
		dateField.setColumns(10);
		dateField.setBounds(320, 30, 40, 20);
		panel.add(dateField);
		
		final MaxLengthNumericField yearField = new MaxLengthNumericField(4);
		yearField.setColumns(10);
		yearField.setBounds(394, 30, 79, 20);
		panel.add(yearField);
		
		JLabel label = new JLabel("/");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(203, 17, 14, 42);
		panel.add(label);
		
		JLabel label_1 = new JLabel("/");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_1.setBounds(296, 17, 14, 42);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("/");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_2.setBounds(370, 17, 14, 42);
		panel.add(label_2);
		panel.repaint();
		
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent exitButtonPressed) {
				frmSearchDeal.dispose();
			}
		});
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent searchButtonPressed) {
				frame = new JFrame();
				if(maxDealNoField.getText().toString().isEmpty() || yearField.getText().toString().isEmpty()|| dateField.getText().toString().isEmpty())
				{
					
					JOptionPane.showMessageDialog(frame, "Enter complete deal number!","Validation Error",JOptionPane.ERROR_MESSAGE);
				}
				else{
					String DealId= String.format(utility.lpad(maxDealNoField.getText().toString(), '0', 6))+Session.GetBranchCode()+utility.lpad(dateField.getText().toString(), '0', 2)+yearField.getText().toString();
					TermDepositApplicationDTO TDADTO =TDRSS.GetTDRDealDetails(DealId,21);
					if(TDADTO.GetTDRDealId()!= null)
					{
						if(TDADTO.GetApplicationDate().equals(Session.GetBranchDate()) )
			        	{
							JOptionPane.showMessageDialog(frame, "Premature Encashment is not possible on same day","Invalid Operation",JOptionPane.ERROR_MESSAGE);

			        	}
						else
						{
							frmSearchDeal.dispose();
							TermDepositApplication TDRApplication= new TermDepositApplication();
							TDRApplication.PrematureEncashment(TDADTO);
						}
					}
					
					else{
						JOptionPane.showMessageDialog(frame, "Deal No doesn't exist!","Invalid Deal NO",JOptionPane.ERROR_MESSAGE);

					}
					
					
				}
			}
		});
	}
	public void CreateTDRApplicationWindow(final int operation) throws SQLException 
	{
		final JFrame frame = new JFrame("Term Deposit");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(834,362);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(143, 188, 143));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		String[] columnNames = {"Account No", "Amount", "Application No",  "Tenure", "Action at Maturity","Maturity_date"};
		
		Object[][] data=null;
		if(Session.getUserRoleId()==1 && operation == 1)
		{
			 data = TDRSS.GetUnauthorizedApplication("Update");
		}
		else if(Session.getUserRoleId()==1 && operation ==2)
		{
			data = TDRSS.GetUnauthorizedApplication("Open");
		}
		else if (Session.getUserRoleId()==2 )
		{
			data = TDRSS.GetUnauthorizedApplication("Authorize");
		}
		

//		Object[][] dataArray = new Object[data.size()][];
//        data.toArray(dataArray);
		DefaultTableModel model = new DefaultTableModel(data, columnNames){
			 public boolean isCellEditable(int row, int column)
			 {
			     return false;
			 }
			};

		JTable termDepositTable = new JTable(model);
		JScrollPane jScrollPane = new JScrollPane(termDepositTable);
		jScrollPane.setForeground(Color.BLACK);
		jScrollPane.setLocation(31, 44);
		//termDepositTable.setBounds(142, 196, 202, -91);
		panel.add(jScrollPane);
		jScrollPane.setSize(771,173);
		
		termDepositTable.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
					//JOptionPane.showMessageDialog(frame, "Amount is required!","row = "+table.getValueAt(row, 0).toString() ,JOptionPane.ERROR_MESSAGE);
		        	String applicationNo= table.getValueAt(row, 2).toString();
		        	TermDepositApplicationDTO TDRAppDto = TDRSS.GetTDRAppDetails(applicationNo);
		        	
		        	TermDepositApplication TDRApplication= new TermDepositApplication();
		        	if(Session.getUserRoleId()==1 && operation==2 )
		        	{
		        		TDRApplication.OpenTDRApplication(TDRAppDto);
		        	}
		        	else if(Session.getUserRoleId()==1 && operation==1)
		        	{
		        		TDRApplication.UpdateTermDepositApplication(TDRAppDto);
		        	}
		        	else if(Session.getUserRoleId()==2 ) {
		        		
		        		TDRApplication.AuthorizeTDRApplication(TDRAppDto);
		        	}
		        	frame.dispose();
		        }
		    }
		});
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnBack.setBounds(690, 268, 89, 23);
		panel.add(btnBack);
		jScrollPane.setVisible(true);
		
		
		panel.repaint();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//TermDepositSearch tds = new TermDepositSearch();

	}
	public void AuthorizePreMatureWindow() throws SQLException 
	{
		final JFrame frame = new JFrame("Term Deposit");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(834,362);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 128));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		String[] columnNames = {"Account No", "Deal No", "Application No","Amount", "Tenure", "Action at Maturity","Maturity_date"};
		
		Object[][] data=null;
		data=TDRSS.GetPrematureTDRApplications();
		DefaultTableModel model = new DefaultTableModel(data, columnNames){
			 public boolean isCellEditable(int row, int column)
			 {
			     return false;
			 }
			};
			
		JTable termDepositTable = new JTable(model);
		JScrollPane jScrollPane = new JScrollPane(termDepositTable);
		jScrollPane.setForeground(Color.BLACK);
		jScrollPane.setLocation(31, 44);
		//termDepositTable.setBounds(142, 196, 202, -91);
		panel.add(jScrollPane);
		jScrollPane.setSize(771,173);
		
		termDepositTable.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
					//JOptionPane.showMessageDialog(frame, "Amount is required!","row = "+table.getValueAt(row, 0).toString() ,JOptionPane.ERROR_MESSAGE);
		        	String DealNo= table.getValueAt(row, 1).toString();
		        	DealNo.replace("/","");
		        	TermDepositApplicationDTO TDRAppDto = TDRSS.GetTDRDealDetails(DealNo,3);
		        	TermDepositApplication TDRApplication= new TermDepositApplication();
		        	TDRApplication.PrematureEncashment(TDRAppDto);
		        	
		        	//frame.dispose();
		        }
		    }
		});
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnBack.setBounds(690, 268, 89, 23);
		panel.add(btnBack);
		jScrollPane.setVisible(true);
		
		
		panel.repaint();
	}

}
