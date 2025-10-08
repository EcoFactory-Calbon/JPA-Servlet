<%@ page import="java.sql.Connection, calbon.infra.ConnectionFactory" %>
<%@ page import="calbon.dao.CategoriaEmpresaDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="calbon.modelEmpresa.CategoriaEmpresa" %>
<html>
<head>
    <title>Title</title>
    <style>
        table{
            font-family: Arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th{
            border: 1px solid #dddddd;
            text-align: left;
            padding: 0px;
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
            <th>Nome da Categoria</th>
            <th>Porte</th>
            <th>Descricao</th>
        </tr>
    <%
        Connection connection = ConnectionFactory.getConnection();
        CategoriaEmpresaDAO categoriaEmpresaDAO = new CategoriaEmpresaDAO();
        List<CategoriaEmpresa> categoria = categoriaEmpresaDAO.findAll();

        for (CategoriaEmpresa cat : categoria){
    %>
    <tr>
        <td><%= cat.getId() %></td>
        <td><%= cat.getNome() %></td>
        <td><%= cat.getPorte() %></td>
        <td><%= cat.getDescricao() %></td>
    </tr>
    <%
        }
    %>
    </table>
</body>
</html>
