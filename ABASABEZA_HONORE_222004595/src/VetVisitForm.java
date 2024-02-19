import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class VetVisitForm extends JFrame implements ActionListener {
    JLabel petNameLabel, vaccinationLabel, dateLabel;
    JTextField petNameTextField, vaccinationTextField, dateTextField;
    JButton submitButton, viewButton, backButton;
    JTextArea vetVisitTextArea;

    VetVisitForm() {
        petNameLabel = new JLabel("Pet Name:");
        petNameLabel.setBounds(20, 20, 80, 30);
        petNameTextField = new JTextField();
        petNameTextField.setBounds(100, 20, 200, 30);

        vaccinationLabel = new JLabel("Vaccination:");
        vaccinationLabel.setBounds(20, 60, 80, 30);
        vaccinationTextField = new JTextField();
        vaccinationTextField.setBounds(100, 60, 200, 30);

        dateLabel = new JLabel("Date:");
        dateLabel.setBounds(20, 100, 80, 30);
        dateTextField = new JTextField();
        dateTextField.setBounds(100, 100, 200, 30);

        submitButton = new JButton("Submit");
        submitButton.setBounds(100, 150, 80, 30);
        submitButton.addActionListener(this);

        viewButton = new JButton("View Vet Visits");
        viewButton.setBounds(190, 150, 120, 30);
        viewButton.addActionListener(this);

        backButton = new JButton("Back");
        backButton.setBounds(320, 150, 80, 30);
        backButton.addActionListener(this);

        vetVisitTextArea = new JTextArea();
        vetVisitTextArea.setBounds(20, 200, 380, 200);

        add(petNameLabel);
        add(petNameTextField);
        add(vaccinationLabel);
        add(vaccinationTextField);
        add(dateLabel);
        add(dateTextField);
        add(submitButton);
        add(viewButton);
        add(backButton);
        add(vetVisitTextArea);

        setSize(800, 600);
        setTitle("ADD VETERNARY INFOS");
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
		setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
        	String petid = petNameTextField.getText();
            String vaccination = vaccinationTextField.getText();
            String date = dateTextField.getText();

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/abasabeza_honore_petpal", "222004595", "222004595");
                PreparedStatement stmt = con.prepareStatement("INSERT INTO vetvisits (petid, vaccination, date) VALUES (?, ?, ?)");
                stmt.setString(1, petid);
                stmt.setString(2, vaccination);
                stmt.setString(3, date);
                stmt.executeUpdate();
                con.close();
                JOptionPane.showMessageDialog(this, "Vet visit added successfully!");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (e.getSource() == viewButton) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/abasabeza_honore_petpal", "222004595", "222004595");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM vetvisits");
                vetVisitTextArea.setText("");
                while (rs.next()) {
                    vetVisitTextArea.append(rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + "\n");
                }
                con.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (e.getSource() == backButton) {
            new PetsReg();
            dispose();
        }
    }

    public static void main(String[] args) {
        new VetVisitForm();
    }
}
