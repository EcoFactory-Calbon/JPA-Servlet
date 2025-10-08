package calbon.dao;

import calbon.modelEmpresa.ISetorDAO;
import calbon.modelEmpresa.Setor;
import calbon.infra.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SetorDAO implements ISetorDAO {

    @Override
    public Setor save(Setor setor) {
        String sql = "INSERT INTO setor (nome) VALUES (?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, setor.getNome());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                setor.setId((long) rs.getInt(1));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return setor;
    }

    @Override
    public Setor update(Setor setor) {
        String sql = "UPDATE setor SET nome = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, setor.getNome());
            ps.setInt(2, Math.toIntExact(setor.getId()));
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return setor;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM setor WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Setor> findAll() {
        List<Setor> setores = new ArrayList<>();
        String sql = "SELECT * FROM setor";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Setor setor = new Setor(
                        (long) rs.getInt("id"),
                        rs.getString("nome")
                );
                setores.add(setor);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return setores;
    }

    @Override
    public Optional<Setor> findById(Long id) {
        Setor setor = null;
        String sql = "SELECT * FROM setor WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                setor = new Setor(
                        (long) rs.getInt("id"),
                        rs.getString("nome")
                );
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return Optional.ofNullable(setor);
    }
}
