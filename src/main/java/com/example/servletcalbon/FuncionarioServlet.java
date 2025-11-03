package com.example.servletcalbon;

import com.example.servletcalbon.dao.*;
import com.example.servletcalbon.infra.ConnectionFactory;
import com.example.servletcalbon.modelFuncionario.Funcionario;
import com.example.servletcalbon.modelFuncionario.Cargo;
import com.example.servletcalbon.modelEmpresa.Setor;
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

        // PARÂMETROS DO FORMULÁRIO
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String email = request.getParameter("email");
        String cargoNome = request.getParameter("cargo");
        String setorNome = request.getParameter("setor");
        String gestorStr = request.getParameter("gestor");
        String estado = request.getParameter("estado");
        String cidade = request.getParameter("cidade");

        // CONVERTER GESTOR PARA BOOLEAN
        boolean isGestor = "Sim".equalsIgnoreCase(gestorStr);

        // SENHA PADRÃO
        String senhaPadrao = "123456";

        // CONEXÃO E DAOs
        Connection connection = ConnectionFactory.getConnection();
        SetorDAO setorDAO = new SetorDAO(connection);
        CargoDAO cargoDAO = new CargoDAO(connection);
        LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO(connection);
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO(connection);

        // CRIAR SETOR
        Setor setor = new Setor(setorNome, 1); // id_empresa = 1
        setor = setorDAO.save(setor);

        // CRIAR CARGO
        Cargo cargo = new Cargo(cargoNome, setor.getId());
        cargo = cargoDAO.save(cargo);

        // CRIAR LOCALIZAÇÃO (extrair sigla do estado)
        String estadoSigla = estado;
        if (estado.contains(" - ")) {
            estadoSigla = estado.split(" - ")[0];
        }
        Localizacao localizacao = new Localizacao(estadoSigla, cidade);
        localizacao = localizacaoDAO.save(localizacao);

        // CRIAR FUNCIONÁRIO
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setSobrenome(sobrenome);
        funcionario.setEmail(email);
        funcionario.setSenha(senhaPadrao);
        funcionario.setGestor(isGestor);
        funcionario.setIdCargo(cargo.getId());
        funcionario.setIdLocalizacao(localizacao.getId());

        // SALVAR FUNCIONÁRIO (agora é void, igual no EmpresaDAO)
        funcionarioDAO.save(funcionario);

        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // REDIRECIONA PARA A PÁGINA INICIAL
        RequestDispatcher dispatcher = request.getRequestDispatcher("inicio.jsp");
        dispatcher.forward(request, response);
    }
}