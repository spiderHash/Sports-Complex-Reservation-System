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

public class ReservationHistory extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private Connection conn;
    
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/sports";
            String username = "ssn";
            String password = "ssn";
            conn = DriverManager.getConnection(dbURL, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><link rel=\"stylesheet\" type=\"text/css\" href=\"/sports/styles.css\"><title>Reservation History</title></head>");
        out.println("<body>");
        out.println("<h1>Reservation History</h1>");

        try  {
            int userId = (Integer) request.getSession().getAttribute("userId");
            boolean is_admin = (boolean) request.getSession().getAttribute("is_admin");
            
            PreparedStatement stmt ;
            
            if(is_admin){
            stmt= conn.prepareStatement(
                "SELECT u.username, c.court_name, s.slot_date, s.slot_start_time, s.slot_end_time " +
                "FROM reservations r  " +
                "JOIN users u ON r.user_id = u.user_id " +
                "JOIN court_slots s ON r.slot_id = s.slot_id " +
                "JOIN courts c ON s.court_id = c.court_id " + 
                " ORDER BY s.slot_date DESC, s.slot_start_time DESC");
            }
            else{
            stmt= conn.prepareStatement( "SELECT u.username, c.court_name, s.slot_date, s.slot_start_time, s.slot_end_time " +
                "FROM reservations r  " +
                "JOIN users u ON r.user_id = u.user_id " +
                "JOIN court_slots s ON r.slot_id = s.slot_id " +
                "JOIN courts c ON s.court_id = c.court_id " + 
                "where r.user_id = " +userId + " ORDER BY s.slot_date DESC, s.slot_start_time DESC"); 
            }
            ResultSet rs = stmt.executeQuery();

            out.println("<table>");
            out.println("<tr><th>User</th><th>Court</th><th>Date</th><th>Start Time</th><th>End Time</th></tr>");
            while (rs.next()) {
                String username = rs.getString("username");
                String courtName = rs.getString("court_name");
                String date = rs.getString("slot_date");
                String startTime = rs.getString("slot_start_time");
                String endTime = rs.getString("slot_end_time");
                out.println("<tr><td>" + username + "</td><td>" + courtName + "</td><td>" + date + "</td><td>" + startTime + "</td><td>" + endTime + "</td></tr>");
            }
            out.println("</table>");

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error retrieving reservation history.</p>");
        }


        out.println("<h3><a href='dashboard.html'>Go to Dashboard</a></h3>");
        out.println("</body>");
        out.println("</html>");
    }
}