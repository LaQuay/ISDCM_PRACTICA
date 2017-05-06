package api;

import controller.servletControlPanel;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.me.server.ServerApplication_Service;
import org.me.server.ServerApplication;
import model.Video;
import org.me.server.VideoServer;

/**
 *
 * @author marc.vila.gomez
 */
public class VideoAPIController {
    public static final String QUERY_VIDEOS_BY_TITLE = "QUERY_VIDEOS_BY_TITLE";
    public static final String QUERY_VIDEOS_BY_AUTHOR = "QUERY_VIDEOS_BY_AUTHOR";
    public static final String QUERY_VIDEOS_BY_AUTHOR_ID = "QUERY_VIDEOS_BY_AUTHOR_ID";
    public static final String QUERY_VIDEOS_BY_YEAR = "QUERY_VIDEOS_BY_YEAR";
    public static final String QUERY_VIDEOS_BY_ID = "QUERY_VIDEOS_BY_ID";
    
    public VideoAPIController(){}
    
    public static ArrayList getAllVideos(String queryType, String value){
        ServerApplication_Service service = new org.me.server.ServerApplication_Service();
        ServerApplication port = service.getServerApplicationPort();
        
        List videosList;
        switch (queryType) {
            case QUERY_VIDEOS_BY_TITLE:
                videosList = port.getVideosByTitle(value);
                break;
            case QUERY_VIDEOS_BY_AUTHOR:
                videosList = port.getVideosByAuthorName(value);
                break;
            case QUERY_VIDEOS_BY_YEAR:
                videosList = port.getVideosByYear(Integer.parseInt(value));        
                break;
            case QUERY_VIDEOS_BY_ID:
                videosList = port.getVideosByID(Integer.parseInt(value));        
                break;
            default:
                videosList = port.getVideosByAuthorID(Integer.parseInt(value));
                break;
        }
        
        ArrayList<Video> arrayVideos = new ArrayList<>();
        for (int i = 0; i < videosList.size(); ++i){
            VideoServer videoFromServer = (VideoServer) videosList.get(i);
            
            String dateString = videoFromServer.getFecha();
                
            java.util.Date utilDate = new Date(Calendar.getInstance().getTimeInMillis());
            try {
                utilDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dateString);
            } catch (ParseException ex) {
                Logger.getLogger(servletControlPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            Date sqlDate = new Date(utilDate.getTime());
            
            String timeString = videoFromServer.getDuracion();
            try {
                utilDate = new SimpleDateFormat("hh:mm", Locale.ENGLISH).parse(timeString);
            } catch (ParseException ex) {
                Logger.getLogger(servletControlPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            Time sqlTime = new Time(utilDate.getTime());
            
            arrayVideos.add(new Video(
                    videoFromServer.getID(), videoFromServer.getAutorID(), 
                    videoFromServer.getTitulo(), videoFromServer.getAutor(), 
                    sqlDate, sqlTime, 
                    videoFromServer.getReproducciones(), videoFromServer.getDescripcion(),
                    videoFromServer.getFormato(), videoFromServer.getURL()));
        }
        
        return arrayVideos;
    }
}
