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
		
		
							<form method="post" action="resetpw" class="row g-3">
								
								<div class="col-md-3">
									<label for="password" class="form-label">Vecchia Password<span class="text-danger">*</span></label>
										<input type="password" class="form-control ${status.error ? 'is-invalid' : ''}" name="vecchiaPassword" id="vecchiaPassword" placeholder="Inserire Vecchia Password"  required>
								</div>								
															 
								<div class="col-md-3">
									<label for="password" class="form-label">Nuova Password<span class="text-danger">*</span></label>
										<input type="password" class="form-control ${status.error ? 'is-invalid' : ''}" name="password" id="password" placeholder="Inserire Password"  required>
								</div>
								
								<div class="col-md-3">
									<label for="confermaPassword" class="form-label">Conferma Nuova Password <span class="text-danger">*</span></label>
										<input type="password" class="form-control ${status.error ? 'is-invalid' : ''}" name="confermaPassword" id="confermaPassword" placeholder="Confermare Password"  required>
								</div>
								
								<%-- facendolo con i tag di spring purtroppo viene un po' spaginato, ho preferito a mano. E poi 
									anche il binding andava gestito diversamente
								
								<div class="col-md-6 form-check">
									<p>Ruoli:</p>
									<form:checkboxes itemValue="id" itemLabel="codice"  element="div class='form-check'" items="${ruoli_totali_attr}" path="ruoli" cssClass=""/>
								</div>
								--%>
								
								<div class="col-12">
									<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
								</div>
		
						</form>
  
				    
				    
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