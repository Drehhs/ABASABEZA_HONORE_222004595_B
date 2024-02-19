import javax.swing.*;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class viewRequest extends JFrame implements ActionListener {
    JLabel titleLabel,nameLabel, breedLabel, priceLabel, descriptionLabel, placeLabel, availabilityLabel;
    JTextField nameTextField, breedTextField, priceTextField, descriptionTextField, placeTextField, availabilityTextField;
    JButton addButton, feedbackButton, requestsButton, vetVisitsButton, viewButton,updateButton,deleteButton;
    JTextArea petsTextArea;

    viewRequest() {
    	
    	titleLabel = new JLabel("    VIEW REQUESTS OF PETS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(50, 10, 620, 30);
        
        
        petsTextArea = new JTextArea();
        petsTextArea.setBounds(6, 50, 600, 500);
       

        feedbackButton = new JButton("View Feedback");
        feedbackButton.setBounds(610, 50, 150, 30);
        feedbackButton.addActionListener(this);


        vetVisitsButton = new JButton("Vet Data Form ");
        vetVisitsButton.setBounds(610, 90, 150, 30);
        vetVisitsButton.addActionListener(this);

        viewButton = new JButton("View Pets");
        viewButton.setBounds(610, 130, 150, 30);
        viewButton.addActionListener(this);

      
        

        add(titleLabel);
        add(feedbackButton);
        add(requestsButton);
        add(vetVisitsButton);
        add(viewButton);
        add(petsTextArea);

        setTitle("VIEW REQUESTED PETS");
        setSize(800, 600);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
		setLocationRelativeTo(null);
		
		displayPets();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == feedbackButton) {
            // Code to display feedback form goes here
        	new ViewFeed();
        	dispose();
        	
            JOptionPane.showMessageDialog(this, "View Feedbacks.");
        }else if (e.getSource() == vetVisitsButton) {
            // Code for vet visits button goes here
        	
        	new VetVisitForm();
        	dispose();
        	
            JOptionPane.showMessageDialog(this, "Vet Visits.");
        } else if (e.getSource() == viewButton) {
            displayPets();
        } 
    }

   

    private void displayPets() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/abasabeza_honore_petpal", "222004595", "222004595");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM request");

            StringBuilder petsData = new StringBuilder();
            while (rs.next()) {
            	Integer reqid = rs.getInt("reqid");
                String petname = rs.getString("petname");
               
                String description = rs.getString("description");
                
                petsData
                .append("RequestId: ")
                .append(reqid)
                .append("Requested Pet name: ")
                .append(petname)
                .append(", Description: ")
                .append(description)
                .append(", Price: ")
                .append("\n");
            }

            petsTextArea.setText(petsData.toString());

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving pets from the database");
        }
    }

   



    public static void main(String[] args) {
        new viewRequest();
    }
}

