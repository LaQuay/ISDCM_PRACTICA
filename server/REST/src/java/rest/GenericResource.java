package rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;

/**
 * REST Web Service
 *
 * @author marc.vila.gomez
 */
@Path("generic")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Sample of GET method
     * @param idPelicula
     * @return 
     */
    @Path("getInfo")
    @GET    
    @Produces("text/html")
    public String getInfo(@QueryParam("idPelicula") int idPelicula) {
        VideoServer.updateVideoReproducciones(idPelicula);
        return "<html><head></head> <body> Actualizando pelicula con ID: " + idPelicula + ", METODO GET";
    }

    /**
     * Sample of POST method
     * 
     * @param idPelicula
     * @return 
     */
    @Path("postInfo")   
    @POST    
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/html")
    public String postInfo(@FormParam("idPelicula") int idPelicula){  
        VideoServer.updateVideoReproducciones(idPelicula);              
        return "<html><head></head> <body> Actualizando pelicula con ID: " + idPelicula + ", METODO POST";
    }    
}