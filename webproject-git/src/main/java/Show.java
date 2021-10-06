import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Show")
public class Show extends HttpServlet {
   private static final long serialVersionUID = 2190L;

   public Show() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      String keyword2 = request.getParameter("keyword2");

      search(keyword,keyword2, response);
   }

   void search(String keyword,String keyword2, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Recipes";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnectionAlkindi.getDBConnection(getServletContext());
         connection = DBConnectionAlkindi.connection;
         
         //if (keyword.isEmpty() && keyword2.isEmpty()) 
         if (keyword.isEmpty()) 
         {
            String selectSQL = "SELECT * FROM Recipe";
            preparedStatement = connection.prepareStatement(selectSQL);
         }
           else if (!keyword.isEmpty())
         {
            String selectSQL = "SELECT * FROM Recipe WHERE NAME LIKE ?";
            String name = keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, name);
         }
           /* else if (!keyword2.isEmpty() && keyword.isEmpty())
	      {
	         String selectSQL = "SELECT * FROM Recipe WHERE PHONE LIKE ?";
	         String phone = keyword2 + "%";
	         preparedStatement = connection.prepareStatement(selectSQL);
	         preparedStatement.setString(1, phone);
	      }
	       else
	       {
		         String selectSQL = "SELECT * FROM Recipe WHERE EMAIL LIKE ? AND PHONE LIKE ?";
		         String phone = keyword2 + "%";
		         String email = keyword + "%";
		         preparedStatement = connection.prepareStatement(selectSQL);
		         preparedStatement.setString(1, email);
		         preparedStatement.setString(2, phone);
	       } */
         
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("NAME").trim();
            String ingredients = rs.getString("INGREDIENTS").trim();
            String how = rs.getString("HOW").trim();

            //if ((keyword.isEmpty() && keyword2.isEmpty()) || name.contains(keyword) || ingredients.contains(keyword2)) {
            if ((keyword.isEmpty()  || name.contains(keyword)))
            {
               //out.println("ID: " + id + ", ");
               out.println("Recipe Name: " + name + ":<br>");
               out.println("Ingredients: " + ingredients + "<br>");
               out.println("How: " + how + "<br><br>");
            }
         }
         out.println("<a href=/webproject-git/Show.html>Search Data</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
