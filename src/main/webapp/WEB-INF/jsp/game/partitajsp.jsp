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
	 <style>
			 
				input {border-style: none; border-bottom-style: solid; border-width:thin;}
				
				div {border:solid thin black; margin: 0.5em; padding: 0.5em; border-radius: 1em; background: #f7f7f7;}
				
				.die {border:solid thin black; margin: 1px; padding: 1px; border-radius: 5px; display: inline-block;}
				
				.outer {margin: auto; max-width: 750px; background: khaki;}
				
				@viewport { width: 640;}
				
	</style>
	
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
			    	
			
				
				
				<script>
				
				  var dieFace = {
				    4:'<svg width="50" height="50"><polygon points="0,3 25,46 50,3" style="stroke: black; fill: none" /><text fill="black" x="20" y="26" font-family="Verdana" font-size="18">REPLACEME</text></svg>',
				    6:'<svg width="50" height="50"><polygon points="3,3 3,47 47,47 47,3" style="stroke: black; fill: none" /><text fill="black" x="20" y="30" font-family="Verdana" font-size="18">REPLACEME</text></svg>',
				    8:'<svg width="60" height="50"><polygon points="17,0 60,25 17,50 17,0 0,25 17,50" style="stroke: black; fill: none" /><text fill="black" x="25" y="30" font-family="Verdana" font-size="18">REPLACEME</text></svg>',
				    10:'<svg width="50" height="50"><polygon points="43,50 0,25 43,0 50,25" style="stroke: black; fill: none" /><text fill="black" x="20" y="30" font-family="Verdana" font-size="18">REPLACEME</text></svg>',
				    12:'<svg width="50" height="50"><polygon points="25,0 1,17 10,45 40,45 49,17" style="stroke: black; fill: none" /><text fill="black" x="13" y="30" font-family="Verdana" font-size="18">REPLACEME</text></svg>',
				    20:'<svg width="50" height="50"><polygon points="0,46 25,0 50,46" style="stroke: black; fill: none" /><text fill="black" x="13" y="40" font-family="Verdana" font-size="18">REPLACEME</text></svg>',
				    'other':'<svg width="50" height="50"><circle cx="25" cy="25" r="20" style="stroke: black; fill: none" /><text fill="black" x="13" y="31" font-family="Verdana" font-size="18">REPLACEME</text></svg>'
				  };
				
				  function rollem(){
				  	total = 0;
				    n = document.getElementById('num').value;
				    d = document.getElementById('die').value;
				  	o = document.getElementById('output');
					p = o.innerHTML;
					
					if (d in dieFace){ 
					  face = dieFace[d];
				    } else {
				      face = dieFace['other'];
				    }
					
				    if (p != ''){
				        p = p.concat("<hr>")
				    } else {
				        p = p.concat('<button id="clear" onclick="clearme()" style="float:right;">Clear</button>');
				    }
				    for (i = 0; i < n; i++){
						r = Math.ceil(Math.random() * d);
						total += r;
				        p = p.concat("<span> " + face.replace('REPLACEME', r.toString()) + " </span>");
				    }
				    p += " Total: " + total;
				    o.innerHTML = p;
				  }
				
				  function clearme(){
				  	o = document.getElementById('output');
				  	o.innerHTML = "";
				  }
				
				  function loady(){
				  	  for(f in dieFace){
				  		  e = document.getElementById(f);
						  if (e) e.innerHTML = dieFace[f].replace("REPLACEME", f);
					  }
				  }
				
				  function dieClick(whatzis){
				    n = document.getElementById('num');
				    d = document.getElementById('die');
						if (whatzis.id == d.value){
							n.value = Number(n.value) + 1;
						} else {
							n.value = 1;
							d.value = whatzis.id
						}
				  }
				</script>
				</head>
				
				<body onload=loady()>
				<div class='outer'>
				  <div id="control">
				  <input type="number" step="1" id="num" value='3' maxlength="3" size="1" width="1em"> d <input type="number" step="1" id="die" value='6' maxlength="3" size="1">
				  <button id="roll" onclick="rollem()">Roll</button>
				  </div>
				
				  
				<span id="4" onclick="dieClick(this)">
				</span>
				<span id="6" onclick="dieClick(this)">
				</span>
				<span id="8" onclick="dieClick(this)">
				</span>
				<span id="10" onclick="dieClick(this)">
				</span>
				<span id="12" onclick="dieClick(this)">
				</span>
				<span id="20" onclick="dieClick(this)">
				</span>
				
				  <div id="output"></div>
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