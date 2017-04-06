<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="servletReg" class="controller.servletRegistroUsu" scope="session"/>
<html class="uk-height-1-1">
    <head>
        <title>Confirmación de Registro</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="uikit_marc.css">
    </head>
    <body class="uk-height-1-1">
        <div class="uk-vertical-align uk-text-center uk-height-1-1">
            <div class="uk-vertical-align-middle" style="width: 250px;">
                <div class="uk-panel uk-panel-box">
                    <h3 class="uk-panel-title">
                        <%
                            boolean invalidName = (Boolean) request.getSession().getAttribute(servletReg.attributeErrorInvalidName);
                            boolean invalidSurname = (Boolean) request.getSession().getAttribute(servletReg.attributeErrorInvalidSurname);
                            boolean invalidEmail = (Boolean) request.getSession().getAttribute(servletReg.attributeErrorInvalidEmail);
                            boolean invalidUsername = (Boolean) request.getSession().getAttribute(servletReg.attributeErrorInvalidUsername);
                            boolean invalidPassword = (Boolean) request.getSession().getAttribute(servletReg.attributeErrorInvalidPassword);   
                            
                            boolean userOrEmailExists = (Boolean) request.getSession().getAttribute(servletReg.attributeUserOrEmailExists);
                            boolean userCreated = (Boolean) request.getSession().getAttribute(servletReg.attributeUserRegisteredOK);

                            if(userCreated){
                                out.println("Registro correcto");
                            } else {
                                out.println("Registro incorrecto");
                            }
                        %>
                    </h3>
                    
                    Usuario: <%= request.getParameter("username") %> <br>
                    Email: <%= request.getParameter("email") %> <br>
                    Nombre: <%= request.getParameter("name") %> <br>
                    Apellidos: <%= request.getParameter("surname") %> <br>

                    <br>
                    <%
                        if(userCreated){
                            out.println("<div class='uk-alert uk-alert-success'>");
                            out.println("Accediendo a pestaña de login");
                            out.println("</div>");  
                        } else {
                            if (invalidName){
                                out.println("<div class='uk-alert uk-alert-danger'>");
                                out.println("Nombre no válido");
                                out.println("</div>");  
                            }
                            if (invalidSurname){
                                out.println("<div class='uk-alert uk-alert-danger'>");
                                out.println("Apellido no válido");
                                out.println("</div>"); 
                            }
                            if (invalidEmail){
                                out.println("<div class='uk-alert uk-alert-danger'>");
                                out.println("Email no válido");
                                out.println("</div>"); 
                            }
                            if (invalidUsername){
                                out.println("<div class='uk-alert uk-alert-danger'>");
                                out.println("Usuario no válido");
                                out.println("</div>"); 
                            }
                            if (invalidPassword){
                                out.println("<div class='uk-alert uk-alert-danger'>");
                                out.println("Contraseña no válida");
                                out.println("</div>"); 
                            }
                            if (userOrEmailExists){
                                out.println("<div class='uk-alert uk-alert-danger'>");
                                out.println("El usuario o email ya existen");
                                out.println("</div>"); 
                            }
                        }
                    %>
                    <br>
                    <button class="uk-button" type="button" name="back" onclick="history.back()">Volver</button>
                </div>
            </div>
        </div>
    </body>
</html>
