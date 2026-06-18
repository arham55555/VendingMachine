/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.vendingmachine;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;


/**
 *
 * @author ARHAM
 */



public class VendingMachineGUI extends JFrame {

    private VendingMachine machine;

    private JTabbedPane tabbedPane;

    // Tab 1 - Buy
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JLabel lblSelected, lblPrice, lblMachineInfo;
    private JTextField txtAmountPaid;
    private JButton btnBuy;
    private JTextArea txaReceipt;

    // Tab 2 - Admin
    private JTextField txtName, txtPrice, txtQty, txtCategory, txtRestock;
    private JButton btnAdd, btnRemove, btnRestock;
    private JLabel lblRevenue;
    private JTextArea txaLog;
    private JTable adminTable;
    private DefaultTableModel adminTableModel;

    public VendingMachineGUI() {
        machine = new VendingMachine("VM-001", "Main Lobby");
        setTitle("Vending Machine Management System");
        setSize(750, 560);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        buildUI();
        refreshTables();
    }

    private void buildUI() {
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("  Buy Products", buildBuyPanel());
        tabbedPane.addTab("  Admin Panel",  buildAdminPanel());
        add(tabbedPane);
    }

    private JPanel buildBuyPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblMachineInfo = new JLabel("Machine: " + machine.getMachineId() +
            "  |  Location: " + machine.getLocation(), SwingConstants.CENTER);
        lblMachineInfo.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblMachineInfo.setForeground(new Color(30, 80, 160));
        panel.add(lblMachineInfo, BorderLayout.NORTH);

        String[] cols = {"#", "Product", "Category", "Price", "Stock"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        productTable = new JTable(tableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.setRowHeight(24);
        productTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        productTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        productTable.getSelectionModel().addListSelectionListener(e -> onProductSelect());
        panel.add(new JScrollPane(productTable), BorderLayout.CENTER);

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBorder(BorderFactory.createTitledBorder("Purchase"));
        right.setPreferredSize(new Dimension(220, 0));

        lblSelected = new JLabel("Selected: ---");
        lblPrice    = new JLabel("Price: ---");
        lblSelected.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblPrice.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JLabel lblPay = new JLabel("Enter Amount ($):");
        txtAmountPaid = new JTextField();
        txtAmountPaid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        btnBuy = new JButton("BUY NOW");
        btnBuy.setBackground(new Color(34, 139, 34));
        btnBuy.setForeground(Color.GREEN);
        btnBuy.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnBuy.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        btnBuy.addActionListener(e -> onBuy());

        txaReceipt = new JTextArea(6, 18);
        txaReceipt.setEditable(false);
        txaReceipt.setFont(new Font("Monospaced", Font.PLAIN, 11));

        JScrollPane receiptScroll = new JScrollPane(txaReceipt);
        receiptScroll.setBorder(BorderFactory.createTitledBorder("Receipt"));

        right.add(Box.createVerticalStrut(10));
        right.add(lblSelected);
        right.add(Box.createVerticalStrut(5));
        right.add(lblPrice);
        right.add(Box.createVerticalStrut(12));
        right.add(lblPay);
        right.add(Box.createVerticalStrut(4));
        right.add(txtAmountPaid);
        right.add(Box.createVerticalStrut(10));
        right.add(btnBuy);
        right.add(Box.createVerticalStrut(14));
        right.add(receiptScroll);

        panel.add(right, BorderLayout.EAST);
        return panel;
    }

    private JPanel buildAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"#", "Product", "Category", "Price", "Stock"};
        adminTableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        adminTable = new JTable(adminTableModel);
        adminTable.setRowHeight(24);
        adminTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        adminTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        panel.add(new JScrollPane(adminTable), BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 6, 6));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Product"));
        txtName     = new JTextField();
        txtCategory = new JTextField();
        txtPrice    = new JTextField();
        txtQty      = new JTextField();
        formPanel.add(new JLabel("Name:"));      formPanel.add(txtName);
        formPanel.add(new JLabel("Category:"));  formPanel.add(txtCategory);
        formPanel.add(new JLabel("Price ($):"));  formPanel.add(txtPrice);
        formPanel.add(new JLabel("Quantity:"));  formPanel.add(txtQty);
        btnAdd = new JButton("Add Product");
        btnAdd.setBackground(new Color(30, 100, 200));
        btnAdd.setForeground(Color.RED);
        btnAdd.addActionListener(e -> onAddProduct());
        formPanel.add(new JLabel());
        formPanel.add(btnAdd);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setPreferredSize(new Dimension(220, 0));

        JPanel removePanel = new JPanel(new FlowLayout());
        removePanel.setBorder(BorderFactory.createTitledBorder("Remove Selected"));
        btnRemove = new JButton("Remove");
        btnRemove.setBackground(new Color(200, 50, 50));
        btnRemove.setForeground(Color.GREEN);
        btnRemove.addActionListener(e -> onRemoveProduct());
        removePanel.add(btnRemove);

        JPanel restockPanel = new JPanel(new GridLayout(3, 1, 4, 4));
        restockPanel.setBorder(BorderFactory.createTitledBorder("Restock Selected"));
        txtRestock = new JTextField();
        btnRestock = new JButton("Restock");
        btnRestock.setBackground(new Color(180, 100, 0));
        btnRestock.setForeground(Color.RED);
        btnRestock.addActionListener(e -> onRestock());
        restockPanel.add(new JLabel("Add Qty:"));
        restockPanel.add(txtRestock);
        restockPanel.add(btnRestock);

        lblRevenue = new JLabel("Total Revenue: $0.00", SwingConstants.CENTER);
        lblRevenue.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblRevenue.setForeground(new Color(0, 120, 0));
        lblRevenue.setBorder(BorderFactory.createTitledBorder("Revenue"));

        txaLog = new JTextArea(5, 18);
        txaLog.setEditable(false);
        txaLog.setFont(new Font("Monospaced", Font.PLAIN, 10));
        JScrollPane logScroll = new JScrollPane(txaLog);
        logScroll.setBorder(BorderFactory.createTitledBorder("Transaction Log"));

        rightPanel.add(removePanel);
        rightPanel.add(Box.createVerticalStrut(8));
        rightPanel.add(restockPanel);
        rightPanel.add(Box.createVerticalStrut(8));
        rightPanel.add(lblRevenue);
        rightPanel.add(Box.createVerticalStrut(8));
        rightPanel.add(logScroll);

        panel.add(formPanel, BorderLayout.SOUTH);
        panel.add(rightPanel, BorderLayout.EAST);
        return panel;
    }

    private void onProductSelect() {
        int row = productTable.getSelectedRow();
        if (row >= 0) {
            Product p = machine.getProducts().get(row);
            lblSelected.setText("Selected: " + p.getName());
            lblPrice.setText("Price: $" + String.format("%.2f", p.getPrice()));
        }
    }

    private void onBuy() {
        int row = productTable.getSelectedRow();
        if (row < 0) { showMsg("Please select a product first!", "Warning", JOptionPane.WARNING_MESSAGE); return; }
        double paid;
        try { paid = Double.parseDouble(txtAmountPaid.getText().trim()); }
        catch (NumberFormatException ex) { showMsg("Enter a valid amount!", "Error", JOptionPane.ERROR_MESSAGE); return; }

        double result = machine.purchaseProduct(row, paid);
        if      (result == -1) { showMsg("Invalid selection!", "Error", JOptionPane.ERROR_MESSAGE); }
        else if (result == -2) { showMsg("OUT OF STOCK!", "Out of Stock", JOptionPane.WARNING_MESSAGE); }
        else if (result == -3) { showMsg("Insufficient funds! Price is $" +
            String.format("%.2f", machine.getProducts().get(row).getPrice()),
            "Insufficient Funds", JOptionPane.ERROR_MESSAGE); }
        else {
            Product p = machine.getProducts().get(row);
            txaReceipt.setText(
                "=== RECEIPT ===\n" +
                "Item   : " + p.getName() + "\n" +
                "Price  : $" + String.format("%.2f", p.getPrice()) + "\n" +
                "Paid   : $" + String.format("%.2f", paid) + "\n" +
                "Change : $" + String.format("%.2f", result) + "\n" +
                "===============\nThank you!"
            );
            txtAmountPaid.setText("");
            refreshTables();
            updateAdminUI();
        }
    }

    private void onAddProduct() {
        try {
            String name = txtName.getText().trim();
            String cat  = txtCategory.getText().trim();
            if (name.isEmpty() || cat.isEmpty()) throw new Exception();
            double price = Double.parseDouble(txtPrice.getText().trim());
            int qty      = Integer.parseInt(txtQty.getText().trim());
            machine.addProduct(new Product(name, price, qty, cat));
            txtName.setText(""); txtCategory.setText("");
            txtPrice.setText(""); txtQty.setText("");
            refreshTables();
            showMsg("Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            showMsg("Fill all fields correctly.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onRemoveProduct() {
        int row = adminTable.getSelectedRow();
        if (row < 0) { showMsg("Select a product to remove.", "Warning", JOptionPane.WARNING_MESSAGE); return; }
        int confirm = JOptionPane.showConfirmDialog(this,
            "Remove \"" + machine.getProducts().get(row).getName() + "\"?",
            "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            machine.removeProduct(row);
            refreshTables();
        }
    }

    private void onRestock() {
        int row = adminTable.getSelectedRow();
        if (row < 0) { showMsg("Select a product to restock.", "Warning", JOptionPane.WARNING_MESSAGE); return; }
        try {
            int qty = Integer.parseInt(txtRestock.getText().trim());
            machine.restockProduct(row, qty);
            txtRestock.setText("");
            refreshTables();
            showMsg("Restocked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            showMsg("Enter a valid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTables() {
        Object[][] data = machine.getProductTableData();
        String[] cols = {"#", "Product", "Category", "Price", "Stock"};
        tableModel.setDataVector(data, cols);
        adminTableModel.setDataVector(data, cols);
        updateAdminUI();
    }

    private void updateAdminUI() {
        lblRevenue.setText("Total Revenue: $" + String.format("%.2f", machine.getTotalRevenue()));
        txaLog.setText("");
        for (String log : machine.getTransactionLog()) txaLog.append(log + "\n");
    }

    private void showMsg(String msg, String title, int type) {
        JOptionPane.showMessageDialog(this, msg, title, type);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new VendingMachineGUI().setVisible(true));
    }
}
