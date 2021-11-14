<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	 	 <style>
		    .error_field {
		        color: red; 
		    }
		</style>
	   
	   <title>Inserisci Nuovo Elemento</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<%-- se l'attributo in request ha errori --%>
					<spring:hasBindErrors  name="insert_tavolo_attr">
						<%-- alert errori --%>
						<div class="alert alert-danger " role="alert">
							Attenzione!! Sono presenti errori di validazione
						</div>
					</spring:hasBindErrors>
			  
			  		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
			  
			  <div class='card'>
				    <div class='card-header'>
				        <h5>Inserisci nuovo elemento</h5> 
				    </div>
				    <div class='card-body'>
		
							<h6 class="card-title">I campi con <span class="text-danger">*</span> sono obbligatori</h6>
		
		
							<form:form modelAttribute="insert_tavolo_attr" method="post" action="save" novalidate="novalidate" class="row g-3">
					
							
								<div class="col-md-6">
									<label for="denominazione" class="form-label">Denominazione <span class="text-danger">*</span></label>
									<spring:bind path="denominazione">
									<input type="text" name="denominazione" id="denominazione" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="Inserire denominazione" value="${insert_tavolo_attr.denominazione }" required>
									</spring:bind>
									<form:errors  path="denominazione" cssClass="error_field" />
								</div>
								
								<div class="col-md-6">
									<label for="esperienzaMinima" class="form-label">Esperienza Minima <span class="text-danger">*</span></label>
									<spring:bind path="esperienzaMinima">
									<input type="number" name="esperienzaMinima" id="esperienzaMinima" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="Inserire Esperienza Minima " value="${insert_tavolo_attr.esperienzaMinima }" required>
									</spring:bind>
									<form:errors  path="esperienzaMinima" cssClass="error_field" />
								</div>
							
								<div class="col-md-6">
									<label for="creditoMinimo" class="form-label">Credito Minimo <span class="text-danger">*</span></label>
									<spring:bind path="creditoMinimo">
									<input type="number" class="form-control ${status.error ? 'is-invalid' : ''}" name="creditoMinimo" id="creditoMinimo" placeholder="Inserire il Credito Minimo" value="${insert_tavolo_attr.creditoMinimo }" required>
									</spring:bind>
									<form:errors  path="creditoMinimo" cssClass="error_field" />
								</div>						
								
							<div class="col-12">
								<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
							</div>
		
						</form:form>
  
				    
				    
					<!-- end card-body -->			   
				    </div>
				<!-- end card -->
				</div>		
					  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>