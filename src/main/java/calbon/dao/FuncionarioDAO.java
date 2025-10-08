package calbon.dao;

import calbon.modelFuncionario.IFuncionarioDAO;
import calbon.modelFuncionario.Funcionario;
import calbon.infra.ConnectionFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FuncionarioDAO implements IFuncionarioDAO {

    @Override
    public Funcionario save(Funcionario funcionario) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "INSERT INTO funcionario (numero_cracha, nome, sobrenome, email, senha_hash, data_nascimento, data_contratacao, is_gestor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, Integer.parseInt(funcionario.getNumeroCracha()));
            preparedStatement.setString(2, funcionario.getNome());
            preparedStatement.setString(3, funcionario.getSobrenome());
            preparedStatement.setString(4, funcionario.getEmail());
            preparedStatement.setString(5, funcionario.getSenhaHash());
            preparedStatement.setDate(6, Date.valueOf(funcionario.getDataNascimento()));
            preparedStatement.setDate(7, Date.valueOf(funcionario.getDataContratacao()));
            preparedStatement.setBoolean(8, funcionario.isGestor());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                funcionario.setId(resultSet.getLong(1));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return funcionario;
    }

    @Override
    public Funcionario update(Funcionario funcionario) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "UPDATE funcionario SET numero_cracha = ?, nome = ?, sobrenome = ?, email = ?, senha_hash = ?, data_nascimento = ?, data_contratacao = ?, is_gestor = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(funcionario.getNumeroCracha()));
            preparedStatement.setString(2, funcionario.getNome());
            preparedStatement.setString(3, funcionario.getSobrenome());
            preparedStatement.setString(4, funcionario.getEmail());
            preparedStatement.setString(5, funcionario.getSenhaHash());
            preparedStatement.setDate(6, Date.valueOf(funcionario.getDataNascimento()));
            preparedStatement.setDate(7, Date.valueOf(funcionario.getDataContratacao()));
            preparedStatement.setBoolean(8, funcionario.isGestor());
            preparedStatement.setLong(9, funcionario.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return funcionario;
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "DELETE FROM funcionario WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Funcionario> findAll() {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionario";

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String numeroCracha = String.valueOf(resultSet.getInt("numero_cracha"));
                String nome = resultSet.getString("nome");
                String sobrenome = resultSet.getString("sobrenome");
                String email = resultSet.getString("email");
                String senhaHash = resultSet.getString("senha_hash");
                LocalDate dataNascimento = resultSet.getDate("data_nascimento").toLocalDate();
                LocalDate dataContratacao = resultSet.getDate("data_contratacao").toLocalDate();
                boolean isGestor = resultSet.getBoolean("is_gestor");

                Funcionario funcionario = new Funcionario(id, numeroCracha, nome, sobrenome, email, senhaHash, dataNascimento, dataContratacao, isGestor, null);
                funcionarios.add(funcionario);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return funcionarios;
    }

    @Override
    public Optional<Funcionario> findById(Long id) {
        Funcionario funcionario = null;
        String sql = "SELECT * FROM funcionario WHERE id = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String numeroCracha = String.valueOf(resultSet.getInt("numero_cracha"));
                String nome = resultSet.getString("nome");
                String sobrenome = resultSet.getString("sobrenome");
                String email = resultSet.getString("email");
                String senhaHash = resultSet.getString("senha_hash");
                LocalDate dataNascimento = resultSet.getDate("data_nascimento").toLocalDate();
                LocalDate dataContratacao = resultSet.getDate("data_contratacao").toLocalDate();
                boolean isGestor = resultSet.getBoolean("is_gestor");

                funcionario = new Funcionario(id, numeroCracha, nome, sobrenome, email, senhaHash, dataNascimento, dataContratacao, isGestor, null);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return Optional.ofNullable(funcionario);
    }
}
