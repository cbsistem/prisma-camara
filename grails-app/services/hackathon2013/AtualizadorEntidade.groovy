package hackathon2013

import java.util.Map;

import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import groovy.text.SimpleTemplateEngine
import groovy.util.slurpersupport.GPathResult

abstract class AtualizadorEntidade {
	
	RestBuilder rest = new RestBuilder()
	
	abstract String getSiglaDeParametro();
	abstract def atualizar();
	
	/**
	 * Recuperar a URL de atualização da entidade
	 * @param parametrosValores {@link Map} com os parâmetros a serem substituidos (opcional)
	 * @return A String com a URL e seus parâmetros preenchidos, se for o caso
	 */
	String getUrlAtualizacao(parametrosValores) {
		
		def texto = Parametro.findBySigla(getSiglaDeParametro()).valor.replace('=&','= &')
		
		Writable template = new SimpleTemplateEngine().createTemplate(texto).make(parametrosValores)
		
		return template.toString();
	}


	protected GPathResult getXML(String url) {
		getXML(url, null)
	}
	
	protected GPathResult getXML(String url, Map parametros) {
		
		def respostaTmp = rest.get(url)
		
		if (respostaTmp instanceof RestResponse) {
			RestResponse resposta = respostaTmp as RestResponse
			return resposta.xml
		} else {
			def msg = "Não foi possível recuperar o XML da resposta para ${url}: ${respostaTmp.text}"
			log.error(msg)
			throw new Exception(msg)
		}
	}
}
