package com.company;//import com.mysql.jdbc.PreparedStatement;
import java.awt.event.*;
import net.miginfocom.swing.MigLayout;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.Vector;

//import com.mysql.jdbc.Connection;
/*
 * Created by JFormDesigner on Sun Aug 8 09:02:22 PDT 2020
 */



/**
 * @author Kovid
 */
public class Interface extends JFrame {

    public String catcode2;

    public Interface() {
        initComponents();
    }


    Connection con1;
    PreparedStatement insert;


    public void updatetable() throws ClassNotFoundException, SQLException {

        int c;
        Class.forName("com.mysql.jdbc.Driver");
        con1 = DriverManager.getConnection("jdbc:mysql://localhost/savings","root","");
        insert = con1.prepareStatement("Select * from savingstable");

        ResultSet rs = insert.executeQuery();
        ResultSetMetaData Res = rs.getMetaData();
        c = Res.getColumnCount();
        DefaultTableModel defaultModel = (DefaultTableModel) table1.getModel();
        defaultModel.setRowCount(0);

        while(rs.next()) {
            Vector v2 = new Vector();

            for(int a =1;a<=c;a++){

                v2.add(rs.getString("custno"));
                v2.add(rs.getString("custname"));
                v2.add(rs.getString("cdep"));
                v2.add(rs.getString("nyears"));
                v2.add(rs.getString("savtype"));

            }
            defaultModel.addRow(v2);
        }
    }



    private void button1ActionPerformed(ActionEvent e) throws ClassNotFoundException, SQLException {
        // TODO add your code here

        // String catcode= "", catdesc = "";
        String custno, custname, cdep, nyears, savtype;
            custno = txtcustnum.getText();
            custname = txtcustname.getText();
            cdep = txtcustdepo.getText();
            nyears = txtcustyear.getText();
            savtype = txtcustsaving.getSelectedItem().toString();


        Class.forName("com.mysql.jdbc.Driver");
        con1 = DriverManager.getConnection("jdbc:mysql://localhost/savings","root","");


        if(e.getSource()==btnadd) {
            insert = con1.prepareStatement("Select * from savingstable where custno = ?");

            insert.setString(1, custno);



            ResultSet rs = insert.executeQuery();

            if(rs.isBeforeFirst()){          //res.isBeforeFirst() is true if the cursor

                JOptionPane.showMessageDialog(null,"The Customer you are trying to enter already exists ");
                txtcustname.setText("");
                txtcustnum.setText("");
                txtcustyear.setText("");
                txtcustdepo.setText("");
                // txtcustsaving.setText("");
                txtcustnum.requestFocus();
                return;
            }


            insert = con1.prepareStatement("insert into savingstable values(?,?,?,?,?)");
            insert.setString(1, custno);
            insert.setString(2, custname);
            insert.setString(3, cdep);
            insert.setString(4, nyears);
            insert.setString(5, savtype);

            insert.executeUpdate();

            JOptionPane.showMessageDialog(null, "Record added");

            txtcustname.setText("");
            txtcustnum.setText("");
            txtcustyear.setText("");
            txtcustdepo.setText("");
            // txtcustsaving.setText("");
            txtcustnum.requestFocus();


            updatetable();


        }


        if(e.getSource()==table1) {


        }


    }

    private void table1MouseClicked(MouseEvent e) {
        // TODO add your code here

        DefaultTableModel df = (DefaultTableModel)table1.getModel();

        int index1 = table1.getSelectedRow();
        txtcustnum.setText(df.getValueAt(index1,0).toString());
        catcode2 = txtcustnum.getText();
        txtcustname.setText(df.getValueAt(index1,1).toString());
        txtcustdepo.setText(df.getValueAt(index1,2).toString());
        txtcustyear.setText(df.getValueAt(index1,3).toString());
        txtcustsaving.setSelectedItem(df.getValueAt(index1,4).toString());

    }

    private void btneditActionPerformed(ActionEvent e) throws SQLException, ClassNotFoundException {
        // TODO add your code here

        String custno, custname, cdep, nyears, savtype;
        custno = txtcustnum.getText();
        custname = txtcustname.getText();
        cdep = txtcustdepo.getText();
        nyears = txtcustyear.getText();
        savtype = txtcustsaving.getSelectedItem().toString();


//        catcode = txtcatcode.getText();
//        catdesc = txtcatdesc.getText();



        Class.forName("com.mysql.jdbc.Driver");
        con1 = DriverManager.getConnection("jdbc:mysql://localhost/savings","root","");


        insert = con1.prepareStatement("update savingstable set custno=?, custname=?, cdep=?, nyears=?, savtype=? where custno =?");

//        insert.setString(1, catcode);
//        insert.setString(2, catdesc);
//        insert.setString(3, catcode2);

        insert.setString(1, custno);
        insert.setString(2, custname);
        insert.setString(3, cdep);
        insert.setString(4, nyears);
        insert.setString(5, savtype);
        insert.setString(6, catcode2);

        insert.executeUpdate();

        JOptionPane.showMessageDialog(null, "Record edited");

        txtcustname.setText("");
        txtcustnum.setText("");
        txtcustyear.setText("");
        txtcustdepo.setText("");
        txtcustnum.requestFocus();
        updatetable();

    }

    private void btndeleteActionPerformed(ActionEvent e) throws ClassNotFoundException, SQLException {
        // TODO add your code here

        Class.forName("com.mysql.jdbc.Driver");
        con1 = DriverManager.getConnection("jdbc:mysql://localhost/savings","root","");


        int result = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete?", "Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.YES_OPTION){

            insert = con1.prepareStatement("delete from savingstable where custno =?");

            insert.setString(1, catcode2);

        }


        insert.execute();

        JOptionPane.showMessageDialog(null, "Record deleted");
        txtcustname.setText("");
        txtcustnum.setText("");
        txtcustyear.setText("");
        txtcustdepo.setText("");
        txtcustnum.requestFocus();
        updatetable();

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license
        label1 = new JLabel();
        txtcustnum = new JTextField();
        label2 = new JLabel();
        txtcustname = new JTextField();
        label3 = new JLabel();
        txtcustdepo = new JTextField();
        label4 = new JLabel();
        txtcustyear = new JTextField();
        label5 = new JLabel();
        txtcustsaving = new JComboBox();
        scrollPane2 = new JScrollPane();
        table1 = new JTable();
        btnadd = new JButton();
        btnedit = new JButton();
        btndelete = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]",
                // rows
                "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]"));

        //---- label1 ----
        label1.setText("Enter the customer number");
        contentPane.add(label1, "cell 1 1");
        contentPane.add(txtcustnum, "cell 3 1");

        //---- label2 ----
        label2.setText("Enter the customer name");
        contentPane.add(label2, "cell 1 3");

        //---- txtcatdesc ----
        txtcustname.setColumns(20);
        contentPane.add(txtcustname, "cell 3 3");

        label3.setText("Enter the initial deposit");
        contentPane.add(label3, "cell 1 5");
        contentPane.add(txtcustdepo, "cell 3 5");

        label4.setText("Enter the number of years");
        contentPane.add(label4, "cell 1 7");
        contentPane.add(txtcustyear, "cell 3 7");

        label5.setText("Choose the type of savings");
        contentPane.add(label5, "cell 1 9");
        String s[] = {"Savings-Deluxe", "Savings-Regular"};
        txtcustsaving = new JComboBox(s);
        contentPane.add(txtcustsaving, "cell 3 9");


        //======== scrollPane2 ========
        {

            //---- table1 ----
            table1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    table1MouseClicked(e);
                }
            });
            scrollPane2.setViewportView(table1);
        }
        contentPane.add(scrollPane2, "cell 3 11,height 100:100:100");

        //---- btnadd ----
        btnadd.setText("Add");
        btnadd.addActionListener(e -> {
            try {
                button1ActionPerformed(e);
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        contentPane.add(btnadd, "cell 3 15,width 150:150:150");

        //---- btnedit ----
        btnedit.setText("Edit");
        btnedit.addActionListener(e -> {
            try {
                btneditActionPerformed(e);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        });
        contentPane.add(btnedit, "cell 3 15,width 150:150:150");

        //---- btndelete ----
        btndelete.setText("Delete");
        btndelete.addActionListener(e -> {
            try {
                btndeleteActionPerformed(e);
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        contentPane.add(btndelete, "cell 3 15");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Cesar
    private JLabel label1;
    private JTextField txtcustnum;
    private JLabel label2;
    private JTextField txtcustname;
    private JLabel label3;
    private JTextField txtcustdepo;
    private JLabel label4;
    private JTextField txtcustyear;
    private JLabel label5;
    private JComboBox txtcustsaving;
    private JScrollPane scrollPane2;
    private JTable table1;
    private JButton btnadd;
    private JButton btnedit;
    private JButton btndelete;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    public void mainLayout(){
        String[] cols = {"Number", "Name", "Deposit","Year","Type of savings"};
        String[][] data = {{"d1", "d1.1"},{"d2", "d2.1"},{"d3","d3.1"},{"d4", "d4.1"},{"d5","d5.1"}};
        DefaultTableModel model = new DefaultTableModel(data, cols);
        table1.setModel(model);
        //  add(table1);
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Interface jj1 = new Interface();
        jj1.mainLayout();
        jj1.updatetable();
        jj1.setVisible(true);
    }
}

