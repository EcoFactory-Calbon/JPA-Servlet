package com.example.servletcalbon.dao;

import com.example.servletcalbon.infra.ConnectionFactory;
import com.example.servletcalbon.modelEmpresa.Setor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SetorDAO {

    public SetorDAO() {
    }

    // SALVAR (verifica se já existe pelo nome)
    public Setor save(Setor setor) {
        Optional<Setor> existente = findByNome(setor.getNome());
        if (existente.isPresent()) {
            return existente.get();
        }

        String sql = "INSERT INTO setor (nome, id_empresa) VALUES (?, ?)";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, setor.getNome());
                ps.setInt(2, setor.getIdEmpresa());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        setor.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar Setor: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }

        return setor;
    }

    // BUSCAR POR NOME
    public Optional<Setor> findByNome(String nome) {
        String sql = "SELECT * FROM setor WHERE nome = ?";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, nome);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Setor setor = new Setor(
                                rs.getInt("id"),
                                rs.getString("nome"),
                                rs.getInt("id_empresa")
                        );
                        return Optional.of(setor);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar Setor por nome: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }

        return Optional.empty();
    }

    // ATUALIZAR
    public Setor update(Setor setor) {
        String sql = "UPDATE setor SET nome = ?, id_empresa = ? WHERE id = ?";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, setor.getNome());
                ps.setInt(2, setor.getIdEmpresa());
                ps.setInt(3, setor.getId());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar Setor: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }

        return setor;
    }

    // DELETAR
    public void delete(int id) {
        String sql = "DELETE FROM setor WHERE id = ?";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar Setor: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }
    }

    // LISTAR TODOS
    public List<Setor> findAll() {
        List<Setor> setores = new ArrayList<>();
        String sql = "SELECT * FROM setor ORDER BY nome";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Setor setor = new Setor(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getInt("id_empresa")
                    );
                    setores.add(setor);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar Setores: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }

        return setores;
    }

    // BUSCAR POR ID
    public Optional<Setor> findById(int id) {
        String sql = "SELECT * FROM setor WHERE id = ?";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Setor setor = new Setor(
                                rs.getInt("id"),
                                rs.getString("nome"),
                                rs.getInt("id_empresa")
                        );
                        return Optional.of(setor);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar Setor por ID: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }

        return Optional.empty();
    }
}
