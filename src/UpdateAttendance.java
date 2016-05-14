import java.awt.geom.CubicCurve2D;
import java.io.*;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateAttendance")
public class UpdateAttendance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateAttendance() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		ServletContext context=getServletContext();
		PrintWriter out=res.getWriter();
		String year=(String)context.getAttribute("year");
		String branch=(String)context.getAttribute("branch");
		
		String today=req.getParameter("date");
		int numberofhours=Integer.parseInt(req.getParameter("numberofhours"));
		String starthour=req.getParameter("starthour");

		res.setContentType("text/html");
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1490");
	
			String sql="select rollnumber,conducted,attended,percent from students where branch=? and year=?";
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setString(1, branch);
			stmt.setString(2, year);
			ResultSet rs=stmt.executeQuery();
			while(rs.next())
			{
				String rollnumber=rs.getString(1);
				int conducted=rs.getInt(2);
				int attended=rs.getInt(3);
				double percent=rs.getDouble(4);
				conducted+=2;
				String check=req.getParameter(rollnumber);
				if(check!=null)
				{
					attended+=2;
				}
				percent=(attended*100.00)/conducted;
				
				sql="update students set conducted=? , attended=? , percent=? where rollnumber=?;";
				stmt=conn.prepareStatement(sql);
				stmt.setInt(1, conducted);
				stmt.setInt(2, attended);
				stmt.setDouble(3, percent);
				stmt.setString(4, rollnumber);
				stmt.executeUpdate();
							
//				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//			String d=dateFormat.format(new Date());
				rollnumber="R"+rollnumber;
				sql="select date from "+rollnumber+" where date=?";
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, today);
					
				ResultSet rs1=stmt.executeQuery();
				if(!rs1.next())
				{
					sql="insert into "+rollnumber+" values( ? , 0 , 0 , 0 , 0 , 0, 0, 0 );";
					stmt=conn.prepareStatement(sql);
					stmt.setString(1, today);
					int rows=stmt.executeUpdate();
					if(rows!=1)
					{
						out.println("Error updating attendance..! Sorry..! :(");
					}
				}
				String[] part = starthour.split("(?<=\\D)(?=\\d)");
				String h=part[0];
				int temp=Integer.parseInt(part[1]);
				while(numberofhours>0)
				{
					sql="update "+rollnumber+" set "+h+temp+" =1 where date=?;";
					temp++;
					stmt=conn.prepareStatement(sql);
					stmt.setString(1, today);
					int rows=stmt.executeUpdate();
					if(rows!=1)
					{
						out.println("Error updating attendance..! Sorry..! :(");
					}
					numberofhours--;
				}
					
			
			}
			RequestDispatcher rd=req.getRequestDispatcher("stafflogin.html");
		    rd.include(req, res);
		}
		catch(SQLException se){
      		se.printStackTrace();
   		}catch(Exception e){
      		e.printStackTrace();
   		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
