import javax.servlet.http.*;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
@SuppressWarnings("serial")
public class AddStaff extends HttpServlet
{
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException
	{
		String name=req.getParameter("name");
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		String email=req.getParameter("email");
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1490");
	
			String sql="insert ignore into staff values (?,?,?,?,?,?);";
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setInt(1, -1);
			stmt.setString(2, name);
			stmt.setString(3, "0000000000");
			stmt.setString(4, username);
			stmt.setString(5, password);
			stmt.setString(6, email);			
			int rows=stmt.executeUpdate();
			
			if(rows!=1)
			{
				out.println("Error while adding staff..! sorry..!");
			}
			RequestDispatcher rd=req.getRequestDispatcher("/adminhomepage.html");
		    rd.include(req, res);										
			out.println("<center><h4>Successfully added Staff details..! :)</h4></center>");

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