-- Parâmetros
INSERT INTO hackathon.parametro (id,version,descricao,sigla,valor) VALUES (1,0,'URL para chegar a página de Biografia de deputado(a)','url_biografia_deputado','http://www2.camara.leg.br/deputados/pesquisa/layouts_deputados_biografia?pk=');
INSERT INTO hackathon.parametro (id,version,descricao,sigla,valor) VALUES (2,0,'URL do servico que retorna o XML com todos os deputados atuais','url_listagem_deputados','http://www.camara.gov.br/SitCamaraWS/Deputados.asmx/ObterDeputados');
INSERT INTO hackathon.parametro (id,version,descricao,sigla,valor) VALUES (3,0,'URL do serviço que retorna o XML com Proposições','url_listagem_proposicoes','http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ListarProposicoes?numero=&datApresentacaoIni=&datApresentacaoFim=&autor=&parteNomeAutor=&siglaPartidoAutor=&siglaUFAutor=&generoAutor=&codEstado=&codOrgaoEstado=&emTramitacao=&sigla=${sigla}&ano=${ano}');
INSERT INTO hackathon.parametro (id,version,descricao,sigla,valor) VALUES (4,0,'URL do serviço que retorna o XML com os tipos de Proposições','url_listagem_tipos_proposicoes','http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ListarSiglasTipoProposicao');
INSERT INTO hackathon.parametro (id,version,descricao,sigla,valor) VALUES (5,0,'URL do serviço que retorna o XML com as votações de uma Proposição','url_votacoes_proposicoes','http://www.camara.gov.br/SitCamaraWS/Proposicoes.asmx/ObterVotacaoProposicao?tipo=${tipo}&numero=${numero}&ano=${ano}');
INSERT INTO hackathon.parametro (id,version,descricao,sigla,valor) VALUES (6,0,'URL do servico que retorna o XML as frequencias de todos os deputados em determindado Dia','url_frequecias_dia','http://www.camara.gov.br/SitCamaraWS/sessoesreunioes.asmx/ListarPresencasDia?numLegislatura=&numMatriculaParlamentar=&siglaPartido=&siglaUF=&data=${data}');
INSERT INTO hackathon.parametro (id,version,descricao,sigla,valor) VALUES (7,0,'URL que recupera o XML com os gastos Atuais da Câmara dos Deputados', 'url_gastos', 'http://www.camara.gov.br/cotas/AnoAtual.zip');
INSERT INTO hackathon.parametro (id,version,descricao,sigla,valor) VALUES (8,0,'URL do serviço que retorna o XML com dos discursos dos Deputados em certo dia', 'url_discursos', 'http://www.camara.gov.br/sitcamaraws/SessoesReunioes.asmx/ListarDiscursosPlenario?codigoSessao=&parteNomeParlamentar=&siglaPartido=&siglaUF=&dataIni=${data}&dataFim=${data}');
INSERT INTO hackathon.parametro (id,version,descricao,sigla,valor) VALUES (9,0,'URL do site da Camara com os discursos de certo Deputado em certo dia', 'url_discurso_deputado_dia', 'http://www.camara.gov.br/internet/sitaqweb/TextoHTML.asp?etapa=${etapa}&nuSessao=${nuSessao}&nuQuarto=${nuQuarto}&nuOrador=${nuOrador}&nuInsercao=${nuInsercao}&dtHorarioQuarto=${horario}&sgFaseSessao=${cdFaseSessao}&Data=${data}&txApelido=${nomeParlamentar}
');
INSERT INTO hackathon.parametro (id,version,descricao,sigla,valor) VALUES (10,0,'URL do site da Camara com Informações Gerais dos discursos em certo dia', 'url_formacoes_discursos_dia', 'http://www.camara.gov.br/internet/sitaqweb/discursodireto.asp?nuSessao=${nuSessao}');
INSERT INTO hackathon.parametro (id,version,descricao,sigla,valor) VALUES (11,0,'URL do site da Camara com Informações sobre os Gastos de certo Deputado', 'url_gastos_site', 'http://www.camara.gov.br/cota-parlamentar/consulta-cota-parlamentar?ideDeputado=');
INSERT INTO hackathon.parametro (id,version,descricao,sigla,valor) VALUES (12,0,'URL do site da Camara com Informações de uma Proposição', 'url_proposicao_site', 'http://www.camara.gov.br/proposicoesWeb/fichadetramitacao?idProposicao=');

-- Usuário e relações
INSERT INTO hackathon.usuario (id, version, account_expired, account_locked, enabled, nome, password, password_expired, receber_biografias, tipo_rede, username) VALUES (1, 0, 0, 0, 1, '', '0', 0, 1, 'face', 'jyoshiriro');
INSERT INTO hackathon.usuario_deputado (id, version, deputado_id, usuario_id) VALUES (1, 0, 10, 1);
INSERT INTO hackathon.usuario_partido (id, version, partido_id, usuario_id) VALUES (1, 0, 2, 1);
INSERT INTO hackathon.usuario_proposicao (id, version, proposicao_id, usuario_id) VALUES (1, 0, 21, 1);
INSERT INTO hackathon.usuario_proposicao (id, version, proposicao_id, usuario_id) VALUES (2, 0, 184, 1);

-- Indice na tabela de despesas
CREATE INDEX idx_despesa ON hackathon.despesa(data_emissao,deputado_id,txt_numero);