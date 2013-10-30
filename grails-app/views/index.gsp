<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Bem-vindo!</title>
	</head>
	<body>
		<div class="container">
			<sec:ifNotLoggedIn>
				<div style="margin-bottom: 0.7em; margin-left: 3em">
					<span class="glyphicon glyphicon-arrow-left"></span> 
					Conecte-se com
					<span class="glyphicon glyphicon-arrow-right"></span>
				</div>			
				<div class="redes">
					<a href="/prisma-camara/j_spring_security_facebook_redirect" class="botao-face" title="Entrar com Facebook"></a>
				</div>
				<div class="redes peq"> ou </div>
				<div class="redes">
					<a href="/prisma-camara/j_spring_twitter_security_check" class="botao-twitter" title="Entrar com Twitter"></a>
				</div>
			</sec:ifNotLoggedIn>
			<sec:ifAllGranted roles="ROLE_USER">
				<p>Use o sistema acessando as opções do menu.</p>
			</sec:ifAllGranted>
			
			<div style="width: 476px; margin-top: 3.5em">
			<g:include view="banner-home.gsp"/>
			</div>
			
		</div>
	</body>
</html>
