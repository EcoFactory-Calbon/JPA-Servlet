<%@ page import="java.sql.Connection" %>
<%@ page import="com.example.servletcalbon.dao.EmpresaDAO" %>
<%@ page import="com.example.servletcalbon.infra.ConnectionFactory" %>
<%@ page import="com.example.servletcalbon.dao.LocalizacaoDAO" %>
<%@ page import="com.example.servletcalbon.dao.CategoriaEmpresaDAO" %>
<%@ page import="com.example.servletcalbon.dao.PorteDAO" %>
<%@ page import="com.example.servletcalbon.modelEmpresa.Empresa" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.servletcalbon.modelFuncionario.Localizacao" %>
<%@ page import="com.example.servletcalbon.modelEmpresa.CategoriaEmpresa" %>
<%@ page import="com.example.servletcalbon.modelEmpresa.Porte" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Funcionários | Painel Administrativo</title>
  <link rel="stylesheet" href="AreaRestrita/css/layout.css" />
  <link rel="stylesheet" href="AreaRestrita/css/visualizar-empresa.css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
  <link rel="icon" href="/AreaRestrita/img/icone-logo.ico" type="image/icon" />
</head>

<body>
  <!--------------------------------------------------------- HEADER ---------------------------------------------------------->
  <header>
    <div class="bem-vindo">
      <span class="subtitulo">Olá Júlia,</span>
      <h1 class="titulo">Vizualizar Empresa</h1>
    </div>
    <!-- <div class="vizu-funcionario"><a href="/Cadastro/visualizar-funcionario.html">Vizualizar Funcionário</a></div> -->
  </header>

  <!------------------------------------------------------ BARRA LATERAL --------------------------------------------------------->
  <aside class="sidebar">
    <ul>
      <a href="inicio.jsp" class="sidebar-item active">
        <i class="fas fa-home icon-img"></i> Início
        <span class="label">Início</span>
      </a>
      <a href="visualizar-empresa.jsp" class="sidebar-item">
        <i class="fa-solid fa-building"></i>Empresas
        <span class="label">Visualizar</span>
      </a>
      <a href="AreaRestrita/visualizar-funcionario.html" class="sidebar-item">
        <i class="fa-solid fa-people-group"></i>Funcionários
        <span class="label">Visualizar</span>
      </a>
      <a href="AreaRestrita/inserir.html" class="sidebar-item">
        <i class="fas fa-plus icon-img"></i> Inserir
        <span class="label">Inserir</span>
      </a>
      <a href="AreaRestrita/editar.html" class="sidebar-item">
        <i class="fa fa-pen icon-img"></i> Editar
        <span class="label">Editar</span>
      </a>
    </ul>

    <hr class="divisao-aside">
    <a href="inicio.jsp" class="sidebar-item">
      <i class="fas fa-right-from-bracket icon-img"></i>
    </a>
  </aside>
  <main>

    <!-- BARRA DE BUSCA -->
    <div class="barra-superior">
      <label for="busca" class="input-pesquisa">
        <img src="AreaRestrita/img/icone-lupa.png" alt="Ícone de busca" class="icone-lupa" />
        <input id="busca" type="text" placeholder="Buscar por nome" aria-label="Buscar por nome" />
        <button class="btn-pesquisa"><i class="fa-solid fa-play"></i>Buscar</button>
      </label>

      <a href="AreaRestrita/inserir-empresa.html" id="btnCadastrar" class="btn-padrao botao-cadastrar">
        <img src="AreaRestrita/img/icone-adicionar.png" alt="Ícone de adicionar" class="icone-cadastro" />
        Cadastrar Empresa
      </a>
    </div>

    <!------------------------------------------------ TABELA - VIZUALIZAÇÃO -------------------------------------------------------->
    <div class="tabela-container">
      <table id="tabelaFuncionarios">
        <thead>
          <tr>
            <th>Nome da Empresa</th>
            <th>CNPJ</th>
            <th>Categoria</th>
            <th>Porte</th>
            <th>Estado</th>
            <th>Cidade</th>
            <th>Ações</th>
          </tr>
        </thead>
          <%
              Connection connection = ConnectionFactory.getConnection();
              EmpresaDAO empresaDAO = new EmpresaDAO(connection);
              LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO(connection);
              CategoriaEmpresaDAO categoriaDAO = new CategoriaEmpresaDAO(connection);
              PorteDAO porteDAO = new PorteDAO(connection);

              List<Empresa> empresas = empresaDAO.findAll();

              for (Empresa empresa : empresas) {
                  Localizacao loc = localizacaoDAO.findById((long)empresa.getIdLocalizacao()).orElse(null);
                  CategoriaEmpresa cat = categoriaDAO.findById((long)empresa.getIdCategoria()).orElse(null);
                  Porte porte = porteDAO.findById(Math.toIntExact((long) empresa.getIdPorte())).orElse(null);
          %>
        <tbody id="tabelaEmpresasBody">
          <tr data-id="1">
              <td><%= empresa.getNome() %></td>
              <td><%= empresa.getCnpj() %></td>
              <td><%= cat != null ? cat.getNome() : "N/D" %></td>
              <td><%= porte != null ? porte.getNome() : "N/D" %></td>
              <td><%= loc != null ? loc.getEstado() : "N/D" %></td>
              <td><%= loc != null ? loc.getCidade() : "N/D" %></td>
              <td class="acoes-linha">
                <button class="botao-excluir" onclick="excluirRegistroEmpresa(1)" title="Excluir empresa">
                  <i class="fas fa-trash"></i>
                </button>
                <button class="botao-salvar hidden" onclick="salvarEdicaoEmpresa(1)" title="Salvar alterações">
                  <i class="fas fa-save"></i>
                </button>
                <button class="botao-cancelar hidden" onclick="cancelarEdicaoEmpresa(1)" title="Cancelar edição">
                  <i class="fas fa-times"></i>
                </button>
                <button class="botao-viz-funcionario">
                  <a href="AreaRestrita/visualizar-funcionario.html" class="botao-visualizar" title="Ver funcionários">
                    <i class="fas fa-users"></i>
                  </a>
                </button>
              </td>
            </td>
          </tr>
          <%
              }
              ConnectionFactory.fechar(connection);
          %>

        </tbody>
      </table>
    </div>

    <div class="paginacao">
      <div class="itens-por-pagina">
        Itens por página:
        <select id="selectQtd">
          <option value="5">5</option>
          <option value="10">10</option>
          <option value="25">25</option>
          <option value="50">50</option>
          <option value="100">100</option>
        </select>
      </div>
      <div class="controles-paginacao" id="controlesPag"></div>
    </div>

    <!-- Toast -->
    <div id="toast" class="toast hidden">
      <img id="toastIcon" src="" alt="">
      <span id="toastMsg"></span>
    </div>

    <!-- Modal de confirmação de exclusão -->
    <div id="modalConfirmacao" class="modal hidden">
      <div class="modal-conteudo">
        <div class="modal-header">
          <h3>Confirmar Exclusão</h3>
          <button class="botao-fechar-modal" onclick="fecharModal()">
            <i class="fas fa-times"></i>
          </button>
        </div>
        <div class="modal-body">
          <p>Tem certeza que deseja excluir esta empresa?</p>
          <p class="texto-destaque">Esta ação não pode ser desfeita.</p>
        </div>
        <div class="modal-footer">
          <button class="botao-cancelar-modal" onclick="fecharModal()">Cancelar</button>
          <button class="botao-confirmar-exclusao" onclick="confirmarExclusaoEmpresa()">Excluir</button>
        </div>
      </div>
    </div>
    </div>
  </main>

  <!-- CSS do modal -->
  <link rel="stylesheet" href="AreaRestrita/css/modal-confirmacao.css" />

  <!-- JS da página -->
  <script src="AreaRestrita/js/sidebar-navigation.js"></script>
  <script src="AreaRestrita/js/modal-confirmacao.js"></script>
</body>

</html>