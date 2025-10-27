<%@ page import="java.sql.Connection" %>
<%@ page import="com.example.servletcalbon.infra.ConnectionFactory" %>
<%@ page import="com.example.servletcalbon.dao.EmpresaDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.servletcalbon.modelEmpresa.Empresa" %>
<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <style>
        table{
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th{
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even){
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
            <th>ID_LOCALIZACAO</th>
            <th>ID_CATEGORIA</th>
            <th>SENHA</th>
            <th>ID_PORTE</th>
        </tr>

        <%
            Connection connection = ConnectionFactory.getConnection();
            EmpresaDAO dao = new EmpresaDAO(connection);
            List<Empresa> empresas = dao.findAll();
            for (Empresa empresa:empresas){
        %>
        <tr>
            <td><%= empresa.getId()%></td>
            <td><%= empresa.getCnpj()%></td>
            <td><%= empresa.getNome()%></td>
            <td><%= empresa.getIdLocalizacao()%></td>
            <td><%= empresa.getIdCategoria()%></td>
            <td><%= empresa.getSenha()%></td>
            <td><%= empresa.getIdPorte()%></td>
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>