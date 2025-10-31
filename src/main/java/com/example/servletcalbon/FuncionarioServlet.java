package com.example.servletcalbon;

import com.example.servletcalbon.dao.CargoDAO;
import com.example.servletcalbon.dao.FuncionarioDAO;
import com.example.servletcalbon.dao.LocalizacaoDAO;
import com.example.servletcalbon.infra.ConnectionFactory;
import com.example.servletcalbon.modelFuncionario.Funcionario;
import com.example.servletcalbon.modelFuncionario.Localizacao;
import com.example.servletcalbon.modelFuncionario.Cargo;
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
        String senha = request.getParameter("senha");
        String cargoNome = request.getParameter("cargo");
        String estado = request.getParameter("estado");
        String cidade = request.getParameter("cidade");

        // CRIA OBJETOS
        Cargo cargo = new Cargo(cargoNome, IdSetor);
        cargo.setNome(cargoNome);
        cargo.setIdSetor(1); // ajustar conforme seu banco

        Localizacao localizacao = new Localizacao(null, estado, cidade);

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setSobrenome(sobrenome);
        funcionario.setEmail(email);
        funcionario.setSenha(senha);

        // CONEXÃO E DAOs
        Connection connection = ConnectionFactory.getConnection();
        CargoDAO cargoDAO = new CargoDAO();
        LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        // SALVA CARGO E LOCALIZAÇÃO
        cargo = cargoDAO.save(cargo);
        localizacao = localizacaoDAO.save(localizacao);

        // ATRIBUI OS IDs AO FUNCIONÁRIO
        funcionario.setIdCargo(cargo.getId());
        funcionario.setIdLocalizacao(localizacao.getId());

        // SALVA FUNCIONÁRIO
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
