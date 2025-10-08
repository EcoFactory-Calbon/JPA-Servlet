import calbon.dao.FuncionarioDAO;
import calbon.modelFuncionario.Funcionario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/funcionario")
public class funcionarioServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String n_cracha = request.getParameter("numero_cracha");
        String nome = request.getParameter("nome");
        String sobrenome = request.getParameter("sobrenome");
        String email = request.getParameter("email");
        String senhaHash = request.getParameter("senha");
        String telefone = request.getParameter("telefone");

        LocalDate dataNascimento = LocalDate.parse(request.getParameter("dt_nascimento"));
        LocalDate dataContratacao = LocalDate.parse(request.getParameter("dt_contratacao"));


        // 🔥 Conversão correta (de String → boolean)
        boolean isGestor = Boolean.parseBoolean(request.getParameter("isGestor"));

        Funcionario funcionario = new Funcionario(
                n_cracha,
                nome,
                sobrenome,
                email,
                senhaHash,
                dataNascimento,
                dataContratacao,
                isGestor,
                telefone
        );

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        funcionarioDAO.save(funcionario);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/categoria.jsp");
        dispatcher.forward(request, response);
    }
}
