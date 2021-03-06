package controller;

import api.VideoAPIController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
            
            ArrayList videosArray = new ArrayList();
            if (request.getParameter("listfilteredvideos") == null) {
                videosArray = VideoAPIController.getAllVideos(VideoAPIController.QUERY_VIDEOS_BY_AUTHOR_ID, ""+idUsuario);                
            }
            
            request.getSession().setAttribute("START_ACTION", "NOACTION");
            request.getSession().setAttribute(attributeVideosArray, videosArray);
            
            if (request.getParameter("addvideo") != null) {
                System.out.println("Abriendo página para añadir video");
                request.getSession().setAttribute("START_ACTION", "ADD");
                response.setHeader("Refresh", "0;url=servletVideoManagement");  
            } else if (isRequestForPlay(request, videosArray)) {
                System.out.println("Abriendo página para reproducir video");
                
                int videoIDtoPlay = getVideoForPlay(request, videosArray);
                request.getSession().setAttribute("START_ACTION", "PLAY");
                request.getSession().setAttribute("VIDEO_PLAY", videoIDtoPlay + "");
                response.setHeader("Refresh", "0;url=servletVideoManagement");  
            } else if (request.getParameter("listvideo") != null) {
                System.out.println("Abriendo página para listar video");
                response.setHeader("Refresh", "0;url=servletControlPanel");  
            } else if (request.getParameter("listfilteredvideos") != null) {
                System.out.println("Filtrando por...");
                //queryValue
                String querySelect = request.getParameter("queryselect");
                String queryText = request.getParameter("querytext");
                
                switch (querySelect) {
                    case "Titulo":
                        videosArray = VideoAPIController.getAllVideos(VideoAPIController.QUERY_VIDEOS_BY_TITLE, queryText);
                        break;
                    case "Nombre de autor":
                        videosArray = VideoAPIController.getAllVideos(VideoAPIController.QUERY_VIDEOS_BY_AUTHOR, queryText);
                        break;
                    case "Año":
                    case "AÃ±o":
                        String valueString = "0";
                        try {
                            Integer valueInteger = Integer.parseInt(queryText);
                            valueString = queryText;                    
                        } catch (NumberFormatException e) {}
                        videosArray = VideoAPIController.getAllVideos(VideoAPIController.QUERY_VIDEOS_BY_YEAR, valueString);
                        break;
                    case "ID de autor":
                        valueString = "0";
                        try {
                            Integer valueInteger = Integer.parseInt(queryText);
                            valueString = queryText;                    
                        } catch (NumberFormatException e) {}
                        videosArray = VideoAPIController.getAllVideos(VideoAPIController.QUERY_VIDEOS_BY_AUTHOR_ID, valueString);
                        break;
                    default:
                        break;
                }   
                
                request.getSession().setAttribute(attributeVideosArray, videosArray);
                request.getRequestDispatcher("/control_panel.jsp").forward(request, response);                       
            } else if (isRequestForDelete(request, videosArray)) {
                System.out.println("Borrando video");
                
                Video video = getVideoForDelete(request, videosArray);
                if (video != null){
                    video.deleteVideo();
                }
                
                response.setHeader("Refresh", "0;url=servletControlPanel");              
            } else if (request.getParameter("logout") != null) {
                System.out.println("Haciendo logout");
                request.getSession().setAttribute(attributeLoggedIn, false);             
                
                request.getRequestDispatcher("/index.html").forward(request, response);
            } else {
                request.getRequestDispatcher("/control_panel.jsp").forward(request, response);                
            }
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
    
    private boolean isRequestForPlay(HttpServletRequest request, ArrayList videosArray) {        
        for (int i = 0; i < videosArray.size(); ++i){
            Video currentVideo = (Video) videosArray.get(i);
            if (request.getParameter("playvideo#" + currentVideo.getID()) != null){
                return true;
            }
        }
        
        return false;
    }
    
    private int getVideoForPlay(HttpServletRequest request, ArrayList videosArray){
        for (int i = 0; i < videosArray.size(); ++i){
            Video currentVideo = (Video) videosArray.get(i);
            if (request.getParameter("playvideo#" + currentVideo.getID()) != null){
                return currentVideo.getID();
            }
        }
        
        return -1;
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
