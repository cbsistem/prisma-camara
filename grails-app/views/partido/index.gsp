<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta name="layout" content="main"/>
	<title>:: Acompanhamento de Partidos</title>
</head>
<body>

	<div class="container">
	
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="row">
				<div class="col-sm-6">
		    		<span class="glyphicon glyphicon-link"></span> <b>Seus Partidos</b>
				</div>
				<div class="col-sm-6">
		    		<span class="pull-right"><g:include controller="painel" action="contagem" id="Partidos"/></span>
				</div>
				</div>
		  	</div>
		</div>
	
		<g:formRemote url="[action:'list',controlr:'partido']" name="searchableForm" update="resultado">
		
			<div class="row">
			
				<div class="col-md-4">
			        <div class="input-group">
				    	<g:textField class="form-control" name="q" id="campoQ" value="${params.q}" size="50" placeholder="Nome ou sigla do Partido"/>
				      	<span class="input-group-btn">
				        	<button class="btn btn-default" type="submit">Pesquisar</button>
				      	</span>
				    </div><!-- /input-group -->
				    <br/>
		        </div>
		        
		        <div class="col-md-4">
			        <div class="panel panel-default">
						<div class="panel-body">
							<span id="slink62" class="glyphicon glyphicon-search"></span>
					        Partidos que você ainda <b>Não Acompanha</b>.<br>
					        <span>Clique no ícone para acompanhar</span>.
						</div>
					</div>        
				</div>
				
				<div class="col-md-4">
			        <div class="panel panel-default acompanhando">
						<div class="panel-body">
							<span id="slink62" class="glyphicon glyphicon-check"></span>
					        Partidos que você <b>Já Acompanha</b>.<br>
					        Clique no ícone para deixar de acompanhar.
						</div>
					</div>        
				</div>
		        
	        </div>
		        
		</g:formRemote>
		    
	    <div style="clear: both;"></div>
		<div id="resultado">
			<g:include controller="partido" action="list"/>
		</div>
	</div>
</body>
</html>