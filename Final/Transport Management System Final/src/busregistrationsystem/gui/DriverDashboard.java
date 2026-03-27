package busregistrationsystem.gui;

import busregistrationsystem.data.DataManager;
import busregistrationsystem.model.BusRoute;
import busregistrationsystem.model.Driver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DriverDashboard extends JFrame {
    private String driverUsername;
    private Driver driver;
    private BusRoute route;

    private JTextField updateField;
    private DefaultTableModel updatesTableModel;

    public DriverDashboard(String driverUsername) {
        this.driverUsername = driverUsername;
        this.driver = DataManager.getInstance().getDriver(driverUsername);

        if (driver == null) {
            // For demo purposes, create a temporary driver
            driver = new Driver(driverUsername, "driver123", "John Driver", 1);
            DataManager.getInstance().addDriver(driver);
        }

        // Get the route for this driver
        this.route = DataManager.getInstance().getRoute(driver.getRouteNumber());

        setTitle("Driver Dashboard - Fast Transport Registration System");
        setIconImage(new ImageIcon("FastLogo.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        initializeUI();
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create top panel with title and logout button
        JPanel topPanel = createTopPanel();

        // Create center panel with driver and route info
        JPanel centerPanel = createCenterPanel();

        // Create bottom panel with updates section
        JPanel bottomPanel = createBottomPanel();

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(41, 128, 185));
        topPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("Fast Transport Registration System - Driver Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);

        JLabel userLabel = new JLabel("Welcome, " + driver.getName());
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton logoutButton = new RoundButton("Logout",30);
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

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Driver info panel
        JPanel driverInfoPanel = new JPanel(new GridBagLayout());
        driverInfoPanel.setBorder(BorderFactory.createTitledBorder("Driver Information"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        driverInfoPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        driverInfoPanel.add(new JLabel(driver.getName()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        driverInfoPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        driverInfoPanel.add(new JLabel(driver.getUsername()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        driverInfoPanel.add(new JLabel("Route Number:"), gbc);
        gbc.gridx = 1;
        driverInfoPanel.add(new JLabel(String.valueOf(driver.getRouteNumber())), gbc);

        // Route info panel
        JPanel routeInfoPanel = new JPanel(new GridBagLayout());
        routeInfoPanel.setBorder(BorderFactory.createTitledBorder("Route Information"));

        if (route != null) {
            gbc.gridx = 0;
            gbc.gridy = 0;
            routeInfoPanel.add(new JLabel("Route Name:"), gbc);
            gbc.gridx = 1;
            routeInfoPanel.add(new JLabel(route.getRouteName()), gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            routeInfoPanel.add(new JLabel("From:"), gbc);
            gbc.gridx = 1;
            routeInfoPanel.add(new JLabel(route.getStartPoint()), gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            routeInfoPanel.add(new JLabel("To:"), gbc);
            gbc.gridx = 1;
            routeInfoPanel.add(new JLabel(route.getEndPoint()), gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            routeInfoPanel.add(new JLabel("Departure Time:"), gbc);
            gbc.gridx = 1;
            routeInfoPanel.add(new JLabel(route.getDepartureTime()), gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            routeInfoPanel.add(new JLabel("Arrival Time:"), gbc);
            gbc.gridx = 1;
            routeInfoPanel.add(new JLabel(route.getArrivalTime()), gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            routeInfoPanel.add(new JLabel("Registered Users:"), gbc);
            gbc.gridx = 1;
            routeInfoPanel.add(new JLabel(String.valueOf(route.getRegisteredUsers())), gbc);

            gbc.gridx = 0;
            gbc.gridy = 6;
            routeInfoPanel.add(new JLabel("Capacity:"), gbc);
            gbc.gridx = 1;
            routeInfoPanel.add(new JLabel(String.valueOf(route.getCapacity())), gbc);
        } else {
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            routeInfoPanel.add(new JLabel("Route information not available"), gbc);
        }

        centerPanel.add(driverInfoPanel);
        centerPanel.add(routeInfoPanel);

        return centerPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Updates"));
        bottomPanel.setBorder(new EmptyBorder(0, 20, 20, 20));

        // Previous updates panel
        JPanel previousUpdatesPanel = new JPanel(new BorderLayout());
        previousUpdatesPanel.setBorder(BorderFactory.createTitledBorder("Previous Updates"));

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

        previousUpdatesPanel.add(scrollPane, BorderLayout.CENTER);

        // New update panel
        JPanel newUpdatePanel = new JPanel(new BorderLayout());
        newUpdatePanel.setBorder(BorderFactory.createTitledBorder("Post New Update"));

        updateField = new JTextField();
        JButton postButton = new RoundButton("Post Update",20);
        postButton.setBackground(new Color(39, 174, 96));
        postButton.setForeground(Color.WHITE);
        postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postUpdate();
            }
        });

        newUpdatePanel.add(updateField, BorderLayout.CENTER);
        newUpdatePanel.add(postButton, BorderLayout.EAST);

        // Load existing updates
        loadUpdates();

        bottomPanel.add(previousUpdatesPanel, BorderLayout.CENTER);
        bottomPanel.add(newUpdatePanel, BorderLayout.SOUTH);

        return bottomPanel;
    }

    private void loadUpdates() {
        List<String> updates = driver.getUpdates();

        for (String update : updates) {
            updatesTableModel.addRow(new Object[]{"Today", update});
        }
    }

    private void postUpdate() {
        String update = updateField.getText().trim();

        if (!update.isEmpty()) {
            // Add to data manager
            DataManager.getInstance().addDriverUpdate(driver.getUsername(), update);

            // Add to table
            updatesTableModel.addRow(new Object[]{"Today", update});

            // Clear field
            updateField.setText("");

            JOptionPane.showMessageDialog(this,
                    "Update posted successfully! Users will see this update.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Please enter an update message.",
                    "Error", JOptionPane.ERROR_MESSAGE);
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