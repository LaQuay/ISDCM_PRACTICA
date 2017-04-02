package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Usuario;

//Si falla glassfish: 
//http://stackoverflow.com/questions/25799671/netbeans-glassfish-server-4-deploy-connection-refused-connect-false-the-mo

/**
 *
 * @author marc.vila.gomez
 */
@WebServlet(name = "servletLoginUsu", urlPatterns = {"/servletLoginUsu"})
public class servletLoginUsu extends HttpServlet {
    public static String attributeErrorInvalidUsername = "ERROR_IS_INVALID_USERNAME";    
    public static String attributeErrorInvalidPassword = "ERROR_IS_INVALID_PASSWORD";    
    public static String attributeUserExists = "ERROR_USER_REGISTERED_YET";
    public static String attributeLoggedIn = "USER_IS_LOGGED";
    
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
            //Recogida de parametros
            System.out.println("Empezando processRequest");
            String userName = request.getParameter("username");
            String password = request.getParameter("password");
            
            //Tratamos para saber si son validos o no
            boolean isValidUsername = !userName.isEmpty();
            request.getSession().setAttribute(attributeErrorInvalidUsername, !isValidUsername);
            boolean isValidPassword = !password.isEmpty();
            request.getSession().setAttribute(attributeErrorInvalidPassword, !isValidPassword);
            
            //Inicializamos los atributos
            request.getSession().setAttribute(attributeLoggedIn, false);
            request.getSession().setAttribute(attributeUserExists, false);
            request.getSession().setAttribute("name", "");   
            request.getSession().setAttribute("surname", "");  
            request.getSession().setAttribute("email", "");  
            request.getSession().setAttribute("username", userName);   
            
            if (isValidUsername && isValidPassword){
                Usuario usuario = new Usuario(userName, password);        
                
                boolean existsUser = usuario.existsUserOrEmail();                
                if (existsUser){
                    System.out.println("Usuario existe");
                    usuario = usuario.getUser();
                    if (usuario != null){
                        request.getSession().setAttribute("name", usuario.getNombre());  
                        request.getSession().setAttribute("surname", usuario.getApellidos());  
                        request.getSession().setAttribute("email", usuario.getEmail());  
                        request.getSession().setAttribute("username", usuario.getUserName());   
                        request.getSession().setAttribute(attributeErrorInvalidPassword, false);
                        
                        //Redireccionamos al panel de control
                        request.getSession().setAttribute(attributeLoggedIn, true);
                        response.setHeader("Refresh", "2;url=servletControlPanel");
                    } else {
                        request.getSession().setAttribute(attributeErrorInvalidPassword, true);                        
                    }
                    request.getSession().setAttribute(attributeUserExists, true);      
                } else {
                    //Mostrar mensaje de error de usuario no existente
                    System.out.println("Usuario no existe");
                    request.getSession().setAttribute(attributeErrorInvalidUsername, true);
                }
            } else {
                // No se cumple algun requisito
            }
            
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
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
