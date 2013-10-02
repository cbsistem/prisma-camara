package hackathon2013

class Deputado {
	
	static String URL_BIOGRAFIAS = "http://www.camara.leg.br/internet/Deputado/dep_Detalhe.asp?id="
	
	Integer ideCadastro
	String condicao
	Integer matricula
	String nome
	String nomeParlamentar
	String sexo
	String uf
	String partido
	String fone
	String email
	Boolean ativo
	
	List<FrequenciaDia> frequenciasDia
	
	static hasMany = [comissoesTitular:Comissao, comissoesSuplente:Comissao]
	
	static transients = ['urlBiografia']
	
	static constraints = {
		condicao(maxSize:20)
		nome(maxSize:60)
		nomeParlamentar(maxSize:40)
		sexo(maxSize:10)
		uf(maxSize:2)
		partido(maxSize:30)
		fone(maxSize:20)
		email(maxSize:60)
	}
	
	String getUrlBiografia() {
		"${URL_BIOGRAFIAS}${ideCadastro}"
	}
}
