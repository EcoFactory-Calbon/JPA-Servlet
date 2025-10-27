package com.example.servletcalbon;

import com.example.servletcalbon.dao.EmpresaDAO;
import com.example.servletcalbon.infra.ConnectionFactory;
import com.example.servletcalbon.modelEmpresa.Empresa;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/ListaServlet")
public class ListaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = ConnectionFactory.getConnection();
        EmpresaDAO dao =  new EmpresaDAO(connection);
        List<Empresa> empresas = dao.findAll();

        request.setAttribute("empresa", empresas);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/empresa.jsp");
        dispatcher.forward(request, response);
    }

}
