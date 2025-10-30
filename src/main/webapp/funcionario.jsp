<%--
  Created by IntelliJ IDEA.
  User: murilomarques-ieg
  Date: 28/10/2025
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP - Funcionario</title>
    <h1>Cadastro Funcionario</h1>
</head>
<body>
<br/>
<form method="post" action="empresaServlet">
    <input type="text" name="cnpj" placeholder="CNPJ">
    <br>
    <input type="text" name="nome" placeholder="Nome">
    <br>
    <input type="password" name="senha" placeholder="Senha">
    <br>
    <input type="text" name="categoria" placeholder="Categoria">
    <br>
    <input type="text" name="descricao" placeholder="Descrição">
    <br>
    <input type="text" name="estado" placeholder="Estado">
    <br>
    <input type="text" name="cidade" placeholder="Cidade">
    <br>
    <input type="text" name="porte" placeholder="Porte">
    <br>
    <input type="submit" value="Cadastrar">
</form>
</body>
</html>
