import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class Student extends JFrame
{

    JTextField textbox;
    JLabel label,lbl;
    JButton allbtn,rollbtn;
    JPanel panel;
    JTable table;
    JScrollPane scroll;
 
    public Student()
    {
          super("Students Record");
          String driver = "com.mysql.jdbc.Driver";
          String url = "jdbc:mysql://localhost/college_20";
          lbl = new JLabel("Students Record");
          lbl.setBounds(200, 35, 100, 20);
          textbox = new JTextField();
          textbox.setBounds(130,100,150,20); 
          label = new JLabel("Enter roll no");
          label.setBounds(50, 100, 100, 20);
          rollbtn = new JButton("search");
          rollbtn.setBounds(300,100,150,20);
          allbtn = new JButton("Display full record");
          allbtn.setBounds(180,360,150,20);
	  DefaultTableModel model = new DefaultTableModel(new String[]{"Roll No", "Name", "Dept", "Division","Total Marks"}, 0);
          table = new JTable(model);
          scroll = new JScrollPane(table); 
          scroll.setBounds(50,150,400,200);
           
	  add(lbl);
          add(textbox);
          add(label);
          add(rollbtn); 
          add(allbtn);
	  add(scroll);
		  
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          setLayout(null);
          setVisible(true);
          setSize(550,450);

          allbtn.addActionListener(new ActionListener()
          {
              public void actionPerformed(ActionEvent ae)
              {

                   model.setRowCount(0);
                   try
                   { 
                        Class.forName(driver); 
                        Connection con = DriverManager.getConnection(url, "root", "");
                        Statement st= con.createStatement();
                        ResultSet rs = st.executeQuery("select * from stud_20");
                        while(rs.next())
                        {
                              model.addRow(new Object[]{rs.getString("rollno"),rs.getString("name"),rs.getString("dept"),rs.getString("division"),rs.getInt("total_marks")});
							 
                        }

                    }
                    catch(Exception ex)
                    {
                            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                    }

              }
         });

         rollbtn.addActionListener(new ActionListener()
         {
             public void actionPerformed(ActionEvent ae)
             {
                String textvalue =textbox.getText();
		if(textvalue=="")
			JOptionPane.showMessageDialog(null,"Enter roll no");
		else
		{
					 
		    model.setRowCount(0);
                    try
                    { 
                        Class.forName(driver); 
                        Connection con = DriverManager.getConnection(url, "root","");
                        Statement st= con.createStatement();
                        ResultSet rs = st.executeQuery("select * from stud_20 where rollno = '"+textvalue+"';");

                        int i =0;
                        if(rs.next())
                        {
                           
                           model.addRow(new Object[]{rs.getString("rollno"),rs.getString("name"),rs.getString("dept"),rs.getString("division"),rs.getInt("total_marks")});
						   i++; 
                        }
                        if(i <1)
                        {
                            JOptionPane.showMessageDialog(null, "No Record Found","Error",JOptionPane.ERROR_MESSAGE);
			}
		    }
		    catch(Exception ex)
                    {
                       JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		    }
		}
	    }
        });
		
    }
}
class Record{

    public static void main(String args[]) throws Exception
    {
         new Student();
    }
}
