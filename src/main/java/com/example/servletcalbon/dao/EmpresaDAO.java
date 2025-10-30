package com.example.servletcalbon.dao;

import com.example.servletcalbon.infra.ConnectionFactory;
import com.example.servletcalbon.modelEmpresa.Empresa;
import com.example.servletcalbon.modelEmpresa.IEmpresaDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmpresaDAO implements IEmpresaDAO {

//    CONSTRUTOR SEM PARÂMETRO
    public EmpresaDAO(Connection connection) {}

//    METODO PARA SALVAR UMA NOVA EMPRESA NO BD
    @Override
    public Empresa save(Empresa empresa) {
        String sql = "INSERT INTO empresa (cnpj, nome, id_localizacao, id_categoria, senha, id_porte) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, empresa.getCnpj());
            ps.setString(2, empresa.getNome());
            ps.setInt(3, empresa.getIdLocalizacao());
            ps.setInt(4, empresa.getIdCategoria());
            ps.setString(5, empresa.getSenha());
            ps.setInt(6, empresa.getIdPorte());

//            EXECUTA INSERT
            ps.executeUpdate();
            ConnectionFactory.fechar(connection);

//            LANÇA ERRO SE DER ERRADO NO BD
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar empresa: " + ex.getMessage(), ex);
        }
        return empresa;
    }

//    METODO PARA ATUALIZART DOS DE UMA EMPRESA EXISTENTE
    @Override
    public Empresa update(Empresa empresa) {
        String sql = "UPDATE empresa SET nome = ?, id_localizacao = ?, id_categoria = ?, senha = ?, id_porte = ? WHERE cnpj = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, empresa.getNome());
            ps.setInt(2, empresa.getIdLocalizacao());
            ps.setInt(3, empresa.getIdCategoria());
            ps.setString(4, empresa.getSenha());
            ps.setInt(5, empresa.getIdPorte());
            ps.setString(6, empresa.getCnpj());

//            EXECUTA UPDATE
            ps.executeUpdate();
            ConnectionFactory.fechar(connection);

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar empresa: " + ex.getMessage(), ex);
        }
        return empresa;
    }

//    METODO PARA DELETAR UMA EMPRESA PELO CNPJ
    @Override
    public void delete(String cnpjParam) {
        String sql = "DELETE FROM empresa WHERE cnpj = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
//            DEFINE O CNPJ QUE VAI SER EXCLUIDO
            ps.setString(1, cnpjParam);
//            EXECUTA O COMANDO DELETE
            ps.executeUpdate();
            ConnectionFactory.fechar(connection);
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar empresa: " + ex.getMessage(), ex);
        }
    }

//    METODO PARA RETORNAR TODAS AS EMPRESAS CADASTRADAS
    @Override
    public List<Empresa> findAll() {
        List<Empresa> empresas = new ArrayList<>();
        String sql = "SELECT id, cnpj, nome, senha, id_localizacao, id_categoria, id_porte FROM empresa";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

//            ADICIONA UMA LISTA
            while (rs.next()) {
                Empresa empresa = new Empresa();
                empresa.setId(rs.getInt("id"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setNome(rs.getString("nome"));
                empresa.setSenha(rs.getString("senha"));
                empresa.setIdLocalizacao(rs.getInt("id_localizacao"));
                empresa.setIdCategoria(rs.getInt("id_categoria"));
                empresa.setIdPorte(rs.getInt("id_porte"));

                empresas.add(empresa);
            }
            ConnectionFactory.fechar(connection);

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar empresas: " + ex.getMessage(), ex);
        }

        return empresas;
    }


//    METODO PARA BUSCAR UMA EMPRESA PELO CNPJ
    @Override
    public Optional<Empresa> findById(String cnpjParam) {
        String sql = "SELECT id, cnpj, nome, id_localizacao, id_categoria, senha, id_porte FROM empresa WHERE cnpj = ?";
        Empresa empresa = null;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

//            DEFINI O CNPJ A SER PESQUISADO
            ps.setString(1, cnpjParam);

//            EXECUTA SELECT
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    empresa = new Empresa();
                    empresa.setId(rs.getInt("id"));
                    empresa.setCnpj(rs.getString("cnpj"));
                    empresa.setNome(rs.getString("nome"));
                    empresa.setIdLocalizacao(rs.getInt("id_localizacao"));
                    empresa.setIdCategoria(rs.getInt("id_categoria"));
                    empresa.setSenha(rs.getString("senha"));
                    empresa.setIdPorte(rs.getInt("id_porte"));
                }
            }

            ConnectionFactory.fechar(connection);

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar empresa por CNPJ: " + ex.getMessage(), ex);
        }

//        RETORNA EMPRESA OU VAZIO, SE NAO ENCONTRADA
        return Optional.ofNullable(empresa);
    }
}
