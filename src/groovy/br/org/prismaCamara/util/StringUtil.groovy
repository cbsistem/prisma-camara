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
package br.org.prismaCamara.util

class StringUtil {

	public static boolean iniciaCom(StringBuilder sb, String... inicios) {
		for (String inicio : inicios) {
			if (sb.indexOf(inicio)==0)
				return true;
		}
		return false;
	}
	
	public static boolean terminaCom(StringBuilder sb, String... fins) {
		return terminaCom(sb.toString(), fins)
	}
	
	public static boolean terminaCom(String texto, String... fins) {
		for (String fim : fins) {
			if (texto.endsWith(fim))
				return true;
		}
		return false;
	}
	
	public static boolean contem(StringBuilder sb, String... termos) {
		for (String termo : termos) {
			if (sb.indexOf(termo)>=0)
				return true;
		}
		return false;
	}

	public static void substituir(StringBuilder sb, String oQue, String paraOQue) {
		int id=sb.indexOf(oQue);
		if (id<0) return;
		sb.replace(id, id+oQue.length(), paraOQue);
	}
	
	public static void substituirFim(StringBuilder sb, String novoFim) {
		sb.trimToSize();
		int fimId = sb.length()-novoFim.length();
		StringBuilder sb2 = new StringBuilder(sb.substring(0, fimId)).append(novoFim);
		//if (sb2.length()>sb.length())
			//sb2 = new StringBuilder(sb.substring(0, --fimId)).append(novoFim);
		sb.delete(0, sb.length());
		sb.append(sb2);
	}
	
	public static void substituirVarios(StringBuilder sb, String novoTermo, String... termosASusbstituir) {
		for (String termo : termosASusbstituir) {
			substituir(sb,termo,novoTermo);
		}
	}

	public static void substituirInicio(StringBuilder sb, String novoInicio) {
		StringBuilder sb2 = new StringBuilder(novoInicio).append((sb.substring(novoInicio.length(),sb.length())));
		sb.delete(0, sb.length());
		sb.append(sb2);
	}

	public static void aplicarAjustesPalavra(StringBuilder sbPalavra,
			Properties propSubstituicoesLetras,
			Properties propSubstituicoesLetrasFinal) {
		
		// trocas por letras em qualquer posição
		for (Object chaveO:propSubstituicoesLetras.keySet()) {
			String[] chaves = chaveO.toString().split(",");
			for (String chave : chaves) {
				substituir(sbPalavra,chave,propSubstituicoesLetras.getProperty(chaveO.toString()).trim());
			}
		}
		
		// trocas por letras em no final da frase
		for (Object chaveO:propSubstituicoesLetrasFinal.keySet()) {
			String[] chaves = chaveO.toString().split(",");
			if (terminaCom(sbPalavra, chaves))
				substituirFim(sbPalavra,propSubstituicoesLetrasFinal.getProperty(chaveO.toString()).trim());
		}
		
		substituirVarios(sbPalavra, "", "_", " ");
	}
	
}
