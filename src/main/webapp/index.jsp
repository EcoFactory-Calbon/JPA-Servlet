<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Cadastro Empresa</title>
    <style>
        /* === RESET E ESTILO GERAL === */
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: "Segoe UI", Arial, sans-serif;
        }

        body {
            background: linear-gradient(135deg, #0c0c0c, #051638);
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            color: #fff;
        }

        /* === CONTAINER === */
        #quadrado {
            background-color: rgba(10, 30, 70, 0.95);
            width: 400px;
            padding: 40px 30px;
            border-radius: 20px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.6);
            text-align: center;
            transition: transform 0.2s ease-in-out;
        }

        #quadrado:hover {
            transform: scale(1.02);
        }

        h1 {
            margin-bottom: 25px;
            font-size: 26px;
            color: #fff;
            letter-spacing: 1px;
        }

        /* === INPUTS === */
        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #2a4b8d;
            border-radius: 8px;
            background-color: rgba(255, 255, 255, 0.05);
            color: #fff;
            font-size: 15px;
            outline: none;
            transition: border-color 0.3s;
        }

        input[type="text"]:focus {
            border-color: #3fa9f5;
            box-shadow: 0 0 5px #3fa9f5;
        }

        /* === BOTÃO === */
        input[type="submit"] {
            width: 100%;
            padding: 12px;
            margin-top: 15px;
            background: #3fa9f5;
            border: none;
            border-radius: 8px;
            color: #fff;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.3s, transform 0.1s;
        }

        input[type="submit"]:hover {
            background: #1d8fe2;
            transform: translateY(-1px);
        }

        /* === LINK === */
        h3 {
            margin-top: 25px;
            font-weight: normal;
        }

        a {
            color: #3fa9f5;
            text-decoration: none;
            transition: color 0.3s;
        }

        a:hover {
            color: #66c0ff;
            text-decoration: underline;
        }
    </style>
</head>

<body>
<div id="quadrado">
    <h1>Cadastro Empresa</h1>
    <form action="categoriaEmpresa" method="post">
        <input type="text" name="categoria" placeholder="Nome da Categoria" required>
        <input type="text" name="porte" placeholder="Porte" required>
        <input type="text" name="descricao" placeholder="Descrição" required>
        <input type="submit" value="Cadastrar">
    </form>
    <h3><a href="cadastroFuncionario.jsp">Entrar como Funcionário</a></h3>
</div>
</body>
</html>
