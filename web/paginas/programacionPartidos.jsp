
<jsp:include page="../templates/header.jsp" flush="true">
    <jsp:param name="title" value="Programación de Partidos" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="${pageContext.request.contextPath}" />


<c:choose>
    <c:when test="${applicationScope.torneo.programacionFinalizada == 0}">
        <div class="ui grid">
    
            <select id="equiposBank" style="display: none;">
                <c:forEach items="${equipos}" var="equipo">
                    <option value="${equipo.id}">${equipo.nombre}</option>
                </c:forEach>
            </select>


            <div class="twelve wide centered column">

                <h1 class="ui dividing center aligned header">Programación de Partidos</h1>
                
                <form action="${context}/ProgramacionPartidoController" method="POST" class="ui form" onsubmit="check(event)" id="formu">

                    <div class="field">
                        <div class="three fields">
                            <div class="field">
                                <label>LOCAL</label>
                                <select name="equipoLocalId" id="equipoLocal" onchange="updateSelect()">
                                    <c:forEach items="${equipos}" var="equipo">
                                        <option value="${equipo.id}">${equipo.nombre}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="field">
                                <label id="vs">VS</label>
                            </div>
                            <div class="field">
                                <label>VISITANTE</label>
                                <select name="equipoVisitanteId" id="equipoVisitante">
                                    <c:forEach items="${equipos}" var="equipo">
                                        <option value="${equipo.id}">${equipo.nombre}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="field">
                        <div class="two fields">
                            <div class="field">
                                <div class="ten wide field">
                                    <label>ARBITRO</label>
                                    <select name="arbitroId" id="arbitro">
                                        <c:forEach items="${arbitros}" var="arbitro">
                                            <option value="${arbitro.id}">${arbitro.nombres}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="field">
                                <div class="eight wide field" id="fechaJorn2Field">
                                    <label class="centerLabel">FECHA PARTIDO</label>
                                    <input type="date" name="fechaJornada1" id="fechaJorn1" required="true" />
                                </div>
                            </div>
                        </div>
                    </div>

                    <button class="ui secondary button" type="button" id="agregarEquipo">Registrar Partido</button>
                    <button class="ui primary button" type="submit" name="submited">Finalizar Programación</button>
                    
                    <table class="ui celled small orange table" id="tablaEquipoProg">
                        <thead>
                            <tr class="center aligned">
                                <th>LOCAL</th>
                                <th>VISITANTE</th>
                                <th>FECHA</th>
                                <th>ARBITRO</th>
                            </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                    
                </form>

                

            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="ui grid">
            <div class="eight wide centered column">
                <div class="ui black huge message">
                    <h1 class="ui center aligned header" style="color:white;">PROGRAMACIÓN FINALIZADA</h1>
                    <strong>${applicationScope.msjTor}</strong>
                    <p>La programación de partidos finalizó para este torneo.</p>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>

<script src="${context}/js/moment.js"></script>


<script>

    function check(event) {
        
        var rowCount = $('#tablaEquipoProg tr').length;
        
        
        if(!rowCount > 1) {
            event.preventDefault();
            $.alertable.alert('<strong>Vacío</strong> <br /> No Ha Programado Ningun Partido', 
                                {
                                    html: true
                                });
        } 
        
    }


    $(document).ready(function() {
        var index = $("#equipoLocal option:selected").index();
        $('#equipoVisitante option:eq(' + index + ')').remove();   
    });
    
    function cloneSelect() {
        var $options = $("#equiposBank > option").clone();
        $("#equipoVisitante").empty();
        $("#equipoVisitante").append($options);
    }
    
    
    function updateSelect() {
        cloneSelect();
        var value = $("#equipoLocal option:selected").val();
        $('#equipoVisitante option[value='+value+']').remove();
    }
    
    function fechasCorrectas() {
        var fechaJorn1 = $("#fechaJorn1").val();
        var fechaPartido = moment(fechaJorn1);
        var currDate = moment();

       if(fechaJorn1 === "" || moment(fechaPartido).isBefore(currDate, 'day') ) {
            return false;
       } 
        
        return true;
        
    }

    
    function isEmptyLocalSelect() {
        if($('#equipoLocal').has('option').length > 0) {
            return false;
        } 
        return true;
    }
    
    function isSelectVisitanteVacio() {
        if($('#equipoVisitante').has('option').length > 0) {
            return false;
        } 
        return true;
    }
    
    function insertPrimeras6(tabla, localNombre, localId, visitanteNombre, visitanteId, fechaJorn1, arbitroId, arbitroNombre) {
        var row = tabla.insertRow(-1);
        row.className = "center aligned";

        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        var cell4 = row.insertCell(3);


        cell1.innerHTML = "<input type='hidden' name='localesId' value='"+localId+"'>"+localNombre;
        cell2.innerHTML = "<input type='hidden' name='visitantesId' value='"+visitanteId+"'>"+visitanteNombre;
        cell3.innerHTML = "<input type='hidden' name='fechas' value='"+fechaJorn1+"'>"+fechaJorn1;
        cell4.innerHTML = "<input type='hidden' name='arbitros' value='"+arbitroId+"'>"+arbitroNombre;

    }    
    
    $("#agregarEquipo").click(function() {
        if( fechasCorrectas() ) {
            var tabla = document.querySelector('#tablaEquipoProg tbody');
            var localNombre = $("#equipoLocal option:selected").text();
            var localId = $("#equipoLocal option:selected").val();
            var visitanteNombre = $("#equipoVisitante option:selected").text();
            var visitanteId = $("#equipoVisitante option:selected").val();
            var fechaJorn1 = $("#fechaJorn1").val();
            var arbitroId = $("#arbitro option:selected").val();
            var arbitroNombre = $("#arbitro option:selected").text();
            
            if(!isSelectVisitanteVacio()) {
                
                insertPrimeras6(tabla, localNombre, localId, visitanteNombre, visitanteId, fechaJorn1, arbitroId, arbitroNombre);
                $("#equipoVisitante option:selected").remove();
                $("#equipoLocal").prop('disabled', true);

                if(isSelectVisitanteVacio()) {
                    $("#equipoLocal option:selected").remove();
                    $("#equipoLocal").prop('disabled', false);

                    if( !isEmptyLocalSelect() ) {
                        updateSelect();
                    } else {
                        $.alertable.alert('<strong>Vacío</strong> <br /> Ya no hay más equipos disponibles', 
                                {
                                    html: true
                                });
                    }

                }
                
            } else {
                $.alertable.alert('<strong>Vacío</strong> <br /> Ya no hay más equipos disponibles', 
                                {
                                    html: true
                                });
            }
            
            
        } else {
            $.alertable.alert('<strong>Fecha Incorrecta</strong> <br /> La fecha no deben ser nula o anterior a la actual.', 
            {
                html: true
            });
        }
        
        
    });

            
</script>

        
        
<jsp:include page="../templates/footer.jsp" flush="true" />
