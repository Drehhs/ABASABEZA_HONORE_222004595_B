import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PetsReg extends JFrame implements ActionListener {
    JLabel titleLabel,nameLabel, breedLabel, priceLabel, descriptionLabel, placeLabel, availabilityLabel;
    JTextField nameTextField, breedTextField, priceTextField, descriptionTextField, placeTextField, availabilityTextField;
    JButton addButton, feedbackButton, requestsButton, vetVisitsButton, viewButton,updateButton,deleteButton;
    JTextArea petsTextArea;
    private JTable dataTable;

    PetsReg() {
    	
    	titleLabel = new JLabel("VIEW AND MODIFY PETS");
	    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    titleLabel.setBounds(40, 10, 320, 20);
	    
	    String[] columnNames = {"ID", "Name","Breed", "Cost", "Description", "Place", "Availability"};
        displayPets(columnNames);
        
        
        JScrollPane scrollPane = new JScrollPane(dataTable);
        // Set the bounds for the scroll pane to position it on the frame
        scrollPane.setBounds(350, 20, 400, 300);

        
        add(scrollPane);
	    
	    
        nameLabel = new JLabel("Pet Name:");
        nameLabel.setBounds(20, 30, 80, 30);
        nameTextField = new JTextField();
        nameTextField.setBounds(100, 30, 200, 30);

        breedLabel = new JLabel("Breed:");
        breedLabel.setBounds(20, 70, 80, 30);
        breedTextField = new JTextField();
        breedTextField.setBounds(100, 70, 200, 30);

        priceLabel = new JLabel("Price:");
        priceLabel.setBounds(20, 110, 80, 30);
        priceTextField = new JTextField();
        priceTextField.setBounds(100, 110, 200, 30);

        descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(20, 150, 80, 30);
        descriptionTextField = new JTextField();
        descriptionTextField.setBounds(100, 150, 200, 30);

        placeLabel = new JLabel("Place:");
        placeLabel.setBounds(20, 190, 80, 30);
        placeTextField = new JTextField();
        placeTextField.setBounds(100, 190, 200, 30);

        availabilityLabel = new JLabel("Availability:");
        availabilityLabel.setBounds(20, 230, 80, 30);
        availabilityTextField = new JTextField();
        availabilityTextField.setBounds(100, 230, 200, 30);

        addButton = new JButton("Add");
        addButton.setBounds(100, 270, 80, 30);
        addButton.addActionListener(this);

        feedbackButton = new JButton("Feedback");
        feedbackButton.setBounds(20, 320, 100, 30);
        feedbackButton.addActionListener(this);

        requestsButton = new JButton("Requests");
        requestsButton.setBounds(140, 320, 100, 30);
        requestsButton.addActionListener(this);

        vetVisitsButton = new JButton("Vet Visits");
        vetVisitsButton.setBounds(260, 320, 100, 30);
        vetVisitsButton.addActionListener(this);

        viewButton = new JButton("View Pets");
        viewButton.setBounds(380, 320, 100, 30);
        viewButton.addActionListener(this);

        updateButton = new JButton("update");
        updateButton.setBounds(500, 320, 100, 30);
        updateButton.addActionListener(this);

        deleteButton = new JButton("delete");
        deleteButton.setBounds(620, 320, 100, 30);
        deleteButton.addActionListener(this);

        /*petsTextArea = new JTextArea();
        petsTextArea.setBounds(350, 20, 400, 300);*/

        add(titleLabel);
        add(nameLabel);
        add(nameTextField);
        add(breedLabel);
        add(breedTextField);
        add(priceLabel);
        add(priceTextField);
        add(descriptionLabel);
        add(descriptionTextField);
        add(placeLabel);
        add(placeTextField);
        add(availabilityLabel);
        add(availabilityTextField);
        add(addButton);
        add(feedbackButton);
        add(requestsButton);
        add(vetVisitsButton);
        add(viewButton);
        add(updateButton);
        add(deleteButton);
       

        setSize(800, 500);
        setTitle("ADMIN PANEL");
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
		setLocationRelativeTo(null);
        
        displayPets(columnNames);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addPetToDatabase();
        } else if (e.getSource() == feedbackButton) {
            new ViewFeed();
            this.dispose();
            JOptionPane.showMessageDialog(this, "processing Feedbacks from users.");
        } else if (e.getSource() == requestsButton) {
            
        	new viewRequest();
        	this.dispose();
            JOptionPane.showMessageDialog(this, "processing Requests from users.");
        } else if (e.getSource() == vetVisitsButton) {
            new VetVisitForm();
            this.dispose();
            JOptionPane.showMessageDialog(this, "add Vet Visits  .");
        } else if (e.getSource() == viewButton) {
            
            new ViewPets().setVisible(true);
            dispose();
        } else if (e.getSource() == updateButton) {
            updatePet();
        } else if (e.getSource()==deleteButton) {
            deletePet();
        }
    }

    private void addPetToDatabase() {
        String name = nameTextField.getText();
        String breed = breedTextField.getText();
        double price = Double.parseDouble(priceTextField.getText());
        String description = descriptionTextField.getText();
        String place = placeTextField.getText();
        String availability = availabilityTextField.getText();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/abasabeza_honore_petpal", "222004595", "222004595");
            PreparedStatement stmt = con.prepareStatement("INSERT INTO pets(Name,Breed,price,description,place,availability) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, breed);
            stmt.setDouble(3, price);
            stmt.setString(4, description);
            stmt.setString(5, place);
            stmt.setString(6, availability);
            stmt.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(this, "Pet added successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding pet to the database");
        }
    }

    private void displayPets(String[] columnNames) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/abasabeza_honore_petpal","222004595", "222004595");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pets");

           
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            model.setRowCount(0); // Clear existing data from the table

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("petid"),
                    rs.getString("Name"),
                   // rs.getInt("userid"),
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

    private void updatePet() {
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
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/abasabeza_honore_petpal", "222004595", "222004595");
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

    private void deletePet() {
        String name = nameTextField.getText();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/abasabeza_honore_petpal", "222004595", "222004595");
            PreparedStatement stmt = con.prepareStatement("DELETE FROM pets WHERE name = ?");
            stmt.setString(1, name);
            stmt.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(this, "Pet deleted successfully!");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }



    public static void main(String[] args) {
        new PetsReg();
    }
}
