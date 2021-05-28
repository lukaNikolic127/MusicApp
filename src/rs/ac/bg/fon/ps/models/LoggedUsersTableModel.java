package rs.ac.bg.fon.ps.models;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.User;

public class LoggedUsersTableModel extends AbstractTableModel {

    private ArrayList<User> users = new ArrayList<>();
    private String[] columns = {"ID", "Username"};

    public void setUsers(ArrayList<User> users) {
        this.users = users;
        fireTableDataChanged();
    }

    public ArrayList<User> getUsers() {
        return users;
    }
    
    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        switch(columnIndex){
            case 0:
                return user.getUserID();
            case 1:
                return user.getUsername();
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    
    
}
