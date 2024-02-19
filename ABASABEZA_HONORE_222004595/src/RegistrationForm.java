import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RegistrationForm extends JFrame implements ActionListener {

    private JTextField firstNameField, lastNameField, usernameField, phoneField, emailField, addressField;
    private JPasswordField passwordField;
    
    private JButton registerButton,loginButton;


    public RegistrationForm() {
        setTitle("Register");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); 
        setLocationRelativeTo(null);
        setResizable(false);
       

        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Increased font size
        titleLabel.setBounds(350, 20, 150, 30); // Adjusted position
        add(titleLabel);

        String[] labels = {"First Name:", "Last Name:", "Username:", "Phone:", "Email:", "Password:", "Address:"};
        int y = 70;
        for (String label : labels) {
            JLabel jLabel = new JLabel(label);
            jLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Increased font size
            jLabel.setBounds(40, y, 150, 25); // Adjusted position and size
            add(jLabel);
            y += 50; // Increased vertical spacing
        }

        firstNameField = new JTextField();
        firstNameField.setFont(new Font("Arial", Font.PLAIN, 18)); // Increased font size
        firstNameField.setBounds(200, 70, 300, 30); // Increased size
        add(firstNameField);

        // Add other text fields similarly
        
        lastNameField = new JTextField();
        lastNameField.setFont(new Font("Arial", Font.PLAIN, 18)); // Increased font size
        lastNameField.setBounds(200, 120, 300, 30); // Increased size
        add(lastNameField);
        
        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18)); // Increased font size
        usernameField.setBounds(200, 170, 300, 30); // Increased size
        add(usernameField);
        
        phoneField = new JTextField();
        phoneField.setFont(new Font("Arial", Font.PLAIN, 18)); // Increased font size
        phoneField.setBounds(200, 220, 300, 30); // Increased size
        add(phoneField);
        
        emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 18)); // Increased font size
        emailField.setBounds(200, 270, 300, 30); // Increased size
        add(emailField);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18)); // Increased font size
        passwordField.setBounds(200, 320, 300, 30); // Increased size
        add(passwordField);

        addressField = new JTextField();
        addressField.setFont(new Font("Arial", Font.PLAIN, 18)); // Increased font size
        addressField.setBounds(200, 370, 300, 30); // Increased size
        add(addressField);

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 18)); // Increased font size
        registerButton.setBounds(200, 420, 150, 40); // Increased size
        registerButton.addActionListener(this);
        add(registerButton);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18)); // Increased font size
        loginButton.setBounds(370, 420, 150, 40); // Increased size
        loginButton.addActionListener(this);
        add(loginButton);
        
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
    	
    
    	if(e.getActionCommand().equals("Register")) {
    		
    		String firstname = firstNameField.getText();
    		String lastname = lastNameField.getText();
    	String name = usernameField.getText();
    	String phone = phoneField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String address = addressField.getText();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/abasabeza_honore_petpal", "222004595", "222004595");
            PreparedStatement stmt = con.prepareStatement(
                    "INSERT INTO users (firstname,lastname,username,phone, email, password,address) VALUES (?, ?, ?,?,?,?,?)"
            );
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, name);
            stmt.setString(4, phone);
            stmt.setString(5, email);
            stmt.setString(6, password);
            stmt.setString(7, address);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE );
            con.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
            		this, "Registration failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE
            );
        }
        
        }
    	if(e.getActionCommand().equals("Login")) {
    		new LoginForm().setVisible(true);
    		dispose();
    	}
    }

    public static void main(String[] args) {
        new RegistrationForm();
    }

	public void setvisible(boolean b) {
		// TODO Auto-generated method stub
		
	}

	
}
