package hackathon2013

import groovy.util.logging.Log4j
import groovy.util.slurpersupport.GPathResult

@Log4j
class AtualizarDeputadosService {
	
	static String URL_ATUALIZACAO='http://www.camara.gov.br/SitCamaraWS/Deputados.asmx/ObterDeputados'

	/**
	 * Atualizar a tabela de Deputados. Os que estiverem na tabela e não chegarem no XML são marcados com "ativo=false"
	 * @param xmlr Conteúdo XML recebido do WebService da Camara
	 */
	private void atualizar(GPathResult xmlr) {
		
		def idsRecebidos = [] // coleta os Ids recebidos para saber quais deputados não são mais ativos 
		log.debug("${xmlr.childNodes().size()} deputados chegaram no XML")
		xmlr.deputado.each{ dep->
			
			def ideCadastroA = dep.ideCadastro.toInteger()
			idsRecebidos+=ideCadastroA
			
			def atributos = [ideCadastro: ideCadastroA, condicao: dep.condicao.toString(), matricula: dep.matricula.toInteger(), nome: dep.nome.toString(), nomeParlamentar: dep.nomeParlamentar.toString(),  sexo: dep.sexo.toString(), uf:dep.uf.toString(), partido: dep.partido.toString(), fone: dep.fone.toString(), email:dep.email.toString(), ativo:true]
			
			Deputado deputado = Deputado.where {ideCadastro==ideCadastroA && ativo}.find()
			
			if (deputado) { // já existe o deputado, atualize os dados
				deputado.properties=atributos
				log.debug("Deputado ${ideCadastroA} atualizado")
			} else { // ainda não existe. Persista agora
				deputado = new Deputado(atributos)
				deputado.save()
				log.debug("Deputado ${ideCadastroA} salvo no banco")
			}
			
		}
		
		def inativos = Deputado.executeUpdate("update Deputado set ativo=false where ideCadastro not in (:ids)",[ids:idsRecebidos])
		log.debug("${idsRecebidos} deputados marcados como inativos")
	}
}
