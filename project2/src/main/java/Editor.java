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

import java.text.SimpleDateFormat;
import java.io.PrintWriter;

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
        //load jdbc driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
            return;
        }
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
        Connection c = null;
        Statement  s = null;
        ResultSet rs = null;

        try {

            //*************************************************
            //******************* Set Up **********************
            //*************************************************
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/CS144", "cs144", "");
            s = c.createStatement() ;
            String act = request.getParameter("action");
            String name = request.getParameter("username");
            String num = request.getParameter("postid");
            String title = request.getParameter("title");
            String body = request.getParameter("body");

            //*************************************************
            //************** Handle null Action ***************
            //*************************************************
            if (act == null) {
                //error message
                System.out.println("Need to select a valid action.");
            }

            //*************************************************
            //************** Handle Open Action ***************
            //*************************************************
            if (act.equals("open")) {
                if (name == null || num == null) {
                System.out.println("Invalid Paramaters, name and postid required.\n");
                }
                else if (title == null || body == null) {
                    rs = s.executeQuery("SELECT title, body FROM Posts Where username= '" + name + "' and postid= '" + num + "'");
                
                    while ( rs.next() ) {
                        title = rs.getString("title");
                        body = rs.getString("body");
                    }

                    request.setAttribute("title", title);
                    request.setAttribute("body", body);
                    request.getRequestDispatcher("/edit.jsp").forward(request, response);
                }
                else {
                    request.setAttribute("title", title);
                    request.setAttribute("body", body);
                    request.getRequestDispatcher("/edit.jsp").forward(request, response);
                }
            }

            //*************************************************
            //************** Handle Preview Action ************
            //*************************************************
            if (act.equals("preview")) {
                if (name == null || num == null ||  title == null || body == null) {
                    System.out.println("Invalid Paramaters, name, postid, title, body required.\n");
                }
                else {
                    request.getRequestDispatcher("/preview.jsp").forward(request, response);
                }
            }

            //*************************************************
            //************** Handle List Action ***************
            //*************************************************
            if (act.equals("list")) {
                if (name == null) {
                    System.out.println("Invalid Paramaters, name required.\n");
                }
                else {
                    doList(request, response, s);
                }
            }
        } //end try
        catch (SQLException ex) {
            //catch exceptions
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
            //close connections
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { s.close(); } catch (Exception e) { /* ignored */ }
            try { c.close(); } catch (Exception e) { /* ignored */ }
        }
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Connection c = null;
        Statement  s = null;
        ResultSet rs = null;

        try {
            //*************************************************
            //******************* Set Up **********************
            //*************************************************
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/CS144", "cs144", "");
            s = c.createStatement();
            String act = request.getParameter("action");
            String name = request.getParameter("username");
            String num = request.getParameter("postid");
            String title = request.getParameter("title");
            String body = request.getParameter("body");

            //*************************************************
            //************** Handle null Action ***************
            //*************************************************
            if (act == null) {
                System.out.println("Need to select a valid action.");
            }

            //*************************************************
            //************** Handle Save Action ***************
            //*************************************************
            if (act.equals("save")) {
                if (name == null || num == null ||  title == null || body == null) {
                    System.out.println("Invalid Paramaters, name, postid, title, body required.\n");
                }
                else {
                    Integer id = Integer.valueOf(num);
                    Date date = new Date();
                    Timestamp ts = new Timestamp(date.getTime());
                    if (id <= 0) {
                        rs = s.executeQuery("SELECT MAX(postid) FROM Posts WHERE username= '" + name + "'");

                        while ( rs.next() ) {
                            num = rs.getString("MAX(postid)");
                            if(num == null) num = "0";
                            id = Integer.valueOf(num);
                            id++;
                            num = Integer.toString(id);
                        }
                    
                        s.executeUpdate("INSERT INTO Posts Values('" + name + "', '" + num + "', '" + title + "', '" + body + "', '" + sdf.format(ts) + "', '" + sdf.format(ts) + "')");
                        doList(request, response, s);
                    }
                    else {
                    s.executeUpdate("UPDATE Posts SET body= '" + body + "', title= '" + title + "', modified= '" + sdf.format(ts) + "' WHERE username= '" + name + "' and postid= '" + id + "'");
                    doList(request, response, s);
                    }
                }
            }

            //*************************************************
            //************** Handle Delete Action ***************
            //*************************************************
            if (act.equals("delete")) {
                if (name == null || num == null) {
                    System.out.println("Invalid Paramaters, name and postid required.\n");
                }
                else {
                    s.executeUpdate("DELETE FROM Posts WHERE username= '" + name + "' and postid= '" + num + "'");
                    doList(request, response, s);
                }
            } 
        } //end try
        catch (SQLException ex) {
            //handle exceptions
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
            //close connections
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { s.close(); } catch (Exception e) { /* ignored */ }
            try { c.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    /**
     * Helper function to print list html
     */
    private void doList(HttpServletRequest request, HttpServletResponse response, Statement s)
        throws ServletException, IOException
    {
        //set up
        String name = request.getParameter("username");
        ResultSet rs = null;
        
        //send first half of page before table
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">");
        out.println("<head><title>Servlet Example</title></head>");
        out.println("<body class=\"w3-padding-16\">");
        out.println("<form action=\"post\" name=\"form1\" method=\"GET\" id=\"form1\">");
        out.println("<div><button class=\"w3-margin w3-btn w3-small w3-round-large w3-ripple w3-gray\" type=\"submit\" form=\"form1\" name=\"action\" value=\"open\">New Post</button></div>");
        out.println("<input type=\"hidden\" name=\"postid\" value=\"0\" >");
        out.println("<input type=\"hidden\" name=\"username\" value=" + name + " >");
        out.println("</form>");
        out.println("<div><table style=\"width:-webkit-fill-available;width: -moz-available;\" class=\"w3-table-all w3-card-4 w3-margin\"><tbody>");
        out.println("<tr><th>Title</th><th>Created</th><th>Modified</th><th>&nbsp</th></tr>");

        //start database data retrieval
        try {
            rs = s.executeQuery("SELECT title, modified, created, postid FROM Posts Where username= '" + name + "'");
            
            //print table
            while ( rs.next() ) {
                String title = rs.getString("title");
                String modified = rs.getString("modified");
                String created = rs.getString("created");
                String id = rs.getString("postid");
                //one row of data
                out.println("<tr><th>" + title + "</th><th>" + created + "</th><th>" + modified + "</th>");
                
                //begin buttons
                out.println("<th>");

                //open button
                out.println("<form action=\"post\" method=\"GET\" id=\"openform"+ id +"\">");
                out.println("<button style=\"margin:4px;\" class=\"w3-btn w3-small w3-round-large w3-ripple w3-gray\" type=\"submit\" form=\"openform"+ id +"\" name=\"action\" value=\"open\">Open</button>");
                out.println("<input type=\"hidden\" name=\"postid\" value=" + id + " >");
                out.println("<input type=\"hidden\" name=\"username\" value=" + name + " >");
                out.println("</form>");
                //delete button
                out.println("<form action=\"post\" method=\"POST\" id=\"deleteform"+ id +"\">");
                out.println("<button style=\"margin:4px;\" class=\"w3-btn w3-small w3-round-large w3-ripple w3-gray\" type=\"submit\" form=\"deleteform"+ id +"\" name=\"action\" value=\"delete\">Delete</button>");
                out.println("<input type=\"hidden\" name=\"postid\" value=" + id + " >");
                out.println("<input type=\"hidden\" name=\"username\" value=" + name + " >");
                out.println("</form>");

                out.println("</th>"); //end button
                out.println("</tr>"); //end table row
            }
            out.println("</tbody></table></div>"); //end table
            out.println("</html>"); //end html
        }
        catch (SQLException ex) {
            //catch exceptions
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
            //close connection
            try { rs.close(); } catch (Exception e) { /* ignored */ }
        }
    }
}
