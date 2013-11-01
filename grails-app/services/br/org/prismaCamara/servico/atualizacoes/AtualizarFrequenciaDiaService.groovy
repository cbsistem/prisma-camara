/*
 * Copyright 2013 de José Yoshiriro (jyoshiriro@gmail.com) e Raimundo Norberto (raimundonorberto@gmail.com)
 * Este arquivo é parte do programa Olho na Câmara.
 * 
 * O Olho na Câmara é um software livre; você pode redistribuí-lo e/ou modificá-lo dentro
 * dos termos da GNU General Public License como publicada pela Fundação do Software Livre
 * (FSF); na versão 3 da Licença. Este programa é distribuído na esperança que possa ser
 * útil, mas SEM NENHUMA GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer
 * MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a licença para maiores detalhes. Você deve ter
 * recebido uma cópia da GNU General Public License, sob o título 'LICENCA.txt', junto com
 * este programa, se não, acesse http://www.gnu.org/licenses/
 */
package br.org.prismaCamara.servico.atualizacoes

import br.org.prismaCamara.modelo.Deputado;
import br.org.prismaCamara.modelo.FrequenciaDia;
import br.org.prismaCamara.modelo.FrequenciaSessao;
import br.org.prismaCamara.modelo.Parametro;
import groovy.util.logging.Log4j;
import groovy.util.slurpersupport.GPathResult

/**
 * Atualização de frequências de todos os Deputados no (dia atual-2) ou de determinada data
 */
@Log4j
class AtualizarFrequenciaDiaService extends AtualizadorEntidade {

	def usuarioService
	
	@Override
	public String getSiglaDeParametro() {
		// "http://www.camara.gov.br/SitCamaraWS/sessoesreunioes.asmx/ListarPresencasDia?numLegislatura=&numMatriculaParlamentar=&siglaPartido=&siglaUF=&data=${data}"
		// data: dd/MM/yyyy
		return 'url_frequecias_dia';
	}
	
	private Date getUltimaAtualizacao() {
		def ultimoDiaS = Parametro.findBySigla('ultimo_dia_frequencia')?.valor
		Date proximaData = null
		try {
			proximaData = Date.parse("dd/MM/yyyy", ultimoDiaS)
		} catch (Exception e) {
			proximaData = (new Date()-30) // caso ocorra algum problema ou seja a primeira vez, a data máxima a ser buscada é de 30 dias atrás
		}
		return proximaData.clearTime()
	}
	
	
    def atualizar() {
		Date ultimaAtualizacao = getUltimaAtualizacao()
		Date proximaAtualizacao = new Date().clearTime()
		
		def urlT = null
		GPathResult xmlr = null
		try {
			def quant = 0
			while (quant==0 && proximaAtualizacao>ultimaAtualizacao) {
				urlT = getUrlAtualizacao([data:proximaAtualizacao.format("dd/MM/yyyy")])
				try {
					xmlr = getXML(urlT)
					quant = xmlr.childNodes()?.size()
				} catch (Exception e) {
					log.error("A url ${urlT} não retornou XML válido: ${e.message}")
				}
				proximaAtualizacao--
			}
			if (quant==0) {
				log.debug("Não há novas frequencias após ${ultimaAtualizacao}")
				return
			}
			proximaAtualizacao++
			
		} catch (Exception e) {
			log.error("A url ${urlT} não retornou XML válido: ${e.message}")
			throw e;
		}
		
		log.debug("Chegaram ${xmlr.parlamentares.childNodes()?.size()} frequências dos deputados chegaram no XML de ${urlT}...")

		for (parlemantar in xmlr.parlamentares.parlamentar) { 
			
			Deputado.withNewTransaction { tx ->
			
			Deputado deputadoA = Deputado.findByMatricula(parlemantar.carteiraParlamentar.toString().toInteger())
			
			if (deputadoA) {
				
				if (!usuarioService.isDeputadoObservado(deputadoA)) {
					log.debug("Deputado ${deputadoA.descricao} não está associado a nenhum usuário. Sua frequência não será registrada na base.")
				} 
				else { // se o deputado está sendo observado
				
					def atributos = [dia:proximaAtualizacao, frequenciaDia:parlemantar.descricaoFrequenciaDia.toString(), justificativa:parlemantar.justificativa.toString()]
					
					atributos+=[deputado:deputadoA]
					
					FrequenciaDia entidade = FrequenciaDia.where {deputado==deputadoA && dia==proximaAtualizacao}.find()
					
					if (entidade) { // já existe o registro, atualize os dados
						entidade.properties=atributos
						log.debug("Frequência de deputado ${deputadoA.nomeParlamentar} em ${entidade.dia} possivelmente atualizada")
					} else { // ainda não existe. Persista agora
						entidade = new FrequenciaDia(atributos)
						entidade.save()
						if (entidade.errors.errorCount>0) {
							log.error("Frequência de deputado ${deputadoA?.nomeParlamentar} em ${entidade?.dia} NÃO foi salva devido a erros: ${entidade?.errors}")
						} else {
							log.debug("Frequência de deputado ${deputadoA?.nomeParlamentar} em ${entidade?.dia} salva no banco")
						}
					}
			
					// Frequencias de sessões do dia
					def frequenciasSessao = parlemantar.childNodes()[7].childNodes()
					for (sd in frequenciasSessao) {
						
						def inicioA=Date.parse('d/M/yyyy HH:mm:ss',sd.childNodes()[0]?.text())
						def descricaoA=sd.childNodes()[1]?.text()
						def frequenciaA=sd.childNodes()[2]?.text()
						
						def atributosS = [inicio:inicioA, descricao:descricaoA, frequencia:frequenciaA] 
						FrequenciaSessao fSessao = FrequenciaSessao.findByFrequenciaDiaAndInicio(entidade,inicioA)
						if (fSessao) {
							fSessao.properties=atributosS
							log.debug("Frequencia da Sessão ${descricaoA} provavelmente atualizada no banco")
						} else {
							atributosS+=[frequenciaDia:entidade]
							fSessao = new FrequenciaSessao(atributosS)
							fSessao.save()
							log.debug("Frequencia da Sessão ${descricaoA} salva no banco")
						}
					}
				
				} // se o deputado está associado a algum usuário
				
			} // if de existencia de Deputado
			
			}
		}

		log.debug("Atualização de Frequencias de Deputados concluída com sucesso")
    }

}
