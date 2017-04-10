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
     * @param i
     * @param j
     * @return 
     */
    @WebMethod(operationName = "add")
    public int add(@WebParam(name = "i") int i, @WebParam(name = "j") int j) {
        return i + j;
    }

    /**
     * Web service operation
     * @param idAuthor
     * @return 
     */
    @WebMethod(operationName = "getVideos")
    public ArrayList getVideos(@WebParam(name = "idAuthor") int idAuthor) {
        VideoServer video = new VideoServer();
        if (idAuthor < 0){
            idAuthor = -1;
        }
        
        return video.getAllVideos(idAuthor);
    }
}
