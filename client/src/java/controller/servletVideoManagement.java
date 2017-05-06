package controller;

import api.VideoAPIController;
import static controller.servletControlPanel.attributeUserID;
import static controller.servletControlPanel.attributeVideosArray;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "servletVideoManagement", urlPatterns = {"/servletVideoManagement"})
public class servletVideoManagement extends HttpServlet {
    public static String attributeVideo = "ATTRIBUTE_VIDEO";
    
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
            //Recogida de parametros
            int idUsuario = -1;
            try {
                idUsuario = (Integer) request.getSession().getAttribute(attributeUserID);
            } catch (Exception e){
                System.err.println(e.toString());
            }
            
            
            if (request.getParameter("title") != null) {
                System.out.println("Añadiendo video");

                String title = request.getParameter("title");

                String author = request.getParameter("author");

                String dateString = request.getParameter("date");

                java.util.Date utilDate = new Date(Calendar.getInstance().getTimeInMillis());
                try {
                    utilDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dateString);
                } catch (ParseException ex) {
                    Logger.getLogger(servletControlPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                Date sqlDate = new Date(utilDate.getTime());

                String timeString = request.getParameter("duration");
                try {
                    utilDate = new SimpleDateFormat("hh:mm", Locale.ENGLISH).parse(timeString);
                } catch (ParseException ex) {
                    Logger.getLogger(servletControlPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                Time sqlTime = new Time(utilDate.getTime());

                String description = request.getParameter("description");

                String format = request.getParameter("format");

                String url = request.getParameter("url");

                Video video = new Video(idUsuario, title, author, sqlDate, sqlTime, description, format, url);
                boolean videoCreated = video.createVideo(); 

                response.setHeader("Refresh", "0;url=servletControlPanel");
            }
            
            String headerValue = (String) request.getSession().getAttribute("START_ACTION");
            if (headerValue != null && !headerValue.equals("NOACTION")){
                if (headerValue.equals("ADD")){
                    request.getRequestDispatcher("/addvideo.html").forward(request, response);                    
                } else if (headerValue.equals("PLAY")){
                    String videoPlay = (String) request.getSession().getAttribute("VIDEO_PLAY");
                    ArrayList videosArray = new ArrayList();
                    if (request.getParameter("listfilteredvideos") == null) {
                        videosArray = VideoAPIController.getAllVideos(VideoAPIController.QUERY_VIDEOS_BY_ID, ""+videoPlay);                
                    }
                    Video currentVideo = (Video) videosArray.get(0);
                    request.getSession().setAttribute(attributeVideo, currentVideo);
                    
                    request.getRequestDispatcher("/playvideo.jsp").forward(request, response);                    
                }
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
