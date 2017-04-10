package api;

import java.util.ArrayList;
import java.util.List;

import org.me.server.ServerApplication_Service;
import org.me.server.ServerApplication;
import model.Video;
import org.me.server.VideoServer;

/**
 *
 * @author marc.vila.gomez
 */
public class VideoAPI {
    
    public VideoAPI(){
        try {
            int i = 3;
            int j = 4;
            int result = add(i, j);
            System.out.println("Result = " + result);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
        }
    }
    
    private static int add(int i, int j) {
        ServerApplication_Service service = new org.me.server.ServerApplication_Service();
        ServerApplication port = service.getServerApplicationPort();
        return port.add(i, j);
    }
    
    public static ArrayList getAllVideos(int idUsuario){
        ServerApplication_Service service = new org.me.server.ServerApplication_Service();
        ServerApplication port = service.getServerApplicationPort();
        
        List videosList = port.getVideos(idUsuario);
        
        ArrayList<Video> arrayVideos = new ArrayList<>();
        for (int i = 0; i < videosList.size(); ++i){
            VideoServer videoFromServer = (VideoServer) videosList.get(i);
            arrayVideos.add(new Video(
                    videoFromServer.getID(), videoFromServer.getAutorID(), 
                    videoFromServer.getTitulo(), videoFromServer.getAutor(), 
                    null, null, 
                    videoFromServer.getReproducciones(), videoFromServer.getDescripcion(),
                    videoFromServer.getFormato(), videoFromServer.getURL()));
        }
        
        return arrayVideos;
    }
}
