package busregistrationsystem.util;

import busregistrationsystem.model.BusRoute;
import busregistrationsystem.model.Registration;
import busregistrationsystem.data.DataManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class PDFGenerator {
    private static final String VOUCHERS_DIR = "vouchers";

    static {
        // Create vouchers directory if it doesn't exist
        File vouchersDir = new File(VOUCHERS_DIR);
        if (!vouchersDir.exists()) {
            vouchersDir.mkdir();
        }
    }

    public static void generateVoucher(Registration registration) {
        // In a real application, we would generate an actual PDF using a library
        // For this demo, we'll create a text file with the voucher information

        BusRoute route = DataManager.getInstance().getRoute(registration.getRouteNumber());
        String filename = VOUCHERS_DIR + File.separator + "voucher_" + registration.getRegistrationId() + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("========================================================");
            writer.println("                  BUS REGISTRATION VOUCHER              ");
            writer.println("========================================================");
            writer.println("Registration ID: " + registration.getRegistrationId());
            writer.println("Date: " + registration.getFormattedDate());
            writer.println("--------------------------------------------------------");
            writer.println("Name: " + registration.getName());
            writer.println("Email: " + registration.getEmail());
            writer.println("University ID: " + registration.getUniversityId());
            writer.println("Designation: " + registration.getDesignation());
            writer.println("--------------------------------------------------------");
            writer.println("Route Number: " + registration.getRouteNumber());
            if (route != null) {
                writer.println("Route Name: " + route.getRouteName());
                writer.println("From: " + route.getStartPoint());
                writer.println("To: " + route.getEndPoint());
                writer.println("Departure: " + route.getDepartureTime());
                writer.println("Arrival: " + route.getArrivalTime());
            }
            writer.println("--------------------------------------------------------");
            writer.println("Amount: PKR " + registration.getPaymentAmount());
            writer.println("Status: " + registration.getStatus());
            writer.println("--------------------------------------------------------");
            writer.println("Payment Instructions:");
            writer.println("1. Please pay the amount to the university account");
            writer.println("2. Reference: BUS-" + registration.getRegistrationId());
            writer.println("3. Email payment proof to transport@university.edu");
            writer.println("4. Your registration will be approved after verification");
            writer.println("========================================================");
            writer.println("This is a computer generated voucher and does not require a signature.");
            writer.println("========================================================");
        } catch (IOException e) {
            System.err.println("Error generating voucher: " + e.getMessage());
            e.printStackTrace();
        }

        // Show file location to user
        showVoucherDownloadPrompt(filename);
    }

    private static void showVoucherDownloadPrompt(String filePath) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Voucher Generated");
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("Your voucher has been generated!");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel pathLabel = new JLabel("Location: " + new File(filePath).getAbsolutePath());
        pathLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton openButton = new JButton("Open File Location");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File(filePath).getParentFile());
                    dialog.dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Could not open file location: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(label);
        panel.add(pathLabel);
        panel.add(openButton);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }
}