package com.example.servletcalbon.dao;

import com.example.servletcalbon.infra.ConnectionFactory;
import com.example.servletcalbon.modelEmpresa.Porte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PorteDAO {

    public PorteDAO(Connection connection) {
    }

    public PorteDAO() {
    }

//    VERIFICA SE JA EXISTE ANTES DE INSERIR
    public Porte save(Porte porte) {
        //verifica se ja existe um porte com esse nome
        Optional<Porte> existente = findByNome(porte.getNome());
        if (existente.isPresent()) {
            //retorna o porte existente
            return existente.get();
        }
        
//        CRIA UM NOVO SE NÃO EXISTE
        String sql = "INSERT INTO porte (nome) VALUES (?)";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, porte.getNome());
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    porte.setId(rs.getInt(1));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar Porte: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }

        return porte;
    }

//    BUSCAR NOME USADO PELO USUARIO
    public Optional<Porte> findByNome(String nome) {
        String sql = "SELECT * FROM porte WHERE nome = ?";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, nome);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Porte porte = new Porte(
                            rs.getInt("id"),
                            rs.getString("nome")
                    );
                    return Optional.of(porte);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar Porte por nome: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }

        return Optional.empty();
    }

//    ATUALIZAR
    public Porte update(Porte porte) {
        String sql = "UPDATE porte SET nome = ? WHERE id = ?";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, porte.getNome());
                ps.setInt(2, porte.getId());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar Porte: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }

        return porte;
    }

//    DELETAR
    public void delete(int id) {
        String sql = "DELETE FROM porte WHERE id = ?";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar Porte: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }
    }

//    LISTAR TODOS
    public List<Porte> findAll() {
        List<Porte> portes = new ArrayList<>();
        String sql = "SELECT * FROM porte";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Porte porte = new Porte(
                            rs.getInt("id"),
                            rs.getString("nome")
                    );
                    portes.add(porte);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar Portes: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }

        return portes;
    }

//    BUSCAR ID
    public Optional<Porte> findById(int id) {
        Porte porte = null;
        String sql = "SELECT * FROM porte WHERE id = ?";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    porte = new Porte(
                            rs.getInt("id"),
                            rs.getString("nome")
                    );
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar Porte por ID: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }

        return Optional.ofNullable(porte);
    }
}
