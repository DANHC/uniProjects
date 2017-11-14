<%@include file="../templates/header.jsp" %> 

<c:if test="${sessionScope.usuario.tipoUsuario != 'Administrador'}">
    <c:redirect url="/paginas/login.jsp" />
</c:if>
    
<div class="ui grid">
    <div class="thirteen wide centered column">
        <h1 class="ui dividing center aligned header">Mantenimiento Equipos</h1>
        
        <form action="${context}/EquipoController" method="POST" class="ui form error" enctype="multipart/form-data" id="formEntre">
            <input type="hidden" name="equipoId">
                <div class="field">
                    <div class="three fields">
                      <div class="required field">
                        <label>Nombre de Equipo</label>
                        <input type="text" name="equipoNombre" placeholder="Nombre de equipo" required="true">
                      </div>
                      <div class="required field">
                        <label>Presidente</label>
                        <input type="text" name="equipoPresidente" placeholder="Presidente(nombre)" required="true">
                      </div>
                      <div class="required field">
                          <label>Estadio</label>
                          <input type="text" name="equipoEstadio" placeholder="Estadio" required="true">
                      </div>
                    </div>
                </div>
                
                <div class="field">
                    <div class="three fields">
                      <div class="required field">
                        <label>Ubicación (Provincia, País)</label>
                        <input type="text" name="equipoUbicacion" placeholder="Ubicación" required="true">
                      </div>
                      <div class="required field">
                        <label>Estado</label>
                        <select class="ui fluid dropdown" name="equipoEstado" disabled="true">
                              <option value="0">Activo</option>
                              <option value="1">Descalificado</option>
                        </select>
                      </div>
                      <div class="required field">
                          <label>Entrenador</label>
                          <select class="ui fluid dropdown" name="equipoEntrenador">
                              <c:forEach var="entrenador" items="${entrenadores}">
                                  <option value="${entrenador.id}">${entrenador.nombres}</option>
                              </c:forEach>
                          </select>
                      </div>
                    </div>
                </div>
                
                <div class="field">
                    <div class="two fields">
                      <div class="required field">
                        <label>Descripción</label>
                        <input type="text" name="equipoDescripcion" placeholder="Detalles" required="true">
                      </div>
                      <div class="required field">
                        <label>Logo</label>
                        <input type="file" name="equipoLogo" required="true">
                      </div>
                      
                    </div>
                </div>
                
                <div class="ui error message">
                    NOTA: no se podrán programar partidos del equipo si este no tiene al menos 11 jugadores. <br />
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
                        <th>LOGO</th>
                        <th>PRESIDENTE</th>
                        <th>ESTADIO</th>
                        <th>UBICACION</th>
                        <th>DETALLES</th>
                        <th>ENTRENADOR</th>
                        <th>ELIMINADO</th>
                        <th>ACCION</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="equ" items="${equipos}">
                        <tr>
                            <td>${equ.id}</td>
                            <td>${equ.nombre}</td>
                            <td><img src="${context}/img/${equ.rutaLogo}" width="40" height="40"></td>
                            <td>${equ.presidente}</td>
                            <td>${equ.estadio}</td>
                            <td>${equ.ubicacion}</td>
                            <td>${equ.detalles}</td>
                            <td>${equ.entrenador.nombres}</td>
                            <td>${equ.descalificado}</td>
                            <td>
                                <button class="tiny ui button" id="editar" onclick="cargar(${equ.id}, '${equ.nombre}', '${equ.presidente}', '${equ.estadio}', '${equ.ubicacion}', '${equ.detalles}', ${equ.entrenador.id}, ${equ.descalificado})">
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
    
    function cargar(equipoId, nombre, presidente, estadio, ubicacion, detalles, idEntrenador, descalificado) {
        $('[name="equipoId"]').val(equipoId);
        $('[name="equipoNombre"]').val(nombre);
        $('[name="equipoPresidente"]').val(presidente);
        $('[name="equipoEstadio"]').val(estadio);
        $('[name="equipoUbicacion"]').val(ubicacion);
        $('[name="equipoEntrenador"]').val(idEntrenador);
        $('[name="equipoDescripcion"]').val(detalles);
                
        $('[name="equipoEstado"]').removeAttr('disabled').val(descalificado);
        
        $('[name="equipoLogo"]').removeAttr('required');
        $('[name="modificar"]').removeAttr('disabled');
        $('[name="insertar"]').prop('disabled', true);
    }
    
    
</script>
    
<%@include file="../templates/footer.jsp" %> 
