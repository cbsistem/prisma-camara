package br.org.prismaCamara.controle

import grails.gsp.PageRenderer;
import br.org.prismaCamara.mensagem.Postagem
import br.org.prismaCamara.mensagem.PostagemBiografiaDeputado
import br.org.prismaCamara.mensagem.PostagemDiscursoDeputado
import br.org.prismaCamara.mensagem.PostagemFrequenciaDeputado
import br.org.prismaCamara.mensagem.PostagemGastoDeputado
import br.org.prismaCamara.mensagem.PostagemVotacaoProposicao
import br.org.prismaCamara.modelo.Deputado;
import br.org.prismaCamara.modelo.Proposicao;
import br.org.prismaCamara.modelo.Votacao;
import br.org.prismaCamara.servico.UsuarioService;

class PostagensController {

	UsuarioService usuarioService
	PageRenderer groovyPageRenderer
	
    def index() {
		[proposicoes:proposicoes, deputados:deputados]
	}
	
	def getDeputados() {
		usuarioService.deputadosMapeados	
	}
	
	def getProposicoes() {
		def proposicoesV = usuarioService.proposicoesMapeadas //Votacao.executeQuery("select proposicao from Votacao") 
		return proposicoesV
	}
	
	def biografiaDeputado() {
		Postagem post = new PostagemBiografiaDeputado(r:groovyPageRenderer)
		flash.postagem = post.getTexto([tipo:Postagem.TIPO_FACE])
		render(view:'index')
	}
	
	def frequenciaDeputado(Long idDeputado) {
		Deputado deputado = Deputado.get(idDeputado)
		Postagem post = new PostagemFrequenciaDeputado(r:groovyPageRenderer) 
		flash.postagem = post.getTexto([dep:deputado,tipo:Postagem.TIPO_FACE])
		render(view:'index')
	}

	def gastoDeputado(Long idDeputado) {
		Deputado deputado = Deputado.get(idDeputado)
		Postagem post = new PostagemGastoDeputado(r:groovyPageRenderer)
		flash.postagem = post.getTexto([dep:deputado,tipo:Postagem.TIPO_FACE])
		render(view:'index')
	}
	
	def discursoDeputado(Long idDeputado) {
		Deputado deputado = Deputado.get(idDeputado)
		Postagem post = new PostagemDiscursoDeputado(r:groovyPageRenderer)
		flash.postagem = post.getTexto([dep:deputado,tipo:Postagem.TIPO_FACE])
		render(view:'index')
	}

	def votacaoProposicao(Long idProposicao) {
		Proposicao proposicao = Proposicao.get(idProposicao)
		Postagem post = new PostagemVotacaoProposicao(r:groovyPageRenderer)	
		flash.postagem = post.getTexto([prop:proposicao,tipo:Postagem.TIPO_FACE])
		render(view:'index',model:[proposicoes:proposicoes,deputados:deputados])
	}	
}
