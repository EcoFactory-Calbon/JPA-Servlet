package com.example.servletcalbon;

import com.example.servletcalbon.dao.EmpresaDAO;
import com.example.servletcalbon.infra.ConnectionFactory;
import com.example.servletcalbon.modelEmpresa.Empresa;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet ("/senhaServlet")
public class SenhaServlet  extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cnpj = (String) request.getSession().getAttribute("cnpjEmpresa");
        String senha = request.getParameter("senha");

        EmpresaDAO dao = new EmpresaDAO(ConnectionFactory.getConnection());
        Optional<Empresa> optionalEmpresa = dao.findById(cnpj);

        if (optionalEmpresa.isPresent()) {
            Empresa empresa = optionalEmpresa.get();
            empresa.setSenha(senha);
            dao.update(empresa);
        }

        response.sendRedirect("inicio.jsp");
    }
}