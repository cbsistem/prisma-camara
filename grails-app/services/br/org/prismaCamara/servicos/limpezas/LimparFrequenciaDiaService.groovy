package br.org.prismaCamara.servicos.limpezas

import hackathon2013.Despesa
import hackathon2013.FrequenciaDia;
import hackathon2013.FrequenciaSessao;
import hackathon2013.Parametro;

/**
 * Classe que exclui os registros defasados de {@link FrequenciaDia} e {@link FrequenciaSessao}. <br>
 * A regra é excluir todas as frequências associadas a deputados cujo "dia" seja anterior ao "ultimo_dia_frequencia" na tabela de parâmetros ({@link Parametro}). <br>
 * Varre-se todos os Deputados associados a {@link Usuario} 
 * @author jyoshiriro
 *
 */
class LimparFrequenciaDiaService extends LimpadorEntidade {

	@Override
	public void limpar() {
		
		def ultimoDiaFrequenciaS = Parametro.findBySigla('ultimo_dia_frequencia').valor
		if (!ultimoDiaFrequenciaS)
			return
		
		def ultimoDiaFrequencia = Date.parse('dd/MM/yyyy', ultimoDiaFrequenciaS)
		
		for (freq in FrequenciaDia.executeQuery("from FrequenciaDia where dia<=?",[ultimoDiaFrequencia])) {
			freq.delete()
			log.debug("Frequencia ${freq.id} excluida")
		}
		log.debug("Limpezas de Frequencias de Deputados defasadas concluída com sucesso!")
	}

}
