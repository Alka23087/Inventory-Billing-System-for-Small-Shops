import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class SalesHistory extends JFrame {
    JTable table;
    DefaultTableModel model;

    SalesHistory() {
        setTitle("Sales History");
        setSize(600,400);

        model=new DefaultTableModel(
          new String[]{"Bill No","Product","Qty","Total","Date"},0);
        table=new JTable(model);
        add(new JScrollPane(table));

        loadData();
        setVisible(true);
    }

    void loadData() {
        try(Connection con=DBConnection.getConnection()) {
            ResultSet rs=con.createStatement().executeQuery("select * from sales");
            while(rs.next()) {
                model.addRow(new Object[]{
                  rs.getInt("bill_no"),
                  rs.getString("product_name"),
                  rs.getInt("qty"),
                  rs.getDouble("total"),
                  rs.getTimestamp("date")
                });
            }
        } catch(Exception e){ e.printStackTrace(); }
    }
}