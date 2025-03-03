package View;

import javax.swing.*;
import java.awt.*;

public class Frame extends javax.swing.JFrame {

    private final CardLayout contentLayout = new CardLayout();
    private final CardLayout frameLayout = new CardLayout();

    public Frame() {
        setupUI();
        this.add(DashboardPanel); // Add DashboardPanel to the frame
        this.pack();
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void setupUI() {
        MainContainer = new javax.swing.JPanel();
        DashboardPanel = new javax.swing.JPanel();
        ContentPanel = new javax.swing.JPanel();
        Sidebar = new javax.swing.JPanel();
        adminDashboardBtn = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();
        managerDashboardBtn = new javax.swing.JButton();
        staffDashboardBtn = new javax.swing.JButton();
        clientDashboardBtn = new javax.swing.JButton();
        signOutBtn = new javax.swing.JButton();
        welcomeLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 450));

        // Apply layout managers
        MainContainer.setLayout(frameLayout);
        ContentPanel.setLayout(contentLayout);

        // Use BorderLayout for DashboardPanel
        DashboardPanel.setLayout(new java.awt.BorderLayout());

        // Configure welcomeLabel
        welcomeLabel.setFont(new java.awt.Font("Tahoma", 1, 22));
        welcomeLabel.setText("WELCOME ADMIN!");
        welcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.WHITE); // White text for better contrast
        welcomeLabel.setOpaque(true);
        welcomeLabel.setBackground(new Color(0, 102, 204)); // Blue background
        welcomeLabel.setPreferredSize(new Dimension(800, 50)); // Set preferred size

        // Add welcomeLabel to the NORTH position of DashboardPanel
        ContentPanel.add(welcomeLabel, java.awt.BorderLayout.NORTH);

        // Configure Sidebar
        Sidebar.setBackground(new java.awt.Color(180, 180, 180));
        Sidebar.setPreferredSize(new Dimension(230, 500));

        adminDashboardBtn.setFont(new java.awt.Font("Tahoma", 1, 15));
        adminDashboardBtn.setText("Admin Home");
        adminDashboardBtn.setFocusable(false);
        adminDashboardBtn.addActionListener(evt -> switchToAdminView());

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 24));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("SECURITY Svcs");

        managerDashboardBtn.setFont(new java.awt.Font("Tahoma", 1, 15));
        managerDashboardBtn.setText("Manager Home");
        managerDashboardBtn.setFocusable(false);
        managerDashboardBtn.addActionListener(evt -> switchToManagerView());

        staffDashboardBtn.setFont(new java.awt.Font("Tahoma", 1, 15));
        staffDashboardBtn.setText("Staff Home");
        staffDashboardBtn.setFocusable(false);
        staffDashboardBtn.addActionListener(evt -> switchToStaffView());

        clientDashboardBtn.setFont(new java.awt.Font("Tahoma", 1, 15));
        clientDashboardBtn.setText("Client Home");
        clientDashboardBtn.setFocusable(false);
        clientDashboardBtn.addActionListener(evt -> switchToClientView());

        signOutBtn.setFont(new java.awt.Font("Tahoma", 1, 15));
        signOutBtn.setText("LOGOUT");
        signOutBtn.setFocusable(false);
        signOutBtn.addActionListener(evt -> logoutUser());

        javax.swing.GroupLayout sidebarLayout = new javax.swing.GroupLayout(Sidebar);
        Sidebar.setLayout(sidebarLayout);
        sidebarLayout.setHorizontalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(sidebarLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(adminDashboardBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                        .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(managerDashboardBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(staffDashboardBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clientDashboardBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(signOutBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
        );
        sidebarLayout.setVerticalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(sidebarLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(adminDashboardBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(managerDashboardBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(staffDashboardBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(clientDashboardBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                    .addComponent(signOutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );

        // Add Sidebar and ContentPanel to DashboardPanel
        DashboardPanel.add(Sidebar, java.awt.BorderLayout.WEST);
        DashboardPanel.add(ContentPanel, java.awt.BorderLayout.CENTER);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Security System Dashboard");
        this.setLocationRelativeTo(null);
    }

    private void switchToAdminView() {
        contentLayout.show(ContentPanel, "adminDashboard");
    }

    private void switchToManagerView() {
        contentLayout.show(ContentPanel, "managerDashboard");
    }

    private void switchToStaffView() {
        contentLayout.show(ContentPanel, "staffDashboard");
    }

    private void switchToClientView() {
        contentLayout.show(ContentPanel, "clientDashboard");
    }

    private void logoutUser() {
        navigateToLogin();
    }

    public void navigateToMain() {
        frameLayout.show(MainContainer, "dashboardView");
    }

    public void navigateToLogin() {
        frameLayout.show(MainContainer, "loginView");
    }

    public void navigateToRegistration() {
        frameLayout.show(MainContainer, "registerView");
    }

    // Variables declaration
    private javax.swing.JPanel MainContainer;
    private javax.swing.JPanel ContentPanel;
    private javax.swing.JPanel DashboardPanel;
    private javax.swing.JPanel Sidebar;
    private javax.swing.JButton adminDashboardBtn;
    private javax.swing.JButton clientDashboardBtn;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton signOutBtn;
    private javax.swing.JButton managerDashboardBtn;
    private javax.swing.JButton staffDashboardBtn;
    private javax.swing.JLabel welcomeLabel;


}