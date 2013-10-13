class UrlMappings {

	static mappings = {
		
		"/painel" (controller: 'painel', action: 'index')
		
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
