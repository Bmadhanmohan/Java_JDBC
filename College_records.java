package my_jdbc_projects;

import java.awt.*;
import java.sql.*;

import javax.swing.*;
import java.awt.CardLayout;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.Color;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Scrollbar;

public class College_records {

	private JFrame frame;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception {
		 
	   
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					College_records window = new College_records();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	public College_records() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws Exception {
		 Class.forName("com.mysql.cj.jdbc.Driver");  
		    String user="root";
			String pass="Madhan2001@mr";
			String db="jdbc:mysql:///mytables";
			Connection con=DriverManager.getConnection(db, user,pass);
			//String query="select * from students_cse where Companyname is not null";
			//Statement st=con.createStatement();
			//ResultSet rs= st.executeQuery(query);
			CallableStatement rs=con.prepareCall("{call students_cse(?,?,?,?,?)}");
			//String s="18751A0512";
			
			
		frame = new JFrame();
		frame.setBounds(100, 100, 871, 640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
	
		JComboBox company = new JComboBox(new String[] {"","Unemployee","Atos syntel",
				"BOSCH",
				"Byjus" ,
				"capegemini",
				"Capgemini",
				"cognizant",
				"hcl tech",
				"IDFC First Bank",
				"KROLL",
				"Masters",
				"persistant",
				"Revature",
				"Soprasteria",
				"TCS",
				"virtusa"
		});
		company.setBounds(467, 57, 112, 22);
		frame.getContentPane().add(company);
		
		JComboBox gender = new JComboBox(new String[] {"","Male","Female"});
		gender.setBounds(589, 57, 112, 22);
		frame.getContentPane().add(gender);
		
		
		search = new JTextField();
		search.setBounds(275, 59, 133, 20);
		frame.getContentPane().add(search);
		search.setColumns(10);
		
		
		
		table = new JTable();
		table.setColumnSelectionAllowed(true);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setFont(new Font("Tahoma", Font.BOLD, 13));
		table.setBackground(new Color(255, 255, 204));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
			},
			new String[] {
				"ROLL NUMBER", "NAME", "CURRENT_COMPANY", "GENDER"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(116);
		table.getColumnModel().getColumn(1).setPreferredWidth(162);
		table.getColumnModel().getColumn(2).setPreferredWidth(137);
		table.setForeground(new Color(255, 0, 0));
		table.setBounds(80, 109, 682, 337);
		frame.getContentPane().add(table);
		
		JLabel lblNewLabel = new JLabel("ROLL NUMBER                    NAME                                           CURRENTCOMPANY               GENDER");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBackground(new Color(255, 255, 0));
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBounds(80, 90, 682, 14);
		frame.getContentPane().add(lblNewLabel);

		String query="select * from students_cse limit 20";
		Statement st=con.createStatement();
		ResultSet rst= st.executeQuery(query);
		DefaultTableModel model=(DefaultTableModel)table.getModel();
		model.setNumRows(0);
	    while(rst.next()) {
		
					model.addRow(new Object[] {"<html><body><h3 style='color:green'>"+rst.getString(1)+"</h3></body></html>",
				    	               "<html><body><h3 style='color:maroon'>"+rst.getString(2)+"</h3></body></html>",
				    	               "<html><body><h3 style='color:red'>"+rst.getString(3)+"</h3></body></html>",
				    	               "<html><body><h3 style='color:orange'>"+rst.getString(4)+"</h3></body></html>"});
			
		}
		
		
		JButton searc = new JButton("@");
		searc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text=search.getText();
				try {
				String query="select * from students_cse where rollno=?";
				PreparedStatement pt=con.prepareStatement(query);
				pt.setString(1,text);
				ResultSet rr=pt.executeQuery();
				if(rr.next()) {
				
				CallableStatement rs=con.prepareCall("{call students_cse(?,?,?,?,?)}");
				rs.setString(1,text);
				rs.registerOutParameter(2,Types.VARCHAR);
				rs.registerOutParameter(3,Types.VARCHAR);
				rs.registerOutParameter(4,Types.VARCHAR);
				rs.registerOutParameter(5,Types.VARCHAR);
				rs.execute();
				DefaultTableModel model=(DefaultTableModel)table.getModel();
				model.setNumRows(0);

				model.addRow(new Object[] {"<html><body><h3 style='color:green'>"+rs.getString(2)+"</h3></body></html>",
	    	               "<html><body><h3 style='color:maroon'>"+rs.getString(3)+"</h3></body></html>",
	    	               "<html><body><h3 style='color:red'>"+rs.getString(4)+"</h3></body></html>",
	    	               "<html><body><h3 style='color:orange'>"+rs.getString(5)+"</h3></body></html>"});
				}
				else {
					JOptionPane.showMessageDialog(null,"Record Not found!");
				}
				}
				catch(Exception ex) {
					ex.getMessage();
				}
			  
				
				
			}
		});
		searc.setBounds(410, 57, 47, 23);
		frame.getContentPane().add(searc);
		
		JButton find = new JButton("@");
		find.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel model=(DefaultTableModel)table.getModel();
					model.setNumRows(0);
					String company_name=company.getSelectedItem().toString();
					String gen=gender.getSelectedItem().toString();
					if((gen.equals(""))&(company_name.equals(""))) {
						String query="select * from students_cse ";
						Statement st=con.createStatement();
						ResultSet rst= st.executeQuery(query);
					    while(rst.next()) {
						
						
							model.addRow(new Object[] {"<html><body><h3 style='color:green'>"+rst.getString(1)+"</h3></body></html>",
								    	               "<html><body><h3 style='color:maroon'>"+rst.getString(2)+"</h3></body></html>",
								    	               "<html><body><h3 style='color:red'>"+rst.getString(3)+"</h3></body></html>",
								    	               "<html><body><h3 style='color:orange'>"+rst.getString(4)+"</h3></body></html>"});
							
						}
						
					}
					else if(gen.equals("")&(company_name.equals("Unemployee")))
					{
						String query="select * from students_cse where Companyname is null";
						
						PreparedStatement pt=con.prepareStatement(query);
						ResultSet rst= pt.executeQuery();
					    while(rst.next()) {
						
						
							model.addRow(new Object[] {"<html><body><h3 style='color:green'>"+rst.getString(1)+"</h3></body></html>",
								    	               "<html><body><h3 style='color:maroon'>"+rst.getString(2)+"</h3></body></html>",
								    	               "<html><body><h3 style='color:red'>"+rst.getString(3)+"</h3></body></html>",
								    	               "<html><body><h3 style='color:orange'>"+rst.getString(4)+"</h3></body></html>"});
							
						}
					}
					else if(!gen.equals("")&(company_name.equals("Unemployee")))
					{
						String query="select * from students_cse where Companyname is null and gender=?";
						
						PreparedStatement pt=con.prepareStatement(query);
						pt.setString(1,gen);
						ResultSet rst= pt.executeQuery();
					    while(rst.next()) {
						
						
							model.addRow(new Object[] {"<html><body><h3 style='color:green'>"+rst.getString(1)+"</h3></body></html>",
								    	               "<html><body><h3 style='color:maroon'>"+rst.getString(2)+"</h3></body></html>",
								    	               "<html><body><h3 style='color:red'>"+rst.getString(3)+"</h3></body></html>",
								    	               "<html><body><h3 style='color:orange'>"+rst.getString(4)+"</h3></body></html>"});
							
						}
					}
					
					else if(gen.equals("")&(!company_name.equals(null)))
					{
						String query="select * from students_cse where Companyname=?";
						
						PreparedStatement pt=con.prepareStatement(query);
						pt.setString(1,company_name);
						ResultSet rst= pt.executeQuery();
					    while(rst.next()) {
						
						
							model.addRow(new Object[] {"<html><body><h3 style='color:green'>"+rst.getString(1)+"</h3></body></html>",
								    	               "<html><body><h3 style='color:maroon'>"+rst.getString(2)+"</h3></body></html>",
								    	               "<html><body><h3 style='color:red'>"+rst.getString(3)+"</h3></body></html>",
								    	               "<html><body><h3 style='color:orange'>"+rst.getString(4)+"</h3></body></html>"});
							
						}
					}
					else if(company_name.equals("")&(!gen.equals(null)))
					{
                        String query="select * from students_cse where gender=?";
						
						PreparedStatement pt=con.prepareStatement(query);
						pt.setString(1,gen);
						ResultSet rst= pt.executeQuery();

					    while(rst.next()) {
						
						
							model.addRow(new Object[] {"<html><body><h3 style='color:green'>"+rst.getString(1)+"</h3></body></html>",
								    	               "<html><body><h3 style='color:maroon'>"+rst.getString(2)+"</h3></body></html>",
								    	               "<html><body><h3 style='color:red'>"+rst.getString(3)+"</h3></body></html>",
								    	               "<html><body><h3 style='color:orange'>"+rst.getString(4)+"</h3></body></html>"});
							
						}
					}
						else
						{
	                        String query="select * from students_cse where Companyname=? and gender=?";
							
							PreparedStatement pt=con.prepareStatement(query);
							pt.setString(1,company_name);
							pt.setString(2,gen);
							ResultSet rst= pt.executeQuery();

						    while(rst.next()) {
							
							
								model.addRow(new Object[] {"<html><body><h3 style='color:green'>"+rst.getString(1)+"</h3></body></html>",
									    	               "<html><body><h3 style='color:maroon'>"+rst.getString(2)+"</h3></body></html>",
									    	               "<html><body><h3 style='color:red'>"+rst.getString(3)+"</h3></body></html>",
									    	               "<html><body><h3 style='color:orange'>"+rst.getString(4)+"</h3></body></html>"});
								
							}
					}
				
				
				}
				
					catch(Exception ex) {
						ex.getMessage();
					}
				
				
				
			}
		});
		find.setBounds(715, 57, 47, 23);
		frame.getContentPane().add(find);
		
		JButton _1 = new JButton("1");
		_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				String query="select * from students_cse limit 20 ";
				Statement st=con.createStatement();
				ResultSet rst= st.executeQuery(query);
				DefaultTableModel model=(DefaultTableModel)table.getModel();
				model.setNumRows(0);
			    while(rst.next()) {
				
					
					model.addRow(new Object[] {"<html><body><h3 style='color:green'>"+rst.getString(1)+"</h3></body></html>",
						    	               "<html><body><h3 style='color:maroon'>"+rst.getString(2)+"</h3></body></html>",
						    	               "<html><body><h3 style='color:red'>"+rst.getString(3)+"</h3></body></html>",
						    	               "<html><body><h3 style='color:orange'>"+rst.getString(4)+"</h3></body></html>"});
					
				}
				}catch(Exception ex) {
					ex.getMessage();
				}
				
				
			}
		});
		_1.setBounds(80, 57, 47, 23);
		frame.getContentPane().add(_1);
	
		
		JButton _2 = new JButton("2");
		_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query="select * from students_cse limit 20,40";
					Statement st=con.createStatement();
					ResultSet rst= st.executeQuery(query);
					DefaultTableModel model=(DefaultTableModel)table.getModel();
					model.setNumRows(0);
				    while(rst.next()) {
					
								model.addRow(new Object[] {"<html><body><h3 style='color:green'>"+rst.getString(1)+"</h3></body></html>",
							    	               "<html><body><h3 style='color:maroon'>"+rst.getString(2)+"</h3></body></html>",
							    	               "<html><body><h3 style='color:red'>"+rst.getString(3)+"</h3></body></html>",
							    	               "<html><body><h3 style='color:orange'>"+rst.getString(4)+"</h3></body></html>"});
						
					}
					}catch(Exception ex) {
						ex.getMessage();
					}
				
			}
		});
		_2.setBounds(128, 57, 47, 23);
		frame.getContentPane().add(_2);
		
		JButton _3 = new JButton("3");
		_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query="select * from students_cse limit 41,60";
					Statement st=con.createStatement();
					ResultSet rst= st.executeQuery(query);
					DefaultTableModel model=(DefaultTableModel)table.getModel();
					model.setNumRows(0);
				    while(rst.next()) {
					
						model.addRow(new Object[] {"<html><body><h3 style='color:green'>"+rst.getString(1)+"</h3></body></html>",
							    	               "<html><body><h3 style='color:maroon'>"+rst.getString(2)+"</h3></body></html>",
							    	               "<html><body><h3 style='color:red'>"+rst.getString(3)+"</h3></body></html>",
							    	               "<html><body><h3 style='color:orange'>"+rst.getString(4)+"</h3></body></html>"});
						
					}
					}catch(Exception ex) {
						ex.getMessage();
					}
				
			}
		});
		_3.setBounds(174, 57, 55, 23);
		frame.getContentPane().add(_3);
		
		JButton update = new JButton("UPDATE");
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel panel=new JPanel();
				JTextField roll=new JTextField(10);
				
				JTextField company=new JTextField(10);
				panel.add(new JLabel("ROLL NUMBER : "));
				panel.add(roll);
				panel.add(new JLabel("COMPAN NAME : "));
				panel.add(company);
				int result=JOptionPane.showConfirmDialog(null,panel,"PLEASE ENTER DETAILS",JOptionPane.OK_CANCEL_OPTION) ;
				
				
				try {
				
				String rol=roll.getText();
				String com=company.getText();
				System.out.print(roll);
				String query="update students_cse set Companyname=? where rollno=?";
				PreparedStatement pt=con.prepareStatement(query);
				pt.setString(1,com);
				pt.setString(2, rol);
				int n= pt.executeUpdate();
				if(n>0) {
					
					JOptionPane.showMessageDialog(null,"Updated successfully!");
			     }
				else {
					
					JOptionPane.showMessageDialog(null,"Record Not found!");
				}
				}
				catch(Exception ex){ex.getMessage();}
			}

		});
		update.setEnabled(true);
		update.setBounds(387, 453, 89, 23);
		frame.getContentPane().add(update);
		
		
		JLabel stat = new JLabel("");
		stat.setVisible(false);
		stat.setBounds(80, 480, 682, 121);
		frame.getContentPane().add(stat);
		
		
		
		JButton statitics = new JButton("Statistics");
		statitics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				stat.setVisible(true);
				try {
				String query1="select count(*) from students_cse where gender='Male'";
				String query2="select count(*) from students_cse where Companyname is not null";
				String query3="select count(*) from students_cse where gender='Male' and Companyname is not null ";
				String query4="select count(*) from students_cse ";
				Statement st=con.createStatement();
				ResultSet rst= st.executeQuery(query4);
				rst.next();
				int total=rst.getInt(1);
			     rst= st.executeQuery(query1);
			    rst.next();
				int total_males=rst.getInt(1);
				int total_females=total-total_males;
			       rst= st.executeQuery(query2);
			    rst.next();
				int total_employees=rst.getInt(1);
				int total_unemployees=total-total_employees;
			       rst= st.executeQuery(query3);
			    rst.next();
				int total_male_employees=rst.getInt(1);
				int total_female_employees=total_employees-total_male_employees;
			
				stat.setText("<html><body><p style='color:green'>       Total Students ="+total+"</p>"
						+ "<p style='color:Red'>Total Males          = "+total_males+"         -------------------                       Total Females = "+total_females+"</p>"
						+ "<p style='color:Red'>Total Empployees     = "+total_employees+"     -------------              Total unemployees = "+total_unemployees+"</p>"
						+ "<p style='color:Red'>Total Males Employee = "+total_male_employees+"-------        Total Female Employees = "+total_female_employees+"</p></body></html>");
			
			}
				catch(Exception ex) {ex.getMessage();}
				
			}
		});

		
		
		
		statitics.setFont(new Font("Times New Roman", Font.BOLD, 14));
		statitics.setForeground(new Color(255, 0, 0));
		statitics.setBounds(90, 452, 211, 23);
		frame.getContentPane().add(statitics);
		
		JLabel lblNewLabel_1 = new JLabel("Roll no");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblNewLabel_1.setBounds(241, 62, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("COMPUTER SCIENCE AND ENGINEERING");
		lblNewLabel_2.setForeground(new Color(148, 0, 211));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("PMingLiU-ExtB", Font.BOLD, 15));
		lblNewLabel_2.setBounds(57, 11, 740, 35);
		frame.getContentPane().add(lblNewLabel_2);
		
		
	
		
		
	
		
			
		
		
	
	}
	
	
	private JTable table;
	private JTextField search;
	private JTextField textField;
}
