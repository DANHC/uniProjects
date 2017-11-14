
<%@include file="../templates/header.jsp" %> 


<div class="ui grid" id="login">
    <div class="seven wide centered column">
        <h1 class="ui center aligned header">Login</h1>
        
        <form action="${context}/LoginServlet" method="POST" class="ui form">
            
            <c:if test="${!empty message}">
                <div class="ui form error">
                    <div class="ui error message">
                        <div class="header">Error</div>
                        <p>${message}</p>
                    </div>
                </div>
            
                <br>
            </c:if>
            
            <div class="required field">
                <label>Usuario:</label>
                <input type="text" name="nombreUsuario" placeholder="Usuario" required="true">
            </div>
            <div class="required field">
                <label>Contraseña:</label>
                <input type="password" name="contraUsuario" placeholder="Contraseña" required="true">
            </div>
            <button class="ui button" type="submit" name="verificarUser">Iniciar Sesión</button>
        </form>
        
    </div>
</div>

<%@include file="../templates/footer.jsp" %> 