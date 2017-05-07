<%@page import="model.Video"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="servletVideoMgmt" class="controller.servletVideoManagement" scope="session"/>
<html class="uk-height-1-1">
    <head>
        <title>Reproducir video</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="uikit_marc.css">
        <link rel="stylesheet" type="text/css" href="skin/pink.flag/css/jplayer.pink.flag.min.css"/>
        <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" src="js/jquery.jplayer.min.js"></script>
                
        <script type="text/javascript">            
            $(document).ready(function(){           
                $("#jquery_jplayer_1").jPlayer({
                  ready: function () {
                    $(this).jPlayer("setMedia", {
                      title: $("#titulo").text(),
                      m4v: $("#url").text()
                    });
                  },
                  cssSelectorAncestor: "#jp_container_1",
                  swfPath: "/js",
                  supplied: "m4v",
                  useStateClassSkin: true,
                  autoBlur: false,
                  smoothPlayBar: true,
                  keyEnabled: true,
                  remainingDuration: true,
                  toggleDuration: true
              });
            });
        </script>
    </head>
    <body class="uk-height-1-1">
        <% 
           Video videoCurrent = (Video) request.getSession().getAttribute("ATTRIBUTE_VIDEO");
        %>
                
        <div class="uk-vertical-align uk-text-center uk-height-1-1">        
            <div class="uk-vertical-align-middle" style="width: 50%;">
                <form class="uk-panel uk-panel-box uk-form" action="servletVideoManagement">
                    <div id="jp_container_1" class="jp-video" role="application" aria-label="media player">
                        <div class="jp-type-single">
                          <div id="jquery_jplayer_1" class="jp-jplayer"></div>
                          <div class="jp-gui">
                            <div class="jp-video-play">
                              <button class="jp-video-play-icon" role="button" tabindex="0">play</button>
                            </div>
                            <div class="jp-interface">
                              <div class="jp-progress">
                                <div class="jp-seek-bar">
                                  <div class="jp-play-bar"></div>
                                </div>
                              </div>
                              <div class="jp-current-time" role="timer" aria-label="time">&nbsp;</div>
                              <div class="jp-duration" role="timer" aria-label="duration">&nbsp;</div>
                              <div class="jp-details">
                                <div class="jp-title" aria-label="title">&nbsp;</div>
                              </div>
                              <div class="jp-controls-holder">
                                <div class="jp-volume-controls">
                                  <button class="jp-mute" role="button" tabindex="0">mute</button>
                                  <button class="jp-volume-max" role="button" tabindex="0">max volume</button>
                                  <div class="jp-volume-bar">
                                    <div class="jp-volume-bar-value"></div>
                                  </div>
                                </div>
                                <div class="jp-controls">
                                  <button class="jp-play" role="button" tabindex="0">play</button>
                                  <button class="jp-stop" role="button" tabindex="0">stop</button>
                                </div>
                                <div class="jp-toggles">
                                  <button class="jp-repeat" role="button" tabindex="0">repeat</button>
                                  <button class="jp-full-screen" role="button" tabindex="0">full screen</button>
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="jp-no-solution">
                            <span>Update Required</span>
                            To play the media you will need to either update your browser to a recent version or update your <a href="http://get.adobe.com/flashplayer/" target="_blank">Flash plugin</a>.
                          </div>
                        </div>
                    </div>
                    
                    <p class="uk-text-large">Titulo</p>
                    <p id="titulo" name="titulo"><%= videoCurrent.getTitulo() %></p>
                    
                    <p class="uk-text-large">Autor</p>
                    <p id="autor" name="autor"><%= videoCurrent.getAutor()%></p>
                    
                    <p class="uk-text-large">Descripción</p>
                    <p id="descripcion" name="descripcion"><%= videoCurrent.getDescripcion()%></p>
                    
                    <p class="uk-text-large">Fecha</p>
                    <p id="fecha" name="fecha"><%= videoCurrent.getFecha()%></p>
                    
                    <p class="uk-text-large">Reproducciones</p>
                    <p id="reproducciones" name="reproducciones"><%= videoCurrent.getReproducciones()+1%></p>
                    
                    <p class="uk-text-large">Más información</p>
                    <p id="url-info" name="url-info"><%= videoCurrent.getURL()%></p>
                    
                    <p class="uk-text-large">Url de video</p>
                    <p id="url" name="url"><%= videoCurrent.getURLInfo()%></p>
                    
                    <div class="uk-form-row uk-text-small">
                        <a class="uk-float-right uk-link uk-link-muted" href="./control_panel.jsp">Volver atrás</a>
                    </div>
                </form> 
            </div>
        </div>
    </body>
</html>
