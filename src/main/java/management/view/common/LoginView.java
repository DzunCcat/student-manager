package main.java.management.view.common;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.java.management.controller.Controller;
import main.java.management.model.User;
import main.java.management.view.base.BaseView;

public class LoginView extends JFrame {
    private Controller controller;
    private JPanel mainPanel;

    public LoginView(Controller controller) {
        this.controller = controller;
        this.setTitle("ログイン");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400, 300);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(mainPanel, BorderLayout.CENTER);

        JLabel userLabel = new JLabel("ユーザー名:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(userLabel, gbc);

        JTextField userText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(userText, gbc);

        JLabel passwordLabel = new JLabel("パスワード:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(passwordLabel, gbc);

        JPasswordField passwordText = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(passwordText, gbc);

        JButton loginButton = new JButton("ログイン");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                try {
                    User user = controller.getUserByUsername(username);
                    if (user != null && user.getPassword().equals(password)) {
                        showMessage("ログイン成功！");
                        controller.setCurrentUser(user);
                        new BaseView(controller).setVisible(true);
                        dispose();
                    } else {
                        showError("ユーザー名またはパスワードが無効です！");
                    }
                }catch (SQLException ex) {
                	 showError("データベースエラー: " + ex.getMessage());
				}
            }
        });
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void showError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
