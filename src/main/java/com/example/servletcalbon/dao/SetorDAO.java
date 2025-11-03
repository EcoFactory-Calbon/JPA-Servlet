package com.example.servletcalbon.dao;

import com.example.servletcalbon.modelEmpresa.Setor;
import com.example.servletcalbon.infra.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SetorDAO {

    private final Connection connection;

    public SetorDAO(Connection connection) {
        this.connection = connection;
    }

    // SALVAR SETOR
    public Setor save(Setor setor) {
        String sql = "INSERT INTO setor (nome, id_empresa) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, setor.getNome());
            stmt.setInt(2, setor.getIdEmpresa());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                setor.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar Setor: " + e.getMessage(), e);
        }
        return setor;
    }

    // ATUALIZAR SETOR
    public Setor update(Setor setor) {
        String sql = "UPDATE setor SET nome = ?, id_empresa = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, setor.getNome());
            stmt.setInt(2, setor.getIdEmpresa());
            stmt.setInt(3, setor.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar Setor: " + e.getMessage(), e);
        }
        return setor;
    }

    // DELETAR SETOR
    public void delete(int id) {
        String sql = "DELETE FROM setor WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir Setor: " + e.getMessage(), e);
        }
    }

    // LISTAR TODOS OS SETORES
    public List<Setor> findAll() {
        List<Setor> setores = new ArrayList<>();
        String sql = "SELECT * FROM setor";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Setor s = new Setor(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("id_empresa")
                );
                setores.add(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar Setores: " + e.getMessage(), e);
        }
        return setores;
    }

    // BUSCAR SETOR POR ID
    public Optional<Setor> findById(int id) {
        String sql = "SELECT * FROM setor WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Setor s = new Setor(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("id_empresa")
                );
                return Optional.of(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar Setor: " + e.getMessage(), e);
        }
        return Optional.empty();
    }
}