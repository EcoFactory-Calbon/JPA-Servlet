package com.example.servletcalbon;

import com.example.servletcalbon.dao.*;
import com.example.servletcalbon.infra.ConnectionFactory;
import com.example.servletcalbon.modelFuncionario.Funcionario;
import com.example.servletcalbon.modelFuncionario.Localizacao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/funcionarioServlet")
public class FuncionarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        PARAMETROS DO FORMULARIO
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String campo = request.getParameter("campo");
        String empresa = request.getParameter("empresa");
        String cargo = request.getParameter("cargo");
        String telefone = request.getParameter("telefone");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");

//        CRIA OBJETOS
        Funcionario funcionario = new Funcionario(null, nome, email, campo, empresa, telefone, cidade, estado);
        Localizacao localizacao = new Localizacao(null, estado, cidade);
        Cargo cargo = new Cargo(cargo);

//        CONEXAO DAOs
        Connection connection = ConnectionFactory.getConnection();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO(connection);
        LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO(connection);
        CargoDAO cargoDAO = new CargoDAO(connection);
        EmpresaDAO empresaDAO = new EmpresaDAO(connection);

        // SALVA FUNCONARIO, LOCALIZAÇÃO E CARGO
        funcionario = funcionario.save(funcionario);
        localizacao = localizacaoDAO.save(localizacao);
        cargo = cargo.save(cargo);

    }
}
