import javax.servlet.http.*;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
@SuppressWarnings("serial")
public class AdminLoginServlet extends HttpServlet
{
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException
	{
		String n=req.getParameter("username");
		String p=req.getParameter("password1");
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		int flag=0;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1490");
	
			String sql="select password from admin where username=?;";
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setString(1, n);
			ResultSet rs=stmt.executeQuery();
			
			if(rs.next())
			{
				String b=rs.getString("password");
				if(b.equals(p))
				{
					flag=1;
					HttpSession hs=req.getSession(true);
					hs.setAttribute("name",n);
					hs.setMaxInactiveInterval(10*60);
					
				}
			}
			if(flag==0)
			{
				out.println("sorry enter correct details");
				RequestDispatcher rd=req.getRequestDispatcher("/adminlogin.html");
			    rd.include(req, res);
			}
			else
			{
				RequestDispatcher rd=req.getRequestDispatcher("/adminhomepage.html");
			    rd.include(req, res);			
			}
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