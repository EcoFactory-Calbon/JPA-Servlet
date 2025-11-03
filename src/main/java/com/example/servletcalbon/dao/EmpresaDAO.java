package com.example.servletcalbon.dao;

import com.example.servletcalbon.infra.ConnectionFactory;
import com.example.servletcalbon.modelEmpresa.Empresa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmpresaDAO {

    private final Connection connection;

    public EmpresaDAO(Connection connection) {
        this.connection = connection;
    }

    // 🔹 LISTAR TODAS AS EMPRESAS (com JOIN em todas as tabelas)
    public List<Empresa> findAll() {
        List<Empresa> empresas = new ArrayList<>();

        String sql = """
            SELECT 
                e.id AS empresa_id,
                e.nome AS empresa_nome,
                e.cnpj AS empresa_cnpj,
                e.senha AS empresa_senha,
                c.nome AS categoria_nome,
                c.descricao AS categoria_descricao,
                p.nome AS porte_nome,
                l.estado AS localizacao_estado,
                l.cidade AS localizacao_cidade
            FROM empresa e
            JOIN categoria_empresa c ON c.id = e.id_categoria
            LEFT JOIN porte p ON p.id = e.id_porte
            JOIN localizacao l ON l.id = e.id_localizacao
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Empresa empresa = new Empresa();
                empresa.setId(rs.getInt("empresa_id"));
                empresa.setNome(rs.getString("empresa_nome"));
                empresa.setCnpj(rs.getString("empresa_cnpj"));
                empresa.setSenha(rs.getString("empresa_senha"));
                empresa.setCategoriaNome(rs.getString("categoria_nome"));
                empresa.setCategoriaDescricao(rs.getString("categoria_descricao"));
                empresa.setPorteNome(rs.getString("porte_nome"));
                empresa.setEstado(rs.getString("localizacao_estado"));
                empresa.setCidade(rs.getString("localizacao_cidade"));
                empresas.add(empresa);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar empresas: " + e.getMessage(), e);
        }

        return empresas;
    }

    // 🔹 SALVAR EMPRESA
    public void save(Empresa empresa) {
        String sql = "INSERT INTO empresa (nome, cnpj, senha, id_localizacao, id_categoria, id_porte) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, empresa.getNome());
            stmt.setString(2, empresa.getCnpj());
            stmt.setString(3, empresa.getSenha());
            stmt.setInt(4, empresa.getIdLocalizacao());
            stmt.setInt(5, empresa.getIdCategoria());
            stmt.setInt(6, empresa.getIdPorte());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar empresa: " + e.getMessage(), e);
        }
    }

    // 🔹 ATUALIZAR EMPRESA
    public void update(Empresa empresa) {
        String sql = """
            UPDATE empresa
            SET nome = ?, cnpj = ?, senha = ?, id_localizacao = ?, id_categoria = ?, id_porte = ?
            WHERE id = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, empresa.getNome());
            stmt.setString(2, empresa.getCnpj());
            stmt.setString(3, empresa.getSenha());
            stmt.setInt(4, empresa.getIdLocalizacao());
            stmt.setInt(5, empresa.getIdCategoria());
            stmt.setInt(6, empresa.getIdPorte());
            stmt.setInt(7, empresa.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar empresa: " + e.getMessage(), e);
        }
    }

    // 🔹 EXCLUIR EMPRESA
    public void delete(int id) {
        String sql = "DELETE FROM empresa WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir empresa: " + e.getMessage(), e);
        }
    }


    public Optional<Empresa> findByNome(String empresaNome) throws SQLException {
        String sql = """
        SELECT 
            e.id AS empresa_id,
            e.nome AS empresa_nome,
            e.cnpj AS empresa_cnpj,
            e.senha AS empresa_senha,
            e.id_localizacao,
            e.id_categoria,
            e.id_porte
        FROM empresa e
        WHERE e.nome = ?
    """;

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, empresaNome);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Empresa empresa = new Empresa();
            empresa.setId(rs.getInt("empresa_id"));
            empresa.setNome(rs.getString("empresa_nome"));
            empresa.setCnpj(rs.getString("empresa_cnpj"));
            empresa.setSenha(rs.getString("empresa_senha"));
            empresa.setIdLocalizacao(rs.getInt("id_localizacao"));
            empresa.setIdCategoria(rs.getInt("id_categoria"));
            empresa.setIdPorte(rs.getInt("id_porte"));
            return Optional.of(empresa);
        }

        return Optional.empty();
    }

}
