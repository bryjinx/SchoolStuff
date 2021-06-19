package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author carlitos
 */
public class UpdateTeams extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateTeams</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateTeams at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        try {
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);

            String dbURL = "jdbc:mysql://localhost:3306/soccer";
            String username = "root";
            String password = "Carlitos!";
//            String username = "grime";
//            String password = "ok";

            Connection connection = DriverManager.getConnection(dbURL, username, password);
            Statement statement = connection.createStatement();

            String resetTable = "UPDATE TEAM SET played = 0, won = 0, draw = 0, lose = 0, gFavor = 0, gAgainst = 0, gDiff = 0, points = 0;";
            statement.executeUpdate(resetTable);

            String myQuery = "SELECT * FROM GAME WHERE played = 1 AND comment != 'BRACKET';";
            ResultSet games = statement.executeQuery(myQuery);


            while (games.next()) {
              String home = Integer.toString(games.getInt("home"));
              String away = Integer.toString(games.getInt("away"));
              int hg = games.getInt("homeGoals");
              int ag = games.getInt("awayGoals");

              Statement statement2 = connection.createStatement();
              Statement statement3 = connection.createStatement();

              String getHome = "SELECT * FROM TEAM where teamID = " +home+ ";";
              ResultSet homeTeam = statement2.executeQuery(getHome);
              homeTeam.next();
              String getAway = "SELECT * FROM TEAM where teamID = " +away+ ";";
              ResultSet awayTeam = statement3.executeQuery(getAway);
              awayTeam.next();

              int hp = homeTeam.getInt("played");
              int hw = homeTeam.getInt("won");
              int hd = homeTeam.getInt("draw");
              int hl = homeTeam.getInt("lose");
              int hf = homeTeam.getInt("gFavor");
              int ha = homeTeam.getInt("gAgainst");
              int hgd = homeTeam.getInt("gDiff");
              int hpts = homeTeam.getInt("points");

              int ap = awayTeam.getInt("played");
              int aw = awayTeam.getInt("won");
              int ad = awayTeam.getInt("draw");
              int al = awayTeam.getInt("lose");
              int af = awayTeam.getInt("gFavor");
              int aa = awayTeam.getInt("gAgainst");
              int agd = awayTeam.getInt("gDiff");
              int apts = awayTeam.getInt("points");

              if ( hg > ag ){
                hw += 1;
                al += 1;
                hpts += 3;
              }
              else if ( hg < ag ){
                hl += 1;
                aw += 1;
                apts += 3;
              }
              else{
                hd += 1;
                ad += 1;
                hpts += 1;
                apts += 1;
              }

              hp += 1;
              ap += 1;

              hf += hg;
              ha += ag;
              hgd += (hg-ag);

              af += ag;
              aa += hg;
              agd += (ag-hg);

              String updateHome = "UPDATE TEAM SET played = " + hp + ", won = " + hw + ", draw = " + hd + ", lose = " + hl + ", gFavor = " + hf + ", gAgainst = " + ha + ", gDiff = " + hgd + ", points = " + hpts + " WHERE teamID = " + home + ";";
              statement2.executeUpdate(updateHome);
              String updateAway = "UPDATE TEAM SET played = " + ap + ", won = " + aw + ", draw = " + ad + ", lose = " + al + ", gFavor = " + af + ", gAgainst = " + aa + ", gDiff = " + agd + ", points = " + apts + " WHERE teamID = " + away + ";";
              statement3.executeUpdate(updateAway);
            }

            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        
        response.sendRedirect("./");
        //request.getRequestDispatcher("/standings.jsp").forward(request, response);
        //processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
// </editor-fold>

}
