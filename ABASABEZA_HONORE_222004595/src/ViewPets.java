import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewPets extends JFrame implements ActionListener {
    JLabel titleLabel;
    
    JButton feedbackButton, requestsButton, vetVisitsButton, viewButton,upButton,delButton;
    
    private JTable dataTable;

    ViewPets() {
    	
    	titleLabel = new JLabel("    VIEW PETS AVAILABLE AT OUR PLACE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(50, 10, 620, 30);
        
        
        
        String[] columnNames = {"ID", "Name","Breed", "Cost", "Description", "Place", "Availability"};
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

        viewButton = new JButton("GO BACK");
        viewButton.setBounds(610, 170, 150, 30);
        viewButton.addActionListener(this);
        
        upButton = new JButton("UPDATE");
        upButton.setBounds(610, 210, 150, 30);
        upButton.addActionListener(this);
        
        delButton = new JButton("DELETE");
        delButton.setBounds(610, 250, 150, 30);
        delButton.addActionListener(this);

      
        

        add(titleLabel);
        add(feedbackButton);
        add(requestsButton);
        add(vetVisitsButton);
        add(viewButton);
        add(upButton);
        add(delButton);
        

        setTitle("VIEW PETS");
        setSize(1000, 600);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
		setLocationRelativeTo(null);
		
		
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == feedbackButton) {
            // Code to display feedback form goes here
        	new ViewFeed();
            this.dispose();
        	
            JOptionPane.showMessageDialog(this, "processing Feedbacks from users.");
        } else if (e.getSource() == requestsButton) {
            // Code to display requests form goes here
        	 
        	new viewRequest();
        	this.dispose();
            JOptionPane.showMessageDialog(this, "processing Requests from users.");
        } else if (e.getSource() == vetVisitsButton) {
            new VetVisitForm();
            this.dispose();
            JOptionPane.showMessageDialog(this, "add Vet Visits  .");
        } else if (e.getSource() == viewButton) {
            new PetsReg();
            dispose();
            
        }else if (e.getSource() == upButton) {
            
        	updateSelectedRow();
        	
            
        }else if (e.getSource() == delButton) {
            int selectedRow = dataTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this row?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Retrieve the ID of the selected row
                int id = (int) dataTable.getValueAt(selectedRow, 0);
                // Delete the row from the database
                deleteRowFromDatabase(id);
            }
        }
        	
           // JOptionPane.showMessageDialog(this, "PetPalConnect.");
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
    
    private void updateSelectedRow() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to update.");
            return;
        }

        // Retrieve the data of the selected row
        int id = (int) dataTable.getValueAt(selectedRow, 0);
        String Name = (String) dataTable.getValueAt(selectedRow, 1);
        
        String Breed = (String) dataTable.getValueAt(selectedRow, 2);
        double cost = (double) dataTable.getValueAt(selectedRow, 3);
        String description = (String) dataTable.getValueAt(selectedRow, 4);
        String place = (String) dataTable.getValueAt(selectedRow, 5);
        String availability = (String) dataTable.getValueAt(selectedRow, 6);
        

        // Display a dialog box or form to allow the user to update the data
        //JTextField idField = new JTextField(String.valueOf(id));
        JTextField NameField = new JTextField(Name);
        JTextField BreedField = new JTextField(Breed);
        JTextField costField = new JTextField(String.valueOf(cost));
        JTextField descriptionField = new JTextField(description);
        JTextField placeField = new JTextField(place);
        JTextField availabilityField = new JTextField(availability);
        

        JPanel panel = new JPanel(new GridLayout(0, 1));
        
        panel.add(new JLabel("Name:"));
        panel.add(NameField);
        panel.add(new JLabel("Breed:"));
        panel.add(BreedField);
        panel.add(new JLabel("Cost:"));
        panel.add(costField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("place:"));
        panel.add(placeField);
        panel.add(new JLabel("Availability:"));
        panel.add(availabilityField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Update Row", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Update the data in the database
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/abasabeza_honore_petpal","222004595", "222004595");
                PreparedStatement stmt = conn.prepareStatement("UPDATE pets SET Name=?, Breed=?, price=?, description=?, place=?, availability=? WHERE petid=?");
                stmt.setString(1, NameField.getText());
                stmt.setString(2, BreedField.getText());
                stmt.setDouble(3, Double.parseDouble(costField.getText()));
                stmt.setString(4, descriptionField.getText());
                stmt.setString(5, placeField.getText());
                stmt.setString(6, availabilityField.getText());
                stmt.setInt(7, id);
               int rowsAffected = stmt.executeUpdate();
                conn.close();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Row updated successfully.");
                    displayPets(null); // Update the table to reflect the changes
                    new ViewPets().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update row.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
    
    private void deleteRowFromDatabase(int id) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/abasabeza_honore_petpal", "222004595", "222004595");
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM pets WHERE petid = ?");
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            conn.close();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Row deleted successfully.");
                displayPets(null);
                new ViewPets().setVisible(true);// Refresh the table
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete row.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting row: " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        new ViewPets();
    }
}
