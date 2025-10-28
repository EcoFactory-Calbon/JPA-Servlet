package com.example.servletcalbon.dao;

import com.example.servletcalbon.modelEmpresa.CategoriaEmpresa;
import com.example.servletcalbon.modelEmpresa.ICategoriaEmpresa;
import com.example.servletcalbon.infra.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriaEmpresaDAO implements ICategoriaEmpresa {

    public CategoriaEmpresaDAO(Connection connection) {
    }

    public CategoriaEmpresaDAO() {
    }

    @Override
    public CategoriaEmpresa save(CategoriaEmpresa categoria) {
        // 1️⃣ Verifica se já existe categoria com esse nome
        Optional<CategoriaEmpresa> existente = findByNome(categoria.getNome());

        if (existente.isPresent()) {
            // Já existe, retorna ela
            return existente.get();
        }

        // 2️⃣ Se não existir, insere uma nova
        String sql = "INSERT INTO categoria_empresa (nome, descricao) VALUES (?, ?)";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, categoria.getNome());
                ps.setString(2, categoria.getDescricao());
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    categoria.setId(rs.getLong(1));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar categoria: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }
        return categoria;
    }

    @Override
    public CategoriaEmpresa update(CategoriaEmpresa categoria) {
        String sql = "UPDATE categoria_empresa SET nome = ?, descricao = ? WHERE id = ?";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, categoria.getNome());
                ps.setString(2, categoria.getDescricao());
                ps.setLong(3, categoria.getId());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar categoria: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }
        return categoria;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM categoria_empresa WHERE id = ?";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao excluir categoria: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }
    }

    @Override
    public List<CategoriaEmpresa> findAll() {
        List<CategoriaEmpresa> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria_empresa";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    CategoriaEmpresa categoria = new CategoriaEmpresa(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getString("descricao")
                    );
                    categorias.add(categoria);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar categorias: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }
        return categorias;
    }

    @Override
    public Optional<CategoriaEmpresa> findById(Long id) {
        CategoriaEmpresa categoria = null;
        String sql = "SELECT * FROM categoria_empresa WHERE id = ?";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    categoria = new CategoriaEmpresa(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getString("descricao")
                    );
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar categoria: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }

        return Optional.ofNullable(categoria);
    }

    // 🆕 Novo método para buscar por nome (usado dentro do save)
    public Optional<CategoriaEmpresa> findByNome(String nome) {
        String sql = "SELECT * FROM categoria_empresa WHERE nome = ?";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, nome);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    CategoriaEmpresa categoria = new CategoriaEmpresa(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getString("descricao")
                    );
                    return Optional.of(categoria);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar categoria por nome: " + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }

        return Optional.empty();
    }
}
