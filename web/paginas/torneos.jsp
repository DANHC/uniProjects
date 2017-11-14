<jsp:include page="../templates/header.jsp" flush="true">
    <jsp:param name="title" value="Torneos" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="${pageContext.request.contextPath}" />

<div class="ui grid">
    <div class="ten wide centered column">
        <h1 class="ui dividing center aligned header">Agregar Nuevo Torneo</h1>
        
        
        <form action="${context}/TorneoController" method="POST" class="ui form error" id="formTor">
                
                <div class="field">
                    <label>Nombre de Torneo</label>
                    <input type="text" name="torneoNombre" placeholder="Torneo" required="true">
                </div>

                <div class="ui error message">
                    <c:if test="${!empty msjTorni}">
                        ${msj}
                    </c:if>
                </div>
                
                <button class="ui button" type="submit" name="insertar">Registrar</button>
                
            </form>
            
            <table class="ui celled inverted small green table">
                <thead>
                    <tr class="center aligned">
                        <th>ID</th>
                        <th>NOMBRE DE TORNEO</th>
                        <th>FINALIZADO</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="tor" items="${torneos}">
                        <tr class="center aligned">
                            <td>${tor.id}</td>
                            <td>${tor.nombreTorneo}</td>
                            <td>${tor.finalizado}</td>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        
        
    </div>
    
    
    
    
</div>






<jsp:include page="../templates/footer.jsp" flush="true" />