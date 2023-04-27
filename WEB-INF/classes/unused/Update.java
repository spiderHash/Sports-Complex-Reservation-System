import java.io.*;
import java.lang.Thread.State;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
 
public class Update extends HttpServlet{
		// JDBC driver name and database URL
      static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
      static final String DB_URL="jdbc:mysql://localhost:3306/test";

      //  Database credentials
      static final String USER = "root";
      static final String PASS = "ssn@123";
	  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
   
		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
           
		try {
			// Register JDBC driver
			//Class.forName("com.mysql.cj.jdbc.Driver");
			Class.forName(JDBC_DRIVER);
         
			// Open a connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	
			// Execute SQL query 

            Statement s = conn.createStatement();
            String sql = "select address from patient where id = " + request.getParameter("id") + ";";
            ResultSet rs = s.executeQuery(sql);
            String add = "";
			while (rs.next()) {
                add = rs.getString("address");
            }

            PreparedStatement st = conn
                   .prepareStatement("update patient set address = \"" + request.getParameter("address") + "\" where id = "  + request.getParameter("id") +  ";");
			st.executeUpdate();
  
            // Close all the connections
            st.close();
            conn.close();
  
            // Get a writer pointer 
            // to display the successful result
            if (add != request.getParameter("address"))
                out.println("<html><body><b>Successfully Updated"
                        + "</b></body></html>");
            else {
                out.println("<html><body><b>Address already present"
                        + "</b></body></html>");
            }
            out.println("<br><br><a href = \"http://localhost:8080/PMS/DatabaseAccess\">VIEW</a>");
        }
        catch (Exception e) {
            e.printStackTrace();
            out.println(e);
        }
    }
} 