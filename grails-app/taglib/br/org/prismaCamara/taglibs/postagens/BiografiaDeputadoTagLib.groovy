package br.org.prismaCamara.taglibs.postagens


class BiografiaDeputadoTagLib extends PostagemTag {

	/**
	 * Mensagem de Biografia de Deputado.
	 * Renderiza o texto a partir de "_biografia-deputado-xxx.gsp"
     * @attr dep REQUIRED Instância de {@link Deputado}
     * @attr tipo REQUIRED Tipo de postagem (facebook ou twitter). Usar um {@link Postagem}.TIPO_XXX
	 */
	String getConteudo(attrs) {
		String mensagem = getTexto("biografia-deputado-${attrs.tipo}", attrs)
		return mensagem
	}
}
 