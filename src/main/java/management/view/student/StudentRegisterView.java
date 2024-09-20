package main.java.management.view.student;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import main.java.management.controller.Controller;
import main.java.management.model.Student;
import main.java.management.model.User;
import main.java.management.view.base.BaseView;

public class StudentRegisterView extends JPanel {
    private Controller controller;

    public StudentRegisterView(Controller controller) {
        this.controller = controller;
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel userLabel = new JLabel("ユーザー名:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(userLabel, gbc);

        JTextField userText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(userText, gbc);

        JLabel passwordLabel = new JLabel("パスワード:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(passwordLabel, gbc);

        JPasswordField passwordText = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(passwordText, gbc);

        JLabel nameLabel = new JLabel("名前:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(nameLabel, gbc);

        JTextField nameText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(nameText, gbc);

        JLabel birthLabel = new JLabel("生年月日:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(birthLabel, gbc);

        JTextField birthText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(birthText, gbc);

        JLabel departmentLabel = new JLabel("学科:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(departmentLabel, gbc);

        String[] departments = {"自動車整備科", "プログラム設計科", "建築科", "介護サービス科", "機械加工エンジニア科", "デザイン塗装科"};
        JComboBox<String> departmentCombo = new JComboBox<>(departments);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(departmentCombo, gbc);

        JLabel gradeLevelLabel = new JLabel("学年:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(gradeLevelLabel, gbc);

        JTextField gradeLevelText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(gradeLevelText, gbc);

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(emailLabel, gbc);

        JTextField emailText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(emailText, gbc);

        JLabel phoneLabel = new JLabel("携帯電話:");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(phoneLabel, gbc);

        JTextField phoneText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(phoneText, gbc);

        JLabel addressLabel = new JLabel("住所:");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(addressLabel, gbc);

        JTextField addressText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(addressText, gbc);

        JButton registerButton = new JButton("登録");
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(registerButton, gbc);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                String name = nameText.getText();
                String birth = birthText.getText();
                String department = (String) departmentCombo.getSelectedItem();
                int gradeLevel = Integer.parseInt(gradeLevelText.getText());
                String email = emailText.getText();
                String phone = phoneText.getText();
                String address = addressText.getText();

                boolean isSuccess = false;

                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setName(name);
                user.setRole("student");

                Student student = new Student();
                LocalDate birthFormat = LocalDate.parse(birth, DateTimeFormatter.ofPattern("yyyyMMdd"));
                student.setBirth(birthFormat);
                student.setDepartment(department);
                student.setGradeLevel(gradeLevel);
                student.setEmail(email);
                student.setPhone(phone);
                student.setAddress(address);

                try {
                    isSuccess = controller.addStudentWithUser(user, student);
                    
                } catch (SQLException ex) {
                    showError("データベースエラー: " + ex.getMessage());
                }

                if (isSuccess) {
                    showMessage("ユーザー登録が成功しました！");
                    SwingUtilities.invokeLater(() -> {
                        controller.recreateView("StudentManage");
                        ((BaseView) getTopLevelAncestor()).showView("StudentManage");
                    });
                } else {
                    showError("ユーザー登録に失敗しました！");
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

