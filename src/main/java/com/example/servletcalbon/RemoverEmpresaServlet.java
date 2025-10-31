package com.example.servletcalbon;

import com.example.servletcalbon.dao.EmpresaDAO;
import com.example.servletcalbon.infra.ConnectionFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet ("/removerEmpresaServlet")
public class RemoverEmpresaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        Connection connection = ConnectionFactory.getConnection();
        EmpresaDAO dao = new EmpresaDAO(connection);
        dao.delete(Math.toIntExact(Long.valueOf(id)));

        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("visualizar-empresa.jsp");
        dispatcher.forward(request, response);
    }
}
