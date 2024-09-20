package main.java.management.view.student;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.management.controller.Controller;
import main.java.management.model.Student;
import main.java.management.model.User;
import main.java.management.view.base.InitializableView;

public class StudentDetailView extends JPanel implements InitializableView {
    private Controller controller;
    private int studentId;

    private JTextField nameText;
    private JTextField birthText;
    private JTextField departmentText;
    private JTextField gradeLevelText;
    private JTextField emailText;
    private JTextField phoneText;
    private JTextField addressText;

    public StudentDetailView(Controller controller) {
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
        nameText.setEditable(false);
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
        birthText.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        contentPanel.add(birthText, gbc);

        JLabel departmentLabel = new JLabel("学科:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        contentPanel.add(departmentLabel, gbc);

        departmentText = new JTextField(20);
        departmentText.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        contentPanel.add(departmentText, gbc);

        JLabel gradeLevelLabel = new JLabel("学年:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        contentPanel.add(gradeLevelLabel, gbc);

        gradeLevelText = new JTextField(20);
        gradeLevelText.setEditable(false);
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
        emailText.setEditable(false);
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
        phoneText.setEditable(false);
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
        addressText.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.LINE_START;
        contentPanel.add(addressText, gbc);
    }

    @Override
    public void initialize(Object... args) {
        if (args.length > 0 && args[0] instanceof Integer) {
            this.studentId = (int) args[0];

            try {
                Student student = controller.getStudentById(studentId);
                User user = controller.getUserById(student.getUserId());

                if (student != null) {
                    nameText.setText(user.getName());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    birthText.setText(student.getBirth().format(formatter));
                    departmentText.setText(student.getDepartment());
                    gradeLevelText.setText(String.valueOf(student.getGradeLevel()));
                    emailText.setText(student.getEmail());
                    phoneText.setText(student.getPhone());
                    addressText.setText(student.getAddress());
                } else {
                    // Handle the case where student is not found
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
