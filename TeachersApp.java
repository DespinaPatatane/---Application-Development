package app;

import javax.swing.*;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.EventQueue;



public class TeachersApp {
	private JFrame frame;
	
	public static void launch() {
		EventQueue.invokeLater(() -> {
			try {
				TeachersApp window = new TeachersApp();
				window.frame.setVisible(true);
			}catch (Exception e) {
				e.printStackTrace();
			}
		});
	} 
	
	public TeachersApp() {
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame("Teacher Management");
		frame.setBounds(100, 100, 400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTitle = new JLabel ("Select an Action");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitle.setBounds(120, 20, 200, 30);
		frame.getContentPane().add(lblTitle);
		
		JButton btnAdd = new JButton("Add Teacher");
		btnAdd.setBounds(120, 60, 150, 30);
		frame.getContentPane().add(btnAdd);
		
		JButton btnSearch = new JButton("Search Teacher");
		btnSearch.setBounds(120, 100, 150, 30);
		frame.getContentPane().add(btnSearch);
		
		JButton btnUpdate = new JButton("Update Teacher");
		btnUpdate.setBounds(120, 140, 150 ,30);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete Teacher");
		btnDelete.setBounds(120, 180, 150 ,30);
		frame.getContentPane().add(btnDelete);
		
		JButton btnExit = new JButton("Close");
		btnExit.setBounds(120, 220, 150 ,30);
		frame.getContentPane().add(btnExit);
		
		
		btnAdd.addActionListener(e -> openAddForm());
		btnSearch.addActionListener(e -> openSearchForm());
		btnUpdate.addActionListener(e -> openUpdateForm());
		btnDelete.addActionListener(e -> openDeleteForm());
		btnExit.addActionListener(e -> openExitForm());
		
	}
	
	private void openAddForm() {
		JFrame addFrame = new JFrame("Add Teacher");
		addFrame.setSize(350, 250);
		addFrame.setLayout(null);
		addFrame.setLocationRelativeTo(null);
		
		JLabel lblId = new JLabel("Teacher ID:");
		lblId.setBounds(20, 20, 100, 25);
		JTextField txtId = new JTextField();
		txtId.setBounds(130, 20, 180, 25);
		txtId.setEditable(false);
		txtId.setEnabled(false);
		
		JLabel lblFName = new JLabel("First Name:");
		lblFName.setBounds(20, 60, 100, 25);
		JTextField txtFName = new JTextField();
		txtFName.setBounds(130, 60, 180, 25);
		
		JLabel lblLName = new JLabel("Last Name:");
		lblLName.setBounds(20, 100, 100, 25);
		JTextField txtLName = new JTextField();
		txtLName.setBounds(130, 100, 180, 25);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(50, 150, 100, 30);
		btnSubmit.addActionListener(e -> {
			String firstName = txtFName.getText().trim();
			String lastName = txtLName.getText().trim();
			
		
			if (firstName.isEmpty() || lastName.isEmpty()) {
				JOptionPane.showMessageDialog(addFrame, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
			return;
			}
			
			if (!firstName.matches("[a-zA-Zα-ωΑ-ΩάέήίόύώΆΈΉΊΌΎΏϊϋΐΰ ]+") || !lastName.matches("[a-zA-Zα-ωΑ-ΩάέήίόύώΆΈΉΊΌΎΏϊϋΐΰ ]+")) {
				JOptionPane.showMessageDialog(addFrame,  "First name and Last name should contain only letters.", "Input Error", JOptionPane.ERROR_MESSAGE);
			return;
			}
			
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TeachersDB", "root", "");
				PreparedStatement stmt = conn.prepareStatement(
				 "INSERT INTO teachers (firstName, lastName) VALUES (?, ?)",
				PreparedStatement.RETURN_GENERATED_KEYS
				);
				stmt.setString(1,  firstName);
				stmt.setString(2,  lastName);
				stmt.executeUpdate();
				
				ResultSet generatedKeys = stmt.getGeneratedKeys();
				if (generatedKeys.next()) {
					int newId = generatedKeys.getInt(1);
					txtId.setText(String.valueOf(newId));
					JOptionPane.showMessageDialog(addFrame, "Teacher added successfully with Id: " + newId);
				}
		 
			
			
			conn.close();
		}catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(addFrame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		 }
			
	});
	
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(170, 150, 100, 30);
		btnClose.addActionListener(e -> addFrame.dispose());
		
		addFrame.add(lblId);
		addFrame.add(txtId);
		addFrame.add(lblFName);
		addFrame.add(txtFName);
		addFrame.add(lblLName);
		addFrame.add(txtLName);
		addFrame.add(btnSubmit);
		addFrame.add(btnClose);
		
		addFrame.setVisible(true);
	}

	
	private void openSearchForm() {
	    JFrame searchFrame = new JFrame("Search Teacher");
	    searchFrame.setSize(350, 250);
	    searchFrame.setLayout(null);
	    searchFrame.setLocationRelativeTo(null);

	    JLabel lblLName = new JLabel("Last Name:");
	    lblLName.setBounds(20, 20, 100, 25);
	    JTextField txtLName = new JTextField();
	    txtLName.setBounds(120, 20, 180, 25);

	    JLabel lblResult = new JLabel("<html><b>Result will appear here</b></html>");
	    lblResult.setBounds(20, 60, 300, 60);

	    JButton btnSearch = new JButton("Search");
	    btnSearch.setBounds(30, 130, 90, 30);

	    JButton btnNext = new JButton("Next");
	    btnNext.setBounds(130, 130, 90, 30);
	    btnNext.setEnabled(false);

	    JButton btnPrev = new JButton("Previous");
	    btnPrev.setBounds(230, 130, 90, 30);
	    btnPrev.setEnabled(false);

	    JButton btnClose = new JButton("Close");
	    btnClose.setBounds(120, 170, 90, 30);

	  
	    List<String> results = new ArrayList<>();
	    final int[] currentIndex = {0};

	    btnSearch.addActionListener(e -> {
	        String lastName = txtLName.getText().trim();

	        if (lastName.isEmpty()) {
	            JOptionPane.showMessageDialog(searchFrame, "Please enter a last name.", "Input Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        results.clear();
	        currentIndex[0] = 0;

	        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TeachersDB", "root", "");
	             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM teachers WHERE lastName = ?")) {

	            stmt.setString(1, lastName);
	            ResultSet rs = stmt.executeQuery();

	            while (rs.next()) {
	                String teacherInfo = "<html>ID: " + rs.getInt("id") +
	                        "<br>First Name: " + rs.getString("firstName") +
	                        "<br>Last Name: " + rs.getString("lastName") + "</html>";
	                results.add(teacherInfo);
	            }

	            if (results.isEmpty()) {
	                lblResult.setText("<html><i>No teacher found with this last name.</i></html>");
	                btnNext.setEnabled(false);
	                btnPrev.setEnabled(false);
	            } else {
	                lblResult.setText(results.get(currentIndex[0]));
	                btnNext.setEnabled(results.size() > 1);
	                btnPrev.setEnabled(false);
	            }

	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(searchFrame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    });

	    btnNext.addActionListener(e -> {
	        if (currentIndex[0] < results.size() - 1) {
	            currentIndex[0]++;
	            lblResult.setText(results.get(currentIndex[0]));
	            btnPrev.setEnabled(true);
	        }
	        if (currentIndex[0] == results.size() - 1) {
	            btnNext.setEnabled(false);
	        }
	    });

	    btnPrev.addActionListener(e -> {
	        if (currentIndex[0] > 0) {
	            currentIndex[0]--;
	            lblResult.setText(results.get(currentIndex[0]));
	            btnNext.setEnabled(true);
	        }
	        if (currentIndex[0] == 0) {
	            btnPrev.setEnabled(false);
	        }
	    });

	    btnClose.addActionListener(e -> searchFrame.dispose());

	    searchFrame.add(lblLName);
	    searchFrame.add(txtLName);
	    searchFrame.add(lblResult);
	    searchFrame.add(btnSearch);
	    searchFrame.add(btnNext);
	    searchFrame.add(btnPrev);
	    searchFrame.add(btnClose);

	    searchFrame.setVisible(true);
	}

	
	


	private void openUpdateForm() {
		JFrame searchFrame = new JFrame("Find Teacher to Update");
		searchFrame.setSize(350, 250);
		searchFrame.setLayout(null);
		searchFrame.setLocationRelativeTo(null);
		
		JLabel lblFName = new JLabel("Current FName:");
		lblFName.setBounds(20, 30, 100, 25);
		JTextField txtFName = new JTextField();
		txtFName.setBounds(130, 30, 180, 25);
			
		JLabel lblLName = new JLabel("Current LName");
		lblLName.setBounds(20, 70, 100, 25);
		JTextField txtLName = new JTextField();
		txtLName.setBounds(130, 70, 180, 25);
		
        JButton btnFind = new JButton("Find");
        btnFind.setBounds(50, 120, 100, 30);
		btnFind.addActionListener(e -> {
        	String FName = txtFName.getText().trim();
        	String LName = txtLName.getText().trim();
        	
			if (FName.isEmpty() || LName.isEmpty()) {
				JOptionPane.showMessageDialog(searchFrame, "Please enter both fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TeachersDB", "root", "");
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM teachers WHERE firstName = ? AND lastname = ?");
				stmt.setString(1, FName);
				stmt.setString(2, LName);
				ResultSet rs = stmt.executeQuery();
				
				if (rs.next()) {
					String id = rs.getString("id");
					searchFrame.dispose();
					openUpdateDetailsForm(id, FName, LName);
				}else {
					JOptionPane.showMessageDialog(searchFrame, "No teacher found with those details.", "Not found", JOptionPane.WARNING_MESSAGE);
				}
				conn.close();
		    }catch (SQLException ex) {
				   ex.printStackTrace();
				   JOptionPane.showMessageDialog(searchFrame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			   }
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(170, 120, 100, 30);
		btnCancel.addActionListener(e -> searchFrame.dispose());
			
		searchFrame.add(lblFName);
		searchFrame.add(txtFName);
		searchFrame.add(lblLName);
		searchFrame.add(txtLName);
		searchFrame.add(btnFind);
		searchFrame.add(btnCancel);
		
		searchFrame.setVisible(true);
	}
	
	
	private void openUpdateDetailsForm(String id, String currentFName, String currentLName) {
		JFrame updateFrame = new JFrame("Update Teacher");
		updateFrame.setSize(350, 250);
		updateFrame.setLayout(null);
		updateFrame.setLocationRelativeTo(null);
		
		JLabel lblId = new JLabel("Teacher ID:");
		lblId.setBounds(20, 20, 100, 25);
		JTextField txtId = new JTextField(id);
		txtId.setEditable(false);
		
		JLabel lblFName = new JLabel("New First Name:");
		lblFName.setBounds(20, 60, 100, 25);
		JTextField txtFName = new JTextField(currentFName);
		txtFName.setBounds(130, 60, 180, 25);
		
		JLabel lblLName = new JLabel("New Last Name:");
		lblLName.setBounds(20, 100, 100, 25);
		JTextField txtLName = new JTextField(currentLName);
		txtLName.setBounds(130, 100, 180, 25);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(50, 150, 100, 30);
		btnUpdate.addActionListener(e -> {
			String newFName = txtFName.getText().trim();
			String newLName = txtLName.getText().trim();
			
			if (newFName.isEmpty() || newLName.isEmpty()) {
				JOptionPane.showMessageDialog(updateFrame, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TeachersDB", "root", "");
				PreparedStatement stmt = conn.prepareStatement("UPDATE teachers SET firstName = ?, lastname = ? WHERE id = ?");
				stmt.setString(1, newFName);
				stmt.setString(2, newLName);
				stmt.setString(3, id);
				stmt.executeUpdate();
				
				JOptionPane.showMessageDialog(updateFrame, "Teacher updates successfully!");
			    conn.close();
			}catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(updateFrame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);				
			}
 			
		});
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(170, 150, 100, 30);
		btnClose.addActionListener(e -> updateFrame.dispose());
		
		updateFrame.add(lblId);
		updateFrame.add(txtId);
		updateFrame.add(lblFName);
		updateFrame.add(txtFName);
		updateFrame.add(lblLName);
		updateFrame.add(txtLName);
		updateFrame.add(btnUpdate);
		updateFrame.add(btnClose);
		
		updateFrame.setVisible(true);
		
	}
	
	
	private void openDeleteForm() {
		JFrame deleteFrame = new JFrame("Delete Teacher");
		deleteFrame.setSize(300, 180);
		deleteFrame.setLayout(null);
		deleteFrame.setLocationRelativeTo(null);
		
		JLabel lblId = new JLabel("Teacher ID:");
		lblId.setBounds(20, 30, 100, 25);
		JTextField txtId = new JTextField();
		txtId.setBounds(120, 30, 140, 25);
		
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(40, 80, 90, 30);
		btnDelete.addActionListener(e -> {
			String id = txtId.getText().trim();
			
			if (id.isEmpty()) {
				JOptionPane.showMessageDialog(deleteFrame, "Please enter teacher's ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TeachersDB", "root", "");
				
				PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM teachers WHERE id = ?");
				checkStmt.setString(1,id);
				ResultSet rs = checkStmt.executeQuery();
				
				if (!rs.next()) {
					JOptionPane.showMessageDialog(deleteFrame, "No teacher dound with this ID.", "Not Found", JOptionPane.WARNING_MESSAGE);
					conn.close();
					return;
				}
				
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM teachers WHERE ID = ?");
				stmt.setString(1, id);
				stmt.executeUpdate();
				
				JOptionPane.showMessageDialog(deleteFrame, "Teacher deleted successfully!");
				conn.close();
			}catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(deleteFrame, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		});
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(150, 80, 90, 30);
		btnClose.addActionListener(e -> deleteFrame.dispose());
		
		deleteFrame.add(lblId);
		deleteFrame.add(txtId);
		deleteFrame.add(btnDelete);
		deleteFrame.add(btnClose);
		
		deleteFrame.setVisible(true);
	}
	
	private void openExitForm() {
		int response = JOptionPane.showConfirmDialog(
				frame,
				"Are you sure you want to close the application?",
				"Exit Confirmation",
				JOptionPane.YES_NO_OPTION
			);
		
		if (response == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
}
	
	

		
		



























