<%@include file="../templates/header.jsp" %> 

<c:if test="${sessionScope.usuario.tipoUsuario != 'Administrador'}">
    <c:redirect url="/paginas/login.jsp" />
</c:if>
    

<div class="ui grid">
    <div class="thirteen wide centered column">
        <h1 class="ui center aligned header">Mantenimiento Jugadores</h1>
        
        <form action="${context}/JugadorController" method="POST" class="ui form error" enctype="multipart/form-data">
            
            <input type="hidden" name="jugadorId">
                <div class="field">
                    <div class="three fields">
                      <div class="required field">
                        <label>Nombres</label>
                        <input type="text" name="jugadorNombre" placeholder="Nombres" required="true">
                      </div>
                      <div class="required field">
                        <label>Apellidos</label>
                        <input type="text" name="jugadorApellido" placeholder="Apellidos" required="true">
                      </div>
                      <div class="required field">
                          <label>Edad</label>
                          <input type="number" min="18" max="90" name="jugadorEdad" placeholder="Edad" required="true">
                      </div>
                    </div>
                </div>
                
                <div class="field">
                    <div class="three fields">
                      <div class="required field">
                        <label>Estatura en CM</label>
                        <input type="text" name="jugadorEstatura" placeholder="Estatura en CM" required="true">
                      </div>
                      <div class="required field">
                        <label>Nacionalidad</label>
                        <input type="text" name="jugadorNacionalidad" placeholder="Nacionalidad" required="true">
                      </div>
                      <div class="required field">
                          <label># de jugador</label>
                          <input type="number" min="1" name="jugadorNumero" placeholder="# de jugador" required="true">
                      </div>
                    </div>
                </div>
                
                <div class="field">
                    <div class="four fields">
                      <div class="required field">
                        <label>Posición en cancha</label>
                        <input type="text" name="jugadorPosicion" placeholder="Posición" required="true">
                      </div>
                      <div class="required field">
                        <label>Foto</label>
                        <input type="file" name="jugadorFoto" required="true">
                      </div>
                      <div class="required field">
                          <label>Estado</label>
                          <select class="ui fluid dropdown" name="jugadorDescalificado" disabled="true">
                              <option value="0">Activo</option>
                              <option value="1">Descalificado</option>
                          </select>
                      </div>
                      <div class="required field">
                          <label>Equipo</label>
                          <select class="ui fluid dropdown" name="jugadorEquipo">
                              <c:forEach var="equipo" items="${equipos}">
                                  <option value="${equipo.id}">${equipo.nombre}</option>
                              </c:forEach>
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
                        <th>NOMBRE</th>
                        <th>APELLIDO</th>
                        <th>EDAD</th>
                        <th>ESTATURA</th>
                        <th>NACIONALIDAD</th>
                        <th>NUMEROJUGADOR</th>
                        <th>POSICION</th>
                        <th>FOTO</th>
                        <th>DESCALIFICADO</th>
                        <th>EQUIPO</th>
                        <th>ACCION</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="jug" items="${jugadores}">
                        <tr>
                            <td>${jug.id}</td>
                            <td>${jug.nombres}</td>
                            <td>${jug.apelllidos}</td>
                            <td>${jug.edad}</td>
                            <td>${jug.estatura}</td>
                            <td>${jug.nacionalidad}</td>
                            <td>${jug.numeroJugador}</td>
                            <td>${jug.posicion}</td>
                            <td><img src="${context}/img/${jug.rutaFoto}" width="40" height="40"></td>
                            <td>${jug.descalificado}</td>
                            <td>${jug.equipo.nombre}</td>
                            <td>
                                <button class="tiny ui button" id="editar" onclick="cargar(${jug.id}, '${jug.nombres}', '${jug.apelllidos}', ${jug.edad}, ${jug.estatura}, '${jug.nacionalidad}', ${jug.numeroJugador}, '${jug.posicion}', ${jug.descalificado}, ${jug.equipo.id})">
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
    function cargar(jugadorId, nombres, apellidos, edad, estatura, nacionalidad, numeroJugador, posicion, descalificado, equipoId) {
        $('[name="jugadorId"]').val(jugadorId);
        $('[name="jugadorNombre"]').val(nombres);
        $('[name="jugadorApellido"]').val(apellidos);
        $('[name="jugadorEdad"]').val(edad);
        $('[name="jugadorEstatura"]').val(estatura);
        $('[name="jugadorNacionalidad"]').val(nacionalidad);
        $('[name="jugadorNumero"]').val(numeroJugador);
        $('[name="jugadorPosicion"]').val(posicion);
        
        $('[name="jugadorDescalificado"]').removeAttr('disabled').val(descalificado);
        
        $('[name="jugadorFoto"]').removeAttr('required');
        $('[name="modificar"]').removeAttr('disabled');
        $('[name="insertar"]').prop('disabled', true);
    }
</script>
        
        
<%@include file="../templates/footer.jsp" %> 
