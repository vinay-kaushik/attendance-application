import javax.servlet.http.*;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
@SuppressWarnings("serial")
public class IntermediateUpdateStaff extends HttpServlet
{
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException
	{
		res.setContentType("text/html");
		HttpSession hs=req.getSession();
		String username=(String) hs.getAttribute("username");

		PrintWriter out=res.getWriter();
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1490");
			
			
			String sql="select * from staff where username=?;";
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setString(1,username);
			ResultSet rs=stmt.executeQuery();
			
			if(rs.next())
			{
				RequestDispatcher rd=req.getRequestDispatcher("/updatestaff.html");
			    rd.include(req, res);
			    out.println("\n");
			    out.println("var id="+rs.getInt("id")+";document.getElementById('id').value=id;");
			    out.println("var nameid='"+rs.getString("name")+"';document.getElementById('nameid').value=nameid;");
			    out.println("var mobileid='"+rs.getString("mobile")+"';document.getElementById('mobileid').value=mobileid;");
			    out.println("var emailid='"+rs.getString("emailid")+"';document.getElementById('emailid').value=emailid;");
			    out.println("</script> </html>");
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