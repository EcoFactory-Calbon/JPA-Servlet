package com.example.servletcalbon.dao;

import com.example.servletcalbon.modelFuncionario.Cargo;
import com.example.servletcalbon.infra.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CargoDAO {

    private final Connection connection;

    public CargoDAO(Connection connection) {
        this.connection = connection;
    }

    // SALVAR CARGO
    public Cargo save(Cargo cargo) {
        String sql = "INSERT INTO cargo (nome, id_setor) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cargo.getNome());
            stmt.setInt(2, cargo.getIdSetor());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cargo.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar Cargo: " + e.getMessage(), e);
        }
        return cargo;
    }

    // ATUALIZAR CARGO
    public Cargo update(Cargo cargo) {
        String sql = "UPDATE cargo SET nome = ?, id_setor = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cargo.getNome());
            stmt.setInt(2, cargo.getIdSetor());
            stmt.setLong(3, cargo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar Cargo: " + e.getMessage(), e);
        }
        return cargo;
    }

    // DELETAR CARGO
    public void delete(Long id) {
        String sql = "DELETE FROM cargo WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir Cargo: " + e.getMessage(), e);
        }
    }

    // LISTAR TODOS OS CARGOS
    public List<Cargo> findAll() {
        List<Cargo> cargos = new ArrayList<>();
        String sql = "SELECT * FROM cargo";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cargo cargo = new Cargo(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getInt("id_setor")
                );
                cargos.add(cargo);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar Cargos: " + e.getMessage(), e);
        }
        return cargos;
    }

    // BUSCAR CARGO POR ID
    public Optional<Cargo> findById(Long id) {
        String sql = "SELECT * FROM cargo WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Cargo cargo = new Cargo(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getInt("id_setor")
                );
                return Optional.of(cargo);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar Cargo: " + e.getMessage(), e);
        }
        return Optional.empty();
    }
}