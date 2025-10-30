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

        // PARÂMETROS DO FORMULÁRIO
        String nome = request.getParameter("nome_empresa");
        String cnpj = request.getParameter("cnpj");
        String categoria = request.getParameter("cat_empresa");
        String descricao = request.getParameter("descricao");
        String porte = request.getParameter("porteEmpresa");
        String estado = request.getParameter("estado");
        String cidade = request.getParameter("cidade");

        // CRIA OBJETOS
        CategoriaEmpresa categoriaEmpresa = new CategoriaEmpresa(null, categoria, descricao);
        Localizacao localizacao = new Localizacao(null, estado, cidade);
        Porte porteObj = new Porte(porte);
        Empresa empresa = new Empresa(nome, cnpj, null); // senha será definida depois

        // CONEXÃO E DAOs
        Connection connection = ConnectionFactory.getConnection();
        CategoriaEmpresaDAO categoriaDAO = new CategoriaEmpresaDAO(connection);
        LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO(connection);
        PorteDAO porteDAO = new PorteDAO(connection);
        EmpresaDAO empresaDAO = new EmpresaDAO(connection);

        // SALVA CATEGORIA, LOCALIZAÇÃO E PORTE
        categoriaEmpresa = categoriaDAO.save(categoriaEmpresa);
        localizacao = localizacaoDAO.save(localizacao);
        porteObj = porteDAO.save(porteObj);

        // ATRIBUI OS IDs À EMPRESA
        empresa.setIdCategoria(Math.toIntExact(categoriaEmpresa.getId()));
        empresa.setIdLocalizacao(Math.toIntExact(localizacao.getId()));
        empresa.setIdPorte(porteObj.getId());

        // SALVA EMPRESA (sem senha por enquanto)
        empresaDAO.save(empresa);

        // GUARDA O CNPJ NA SESSÃO PRA USAR DEPOIS NO SenhaServlet
        request.getSession().setAttribute("cnpjEmpresa", cnpj);

        // REDIRECIONA PARA A PÁGINA DE CRIAR SENHA
        RequestDispatcher dispatcher = request.getRequestDispatcher("inicio.jsp");
        dispatcher.forward(request, response);
    }
}
