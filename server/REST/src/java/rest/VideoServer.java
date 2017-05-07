package rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author marc.vila.gomez
 */
public class VideoServer {    
    private static final String DB_HOST = "jdbc:derby://localhost:1527/DBUsuarios";
    private static final String DB_USER = "marc";
    private static final String DB_PASSWORD = "vila";
    private static final String TABLENAME = "VIDEOS";
    
    public static int updateVideoReproducciones(int idVideo){
        int reproducciones = 0;
        try {
            Connection conn = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            String sql = "SELECT VISUALIZATIONS FROM " + TABLENAME + " WHERE ID=" + idVideo;
            System.out.println("Sentencia SQL: " + sql);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {                
                reproducciones = rs.getInt("VISUALIZATIONS") + 1;
            }
            
            String query = "UPDATE " + TABLENAME + " SET VISUALIZATIONS= ? WHERE ID= ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, reproducciones);
            preparedStmt.setInt(2, idVideo);
            
            preparedStmt.executeUpdate();
            conn.close();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        
        return reproducciones;
    }
}
