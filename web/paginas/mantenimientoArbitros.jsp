<%@include file="../templates/header.jsp" %> 

<c:if test="${sessionScope.usuario.tipoUsuario != 'Administrador'}">
    <c:redirect url="/paginas/login.jsp" />
</c:if>
    
<div class="ui grid">
    <div class="thirteen wide centered column">
        
        <h1 class="ui dividing center aligned header">Mantenimiento Entrenadores</h1>
        
            <form action="${context}/ArbitroController" method="POST" class="ui form error" id="formEntre">
                <input type="hidden" name="arbitroId">
                
                <div class="field">
                    <div class="three fields">
                      <div class="required field">
                        <label>Nombres</label>
                        <input type="text" name="arbitroNombre" placeholder="Nombres" required="true">
                      </div>
                      <div class="required field">
                        <label>Apellidos</label>
                        <input type="text" name="arbitroApellido" placeholder="Apellidos" required="true">
                      </div>
                      <div class="required field">
                          <label>Edad</label>
                          <input type="number" min="18" max="90" name="arbitroEdad" placeholder="Edad" required="true">
                      </div>
                    </div>
                </div>
                
                <div class="field">
                    <div class="two fields">
                      <div class="required field">
                       <label>Sexo</label>
                        <select class="ui fluid dropdown" name="arbitroSexo">
                              <option value="Masculino">Masculino</option>
                              <option value="Femenino">Femenino</option>
                        </select>
                      </div>
                      <div class="required field">
                        <label>Nacionalidad</label>
                        <input type="text" name="arbitroNacionalidad" required="true">
                      </div>
                      <div class="required field">
                          <label>Estado</label>
                          <select class="ui fluid dropdown" name="arbitroEstado" disabled="true">
                              <option value="0">Activo</option>
                              <option value="1">Descalificado</option>
                          </select>
                      </div>
                    </div>
                </div>
                
                
                <div class="ui error message">
                    <c:if test="${!empty msj}">
                        ${msj}
                    </c:if>
                </div>
                
                <button class="ui button" type="submit" name="insertar">Registrar</button>
                <button class="ui button" type="submit" name="modificar" disabled="true">Modificar</button>
            </form>
            
            <table class="ui celled inverted small green table">
                <thead>
                    <tr class="center aligned">
                        <th>ID</th>
                        <th>NOMBRES</th>
                        <th>APELLIDOS</th>
                        <th>EDAD</th>
                        <th>SEXO</th>
                        <th>NACIONALIDAD</th>
                        <th>ELIMINADO</th>
                        <th>CARGAR</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="ar" items="${arbitros}">
                        <tr>
                            <td>${ar.id}</td>
                            <td>${ar.nombres}</td>
                            <td>${ar.apellidos}</td>
                            <td>${ar.edad}</td>
                            <td>${ar.sexo}</td>
                            <td>${ar.nacionalidad}</td>
                            <td>${ar.eliminado}</td>
                            <td>
                                <button class="tiny ui button" id="editar" onclick="cargarArbitro(${ar.id}, '${ar.nombres}', '${ar.apellidos}', ${ar.edad}, '${ar.sexo}', '${ar.nacionalidad}', ${ar.eliminado})">
                                    Editar
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
        
        
    </div>
</div>

<script>
    
    function cargarArbitro(arId, nombres, apellidos, edad, sexo, nacionalidad, eliminado) {
        $('[name="arbitroId"]').val(arId);
        $('[name="arbitroNombre"]').val(nombres);
        $('[name="arbitroApellido"]').val(apellidos);
        $('[name="arbitroEdad"]').val(edad);
        $('[name="arbitroSexo"]').val(sexo);
        $('[name="arbitroNacionalidad"]').val(nacionalidad);
        $('[name="arbitroEstado"]').removeAttr('disabled').val(eliminado);
        
        $('[name="modificar"]').removeAttr('disabled');
        $('[name="insertar"]').prop('disabled', true);
    }
    
    
</script>


    
<%@include file="../templates/footer.jsp" %> 
