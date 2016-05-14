import javax.servlet.http.*;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
@SuppressWarnings("serial")
public class UpdateStudent extends HttpServlet
{
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException
	{
		String branch=req.getParameter("branch");
		String year=req.getParameter("year");

		String name=req.getParameter("name");
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		String rollnumber = req.getParameter("rollnumber");
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1490");
	
			String sql="update students set rollnumber=? , branch=? , year=? , username=? , password=? where rollnumber=?;";
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setString(1, rollnumber);
			stmt.setString(2, branch);
			stmt.setString(3, year);
			stmt.setString(4, username);
			stmt.setString(5, password);
			stmt.setString(6, rollnumber);
			
			int rows=stmt.executeUpdate();
			String table="create table if not exists R"+rollnumber+" (date Date,h1 int,h2 int,h3 int,h4 int,h5 int,h6 int,h7 int);";
			PreparedStatement tablestmt=conn.prepareStatement(table);
			tablestmt.executeUpdate();
			
			if(rows!=1)
			{
				out.println("Error while adding student..! sorry..!");
			}
			RequestDispatcher rd=req.getRequestDispatcher("/updatestudent.html");
		    rd.include(req, res);										
			out.println("<center><h4>Successfully updated Student details..! :)</h4></center>");

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