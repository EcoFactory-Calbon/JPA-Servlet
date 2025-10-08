package calbon.dao;

import calbon.modelFuncionario.Localizacao;
import calbon.modelFuncionario.ILocalizacaoDAO;
import calbon.infra.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocalizacaoDAO implements ILocalizacaoDAO {

    @Override
    public Localizacao save(Localizacao localizacao) {
        String sql = "INSERT INTO localizacao (estado, pais, end_cidade, end_bairro, end_rua, end_numero, complemento) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, localizacao.getEstado());
            ps.setString(2, localizacao.getPais());
            ps.setString(3, localizacao.getEnd_cidade());
            ps.setString(4, localizacao.getEnd_bairro());
            ps.setString(5, localizacao.getEnd_rua());
            ps.setString(6, localizacao.getEnd_numero());
            ps.setString(7, localizacao.getComplemento());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                localizacao.setId(rs.getLong(1));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return localizacao;
    }

    @Override
    public Localizacao update(Localizacao localizacao) {
        String sql = "UPDATE localizacao SET estado = ?, pais = ?, end_cidade = ?, end_bairro = ?, end_rua = ?, end_numero = ?, complemento = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, localizacao.getEstado());
            ps.setString(2, localizacao.getPais());
            ps.setString(3, localizacao.getEnd_cidade());
            ps.setString(4, localizacao.getEnd_bairro());
            ps.setString(5, localizacao.getEnd_rua());
            ps.setString(6, localizacao.getEnd_numero());
            ps.setString(7, localizacao.getComplemento());
            ps.setLong(8, localizacao.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return localizacao;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM localizacao WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Localizacao> findAll() {
        List<Localizacao> localizacoes = new ArrayList<>();
        String sql = "SELECT * FROM localizacao";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Localizacao l = new Localizacao(
                        rs.getLong("id"),
                        rs.getString("estado"),
                        rs.getString("pais"),
                        rs.getString("end_cidade"),
                        rs.getString("end_bairro"),
                        rs.getString("end_rua"),
                        rs.getString("end_numero"),
                        rs.getString("complemento")
                );
                localizacoes.add(l);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return localizacoes;
    }

    @Override
    public Optional<Localizacao> findById(Long id) {
        Localizacao localizacao = null;
        String sql = "SELECT * FROM localizacao WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                localizacao = new Localizacao(
                        rs.getLong("id"),
                        rs.getString("estado"),
                        rs.getString("pais"),
                        rs.getString("end_cidade"),
                        rs.getString("end_bairro"),
                        rs.getString("end_rua"),
                        rs.getString("end_numero"),
                        rs.getString("complemento")
                );
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return Optional.ofNullable(localizacao);
    }
}
