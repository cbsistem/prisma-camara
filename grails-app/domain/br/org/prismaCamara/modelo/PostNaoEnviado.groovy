package br.org.prismaCamara.modelo


class PostNaoEnviado {
	
	String hash
	String conteudo
	
	Long idEntidade 
	String tipoInformacao 
	
	boolean pendente = false // um post "pendente=true" é aquele que foi tentado o envio à rede social mas ocorreu algum problema
	Integer tentativas = 0 // a cada 3 tentativas de reenvio o post será excluído 
	
	static transients = ['hashGerado']
	
	static constraints = {
		hash(maxSize:256) 
		conteudo(maxSize:8192) 
		tipoInformacao(maxSize:30) 
	}

	def beforeValidate() {
		if (!hash)
			hash = hashGerado
	}
	
	//"${idEntidade}-${tipoInformacao}-${usuario.tipoRede}-${conteudo[0..143]}".encodeAsSHA1()
	String getHashGerado() {
		getHashGerado(idEntidade, tipoInformacao)
	}
	
	public static String getHashGerado(idEntidade,tipoInformacao) {
		"${idEntidade}-${tipoInformacao}".encodeAsSHA1()
	}
	
	def afterUpdate() {
		if (tentativas>=20) {
			this.delete();
		}
	}
	
}
