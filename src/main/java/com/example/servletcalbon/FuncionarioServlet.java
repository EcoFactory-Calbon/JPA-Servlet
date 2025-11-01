//package com.example.servletcalbon;
//
//import com.example.servletcalbon.dao.*;
//import com.example.servletcalbon.infra.ConnectionFactory;
//import com.example.servletcalbon.modelFuncionario.Funcionario;
//import com.example.servletcalbon.modelFuncionario.Loc
//
//
// 0alizacao;
//import com.example.servletcalbon.modelFuncionario.Cargo;
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//@WebServlet("/funcionarioServlet")
//public class FuncionarioServlet extends HttpServlet {
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
////        PARAMETROS DO FORMULÁRIO
//        String numeroCracha = request.getParameter("numero_cracha");
//        String nome = request.getParameter("nome");
//        String sobrenome = request.getParameter("sobrenome");
//        String email = request.getParameter("email");
//        String senha = request.getParameter("senha");
//        boolean isGestor = request.getParameter("is_gestor") != null;
//        String nomeCargo = request.getParameter("cargo");
//        String estado = request.getParameter("estado");
//        String cidade = request.getParameter("cidade");
//
////        CRIA OBJETOS
//        Cargo cargoObj = new Cargo(nomeCargo);
//        Localizacao localizacao = new Localizacao(null, estado, cidade);
//
//        Funcionario funcionario = new Funcionario();
//        funcionario.setNumeroCracha(numeroCracha);
//        funcionario.setNome(nome);
//        funcionario.setSobrenome(sobrenome);
//        funcionario.setEmail(email);
//        funcionario.setSenha(senha);
//        funcionario.setGestor(isGestor);
//
////        CONEXÃO E DAOs
//        Connection connection = ConnectionFactory.getConnection();
//        CargoDAO cargoDAO = new CargoDAO(connection);
//        LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO(connection);
//        FuncionarioDAO funcionarioDAO = new FuncionarioDAO(connection);
//        EmpresaDAO empresaDAO = new EmpresaDAO(connection); // opcional
//
////        SALVA LOCALIZAÇÃO E CARGO
//        cargoObj = cargoDAO.save(cargoObj);
//        localizacao = localizacaoDAO.save(localizacao);
//
////        ATRIBUI OS IDs AO FUNCIONÁRIO
//        funcionario.setIdCargo(cargoObj.getId());
//        funcionario.setIdLocalizacao(localizacao.getId());
//
////        SALVA FUNCIONÁRIO
//        funcionarioDAO.save(funcionario);
//
////        FECHA CONEXÃO
//        try {
//            connection.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
////        REDIRECIONA PARA A PÁGINA DE SUCESSO
//        RequestDispatcher dispatcher = request.getRequestDispatcher("inicio.jsp");
//        dispatcher.forward(request, response);
//    }
//}
