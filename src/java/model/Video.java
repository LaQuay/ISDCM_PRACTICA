package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

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
    }    
    
    public Video(int ID, int autorID, String titulo, String autor, Date fecha, Time duracion, int reproducciones, String descripcion, String formato){
        System.out.println("Creando Video: " + ID + autorID + " - " + titulo + " - " + autor + " - " + fecha + " - " + duracion + " - " + reproducciones + " - " + descripcion + " - " + formato);
        this._ID = ID;
        this.autorID = autorID;
        this.titulo = titulo;
        this.autor = autor;
        this.fecha = fecha;
        this.duracion = duracion;
        this.reproducciones = reproducciones;
        this.descripcion = descripcion;
        this.formato = formato;
    }
    
    public Video(int autorID, String titulo, String autor, Date fecha, Time duracion, String descripcion, String formato){
        System.out.println("Cargando Video: " + "UNREGISTERED" + " - " + autorID + " - " + " - " + titulo + " - " + autor + " - " + fecha + " - " + duracion + " - " + 0 + " - " + descripcion + " - " + formato);
        this._ID = -1;
        this.autorID = autorID;
        this.titulo = titulo;
        this.autor = autor;
        this.fecha = fecha;
        this.duracion = duracion;
        this.reproducciones = 0;
        this.descripcion = descripcion;
        this.formato = formato;
    }

    public boolean createVideo(){
        boolean result = false;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            String sql = "INSERT INTO " + TABLENAME 
                    + "(AUTHORID, TITLE, AUTHOR, DATE, DURATION, VISUALIZATIONS, DESCRIPTION, FORMAT)"
                    + " VALUES (" + this.autorID + ", '" + this.titulo + "', '" + this.autor + "', '" + this.fecha + "', '" + this.duracion + "', " + this.reproducciones + ", '" + this.descripcion + "', '" + this.formato + "')";
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
            
            String sql = "SELECT * FROM " + TABLENAME + " WHERE id='" + this._ID + "'";
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
                                
                video = new Video(_ID, autorID, titulo, autor, fecha, duracion, reproducciones, descripcion, formato);
            }
            
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return video;
    }    
}
