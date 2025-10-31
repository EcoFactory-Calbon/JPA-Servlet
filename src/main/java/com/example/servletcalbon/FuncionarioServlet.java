package com.example.servletcalbon;

import com.example.servletcalbon.dao.CargoDAO;
import com.example.servletcalbon.dao.FuncionarioDAO;
import com.example.servletcalbon.dao.LocalizacaoDAO;
import com.example.servletcalbon.dao.SetorDAO;
import com.example.servletcalbon.infra.ConnectionFactory;
import com.example.servletcalbon.modelEmpresa.Setor;
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
        String email = request.getParameter("email");
        String empresa = request.getParameter("empresa");
        String cargoNome = request.getParameter("cargo");
        String telefone = request.getParameter("telefone");
        String estado = request.getParameter("estado");
        String cidade = request.getParameter("cidade");

        // CRIA OBJETOS
     Funcionario funcionario = new Funcionario(null,nome, email,empresa,cargoNome, telefone, null, null);
     Localizacao localizacao = new Localizacao(null, estado, cidade);
     Setor setor = new Setor(nome, null);
     Cargo cargo = new Cargo(null, nome, null);



//     CONEXAO E DAOs
        Connection connection = ConnectionFactory.getConnection();
        LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO(connection);
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO(connection);
        SetorDAO setorDAO = new SetorDAO(connection);
        CargoDAO cargoDAO = new CargoDAO(connection);

//        SALVA SETOR, LOCALIZAÇÃO E CARGO
        setor = setorDAO.save(setor);
        localizacao = localizacaoDAO.save(localizacao);
        cargo = cargoDAO.save(cargo);



//        ATRIBUI OS IDs À FUNCIONARIOS
        funcionario.setIdCargo((long) Math.toIntExact(cargo.getId()));
        funcionario.setIdLocalizacao((long) Math.toIntExact(localizacao.getId()));


//        SALVA FUNCIONARIO
        funcionarioDAO.save(funcionario);

        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // REDIRECIONA PARA A PÁGINA DE CRIAR SENHA
        RequestDispatcher dispatcher = request.getRequestDispatcher("inicio.jsp");
        dispatcher.forward(request, response);



    }
}
