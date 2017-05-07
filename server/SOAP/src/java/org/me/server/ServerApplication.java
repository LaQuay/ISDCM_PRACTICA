package org.me.server;

import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author marc.vila.gomez
 */
@WebService(serviceName = "ServerApplication")
@Stateless()
@XmlSeeAlso(VideoServer.class)
public class ServerApplication {

    /**
     * Web service operation
     * @param idAuthor
     * @return 
     */
    @WebMethod(operationName = "getVideosByAuthorID")
    public ArrayList getVideosByAuthorID(@WebParam(name = "idAuthor") int idAuthor) {
        VideoServer video = new VideoServer();
        if (idAuthor < 0){
            idAuthor = -1;
        }
        
        return video.getAllVideosByAuthorID(idAuthor);
    }
    
    /**
     * Web service operation
     * @param title
     * @return 
     */
    @WebMethod(operationName = "getVideosByTitle")
    public ArrayList getVideosByTitle(@WebParam(name = "title") String title) {
        VideoServer video = new VideoServer();        
        return video.getAllVideosByTitle(title);
    }
    
    /**
     * Web service operation
     * @param authorName
     * @return 
     */
    @WebMethod(operationName = "getVideosByAuthorName")
    public ArrayList getVideosByAuthorName(@WebParam(name = "authorName") String authorName) {
        VideoServer video = new VideoServer();
        return video.getAllVideosByAuthorName(authorName);
    }
    
    /**
     * Web service operation
     * @param year
     * @return 
     */
    @WebMethod(operationName = "getVideosByYear")
    public ArrayList getVideosByYear(@WebParam(name = "year") int year) {
        VideoServer video = new VideoServer();
        return video.getAllVideosByYear(year);
    }
    
    /**
     * Web service operation
     * @param id
     * @return 
     */
    @WebMethod(operationName = "getVideosByID")
    public ArrayList getVideosByID(@WebParam(name = "id") int id) {
        VideoServer video = new VideoServer();
        return video.getAllVideosByID(id);
    }
}
