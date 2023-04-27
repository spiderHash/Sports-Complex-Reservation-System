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

public class ReserveSlot extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private Connection conn;
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int slotId = Integer.parseInt(request.getParameter("slotId"));
        int userId = (Integer) request.getSession().getAttribute("userId");
           
        response.setContentType("text/xml");
            PrintWriter out = response.getWriter();
            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");


        try {
              Class.forName("com.mysql.jdbc.Driver");
              String jdbcUrl = "jdbc:mysql://localhost:3306/sports"; // Replace with your database URL
              String dbUsername = "ssn"; // Replace with your database username
              String dbPassword = "ssn"; // Replace with your database password
                      
            // Establish database connection
            conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String checkSlotSql = "SELECT is_available FROM court_slots WHERE slot_id = ?";
            PreparedStatement checkSlotStmt = conn.prepareStatement(checkSlotSql);
            checkSlotStmt.setInt(1, slotId);
            ResultSet checkSlotRs = checkSlotStmt.executeQuery();
            if (checkSlotRs.next()) {
                boolean isAvailable = checkSlotRs.getBoolean("is_available");
                if (!isAvailable) {
                    sendErrorResponse(out,"Slot is already reserved.");
                    return;
                }
            } else {
                sendErrorResponse(out,"Invalid slot ID.");
                return;
            }
            checkSlotRs.close();
            checkSlotStmt.close();
            
            // Reserve the slot
            String reserveSlotSql = "UPDATE court_slots SET is_available = false WHERE slot_id = ?";
            PreparedStatement reserveSlotStmt = conn.prepareStatement(reserveSlotSql);
            reserveSlotStmt.setInt(1, slotId);
            int rowsAffected = reserveSlotStmt.executeUpdate();
            
            if (rowsAffected == 1) {
            
             // Reserve the slot
            String createReservationSql = "INSERT INTO reservations (user_id, slot_id) VALUES (?,?)";
            PreparedStatement createReservationStmt = conn.prepareStatement(createReservationSql);
           
            createReservationStmt.setInt(1, userId);
            createReservationStmt.setInt(2, slotId);

            int rowsA = createReservationStmt.executeUpdate();
                 if (rowsA == 1) {
                      out.println("<success>true</success>");
                 }

                 else{
                       sendErrorResponse(out,"failed");
                       return;
                 }
           
            }

            else{
               sendErrorResponse(out,"failed");
               return;
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
                out.println("<success>false</success>");
                out.println("<message>"+message+"</message>");
        }

}