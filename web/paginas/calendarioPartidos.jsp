<jsp:include page="../templates/header.jsp" flush="true">
    <jsp:param name="title" value="Calendarización" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="${pageContext.request.contextPath}" />


<c:choose>
    <c:when test="${!empty programaciones}">
        <div class="ui grid">
    
            <div class="twelve wide centered column">

                <h2 class="ui dividing center aligned header">CALENDARIZACIÓN DE PARTIDOS</h2>


                <small>1 = FINALIZADO, 0 = PENDIENTE</small>

                <table class="ui very compact celled striped small red table" id="tablaEquipoProg">
                    <caption id="estadisticsHeader">TABLAS</caption>
                    <thead>
                        <tr class="center aligned">
                            <th colspan="2">LOCAL</th>
                            <th>VS</th>
                            <th colspan="2">VISITANTE</th>
                            <th>ARBITRO</th>
                            <th>FECHA</th>
                            <th>FINALIZADO</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${programaciones}" var="pro">
                            <tr class="center aligned">
                                <td>${pro.equipoLocal.nombre}</td>
                                <td>
                                    <img src="${context}/img/${pro.equipoLocal.rutaLogo}" width="50" height="50" />
                                </td>
                                <td>VS</td>
                                <td>
                                    <img src="${context}/img/${pro.equipoVisitante.rutaLogo}" width="50" height="50" />
                                </td>
                                <td>${pro.equipoVisitante.nombre}</td>
                                <td>${pro.arbitro.nombres}</td>
                                <td>${pro.fecha}</td>
                                <td>${pro.finalizado}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>
        
    </c:when>
    <c:otherwise>
        <div class="ui grid">
            <div class="eight wide centered column">
                <div class="ui black huge message">
                    <h1 class="ui center aligned header" style="color:white;">Calendario aún no disponible</h1>
                    <p>Aún no se ha programado ningún partido. Lo sentimos.</p>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>











<jsp:include page="../templates/footer.jsp" flush="true" />