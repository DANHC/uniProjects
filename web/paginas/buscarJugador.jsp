<jsp:include page="../templates/header.jsp" flush="true">
    <jsp:param name="title" value="Control de Partido" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}" />



<div class="ui grid">
    <div class="twelve wide centered column">
        
        <h3 class="ui dividing center aligned header">Búsqueda de Jugadores</h3>
        
        <form action="${context}/JugadorSearch" method="POST" class="ui form">
            
            <div class="field">
                <div class="required field">
                    <div class="one fields">
                        <label>Buscar jugador:</label>
                        <input type="text" name="busquedaString" required="true" placeholder="Nombre o equipo">
                    </div>
                </div>
            </div>
            
            <button class="ui button" type="submit" name="buscar">Buscar Jugador(es)</button>
            
        </form>
        
        <hr />
        
        <c:choose>
            <c:when test="${!empty jugadores}">
                <div id="listJugadores">
                    <div class="ui divided items">
                            <c:forEach items="${jugadores}" var="jugador">
                                <div class="item">
                                    <div class="ui tiny image">
                                      <img src="${context}/img/${jugador.rutaFoto}">
                                    </div>
                                    <div class="middle aligned content">
                                        <p class="header">${jugador.nombres} ${jugador.apelllidos}</p>
                                        <div class="meta">
                                            <span>${jugador.equipo.nombre}</span>
                                        </div>
                                        <div class="description">
                                            <strong>Edad: </strong> ${jugador.edad}, 
                                            <strong>Estatura: </strong> ${jugador.estatura}, 
                                            <strong>Nacionalidad: </strong> ${jugador.nacionalidad}, 
                                            <strong>N° jugador: </strong> ${jugador.numeroJugador}, 
                                            <strong>Posición: </strong> ${jugador.posicion}
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                    </div>
                </div>
            </c:when>
            <c:when test="${!empty ninguno}">
                <div class="ui small message">
                    ${ninguno}
                </div>
            </c:when>
        </c:choose>
        
        
        
        
            
        
        
        
        
        
    </div>
</div>




<jsp:include page="../templates/footer.jsp" flush="true" />