package main.java.management.view.base;

import javax.swing.table.DefaultTableModel;

public interface RefreshableView {
    void refreshTable(DefaultTableModel newModel);
    void refreshTable();
}
