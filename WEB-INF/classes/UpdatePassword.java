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

public class UpdatePassword extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private Connection conn;
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = (Integer) request.getSession().getAttribute("userId");
        String oldPassword = request.getParameter("old-password");
        String newPassword =  request.getParameter("new-password");

       
           
        response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");

        try {
              Class.forName("com.mysql.jdbc.Driver");
              String jdbcUrl = "jdbc:mysql://localhost:3306/sports"; // Replace with your database URL
              String dbUsername = "ssn"; // Replace with your database username
              String dbPassword = "ssn"; // Replace with your database password
                      
            // Establish database connection
            conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String sql = "UPDATE users SET password = ? where user_id = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);
            stmt.setString(3, oldPassword);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected==0) {
                sendErrorResponse(out,"Failed to update Password");
                return;
                }
                else{
                    out.println("<h2>Password update successful</h2><link rel=\"stylesheet\" type=\"text/css\" href=\"/sports/styles.css\">");
                    out.println("<h3><a href='dashboard.html'>Go to Dashboard</a></h3>");
                }

                
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
        }

}