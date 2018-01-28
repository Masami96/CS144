import java.io.IOException;
import java.sql.* ;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 * Servlet implementation class for Servlet: ConfigurationTest
 *
 */
public class Editor extends HttpServlet {
    /**
     * The Servlet constructor
     *
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
    public Editor() {}

    public void init() throws ServletException
    {
      try {
          Class.forName("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException ex) {
          System.out.println(ex);
          return;
      }

        /*  write any servlet initialization code here or remove this function */
    }

    public void destroy()
    {
        /*  write any servlet cleanup code here or remove this function */
    }

    /**
     * Handles HTTP GET requests
     *
     * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
     *      HttpServletResponse response)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
            //request.getRequestDispatcher("/edit.jsp").forward(request, response);
          	// implement your GET method handling code here
            Connection c = null;
            Statement  s = null;
            ResultSet rs = null;

            try {
            /* create an instance of a Connection object */
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/CS144", "cs144", "");

            s = c.createStatement() ;

            String act = request.getParameter("action");

            String name = request.getParameter("username");
            String num = request.getParameter("postid");
            String title = request.getParameter("title");
            String body = request.getParameter("body");

            //open, save, delete, preview, list
            //GET: open, preview, list
            if (act == null){
              //error message
              System.out.println("Need to select a valid action.");
            }

            if (act.equals("open")){
              if (name == null || num == null){
                System.out.println("Invalid Paramaters, name and postid required.\n");
              }
              else if (title == null || body == null){
                rs = s.executeQuery("SELECT title, body FROM Posts Where username= '" + name + "' and postid= '" + num + "'");
                while (rs.next() ){
                  title = rs.getString("title");
                  body = rs.getString("body");
                }
                request.setAttribute("title", title);
                request.setAttribute("body", body);
                request.getRequestDispatcher("/edit.jsp").forward(request, response);
              }
              else {
                request.getRequestDispatcher("/edit.jsp").forward(request, response);
              }
            }
            if (act.equals("preview")){
              if (name == null || num == null ||  title == null || body == null){
                System.out.println("Invalid Paramaters, name, postid, title, body required.\n");
              }
              else {
                request.getRequestDispatcher("/preview.jsp").forward(request, response);
              }
            }
            if (act.equals("list")){
              if (name == null){
                System.out.println("Invalid Paramaters, name required.\n");
              }
              else {
                rs = s.executeQuery("SELECT title, modified, created FROM Posts Where username=" + name);
                while (rs.next() ){
                  title = rs.getString("title");
                  String modified = rs.getString("modified");
                  String created = rs.getString("created");
                }
                request.getRequestDispatcher("/list.jsp").forward(request, response);
              }
            }
          } //end try
         catch (SQLException ex){
            System.out.println("SQLException caught");
            System.out.println("---");
            while ( ex != null ) {
                System.out.println("Message   : " + ex.getMessage());
                System.out.println("SQLState  : " + ex.getSQLState());
                System.out.println("ErrorCode : " + ex.getErrorCode());
                System.out.println("---");
                ex = ex.getNextException();
            }
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { s.close(); } catch (Exception e) { /* ignored */ }
            try { c.close(); } catch (Exception e) { /* ignored */ }
        }
        //request.getRequestDispatcher("/edit.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests
     *
     * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
     *      HttpServletResponse response)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
	// implement your POST method handling code here
	// currently we simply show the page generated by "edit.jsp"
        request.getRequestDispatcher("/edit.jsp").forward(request, response);
    }
}
