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
        <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
        
        <%!
        public String isSelectionSelected(String currentText, String currentSelection){
            if (currentText != null && currentSelection != null && currentText.equals(currentSelection)) {
                return "selected";
            }

            // No deberia estar este caso, pero por problemas con el lenguaje la añado
            if (currentText != null && currentText.equals("Año") && currentSelection != null && currentSelection.equals("AÃ±o")) {
                return "selected";
            }
            return "";
        }
        %>
    </head>
    <body class="uk-height-1-1 ">
        <div class="uk-vertical-align uk-text-center uk-height-1-1">
            <div class="uk-vertical-align-middle" style="width: 75%">
                <div class="uk-panel uk-panel-box">
                    <h3 class="uk-panel-title">Panel de control</h3>
                    
                    <div class="uk-grid">                        
                        <div class="uk-width-1-4">
                            <h3>Bienvenido <%= request.getSession().getAttribute("username") %></h3>
                                                          
                            <table class="uk-table vertical-line">
                                <thead>
                                    <tr>
                                        <th>Funciones</th>
                                    </tr>                                    
                                </thead>
                                
                                <tbody>
                                    <tr>
                                        <td>
                                            <form class="uk-form" style="text-align: left !important" action="servletControlPanel">
                                                <input class="uk-button uk-button-link" type="submit" name="listvideo" value="Listar videos">
                                            </form>
                                        </td>
                                    </tr>
                                </tbody>
                                
                                 <tbody>
                                    <tr>
                                        <td>
                                            <form class="uk-form" style="text-align: left !important" action="servletControlPanel">
                                                <input class="uk-button uk-button-link" type="submit" name="addvideo" value="Añadir vídeo">
                                            </form>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            
                            <table class="uk-table vertical-line">
                                <thead>
                                    <tr>
                                        <th>Configuración</th>
                                    </tr>                                    
                                </thead>
                                
                                <tbody>
                                    <tr>
                                        <td>
                                            <form class="uk-form" style="text-align: left !important" action="servletControlPanel">
                                                <input class="uk-button uk-button-link" type="submit" name="logout" value="Logout">
                                            </form>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>                        

                        <div class="uk-width-3-4">
                            <form class="uk-form">
                                <fieldset data-uk-margin>
                                    <legend>Filtrado de vídeos</legend>
                                    Filtrar por:
                                    <select name="queryselect">
                                        <option <%= isSelectionSelected("Titulo", request.getParameter("queryselect"))%>>Titulo</option>
                                        <option <%= isSelectionSelected("Nombre de autor", request.getParameter("queryselect"))%>>Nombre de autor</option>
                                        <option <%= isSelectionSelected("Año", request.getParameter("queryselect"))%>>Año</option>
                                        <option <%= isSelectionSelected("ID de autor", request.getParameter("queryselect"))%>>ID de autor</option>
                                    </select>
                                    <% 
                                        String inputTextDefaultValue = "";
                                        String queryText = (String) request.getParameter("querytext");
                                        if (queryText != null && !queryText.equals("null")) {
                                            inputTextDefaultValue = queryText;
                                        }
                                    %>
                                    <input type="text" name="querytext" value="<%= inputTextDefaultValue %>" required>
                                    
                                    <div class='uk-button'>
                                        <i class='uk-icon-search'></i>
                                        <input class='uk-button-link input-cursor-pointer' type='submit' name='listfilteredvideos' value='Buscar'>
                                    </div>
                                </fieldset>
                            </form>
                            
                            <form class="uk-form" style="margin-top: 30px">
                                <fieldset data-uk-margin>
                                    <legend>Listado de vídeos</legend>
                            
                                    <table class="uk-table uk-table-hover">
                                        <thead>
                                            <tr>
                                                <th>Título</th>
                                                <th>Autor</th>
                                                <th>Fecha</th>
                                                <th>Duración</th>
                                                <th>Reprod.</th>
                                                <th>Descrip.</th>
                                                <th>Formato</th>
                                                <th></th>
                                                <th></th>
                                                <th></th>
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
                                                    
                                                    out.println("<td>");
                                                    String videoURL = video.getURL();
                                                    if (videoURL != null && !videoURL.equals("")){
                                                        out.println("<form class='uk-form' action='servletControlPanel'>");
                                                        out.println("<div class='uk-button uk-button-primary'>");
                                                        out.println("<i class='uk-icon-info-circle'></i>");
                                                        out.println("<a href='" + videoURL + "' target='_blank' style='color:white;'>" + "Info");
                                                        out.println("</div>");
                                                        out.println("</form>");
                                                    } 
                                                    out.println("</td>");
                                                    out.println("<td>");
                                                    out.println("<form class='uk-form' action='servletControlPanel'>");
                                                    out.println("<div class='uk-button uk-button-success'>");
                                                    out.println("<i class='uk-icon-play-circle'></i>");
                                                    out.println("<input class='uk-button-link input-cursor-pointer' type='submit' name='playvideo#" + video.getID() + "' value='Play'>");
                                                    out.println("</div>");
                                                    out.println("</form>");
                                                    out.println("</td>");
                                                    out.println("<td>");
                                                    out.println("<form class='uk-form' action='servletControlPanel'>");
                                                    out.println("<div class='uk-button uk-button-danger'>");
                                                    out.println("<i class='uk-icon-minus-square'></i>");
                                                    out.println("<input class='uk-button-link input-cursor-pointer' type='submit' name='deletevideo#" + video.getID() + "' value='Borrar'>");
                                                    out.println("</div>");
                                                    out.println("</form>");
                                                    out.println("</td>");
                                                    out.println("<tr>");
                                                }                                    
                                            %>
                                        </tbody>                                
                                    </table>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>