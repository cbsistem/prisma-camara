<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Welcome to Grails</title>
	</head>
	<body>
		<a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id="page-body" role="main">
			<h1>Painel</h1>
			<p>Bem-vindo <g:if test="usuario?.nome?.trim()?.isEmpty()">${usuario.username}</g:if><g:else>${usuario.nome}</g:else>!</p>
			<div>
				<g:form name="config" url="[action:'gravarConfiguracoes']">
					<h2>Partidos de Interesse</h2>
					<g:each in="${partidos}" var="partido" status="i">
					    <g:checkBox name="partidosSelecionados" value="${partido.id}" checked="${usuario.partidos.contains(partido)}" />
					    <label for="partidosSelecionados">${partido.sigla}</label><br/>
					</g:each>
					<br/>
					<br/>
					<h2>Deputados de Interesse</h2>
					<g:each in="${deputados}" var="deputado" status="i">
					    <g:checkBox name="deputadosSelecionados" value="${deputado.id}" checked="${usuario.deputados.contains(deputado)}" />
					    <label for="deputadosSelecionados">${deputado.descricao}</label><br/>
					</g:each>
					<br/>
					<g:submitButton name="gravar" value="Gravar"/>
				</g:form>
			</div>
			<g:link action="">Voltar</g:link>			
		</div>
	</body>
</html>