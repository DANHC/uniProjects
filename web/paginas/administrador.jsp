
<%@include file="../templates/header.jsp" %> 

<c:if test="${sessionScope.usuario.tipoUsuario != 'Administrador'}">
    <c:redirect url="/paginas/login.jsp" />
</c:if>

<div class="ui grid">
    <div class="thirteen wide centered column">
        
        <h1 class="ui dividing center aligned header">Mantenimiento Entrenadores</h1>
        
            <form action="${context}/EntrenadorController" method="POST" class="ui form error" id="formEntre">
                <input type="hidden" name="entrenadorId">
                <input type="hidden" name="entrenadorUsuarioId">
                <div class="field">
                    <div class="three fields">
                      <div class="required field">
                        <label>Nombres</label>
                        <input type="text" name="entrenadorNombre" placeholder="Nombres" required="true">
                      </div>
                      <div class="required field">
                        <label>Apellidos</label>
                        <input type="text" name="entrenadorApellido" placeholder="Apellidos" required="true">
                      </div>
                      <div class="required field">
                          <label>Edad</label>
                          <input type="number" min="18" max="90" name="entrenadorEdad" placeholder="Edad" required="true">
                      </div>
                    </div>
                </div>
                
                <div class="field">
                    <div class="two fields">
                      <div class="required field">
                       <label>Sexo</label>
                        <select class="ui fluid dropdown" name="entrenadorSexo">
                              <option value="Masculino">Masculino</option>
                              <option value="Femenino">Femenino</option>
                        </select>
                      </div>
                      <div class="required field">
                        <label>Nacionalidad</label>
                        <input type="text" name="entrenadorNacionalidad" required="true">
                      </div>
                      <div class="required field">
                          <label>Estado</label>
                          <select class="ui fluid dropdown" name="entrenadorEstado" disabled="true">
                              <option value="0">Activo</option>
                              <option value="1">Descalificado</option>
                          </select>
                      </div>
                    </div>
                </div>
                
                <div class="field">
                    <div class="three fields">
                      <div class="required field">
                        <label>Usuario</label>
                        <input type="text" name="entrenadorUsuario" placeholder="Usuario" required="true">
                      </div>
                      <div class="required field">
                        <label>Contraseña</label>
                        <input type="password" name="entrenadorContra" required="true">
                      </div>
                      <div class="required field">
                        <label>Repita Contraseña</label>
                        <input type="password" name="entrenadorRepiteContra" required="true">
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
                        <th>USUARIO ID</th>
                        <th>USUARIO</th>
                        <th>NOMBRES</th>
                        <th>APELLIDOS</th>
                        <th>EDAD</th>
                        <th>SEXO</th>
                        <th>NACIONALIDAD</th>
                        <th>ELIMINADO</th>
                        <th colspan="2">ACCIONES</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="en" items="${entrenadores}">
                        <tr>
                            <td>${en.id}</td>
                            <td>${en.usuario.id}</td>
                            <td>${en.usuario.nombreUsuario}</td>
                            <td>${en.nombres}</td>
                            <td>${en.apellidos}</td>
                            <td>${en.edad}</td>
                            <td>${en.sexo}</td>
                            <td>${en.nacionalidad}</td>
                            <td>${en.eliminado}</td>
                            <td>
                                <button class="tiny ui button" id="editar" onclick="cargarEntrenador(${en.id}, ${en.usuario.id}, '${en.usuario.nombreUsuario}', '${en.nombres}', '${en.apellidos}', ${en.edad}, '${en.sexo}', '${en.nacionalidad}', ${en.eliminado}, '${en.usuario.contra}')">
                                    Editar
                                </button>
                            </td>
                            <td>
                                <a class="tiny ui button" href="EntrenadorController?eliminarEntrenador=${en.id}&usuarioId=${en.usuario.id}">Eliminar</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
        
        
    </div>
</div>

<script>
   
    $('#formEntre')
    .form({
      on: 'blur',
      fields: {
        match: {
          identifier  : 'entrenadorRepiteContra',
          rules: [
            {
              type   : 'match[entrenadorContra]',
              prompt : '<strong><pre>   LAS CONTRASEÑAS DEBEN COINCIDIR</pre></strong>'
            }
          ]
        }
      }
    })
    ;
    
    function cargarEntrenador(entId, userId, nombreUsuario, nombres, apellidos, edad, sexo, nacionalidad, eliminado, contra) {
        $('[name="entrenadorId"]').val(entId);
        $('[name="entrenadorUsuarioId"]').val(userId);
        $('[name="entrenadorUsuario"]').val(nombreUsuario);
        $('[name="entrenadorNombre"]').val(nombres);
        $('[name="entrenadorApellido"]').val(apellidos);
        $('[name="entrenadorEdad"]').val(edad);
        $('[name="entrenadorSexo"]').val(sexo);
        $('[name="entrenadorNacionalidad"]').val(nacionalidad);
        $('[name="entrenadorEstado"]').removeAttr('disabled').val(eliminado);
        $('[name="entrenadorContra"]').val(contra);
        $('[name="entrenadorRepiteContra"]').val(contra);
        
        $('[name="modificar"]').removeAttr('disabled');
        $('[name="insertar"]').prop('disabled', true);
    }
    
    
</script>

<%@include file="../templates/footer.jsp" %> 