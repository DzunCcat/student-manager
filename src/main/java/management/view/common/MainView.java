package main.java.management.view.common;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.java.management.controller.Controller;
import main.java.management.model.User;
import main.java.management.view.base.BaseView;

public class MainView extends JPanel {
    private Controller controller;

    public MainView(Controller controller) {
        this.controller = controller;
        User user = controller.getCurrentUser();
        String username = user.getUsername();
        String role = user.getRole();

        this.setLayout(new BorderLayout());

        // メインコンテンツパネルの設定
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout());
        this.add(mainContentPanel, BorderLayout.CENTER);

        // ユーザー概要情報の表示
        JLabel userInfoLabel = new JLabel("ようこそ、 " + username + " さん (" + role + ")", SwingConstants.CENTER);
        userInfoLabel.setFont(new java.awt.Font("Serif", java.awt.Font.BOLD, 18));
        mainContentPanel.add(userInfoLabel, BorderLayout.NORTH);

        // ダッシュボードやショートカットボタンのパネル
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new GridLayout(2, 2, 10, 10));
        mainContentPanel.add(dashboardPanel, BorderLayout.CENTER);

        // ダッシュボード項目の追加（共通）
        JButton messageButton = createDashboardButton("メッセージ", "Message");
        dashboardPanel.add(messageButton);

        JButton calendarButton = createDashboardButton("カレンダー", "Calendar");
        dashboardPanel.add(calendarButton);

        // 役割に応じたダッシュボード項目の追加
        if (role.equals("student")) {
            JButton attendanceButton = createDashboardButton("出席情報", "AttendanceInfo");
            dashboardPanel.add(attendanceButton);

            JButton gradeButton = createDashboardButton("成績情報", "GradeInfo");
            dashboardPanel.add(gradeButton);
        } else if (role.equals("staff")) {
            JButton studentManageButton = createDashboardButton("学生管理", "StudentView");
            dashboardPanel.add(studentManageButton);

            JButton attendanceManageButton = createDashboardButton("出席管理", "AttendanceManageView");
            dashboardPanel.add(attendanceManageButton);

            JButton gradeManageButton = createDashboardButton("成績管理", "GradeManage");
            dashboardPanel.add(gradeManageButton);
            
            JButton staffRegisterButton = createDashboardButton("Staff登録", "Register");
            dashboardPanel.add(staffRegisterButton);
        }
    }

    private JButton createDashboardButton(String text, String viewName) {
        JButton button = new JButton(text);
        button.setActionCommand(viewName);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((BaseView) MainView.this.getTopLevelAncestor()).showView(e.getActionCommand());
            }
        });
        return button;
    }
}
