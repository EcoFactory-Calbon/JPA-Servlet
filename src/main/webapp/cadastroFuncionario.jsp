<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
      <h1>Cadastro Funcionário</h1>
      <form action="funcionario" method="post">
        <input type="text" name="numero_cracha" placeholder="Número do Crachá">
        <input type="text" name="nome" placeholder="Nome">
        <input type="text" name="sobrenome" placeholder="Sobrenome">
        <input type="email" name="email" placeholder="Email">
        <input type="password" name="senha" placeholder="Senha">
          <label>Data de Nascimento: <input type="date" name="dt_nascimento" placeholder="Data de Nascimento"></label>
        <label>Data de Contratação: <input type="date" name="dt_contratacao" placeholder="Data de Contratação"></label>
          <label>Gestor
              <select name="isGestor" required>
              <option value="false">Não</option>
              <option value="true">Sim</option>
              </select>
          <input type="number" name="telefone" placeholder="telefone">
          </label>
          <input type="submit" value="Cadastrar">
      </form>
</body>
</html>
