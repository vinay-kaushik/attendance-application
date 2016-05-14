import javax.servlet.http.*;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
@SuppressWarnings("serial")
public class AddClassOfStudents extends HttpServlet
{
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException
	{
		String branch=req.getParameter("branch");
		String year=req.getParameter("year");

		int numberofstudents=Integer.parseInt(req.getParameter("numberofstudents"));
		int startrollnumber=Integer.parseInt(req.getParameter("startrollnumber"));
		int endrollnumber=Integer.parseInt(req.getParameter("endrollnumber"));
		int temp=startrollnumber;
		
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1490");
			String sql="insert ignore into students values (?,?,?,?,?,?,?,?);",table="";
			PreparedStatement stmt=conn.prepareStatement(sql),tablestmt=null;
			
			for(;numberofstudents>0 || temp<=endrollnumber;numberofstudents--)
			{
				
				String rollnumber=""+temp;
				temp++;
				stmt.setString(1, rollnumber);
				stmt.setString(2, branch);
				stmt.setString(3, year);
				stmt.setInt(4, 0);
				stmt.setInt(5, 0);
				stmt.setDouble(6, 0.0);
				stmt.setString(7, rollnumber);
				stmt.setString(8, rollnumber);				
				stmt.addBatch();
				table="create table if not exists R"+rollnumber+" (date Date,h1 int,h2 int,h3 int,h4 int,h5 int,h6 int,h7 int);";
				tablestmt=conn.prepareStatement(table);
				tablestmt.executeUpdate();
				
			}
			
			stmt.executeBatch();
			
			RequestDispatcher rd=req.getRequestDispatcher("/addclassstudents.html");
		    rd.include(req, res);										
			out.println("<center><h4>Successfully Added class Details..! :)</h4></center>");

		}catch(SQLException se){
      		se.printStackTrace();
   		}catch(Exception e){
      		e.printStackTrace();
   		}
		
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException
	{
		doGet(req,res);
	}
}