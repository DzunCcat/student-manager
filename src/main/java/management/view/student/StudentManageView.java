package main.java.management.view.student;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import main.java.management.controller.Controller;
import main.java.management.model.Student;
import main.java.management.model.User;
import main.java.management.view.base.BaseView;
import main.java.management.view.base.RefreshableView;

public class StudentManageView extends JPanel implements RefreshableView {
    private Controller controller;
    private DefaultTableModel tableModel;
    private JTable studentTable;
    private JTextField searchNameText;
    private JTextField searchStudentIdText;
    private JTextField searchDepartmentText;
    private JTextField searchGradeLevelText;
    private JTextField searchAddressText;
    private JTextField searchAgeText;
    private JButton searchButton;

    public StudentManageView(Controller controller) {
        this.controller = controller;
        this.setLayout(new BorderLayout());

        // Table model setup
        tableModel = new DefaultTableModel(new Object[]{"ID", "ユーザー名", "名前", "学科", "学年", "メール", "電話", "住所", "年齢"}, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        this.add(scrollPane, BorderLayout.CENTER);

        // Control panel setup
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        searchNameText = new JTextField(10);
        searchStudentIdText = new JTextField(5);
        searchDepartmentText = new JTextField(10);
        searchGradeLevelText = new JTextField(5);
        searchAddressText = new JTextField(10);
        searchAgeText = new JTextField(5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(new JLabel("名前:"), gbc);
        gbc.gridx = 1;
        controlPanel.add(searchNameText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        controlPanel.add(searchStudentIdText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        controlPanel.add(new JLabel("学科:"), gbc);
        gbc.gridx = 1;
        controlPanel.add(searchDepartmentText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        controlPanel.add(new JLabel("学年:"), gbc);
        gbc.gridx = 1;
        controlPanel.add(searchGradeLevelText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        controlPanel.add(new JLabel("住所:"), gbc);
        gbc.gridx = 1;
        controlPanel.add(searchAddressText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        controlPanel.add(new JLabel("年齢:"), gbc);
        gbc.gridx = 1;
        controlPanel.add(searchAgeText, gbc);

        searchButton = new JButton("検索");
        gbc.gridx = 1;
        gbc.gridy = 6;
        controlPanel.add(searchButton, gbc);

        this.add(controlPanel, BorderLayout.NORTH);

        // Control buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton addButton = new JButton("追加");
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(addButton, gbc);

        JButton editButton = new JButton("編集");
        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(editButton, gbc);

        JButton deleteButton = new JButton("削除");
        gbc.gridx = 2;
        gbc.gridy = 0;
        buttonPanel.add(deleteButton, gbc);

        JButton detailButton = new JButton("詳細");
        gbc.gridx = 3;
        gbc.gridy = 0;
        buttonPanel.add(detailButton, gbc);

        this.add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchStudents();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((BaseView) getTopLevelAncestor()).showView("StudentRegister");
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int studentId = (int) tableModel.getValueAt(selectedRow, 0);
                    ((BaseView) getTopLevelAncestor()).showView("StudentEdit", studentId);
                } else {
                    showError("編集する生徒を選択してください。");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int studentId = (int) tableModel.getValueAt(selectedRow, 0);
                    try {
                        if (controller.deleteStudentWithUser(studentId)) {
                            showMessage("生徒を削除しました。");
                            loadStudents();
                        } else {
                            showError("生徒の削除に失敗しました。");
                        }
                    } catch (SQLException ex) {
                        showError("データベースエラー: " + ex.getMessage());
                    }
                } else {
                    showError("削除する生徒を選択してください。");
                }
            }
        });

        detailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int studentId = (int) tableModel.getValueAt(selectedRow, 0);
                    ((BaseView) getTopLevelAncestor()).showView("StudentDetail", studentId);
                } else {
                    showError("詳細を見る生徒を選択してください。");
                }
            }
        });

        // Load students initially
        loadStudents();
    }

    private void loadStudents() {
        try {
            List<Student> students = controller.getAllStudents();
            updateTableModel(students);
        } catch (SQLException e) {
            showError("データベースエラー: " + e.getMessage());
        }
    }

    private void searchStudents() {
        SwingUtilities.invokeLater(() -> {
            try {
                String name = searchNameText.getText();
                Integer studentId = null;
                if (!searchStudentIdText.getText().isEmpty()) {
                    studentId = Integer.parseInt(searchStudentIdText.getText());
                }
                String department = searchDepartmentText.getText();
                String gradeLevel = searchGradeLevelText.getText();
                String address = searchAddressText.getText();
                Integer age = null;
                if (!searchAgeText.getText().isEmpty()) {
                    age = Integer.parseInt(searchAgeText.getText());
                }

                List<Student> students = controller.searchStudents(name, studentId, department, gradeLevel, address, age);
                updateTableModel(students);
            } catch (SQLException e) {
                showError("データベースエラー: " + e.getMessage());
            }
        });
    }

    private void updateTableModel(List<Student> students) {
        tableModel.setRowCount(0); // Clear existing rows
        for (Student student : students) {
            try {
                User user = controller.getUserById(student.getUserId());
                tableModel.addRow(new Object[]{
                    student.getStudentId(),
                    user.getUsername(),
                    user.getName(),
                    student.getDepartment(),
                    student.getGradeLevel(),
                    student.getEmail(),
                    student.getPhone(),
                    student.getAddress(),
                    student.getAge()
                });
            } catch (SQLException e) {
                showError("ユーザーデータの取得エラー: " + e.getMessage());
            }
        }
        refreshTableData();
    }
    @Override
    public void refreshTable(DefaultTableModel newModel) {
        studentTable.setModel(newModel);
        refreshTableData();
    }

    @Override
    public void refreshTable() {
        SwingUtilities.invokeLater(this::loadStudents);
    }
    
    private void refreshTableData() {
        DefaultTableModel tempModel = (DefaultTableModel) studentTable.getModel();
        studentTable.setModel(new DefaultTableModel());
        studentTable.setModel(tempModel);
        studentTable.revalidate();
        studentTable.repaint();
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void showError(String error) {
        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
}