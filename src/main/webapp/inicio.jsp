<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Início | Painel Administrativo</title>
    <link rel="stylesheet" href="AreaRestrita/css/layout.css" />
    <link rel="stylesheet" href="AreaRestrita/css/inicio.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <link rel="icon" href="AreaRestrita/img/icone-logo.ico" type="image/icon" />
</head>

<body>
<!--------------------------------------------------------- HEADER ---------------------------------------------------------->
<header>
    <div class="bem-vindo">
        <span class="subtitulo">Olá Júlia,</span>
        <h1 class="titulo">Bem-vindo(a) a Área Restrita do Calbon!</h1>
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
    <a href="index.html" class="sidebar-item">
        <i class="fas fa-right-from-bracket icon-img"></i>
    </a>
</aside>

<main>
    <!---------------------------------------------- CONTEÚDO PRINCIPAL ------------------------------------------------------------>

    <!-- Cards de Estatísticas -->
    <section class="estatisticas">
        <div class="card-estatistica total">
            <div class="icone-card">
                <i class="fas fa-building"></i>
            </div>
            <div class="conteudo-card">
                <h3>Total de Empresas</h3>
                <p>56</p>
            </div>
        </div>

        <div class="card-estatistica ativas">
            <div class="icone-card">
                <i class="fas fa-check-circle"></i>
            </div>
            <div class="conteudo-card">
                <h3>Empresas Ativas</h3>
                <p>48</p>
            </div>
        </div>

        <div class="card-estatistica funcionarios">
            <div class="icone-card">
                <i class="fas fa-users"></i>
            </div>
            <div class="conteudo-card">
                <h3>Total de Funcionários</h3>
                <p>124</p>
            </div>
        </div>
    </section>

    <!-- Seção de Navegação Rápida -->
    <section class="navegacao-rapida">
        <h2 >Navegação Rápida</h2>
        <p>Acesse rapidamente as principais funcionalidades do sistema</p>

        <div class="botoes-navegacao">
            <a href="visualizar-empresa.jsp" class="botao-navegacao empresa">
                <div class="icone-botao">
                    <i class="fas fa-building"></i>
                </div>
                <div class="conteudo-botao">
                    <h3>Visualizar Empresas</h3>
                    <p>Gerencie o cadastro de empresas, visualize dados e realize operações CRUD</p>
                </div>
                <div class="seta-botao">
                    <i class="fas fa-arrow-right"></i>
                </div>
            </a>

            <a href="AreaRestrita/visualizar-funcionario.html" class="botao-navegacao funcionario">
                <div class="icone-botao">
                    <i class="fas fa-users"></i>
                </div>
                <div class="conteudo-botao">
                    <h3>Visualizar Funcionários</h3>
                    <p>Gerencie o cadastro de funcionários, visualize dados e realize operações CRUD</p>
                </div>
                <div class="seta-botao">
                    <i class="fas fa-arrow-right"></i>
                </div>
            </a>
        </div>
    </section>

    <!-- Seção de Ações Rápidas -->
    <section class="acoes-rapidas">
        <h2>Ações Rápidas</h2>
        <p>Operações mais utilizadas no sistema</p>

        <div class="grid-acoes">
            <button class="acao-item"  onclick="window.location.href='AreaRestrita/inserir.html'">
                <i class="fas fa-plus-circle"></i>
                <span>Nova Empresa</span>
            </button>

            <button class="acao-item" onclick="window.location.href='AreaRestrita/inserir.html'">
                <i class="fas fa-user-plus"></i>
                <span>Novo Funcionário</span>
            </button>
        </div>
    </section>
</main>

<!-- Toast -->
<div id="toast" class="toast hidden">
    <img id="toastIcon" src="" alt="">
    <span id="toastMsg"></span>
</div>

<!-- JS da página -->
<script src="AreaRestrita/js/toast.js"></script>
<script src="AreaRestrita/js/sidebar-navigation.js"></script>
</body>
</html>