<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="servletLog" class="controller.servletLoginUsu" scope="session"/>
<html class="uk-height-1-1">
    <head>
        <title>Confirmación de Login</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="uikit_marc.css">
    </head>
    <body class="uk-height-1-1">
        <div class="uk-vertical-align uk-text-center uk-height-1-1">
            <div class="uk-vertical-align-middle" style="width: 250px;">
                <div class="uk-panel uk-panel-box">
                    <h3 class="uk-panel-title">
                        <%
                            boolean userLoggedIn = (Boolean) request.getSession().getAttribute(servletLog.attributeLoggedIn);
                            boolean invalidUsername = (Boolean) request.getSession().getAttribute(servletLog.attributeErrorInvalidUsername);
                            boolean invalidPassword = (Boolean) request.getSession().getAttribute(servletLog.attributeErrorInvalidPassword);     

                            if(userLoggedIn){
                                out.println("Credenciales correctas");
                            } else {
                                out.println("Credenciales incorrectas");
                            }
                        %>
                    </h3>
                    
                    Usuario: <%= request.getSession().getAttribute("username") %> <br>
                    Email: <%= request.getSession().getAttribute("email") %> <br>

                    <br>
                    <%
                        if (userLoggedIn){
                            out.println("<div class='uk-alert uk-alert-success'>");
                            out.println("Accediendo a panel de control");
                            out.println("</div>");  
                        } else {
                            String errorMessage = "";
                            if (invalidUsername){
                                errorMessage = "El usuario no existe";
                            }

                            if (invalidPassword){
                                errorMessage = "Contraseña invalida";
                            }

                            out.println("<div class='uk-alert uk-alert-danger'>");
                            out.println(errorMessage);
                            out.println("</div>");                                
                        }
                    %>
                    <br>
                    <button class="uk-button" type="button" name="back" onclick="history.back()">Volver</button>
                </div>
            </div>
        </div>
    </body>
</html>
