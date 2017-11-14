
<%@include file="../templates/header.jsp" %> 

    <c:if test="${sessionScope.usuario.tipoUsuario != 'Entrenador'}">
        <c:redirect url="/paginas/login.jsp" />
    </c:if>


    <h1>Bienvenido Entrenador</h1>

    
<%@include file="../templates/footer.jsp" %> 