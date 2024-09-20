package main.java.management.view.base;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import main.java.management.controller.Controller;
import main.java.management.model.User;
import main.java.management.view.common.LoginView;
import main.java.management.view.common.MainView;
import main.java.management.view.common.RegisterView;
import main.java.management.view.student.StudentDetailView;
import main.java.management.view.student.StudentEditView;
import main.java.management.view.student.StudentManageView;
import main.java.management.view.student.StudentRegisterView;
import main.java.management.view.student.StudentView;

public class BaseView extends JFrame {
    protected JPanel mainPanel;
    protected CardLayout cardLayout;
    protected JPanel cardPanel;
    protected Controller controller;

    private Map<String, Set<String>> roleViewAccessMap;
    private Map<String, JPanel> viewMap;

    public BaseView(Controller controller) {
        this.controller = controller;
        this.controller.setBaseView(this); // Set BaseView instance to the controller
        this.setTitle("Application Base");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 600);

        mainPanel = new JPanel(new BorderLayout());
        this.add(mainPanel);
        setLocationRelativeTo(null);

        // Header
        JLabel headerLabel = new JLabel("Application Header", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 24));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        JPanel footerPanel = new JPanel(new BorderLayout());
        JLabel usernameLabel = new JLabel("Username: " + getUsername(), SwingConstants.LEFT);
        footerPanel.add(usernameLabel, BorderLayout.WEST);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Sidebar with scrollable toolbar
        JPanel sidebarPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(sidebarPanel);
        JPanel toolbar = new JPanel(new GridLayout(0, 1));

        toolbar.add(createButton("Home", "MainView"));
        toolbar.add(createButton("Dashboard"));
        toolbar.add(createButton("Message"));
        toolbar.add(createButton("Calendar"));
        toolbar.add(createButton("Student Manage", "StudentManage"));

        JPanel toolbarContainer = new JPanel(new BorderLayout());
        toolbarContainer.add(toolbar, BorderLayout.NORTH);

        JButton logoutToolbarButton = new JButton("Logout");
        logoutToolbarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clearCurrentUser();
                new LoginView(controller).setVisible(true);
                dispose();
            }
        });
        toolbarContainer.add(logoutToolbarButton, BorderLayout.SOUTH);

        sidebarPanel.add(toolbarContainer, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.WEST);

        // Card layout panel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        viewMap = new HashMap<>();

        addView("MainView", new MainView(controller));
        addView("Register", new RegisterView(controller));
        addView("StudentView", new StudentView(controller));
        addView("StudentRegister", new StudentRegisterView(controller));
        addView("StudentManage", new StudentManageView(controller));
        addView("StudentDetail", new StudentDetailView(controller));
        addView("StudentEdit", new StudentEditView(controller));

        setupRoleViewAccessMap();

        showView("MainView");
    }

    private void setupRoleViewAccessMap() {
        roleViewAccessMap = new HashMap<>();

        Set<String> studentViews = new HashSet<>();
        studentViews.add("MainView");
        studentViews.add("Message");
        studentViews.add("Calendar");

        Set<String> staffViews = new HashSet<>();
        staffViews.add("MainView");
        staffViews.add("Dashboard");
        staffViews.add("Message");
        staffViews.add("Calendar");
        staffViews.add("StudentView");
        staffViews.add("StudentRegister");
        staffViews.add("StudentManage");
        staffViews.add("StudentDetail");
        staffViews.add("StudentEdit");
        staffViews.add("Register");

        roleViewAccessMap.put("student", studentViews);
        roleViewAccessMap.put("staff", staffViews);
    }

    private JButton createButton(String name) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showView(name);
            }
        });
        return button;
    }

    private JButton createButton(String name, String viewName) {
        JButton button = new JButton(name);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showView(viewName);
            }
        });
        return button;
    }

    private String getUsername() {
        User currentUser = controller.getCurrentUser();
        return (currentUser != null) ? currentUser.getUsername() : "Guest";
    }

    public void addView(String name, JPanel panel) {
        viewMap.put(name, panel);
        cardPanel.add(panel, name);
    }
    
    public JPanel getView(String name) {
        return viewMap.get(name);
    }

    public void showView(String name, Object... args) {
        User currentUser = controller.getCurrentUser();
        if (currentUser != null) {
            String role = currentUser.getRole();
            Set<String> allowedViews = roleViewAccessMap.get(role);
            if (allowedViews != null && !allowedViews.contains(name)) {
                JOptionPane.showMessageDialog(this, currentUser.getUsername() + "さんの権限は" + role + "です。");
                name = "MainView";
            }
        }
        if (viewMap.get(name) instanceof InitializableView) {
            ((InitializableView) viewMap.get(name)).initialize(args);
        }
        cardLayout.show(cardPanel, name);
    }

    
    public void refreshTable(String viewName, DefaultTableModel newModel) {
        JPanel view = getView(viewName);
        if (view instanceof RefreshableView) {
            ((RefreshableView) view).refreshTable(newModel);
        }
    }
    
    public void recreateView(String viewName) {
        JPanel oldView = viewMap.get(viewName);
        if (oldView != null) {
            cardPanel.remove(oldView);
            
            JPanel newView = createNewView(viewName);
            if (newView != null) {
                viewMap.put(viewName, newView);
                cardPanel.add(newView, viewName);
                
                cardLayout.show(cardPanel, viewName);
                cardPanel.revalidate();
                cardPanel.repaint();
            } else {
                // 新しいビューの作成に失敗した場合、古いビューを再追加
                cardPanel.add(oldView, viewName);
                System.err.println("Failed to recreate view: " + viewName);
            }
        } else {
            System.err.println("View not found: " + viewName);
        }
    }

    private JPanel createNewView(String viewName) {
        switch (viewName) {
	        case "StudentView":
	        	return new StudentView(controller);
        	
            case "StudentManage":
                return new StudentManageView(controller);
            case "StudentRegister":
                return new StudentRegisterView(controller);
            case "StudentEdit":
                return new StudentEditView(controller);
            case "StudentDetail":
                return new StudentDetailView(controller);
            case "MainView":
                return new MainView(controller);
            case "RegisterView":
                return new RegisterView(controller);
            // 他のビューも同様に追加
            default:
                return null;
        }
    }
}
