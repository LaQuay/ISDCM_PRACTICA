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
@WebServlet(name = "servletRegistroUsu", urlPatterns = {"/servletRegistroUsu"})
public class servletRegistroUsu extends HttpServlet {
    public static String attributeErrorInvalidName = "ERROR_IS_INVALID_NAME";        
    public static String attributeErrorInvalidSurname = "ERROR_IS_INVALID_SURNAME";    
    public static String attributeErrorInvalidEmail = "ERROR_IS_INVALID_EMAIL";    
    public static String attributeErrorInvalidUsername = "ERROR_IS_INVALID_USERNAME";    
    public static String attributeErrorInvalidPassword = "ERROR_IS_INVALID_PASSWORD";    
    public static String attributeUserOrEmailExists = "ERROR_USER_REGISTERED_YET";
    public static String attributeUserRegisteredOK = "IS_USER_REGISTERED_OK";
    
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
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String email = request.getParameter("email");
            String userName = request.getParameter("username");
            String password = request.getParameter("password");
            String passwordConfirm = request.getParameter("password_confirmation");
            
            //Tratamos para saber si son validos o no
            boolean isValidName = !name.isEmpty();
            request.getSession().setAttribute(attributeErrorInvalidName, !isValidName);
            boolean isValidSurname = !surname.isEmpty();
            request.getSession().setAttribute(attributeErrorInvalidSurname, !isValidSurname);
            boolean isValidEmail = !email.isEmpty();
            request.getSession().setAttribute(attributeErrorInvalidEmail, !isValidEmail);
            boolean isValidUsername = !userName.isEmpty();
            request.getSession().setAttribute(attributeErrorInvalidUsername, !isValidUsername);
            boolean isValidPassword = !password.isEmpty() && !passwordConfirm.isEmpty() && password.equals(passwordConfirm);
            request.getSession().setAttribute(attributeErrorInvalidPassword, !isValidPassword);
            
            //Inicializamos los atributos
            request.getSession().setAttribute(attributeUserOrEmailExists, false);
            request.getSession().setAttribute(attributeUserRegisteredOK, false);            
            
            if (isValidName && isValidSurname && isValidEmail && isValidUsername && isValidPassword){
                System.out.println("Passwords OK");
                Usuario usuario = new Usuario(name, surname, email, userName, password);        
                
                boolean existsUser = usuario.existsUserOrEmail();                
                if(!existsUser){
                    System.out.println("Usuario registrado OK");
                    boolean registered = usuario.createUser();
                    request.getSession().setAttribute(attributeUserRegisteredOK, registered);
                    
                    response.setHeader("Refresh", "3;url=index.html");
                } else {
                    //Mostrar mensaje de error de usuario ya existente
                    System.out.println("Usuario o email ya existe");
                }          
                request.getSession().setAttribute(attributeUserOrEmailExists, existsUser);
            } else {
                // No se cumple algun requisito
            }
            
            request.getRequestDispatcher("/registro.jsp").forward(request, response);
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
