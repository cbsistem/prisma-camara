<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta name="layout" content="main"/>
	<title>:: Acompanhamento de Proposicoes</title>
</head>
<body>

	<p><g:link controller="painel" class="btn btn-success">Voltar para Painel de Acompanhamentos</g:link></p>

	<div id="page-body" role="main">

		<nav class="navbar navbar-default">
			<div class="nav navbar-nav">
				<p class="navbar-text">
					<span class="glyphicon glyphicon-transfer"></span> <b>Seus Proposições</b>
				</p>
			</div>
			<div class="nav navbar-nav navbar-right">
				<p class="navbar-text" id="pContagem">
					<g:include controller="painel" action="contagem" id="Proposicoes"/>
				</p>
			</div>
		</nav>

			<g:formRemote url="[action:'list',controlr:'partido']" name="searchableForm" update="resultado">
	        <g:textField name="q" value="${params.q}" size="50" placeholder="Número, ano, tipo ou qualquer palavra da ementa"/>
	        <input type="submit" value="Pesquisar" id="bPesquisar"/>
	        
	        <div class="info-acompanhamento nao">
	        <span id="slink62" class="glyphicon glyphicon-search"></span>
	        Proposições que ainda <b>Não Acompanha</b>.<br>
	        <span>Clique para acompanhar</span>.
	        </div>
	        
	        <div class="info-acompanhamento">
	        <span id="slink62" class="glyphicon glyphicon-check"></span>
	        Proposições que você <b>Já Acompanha</b>.<br>
	        Clique para deixar de acompanhar.
	        </div>
	        
	    </g:formRemote>
	    
	    <div style="clear: both;"></div>
		<div id="resultado">
			<g:include controller="partido" action="list"/>
		</div>						
	</div>
	
</body>
</html>