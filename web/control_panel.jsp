<%@page import="model.Video"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="servletCtrlPan" class="controller.servletControlPanel" scope="session"/>
<html class="uk-height-1-1">
    <head>
        <title>Panel de control</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="uikit_marc.css">
    </head>
    <body class="uk-height-1-1 ">
        <div class="uk-vertical-align uk-text-center uk-height-1-1">
            <div class="uk-vertical-align-middle" style="width: 75%">
                <div class="uk-panel uk-panel-box">
                    <h3 class="uk-panel-title">Panel de control</h3>
                    
                    <div class="uk-grid">                        
                        <div class="uk-width-1-4">
                            <h3>Bienvenido <%= request.getSession().getAttribute("username") %></h3>
                                                          
                            <table class="uk-table">
                                <thead>
                                    <tr>
                                        <th>Funciones</th>
                                    </tr>                                    
                                </thead>
                                
                                <body>
                                    <tr>
                                        <td>
                                            <form class="uk-form" style="text-align: left !important" action="servletControlPanel">
                                                <input class="uk-button uk-button-link" type="submit" name="addvideo" value="Añadir vídeo">
                                            </form>
                                        </td>
                                    </tr>
                                </body>
                            </table>
                            
                            <table class="uk-table">
                                <thead>
                                    <tr>
                                        <th>Configuración</th>
                                    </tr>                                    
                                </thead>
                                
                                <body>
                                    <tr>
                                        <td>
                                            <form class="uk-form" style="text-align: left !important" action="servletControlPanel">
                                                <input class="uk-button uk-button-link" type="submit" name="logout" value="Logout">
                                            </form>
                                        </td>
                                    </tr>
                                </body>
                            </table>
                        </div>                        

                        <div class="uk-width-3-4">
                            <h3>Listado de vídeos</h3>
                            
                            <table class="uk-table uk-table-hover">
                                <thead>
                                    <tr>
                                        <th>Título</th>
                                        <th>Autor</th>
                                        <th>Fecha</th>
                                        <th>Duración</th>
                                        <th>Reproducciones</th>
                                        <th>Descripción</th>
                                        <th>Formato</th>
                                        <th>URL</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        ArrayList videosArray = (ArrayList) request.getSession().getAttribute(servletCtrlPan.attributeVideosArray);
                                        
                                        for (int i = 0; i < videosArray.size(); ++i){
                                            Video video = (Video) videosArray.get(i);
                                            
                                            out.println("<tr>");
                                            out.println("<td>" + video.getTitulo() + "</td>"); 
                                            out.println("<td>" + video.getAutor()+ "</td>"); 
                                            out.println("<td>" + video.getFecha()+ "</td>"); 
                                            out.println("<td>" + video.getDuracion()+ "</td>"); 
                                            out.println("<td>" + video.getReproducciones()+ "</td>"); 
                                            out.println("<td>" + video.getDescripcion()+ "</td>"); 
                                            out.println("<td>" + video.getFormato()+ "</td>"); 
                                            String videoURL = video.getURL();
                                            if (videoURL != null && !videoURL.equals("")){
                                                out.println("<td> <a href='" + videoURL + "'> Enlace" + "</td>"); 
                                            } else {
                                                out.println("<td> </td>"); 
                                            }
                                            out.println("<tr>");
                                        }                                    
                                    %>
                                </tbody>                                
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

