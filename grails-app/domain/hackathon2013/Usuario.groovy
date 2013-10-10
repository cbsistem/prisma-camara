package hackathon2013

import br.org.prismaCamara.mensagens.Postagem;

class Usuario {

	transient springSecurityService

	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	String nome
	String tipoRede
	boolean receberBiografias = true
	
	static hasMany = [partidos:Partido,deputados:Deputado,proposicoes:Proposicao]
	
	static constraints = {
		username blank: false, unique: true
		password blank: false
		nome(maxSize:50, nullable:true)
		tipoRede(nullable:true, inList:Postagem.TIPOS)
	}

	static mapping = {
		password column: '`password`'
		
		deputados(cascade:'all')
		partidos(cascade:'all')
		proposicoes(cascade:'all')
	}

	Set<Perfil> getAuthorities() {
		UsuarioPerfil.findAllByUsuario(this).collect { it.perfil } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
	
	
	
}
