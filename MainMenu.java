import javax.swing.*;
import java.awt.event.*;

public class MainMenu extends JFrame implements ActionListener {
    JButton pBtn, bBtn, sBtn;

    MainMenu() {
        setTitle("Shop Billing System");
        setSize(400,300);
        setLayout(null);

        pBtn = new JButton("Product Management");
        pBtn.setBounds(100,50,200,40);

        bBtn = new JButton("Create Bill");
        bBtn.setBounds(100,110,200,40);

        sBtn = new JButton("Sales History");
        sBtn.setBounds(100,170,200,40);

        add(pBtn); add(bBtn); add(sBtn);

        pBtn.addActionListener(this);
        bBtn.addActionListener(this);
        sBtn.addActionListener(this);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==pBtn) new ProductForm();
        if(e.getSource()==bBtn) new BillingForm();
        if(e.getSource()==sBtn) new SalesHistory();
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}