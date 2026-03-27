package busregistrationsystem.gui;

import busregistrationsystem.data.DataManager;
import busregistrationsystem.model.BusRoute;
import busregistrationsystem.model.Registration;
import busregistrationsystem.util.PDFGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;

public class RegistrationForm extends JDialog {

    private JTextField nameField;
    private JTextField emailField;
    private JTextField universityIdField;
    private JComboBox<String> designationCombo;
    private JComboBox<String> routeCombo;
    private JButton submitButton;
    private JButton cancelButton;
    private JLabel messageLabel;

    private String userEmail;

    public RegistrationForm(Frame parent, String userEmail) {
        super(parent, "Bus Registration Form", true);
        setIconImage(new ImageIcon("FastLogo.png").getImage());
        this.userEmail = userEmail;

        setSize(500, 400);
        setLocationRelativeTo(parent);
        setResizable(false);

        initializeUI();
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Name Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("\uE2AF Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        // Email Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("\uE135 Email:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        emailField = new JTextField(20);
        emailField.setText(userEmail);
        emailField.setEditable(false);
        formPanel.add(emailField, gbc);

        // University ID Field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("\uE12A University ID:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        universityIdField = new JTextField(20);
        formPanel.add(universityIdField, gbc);

        // Designation Combo
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("\uE125 Designation:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        String[] designations = {"Student", "Faculty"};
        designationCombo = new JComboBox<>(designations);
        formPanel.add(designationCombo, gbc);

        // Route Combo
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("\uD83D\uDE8C Bus Route:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        routeCombo = new JComboBox<>();
        populateRouteCombo();
        formPanel.add(routeCombo, gbc);

        // Message Label
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED);
        formPanel.add(messageLabel, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        submitButton = new RoundButton("Submit", 30);
        submitButton.setBackground(new Color(39, 174, 96));
        submitButton.setForeground(Color.WHITE);

        cancelButton = new RoundButton("Cancel", 30);
        cancelButton.setBackground(new Color(172, 30, 34));
        cancelButton.setForeground(Color.WHITE);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateForm()) {
                    submitRegistration();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        // Add panels to main panel
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel titleLabel = new JLabel("Bus Registration Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        headerPanel.add(titleLabel, BorderLayout.CENTER);

        return headerPanel;
    }

    private void populateRouteCombo() {
        Collection<BusRoute> routes = DataManager.getInstance().getAllRoutes();

        for (BusRoute route : routes) {
            routeCombo.addItem(route.getRouteNumber() + ": " + route.getRouteName());
        }
    }

    private boolean validateForm() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String universityId = universityIdField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || universityId.isEmpty()) {
            messageLabel.setText("All fields are required");
            return false;
        }

        if (routeCombo.getSelectedIndex() == -1) {
            messageLabel.setText("Please select a bus route");
            return false;
        }

        return true;
    }

    private void submitRegistration() {
        // Get the form data
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String universityId = universityIdField.getText().trim();
        String designation = (String) designationCombo.getSelectedItem();

        // Check if email is already registered
        DataManager dataManager = DataManager.getInstance();
        List<Registration> existingRegistrations = dataManager.getRegistrationsByUser(email);
        if (!existingRegistrations.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "This email is already registered in the system.",
                    "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Extract route number from the selected item (format: "1: Route Name")
        String routeString = (String) routeCombo.getSelectedItem();
        int routeNumber = Integer.parseInt(routeString.split(":")[0].trim());

        // Create registration object
        Registration registration = new Registration(email, universityId, designation, routeNumber, name);

        // Save to data manager
        dataManager.addRegistration(registration);

        // Generate voucher
        PDFGenerator.generateVoucher(registration);

        // Show success message and close
        JOptionPane.showMessageDialog(this,
                "Registration successful! Your registration ID is: " + registration.getRegistrationId()
                + "\nStatus: " + registration.getStatus()
                + "\nPlease check the voucher for payment instructions.",
                "Registration Complete", JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }
}
