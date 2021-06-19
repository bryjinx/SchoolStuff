package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import dao.UserDao;
import java.util.Enumeration;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Michael Malett
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    private UserDao dao;
    public HttpSession session;

    public UserController() {
        super();
        dao = new UserDao();
    }

    public static void printSession(HttpSession session) {
        Enumeration e = (Enumeration) (session.getAttributeNames());

        while (e.hasMoreElements()) {
            Object t;
            if ((t = e.nextElement()) != null) {
                System.out.println(session.getAttribute((String) t));
            }
        }
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

        String op = request.getParameter("operation");

        if (op.equals("login")) {
            session = request.getSession();
            User u = new User(request.getParameter("username"), request.getParameter("password"));
            User user = dao.login(u);
            String feedback;
            if (user == null) {
                // incorrect login
                feedback = "incorrect credentials, try again.";

            } else {
                // create session
                feedback = "";
                session.setAttribute("user", user);
                session.setAttribute("currentTab", "modifyPlayer");
            }
            session.setAttribute("loginFeedback", feedback);

        } else if (op.equals("logout")) {
            session.invalidate();

        } else if (op.equals("create")) {
            String feedback;

            if (session.getAttribute("currentTab") != null) {
                session.removeAttribute("currentTab");
            }
            session.setAttribute("currentTab", "createUser");
            if (!dao.verifyAccess((User) session.getAttribute("user"), UserDao.UserAccessType.ROOT)) {
                feedback = "You do not have privileges to perform this action";
                response.sendRedirect("./");
                session.setAttribute("insertUserFeedback", feedback);
                return;
            }
            User u = new User(
                    request.getParameter("firstname"),
                    request.getParameter("lastname"),
                    request.getParameter("username"),
                    request.getParameter("password"),
                    request.getParameter("email"),
                    Integer.parseInt(request.getParameter("access")));
            if (request.getParameter("confirm").equals(u.getPassword())) {
                feedback = dao.insertUser((User) session.getAttribute("user"), u);
            } else {
                feedback = "password and password confirmation do not match";
            }
            session.setAttribute("insertUserFeedback", feedback);

        } // change password and any other personal details
        else if (op.equals("modify")) {

        } // delete user from system
        else if (op.equals("delete")) {
            String feedback;
            if (session.getAttribute("currentTab") != null) {
                session.removeAttribute("currentTab");
            }
            session.setAttribute("currentTab", "deleteUser");
            if (!dao.verifyAccess((User) session.getAttribute("user"), UserDao.UserAccessType.ROOT)) {
                feedback = "You do not have privileges to perform this action";
                response.sendRedirect("./");
                session.setAttribute("deleteUserFeedback", feedback);
                return;
            }
            feedback = dao.delete((User) session.getAttribute("user"), new User(request.getParameter("username"), ""));

            session.setAttribute("deleteUserFeedback", feedback);
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
