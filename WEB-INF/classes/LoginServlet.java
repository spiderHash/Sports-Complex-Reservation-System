import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// @WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Example method to handle POST request for login form submission
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int userId=-1;
        boolean is_admin=false;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        // out.println("<html><h1>"+username+" "+password+"</h1></html>");

        // Perform database check for username and password
        boolean isValid = false;

         try{
        Class.forName("com.mysql.jdbc.Driver");
        String jdbcUrl = "jdbc:mysql://localhost:3306/sports"; // Replace with your database URL
        String dbUsername = "ssn"; // Replace with your database username
        String dbPassword = "ssn"; // Replace with your database password
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        
            // Establish database connection
            conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            // Prepare SQL query to check username and password
            String sql = "SELECT user_id,is_admin FROM users WHERE username = ? AND password = ?";
            //String sql = "SELECT * FROM users";

            stmt = conn.prepareStatement(sql);
             stmt.setString(1, username);
             stmt.setString(2, password);

            // Execute query and retrieve result set
            rs = stmt.executeQuery();

            // If result set has at least one row, then username and password are valid
            if (rs.next()) {

                userId = rs.getInt("user_id");
                is_admin = rs.getBoolean("is_admin");
                isValid = true;
            }

        } catch (Exception e) {
            out.println(e);
        } 
        


        if (isValid) {
            // Redirect to dashboard upon successful login
            request.getSession().setAttribute("userId", userId);
            request.getSession().setAttribute("username", username);
            request.getSession().setAttribute("is_admin", is_admin);
            response.sendRedirect("dashboard.html");
        } else {
            // Display error message on login failure
            
            out.println("<html>");
            out.println("<head><link rel=\"stylesheet\" type=\"text/css\" href=\"/sports/styles.css\">");
            out.println("<title>Login Error</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Login Failed</h1>");
            out.println("<p>Invalid username or password. Please try again.</p>");
            out.println("<a href='index.html'>Back to Login</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

}
