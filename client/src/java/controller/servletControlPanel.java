package controller;

import api.VideoAPI;
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
@WebServlet(name = "servletControlPanel", urlPatterns = {"/servletControlPanel"})
public class servletControlPanel extends HttpServlet {
    public static String attributeLoggedIn = "USER_IS_LOGGED";
    public static String attributeVideosArray = "ATTRIBUTE_VIDEOS_ARRAY";
    public static String attributeUserID = "USER_ID";
    
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
            int idUsuario = -1;
            try {
                idUsuario = (Integer) request.getSession().getAttribute(attributeUserID);
            } catch (Exception e){
                System.err.println(e.toString());
            }
            
            //TODO Hacer solo caso principal, el else
            ArrayList videosArray = new ArrayList();
            if (request.getParameter("listfilteredvideos") != null && request.getParameter("querytext") != null && !request.getParameter("querytext").equals("")){
                String querySelect = request.getParameter("queryselect");
                String queryText = request.getParameter("querytext");
                
                if (querySelect.equals("Titulo")){
                    videosArray = VideoAPI.getAllVideos(VideoAPI.QUERY_VIDEOS_BY_TITLE, queryText);
                } else if (querySelect.equals("Nombre de autor")){
                    videosArray = VideoAPI.getAllVideos(VideoAPI.QUERY_VIDEOS_BY_AUTHOR, queryText);                    
                } else if (querySelect.equals("Año") || querySelect.equals("AÃ±o")){
                    String valueString = "0";
                    try {
                        Integer valueInteger = Integer.parseInt(queryText);
                        valueString = queryText;
                    } catch (NumberFormatException e) {}
                    videosArray = VideoAPI.getAllVideos(VideoAPI.QUERY_VIDEOS_BY_YEAR, valueString);                    
                } else if (querySelect.equals("ID de autor")){
                    videosArray = VideoAPI.getAllVideos(VideoAPI.QUERY_VIDEOS_BY_AUTHOR_ID, queryText);                    
                }
            } else {
                videosArray = VideoAPI.getAllVideos(VideoAPI.QUERY_VIDEOS_BY_AUTHOR_ID, ""+idUsuario);                
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
            } else if (request.getParameter("addvideo") != null) {
                System.out.println("Abriendo página para añadir video");
                request.getRequestDispatcher("/addvideo.html").forward(request, response);
            } else if (request.getParameter("listvideo") != null) {
                System.out.println("Abriendo página para listar video");
                response.setHeader("Refresh", "0;url=servletControlPanel");  
            } else if (request.getParameter("listfilteredvideos") != null) {
                System.out.println("Filtrando por...");
                //queryValue
                
            } else if (isRequestForDelete(request, videosArray)) {
                System.out.println("Borrando video");
                
                Video video = getVideoForDelete(request, videosArray);
                if (video != null){
                    video = video.deleteVideo();
                }
                
                response.setHeader("Refresh", "0;url=servletControlPanel");              
            } else if (request.getParameter("logout") != null) {
                System.out.println("Haciendo logout");
                request.getSession().setAttribute(attributeLoggedIn, false);                
                
                request.getRequestDispatcher("/index.html").forward(request, response);
            }
            
            request.getSession().setAttribute(attributeVideosArray, videosArray);
            
            request.getRequestDispatcher("/control_panel.jsp").forward(request, response);
        }
    }
    
    private boolean isRequestForDelete(HttpServletRequest request, ArrayList videosArray) {
        for (int i = 0; i < videosArray.size(); ++i){
            Video currentVideo = (Video) videosArray.get(i);
            if (request.getParameter("deletevideo#" + currentVideo.getID()) != null){
                return true;
            }
        }
        
        return false;
    }
    
    private Video getVideoForDelete(HttpServletRequest request, ArrayList videosArray){
        for (int i = 0; i < videosArray.size(); ++i){
            Video currentVideo = (Video) videosArray.get(i);
            if (request.getParameter("deletevideo#" + currentVideo.getID()) != null){
                return currentVideo;
            }
        }
        
        return null;
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
