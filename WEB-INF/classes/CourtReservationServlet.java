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

public class CourtReservationServlet extends HttpServlet {
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
        String courtName = request.getParameter("courtName");
        try {
            String sql = "SELECT s.slot_id, s.slot_date, s.slot_start_time, s.slot_end_time, s.is_available " 
                       + "FROM court_slots s JOIN courts c ON s.court_id = c.court_id WHERE c.court_NAME = ? AND c.is_blocked = false ORDER BY s.slot_date, s.slot_start_time";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, courtName);
            ResultSet rs = ps.executeQuery();
            
            response.setContentType("text/xml");
            PrintWriter out = response.getWriter();
            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<slots>");
            while (rs.next()) {
                int slotId = rs.getInt("slot_id");
                String slotDate = rs.getString("slot_date");
                String slotStartTime = rs.getString("slot_start_time");
                String slotEndTime = rs.getString("slot_end_time");
                boolean isAvailable = rs.getBoolean("is_available");
                
                out.println("<slot slotId=\"" + slotId + "\">");
                out.println("<slotTime>" + slotDate + " " + slotStartTime + " - " + slotEndTime + "</slotTime>");
                out.println("<slotStatus>" + (isAvailable ? "Available" : "Reserved") + "</slotStatus>");
                out.println("</slot>");
            }
            out.println("</slots>");
            
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    

}
