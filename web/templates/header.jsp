
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="context" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${param.title}</title>
    <link href="${context}/img/favicon.ico" rel="icon" type="image/x-icon" />
    <link rel="stylesheet" type="text/css" href="${context}/css/semantic.min.css">
    <script src="${context}/js/jquery.min.js"></script>
    <script src="${context}/js/jquery.alertable.min.js"></script>
    <script src="${context}/js/semantic.min.js"></script>
    <script src="${context}/js/simpleslider.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${context}/css/custom.css">
    <link rel="stylesheet" type="text/css" href="${context}/css/jquery.alertable.css">
    <script>
        $(document)
          .ready(function() {

            // fix main menu to page on passing
            $('.main.menu').visibility({
              type: 'fixed'
            });
            $('.overlay').visibility({
              type: 'fixed',
              offset: 80
            });

            // show dropdown on hover
            $('.main.menu  .ui.dropdown').dropdown({
              on: 'hover'
            });
          })
        ;
    </script>
    <style>
        #banner {
            background-image: url("${context}/img/banner.png");
            background-size: cover;
            min-height: 70px;
        }
        
        
        #textBanner {
            text-align: center;
            margin-top: 22px;
            font-weight: bold;
            font-size: x-large;
            text-shadow: 2px 5px 15px green;
        }
    </style>
</head>

<body class="site">

    <div id="banner">
        <div id="textBanner">FUTBOL MANAGER</div>
    </div>
    
<div class="ui green inverted main menu">
    <div class="ui container">
      <a href="${context}/index.jsp" class="header item">
        <img class="logo" src="${context}/img/logo.png">
        Futbol Manager
      </a>
      <a href="${context}/index.jsp" class="item">Inicio</a>
      <a href="${context}/CalendarioController" class="item">Calendario de Partidos</a>
      <a href="${context}/ResultadosPartidosController" class="item">Resultados de Partidos</a>
      <a href="${context}/EstadisticasController" class="item">Estadisticas</a>
      <a href="${context}/paginas/buscarJugador.jsp" class="item">Jugadores</a>
      
      <div class="right menu">
          <c:if test="${empty sessionScope.usuario}">
            <a href="${context}/paginas/login.jsp" class="item">Iniciar Sesión</a>
          </c:if>
          
          <c:if test="${sessionScope.usuario.tipoUsuario == 'Administrador'}">
              <div class="ui simple dropdown item">
                Administrador <i class="dropdown icon"></i>
                <div class="menu">
                  <a class="item" href="${context}/EntrenadorController">Mantenimiento Entrenadores</a>
                  <a class="item" href="${context}/JugadorController">Mantenimiento Jugadores</a>
                  <a class="item" href="${context}/EquipoController">Mantenimiento Equipos</a>
                  <a class="item" href="${context}/ArbitroController">Mantenimiento Arbitros</a>
                  <a class="item" href="${context}/ProgramacionPartidoController">Programación de Partidos</a>
                  <a class="item" href="${context}/ControlPartidoController">Control de Partidos</a>
                  <a class="item" href="${context}/TorneoController">Torneos</a>
                  <div class="divider"></div>
                  <a class="item" href="${context}/LoginServlet?cerrar=1">Cerrar Sesión</a>
                  
                </div>
              </div> 
          </c:if>
          <c:if test="${sessionScope.usuario.tipoUsuario == 'Entrenador'}">
              <div class="ui simple dropdown item">
                Entrenador: ${sessionScope.usuario.nombreUsuario} <i class="dropdown icon"></i>
                <div class="menu">
                  <a class="item" href="${context}/ControlPartidoController">Control de Partidos</a>
                  <a class="item" href="${context}/ProgramacionPartidoController">Programación de Partidos</a>
                  <a class="item" href="${context}/EntrenadorJugadores">Mi Equipo</a>
                  <div class="divider"></div>
                  <a class="item" href="${context}/LoginServlet?cerrar=1">Cerrar Sesión</a>
                  
                </div>
              </div> 
          </c:if>
            
      </div>
    </div>
</div>
<div class="content">