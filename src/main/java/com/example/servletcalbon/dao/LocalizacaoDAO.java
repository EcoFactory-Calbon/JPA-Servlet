package com.example.servletcalbon.dao;

import com.example.servletcalbon.modelFuncionario.Localizacao;
import com.example.servletcalbon.modelFuncionario.ILocalizacaoDAO;
import com.example.servletcalbon.infra.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocalizacaoDAO implements ILocalizacaoDAO {

//    CONSTRUTOR SEM CONEXAO EXTERNA
    public LocalizacaoDAO(Connection connection) {
    }

//    CONSTRUTOR PADRÃO
    public LocalizacaoDAO() {

    }

//    METODO PARA SALVAR UMA NOVA LOCALIZACAO NO BANCO
    @Override
    public Localizacao save(Localizacao localizacao) {
        String sql = "INSERT INTO localizacao (estado, cidade) VALUES (?, ?)";
        Connection connection = null;
        try {
            connection = ConnectionFactory.getConnection();

//            Prepara a query para inserir e retornar um id gerado
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, localizacao.getEstado());
                ps.setString(2, localizacao.getCidade());
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    localizacao.setId(rs.getLong(1));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
//            Fecha conexao com bd
            ConnectionFactory.fechar(connection);
        }
        return localizacao;
    }


//    METODO PARA ATUALIZAR A LOCALIZAO EXISTENTE NO BD
    @Override
    public Localizacao update(Localizacao localizacao) {
        String sql = "UPDATE localizacao SET estado = ?, cidade = ? WHERE id = ?";
        Connection connection = null;
        try {
//            Abre a conexao
            connection = ConnectionFactory.getConnection();
//            Prepara a query para execução
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, localizacao.getEstado());
                ps.setString(2, localizacao.getCidade());
                ps.setLong(3, localizacao.getId());
//                Executa o comando SQL
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }
        return localizacao;
    }

//    METODO PARA DELETAR UMA LOCALIZACAO
    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM localizacao WHERE id = ?";
        Connection connection = null;
        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
//                Define o id gerado do registro a ser deletado
                ps.setLong(1, id);
//                Executa o DELETE
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }
    }

    @Override
    public List<Localizacao> findAll() {
        List<Localizacao> localizacoes = new ArrayList<>();
        String sql = "SELECT * FROM localizacao";
        Connection connection = null;
        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Localizacao l = new Localizacao(
                            rs.getLong("id"),
                            rs.getString("estado"),
                            rs.getString("cidade")
                    );
                    localizacoes.add(l);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }
        return localizacoes;
    }

    @Override
    public Optional<Localizacao> findById(Long id) {
        Localizacao localizacao = null;
        String sql = "SELECT * FROM localizacao WHERE id = ?";
        Connection connection = null;
        try {
            connection = ConnectionFactory.getConnection();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    localizacao = new Localizacao(
                            rs.getLong("id"),
                            rs.getString("estado"),
                            rs.getString("cidade")
                    );
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory.fechar(connection);
        }
        return Optional.ofNullable(localizacao);
    }
}
