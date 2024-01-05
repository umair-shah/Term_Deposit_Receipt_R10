package TermDeposit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import Utilities.MaxLengthNumericField;
import Utilities.MaxLengthPwdField;
import Utilities.MaxLengthTextField;
import Utilities.utility;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.awt.Font;

public class LoginUser {
	private static MaxLengthNumericField branchCodeField;
	private static MaxLengthTextField userIDField;
	private static MaxLengthPwdField passwordField;
	private static JLabel logoLabel;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	
	public LoginUser()
	{
		createWindow();
	}
	private static void createWindow(){
		final JFrame frame = new JFrame("Login");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(701,445);
		frame.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(143, 188, 143));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		

		SpinnerDateModel model = new SpinnerDateModel();
        final JSpinner spinner = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(editor);
        panel.add(spinner);
		


        
		
		panel.repaint();
		
		passwordField = new MaxLengthPwdField(10);
		passwordField.setBounds(403, 221, 200, 20);
		panel.add(passwordField);
		
		logoLabel = new JLabel("");
		ImageIcon logoIcon = new ImageIcon("C:/Users/Mhammad.27388/Desktop/logo.png");
		Image scaledLogoImage = logoIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		ImageIcon scaledLogoIcon = new ImageIcon(scaledLogoImage);
		logoLabel.setIcon(scaledLogoIcon);
		logoLabel.setBounds(10, 30, 311, 356);
		panel.add(logoLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(143, 188, 143));
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Login", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(344, 131, 294, 194);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		userIDField = new MaxLengthTextField(10);
		userIDField.setBounds(128, 37, 133, 20);
		panel_1.add(userIDField);
		userIDField.setColumns(10);
		branchCodeField = new MaxLengthNumericField(4);
		branchCodeField.setBounds(58, 37, 60, 20);
		panel_1.add(branchCodeField);
		branchCodeField.setToolTipText("");
		branchCodeField.setColumns(10);
		
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(58, 148, 89, 23);
		panel_1.add(loginButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.setBounds(172, 148, 89, 23);
		panel_1.add(exitButton);
		
		frame.setVisible(true);
		
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent Login) {
				String branchCode = utility.lpad(branchCodeField.getText().toString(), '0', 4);
				String userId = userIDField.getText().toString();
				String password = passwordField.getText().toString();
				LoginUserService loginUserService = new LoginUserService();
				if(branchCode.isEmpty())
				{
					JOptionPane.showMessageDialog(frame, "Field branch code is required!","Validation Error",JOptionPane.ERROR_MESSAGE);
				}
				else if(userId.isEmpty())
				{
					JOptionPane.showMessageDialog(frame, "Field user id is required!","Validation Error",JOptionPane.ERROR_MESSAGE);
				}
				else if(password.isEmpty())
				{
					JOptionPane.showMessageDialog(frame, "Field password is required!","Validation Error",JOptionPane.ERROR_MESSAGE);
				}
				else if(loginUserService.ValidateBranch(branchCode) == false)
				{
					JOptionPane.showMessageDialog(frame, "Invalid Branch Code","Validation Error",JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					LoginUserDTO loginUserDTO = loginUserService.ValidateUser(branchCode, userId, password);
					if (loginUserDTO.GetResult() == true)
					{
						JOptionPane.showMessageDialog(frame, "Login Success! Last login was on "+ loginUserDTO.GetLastSignon(),"Success",JOptionPane.INFORMATION_MESSAGE);
						loginUserService.SetLastSignOn(userId, branchCode);
						frame.dispose();
						MainMenu mm = new MainMenu();
					}
					else
					{
						JOptionPane.showMessageDialog(frame, "Invalid User ID or Password","Failure",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
	
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		createWindow();

	}
}
