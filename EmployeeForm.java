import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

class EmployeeForm extends JFrame
{
	JLabel l1,l2,l3,title,info;
	JTextField t1,t2,t3;
	JButton b1,b2,b3,b4,exit;
	Connection con;
	PreparedStatement insertps;
	PreparedStatement updateps;
	PreparedStatement deleteps;
	PreparedStatement selectps;
	EmployeeForm()
	{
		setSize(355,300);
		setLocation(100,100);
		Container c=getContentPane();
		title=new JLabel(" Employee Details ");
		title.setFont(new Font("times new roman",Font.BOLD,15));
		l1=new JLabel("Employee No :");
		l2=new JLabel("Name :");
		l3=new JLabel("Salary :");
		t1=new JTextField(10);
		t2=new JTextField(10);
		t3=new JTextField(10);
		b1=new JButton("insrt");
		b2=new JButton("delet");
		b3=new JButton("updt");
		b4=new JButton("show");
		exit=new JButton("exit");
		c.setLayout(null);
		title.setBounds(60,10,160,20);
		c.add(title);
		l1.setBounds(40,40,50,20);
		c.add(l1);
		t1.setBounds(95,40,108,20);
		c.add(t1);
		l2.setBounds(40,70,50,20);
		c.add(l2);
		t2.setBounds(95,70,108,20);
		c.add(t2);
		l3.setBounds(40,100,50,20);
		c.add(l3);
		t3.setBounds(95,100,108,20);
		c.add(t3);
		b1.setBounds(10,140,65,40);
		c.add(b1);
		b2.setBounds(77,140,65,40);
		c.add(b2);
		b3.setBounds(144,140,65,40);
		c.add(b3);
		b4.setBounds(211,140,65,40);
		c.add(b4);
		exit.setBounds(278,140,65,40);
		c.add(exit);
		info=new JLabel("Getting Connected to database");
		info.setFont(new Font("Dialog",Font.BOLD,15));	//Dialog is font name here
		info.setBounds(20,190,330,40);
		c.add(info);
		b1.addActionListener(new InsertListener());
		b2.addActionListener(new DeleteListener());
		b3.addActionListener(new UpdateListener());
		b4.addActionListener(new ShowListener());
		exit.addActionListener(new ExitListener());
		setVisible(true);
		getConnection();
	}
	void getConnection()
	{
		try
		{
						// Type one

		//	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");	
		//	String url="jdbc:odbc:kamal";
		//	con=DriverManager.getConnection(url,"scott","tiger");

						// Type four

		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		//con=DriverManager.getConnection("jdbc:oracle:thin:@SANTOSH-PC:1521:XE","santosh","santosh");
		con=DriverManager.getConnection("jdbc:oracle:thin:@SANTOSH:1521:XE","santosh","santosh");

			info.setText("Connection is established with the database");

			insertps=con.prepareStatement("insert into employee values(?,?,?)");
			updateps=con.prepareStatement("update employee set name=?,salary=? where empno=?");
			deleteps=con.prepareStatement("delete from employee where empno=?");
			selectps=con.prepareStatement("select * from employee where empno=?");
		}
		catch(SQLException e)
		{
			info.setText("unnable to get connected to the database");
		}
		catch (Exception e)
		{
			System.out.println("Driver class not found.....");
			System.out.println(e);
		}
	}
// ***********************************************************************

class InsertListener implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			int empno=Integer.parseInt(t1.getText());
			String name=t2.getText();
			float salary=Float.parseFloat(t3.getText());
			insertps.setInt(1,empno);
			insertps.setString(2,name);
			insertps.setFloat(3,salary);

			insertps.executeUpdate();   // to insert 

			info.setText("One Row Successfully Inserted");
			insertps.clearParameters();
			t1.setText("");
			t2.setText("");
			t3.setText("");
		}
		catch (SQLException se)
		{
			info.setText("Failed to insert Record.....");
		}
		catch(Exception de)
		{
			info.setText("Enter proper data before insertion...");
		}
	}
}
// ********************************************************************

class DeleteListener implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			int empno=Integer.parseInt(t1.getText());
			deleteps.setInt(1,empno);

			deleteps.executeUpdate(); //delete

			deleteps.clearParameters();
			info.setText("One Row Deleted Successfully");
			t1.setText("");
			t2.setText("");
			t3.setText("");
		}
		catch (SQLException se)
		{
			info.setText("Failed To Delete Record.....");
		}
		catch(Exception de)
		{
			info.setText("Enter proper data before deletion...");
		}
	}
}
//**********************************************************************

class UpdateListener implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			int empno=Integer.parseInt(t1.getText());
			String name=t2.getText();
			float salary=Float.parseFloat(t3.getText());
			updateps.setInt(3,empno);
			updateps.setString(1,name);
			updateps.setFloat(2,salary);

			updateps.executeUpdate(); // update

			info.setText("One Row Updated Successfully ");
			insertps.clearParameters();
			t1.setText("");
			t2.setText("");
			t3.setText("");
		}
		catch (SQLException se)
		{
			System.out.println(se);
			info.setText("Failed to update Record.....");
		}
		catch(Exception de)
		{
			System.out.println(de);
			info.setText("Enter proper data before selecting update...");
		}
	}
}
//**********************************************************************

class ShowListener implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			int empno=Integer.parseInt(t1.getText());
			selectps.setInt(1,empno);

			selectps.execute();    // will execute query (select query)

			ResultSet rs=selectps.getResultSet();
			rs.next();
			t2.setText(rs.getString(2));
			t3.setText(" "+rs.getFloat(3));

			info.setText("One row displayed successfully");
			selectps.clearParameters();
		}
		catch (SQLException se)
		{
			info.setText("Failed To Show The Record....");
		}
		catch(Exception de)
		{
			info.setText("Enter proper Empno before Selecting show...");
		}
	}
}
//**********************************************************************

class ExitListener implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			insertps.close();
			deleteps.close();
			updateps.close();
			selectps.close();
			if(con!=null)
				con.close();
			System.exit(0);
		}
		catch (SQLException se)
		{
			System.out.println(se);
		}
	}
}
	public static void main(String [] args)
	{
		new EmployeeForm();
	}
}
