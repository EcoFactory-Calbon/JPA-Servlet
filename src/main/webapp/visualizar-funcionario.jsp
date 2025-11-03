<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.example.servletcalbon.infra.ConnectionFactory" %>
<%@ page import="com.example.servletcalbon.dao.FuncionarioDAO" %>
<%@ page import="com.example.servletcalbon.modelFuncionario.Funcionario" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Connection" %>

<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="PT-BR" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Funcionários | Painel Administrativo</title>
    <link rel="stylesheet" href="AreaRestrita/css/layout.css" />
    <link rel="stylesheet" href="AreaRestrita/css/acoes.css">
    <link rel="stylesheet" href="AreaRestrita/css/visualizar-empresa.css">
    <link rel="stylesheet" href="AreaRestrita/css/visualizar-funcionario.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <link rel="icon" href="LandingPage/assets/icone-logo.png" type="image/icon" />
</head>

<body>
<!--------------------------------------------------------- HEADER ---------------------------------------------------------->
<header>
    <div class="bem-vindo">
        <span class="subtitulo">Olá Júlia,</span>
        <h1 class="titulo">Visualizar Funcionário</h1>
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
            <i class="fa-solid fa-building"></i> Empresas
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
    <a href="index.html" class="sidebar-item">
        <i class="fas fa-right-from-bracket icon-img"></i>
    </a>
</aside>
<!---------------------------------------------- BARRA SUPERIOR ------------------------------------------------------------>
<main>
    <div class="barra-superior">
        <label for="busca" class="input-pesquisa">
            <img src="AreaRestrita/img/icone-lupa.png" alt="Ícone de busca" class="icone-lupa" />
            <input id="busca" type="text" placeholder="Buscar por nome" aria-label="Buscar por nome" />
            <button class="btn-pesquisa"><i class="fa-solid fa-play"></i>Buscar</button>
        </label>
        <a href="AreaRestrita/inserir-funcionario.html" id="btnCadastrar" class="btn-padrao botao-cadastrar">
            <img src="AreaRestrita/img/icone-adicionar.png" alt="Ícone de adicionar" class="icone-cadastro" />Cadastrar Funcionário</a>
    </div>

    <!------------------------------------------------ TABELA - VIZUALIZAÇÃO -------------------------------------------------------->
    <div class="tabela-container">
        <table id="tabelaFuncionarios">
            <thead>
            <tr>
                <th>Crachá</th>
                <th>Nome</th>
                <th>Sobrenome</th>
                <th>E-mail</th>
                <th>Cargo</th>
                <th>Setor</th>
                <th>Gestor</th>
                <th>Estado</th>
                <th>Cidade</th>
                <th>Ações</th>
            </tr>
            </thead>
            <tbody>
            <%
                Connection connection = null;
                try {
                    connection = ConnectionFactory.getConnection();
                    FuncionarioDAO funcionarioDAO = new FuncionarioDAO(connection);
                    List<Funcionario> funcionarios = funcionarioDAO.findAll();

                    if (funcionarios.isEmpty()) {
            %>
            <tr>
                <td colspan="10" style="text-align: center; padding: 20px;">
                    Nenhum funcionário cadastrado.
                </td>
            </tr>
            <%
            } else {
                for (Funcionario funcionario : funcionarios) {
            %>
            <tr>
                <td><%= funcionario.getNumeroCracha() %></td>
                <td><%= funcionario.getNome() %></td>
                <td><%= funcionario.getSobrenome() != null ? funcionario.getSobrenome() : "" %></td>
                <td><%= funcionario.getEmail() %></td>
                <td>Cargo ID: <%= funcionario.getIdCargo() %></td>
                <td>Setor ID: <%= funcionario.getIdCargo() %></td>
                <td><%= funcionario.isGestor() ? "Sim" : "Não" %></td>
                <td>Estado ID: <%= funcionario.getIdLocalizacao() %></td>
                <td>Cidade ID: <%= funcionario.getIdLocalizacao() %></td>
                <td>
                    <div class="acoes-linha">
                        <button class="btn-excluir" onclick="if(confirm('Tem certeza que deseja excluir este funcionário?')) { window.location.href='excluirFuncionarioServlet?numeroCracha=<%= funcionario.getNumeroCracha() %>' }">
                            <i class="fas fa-trash"></i>
                        </button>
                    </div>
                </td>
            </tr>
            <%
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            %>
            <tr>
                <td colspan="10" style="text-align: center; padding: 20px; color: red;">
                    Erro ao carregar funcionários: <%= e.getMessage() %>
                </td>
            </tr>
            <%
                } finally {
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            %>
            </tbody>
        </table>
    </div>

    <!-- Itens por página -->
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
</main>

<!-- JS da página -->
<script src="AreaRestrita/js/sidebar-navigation.js"></script>
</body>
</html>