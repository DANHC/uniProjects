<jsp:include page="../templates/header.jsp" flush="true">
    <jsp:param name="title" value="Resultados de Partidos" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="${pageContext.request.contextPath}" />

<c:choose>
    <c:when test="${!empty partidos}">
        <div class="ui grid">
    
            <div class="twelve wide centered column">

                <h2 class="ui dividing center aligned header">RESULTADOS DE PARTIDOS</h2>

                <table class="ui very compact celled striped small red table" id="tablaEquipoProg">
                    <caption id="estadisticsHeader">TABLAS</caption>
                    <thead>
                        <tr class="center aligned">
                            <th colspan="3">LOCAL</th>
                            <th>VS</th>
                            <th colspan="3">VISITANTE</th>
                            <th>ARBITRO</th>
                            <th>FECHA</th>
                            <th>GOLES</th>
                            <th>FALTAS</th>
                            <th>CORNERS</th>
                            <th>PENALTIS</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${partidos}" var="part">
                            <tr class="center aligned">
                                <td>${part.prog.equipoLocal.nombre}</td>
                                <td>
                                    <img src="${context}/img/${part.prog.equipoLocal.rutaLogo}" width="50" height="50" />
                                </td>
                                <td class="active">${part.golesLocal}</td>
                                <td>VS</td>
                                <td class="active">${part.golesVisitante}</td>
                                <td>
                                    <img src="${context}/img/${part.prog.equipoVisitante.rutaLogo}" width="50" height="50" />
                                </td>
                                <td>${part.prog.equipoVisitante.nombre}</td>
                                <td>${part.prog.arbitro.nombres}</td>
                                <td>${part.prog.fecha}</td>
                                <td>${part.totalGoles}</td>
                                <td>${part.totalFaltas}</td>
                                <td>${part.totalCorner}</td>
                                <td>${part.totalPenalti}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>
        
    </c:when>
    <c:otherwise>
        <div class="ui grid">
            <div class="ten wide centered column">
                <div class="ui black huge message">
                    <h1 class="ui center aligned header" style="color:white;">Resultados aún no disponibles</h1>
                    <p>Aún no se ha jugado ningun partido, lo sentimos. Vuelva pronto.</p>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>







<jsp:include page="../templates/footer.jsp" flush="true" />