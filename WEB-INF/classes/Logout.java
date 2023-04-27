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
public class Logout extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Example method to handle POST request for login form submission
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        

            response.setContentType("text/html");
            request.getSession().invalidate();
            response.sendRedirect("index.html");
        
    }

}
