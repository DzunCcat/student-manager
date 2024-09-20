package main.java.management.view.student;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.java.management.controller.Controller;
import main.java.management.view.base.BaseView;

public class StudentView extends JPanel {
    private Controller controller;

    public StudentView(Controller controller) {
        this.controller = controller;

        this.setLayout(new BorderLayout());

        // メインコンテンツパネルの設定
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout());
        this.add(mainContentPanel, BorderLayout.CENTER);

        // ユーザー概要情報の表示
        JLabel ScreenInfoLabel = new JLabel("StudetView", SwingConstants.CENTER);
        ScreenInfoLabel.setFont(new java.awt.Font("Serif", java.awt.Font.BOLD, 18));
        mainContentPanel.add(ScreenInfoLabel, BorderLayout.NORTH);

        // ダッシュボードやショートカットボタンのパネル
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new GridLayout(0, 1, 10, 10));
        mainContentPanel.add(dashboardPanel, BorderLayout.CENTER);

        // ダッシュボード項目の追加（共通）
        JButton messageButton = createDashboardButton("StudentManage", "StudentManage");
        dashboardPanel.add(messageButton);

        JButton calendarButton = createDashboardButton("StudenrRegister", "StudentRegister");
        dashboardPanel.add(calendarButton);
    }

    private JButton createDashboardButton(String text, String viewName) {
        JButton button = new JButton(text);
        button.setActionCommand(viewName);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((BaseView) StudentView.this.getTopLevelAncestor()).showView(e.getActionCommand());
            }
        });
        return button;
    }
}
