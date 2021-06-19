package controller;

import dao.PlayerDao;
import dao.UserDao;
import util.Database;
import model.Player;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.User;

/**
 * This servlet accepts requests regarding soccer player interactions. It
 * currently supports searching, updating, adding and deleting players.
 *
 * @author Michael Malett
 */
@WebServlet(name = "PlayerController", urlPatterns = {"/PlayerController"})
public class PlayerController extends HttpServlet {

    private final PlayerDao dao;
    private final UserDao userDao;
    public HttpSession session;

    public PlayerController() {
        super();
        dao = new PlayerDao();
        userDao = new UserDao();
    }

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
        session = request.getSession();
        String op = request.getParameter("operation");
        User user;
        try {
            user = (User) session.getAttribute("user");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage() + "\nIt is likely the session expired");
            response.sendRedirect("./");
            return;
        }

        // search players
        if (op.equals("search")) {
            ArrayList<Player> players = dao.listPlayers(request.getParameter("teamFilter"), request.getParameter("playerName"));
            if (session.getAttribute("players") != null) {
                session.removeAttribute("players");
            }
            if (session.getAttribute("currentTab") != null) {
                session.removeAttribute("currentTab");
            }
            session.setAttribute("currentTab", "modifyPlayer");
            session.setAttribute("players", players);
        } // change player details
        else if (op.equals("update")) {
            // call update code
            String feedback = null;
            if (session.getAttribute("modifyPlayerFeedback") != null) {
                session.removeAttribute("modifyPlayerFeedback");
            }
            ArrayList<Player> players = (ArrayList<Player>) session.getAttribute("players");
            Player player = null;
            for (Player p : players) {
                if (p.getPlayerID() == Integer.parseInt(request.getParameter("playerID"))) {
                    player = p;
                }
            }
            if (player == null) {
                return;
            }

            if (userDao.verifyAccess(user, UserDao.UserAccessType.CAPTAIN)) {
                String nn = player.getNickName();
                player.setNickName((String) request.getParameter("nickName"));
                if (!nn.equals(player.getNickName())) {

                    feedback = player.getPlayerName() + "'s nick name has been altered";
                } else {
                    feedback = "No change processed";
                }
            }
            if (userDao.verifyAccess(user, UserDao.UserAccessType.REF)) {
                String captain;
                if ((captain = request.getParameter("captain")) != null) {
                    player.setIsCaptain(captain.equals("yes"));
                } else {
                    player.setIsCaptain(false);
                }
                player.setPlayerName((String) request.getParameter("playerName"));
                player.setTeamID(Integer.parseInt(request.getParameter("teamID")));
                feedback = "The requested change has been applied to " + player.getPlayerName();
            }
            // change the database entry for the player accordingly
            player = dao.modifyPlayer(player);

            // place the correct object back into the array in the same position
            for (int i = 0; i < players.size(); i++) {
                System.out.println(player.getPlayerID());
                if (players.get(i).getPlayerID() == player.getPlayerID()) {
                    players.remove(i);
                    players.set(i, player);
                    break;
                }
            }
            if (feedback != null) {
                session.setAttribute("modifyPlayerFeedback", feedback);
            }

        } else if (op.equals("add")) {
            if (session.getAttribute("currentTab") != null) {
                session.removeAttribute("currentTab");
            }
            session.setAttribute("currentTab", "addPlayer");

            String feedback = null;
            if (session.getAttribute("modifyPlayerFeedback") != null) {
                session.removeAttribute("modifyPlayerFeedback");
            }

            if (userDao.verifyAccess(user, UserDao.UserAccessType.REF)) {
                Player player = new Player();
                int goals;
                String s = (String) request.getParameter("goals");
                try {
                    if (s != null && !s.equals("")) {
                        goals = Integer.parseInt(request.getParameter("goals"));
                    } else {
                        goals = 0;
                    }
                } catch (NumberFormatException e) {
                    goals = 0;
                }

                player.setPlayerName((String) request.getParameter("playerName"));
                player.setTeamID(Integer.parseInt(request.getParameter("teamID")));
                player.setGoals(goals);
                player.setIsCaptain(((String) request.getParameter("captain")).equals("true"));
                player.setComment((String) request.getParameter("comment"));

                if (player.getPlayerName() == null || player.getPlayerName().equals("")) {
                    response.sendRedirect("./");
                    return;
                }

                feedback = dao.addNewPlayer(player);
            } else {
                feedback = "You do not have privileges to add a player.";
            }
            session.setAttribute("addPlayerFeedback", feedback);

        } else if (op.equals("delete") && userDao.verifyAccess(user, UserDao.UserAccessType.REF)) {
            ArrayList<Player> players = (ArrayList<Player>) session.getAttribute("players");
            Player player = null;
            for (Player p : players) {
                if (p.getPlayerID() == Integer.parseInt(request.getParameter("playerID"))) {
                    player = p;
                }
            }
            if (player == null) {
                return;
            }
            //dao.deletePlayer(player.getPlayerID());

        }
        response.sendRedirect("./");
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
        processRequest(request, response);
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
