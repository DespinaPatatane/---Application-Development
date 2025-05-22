package app;

import javax.swing.*;


public class WelcomeFrame {
  private JFrame frame;
  
  public WelcomeFrame () {
	  initialize();
  }
  
  private void initialize () {
	  frame = new JFrame ("Welcome");
	  frame.setSize(400, 200);
	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  frame.setLocationRelativeTo(null);
	  frame.setLayout(null);
	  
	  JLabel lblWelcome = new JLabel ("Welcome to the teachers managment application");
	  lblWelcome.setBounds(20, 40, 360, 30);
	  lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
	  frame.add(lblWelcome);
	  
	  JButton btnEnter = new JButton("Entry");
	  btnEnter.setBounds(140, 80, 120, 30);
	  
	  JButton btnClose = new JButton("Close");
		btnClose.setBounds(140, 120, 120, 30);
		frame.getContentPane().add(btnClose);
		
	  btnEnter.addActionListener(e -> {
		  frame.dispose();
		  TeachersApp.launch();
	  });
	  
	  btnClose.addActionListener(e -> {
		  openCloseForm();
	  });
	  
	  frame.add(btnEnter);
	  frame.setVisible(true);
  }
	  
	  private void openCloseForm() {
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
