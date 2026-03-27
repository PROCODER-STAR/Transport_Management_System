package busregistrationsystem.gui;

import busregistrationsystem.data.DataManager;
import busregistrationsystem.model.BusRoute;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Collection;

public class RouteDisplay extends JDialog {
    private JTable routesTable;
    private DefaultTableModel tableModel;

    public RouteDisplay(Frame parent) {
        super(parent, "Available Bus Routes", true);

        setSize(700, 500);
        setLocationRelativeTo(parent);

        initializeUI();
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel titleLabel = new JLabel("Available Bus Routes");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Create table
        String[] columnNames = {
                "Route #", "Route Name", "From", "To", "Departure", "Arrival", "Stops", "Available Seats"
        };

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        routesTable = new JTable(tableModel);
        routesTable.setFillsViewportHeight(true);
        routesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        routesTable.getTableHeader().setReorderingAllowed(false);

        // Set column widths
        routesTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        routesTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        routesTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        routesTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        routesTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        routesTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        routesTable.getColumnModel().getColumn(6).setPreferredWidth(150);
        routesTable.getColumnModel().getColumn(7).setPreferredWidth(80);

        JScrollPane scrollPane = new JScrollPane(routesTable);

        // Load routes data
        loadRoutesData();

        // Create description panel
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JTextArea descriptionText = new JTextArea(
                "These are the available bus routes for the current semester.\n" +
                        "To register for a route, please go back and click on the 'Register' button.\n" +
                        "After registration, you will need to pay the fee and get your registration verified."
        );
        descriptionText.setEditable(false);
        descriptionText.setLineWrap(true);
        descriptionText.setWrapStyleWord(true);
        descriptionText.setBackground(UIManager.getColor("Panel.background"));
        descriptionText.setFont(new Font("Arial", Font.PLAIN, 12));

        descriptionPanel.add(descriptionText, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton closeButton = new RoundButton("Close",30);
        closeButton.setBackground(new Color(172, 30, 34));
        closeButton.setForeground(Color.WHITE);

        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);

        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(descriptionPanel, BorderLayout.SOUTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }

    private void loadRoutesData() {
        Collection<BusRoute> routes = DataManager.getInstance().getAllRoutes();

        for (BusRoute route : routes) {
            String stops = String.join(", ", route.getStops());
            int availableSeats = route.getCapacity() - route.getRegisteredUsers();

            Object[] rowData = {
                    route.getRouteNumber(),
                    route.getRouteName(),
                    route.getStartPoint(),
                    route.getEndPoint(),
                    route.getDepartureTime(),
                    route.getArrivalTime(),
                    stops,
                    availableSeats
            };

            tableModel.addRow(rowData);
        }
    }
}