<%@ page import="java.sql.Connection" %>
<%@ page import="com.example.servletcalbon.infra.ConnectionFactory" %>
<%@ page import="com.example.servletcalbon.dao.EmpresaDAO" %>
<%@ page import="com.example.servletcalbon.modelEmpresa.Empresa" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Empresas | Painel Administrativo</title>
    <link rel="stylesheet" href="AreaRestrita/css/layout.css" />
    <link rel="stylesheet" href="AreaRestrita/css/visualizar-empresa.css" />
    <link rel="stylesheet" href="AreaRestrita/css/acoes.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <link rel="icon" href="LandingPage/assets/icone-logo.png" type="image/icon" />
</head>

<body>
<!--------------------------------------------------------- HEADER --------------------------------------------------------->
<header>
    <div class="bem-vindo">
        <span class="subtitulo">Olá Calbon,</span>
        <h1 class="titulo">Visualizar Empresas</h1>
    </div>
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
        <a href="visualizar-funcionario.jsp" class="sidebar-item">
            <i class="fa-solid fa-people-group"></i>Funcionários
            <span class="label">Visualizar</span>
        </a>
        <a href="AreaRestrita/inserir.html" class="sidebar-item">
            <i class="fas fa-plus icon-img"></i> Inserir
            <span class="label">Inserir</span>
        </a>

    </ul>

    <hr class="divisao-aside">
    <a href="index.html" class="sidebar-item">
        <i class="fas fa-right-from-bracket icon-img"></i>
    </a>
</aside>

<!------------------------------------------------------ CONTEÚDO PRINCIPAL --------------------------------------------------------->
<main>
    <!-- BARRA DE BUSCA -->
    <div class="barra-superior">
        <label for="busca" class="input-pesquisa">
            <img src="AreaRestrita/img/icone-lupa.png" alt="Ícone de busca" class="icone-lupa" />
            <input id="busca" type="text" placeholder="Buscar por nome" aria-label="Buscar por nome" />
            <button class="btn-pesquisa"><i class="fa-solid fa-play"></i>Buscar</button>
        </label>

        <a href="AreaRestrita/inserir-empresa.html" id="btnCadastrar" class="btn-padrao botao-cadastrar">
            <img src="AreaRestrita/img/icone-adicionar.png" alt="Ícone de adicionar" class="icone-cadastro" />Cadastrar Empresa
        </a>
    </div>

    <!------------------------------------------------ TABELA - VISUALIZAÇÃO -------------------------------------------------------->
    <div class="tabela-container">
        <table id="tabelaEmpresas">
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
            <tbody>
            <%
                Connection connection = ConnectionFactory.getConnection();
                EmpresaDAO empresaDAO = new EmpresaDAO(connection);
                List<Empresa> empresas = empresaDAO.findAll();

                for (Empresa empresa : empresas) {
            %>
            <tr>
                <td><%= empresa.getNome() %></td>
                <td><%= empresa.getCnpj() %></td>
                <td><%= empresa.getCategoriaNome() %></td>
                <td><%= empresa.getPorteNome() %></td>
                <td><%= empresa.getEstado() %></td>
                <td><%= empresa.getCidade() %></td>
                <td class="acoes-linha">
                    <!-- Botão Excluir -->
                    <button type="button" class="botao-excluir" data-id="<%= empresa.getId() %>" title="Excluir empresa">
                        <i class="fas fa-trash"></i>
                    </button>
                    <a href="visualizar-funcionario.jsp" class="botao-visualizar" title="Ver funcionários">
                        <i class="fas fa-users"></i>
                    </a>
                </td>
            </tr>
            <% } %>
            <%
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

    <!-- Modal de confirmação -->
    <div id="modalConfirmacao" class="modal" style="display: none;">
        <div class="conteudo">
            <h2>Confirmar exclusão</h2>
            <p>Tem certeza que deseja excluir esta empresa? Essa ação não pode ser desfeita.</p>
            <div class="botoes">
                <button class="cancelar">Cancelar</button>
                <button class="confirmar">Confirmar</button>
            </div>
        </div>
    </div>
</main>

<!-- JS da página -->
<script src="AreaRestrita/js/sidebar-navigation.js"></script>
<script src="AreaRestrita/js/modal-confirmacao.js"></script>
<script src="AreaRestrita/js/crud-empresas.js"></script>
</body>
</html>
