<%@ page import="java.util.List" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.example.servletcalbon.infra.ConnectionFactory" %>
<%@ page import="com.example.servletcalbon.dao.EmpresaDAO" %>
<%@ page import="com.example.servletcalbon.modelEmpresa.Empresa" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Visualizar Empresas</title>
    <link rel="stylesheet" href="AreaRestrita/css/layout.css">
    <link rel="icon" href="AreaRestrita/img/icone-logo.ico" type="image/icon" />
    <link rel="stylesheet" href="AreaRestrita/css/visualizar-empresa.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <link rel="stylesheet" href="AreaRestrita/css/modal-confirmacao.css">
</head>
<body>
<header>
    <div class="bem-vindo">
        <span class="subtitulo">Olá Júlia,</span>
        <h1 class="titulo">Visualizar Empresa</h1>
    </div>
</header>

<aside class="sidebar">
    <ul>
        <a href="inicio.jsp" class="sidebar-item active">
            <i class="fas fa-home icon-img"></i> Início
        </a>
        <a href="visualizar-empresa.jsp" class="sidebar-item">
            <i class="fa-solid fa-building"></i>Empresas
        </a>
        <a href="AreaRestrita/visualizar-funcionario.html" class="sidebar-item">
            <i class="fa-solid fa-people-group"></i>Funcionários
        </a>
        <a href="AreaRestrita/inserir.html" class="sidebar-item">
            <i class="fas fa-plus icon-img"></i> Inserir
        </a>
        <a href="AreaRestrita/editar.html" class="sidebar-item">
            <i class="fa fa-pen icon-img"></i> Editar
        </a>
    </ul>
</aside>

<main>
    <div class="barra-superior">
        <label for="busca" class="input-pesquisa">
            <img src="AreaRestrita/img/icone-lupa.png" alt="Ícone de busca" class="icone-lupa">
            <input id="busca" type="text" placeholder="Buscar por nome">
            <button class="btn-pesquisa"><i class="fa-solid fa-play"></i>Buscar</button>
        </label>
        <a href="AreaRestrita/inserir-empresa.html" id="btnCadastrar" class="btn-padrao botao-cadastrar">
            <img src="AreaRestrita/img/icone-adicionar.png" alt="Ícone de adicionar" class="icone-cadastro">
            Cadastrar Empresa
        </a>
    </div>

    <div class="tabela-container">
        <table id="tabelaFuncionarios">
            <thead>
            <tr>
                <th>Nome da Empresa</th>
                <th>CNPJ</th>
                <th>Categoria</th>
                <th>Descrição Categoria</th>
                <th>Porte</th>
                <th>Estado</th>
                <th>Cidade</th>
                <th>Ações</th>
            </tr>
            </thead>
            <tbody id="tabelaEmpresasBody">
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
                <td><%= empresa.getCategoriaDescricao() %></td>
                <td><%= empresa.getPorteNome() %></td>
                <td><%= empresa.getEstado() %></td>
                <td><%= empresa.getCidade() %></td>
                <td class="acoes-linha">
                    <!-- Formulário POST para remover empresa -->
                    <form method="post" action="removerEmpresaServlet" style="display:inline;">
                        <input type="hidden" name="id" value="<%= empresa.getId() %>"/>
                        <button type="submit" class="botao-excluir" title="Excluir empresa">
                            <i class="fas fa-trash"></i>
                        </button>
                    </form>
                    <a href="AreaRestrita/visualizar-funcionario.html" class="botao-visualizar" title="Ver funcionários">
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
    <!-- Modal de confirmação de exclusão -->
    <div id="modalConfirmacao" class="modal hidden">
        <div class="modal-conteudo">
            <div class="modal-header">
            <h3>Confirmar Exclusão</h3>
            <button class="botao-fechar-modal" onclick="fecharModal()">
                <i class="fas fa-times"></i>
            </button>
        </div>
        </div>
        </div>
            <div class="modal-body">
                <p>Tem certeza que deseja excluir esta empresa?</p>
                <p class="texto-destaque">Esta ação não pode ser desfeita.</p>
            </div> <div class="modal-footer">
                <button class="botao-cancelar-modal" onclick="fecharModal()">Cancelar</button>
                <button class="botao-confirmar-exclusao">Excluir</button>
            </div>
</main> <script src="AreaRestrita/js/sidebar-navigation.js">
</script> <script src="AreaRestrita/js/modal-confirmacao.js">
</script>
</body>
</html>