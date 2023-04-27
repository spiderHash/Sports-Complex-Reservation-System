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

public class Grievance extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
       
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = (Integer) request.getSession().getAttribute("userId");
        String grievance = request.getParameter("grievance");
        Connection conn;       
           
        response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"/sports/styles.css\"></head><body>");

                    
        try {
              Class.forName("com.mysql.jdbc.Driver");
              String jdbcUrl = "jdbc:mysql://localhost:3306/sports"; // Replace with your database URL
              String dbUsername = "ssn"; // Replace with your database username
              String dbPassword = "ssn"; // Replace with your database password
                    
            // Establish database connection
            conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String sql = "INSERT INTO grievances (user_id, grievance) values (?,?);";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, grievance);

             

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected==0) {
                sendErrorResponse(out,"Failed to submit grievance");
                
                }
                else{
                    out.println("<h2>Grievance Submitted successful</h2>");
                    out.println("<a href='dashboard.html'>Go to Dashboard</a>");
                }

            
                
            } 
            
            
        catch (SQLException e) {
            out.println(e);
        } catch(Exception e)
        {
             out.println(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        Connection conn;       
           
        response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body><link rel=\"stylesheet\" type=\"text/css\" href=\"/sports/styles.css\">");

                    
        try {
              Class.forName("com.mysql.jdbc.Driver");
              String jdbcUrl = "jdbc:mysql://localhost:3306/sports"; // Replace with your database URL
              String dbUsername = "ssn"; // Replace with your database username
              String dbPassword = "ssn"; // Replace with your database password
                    
            // Establish database connection
            conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String sql = "select * from grievances";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            response.setContentType("text/html");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Id</th>");
            out.println("<th>Date & Time</th>");
            out.println("<th>User Id</th>");
            out.println("<th>Grievance</th>");
          //  out.println("<th>Status</th>");
           // out.println("<th></th>");
            out.println("</tr>");

            while (rs.next()) {
                int grievanceId = rs.getInt("grievance_id");
                String grievanceDate = rs.getString("grievance_date");
                String userId = rs.getString("user_id");
                String grievance = rs.getString("grievance");
                boolean isResolved = rs.getBoolean("is_resolved");
                
                out.println("<tr>");
                out.println("<td>" + grievanceId + "</td>");
                out.println("<td>" + grievanceDate + "</td>");
                out.println("<td>" + userId + "</td>");
                out.println("<td>" + grievance + "</td>");
               // out.println("<td>" + (isResolved? "Resolved" : "Not Resolved") + "</td>");
                //out.println("<td>" + (isResolved? "</td>" : "<button onclick=\"hello()\">" + "Mark Resolved" + "</button></td>" ));
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<h3><a href='dashboard.html'>Go to Dashboard</a></h3>");
            out.println(" </body></html>");
            
            rs.close();
            ps.close();
            
                
            } 
            
            
        catch (SQLException e) {
            out.println(e);
        } catch(Exception e)
        {
             out.println(e);
        }
    }

    
        void sendErrorResponse(PrintWriter out, String message)
        {
                out.println("<h2>"+message+"</h2>");
        }

}