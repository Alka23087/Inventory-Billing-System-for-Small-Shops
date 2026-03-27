import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class BillingForm extends JFrame {
    JTextField pname, qty;
    JTable table;
    DefaultTableModel model;
    double grandTotal=0;

    BillingForm() {
        setTitle("Billing");
        setSize(600,400);
        setLayout(null);

        JLabel l1=new JLabel("Product Name"), l2=new JLabel("Qty");
        l1.setBounds(20,20,100,25);
        l2.setBounds(20,60,100,25);

        pname=new JTextField();
        qty=new JTextField();
        pname.setBounds(130,20,150,25);
        qty.setBounds(130,60,150,25);

        JButton addBtn=new JButton("Add To Bill");
        addBtn.setBounds(20,100,120,30);

        add(l1); add(l2); add(pname); add(qty); add(addBtn);

        model=new DefaultTableModel(new String[]{"Name","Price","Qty","Total"},0);
        table=new JTable(model);
        JScrollPane sp=new JScrollPane(table);
        sp.setBounds(300,20,260,250);
        add(sp);

        JLabel totalLbl=new JLabel("Grand Total: 0");
        totalLbl.setBounds(300,280,200,25);
        add(totalLbl);

        addBtn.addActionListener(e -> {
            try(Connection con=DBConnection.getConnection()) {
                PreparedStatement ps=con.prepareStatement(
                  "select * from products where name=?");
                ps.setString(1,pname.getText());
                ResultSet rs=ps.executeQuery();

                if(rs.next()) {
                    double price=rs.getDouble("price");
                    int q=Integer.parseInt(qty.getText());
                    double total=price*q;

                    grandTotal+=total;
                    totalLbl.setText("Grand Total: "+grandTotal);

                    model.addRow(new Object[]{pname.getText(),price,q,total});

                    PreparedStatement sale=con.prepareStatement(
                      "insert into sales(product_id,product_name,price,qty,total) values(?,?,?,?,?)");
                    sale.setInt(1,rs.getInt("id"));
                    sale.setString(2,pname.getText());
                    sale.setDouble(3,price);
                    sale.setInt(4,q);
                    sale.setDouble(5,total);
                    sale.executeUpdate();

                    PreparedStatement upd=con.prepareStatement(
                      "update products set stock=stock-? where id=?");
                    upd.setInt(1,q);
                    upd.setInt(2,rs.getInt("id"));
                    upd.executeUpdate();
                }
            } catch(Exception ex){ ex.printStackTrace(); }
        });

        setVisible(true);
    }
}