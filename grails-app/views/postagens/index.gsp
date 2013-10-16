<%@page import="br.org.prismaCamara.modelo.Deputado"%>
<style>
	li{
	margin-bottom: 1em;
	}
</style>

<g:set var="sDeputados">
	<g:select name="idDeputado" value="${params.idDeputado}" from="${deputados}" optionKey="id" optionValue="descricao"/>
</g:set>
	
<h2>Mensagens deseja ver agora?</h2>
<ul>
	<li><g:link action="biografiaDeputado">Biografia aleatória de Deputado</g:link> 
	</li>
	<li>
	<g:form action="frequenciaDeputado" method="get">
		${sDeputados}
		<input type="submit" value="Última Frequência"/> 
	</g:form>
	</li>
	<li>
	<g:form action="gastoDeputado" method="get">
		${sDeputados}
		<input type="submit" value="Últimos Gastos"/> 
	</g:form>
	</li>
	<li>
	<g:form action="discursoDeputado" method="get">
		${sDeputados}
		<input type="submit" value="Últimos Discursos"/> 
	</g:form>
	</li>
	<li>
	<g:form action="votacaoProposicao" method="get">
		<g:select name="idProposicao" value="${params.idProposicao}" 
		from="${proposicoes}" 
		optionKey="id" optionValue="descricao"/>
		<input type="submit" value="Últimos Votos"/> 
	</g:form>
	</li>
</ul>

<pre>${flash.postagem}</pre>
${flash.error}

<g:if test="${flash.postagem}">
	<g:form controller="facebookPost" action="postarNoMural">
		<input type="hidden" name="mp" value="${flash.postagem}"/>
		<input type="submit" value="Enviar postagem para o face! Ah muleke!"/> 
	</g:form>
</g:if>