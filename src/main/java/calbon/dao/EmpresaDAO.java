package calbon.dao;

import calbon.modelEmpresa.Empresa;
import calbon.modelEmpresa.CategoriaEmpresa;
import calbon.infra.ConnectionFactory;
import calbon.modelEmpresa.IEmpresaDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmpresaDAO implements IEmpresaDAO {

    @Override
    public Empresa save(Empresa empresa) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "INSERT INTO empresa (cnpj, nome, dt_cadastro, categoria_id) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, empresa.getCnpj());
            preparedStatement.setString(2, empresa.getNome());
            preparedStatement.setDate(3, new Date(empresa.getDtCadastro().getTime()));
            preparedStatement.setLong(4, empresa.getCategoria().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return empresa;
    }

    @Override
    public Empresa update(Empresa empresa) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "UPDATE empresa SET nome = ?, dt_cadastro = ?, categoria_id = ? WHERE cnpj = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, empresa.getNome());
            preparedStatement.setDate(2, new Date(empresa.getDtCadastro().getTime()));
            preparedStatement.setLong(3, empresa.getCategoria().getId());
            preparedStatement.setString(4, empresa.getCnpj());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return empresa;
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "DELETE FROM empresa WHERE cnpj = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(id));
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Empresa> findAll() {
        List<Empresa> empresas = new ArrayList<>();
        String sql = "SELECT e.cnpj, e.nome, e.dt_cadastro, c.id AS categoria_id, c.nome AS categoria_nome, c.descricao, c.porte " +
                "FROM empresa e " +
                "JOIN categoria_empresa c ON e.categoria_id = c.id";

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String cnpj = resultSet.getString("cnpj");
                String nome = resultSet.getString("nome");
                Date dtCadastro = resultSet.getDate("dt_cadastro");

                CategoriaEmpresa categoria = new CategoriaEmpresa();
                categoria.setId(resultSet.getLong("categoria_id"));
                categoria.setNome(resultSet.getString("categoria_nome"));
                categoria.setDescricao(resultSet.getString("descricao"));
                categoria.setPorte(resultSet.getString("porte"));

                Empresa empresa = new Empresa();
                empresa.setCnpj(cnpj);
                empresa.setNome(nome);
                empresa.setDtCadastro(dtCadastro);
                empresa.setCategoria(categoria);

                empresas.add(empresa);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return empresas;
    }

    @Override
    public Optional<Empresa> findById(Long id) {
        Empresa empresa = null;
        String sql = "SELECT e.cnpj, e.nome, e.dt_cadastro, c.id AS categoria_id, c.nome AS categoria_nome, c.descricao, c.porte " +
                "FROM empresa e " +
                "JOIN categoria_empresa c ON e.categoria_id = c.id " +
                "WHERE e.cnpj = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(id));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nome = resultSet.getString("nome");
                Date dtCadastro = resultSet.getDate("dt_cadastro");

                CategoriaEmpresa categoria = new CategoriaEmpresa();
                categoria.setId(resultSet.getLong("categoria_id"));
                categoria.setNome(resultSet.getString("categoria_nome"));
                categoria.setDescricao(resultSet.getString("descricao"));
                categoria.setPorte(resultSet.getString("porte"));

                empresa = new Empresa();
                empresa.setCnpj(String.valueOf(id));
                empresa.setNome(nome);
                empresa.setDtCadastro(dtCadastro);
                empresa.setCategoria(categoria);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return Optional.ofNullable(empresa);
    }
}
