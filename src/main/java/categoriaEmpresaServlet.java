import calbon.dao.CategoriaEmpresaDAO;
import calbon.infra.ConnectionFactory;
import calbon.modelEmpresa.CategoriaEmpresa;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet("/categoriaEmpresa")
public class categoriaEmpresaServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = null;
        String nome = request.getParameter("categoria");
        String descricao = request.getParameter("descricao");
        String porte = request.getParameter("porte");

        CategoriaEmpresa categoriaEmpresa = new CategoriaEmpresa(null, nome, descricao, porte);
        CategoriaEmpresaDAO categoriaEmpresaDAO = new CategoriaEmpresaDAO();
        categoriaEmpresaDAO.save(categoriaEmpresa);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/categoria.jsp");
        dispatcher.forward(request, response);
    }
}
