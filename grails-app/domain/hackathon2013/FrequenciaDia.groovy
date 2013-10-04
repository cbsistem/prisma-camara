package hackathon2013

class FrequenciaDia {
	
	Date dia
	String frequenciaDia
	String justificativa
		
	Deputado deputado
	
	static hasMany = [frequenciasSessao:FrequenciaSessao]
	
	static mapping = {
		deputado(cascade:'all')
	}
	
	static constraints = {
		frequenciaDia(maxSize:12)
		justificativa(maxSize:1024, nullable:true)
	}
}
