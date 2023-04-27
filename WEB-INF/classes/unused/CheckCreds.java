import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;



public class CheckCreds extends HttpServlet {
     static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
      static final String DB_URL="jdbc:mysql://localhost:3306/sports";

      //  Database credentials
      static final String USER = "root";
      static final String PASS = "ssn";
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

            
            String user = request.getParameter("user");
            String pass = request.getParameter("pass");
            
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
      



        try {

          pw.println("<h1>hiee</h1>");
	/*		// Register JDBC driver
			Class.forName(JDBC_DRIVER);
	
			// Open a connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
		
			// Execute SQL query
			Statement stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM sports.user";
			ResultSet rs = stmt.executeQuery(sql);

            boolean found=false;

            pw.println("<h1>hii</h1>");

            while(rs.next()){
				//Retrieve by column name
				pw.println("<h1>hi</h1>");
				String curUser = rs.getString("id");
                String curPass = rs.getString("pass");

                if(curUser.equals(user) && curPass.equals(pass)){
                    found=true;
                    RequestDispatcher rd = request.getRequestDispatcher("app.html");
                    rd.forward(request,response);
                }
            }

            
            if(!found){
                pw.println("<h1>Wrong username or password</h1>");
                RequestDispatcher rd = request.getRequestDispatcher("index.html");
                rd.include(request,response);
            }
*/
        }
        catch(Exception e) {
         //Handle errors for Class.forName
          pw.println("<h1>byee</h1>");
         e.printStackTrace();
		} 

            pw.close();
    }
}