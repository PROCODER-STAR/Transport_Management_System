package busregistrationsystem.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPage extends JFrame {

    private JPanel container;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private Timer slideTimer;

    public StartPage() {
        setTitle("FAST Transport Registration System");
        setIconImage(new ImageIcon("FastLogo.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);


        initializeUI();
        startAnimation();
    }

    private void initializeUI() {
        container = new JPanel(null);
        container.setBounds(0, 0, 900, 600);

        // Left Panel - Logo and System Name
        leftPanel = new JPanel(null);
        leftPanel.setBackground(new Color(0, 137, 188));
        leftPanel.setBounds(0, 0, 900, 600);

        JLabel fastLogoLabel = new JLabel();
        try {
            ImageIcon fastLogoIcon = new ImageIcon("FastLogo.png");
            Image scaledLogo = fastLogoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            fastLogoLabel.setIcon(new ImageIcon(scaledLogo));
        } catch (Exception e) {
            System.out.println("FAST logo image not found.");
        }
        fastLogoLabel.setBounds(350, 40, 200, 200);
        leftPanel.add(fastLogoLabel);
        JLabel systemNameLabel = new JLabel("<html><div style='text-align: center;'>FAST Transport<br>Registration System</div></html>");
        systemNameLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        systemNameLabel.setForeground(Color.WHITE);
        systemNameLabel.setBounds(300, 250, 500, 150);
        leftPanel.add(systemNameLabel);

        // Right Panel - Login Options
        rightPanel = new JPanel(null);
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBounds(900, 0, 400, 600);

        JLabel welcomeLabel = new JLabel("Welcome! Please select your role:");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBounds(50, 50, 300, 30);
        rightPanel.add(welcomeLabel);

        RoundButton adminButton = new RoundButton("Administrator ", 50); // 50px corner radius
        adminButton.setBackground(new Color(0, 137, 188));
        adminButton.setHoverColor(new Color(221, 9, 248));
        adminButton.setForeground(Color.WHITE); // White text
        adminButton.setFont(new Font("Arial", Font.BOLD, 16));
        adminButton.setPreferredSize(new Dimension(150, 50)); // Adjust size

        RoundButton driverButton = new RoundButton("Driver", 50); // 50px corner radius
        driverButton.setBackground(new Color(0, 137, 188));
        driverButton.setHoverColor(new Color(221, 9, 248));
        driverButton.setForeground(Color.WHITE); // White text
        driverButton.setFont(new Font("Arial", Font.BOLD, 16));
        driverButton.setPreferredSize(new Dimension(150, 50)); // Adjust size

        RoundButton userButton = new RoundButton("User", 50); // 50px corner radius
        userButton.setBackground(new Color(0,137,188));
        userButton.setHoverColor(new Color(221, 9, 248));
        userButton.setForeground(Color.WHITE); // White text
        userButton.setFont(new Font("Arial", Font.BOLD, 16));
        userButton.setPreferredSize(new Dimension(150, 50)); // Adjust size

        adminButton.setBounds(50, 120, 300, 50);
        driverButton.setBounds(50, 190, 300, 50);
        userButton.setBounds(50, 260, 300, 50);

        adminButton.addActionListener(e -> {
            LoginDialog adminLogin = new LoginDialog(StartPage.this, "Administrator Login", "admin");
            adminLogin.setVisible(true);
        });

        driverButton.addActionListener(e -> {
            LoginDialog driverLogin = new LoginDialog(StartPage.this, "Driver Login", "driver");
            driverLogin.setVisible(true);
        });

        userButton.addActionListener(e -> {
            UserLoginOptionsDialog userOptions = new UserLoginOptionsDialog(StartPage.this);
            userOptions.setVisible(true);
        });

        rightPanel.add(adminButton);
        rightPanel.add(driverButton);
        rightPanel.add(userButton);

        container.add(leftPanel);
        container.add(rightPanel);
        add(container);
    }

    private void startAnimation() {
        // Get references to the logo and title labels from leftPanel
        Component[] leftComponents = leftPanel.getComponents();
        JLabel logoLabel = (JLabel)leftComponents[0];
        JLabel systemNameLabel = (JLabel)leftComponents[1];

        new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ((Timer) evt.getSource()).stop();
                Timer slideTimer = new Timer(5, null);
                slideTimer.addActionListener(new ActionListener() {
                    int leftPanelX = 0;  // Starts at left edge
                    int rightPanelX = 900; // Starts off-screen to the right
                    final int leftPanelFinalWidth = 500; // Final width for left panel
                    final int rightPanelWidth = 400; // Width of right panel

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        boolean animationOngoing = false;

                        // Animate left panel (moving right and shrinking)
                        if (leftPanel.getWidth() > leftPanelFinalWidth) {
                            int newWidth = leftPanel.getWidth() - 20;
                            if (newWidth < leftPanelFinalWidth) {
                                newWidth = leftPanelFinalWidth;
                            }
                            leftPanel.setBounds(leftPanelX, 0, newWidth, 600);

                            // Keep logo and title centered during animation
                            int logoX = (newWidth - 200) / 2;
                            int titleX = (newWidth - 300) / 2;
                            logoLabel.setBounds(logoX, 40, 200, 200);
                            systemNameLabel.setBounds(titleX, 250, 500, 150);

                            animationOngoing = true;
                        }

                        // Animate right panel (sliding in from right)
                        if (rightPanelX > (900 - rightPanelWidth)) {
                            rightPanelX -= 20;
                            if (rightPanelX < (900 - rightPanelWidth)) {
                                rightPanelX = 900 - rightPanelWidth;
                            }
                            rightPanel.setBounds(rightPanelX, 0, rightPanelWidth, 600);
                            animationOngoing = true;
                        }

                        // Stop timer when animation completes
                        if (!animationOngoing) {
                            leftPanel.setBounds(0, 0, leftPanelFinalWidth, 600);
                            rightPanel.setBounds(900 - rightPanelWidth, 0, rightPanelWidth, 600);
                            slideTimer.stop();
                        }
                    }
                });
                slideTimer.start();
            }
        }).start();
    }

    // Inner class for Login Dialog
    class LoginDialog extends JDialog {
        private JTextField usernameField;
        private JPasswordField passwordField;
        private JLabel messageLabel;
        private String userType;

        public LoginDialog(Frame parent, String title, String userType) {
            super(parent, title, true);
            this.userType = userType;
            initializeDialogUI();
        }

        private void initializeDialogUI() {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints cs = new GridBagConstraints();
            cs.fill = GridBagConstraints.HORIZONTAL;

            // Username components
            JLabel usernameLabel = new JLabel("\uE2AF Username: ");
            cs.gridx = 0;
            cs.gridy = 0;
            cs.gridwidth = 1;
            cs.insets = new Insets(10, 10, 5, 5);
            panel.add(usernameLabel, cs);

            usernameField = new JTextField(20);
            cs.gridx = 1;
            cs.gridy = 0;
            cs.gridwidth = 2;
            cs.insets = new Insets(10, 0, 5, 10);
            panel.add(usernameField, cs);

            // Password components
            JLabel passwordLabel = new JLabel("\uE1F6 Password: ");
            cs.gridx = 0;
            cs.gridy = 1;
            cs.gridwidth = 1;
            cs.insets = new Insets(5, 10, 5, 5);
            panel.add(passwordLabel, cs);

            passwordField = new JPasswordField(20);
            cs.gridx = 1;
            cs.gridy = 1;
            cs.gridwidth = 2;
            cs.insets = new Insets(5, 0, 5, 10);
            panel.add(passwordField, cs);

            // Message label
            messageLabel = new JLabel(" ");
            messageLabel.setForeground(Color.RED);
            cs.gridx = 0;
            cs.gridy = 2;
            cs.gridwidth = 3;
            cs.insets = new Insets(5, 10, 5, 10);
            panel.add(messageLabel, cs);

            // Buttons
            JPanel buttonPanel = new JPanel();
            JButton loginButton = new RoundButton("Login",30);
            loginButton.setBackground(new Color(39, 174, 96));
            loginButton.setForeground(Color.WHITE);

            JButton cancelButton = new RoundButton("Cancel",30);
            cancelButton.setBackground(new Color(172, 30, 34));
            cancelButton.setForeground(Color.WHITE);

            loginButton.addActionListener(e -> handleLogin());
            cancelButton.addActionListener(e -> dispose());

            buttonPanel.add(loginButton);
            buttonPanel.add(cancelButton);

            cs.gridx = 0;
            cs.gridy = 3;
            cs.gridwidth = 3;
            cs.insets = new Insets(10, 10, 10, 10);
            panel.add(buttonPanel, cs);

            getContentPane().add(panel);
            pack();
            setLocationRelativeTo(getParent());
            setResizable(false);
        }

        private void handleLogin() {
            if (authenticate(usernameField.getText(), new String(passwordField.getPassword()))) {
                dispose();
            } else {
                messageLabel.setText("Invalid username or password");
            }
        }

        private boolean authenticate(String username, String password) {
            switch (userType) {
                case "admin":
                    if (username.equals("admin") && password.equals("admin123")) {
                        openAdminDashboard();
                        return true;
                    }
                    break;
                case "driver":
                    if (username.equals("driver1") && password.equals("driver123")) {
                        openDriverDashboard(username);
                        return true;
                    }
                    break;
            }
            return false;
        }

        private void openAdminDashboard() {
            SwingUtilities.invokeLater(() -> {
                AdminDashboard adminDashboard = new AdminDashboard();
                adminDashboard.setVisible(true);
                StartPage.this.dispose();
            });
        }

        private void openDriverDashboard(String username) {
            SwingUtilities.invokeLater(() -> {
                DriverDashboard driverDashboard = new DriverDashboard(username);
                driverDashboard.setVisible(true);
                StartPage.this.dispose();
            });
        }
    }

    // Inner class for User Login Options Dialog
    class UserLoginOptionsDialog extends JDialog {
        public UserLoginOptionsDialog(Frame parent) {
            super(parent, "User Options", true);
            initializeDialogUI();
        }

        private void initializeDialogUI() {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 20, 10, 20);

            JLabel titleLabel = new JLabel("Select an Option:");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            panel.add(titleLabel, gbc);

            RoundButton signInButton = new RoundButton("Sign In", 30); // 30px corner radius
            signInButton.setBackground(new Color(0, 137, 188));
            signInButton.setHoverColor(new Color(39, 174, 96));
            signInButton.setForeground(Color.WHITE); // White text
            signInButton.setFont(new Font("Arial", Font.BOLD, 16));

            RoundButton signUpButton = new RoundButton("Sign Up", 30); // 30px corner radius
            signUpButton.setBackground(new Color(0, 137, 188));
            signUpButton.setHoverColor(new Color(39, 174, 96));
            signUpButton.setForeground(Color.WHITE); // White text
            signUpButton.setFont(new Font("Arial", Font.BOLD, 16));

            signInButton.addActionListener(e -> {
                dispose();
                new UserLoginDialog(StartPage.this).setVisible(true);
            });

            signUpButton.addActionListener(e -> {
                dispose();
                new UserSignupDialog(StartPage.this).setVisible(true);
            });

            gbc.insets = new Insets(15, 30, 15, 30);
            panel.add(signInButton, gbc);
            panel.add(signUpButton, gbc);

            getContentPane().add(panel);
            setSize(300, 200);
            setLocationRelativeTo(getParent());
            setResizable(false);
        }
    }

    // Inner class for User Login Dialog
    class UserLoginDialog extends JDialog {
        private JTextField emailField;
        private JPasswordField passwordField;
        private JLabel messageLabel;

        public UserLoginDialog(Frame parent) {
            super(parent, "User Login", true);
            initializeDialogUI();
        }

        private void initializeDialogUI() {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints cs = new GridBagConstraints();
            cs.fill = GridBagConstraints.HORIZONTAL;

            // Email components
            JLabel emailLabel = new JLabel("\uE135 Email: ");
            cs.gridx = 0;
            cs.gridy = 0;
            cs.gridwidth = 1;
            cs.insets = new Insets(10, 10, 5, 5);
            panel.add(emailLabel, cs);

            emailField = new JTextField(20);
            cs.gridx = 1;
            cs.gridy = 0;
            cs.gridwidth = 2;
            cs.insets = new Insets(10, 0, 5, 10);
            panel.add(emailField, cs);

            // Password components
            JLabel passwordLabel = new JLabel("\uE1F6 Password: ");
            cs.gridx = 0;
            cs.gridy = 1;
            cs.gridwidth = 1;
            cs.insets = new Insets(5, 10, 5, 5);
            panel.add(passwordLabel, cs);

            passwordField = new JPasswordField(20);
            cs.gridx = 1;
            cs.gridy = 1;
            cs.gridwidth = 2;
            cs.insets = new Insets(5, 0, 5, 10);
            panel.add(passwordField, cs);

            // Message label
            messageLabel = new JLabel(" ");
            messageLabel.setForeground(Color.RED);
            cs.gridx = 0;
            cs.gridy = 2;
            cs.gridwidth = 3;
            cs.insets = new Insets(5, 10, 5, 10);
            panel.add(messageLabel, cs);

            // Buttons
            JPanel buttonPanel = new JPanel();
            JButton loginButton = new RoundButton("Login",30);
            loginButton.setBackground(new Color(39, 174, 96));
            loginButton.setForeground(Color.WHITE);

            JButton cancelButton = new RoundButton("Cancel",30);
            cancelButton.setBackground(new Color(172, 30, 34));
            cancelButton.setForeground(Color.WHITE);

            loginButton.addActionListener(e -> {
                if (authenticateUser(emailField.getText(), new String(passwordField.getPassword()))) {
                    openUserDashboard(emailField.getText());
                    dispose();
                } else {
                    messageLabel.setText("Invalid email or password");
                }
            });

            cancelButton.addActionListener(e -> dispose());

            buttonPanel.add(loginButton);
            buttonPanel.add(cancelButton);

            cs.gridx = 0;
            cs.gridy = 3;
            cs.gridwidth = 3;
            cs.insets = new Insets(10, 10, 10, 10);
            panel.add(buttonPanel, cs);

            getContentPane().add(panel);
            pack();
            setLocationRelativeTo(getParent());
            setResizable(false);
        }

        private boolean authenticateUser(String email, String password) {
            return busregistrationsystem.data.DataManager.getInstance().authenticateUser(email, password);
        }

        private void openUserDashboard(String email) {
            SwingUtilities.invokeLater(() -> {
                UserDashboard userDashboard = new UserDashboard(email);
                userDashboard.setVisible(true);
                StartPage.this.dispose();
            });
        }
    }

    // Inner class for User Signup Dialog
    class UserSignupDialog extends JDialog {
        private JTextField nameField;
        private JTextField emailField;
        private JPasswordField passwordField;
        private JPasswordField confirmPasswordField;
        private JLabel messageLabel;

        public UserSignupDialog(Frame parent) {
            super(parent, "User Sign Up", true);
            initializeDialogUI();
        }

        private void initializeDialogUI() {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints cs = new GridBagConstraints();
            cs.fill = GridBagConstraints.HORIZONTAL;

            // Name components
            JLabel nameLabel = new JLabel("\uE2AF Name: ");
            cs.gridx = 0;
            cs.gridy = 0;
            cs.gridwidth = 1;
            cs.insets = new Insets(10, 10, 5, 5);
            panel.add(nameLabel, cs);

            nameField = new JTextField(20);
            cs.gridx = 1;
            cs.gridy = 0;
            cs.gridwidth = 2;
            cs.insets = new Insets(10, 0, 5, 10);
            panel.add(nameField, cs);

            // Email components
            JLabel emailLabel = new JLabel("\uE135 Email: ");
            cs.gridx = 0;
            cs.gridy = 1;
            cs.gridwidth = 1;
            cs.insets = new Insets(5, 10, 5, 5);
            panel.add(emailLabel, cs);

            emailField = new JTextField(20);
            cs.gridx = 1;
            cs.gridy = 1;
            cs.gridwidth = 2;
            cs.insets = new Insets(5, 0, 5, 10);
            panel.add(emailField, cs);

            // Password components
            JLabel passwordLabel = new JLabel("\uE1F6 Password: ");
            cs.gridx = 0;
            cs.gridy = 2;
            cs.gridwidth = 1;
            cs.insets = new Insets(5, 10, 5, 5);
            panel.add(passwordLabel, cs);

            passwordField = new JPasswordField(20);
            cs.gridx = 1;
            cs.gridy = 2;
            cs.gridwidth = 2;
            cs.insets = new Insets(5, 0, 5, 10);
            panel.add(passwordField, cs);

            // Confirm Password components
            JLabel confirmPasswordLabel = new JLabel("\uE1F6 Confirm Password: ");
            cs.gridx = 0;
            cs.gridy = 3;
            cs.gridwidth = 1;
            cs.insets = new Insets(5, 10, 5, 5);
            panel.add(confirmPasswordLabel, cs);

            confirmPasswordField = new JPasswordField(20);
            cs.gridx = 1;
            cs.gridy = 3;
            cs.gridwidth = 2;
            cs.insets = new Insets(5, 0, 5, 10);
            panel.add(confirmPasswordField, cs);

            // Message label
            messageLabel = new JLabel(" ");
            messageLabel.setForeground(Color.RED);
            cs.gridx = 0;
            cs.gridy = 4;
            cs.gridwidth = 3;
            cs.insets = new Insets(5, 10, 5, 10);
            panel.add(messageLabel, cs);

            // Buttons
            JPanel buttonPanel = new JPanel();
            JButton signupButton = new RoundButton("Sign Up",30);
            signupButton.setBackground(new Color(39, 174, 96));
            signupButton.setForeground(Color.WHITE);

            JButton cancelButton = new RoundButton("Cancel",30);
            cancelButton.setBackground(new Color(172, 30, 34));
            cancelButton.setForeground(Color.WHITE);

            signupButton.addActionListener(e -> {
                if (validateAndSignup()) {
                    dispose();
                }
            });

            cancelButton.addActionListener(e -> dispose());

            buttonPanel.add(signupButton);
            buttonPanel.add(cancelButton);

            cs.gridx = 0;
            cs.gridy = 5;
            cs.gridwidth = 3;
            cs.insets = new Insets(10, 10, 10, 10);
            panel.add(buttonPanel, cs);

            getContentPane().add(panel);
            pack();
            setLocationRelativeTo(getParent());
            setResizable(false);
        }

        private boolean validateAndSignup() {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                messageLabel.setText("All fields are required");
                return false;
            }

            if (!password.equals(confirmPassword)) {
                messageLabel.setText("Passwords do not match");
                return false;
            }

            if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                messageLabel.setText("Invalid email format");
                return false;
            }

            if (password.length() < 6) {
                messageLabel.setText("Password must be at least 6 characters");
                return false;
            }

            // Create and save new user
            busregistrationsystem.model.User newUser = new busregistrationsystem.model.User(name, email, password);
            busregistrationsystem.data.DataManager.getInstance().addUser(newUser);

            JOptionPane.showMessageDialog(this,
                    "Account created successfully. Please login.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            dispose();
            new UserLoginDialog(StartPage.this).setVisible(true);
            return true;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartPage startPage = new StartPage();
            startPage.setVisible(true);
        });
    }
}