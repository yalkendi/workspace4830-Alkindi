
/**
 * @file InsertAlkindi.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/InsertAlkindi")
@WebServlet(name="InsertAlkindi",urlPatterns={"/InsertAlkindi"})
public class InsertAlkindi extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertAlkindi() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String name = request.getParameter("name");
      String ingredients = request.getParameter("ingredients");
      String how = request.getParameter("how");


      Connection connection = null;
      String insertSql = " INSERT INTO Recipe (id, NAME, INGREDIENTS, HOW) values (default, ?, ?, ?)";

      try {
         DBConnectionAlkindi.getDBConnection(getServletContext());
         connection = DBConnectionAlkindi.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, name);
         preparedStmt.setString(2, ingredients);
         preparedStmt.setString(3, how);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>User Name</b>: " + name + "\n" + //
            "  <li><b>Email</b>: " + ingredients + "\n" + //
            "  <li><b>Phone</b>: " + how + "\n" + //


            "</ul>\n");

      out.println("<a href=/webproject-git/InsertAlkindi.html>Add more data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
