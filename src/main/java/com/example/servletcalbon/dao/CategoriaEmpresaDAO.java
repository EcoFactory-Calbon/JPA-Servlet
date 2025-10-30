package com.example.servletcalbon.dao;

import com.example.servletcalbon.modelEmpresa.CategoriaEmpresa;
import com.example.servletcalbon.modelEmpresa.ICategoriaEmpresa;
import com.example.servletcalbon.infra.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriaEmpresaDAO implements ICategoriaEmpresa {

//    CONSTRUTOR SEM CONEXÃO EXTERNA
    public CategoriaEmpresaDAO(Connection connection) {
    }

//    CONSTRUTOR PADRÃO
    public CategoriaEmpresaDAO() {
    }

//    METODO PARA SALVAR UMA NOVA CATEGORIA NO BANCO
    @Override
    public CategoriaEmpresa save(CategoriaEmpresa categoria) {
//        VERIFICA SE JA EXISTE CATEGORIA COM O MESMO NOME
        Optional<CategoriaEmpresa> existente = findByNome(categoria.getNome());

        if (existente.isPresent()) {
//           RETORNA, SE JA EXISTE
            return existente.get();
        }

//        CRIAR UMA NOVA, SE NÃO EXISTE
        String sql = "INSERT INTO categoria_empresa (nome, descricao) VALUES (?, ?)";
        Connection connection = null;

        try {
//            ABRE CONEXÃO COM O BANCO
            connection = ConnectionFactory.getConnection();

//            PERMITE OBTER ID AUTOMATICAMENTE
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, categoria.getNome());
                ps.setString(2, categoria.getDescricao());
                ps.executeUpdate();

//                RECUPÉRA O ID GERADO AUTOMATICAMENTE
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    categoria.setId(rs.getLong(1));
                }
            }
        } catch (SQLException ex) {
//            CASO OCORRA ERRO, LANCE UMA MENSAGENM
            throw new RuntimeException("Erro ao salvar categoria: " + ex.getMessage(), ex);
        } finally {
//            FECHA CONEXAO
            ConnectionFactory.fechar(connection);
        }
        return categoria;
    }

//    METODO PARA ATUALIZAR UMA CATEGORIA EXISTENTE
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

//    METODO PARA EXCLUIR UMA CATEGORIA PELO ID
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

//    METODO PARA LISTAR TODAS AS CATEGORIAS CADASTRADAS
    @Override
    public List<CategoriaEmpresa> findAll() {
        List<CategoriaEmpresa> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria_empresa";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

//                PERCORRE TODAS AS LINHAS RETORNADAS PELO SELECT
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

//    METODO PARA BUSCAR UMA CATEGORIA PELO ID
    public static Optional<CategoriaEmpresa> findById(Long id) {
        CategoriaEmpresa categoria = null;
        String sql = "SELECT * FROM categoria_empresa WHERE id = ?";
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();

//                SE ENCONTRAR UMA LINHA CORRESPONDENTE CRIA UM OBJETO CATEGORIA
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

//        RETORNA CATEGORIA OU VAZIO, SE NÃO ENCONTRADA
        return Optional.ofNullable(categoria);
    }

//    METODO ADICIONAL PARA BUSCAR CATEGORIA PELO NOME
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

//        R
        return Optional.empty();
    }
}
