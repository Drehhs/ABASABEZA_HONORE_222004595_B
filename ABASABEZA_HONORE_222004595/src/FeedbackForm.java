import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FeedbackForm extends JFrame implements ActionListener {
    JLabel commentLabel,titleLabel;
    JTextField commentTextField;
    JButton submitButton, viewButton, backButton;
    JTextArea feedbackTextArea;
    private JTable dataTable;

    FeedbackForm() {
    	
    	titleLabel = new JLabel("SUBMIT FEEDBACK TO PETPAL HUB");
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    titleLabel.setBounds(40, 10, 320, 30);
    	
        commentLabel = new JLabel("Comment:");
        commentLabel.setBounds(20, 50, 80, 30);
        commentTextField = new JTextField();
        commentTextField.setBounds(100, 50, 300, 50);

        submitButton = new JButton("Submit");
        submitButton.setBounds(100, 120, 80, 30);
        submitButton.addActionListener(this);

        viewButton = new JButton("View Feedback");
        viewButton.setBounds(190, 120, 120, 30);
        viewButton.addActionListener(this);

        backButton = new JButton("Back");
        backButton.setBounds(320, 120, 80, 30);
        backButton.addActionListener(this);

       /* feedbackTextArea = new JTextArea();
        feedbackTextArea.setBounds(20, 150, 380, 200);*/
        
        String[] columnNames = { "FEEDBACK FROM OTHER USERS"};
        displayfeed(columnNames);
        
        
        JScrollPane scrollPane = new JScrollPane(dataTable);
        // Set the bounds for the scroll pane to position it on the frame
        scrollPane.setBounds(20, 150, 380, 200);

        
        add(scrollPane);

        add(titleLabel);
        add(commentLabel);
        add(commentTextField);
        add(submitButton);
        add(viewButton);
        add(backButton);
       

        setTitle("FEEDBACK");
        setSize(450, 400);
        setLayout(null);
        setVisible(true);
        setResizable(false);
		setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String comment = commentTextField.getText();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/abasabeza_honore_petpal", "222004595", "222004595");
                PreparedStatement stmt = con.prepareStatement("INSERT INTO feedback (comment) VALUES (?)");
                stmt.setString(1, comment);
                stmt.executeUpdate();
                con.close();
                JOptionPane.showMessageDialog(this, "Feedback submitted successfully!");
                displayfeed(null);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (e.getSource() == viewButton) {
            displayfeed(null);
            new FeedbackForm().setVisible(true);
            dispose();
        } else if (e.getSource() == backButton) {
            new PetPalConnect();
            dispose();
        }
    }
    
    private void displayfeed(String[] columnNames) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/abasabeza_honore_petpal","222004595", "222004595");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM feedback");

           
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            model.setRowCount(0); // Clear existing data from the table

            while (rs.next()) {
                Object[] row = {
                   
                    rs.getString("comment")
                };
                model.addRow(row);
            }
            
            dataTable = new JTable(model);
         // Add the JTable to a JScrollPane
            JScrollPane scrollPane = new JScrollPane(dataTable);

            // Add the JScrollPane to the JFrame
            add(scrollPane, BorderLayout.CENTER);

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            // Log the error for debugging
            e.printStackTrace();
            // Display a user-friendly error message
            JOptionPane.showMessageDialog(this, "Error fetching data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    public static void main(String[] args) {
        new FeedbackForm();
    }
}
