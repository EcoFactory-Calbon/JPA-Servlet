package com.example.servletcalbon;

import com.example.servletcalbon.dao.CategoriaEmpresaDAO;
import com.example.servletcalbon.dao.EmpresaDAO;
import com.example.servletcalbon.dao.LocalizacaoDAO;
import com.example.servletcalbon.dao.PorteDAO;
import com.example.servletcalbon.infra.ConnectionFactory;
import com.example.servletcalbon.modelEmpresa.CategoriaEmpresa;
import com.example.servletcalbon.modelEmpresa.Empresa;
import com.example.servletcalbon.modelEmpresa.Porte;
import com.example.servletcalbon.modelFuncionario.Localizacao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/empresaServlet")
public class EmpresaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Parâmetros do formulário
        String nome = request.getParameter("nome");
        String cnpj = request.getParameter("cnpj");
        String senha = request.getParameter("senha");

        String categoria = request.getParameter("categoria");
        String descricao = request.getParameter("descricao");
        String porte = request.getParameter("porte");
        String estado = request.getParameter("estado");
        String cidade = request.getParameter("cidade");

        // Cria os objetos
        CategoriaEmpresa categoriaEmpresa = new CategoriaEmpresa(null, categoria, descricao);
        Localizacao localizacao = new Localizacao(null, estado, cidade);
        Porte porteObj = new Porte(porte);
        Empresa empresa = new Empresa(nome, cnpj, senha);

        // Conexão única compartilhada entre DAOs
        Connection connection = ConnectionFactory.getConnection();

        CategoriaEmpresaDAO categoriaDAO = new CategoriaEmpresaDAO(connection);
        LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO(connection);
        PorteDAO porteDAO = new PorteDAO(connection);
        EmpresaDAO empresaDAO = new EmpresaDAO(connection);

        // Salva em ordem
        categoriaEmpresa = categoriaDAO.save(categoriaEmpresa);
        localizacao = localizacaoDAO.save(localizacao);
        porteObj = porteDAO.save(porteObj);

        // Atribui os IDs corretos à empresa
        empresa.setIdCategoria(Math.toIntExact(categoriaEmpresa.getId()));
        empresa.setIdLocalizacao(Math.toIntExact(localizacao.getId()));
        empresa.setIdPorte(porteObj.getId());

        // Salva empresa
        empresaDAO.save(empresa);

        //comentario teste

        // Redireciona
        RequestDispatcher dispatcher = request.getRequestDispatcher("/empresa.jsp");
        dispatcher.forward(request, response);
    }
}
