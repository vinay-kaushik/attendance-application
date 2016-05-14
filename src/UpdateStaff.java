import javax.servlet.http.*;

import java.io.*;
import java.sql.*;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
@SuppressWarnings("serial")
public class UpdateStaff extends HttpServlet
{
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException
	{
		Enumeration<String> enu=req.getParameterNames();
		String password="",email,mobile,name;
		HttpSession hs=req.getSession();
		String username=(String) hs.getAttribute("username");

		int id;
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		try{
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1490");
				String temp=enu.nextElement();
				int flag=0;
				if(temp.equals("password"))
				{
					password=req.getParameter(temp);
					name=req.getParameter(enu.nextElement());
					mobile=req.getParameter(enu.nextElement());
					id=Integer.parseInt(req.getParameter(enu.nextElement()));
					email=req.getParameter(enu.nextElement());

					flag=1;
				}
				else
				{
					name=req.getParameter(temp);
					mobile=req.getParameter(enu.nextElement());
					id=Integer.parseInt(req.getParameter(enu.nextElement()));
					email=req.getParameter(enu.nextElement());
				}
				String sql;
				PreparedStatement stmt;
				if(flag==1)
				{
					sql="update staff set name=? , mobile=? , emailid=? , id=? , password=? where username=?;";
					stmt=conn.prepareStatement(sql);
					stmt.setString(5, password);
					stmt.setString(6, username);
				}
				else
				{
					sql="update staff set name=? , mobile=? , emailid=? , id=? where username=?;";
					stmt=conn.prepareStatement(sql);
					stmt.setString(5, username);
				}
				stmt.setString(1, name);
				stmt.setString(2, mobile);
				stmt.setString(3, email);
				stmt.setInt(4, id);			
				int rows=stmt.executeUpdate();
				
				if(rows!=1)
				{
					out.println("Error while Updating Your info..! sorry..! :(");
				}
				RequestDispatcher rd=req.getRequestDispatcher("/stafflogin.html");
			    rd.include(req, res);
			    out.println("<center><h4>Successfully updated Staff details..! :)</h4></center>");
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