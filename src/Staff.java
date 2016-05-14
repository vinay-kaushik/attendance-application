import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Staff")
public class Staff extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Staff() 
    {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		String branch=req.getParameter("branch");
		String year=req.getParameter("year");
		ServletContext context=getServletContext();
		context.setAttribute("branch", branch);
		context.setAttribute("year", year);
		String today=req.getParameter("date");
		
		int numberofhours=Integer.parseInt(req.getParameter("numberofhours"));
		String starthour=req.getParameter("starthour");
		
		PrintWriter out=res.getWriter();
		
		res.setContentType("text/html");

		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1490");
	
			String sql="select rollnumber from students where branch=? and year=?";
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setString(1, branch);
			stmt.setString(2, year);
			ResultSet rs=stmt.executeQuery();
			
			RequestDispatcher rd=req.getRequestDispatcher("/sample.html");
			rd.include(req, res);
			out.println("<p  name='"+branch+"'> Branch :"+branch+"</p>");
			out.println("</div> <div class='form-row'>");
			out.println("<p  name='"+year+"'> Year :"+year+"</p>");
			out.println("</div> <div class='form-row'>");
			out.println("<p  name='starthour'> Start Hour :"+starthour+"</p>");
			out.println("</div> <div class='form-row'>");
			out.println("<input type='hidden' name='starthour' value='"+starthour+"'>");
			out.println("<p  name='numberofhours'> Number of Hours :"+numberofhours+"</p>");
			out.println("</div> <div class='form-row'>");
			out.println("<input type='hidden'  name='numberofhours' value='"+numberofhours+"'>");
			out.println("<input type='hidden'  name='date' value='"+today+"'>");
			
			
			out.println("<h3> UnCheck if absent </h3>");
			while(rs.next()) 
			{
				String roll=rs.getString(1);
				out.println("</div> <div class='form-row'>");
				out.println(roll+"<input type='checkbox' name='"+roll+"' checked>"+"<br>");
			}
			out.println("<br><input type='submit' value='submit'> </form>");
			out.println("</div> </form> </div> </body>");
		}
		catch(SQLException se){
      		se.printStackTrace();
   		}catch(Exception e){
      		e.printStackTrace();
   		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
