package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Video;

//Si falla glassfish: 
//http://stackoverflow.com/questions/25799671/netbeans-glassfish-server-4-deploy-connection-refused-connect-false-the-mo

/**
 *
 * @author marc.vila.gomez
 */
@WebServlet(name = "servletControlPanel", urlPatterns = {"/servletControlPanel"})
public class servletControlPanel extends HttpServlet {
    public static String attributeLoggedIn = "USER_IS_LOGGED";
    
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
            if (request.getParameter("title") != null) {
                System.out.println("Añadiendo video");
                
                String title = request.getParameter("title");
                String author = request.getParameter("author");
                //Date date = request.getParameter("date");
                //Time duration = request.getParameter("duration");
                String description = request.getParameter("description");
                String format = request.getParameter("format");
                
                Video video = new Video(0, title, author, new Date(2017, 1, 2), new Time(23, 54, 59), description, format);
                boolean videoCreated = video.createVideo();
                
                // TODO Hacer gestion sobre si video esta creado bien o no y redireccionar
                
            } else if (request.getParameter("addvideo") != null) {
                System.out.println("Abriendo página para añadir video");
                request.getRequestDispatcher("/addvideo.html").forward(request, response);
            } else if (request.getParameter("logout") != null) {
                System.out.println("Haciendo logout");
                request.getSession().setAttribute(attributeLoggedIn, false);                
                
                request.getRequestDispatcher("/index.html").forward(request, response);
            }
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
