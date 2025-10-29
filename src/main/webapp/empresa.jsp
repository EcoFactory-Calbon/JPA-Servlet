<%@ page import="com.example.servletcalbon.dao.EmpresaDAO" %>
<%@ page import="com.example.servletcalbon.dao.LocalizacaoDAO" %>
<%@ page import="com.example.servletcalbon.dao.CategoriaEmpresaDAO" %>
<%@ page import="com.example.servletcalbon.modelEmpresa.Empresa" %>
<%@ page import="com.example.servletcalbon.modelFuncionario.Localizacao" %>
<%@ page import="com.example.servletcalbon.modelEmpresa.CategoriaEmpresa" %>
<%@ page import="com.example.servletcalbon.infra.ConnectionFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Connection" %>
<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }
        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }
        tr:nth-child(even) {
            background-color: #dddddd;
        }
    </style>
</head>
<body>
<table>
    <tr>
        <th>ID</th>
        <th>CNPJ</th>
        <th>NOME</th>
        <th>CIDADE</th>
        <th>ESTADO</th>
        <th>CATEGORIA</th>
        <th>SENHA</th>
        <th>ID_PORTE</th>
    </tr>
    <%
        Connection connection = ConnectionFactory.getConnection();
        EmpresaDAO empresaDAO = new EmpresaDAO(connection);
        LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO(connection);
        CategoriaEmpresaDAO categoriaDAO = new CategoriaEmpresaDAO(connection);

        List<Empresa> empresas = empresaDAO.findAll();

        for (Empresa empresa : empresas) {
            Localizacao loc = localizacaoDAO.findById((long)empresa.getIdLocalizacao()).orElse(null);
            CategoriaEmpresa cat = categoriaDAO.findById((long)empresa.getIdCategoria()).orElse(null);
    %>
    <tr>
        <td><%= empresa.getId() %></td>
        <td><%= empresa.getCnpj() %></td>
        <td><%= empresa.getNome() %></td>
        <td><%= loc != null ? loc.getCidade() : "N/D" %></td>
        <td><%= loc != null ? loc.getEstado() : "N/D" %></td>
        <td><%= cat != null ? cat.getNome() : "N/D" %></td>
        <td><%= empresa.getSenha() %></td>
        <td><%= empresa.getIdPorte() %></td>
    </tr>
    <%
        }
        ConnectionFactory.fechar(connection);
    %>
</table>
</body>
</html>
