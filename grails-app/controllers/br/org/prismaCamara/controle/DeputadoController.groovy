package br.org.prismaCamara.controle

import groovy.util.logging.Log4j
import br.org.prismaCamara.modelo.Deputado
import br.org.prismaCamara.modelo.Usuario
import br.org.prismaCamara.modelo.UsuarioDeputado
import br.org.prismaCamara.util.PesquisaFoneticaUtil

@Log4j
class DeputadoController {

	def usuarioService
	def springSecurityService
	
    def list() {		
		//cache(validUntil:new Date()+3)
		Usuario usuario = springSecurityService.currentUser
		
		LinkedHashMap mapDeputados = new LinkedHashMap()
		def listaDeputados = []

		def deputadosDeUsuario = usuarioService.getDeputadosDeUsuario(usuario)
		
		// caso nada venha na pesquisa, pegar todos os já associados ao usuario e mandar essa lista para a view
		if (!params.q) {
			listaDeputados = deputadosDeUsuario
		}
		else {
			if (params.q.size()>=2) {
				def pesquisa = PesquisaFoneticaUtil.getTermosFoneticosParaPesquisa(params.q)
				
				log.debug "Pesquisa fonetizada: ${pesquisa}"
				def listaDeputadosTmp = Deputado.searchEvery(pesquisa)
				log.debug "Resultado: ${listaDeputados.size()}"
				
				if (!listaDeputadosTmp) {
					request.message="Nenhum Deputado encontrado com \"${params.q}\""
				} else {
					listaDeputadosTmp.each { 
						listaDeputados += Deputado.get(it.id) 
					}
					listaDeputados.sort{d1,d2-> d1.nomeParlamentar<=>d2.nomeParlamentar}
				}
			} else {
				request.message="Digite pelo menos 2 letras na pesquisa"
			}
			
		}
		
		for (dep in listaDeputados) {
			if (!dep.ativo) {
				continue
			}
			def mapeado = deputadosDeUsuario.contains(dep)
			mapDeputados.put(dep, mapeado)
		}
		
		render(template:'resultadoPesquisa',model:[mapa:mapDeputados])
		
	}
	
	
	/**
	 * Download da foto miniatura do deputado (".../deputado/foto/$id")
	 * @return
	 */
	def foto() {
		cache(validUntil:new Date()+90)
		
		def dep = Deputado.get(params.id)
		def bmini = dep.foto
		
		if (!bmini) {
			bmini = grailsAttributes.getApplicationContext().getResource("/images/pessoa-sem-foto.png").getFile().bytes
		}
		response.contentType='image/jpeg'
		response.contentLength=bmini.size()
		response.outputStream<<bmini
		response.setStatus(200)
	}
			
	def toogleAssociar() {
		Usuario usuario = springSecurityService.currentUser
		Deputado deputado = Deputado.get(params.id)
		try {
			def ud = UsuarioDeputado.findByUsuarioAndDeputado(usuario,deputado)
			if (ud) {
				ud.delete()
			} else {
				ud = new UsuarioDeputado(usuario:usuario, deputado:deputado)
				ud.save()
			}
			Integer contagemDeputados = usuarioService.countDeputadosDeUsuario(usuario)
			session.contagemDeputados = contagemDeputados
			render(status:200)
		} catch (Exception e) {
			log.error("Erro ao tentar (des)associar deputado (${deputado.descricao}) a usuário ${usuario.login}: ${e.message}")
			e.printStackTrace()
			render(status:500, text:message(code:'erro.padrao'))
		} 
	}
}
