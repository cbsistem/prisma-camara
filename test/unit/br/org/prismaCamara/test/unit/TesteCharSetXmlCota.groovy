/*
 * Copyright 2013 de José Yoshiriro (jyoshiriro@gmail.com) e Raimundo Norberto (raimundonorberto@gmail.com)
 * Este arquivo é parte do programa Olho na Câmara.
 * 
 * O Olho na Câmara é um software livre; você pode redistribuí-lo e/ou modificá-lo dentro
 * dos termos da GNU Affero General Public License como publicada pela Fundação do Software Livre
 * (FSF); na versão 3 da Licença. Este programa é distribuído na esperança que possa ser
 * útil, mas SEM NENHUMA GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer
 * MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a licença para maiores detalhes. Você deve ter
 * recebido uma cópia da GNU Affero General Public License, sob o título 'LICENCA.txt', junto com
 * este programa, se não, acesse http://www.gnu.org/licenses/
 */
package br.org.prismaCamara.test.unit

import grails.test.mixin.*
import br.org.prismaCamara.modelo.Deputado
import br.org.prismaCamara.util.xml.LerXmlCota

class TesteCharSetXmlCota {

	static main(args) {
		File fxml = new File("/home/yoshiriro/Downloads/AnoAtual.zip")
		LerXmlCota ler = new LerXmlCota()
		def fv = new File("../deputados.txt").text //18 (jordy) 21 (elcione)
		def deputados = []
		fv.eachLine {
			deputados+=new Deputado(ultimoDiaGasto:(new Date()-665),matricula:it.toInteger())
		}
		def resultado = ler.getNovasDespesas(fxml.bytes, deputados as Set, "testeX"+new Date().getTime())
		resultado.each { mapa ->
			mapa.each{
				if (['txtDescricao','deputado.matricula','dataEmissao'].contains(it.key)) {
					println "->"+it.value+"<-"				
				}
			}
		}
	}

}
