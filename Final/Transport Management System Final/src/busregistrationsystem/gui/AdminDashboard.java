package busregistrationsystem.gui;

import busregistrationsystem.data.DataManager;
import busregistrationsystem.model.BusRoute;
import busregistrationsystem.model.Registration;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class AdminDashboard extends JFrame {
    private DefaultTableModel registrationsTableModel;
    private JLabel totalRevenueLabel;
    private JLabel pendingRegistrationsLabel;
    private JLabel approvedRegistrationsLabel;
    private JComboBox<String> routeCombo;
    private JLabel routeRegistrationsLabel;
    private JLabel mostPopularRouteLabel;

    public AdminDashboard() {
        setTitle("Admin Dashboard - Fast Transport Registration System");
        setIconImage(new ImageIcon("FastLogo.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        initializeUI();
        loadData();
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create top panel with title and logout button
        JPanel topPanel = createTopPanel();

        // Create stats panel
        JPanel statsPanel = createStatsPanel();

        // Create registrations table panel
        JPanel tablePanel = createTablePanel();

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(statsPanel, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(192, 57, 43));
        topPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("Fast Transport Registration System - Admin Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setOpaque(false);

        JLabel userLabel = new JLabel("Welcome, Administrator");
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

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create summary panel
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 20, 0));

        // Revenue Panel
        JPanel revenuePanel = createStatBox("Total Revenue", "", new Color(39, 174, 96));
        totalRevenueLabel = (JLabel) ((JPanel) revenuePanel.getComponent(0)).getComponent(1);

        // Pending Panel
        JPanel pendingPanel = createStatBox("Pending Registrations", "", new Color(41, 128, 185));
        pendingRegistrationsLabel = (JLabel) ((JPanel) pendingPanel.getComponent(0)).getComponent(1);

        // Approved Panel
        JPanel approvedPanel = createStatBox("Approved Registrations", "", new Color(230, 126, 34));
        approvedRegistrationsLabel = (JLabel) ((JPanel) approvedPanel.getComponent(0)).getComponent(1);

        summaryPanel.add(revenuePanel);
        summaryPanel.add(pendingPanel);
        summaryPanel.add(approvedPanel);

        // Create route statistics panel
        JPanel routeStatsPanel = new JPanel(new BorderLayout());
        routeStatsPanel.setBorder(BorderFactory.createTitledBorder("Route Statistics"));

        JPanel routeSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        routeSelectionPanel.add(new JLabel("Select Route:"));

        routeCombo = new JComboBox<>();
        populateRouteCombo();
        routeCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRouteStats();
            }
        });

        routeSelectionPanel.add(routeCombo);

        JPanel routeInfoPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        routeInfoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        routeInfoPanel.add(new JLabel("Registrations on selected route:"));
        routeRegistrationsLabel = new JLabel("0");
        routeRegistrationsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        routeInfoPanel.add(routeRegistrationsLabel);

        routeInfoPanel.add(new JLabel("Most popular route:"));
        mostPopularRouteLabel = new JLabel("");
        mostPopularRouteLabel.setFont(new Font("Arial", Font.BOLD, 14));
        routeInfoPanel.add(mostPopularRouteLabel);

        routeStatsPanel.add(routeSelectionPanel, BorderLayout.NORTH);
        routeStatsPanel.add(routeInfoPanel, BorderLayout.CENTER);

        // Add all panels to stats panel
        statsPanel.add(summaryPanel, BorderLayout.NORTH);
        statsPanel.add(routeStatsPanel, BorderLayout.CENTER);

        return statsPanel;
    }

    private JPanel createStatBox(String title, String value, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(color, 2));

        JPanel contentPanel = new JPanel(new GridLayout(2, 1));
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPanel.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 50));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));

        contentPanel.add(titleLabel);
        contentPanel.add(valueLabel);

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Registration Management"));
        tablePanel.setBorder(new EmptyBorder(0, 20, 20, 20));

        // Create table
        String[] columnNames = {
                "ID", "Name", "Email", "University ID", "Designation", "Route", "Date", "Amount", "Status", "Action"
        };

        registrationsTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 9; // Only action column is editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 9) {
                    return JButton.class;
                }
                return Object.class;
            }
        };

        JTable registrationsTable = new JTable(registrationsTableModel);
        registrationsTable.setRowHeight(35);
        registrationsTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        registrationsTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        registrationsTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        registrationsTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        registrationsTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        registrationsTable.getColumnModel().getColumn(5).setPreferredWidth(40);
        registrationsTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        registrationsTable.getColumnModel().getColumn(7).setPreferredWidth(70);
        registrationsTable.getColumnModel().getColumn(8).setPreferredWidth(60);
        registrationsTable.getColumnModel().getColumn(9).setPreferredWidth(80);

        // Set custom renderer for action buttons
        registrationsTable.getColumnModel().getColumn(9).setCellRenderer(new ButtonRenderer());
        registrationsTable.getColumnModel().getColumn(9).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(registrationsTable);
        scrollPane.setPreferredSize(new Dimension(800, 300));

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private void populateRouteCombo() {
        Collection<BusRoute> routes = DataManager.getInstance().getAllRoutes();

        routeCombo.addItem("All Routes");

        for (BusRoute route : routes) {
            routeCombo.addItem(route.getRouteNumber() + ": " + route.getRouteName());
        }
    }

    private void updateRouteStats() {
        String selected = (String) routeCombo.getSelectedItem();

        if (selected != null && !selected.equals("All Routes")) {
            // Extract route number
            int routeNumber = Integer.parseInt(selected.split(":")[0].trim());

            // Get registrations count for this route
            Map<Integer, Integer> routeCounts = DataManager.getInstance().getRouteRegistrationCounts();
            int count = routeCounts.getOrDefault(routeNumber, 0);

            routeRegistrationsLabel.setText(String.valueOf(count));
        } else {
            routeRegistrationsLabel.setText("Select a route");
        }
    }

    private void loadData() {
        // Load total revenue
        double totalRevenue = DataManager.getInstance().getTotalRevenue();
        totalRevenueLabel.setText("PKR " + totalRevenue);

        // Load registrations
        List<Registration> registrations = DataManager.getInstance().getAllRegistrations();

        int pendingCount = 0;
        int approvedCount = 0;

        // Clear table
        registrationsTableModel.setRowCount(0);

        for (Registration reg : registrations) {
            if (reg.isPaymentVerified()) {
                approvedCount++;
            } else {
                pendingCount++;
            }

            Object[] rowData = {
                    reg.getRegistrationId(),
                    reg.getName(),
                    reg.getEmail(),
                    reg.getUniversityId(),
                    reg.getDesignation(),
                    reg.getRouteNumber(),
                    reg.getFormattedDate(),
                    "PKR " + reg.getPaymentAmount(),
                    reg.getStatus(),
                    reg.isPaymentVerified() ? "Approved" : "Approve"
            };

            registrationsTableModel.addRow(rowData);
        }

        // Update counts
        pendingRegistrationsLabel.setText(String.valueOf(pendingCount));
        approvedRegistrationsLabel.setText(String.valueOf(approvedCount));

        // Update most popular route
        int popularRoute = DataManager.getInstance().getMostPopularRoute();
        if (popularRoute != -1) {
            BusRoute route = DataManager.getInstance().getRoute(popularRoute);
            if (route != null) {
                mostPopularRouteLabel.setText(popularRoute + ": " + route.getRouteName());
            } else {
                mostPopularRouteLabel.setText("No data");
            }
        } else {
            mostPopularRouteLabel.setText("No data");
        }

        // Update route stats
        updateRouteStats();
    }

    private void approveRegistration(int registrationId) {
        // Update registration status
        DataManager.getInstance().updateRegistrationStatus(registrationId, true);

        // Reload data
        loadData();

        JOptionPane.showMessageDialog(this,
                "Registration #" + registrationId + " has been approved.",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void logout() {
        SwingUtilities.invokeLater(() -> {
            StartPage startPage = new StartPage();
            startPage.setVisible(true);
            dispose();
        });
    }

    // Button renderer for the action column
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {

            setText((value == null) ? "" : value.toString());

            if (value != null && value.toString().equals("Approved")) {
                setBackground(new Color(39, 174, 96));
                setForeground(Color.WHITE);
                setEnabled(false);
            } else {
                setBackground(new Color(41, 128, 185));
                setForeground(Color.WHITE);
                setEnabled(true);
            }

            return this;
        }
    }

    // Button editor for the action column
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {

            label = (value == null) ? "" : value.toString();
            button.setText(label);

            if (label.equals("Approved")) {
                button.setBackground(new Color(39, 174, 96));
                button.setForeground(Color.WHITE);
                button.setEnabled(false);
            } else {
                button.setBackground(new Color(41, 128, 185));
                button.setForeground(Color.WHITE);
                button.setEnabled(true);
            }

            this.row = row;
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed && label.equals("Approve")) {
                // Get registration ID from the first column
                int registrationId = (int) registrationsTableModel.getValueAt(row, 0);
                approveRegistration(registrationId);
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}