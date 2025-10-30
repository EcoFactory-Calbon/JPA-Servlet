package com.example.servletcalbon.dao;

import com.example.servletcalbon.modelFuncionario.Funcionario;
import com.example.servletcalbon.modelFuncionario.IFuncionarioDAO;
import com.example.servletcalbon.infra.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FuncionarioDAO implements IFuncionarioDAO {

//    INSERIR UM NOVO FUNCIONARIO

    @Override
    public Funcionario save(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario (numero_cracha, nome, sobrenome, email, senha, is_gestor, id_cargo, id_localizacao) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNumeroCracha());
            stmt.setString(2, funcionario.getNome());
            stmt.setString(3, funcionario.getSobrenome());
            stmt.setString(4, funcionario.getEmail());
            stmt.setString(5, funcionario.getSenha());
            stmt.setBoolean(6, funcionario.isGestor());
            stmt.setLong(7, funcionario.getIdCargo());
            stmt.setLong(8, funcionario.getIdLocalizacao());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar funcionário: " + e.getMessage(), e);
        }

        return funcionario;
    }


//    ATUALIZAR UM FUNCIONARIO NOVO
    @Override
    public Funcionario update(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET nome = ?, sobrenome = ?, email = ?, senha = ?, " +
                "is_gestor = ?, id_cargo = ?, id_localizacao = ? WHERE numero_cracha = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getSobrenome());
            stmt.setString(3, funcionario.getEmail());
            stmt.setString(4, funcionario.getSenha());
            stmt.setBoolean(5, funcionario.isGestor());
            stmt.setLong(6, funcionario.getIdCargo());
            stmt.setLong(7, funcionario.getIdLocalizacao());
            stmt.setString(8, funcionario.getNumeroCracha());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar funcionário: " + e.getMessage(), e);
        }

        return funcionario;
    }


//    EXCLUIR FUNCIONARIO
    @Override
    public void delete(Long numeroCracha) {
        String sql = "DELETE FROM funcionario WHERE numero_cracha = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, numeroCracha);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir funcionário: " + e.getMessage(), e);
        }
    }


//    LISTAR TODOS OS FUNCIONARIOS
    @Override
    public List<Funcionario> findAll() {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionario";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setNumeroCracha(rs.getString("numero_cracha"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setSobrenome(rs.getString("sobrenome"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setSenha(rs.getString("senha"));
                funcionario.setGestor(rs.getBoolean("is_gestor"));
                funcionario.setIdCargo(rs.getLong("id_cargo"));
                funcionario.setIdLocalizacao(rs.getLong("id_localizacao"));

                funcionarios.add(funcionario);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar funcionários: " + e.getMessage(), e);
        }

        return funcionarios;
    }



//    BUSCAR FUNCIONARIO PELO ID
    @Override
    public Optional<Funcionario> findById(Long numeroCracha) {
        Funcionario funcionario = null;
        String sql = "SELECT * FROM funcionario WHERE numero_cracha = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, numeroCracha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    funcionario = new Funcionario();
                    funcionario.setNumeroCracha(rs.getString("numero_cracha"));
                    funcionario.setNome(rs.getString("nome"));
                    funcionario.setSobrenome(rs.getString("sobrenome"));
                    funcionario.setEmail(rs.getString("email"));
                    funcionario.setSenha(rs.getString("senha"));
                    funcionario.setGestor(rs.getBoolean("is_gestor"));
                    funcionario.setIdCargo(rs.getLong("id_cargo"));
                    funcionario.setIdLocalizacao(rs.getLong("id_localizacao"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário: " + e.getMessage(), e);
        }

        return Optional.ofNullable(funcionario);
    }
}
