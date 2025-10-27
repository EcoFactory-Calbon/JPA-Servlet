<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<h1>Cadastro/Login</h1>
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