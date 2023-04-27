import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Block extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private Connection conn;
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String courtName = request.getParameter("courtName");
        boolean block = Boolean.parseBoolean(request.getParameter("block"));
        boolean is_admin = (boolean) request.getSession().getAttribute("is_admin");

       
           
        response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><link rel=\"stylesheet\" type=\"text/css\" href=\"/sports/styles.css\"><body>");

             if(!is_admin)
                {
                    sendErrorResponse(out,"NOT ADMIN");
                    return;
                }

        try {
              Class.forName("com.mysql.jdbc.Driver");
              String jdbcUrl = "jdbc:mysql://localhost:3306/sports"; // Replace with your database URL
              String dbUsername = "ssn"; // Replace with your database username
              String dbPassword = "ssn"; // Replace with your database password
                      
            // Establish database connection
            conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String sql = "UPDATE courts SET is_blocked = ? where court_name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setBoolean(1, block);
            stmt.setString(2, courtName);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected==0) {
                sendErrorResponse(out,"Failed to block/unblock");
                return;
                }

                response.sendRedirect("dashboard.html");
            } 
            
            
        catch (SQLException e) {
            e.printStackTrace();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
       
        void sendErrorResponse(PrintWriter out, String message)
        {
                out.println("<h2>"+message+"</h2>");

            out.println("<h3><a href='dashboard.html'>Go to Dashboard</a></h3>");
        }

}