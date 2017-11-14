<jsp:include page="../templates/header.jsp" flush="true">
    <jsp:param name="title" value="Control de Partido" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}" />


<c:choose>
    <c:when test="${!empty prog}">
        <div class="ui grid">
            <div class="twelve wide centered column">
                <h1 class="ui dividing center aligned header">Control de Partido</h1>

                <form action="${context}/ControlPartidoController" method="POST" class="ui form">

                    <input type="hidden" name="idProgramacionPartido" value="${prog.id}" />
                    <input type="hidden" name="totalGolesLocal" id="totalGolesLocal" value="0" />
                    <input type="hidden" name="totalGolesVisitante" id="totalGolesVisitante" value="0" />
                    <input type="hidden" name="equipoLocalId" value="${prog.equipoLocal.id}" />
                    <input type="hidden" name="equipoVisitanteId" value="${prog.equipoVisitante.id}" />

                    <div class="field">
                        <div class="three fields">
                            <div class="field">
                                <label>LOCAL</label>
                                <div class="ui blue card" >
                                        <img src="${context}/img/${prog.equipoLocal.rutaLogo}" class="equipoPartido" />
                                    <div class="content">
                                        <h2 class="center aligned header">${prog.equipoLocal.nombre}</h2>
                                        <h3 class="center aligned header">GOLES: <span id="golLocal">0</span></h3>
                                    </div>
                                </div>
                            </div>
                            <div class="field">
                                <button type="submit" class="ui primary button" id="terminarPartido" name="partidoTerminado">Terminar Partido</button>
                                <label id="vsControl">VS</label>
                                <br />
                                <br />
                                <h3 class="ui right floated header">
                                    CORNERS: 
                                    <a href="javascript:void(0)" id="substractCorner"> - </a>
                                    <span class="corners" id="corner">0</span>
                                    <a href="javascript:void(0)" id="addCorner"> + </a>
                                    <input type="hidden" name="corners" value="0" />
                                </h3>
                                <h3 class="ui left floated header">
                                    PENALTIS: 
                                    <a href="javascript:void(0)" id="substractPenalti"> - </a>
                                    <span class="penaltis" id="penalti">0</span>
                                    <a href="javascript:void(0)" id="addPenalti"> + </a>
                                    <input type="hidden" name="penaltis" value="0" />
                                </h3>
                            </div>
                            <div class="field">
                                <label id="equipoVisitanteLabel">VISITANTE</label>
                                <div class="ui teal card" id="visitanteCard">
                                        <img src="${context}/img/${prog.equipoVisitante.rutaLogo}" class="equipoPartido" />
                                    <div class="content">
                                        <h2 class="center aligned header">${prog.equipoVisitante.nombre}</h2>
                                        <h3 class="center aligned header">GOLES: <span id="golVisitante">0</span></h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="field">
                        <div class="three fields">
                            <div class="field">
                                <label class="centerLabel">Jugador</label>
                                <select name="idJugadorLocal" id="jugadoresLocal">
                                    <c:forEach items="${jugadoresLocal}" var="jugadorLocal">
                                        <option value="${jugadorLocal.id}">${jugadorLocal.nombres}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="field">

                            </div>

                            <div class="field">
                                <label class="centerLabel">Jugador</label>
                                <select name="idJugadorVisitante" id="jugadoresVisitante">
                                    <c:forEach items="${jugadoresVisitante}" var="jugadorVisitante">
                                        <option value="${jugadorVisitante.id}">${jugadorVisitante.nombres}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="field">
                        <div class="three fields">
                            <div class="field">
                                <label class="centerLabel">Falta</label>
                                <select name="faltaJugadorLocal" id="faltasLocal">
                                    <option value="Tarjeta Amarilla">Tarjeta Amarilla</option>
                                    <option value="Tarjeta Roja">Tarjeta Roja</option>
                                </select>
                            </div>

                            <div class="field">

                            </div>

                            <div class="field">
                                <label class="centerLabel">Falta</label>
                                <select name="faltaJugadorVisitante" id="faltasVisitante">
                                    <option value="Tarjeta Amarilla">Tarjeta Amarilla</option>
                                    <option value="Tarjeta Roja">Tarjeta Roja</option>
                                </select>
                            </div>
                        </div>
                    </div>


                    <div class="field">
                        <div class="five fields">
                            <div class="field" style="margin-top: 23px;">
                                <button type="button" id="btnMarcarGolLocal" class="ui green button">Marcar Gol</button>
                            </div>
                            <div class="field" style="margin-top: 23px;">
                                <button type="button" id="btnMarcarFaltaLocal" class="ui red button">Marcar Falta</button>
                            </div>
                            <div class="field" style="margin-top: 23px; width: 685px;">

                            </div>
                            <div class="field" style="margin-top: 23px; ">
                                <button type="button" id="btnMarcarGolVisitante" class="ui green button">Marcar Gol</button>
                            </div>
                            <div class="field" style="margin-top: 23px; ">
                                <button type="button" id="btnMarcarFaltaVisitante" class="ui red button">Marcar Falta</button>
                            </div>
                        </div>
                    </div>                

                    <div class="field">
                        <div class="three fields">
                            <div class="field">
                                <table class="ui very compact celled inverted small blue table" id="tablaGolesLocal">
                                    <caption class="captionLocal">GOLES LOCAL</caption>
                                    <thead>
                                        <tr class="center aligned">
                                            <th>JUGADOR</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>


                            </div>
                            <div class="field">
                                <table class="ui very compact celled inverted small red table" id="tablaFaltas">
                                    <caption class="captionFaltas">FALTAS</caption>
                                    <thead>
                                        <tr class="center aligned">
                                            <th>JUGADOR</th>
                                            <th>FALTA</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>
                            </div>
                            <div class="field">
                                <table class="ui very compact celled inverted small teal table" id="tablaGolesVisitante">
                                    <caption class="captionVisitante">GOLES VISITANTE</caption>
                                    <thead>
                                        <tr class="center aligned">
                                            <th>JUGADOR</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>



                </form>

            </div>
        </div>
    </c:when>
    <c:when test="${!empty noProgramados}">
        <div class="ui grid">
            <div class="ten wide centered column">
                <div class="ui black huge message">
                    <h1 class="ui center aligned header" style="color:white;">Partidos No Programados</h1>
                    <p>Necesita programar los partidos.</p>
                </div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="ui grid">
            <div class="ten wide centered column">
                <div class="ui black huge message">
                    <h1 class="ui center aligned header" style="color:white;">TORNEO FINALIZADO</h1>
                    <p>El torneo finalizó. Registre otro torneo y programe partidos para volver a marcar goles.</p>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>


<script>

    corners = 0;
    penaltis = 0;
    golesLocal = 0;
    golesVisitante = 0;

    $("#addPenalti").click(function() {
        penaltis++;
        $("[name='penaltis']").val(penaltis);
        $("#penalti").text(penaltis);
    });
    
    $("#substractPenalti").click(function() {
        if(penaltis !== 0) {
            penaltis--;
            $("[name='penaltis']").val(penaltis);
            $("#penalti").text(penaltis);
        }
    });
    
    $("#addCorner").click(function() {
        corners++;
        $("[name='corners']").val(corners);
        $("#corner").text(corners);
    });
    
    $("#substractCorner").click(function() {
        if(corners !== 0) {
            corners--;
            $("[name='corners']").val(corners);
            $("#corner").text(corners);
        }
    });
    
    
    $("#btnMarcarGolLocal").click(function() {
        var tabla = document.querySelector("#tablaGolesLocal tbody");
        var indicadorGol = $("#golLocal");
        var jugadorId = $("#jugadoresLocal option:selected").val();
        var jugadorNombre = $("#jugadoresLocal option:selected").text();
        var totalGolesLocal = $("#totalGolesLocal");
        
        insertarGolLocal(tabla, jugadorId, jugadorNombre);
        golesLocal++;
        indicadorGol.text(golesLocal);
        totalGolesLocal.val(golesLocal);
    });
    
    $("#btnMarcarGolVisitante").click(function() {
        var tabla = document.querySelector("#tablaGolesVisitante tbody");
        var indicadorGol = $("#golVisitante");
        var jugadorId = $("#jugadoresVisitante option:selected").val();
        var jugadorNombre = $("#jugadoresVisitante option:selected").text();
        var totalGolesVisitante = $("#totalGolesVisitante");
        
        insertarGolVisitante(tabla, jugadorId, jugadorNombre);
        golesVisitante++;
        indicadorGol.text(golesVisitante);
        totalGolesVisitante.val(golesVisitante);
    });
    
    $("#btnMarcarFaltaLocal").click(function() {
        var tabla = document.querySelector("#tablaFaltas tbody");
        var jugadorId = $("#jugadoresLocal option:selected").val();
        var jugadorNombre = $("#jugadoresLocal option:selected").text();
        var tipoFalta = $("#faltasLocal option:selected").text();
        
        insertarFalta(tabla, jugadorId, jugadorNombre, tipoFalta);
        
    });
    
    $("#btnMarcarFaltaVisitante").click(function() {
        var tabla = document.querySelector("#tablaFaltas tbody");
        var jugadorId = $("#jugadoresVisitante option:selected").val();
        var jugadorNombre = $("#jugadoresVisitante option:selected").text();
        var tipoFalta = $("#faltasVisitante option:selected").text();
        
        insertarFalta(tabla, jugadorId, jugadorNombre, tipoFalta);
    });
    
    function insertarFalta(tabla, jugadorId, jugadorNombre, tipoFalta) {
        var row = tabla.insertRow(-1);
        row.className = "center aligned";
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        cell1.innerHTML = "<input type='hidden' name='idJugadorFalta' value='"+jugadorId+"'>"+jugadorNombre;
        cell2.innerHTML = "<input type='hidden' name='tipoFalta' value='"+tipoFalta+"'>"+tipoFalta;
    }
    
    function insertarGolLocal(tabla, jugadorId, jugadorNombre) {
        var row = tabla.insertRow(-1);
        row.className = "center aligned";
        var cell = row.insertCell(0);
        cell.innerHTML = "<input type='hidden' name='idGoleadoresLocal' value='"+jugadorId+"'>"+jugadorNombre;
    }
    
    function insertarGolVisitante(tabla, jugadorId, jugadorNombre) {
        var row = tabla.insertRow(-1);
        row.className = "center aligned";
        var cell = row.insertCell(0);
        cell.innerHTML = "<input type='hidden' name='idGoleadoresVisitante' value='"+jugadorId+"'>"+jugadorNombre;
    }
    
    
</script>


<jsp:include page="../templates/footer.jsp" flush="true" />
