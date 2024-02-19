import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PetPalConnect extends JFrame implements ActionListener {
    JLabel titleLabel;
    
    JButton feedbackButton, requestsButton, vetVisitsButton, viewButton,upButton,delButton;
    
    private JTable dataTable;

    PetPalConnect() {
    	
    	titleLabel = new JLabel("    VIEW PETS AVAILABLE AT OUR PLACE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(50, 10, 620, 30);
        
        
        
        String[] columnNames = {"ID", "Name",  "Breed", "Cost", "Description", "Place", "Availability"};
        displayPets(columnNames);
        
        
        JScrollPane scrollPane = new JScrollPane(dataTable);
        // Set the bounds for the scroll pane to position it on the frame
        scrollPane.setBounds(6, 50, 600, 500);

        
        add(scrollPane);
        
       

        feedbackButton = new JButton("Feedback Form");
        feedbackButton.setBounds(610, 50, 150, 30);
        feedbackButton.addActionListener(this);

        requestsButton = new JButton("Requests Form");
        requestsButton.setBounds(610, 90, 150, 30);
        requestsButton.addActionListener(this);

        vetVisitsButton = new JButton("Veternary History");
        vetVisitsButton.setBounds(610, 130, 150, 30);
        vetVisitsButton.addActionListener(this);

        viewButton = new JButton("View Pets");
        viewButton.setBounds(610, 170, 150, 30);
        viewButton.addActionListener(this);
        
        

      
        

        add(titleLabel);
        add(feedbackButton);
        add(requestsButton);
        add(vetVisitsButton);
        add(viewButton);
        
        setTitle("VIEW PETS");
        setSize(800, 600);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
		setLocationRelativeTo(null);
		
		
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == feedbackButton) {
            // Code to display feedback form goes here
        	new FeedbackForm();
        	dispose();
        	
            JOptionPane.showMessageDialog(this, "Submit a Feedback in the form.");
        } else if (e.getSource() == requestsButton) {
            // Code to display requests form goes here
        	new RequestForm();
        	dispose();
        	
            JOptionPane.showMessageDialog(this, "Submit your Requests in the form.");
        } else if (e.getSource() == vetVisitsButton) {
            // Code for vet visits button goes here
        	
        	new vetvisits();
        	dispose();
        	
            JOptionPane.showMessageDialog(this, "Vet Visits.");
        } else if (e.getSource() == viewButton) {
            new PetPalConnect();
        } 
    }

   

    private void displayPets(String[] columnNames) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/abasabeza_honore_petpal", "222004595", "222004595");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pets");

           
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            model.setRowCount(0); // Clear existing data from the table

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("petid"),
                    rs.getString("Name"),
                   
                    rs.getString("Breed"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getString("place"),
                    rs.getString("availability")
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
        new PetPalConnect();
    }
}
