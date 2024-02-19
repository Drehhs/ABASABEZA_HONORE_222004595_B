import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class LoginForm extends JFrame implements ActionListener {
	 private JLabel usernameLabel, passwordLabel, titleLabel;
	    private JTextField usernameField;
	    private JPasswordField passwordField;
	    private JButton loginButton, registerButton;

	    public LoginForm() {
	    	
	        setTitle("Login");
	        setSize(550, 350);
			setBackground(Color.gray);
			setLayout(null);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setResizable(false);
			setLocationRelativeTo(null);
	       

	        titleLabel = new JLabel("Login to PETPAL");
	        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
	        titleLabel.setBounds(40, 20, 320, 30);
	        add(titleLabel);
	        
	       
	        usernameLabel = new JLabel("Username:");
	        usernameLabel.setBounds(40, 70, 80, 25);
	        add(usernameLabel);

	        passwordLabel = new JLabel("Password:");
	        passwordLabel.setBounds(40, 110, 80, 25);
	        add(passwordLabel);


	        usernameField = new JTextField();
	        usernameField.setBounds(130, 70, 200, 25);
	        add(usernameField);

	        passwordField = new JPasswordField();
	        passwordField.setBounds(130, 110, 200, 25);
	        add(passwordField);


	        loginButton = new JButton("Login");
	        loginButton.setBounds(130, 150, 90, 30);
	        loginButton.addActionListener(this);
	        add(loginButton);
	        
	        registerButton = new JButton("Register");
	        registerButton.setBounds(240, 150, 90, 30);
	        registerButton.addActionListener(this);
	        add(registerButton);
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
	        if (e.getSource() == loginButton) {
	           
        String name = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/abasabeza_honore_petpal", "222004595", "222004595");
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT * FROM users WHERE username = ? AND password = ?"
            );
            stmt.setString(1, name);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	
            	 String role = rs.getString("username");
                 if (role.equalsIgnoreCase("admin")) {
                     PetsReg admin = new PetsReg();
                     admin.setVisible(true);
                     this.dispose();
                 } else {
                     PetPalConnect other = new  PetPalConnect();
                     other.setVisible(true);
                     this.dispose();
                 }
                JOptionPane.showMessageDialog(
                        this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE
                );
            }
            con.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this, "Login failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE
            );
        }
	        }
	        if (e.getSource() == registerButton) {
	        	new RegistrationForm().setvisible(true);
	        	this.dispose();
	        }
    }

    public static void main(String[] args) {
    	LoginForm login = new LoginForm();
        login.setVisible(true);
    }
}
