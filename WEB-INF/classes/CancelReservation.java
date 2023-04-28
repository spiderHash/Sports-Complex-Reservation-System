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

public class CancelReservation extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private Connection conn;
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int reservation_id = Integer.parseInt(request.getParameter("reservation_id"));
           
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

            String sql2 = "UPDATE court_slots c SET c.is_available = true where c.slot_id = (select r.slot_id from reservations r where r.reservation_id = ?)";
            PreparedStatement stmt2 = conn.prepareStatement(sql2);
            stmt2.setInt(1, reservation_id);
            int rowsAffected2 = stmt2.executeUpdate();

            String sql = "DELETE from reservations WHERE reservation_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, reservation_id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected==0 || rowsAffected2==0) {
                out.println("<h1>Error</h1>");
                return;
                }
                else{                    
                out.println("<h1>Success</h1>");
                response.sendRedirect("ReservationHistory");
                }  


            }
            catch (SQLException e) {
            e.printStackTrace();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
       


}