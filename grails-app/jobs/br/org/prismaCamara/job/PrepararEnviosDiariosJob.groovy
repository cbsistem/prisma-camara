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
package br.org.prismaCamara.job

import groovy.util.logging.Log4j
import br.org.prismaCamara.modelo.Deputado;
import br.org.prismaCamara.modelo.Usuario
import br.org.prismaCamara.modelo.UsuarioDeputado
import br.org.prismaCamara.servico.UsuarioService
import br.org.prismaCamara.servico.postagens.PrepararPostBiografiaService;
import br.org.prismaCamara.servico.postagens.PrepararPostDespesaService;
import br.org.prismaCamara.servico.postagens.PrepararPostDiscursoService;
import br.org.prismaCamara.servico.postagens.PrepararPostFrequenciaDiaService
import br.org.prismaCamara.servico.postagens.PrepararPostVotacaoService;


@Log4j
class PrepararEnviosDiariosJob {
	
	UsuarioService usuarioService
	
	PrepararPostFrequenciaDiaService prepararPostFrequenciaDiaService
	PrepararPostDespesaService prepararPostDespesaService
	PrepararPostDiscursoService prepararPostDiscursoService
	PrepararPostVotacaoService prepararPostVotacaoService
	PrepararPostBiografiaService prepararPostBiografiaService
		
    static triggers = {
	  cron name: 'prepararPostsDiariosTrigger', cronExpression: "1 0 0 * * ?"
      //cron name: 'prepararPostsFrequenciaTrigger', cronExpression: "0 0 8 * * ?"
    }

    def execute() {
        def usuarios = Usuario.list() 
		
		for (usuario in usuarios) {
			def deputados = usuarioService.getDeputadosDeUsuario(usuario,true)

			// recebe biografias aleatórias?
			if (usuario.receberBiografias) {
				prepararPostBiografiaService.preparar(usuario, usuarioService.deputadoAleatorio.id)
				log.debug("Postagens com mini-biografia de deputado aleatório de ${usuario.username} preparadas!")
			}
			// posts relativos a Deputados
			for (deputado in deputados) {
				prepararPostFrequenciaDiaService.preparar(usuario, deputado.id)
				log.debug("Postagens a sobre frequencia do deputado ${deputado.descricao} de ${usuario.username} preparadas!")
				
				prepararPostDespesaService.preparar(usuario, deputado.id)
				log.debug("Postagens a sobre despesa do deputado ${deputado.descricao} de ${usuario.username} preparadas!")
				
				prepararPostDiscursoService.preparar(usuario, deputado.id)
				log.debug("Postagens a sobre discurso do deputado ${deputado.descricao} de ${usuario.username} preparadas!")
				
			}
			log.debug("Todas as postagens sobre Deputados de ${usuario.username} já preparadas!")

			// posts relativos a Proposições			
			def proposicoes = usuarioService.getProposicoesDeUsuario(usuario)
			for (proposicao in proposicoes) {
				prepararPostVotacaoService.preparar(usuario, proposicao.id)
				log.debug("Postagens a sobre votacao da proposição ${proposicao.descricao} de ${usuario.username} preparada!")
			}
			log.debug("Todas as postagens sobre Proposições de ${usuario.username} já preparadas!")
			
		}
		log.debug("Todas as postagens de todos os usuários já preparadas!")
    }
}
