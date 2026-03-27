import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class ProductForm extends JFrame {
    JTextField name, price, stock;
    JTable table;
    DefaultTableModel model;

    ProductForm() {
        setTitle("Product Management");
        setSize(600,400);
        setLayout(null);

        JLabel l1=new JLabel("Name"), l2=new JLabel("Price"), l3=new JLabel("Stock");
        l1.setBounds(20,20,80,25);
        l2.setBounds(20,60,80,25);
        l3.setBounds(20,100,80,25);

        name=new JTextField(); price=new JTextField(); stock=new JTextField();
        name.setBounds(100,20,150,25);
        price.setBounds(100,60,150,25);
        stock.setBounds(100,100,150,25);

        JButton add=new JButton("Add");
        add.setBounds(20,150,80,30);

        add(l1); add(l2); add(l3);
        add(name); add(price); add(stock);
        add(add);

        model=new DefaultTableModel(new String[]{"ID","Name","Price","Stock"},0);
        table=new JTable(model);
        JScrollPane sp=new JScrollPane(table);
        sp.setBounds(280,20,280,300);
        add(sp);

        loadTable();

        add.addActionListener(e -> addProduct());
        setVisible(true);
    }

    void loadTable() {
        try(Connection con=DBConnection.getConnection()) {
            model.setRowCount(0);
            ResultSet rs=con.createStatement().executeQuery("select * from products");
            while(rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt(1),rs.getString(2),
                    rs.getDouble(3),rs.getInt(4)
                });
            }
        } catch(Exception e){ e.printStackTrace(); }
    }

    void addProduct() {
        try(Connection con=DBConnection.getConnection()) {
            PreparedStatement ps=con.prepareStatement(
              "insert into products(name,price,stock) values(?,?,?)");
            ps.setString(1,name.getText());
            ps.setDouble(2,Double.parseDouble(price.getText()));
            ps.setInt(3,Integer.parseInt(stock.getText()));
            ps.executeUpdate();
            loadTable();
        } catch(Exception e){ e.printStackTrace(); }
    }
}