<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	 	<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/jqueryUI/jquery-ui.min.css" />
		<style>
			.ui-autocomplete-loading {
				background: white url("../assets/img/jqueryUI/anim_16x16.gif") right center no-repeat;
			}
			.error_field {
		        color: red; 
		    }
		</style>
	   
	   <title>Ricerca</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
			  
			  <div class='card'>
				    <div class='card-header'>
				        <h5>Ricerca elementi</h5> 
				    </div>
				    <div class='card-body'>
		
		
		
							<form method="post" action="${pageContext.request.contextPath}/tavolo/list" class="row g-3" >
							
							
								<div class="col-md-6">
									<label for="denominazione" class="form-label">Denominazione</label>
									<input type="text" name="denominazione" id="denominazione" class="form-control" placeholder="Inserire Denominazione"  >
								</div>
								
								<div class="col-md-6">
									<label for="creditoMinimo" class="form-label">Credito Minimo </label>
									<input type="number" name="creditoMinimo" id="creditoMinimo" class="form-control" placeholder="Inserire il Credito Minimo" value="0" >
								</div>
							
								<div class="col-md-6">
									<label for="esperienzaMinima" class="form-label">Esperienza Minima </label>
									<input type="number" class="form-control" name="esperienzaMinima" id="esperienzaMinima" placeholder="Inserire Esperienza Minima" value="0" >
								</div>
								
								<div class="col-md-3">
									<label for="dateCreated" class="form-label">Data Creazione </label>
                        			<input class="form-control" id="dateCreated" type="date" placeholder="dd/MM/yy"
                            			title="formato : gg/mm/aaaa"  name="dateCreated"   >
								</div>
								<sec:authorize access="hasRole('ADMIN')">
								<div class="col-md-6">
										<label for="utenteSearchInput" class="form-label">Utente Creatore:</label>
											<input class="form-control ${status.error ? 'is-invalid' : ''}" type="text" id="utenteSearchInput"
												name="utenteInput" value="${insert_tavolo_attr.utenteCreazione.nome}${empty insert_tavolo_attr.utenteCreazione.nome?'':' '}${insert_tavolo_attr.utenteCreazione.cognome}">
										<input type="hidden" name="utenteCreazione.id" id="utenteId" value="${insert_tavolo_attr.utenteCreazione.id}">
								</div>
								</sec:authorize>
							<div class="col-12">
								<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
								<a class="btn btn-outline-primary ml-2" href="${pageContext.request.contextPath}/tavolo/insert">Add New</a>
								<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
							</div>
		
						</form>
  	
  								<script>
									$("#utenteSearchInput").autocomplete({
										 source: function(request, response) {
										        $.ajax({
										            url: "${pageContext.request.contextPath}/utente/searchUtenteAjax",
										            datatype: "json",
										            data: {
										                term: request.term,   
										            },
										            success: function(data) {
										                response($.map(data, function(item) {
										                    return {
											                    label: item.label,
											                    value: item.value
										                    }
										                }))
										            }
										        })
										    },
										//quando seleziono la voce nel campo deve valorizzarsi la descrizione
									    focus: function(event, ui) {
									        $("#utenteSearchInput").val(ui.item.label)
									        return false
									    },
									    minLength: 2,
									    select: function( event, ui ) {
									    	$('#utenteId').val(ui.item.value);
									    	//console.log($('#utenteId').val())
									        return false;
									    }
									});
								</script>
				    
				    
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