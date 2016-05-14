import javax.servlet.http.*;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
@SuppressWarnings("serial")
public class StudentServlet extends HttpServlet
{
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException
	{
		String username=req.getParameter("username");
		String p=req.getParameter("password1");
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		int flag=0;
		try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1490");

		String sql="select attended,conducted,percent,password from students where username=?;";
		PreparedStatement stmt=conn.prepareStatement(sql);
		stmt.setString(1, username);
		int attended=0,conducted=0;
		double percent=0.0;
		ResultSet rs=stmt.executeQuery();
		
		if(rs.next())
		{
			String b=rs.getString("password");
			attended=rs.getInt("attended");
			conducted=rs.getInt("conducted");
			percent=rs.getDouble("percent");
			
			if(b.equals(p))
			{
				flag=1;
				HttpSession hs=req.getSession(true);
				hs.setAttribute("username",username);
				hs.setMaxInactiveInterval(10*60);
			}
		}
		if(flag==0)
		{

				out.println("sorry enter correct details");
				RequestDispatcher rd=req.getRequestDispatcher("/studentlogin.html");
			    rd.include(req, res);
		}
		else
		{
			sql="select * from R"+username+";";
			stmt=conn.prepareStatement(sql);
			ResultSet rs1=stmt.executeQuery();
			RequestDispatcher rd=req.getRequestDispatcher("/table.html");
			rd.include(req, res);
				while(rs1.next())
				{
					Date d=rs1.getDate("date");
					int h1=rs1.getInt("h1");
					int h2=rs1.getInt("h2");
					int h3=rs1.getInt("h3");
					int h4=rs1.getInt("h4");	
					int h5=rs1.getInt("h5");
					int h6=rs1.getInt("h6");
					int h7=rs1.getInt("h7");
					out.println("<tr>");
					out.println("<td>"+d+"</td>");
					out.println("<td>"+h1+"</td>");
					out.println("<td>"+h2+"</td>");
					out.println("<td>"+h3+"</td>");
					out.println("<td>"+h4+"</td>");
					out.println("<td>"+h5+"</td>");
					out.println("<td>"+h6+"</td>");
					out.println("<td>"+h7+"</td>");
					out.println("</tr>");
				}
				out.println("</tbody></table></div></body>");
				out.println("<script>var str=\" <h3>Classes Attended : "+attended+"   Classes Conducted : "+conducted+"  Percentage : "+percent+"<br></h3>\";document.getElementById('totalconducted').innerHTML=str;</script>");
			
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
