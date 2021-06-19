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
 * @author Eugene Garcia
 */
public class UpdateGoals extends HttpServlet {

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
            out.println("<title>Servlet UpdateGoals</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateGoals at " + request.getContextPath() + "</h1>");
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

            Connection connection = DriverManager.getConnection(dbURL, username, password);
            Statement statement = connection.createStatement();
            
            String resetTable = "UPDATE PLAYER set goals = 0;";
            statement.executeUpdate(resetTable);

            String myQuery = "SELECT * FROM GOAL;";
            ResultSet goalsScored = statement.executeQuery(myQuery);
            

            while (goalsScored.next()) {
                String comment = goalsScored.getString("comment");
                
                if (comment.isEmpty()) {
                     String player = Integer.toString(goalsScored.getInt("player"));

                    Statement statement2 = connection.createStatement();

                    String getGoalsQuery = "SELECT * FROM PLAYER where playerID = " + player + ";";
                    ResultSet getGoals = statement2.executeQuery(getGoalsQuery);
                    getGoals.next();

                    int playerGoals = getGoals.getInt("goals");

                    playerGoals += 1;

                    String updatePlayerGoal = "UPDATE PLAYER set goals = " + playerGoals + " WHERE playerid = " + player + ";";
                    statement2.executeUpdate(updatePlayerGoal);
                }
            }
            
            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        
                response.sendRedirect("./");
        //request.getRequestDispatcher("/topscorers.jsp").forward(request, response);
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
    }// </editor-fold>

}
