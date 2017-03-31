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
                            <form class="uk-form" action="servletControlPanel">
                                <input class="uk-button uk-margin-small-bottom" type="submit" name="addvideo" value="Añadir vídeo">
                            </form>
                            
                            <form class="uk-form" action="servletControlPanel">
                                <input class="uk-button uk-button-primary" type="submit" name="logout" value="Logout">
                            </form>
                        </div>                        

                        <div class="uk-width-3-4">
                            <h3>Listado de vídeos</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

