
<jsp:include page="templates/header.jsp" flush="true">
    <jsp:param name="title" value="Inicio - FutbolManager" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}" />




<div class="ui grid">

<div class="eleven wide centered column">
  
    <div data-simple-slider id="bannerSlider" class="float-center">
        <img class="float-center" src="${context}/img/banner1.jpg">
	  <img class="float-center" src="${context}/img/banner2.jpg">
	  <img class="float-center" src="${context}/img/banner3.jpg">
	</div>
    <script>
	  simpleslider.getSlider();
	</script>

</div>
    
    
    
    
</div>

    <div class="ui grid">
        <div class="eleven wide centered column">
        <div class="ui big message">
            Bienvenido. 
        </div>
    </div>
    </div>
    
    

<jsp:include page="templates/footer.jsp" flush="true" />


