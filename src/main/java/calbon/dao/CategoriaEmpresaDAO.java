package calbon.dao;

import calbon.modelEmpresa.CategoriaEmpresa;
import calbon.modelEmpresa.ICategoriaEmpresa;
import calbon.infra.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriaEmpresaDAO implements ICategoriaEmpresa {

    @Override
    public CategoriaEmpresa save(CategoriaEmpresa categoria) {
        String sql = "INSERT INTO categoria_empresa (nome, descricao, porte) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, categoria.getNome());
            ps.setString(2, categoria.getDescricao());
            ps.setString(3, categoria.getPorte());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                categoria.setId(rs.getLong(1));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return categoria;
    }

    @Override
    public CategoriaEmpresa update(CategoriaEmpresa categoria) {
        String sql = "UPDATE categoria_empresa SET nome = ?, descricao = ?, porte = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, categoria.getNome());
            ps.setString(2, categoria.getDescricao());
            ps.setString(3, categoria.getPorte());
            ps.setLong(4, categoria.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return categoria;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM categoria_empresa WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<CategoriaEmpresa> findAll() {
        List<CategoriaEmpresa> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria_empresa";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CategoriaEmpresa categoria = new CategoriaEmpresa(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("porte")
                );
                categorias.add(categoria);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return categorias;
    }

    @Override
    public Optional<CategoriaEmpresa> findById(Long id) {
        CategoriaEmpresa categoria = null;
        String sql = "SELECT * FROM categoria_empresa WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                categoria = new CategoriaEmpresa(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("porte")
                );
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return Optional.ofNullable(categoria);
    }
}