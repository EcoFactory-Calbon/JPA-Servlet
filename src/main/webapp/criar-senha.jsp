<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Acesso à Conta - Senha</title>
    <link rel="stylesheet" href="LandingPage/css/login.css">
    <link rel="shortcut icon" href="LandingPage/assets/icone-logo.ico" type="image/x-icon">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
</head>

<body>
    <header>
        <img class="logoheader" width="30" src="LandingPage/assets/icone-logo.png" alt="icone-logo">
        <nav class="lista-header">
            <ul>
                <li><a href="index.html">Home</a></li>
            </ul>
        </nav>
        <div class="button-header">
            <a href="LandingPage/cadastro.html" class="entrar">Cadastrar</a>
        </div>
    </header>

    <section class="login">
        <div class="login-card">
            <h2 class="titulo">Criar senha</h2>

            <form class="login-form" id="formSenha" action="senhaServlet" method="POST">

                <div class="input-group">
                    <label for="senha">Senha</label>
                    <input type="password" id="senha" name="senha" required>
                    <span class="erro"></span>
                </div>

                <div class="input-group">
                    <label for="confirmarSenha">Confirmar Senha</label>
                    <input type="password" id="confirmarSenha" name="confirmarSenha" required>
                    <span class="erro"></span>
                </div>

                <div class="lembrar-me">
                    <input type="checkbox" id="lembrar" name="lembrar" checked>
                    <label for="lembrar">Lembrar de mim neste dispositivo</label>
                </div>

                <button class="entrar-crud">Entrar</button>
            </form>

    </section>
    <footer class="rodape-simples">
        <p class="copyright">&copy; 2025 Calbon</p>
        <a href="/politica-de-privacidade" class="link-termo">Privacidade e termos</a>
    </footer>
    <script src="LandingPage/regex.js" defer></script>
</body>

</html>