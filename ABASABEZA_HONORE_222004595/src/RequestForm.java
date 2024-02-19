import javax.swing.*;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RequestForm extends JFrame implements ActionListener {
    JLabel titleLabel,petNameLabel, descriptionLabel;
    JTextField petNameTextField, descriptionTextField;
    JButton submitButton, viewButton, backButton;
    JTextArea requestTextArea;

    RequestForm() {
    	
    	titleLabel = new JLabel("    REQUEST ANY DESIRED PET");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(40, -1, 320, 30);
    	
        petNameLabel = new JLabel("Pet Name:");
        petNameLabel.setBounds(20, 30, 80, 30);
        petNameTextField = new JTextField();
        petNameTextField.setBounds(100, 30, 200, 30);

        descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(20, 70, 80, 30);
        descriptionTextField = new JTextField();
        descriptionTextField.setBounds(100, 70, 200, 30);

        submitButton = new JButton("Submit");
        submitButton.setBounds(100, 110, 80, 30);
        submitButton.addActionListener(this);

        viewButton = new JButton("View Requests");
        viewButton.setBounds(190, 110, 120, 30);
        viewButton.addActionListener(this);

        backButton = new JButton("Back");
        backButton.setBounds(320, 110, 80, 30);
        backButton.addActionListener(this);

        requestTextArea = new JTextArea();
        requestTextArea.setBounds(20, 160, 380, 200);

        add(titleLabel);
        add(petNameLabel);
        add(petNameTextField);
        add(descriptionLabel);
        add(descriptionTextField);
        add(submitButton);
        add(viewButton);
        add(backButton);
        add(requestTextArea);

        setTitle("PET REQUEST");
        setSize(600, 600);
        
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
		setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String petName = petNameTextField.getText();
            String description = descriptionTextField.getText();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/abasabeza_honore_petpal", "222004595", "222004595");
                PreparedStatement stmt = con.prepareStatement("INSERT INTO request (petname, description) VALUES (?, ?)");
                stmt.setString(1, petName);
                stmt.setString(2, description);
                stmt.executeUpdate();
                con.close();
                JOptionPane.showMessageDialog(this, "Request submitted successfully!");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (e.getSource() == viewButton) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/abasabeza_honore_petpal", "222004595", "222004595");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM request");
                requestTextArea.setText("");
                while (rs.next()) {
                    requestTextArea.append(rs.getString(2) + " " + rs.getString(3) + "\n");
                }
                con.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (e.getSource() == backButton) {
            new PetPalConnect();
            dispose();
        }
    }

    public static void main(String[] args) {
        new RequestForm();
    }
}
