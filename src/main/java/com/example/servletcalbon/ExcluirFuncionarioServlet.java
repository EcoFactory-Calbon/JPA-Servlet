package com.example.servletcalbon;

import com.example.servletcalbon.dao.FuncionarioDAO;
import com.example.servletcalbon.infra.ConnectionFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/excluirFuncionarioServlet")
public class ExcluirFuncionarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obter o número do crachá do parâmetro
        String numeroCrachaStr = request.getParameter("numeroCracha");

        Connection connection = null;
        try {
            int numeroCracha = Integer.parseInt(numeroCrachaStr);

            connection = ConnectionFactory.getConnection();
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO(connection);

            // Excluir o funcionário
            funcionarioDAO.delete(numeroCracha);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Redireciona de volta para a lista
        response.sendRedirect("visualizar-funcionario.jsp");
    }
}