package com.example.servletcalbon.dao;

import com.example.servletcalbon.infra.ConnectionFactory;
import com.example.servletcalbon.modelFuncionario.Cargo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CargoDAO {

    public CargoDAO() {
    }

    // 🔹 SALVAR (verifica se já existe pelo nome)
    public Cargo save(Cargo cargo) {
        Optional<Cargo> existente = findByNome(cargo.getNome());
        if (existente.isPresent()) {
            return existente.get();
        }

        String sql = "INSERT INTO cargo (nome, id_setor) VALUES (?, ?)";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, cargo.getNome());
                ps.setInt(2, cargo.getIdSetor());
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    cargo.setId(rs.getInt(1));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar Cargo: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }

        return cargo;
    }

    // 🔹 BUSCAR POR NOME
    public Optional<Cargo> findByNome(String nome) {
        String sql = "SELECT * FROM cargo WHERE nome = ?";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, nome);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Cargo cargo = new Cargo(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getInt("id_setor")
                    );
                    return Optional.of(cargo);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar Cargo por nome: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }

        return Optional.empty();
    }

    // 🔹 ATUALIZAR
    public Cargo update(Cargo cargo) {
        String sql = "UPDATE cargo SET nome = ?, id_setor = ? WHERE id = ?";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, cargo.getNome());
                ps.setInt(2, cargo.getIdSetor());
                ps.setInt(3, cargo.getId());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar Cargo: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }

        return cargo;
    }

    // 🔹 DELETAR
    public void delete(int id) {
        String sql = "DELETE FROM cargo WHERE id = ?";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar Cargo: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }
    }

    // 🔹 LISTAR TODOS
    public List<Cargo> findAll() {
        List<Cargo> cargos = new ArrayList<>();
        String sql = "SELECT * FROM cargo";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Cargo cargo = new Cargo(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getInt("id_setor")
                    );
                    cargos.add(cargo);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar Cargos: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }

        return cargos;
    }

    // 🔹 BUSCAR POR ID
    public Optional<Cargo> findById(int id) {
        String sql = "SELECT * FROM cargo WHERE id = ?";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Cargo cargo = new Cargo(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getInt("id_setor")
                    );
                    return Optional.of(cargo);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar Cargo por ID: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }

        return Optional.empty();
    }
}
