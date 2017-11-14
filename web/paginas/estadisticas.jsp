<jsp:include page="../templates/header.jsp" flush="true">
    <jsp:param name="title" value="Estadisticas" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="${pageContext.request.contextPath}" />

<c:choose>
    <c:when test="${goleador.numGoles != 0}">
        <div class="ui grid">
    
            <div class="eight wide centered column">

                <h2 class="ui dividing center aligned header">ESTADISTICAS DE EQUIPOS</h2>

                <table class="ui celled striped small red table" id="tablaEquipoProg">
                    <caption id="estadisticsHeader">TABLAS</caption>
                    <thead>
                        <tr class="center aligned">
                            <th>POS.</th>
                            <th colspan="2">EQUIPO</th>
                            <th>PJUG</th>
                            <th>PG</th>
                            <th>EM</th>
                            <th>PER</th>
                            <th>GLFAV</th>
                            <th>GLCON</th>
                            <th>PNTS</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${estadisticas}" var="estadistica">
                            <tr class="center aligned">
                                <td>${estadistica.id}</td>
                                <td>
                                    <img src="${context}/img/${estadistica.equipo.rutaLogo}" height="50" width="50" />
                                </td>
                                <td>${estadistica.equipo.nombre}</td>
                                <td>${estadistica.partidosJugados}</td>
                                <td>${estadistica.partidosGanados}</td>
                                <td>${estadistica.partidosEmpatados}</td>
                                <td>${estadistica.partidosPerdidos}</td>
                                <td>${estadistica.golesAfavor}</td>
                                <td>${estadistica.golesEnContra}</td>
                                <td>${estadistica.puntos}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>


            </div>

            <div class="five wide centered column">

                 <h2 class="ui dividing center aligned header">GOLEADOR SUPREMO</h2>

                 <div class="ui card" id="cardGoleador">
                     <div class="image">
                         <img src="${context}/img/${goleador.rutaFoto}" id="imgGoleador" />
                     </div>
                     <div class="content">
                         <h3 class="header">${goleador.nombres} ${goleador.apelllidos}</h3>
                         <div class="description">
                             <strong>Goles:</strong> ${goleador.numGoles} <br />
                             <strong>Equipo:</strong> ${goleador.equipo.nombre} <br />
                             <strong>Nacionalidad:</strong> ${goleador.nacionalidad} <br />
                             <strong>Posición:</strong> ${goleador.posicion}
                         </div>
                     </div>
                 </div>

            </div>
    
        </div>

                 
        <div class="ui grid">
            <div class="nine wide column">
                <div id="chartContainer" style="height: 300px; width: 100%; margin-left: 38px;"></div>
            </div>
        </div>
                         
    </c:when>
    <c:otherwise>
        <div class="ui grid">
            <div class="ten wide centered column">
                <div class="ui black huge message">
                    <h1 class="ui center aligned header" style="color:white;">Estadisticas aún no disponibles</h1>
                    <p>Las estadisticas aún no están disponibles. Lo sentimos. Vuelva pronto.</p>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>




                 
                 
                 
<script src="${context}/js/canvasjs.min.js"></script>            
                 
                 
<script>
    
    $(document).ready(function() {
        var index = $("#equipoLocal option:selected").index();
        $('#equipoVisitante option:eq(' + index + ')').remove();
        
        var chart = new CanvasJS.Chart("chartContainer", {
		title:{
			text: "3 PRIMERAS POSICIONES - PUNTOS"              
		},
		data: [              
		{
			// Change type to "doughnut", "line", "splineArea", etc.
			type: "column",
            
            dataPoints: [
            <c:forEach begin="0" end="2" varStatus="loop">
                <c:choose>
                    <c:when test="${!loop.last}">
                        { label: "${estadisticas[loop.index].equipo.nombre}", y: ${estadisticas[loop.index].puntos} },
                    </c:when>
                    <c:otherwise>
                        { label: "${estadisticas[loop.index].equipo.nombre}", y: ${estadisticas[loop.index].puntos} }
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            ]
		}
		]
	});
	chart.render();
        
        
        
    });
    
    
    
</script>            

<jsp:include page="../templates/footer.jsp" flush="true" />