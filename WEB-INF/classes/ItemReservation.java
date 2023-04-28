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

public class ItemReservation extends HttpServlet {
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
        String itemName = request.getParameter("itemName");
        try {
            String sql = "SELECT item_id,count from inventory where item_name = ? " ;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, itemName);
            ResultSet rs = ps.executeQuery();
            
            response.setContentType("text/xml");
            PrintWriter out = response.getWriter();
            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<items>");
            while (rs.next()) {
                int count = rs.getInt("count");
                int id = rs.getInt("item_id");
                out.println("<item>");
                out.println("<id>"+id+"</id>");
                out.println("<count>"+count+"</count>");
                out.println("</item>");
            }
            out.println("</items>");
            
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                String itemId = request.getParameter("itemId");
                int count = Integer.parseInt(request.getParameter("count"));

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

        try {
            
            String sql = "UPDATE inventory SET count = ? where item_id= ? " ;
            PreparedStatement ps = conn.prepareStatement(sql);
            if(count<0)
            {out.println("<h2>Count cannot be -ve</h2>"); return;}

            ps.setInt(1, count);
            ps.setString(2, itemId);

            int rs = ps.executeUpdate();

            if(rs>0)
            {
                out.println("<script>alert('Updated'); window.location.replace(\"dashboard.html\")</script>");              
            }

        }catch(Exception e)
        {
            out.println("<h2>"+e+"</h2>");
        }
    

            }}
