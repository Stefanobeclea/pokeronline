<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="it" class="h-100">
<head>
	<!-- Common imports in pages -->
	<jsp:include page="../header.jsp" />
	<title>Visualizza elemento</title>
	
</head>
<body class="d-flex flex-column h-100">
	<!-- Fixed navbar -->
	<jsp:include page="../navbar.jsp" />
	
	<!-- Begin page content -->
	<main class="flex-shrink-0">
	  	<div class="container">
	  	
	  		<div class="alert alert-success alert-dismissible fade show  ${successMessage==null?'d-none':'' }" role="alert">
				  ${successMessage}
				  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
				</div>
			
			<div class='card'>
			    <div class='card-header'>
			        Visualizza dettaglio
			    </div>
			
			    <div class='card-body'>
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Id:</dt>
					  <dd class="col-sm-9">${show_tavolo_attr.id}</dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Denominazione:</dt>
					  <dd class="col-sm-9">${show_tavolo_attr.denominazione}</dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Esperienza Minima:</dt>
					  <dd class="col-sm-9">${show_tavolo_attr.esperienzaMinima}</dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Credito Minimo:</dt>
					  <dd class="col-sm-9">${show_tavolo_attr.creditoMinimo}</dd>
			    	</dl>
			    	
			    	<dl class="row">
					  <dt class="col-sm-3 text-right">Data Creazione:</dt>
					  <dd class="col-sm-9"><fmt:formatDate type = "date" value = "${show_tavolo_attr.dateCreated}" /></dd>
			    	</dl>
			    	
			    	<!-- info Utente -->
			    	<dl class="row">
			    	<dt class="col-sm-3 text-right">Giocatori Al Tavolo:</dt>
			    	<dd class="col-sm-9">
			    	<c:if test="${empty show_tavolo_attr.utenti }">
			    		<td>Non ci sono giocatori al tavolo<br></td>
			    	</c:if>
			    	<c:forEach items="${show_tavolo_attr.utenti }" var="giocatoriItem">
							<td>${giocatoriItem.username}<br></td>
					</c:forEach>
					
					</dd>
			    	</dl>
					<!-- end info Utente -->
					</div>
			    	
			    <!-- end card body -->
			    </div>
			    
			    <div class='card-footer' style="display: -webkit-inline-box">
					<form action="${pageContext.request.contextPath}/game/partita/${show_tavolo_attr.id}" method="post">
						  <button type="submit" name="submit" id="submit" class="btn btn-primary">Gioca</button>
				    </form>
				    &nbsp;&nbsp;
				 	<form action="${pageContext.request.contextPath}/game/exit/${show_tavolo_attr.id}" method="post">
						  <button type="submit" name="submit" id="submit" class="btn btn-dark">Esci</button>
					</form>
					 &nbsp;&nbsp;
					<a href="${pageContext.request.contextPath}/game/gioca/${show_tavolo_attr.id}" class='btn btn-outline-secondary' style='width:80px'>
						 <i class='fa fa-chevron-left'></i> Back
					</a>
				</div>
			<!-- end card -->
			</div>	
	
		<!-- end container -->  
		</div>
		
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
</html>