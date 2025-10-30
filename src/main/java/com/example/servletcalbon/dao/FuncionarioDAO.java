package com.example.servletcalbon.dao;

import com.example.servletcalbon.modelFuncionario.Funcionario;
import com.example.servletcalbon.modelFuncionario.IFuncionarioDAO;
import com.example.servletcalbon.infra.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FuncionarioDAO implements IFuncionarioDAO {

//    CONSTRUTOR SEM CONEXAO EXTERNA
    public FuncionarioDAO(Connection connection) {
    }

//    CONSTRUTOR PADRAO
    public FuncionarioDAO() {
    }


//    METODO SPARA INSERIR UM FUNCIONARIO
    @Override
    public Funcionario save(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario (numero_cracha, nome, sobrenome, email, senha, is_gestor, id_cargo, id_localizacao) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

//                Define os valores dos parametros
                ps.setString(1, funcionario.getNumeroCracha());
                ps.setString(2, funcionario.getNome());
                ps.setString(3, funcionario.getSobrenome());
                ps.setString(4, funcionario.getEmail());
                ps.setString(5, funcionario.getSenha());
                ps.setBoolean(6, funcionario.isGestor());
                ps.setLong(7, funcionario.getIdCargo());
                ps.setLong(8, funcionario.getIdLocalizacao());

//                Executa o comando SQL
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar funcionário: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }
        return funcionario;
    }



    @Override
    public Funcionario update(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET nome = ?, sobrenome = ?, email = ?, senha = ?, " +
                "is_gestor = ?, id_cargo = ?, id_localizacao = ? WHERE numero_cracha = ?";
        Connection connection = null;
        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, funcionario.getNome());
                ps.setString(2, funcionario.getSobrenome());
                ps.setString(3, funcionario.getEmail());
                ps.setString(4, funcionario.getSenha());
                ps.setBoolean(5, funcionario.isGestor());
                ps.setLong(6, funcionario.getIdCargo());
                ps.setLong(7, funcionario.getIdLocalizacao());
                ps.setString(8, funcionario.getNumeroCracha());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar funcionário: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }
        return funcionario;
    }

    @Override
    public void delete(Long numeroCracha) {
        String sql = "DELETE FROM funcionario WHERE numero_cracha = ?";
        Connection connection = null;
        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setLong(1, numeroCracha);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao excluir funcionário: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }
    }



    @Override
    public List<Funcionario> findAll() {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionario";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Funcionario f = new Funcionario(
                            rs.getString("numero_cracha"),
                            rs.getString("nome"),
                            rs.getString("sobrenome"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getBoolean("is_gestor"),
                            rs.getLong("id_cargo"),
                            rs.getLong("id_localizacao")
                    );
                    funcionarios.add(f);
                }

            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar funcionários: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }

        return funcionarios;
    }




    @Override
    public Optional<Funcionario> findById(Long numeroCracha) {
        Funcionario funcionario = null;
        String sql = "SELECT * FROM funcionario WHERE numero_cracha = ?";
        Connection connection = null;
        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setLong(1, numeroCracha);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    funcionario  = new Funcionario(
                    rs.getString("numero_cracha"),
                    rs.getString("nome"),
                    rs.getString("sobrenome"),
                    rs.getString("email"),
                    rs.getString("senha"),
                    rs.getBoolean("is_gestor"),
                    rs.getLong("id_cargo"),
                    rs.getLong("id_localizacao")
                    );
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar funcionário: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }
        return Optional.ofNullable(funcionario);
    }
}
