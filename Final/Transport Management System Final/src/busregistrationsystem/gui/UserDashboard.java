package busregistrationsystem.gui;
import busregistrationsystem.data.DataManager;
import busregistrationsystem.model.BusRoute;
import busregistrationsystem.model.Registration;
import busregistrationsystem.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserDashboard extends JFrame {
    private String userEmail;
    private User user;
    private Registration activeRegistration;

    private JPanel contentPanel;
    private JPanel pendingPanel;
    private JPanel dashboardPanel;

    private DefaultTableModel updatesTableModel;

    public UserDashboard(String userEmail) {
        this.userEmail = userEmail;
        this.user = DataManager.getInstance().getUser(userEmail);

        if (user == null) {
            // For demo purposes, create a temporary user
            user = new User("Test User", userEmail, "password");
            DataManager.getInstance().addUser(user);
        }

        // Check if user has any registrations
        List<Registration> userRegistrations = DataManager.getInstance().getRegistrationsByUser(userEmail);
        if (!userRegistrations.isEmpty()) {
            // Get the most recent registration
            activeRegistration = userRegistrations.get(userRegistrations.size() - 1);
        }

        setTitle("User Dashboard - Fast Transport Registration System");
        setIconImage(new ImageIcon("FastLogo.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        initializeUI();
    }

    private void initializeUI() {
        // Create the main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create top panel with title and logout button
        JPanel topPanel = createTopPanel();

        // Create content panel - will display either pending or dashboard based on verification status
        contentPanel = new JPanel(new CardLayout());

        // Create panels
        pendingPanel = createPendingPanel();
        dashboardPanel = createDashboardPanel();

        // Add panels to content panel
        contentPanel.add(pendingPanel, "pending");
        contentPanel.add(dashboardPanel, "dashboard");

        // Show the appropriate panel based on verification status
        if (activeRegistration != null && !activeRegistration.isPaymentVerified()) {
            ((CardLayout) contentPanel.getLayout()).show(contentPanel, "pending");
        } else {
            ((CardLayout) contentPanel.getLayout()).show(contentPanel, "dashboard");
        }

        // Add components to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(41, 128, 185));
        topPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("Fast Transport Registration System - User Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);

        JLabel userLabel = new JLabel("Welcome, " + user.getName());
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton logoutButton = new  RoundButton("Logout",30);
        logoutButton.setBackground(new Color(39, 174, 96));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        userPanel.add(userLabel);
        userPanel.add(logoutButton);

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(userPanel, BorderLayout.EAST);

        return topPanel;
    }

    private JPanel createPendingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel pendingLabel = new JLabel("Payment Verification Pending");
        pendingLabel.setFont(new Font("Arial", Font.BOLD, 22));
        pendingLabel.setForeground(new Color(192, 57, 43));
        pendingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel infoLabel = new JLabel("Your registration is pending payment verification");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel detailsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        if (activeRegistration != null) {
            addDetailRow(detailsPanel, gbc, "Registration ID:", String.valueOf(activeRegistration.getRegistrationId()), 0);
            addDetailRow(detailsPanel, gbc, "Date:", activeRegistration.getFormattedDate(), 1);
            addDetailRow(detailsPanel, gbc, "Route Number:", String.valueOf(activeRegistration.getRouteNumber()), 2);
            addDetailRow(detailsPanel, gbc, "Status:", activeRegistration.getStatus(), 3);
            addDetailRow(detailsPanel, gbc, "Amount:", "PKR " + activeRegistration.getPaymentAmount(), 4);
        }

        JPanel instructionsPanel = new JPanel();
        instructionsPanel.setLayout(new BoxLayout(instructionsPanel, BoxLayout.Y_AXIS));
        instructionsPanel.setBorder(BorderFactory.createTitledBorder("Payment Instructions"));

        JTextArea instructionsText = new JTextArea();
        instructionsText.setText(
                "1. Please pay the amount to the university account\n" +
                        "2. Reference: BUS-" + (activeRegistration != null ? activeRegistration.getRegistrationId() : "") + "\n" +
                        "3. Email payment proof to transport@university.edu\n" +
                        "4. Your registration will be approved after verification");
        instructionsText.setEditable(false);
        instructionsText.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionsText.setBackground(UIManager.getColor("Panel.background"));
        instructionsText.setLineWrap(true);
        instructionsText.setWrapStyleWord(true);

        instructionsPanel.add(instructionsText);

        // Add some vertical spacing with empty panels
        JPanel spacer1 = new JPanel();
        spacer1.setPreferredSize(new Dimension(1, 20));
        JPanel spacer2 = new JPanel();
        spacer2.setPreferredSize(new Dimension(1, 10));
        JPanel spacer3 = new JPanel();
        spacer3.setPreferredSize(new Dimension(1, 20));

        contentPanel.add(pendingLabel);
        contentPanel.add(spacer1);
        contentPanel.add(infoLabel);
        contentPanel.add(spacer2);
        contentPanel.add(detailsPanel);
        contentPanel.add(spacer3);
        contentPanel.add(instructionsPanel);

        JPanel centeringPanel = new JPanel(new GridBagLayout());
        centeringPanel.add(contentPanel);

        panel.add(centeringPanel, BorderLayout.CENTER);

        return panel;
    }

    private void addDetailRow(JPanel panel, GridBagConstraints gbc, String label, String value, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(valueLabel, gbc);
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create content with registration options or bus details
        if (activeRegistration != null && activeRegistration.isPaymentVerified()) {
            // User has a verified registration - show bus details
            panel.add(createVerifiedUserPanel(), BorderLayout.CENTER);
        } else {
            // User doesn't have a registration or it's not verified - show registration options
            panel.add(createRegistrationOptionsPanel(), BorderLayout.CENTER);
        }

        return panel;
    }

    private JPanel createVerifiedUserPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create user info panel
        JPanel userInfoPanel = new JPanel(new GridBagLayout());
        userInfoPanel.setBorder(BorderFactory.createTitledBorder("User Information"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Add user details
        gbc.gridx = 0;
        gbc.gridy = 0;
        userInfoPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        userInfoPanel.add(new JLabel(user.getName()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        userInfoPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        userInfoPanel.add(new JLabel(user.getEmail()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        userInfoPanel.add(new JLabel("University ID:"), gbc);
        gbc.gridx = 1;
        userInfoPanel.add(new JLabel(activeRegistration.getUniversityId()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        userInfoPanel.add(new JLabel("Designation:"), gbc);
        gbc.gridx = 1;
        userInfoPanel.add(new JLabel(activeRegistration.getDesignation()), gbc);

        // Create bus info panel
        JPanel busInfoPanel = new JPanel(new GridBagLayout());
        busInfoPanel.setBorder(BorderFactory.createTitledBorder("Bus Information"));

        // Get route information
        BusRoute route = DataManager.getInstance().getRoute(activeRegistration.getRouteNumber());

        if (route != null) {
            gbc.gridx = 0;
            gbc.gridy = 0;
            busInfoPanel.add(new JLabel("Route Number:"), gbc);
            gbc.gridx = 1;
            busInfoPanel.add(new JLabel(String.valueOf(route.getRouteNumber())), gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            busInfoPanel.add(new JLabel("Route Name:"), gbc);
            gbc.gridx = 1;
            busInfoPanel.add(new JLabel(route.getRouteName()), gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            busInfoPanel.add(new JLabel("From:"), gbc);
            gbc.gridx = 1;
            busInfoPanel.add(new JLabel(route.getStartPoint()), gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            busInfoPanel.add(new JLabel("To:"), gbc);
            gbc.gridx = 1;
            busInfoPanel.add(new JLabel(route.getEndPoint()), gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            busInfoPanel.add(new JLabel("Departure Time:"), gbc);
            gbc.gridx = 1;
            busInfoPanel.add(new JLabel(route.getDepartureTime()), gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            busInfoPanel.add(new JLabel("Arrival Time:"), gbc);
            gbc.gridx = 1;
            busInfoPanel.add(new JLabel(route.getArrivalTime()), gbc);
        }

        // Create updates panel
        JPanel updatesPanel = new JPanel(new BorderLayout());
        updatesPanel.setBorder(BorderFactory.createTitledBorder("Driver Updates"));

        String[] columnNames = {"Date", "Update"};
        updatesTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable updatesTable = new JTable(updatesTableModel);
        JScrollPane scrollPane = new JScrollPane(updatesTable);
        scrollPane.setPreferredSize(new Dimension(400, 150));

        updatesPanel.add(scrollPane, BorderLayout.CENTER);

        // Load updates
        loadUpdates();

        // Add all panels to main panel
        JPanel topPanels = new JPanel(new GridLayout(1, 2, 20, 0));
        topPanels.add(userInfoPanel);
        topPanels.add(busInfoPanel);

        panel.add(topPanels, BorderLayout.NORTH);
        panel.add(updatesPanel, BorderLayout.CENTER);

        return panel;
    }

    private void loadUpdates() {
        if (activeRegistration != null) {
            List<String> updates = DataManager.getInstance()
                    .getUpdatesForRoute(activeRegistration.getRouteNumber());

            for (String update : updates) {
                updatesTableModel.addRow(new Object[]{"Today", update});
            }

            // Add some sample updates if none exist
            if (updates.isEmpty()) {
                updatesTableModel.addRow(new Object[]{"Today", "Bus service running as scheduled."});
                updatesTableModel.addRow(new Object[]{"Yesterday", "Bus will depart 10 minutes early due to traffic conditions."});
                updatesTableModel.addRow(new Object[]{"Last Week", "New stop added at the Science Building."});
            }
        }
    }

    private JPanel createRegistrationOptionsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome to the Fast Transport Registration System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel infoLabel = new JLabel("Please select one of the options below:");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setLayout(new GridLayout(1, 2, 20, 0));

        RoundButton registerButton = new RoundButton("Register for Bus",50);
        registerButton.setBackground(new Color(0, 137, 188));
        registerButton.setHoverColor(new Color(221, 9, 248));
        registerButton.setForeground(Color.WHITE); // White text
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setPreferredSize(new Dimension(150, 50)); // Adjust size

        RoundButton routesButton = new RoundButton("View Routes",50);
        routesButton.setBackground(new Color(0, 137, 188));
        routesButton.setHoverColor(new Color(221, 9, 248));
        routesButton.setForeground(Color.WHITE); // White text
        routesButton.setFont(new Font("Arial", Font.BOLD, 16));
        routesButton.setPreferredSize(new Dimension(150, 50)); // Adjust size


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistrationForm();
            }
        });

        routesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRouteDisplay();
            }
        });

        buttonPanel.add(registerButton);
        buttonPanel.add(routesButton);

        // Add spacing between components
        JPanel spacer1 = new JPanel();
        spacer1.setPreferredSize(new Dimension(1, 20));
        JPanel spacer2 = new JPanel();
        spacer2.setPreferredSize(new Dimension(1, 40));

        contentPanel.add(welcomeLabel);
        contentPanel.add(spacer1);
        contentPanel.add(infoLabel);
        contentPanel.add(spacer2);
        contentPanel.add(buttonPanel);

        panel.add(contentPanel);

        return panel;
    }

    private void openRegistrationForm() {
        RegistrationForm registrationForm = new RegistrationForm(this, userEmail);
        registrationForm.setVisible(true);

        // After registration form is closed, refresh dashboard
        refreshDashboard();
    }

    private void openRouteDisplay() {
        RouteDisplay routeDisplay = new RouteDisplay(this);
        routeDisplay.setVisible(true);
    }

    private void refreshDashboard() {
        // Reload registrations
        List<Registration> userRegistrations = DataManager.getInstance().getRegistrationsByUser(userEmail);
        if (!userRegistrations.isEmpty()) {
            activeRegistration = userRegistrations.get(userRegistrations.size() - 1);

            // Show the appropriate panel based on verification status
            if (!activeRegistration.isPaymentVerified()) {
                ((CardLayout) contentPanel.getLayout()).show(contentPanel, "pending");
            } else {
                // Recreate the dashboard panel with updated information
                dashboardPanel = createDashboardPanel();
                contentPanel.remove(1);  // Remove old dashboard panel
                contentPanel.add(dashboardPanel, "dashboard");
                ((CardLayout) contentPanel.getLayout()).show(contentPanel, "dashboard");
            }
        }
    }

    private void logout() {
        SwingUtilities.invokeLater(() -> {
            StartPage startPage = new StartPage();
            startPage.setVisible(true);
            dispose();
        });
    }
}