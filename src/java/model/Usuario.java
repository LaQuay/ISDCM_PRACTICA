package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author marc.vila.gomez
 */

public class Usuario {
    private String nombre;
    private String apellidos;
    private String email;
    private String userName;
    private String password;
    
    private static final String host = "jdbc:derby://localhost:1527/DBUsuarios";
    private static final String uName = "marc";
    private static final String uPass = "vila";
    
    public Usuario(){
        this.nombre = null;
        this.apellidos = null;
        this.email = null;
        this.userName = null;
        this.password = null;
    }    
    
    public Usuario(String nombre, String apellidos, String email, String userName, String password){
        System.out.println("Creando Usuario: " + nombre + " - " + apellidos + " - " + email + " - " + userName + " - " + password);
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }
    
    public Usuario(String userName, String password){
        this.nombre = null;
        this.apellidos = null;
        this.email = null;
        this.userName = userName;
        this.password = password;
    }   

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean existsUserOrEmail(){
        boolean existsUserOrEmail = true;
        try {
            Connection conn = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = conn.createStatement();
            
            String sql = "SELECT COUNT(*) as COUNT FROM USUARIO WHERE username='" + this.userName + "' OR email='" + this.email + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                existsUserOrEmail = (rs.getInt("COUNT") > 0);
            }
            
            return existsUserOrEmail;            
        } catch (SQLException err) {
            System.out.println(err.getMessage());       
        }
        return existsUserOrEmail;
    }
    
    public boolean createUser(){
        boolean result = false;
        try {
            Connection conn = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = conn.createStatement();
            
            String sql = "INSERT INTO USUARIO " +
                   "VALUES('" + this.nombre + "', '" + this.apellidos + "', '" + this.email + "', '" + this.userName + "', '" + this.password + "')";
            System.out.println("Sentencia SQL: " + sql);
            stmt.executeUpdate(sql);
            
            result = true;
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return result;
    }
    
    public Usuario getUser(){
        Usuario usuario = null;
        try {
            Connection conn = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = conn.createStatement();
            
            String sql = "SELECT * FROM USUARIO WHERE username='" + this.userName + "' AND password='" + this.password + "'";
            System.out.println("Sentencia SQL: " + sql);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String name = rs.getString("NAME");
                String surname = rs.getString("SURNAME");
                String email = rs.getString("EMAIL");
                String userName = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                                
                usuario = new Usuario(name, surname, email, userName, password);
            }
            
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return usuario;
    }
}
