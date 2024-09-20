package main.java.management.view.student;

import java.awt.BorderLayout;
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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import main.java.management.controller.Controller;
import main.java.management.model.Student;
import main.java.management.model.User;
import main.java.management.view.base.InitializableView;

public class StudentEditView extends JPanel implements InitializableView {
    private Controller controller;
    private int studentId;

    private JTextField nameText;
    private JTextField birthText;
    private JComboBox<String> departmentCombo;
    private JTextField gradeLevelText;
    private JTextField emailText;
    private JTextField phoneText;
    private JTextField addressText;

    public StudentEditView(Controller controller) {
        this.controller = controller;
        this.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(contentPanel, BorderLayout.CENTER);

        JLabel nameLabel = new JLabel("名前:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        contentPanel.add(nameLabel, gbc);

        nameText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        contentPanel.add(nameText, gbc);

        JLabel birthLabel = new JLabel("誕生日:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        contentPanel.add(birthLabel, gbc);

        birthText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        contentPanel.add(birthText, gbc);

        JLabel departmentLabel = new JLabel("学科:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        contentPanel.add(departmentLabel, gbc);

        String[] departments = {"自動車整備科", "プログラム設計科", "建築科", "介護サービス科", "機械加工エンジニア科", "デザイン塗装科"};
        departmentCombo = new JComboBox<>(departments);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        contentPanel.add(departmentCombo, gbc);

        JLabel gradeLevelLabel = new JLabel("学年:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        contentPanel.add(gradeLevelLabel, gbc);

        gradeLevelText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        contentPanel.add(gradeLevelText, gbc);

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_END;
        contentPanel.add(emailLabel, gbc);

        emailText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_START;
        contentPanel.add(emailText, gbc);

        JLabel phoneLabel = new JLabel("携帯電話:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_END;
        contentPanel.add(phoneLabel, gbc);

        phoneText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.LINE_START;
        contentPanel.add(phoneText, gbc);

        JLabel addressLabel = new JLabel("住所:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.LINE_END;
        contentPanel.add(addressLabel, gbc);

        addressText = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.LINE_START;
        contentPanel.add(addressText, gbc);

        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveStudent();
                } catch (SQLException ex) {
                    showError("データベースエラー: " + ex.getMessage());
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(saveButton, gbc);
    }

    @Override
    public void initialize(Object... args) {
        if (args.length > 0 && args[0] instanceof Integer) {
            this.studentId = (int) args[0];

            try {
                Student student = controller.getStudentById(studentId);
                User user = controller.getUserById(student.getUserId());

                if (student != null && user != null) {
                    nameText.setText(user.getName());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                    birthText.setText(student.getBirth().format(formatter));
                    departmentCombo.setSelectedItem(student.getDepartment());
                    gradeLevelText.setText(String.valueOf(student.getGradeLevel()));
                    emailText.setText(student.getEmail());
                    phoneText.setText(student.getPhone());
                    addressText.setText(student.getAddress());
                } else {
                    showError("学生情報が見つかりません。");
                }
            } catch (SQLException e) {
                showError("データベースエラー: " + e.getMessage());
            }
        }
    }

    private void saveStudent() throws SQLException {
        String name = nameText.getText();
        String birth = birthText.getText();
        String department = (String) departmentCombo.getSelectedItem();
        int gradeLevel = Integer.parseInt(gradeLevelText.getText());
        String email = emailText.getText();
        String phone = phoneText.getText();
        String address = addressText.getText();

        Student student = controller.getStudentById(studentId);
        User user = controller.getUserById(student.getUserId());
        
        user.setName(name);
        LocalDate birthFormat = LocalDate.parse(birth, DateTimeFormatter.ofPattern("yyyyMMdd"));
        student.setBirth(birthFormat);
        
        student.setDepartment(department);
        student.setGradeLevel(gradeLevel);
        student.setEmail(email);
        student.setPhone(phone);
        student.setAddress(address);

        boolean isSuccess = controller.updateStudentWithUser(user, student);
        
        if (isSuccess) {
            showMessage("学生情報の更新が成功しました！");
            SwingUtilities.invokeLater(() -> {
                controller.recreateView("StudentManage");
            });
        } else {
            showError("学生情報の更新に失敗しました！");
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void showError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
