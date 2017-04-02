package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author marc.vila.gomez
 */

public class Video {
    private int _ID;
    private int autorID;
    private String titulo;
    private String autor;
    private Date fecha;
    private Time duracion;
    private int reproducciones;
    private String descripcion;
    private String formato;
    private String URL;
    
    private static final String DB_HOST = "jdbc:derby://localhost:1527/DBUsuarios";
    private static final String DB_USER = "marc";
    private static final String DB_PASSWORD = "vila";
    private static final String TABLENAME = "VIDEOS";
    
    public Video(){
        this._ID = -1;
        this.autorID = -1;
        this.titulo = null;
        this.autor = null;
        this.fecha = null;
        this.duracion = null;
        this.reproducciones = 0;
        this.descripcion = null;
        this.formato = null;
        this.URL = null;
    }    
    
    public Video(int ID, int autorID, String titulo, String autor, Date fecha, Time duracion, int reproducciones, String descripcion, String formato, String url){
        System.out.println("Creando Video: " + ID + autorID + " - " + titulo + " - " + autor + " - " + fecha + " - " + duracion + " - " + reproducciones + " - " + descripcion + " - " + formato + " - " + url);
        this._ID = ID;
        this.autorID = autorID;
        this.titulo = titulo;
        this.autor = autor;
        this.fecha = fecha;
        this.duracion = duracion;
        this.reproducciones = reproducciones;
        this.descripcion = descripcion;
        this.formato = formato;
        this.URL = url;
    }
    
    public Video(int autorID, String titulo, String autor, Date fecha, Time duracion, String descripcion, String formato, String url){
        System.out.println("Cargando Video: " + "UNREGISTERED" + " - " + autorID + " - " + " - " + titulo + " - " + autor + " - " + fecha + " - " + duracion + " - " + 0 + " - " + descripcion + " - " + formato + " - " + url);
        this._ID = -1;
        this.autorID = autorID;
        this.titulo = titulo;
        this.autor = autor;
        this.fecha = fecha;
        this.duracion = duracion;
        this.reproducciones = 0;
        this.descripcion = descripcion;
        this.formato = formato;
        this.URL = url;
    }  
    
    public boolean createVideo(){
        boolean result = false;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            String sql = "INSERT INTO " + TABLENAME 
                    + "(AUTHORID, TITLE, AUTHOR, DATE, DURATION, VISUALIZATIONS, DESCRIPTION, FORMAT, URL)"
                    + " VALUES (" + this.getAutorID() + ", '" + this.getTitulo() + "', '" + this.getAutor() + "', '" + this.getFecha() + "', '" + this.getDuracion() + "', " + this.getReproducciones() + ", '" + this.getDescripcion() + "', '" + this.getFormato() + "', '" + this.getURL() + "')";
            System.out.println("Sentencia SQL: " + sql);
            stmt.executeUpdate(sql);
            
            result = true;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return result;
    }
    
    public Video getVideo(){
        Video video = null;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            String sql = "SELECT * FROM " + TABLENAME + " WHERE AUTHORID='" + this.getAutorID() + "'";
            System.out.println("Sentencia SQL: " + sql);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {                
                int _ID = rs.getInt("ID");
                int autorID = rs.getInt("AUTHORID");
                String titulo = rs.getString("TITLE");
                String autor = rs.getString("AUTHOR");
                Date fecha = rs.getDate("DATE");
                Time duracion = rs.getTime("DURATION");
                int reproducciones = rs.getInt("VISUALIZATIONS");
                String descripcion = rs.getString("DESCRIPTION");
                String formato = rs.getString("FORMAT");
                String url = rs.getString("URL");
                                
                video = new Video(_ID, autorID, titulo, autor, fecha, duracion, reproducciones, descripcion, formato, url);
            }            
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return video;
    }    
    
    public Video deleteVideo(){
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            String sql = "DELETE FROM " + TABLENAME + " WHERE ID=" + this.getID();
            System.out.println("Sentencia SQL: " + sql);
            stmt.executeUpdate(sql);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        
        return new Video();
    }
    
    public ArrayList getAllVideos(){
        ArrayList videosArray = new ArrayList();
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            //String sql = "SELECT * FROM " + TABLENAME + " WHERE AUTHORID='" + this.autor + "'";
            String sql = "SELECT * FROM " + TABLENAME;
            System.out.println("Sentencia SQL: " + sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {                
                int _ID = rs.getInt("ID");
                int autorID = rs.getInt("AUTHORID");
                String titulo = rs.getString("TITLE");
                String autor = rs.getString("AUTHOR");
                Date fecha = rs.getDate("DATE");
                Time duracion = rs.getTime("DURATION");
                int reproducciones = rs.getInt("VISUALIZATIONS");
                String descripcion = rs.getString("DESCRIPTION");
                String formato = rs.getString("FORMAT");
                String url = rs.getString("URL");
                                
                videosArray.add(new Video(_ID, autorID, titulo, autor, fecha, duracion, reproducciones, descripcion, formato, url));
            }            
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return videosArray;
    }

    /**
     * @return the _ID
     */
    public int getID() {
        return _ID;
    }

    /**
     * @param _ID the _ID to set
     */
    public void setID(int _ID) {
        this._ID = _ID;
    }

    /**
     * @return the autorID
     */
    public int getAutorID() {
        return autorID;
    }

    /**
     * @param autorID the autorID to set
     */
    public void setAutorID(int autorID) {
        this.autorID = autorID;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the autor
     */
    public String getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the duracion
     */
    public Time getDuracion() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(Time duracion) {
        this.duracion = duracion;
    }

    /**
     * @return the reproducciones
     */
    public int getReproducciones() {
        return reproducciones;
    }

    /**
     * @param reproducciones the reproducciones to set
     */
    public void setReproducciones(int reproducciones) {
        this.reproducciones = reproducciones;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the formato
     */
    public String getFormato() {
        return formato;
    }

    /**
     * @param formato the formato to set
     */
    public void setFormato(String formato) {
        this.formato = formato;
    }

    /**
     * @return the URL
     */
    public String getURL() {
        return URL;
    }

    /**
     * @param URL the URL to set
     */
    public void setURL(String URL) {
        this.URL = URL;
    }
}
