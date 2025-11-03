package com.example.servletcalbon.dao;

import com.example.servletcalbon.modelFuncionario.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FuncionarioDAO {

    private final Connection connection;

    public FuncionarioDAO(Connection connection) {
        this.connection = connection;
    }

    // 🔹 SALVAR FUNCIONÁRIO (com geração do numero_cracha)
    public void save(Funcionario funcionario) {
        // Primeiro busca o próximo número de crachá
        String nextCrachaSql = "SELECT COALESCE(MAX(numero_cracha), 0) + 1 AS next_cracha FROM funcionario";
        String insertSql = "INSERT INTO funcionario (numero_cracha, nome, sobrenome, email, senha, is_gestor, id_cargo, id_localizacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Buscar próximo número de crachá
            PreparedStatement nextStmt = connection.prepareStatement(nextCrachaSql);
            ResultSet rs = nextStmt.executeQuery();

            int nextCracha = 1;
            if (rs.next()) {
                nextCracha = rs.getInt("next_cracha");
            }
            nextStmt.close();

            // Agora faz o INSERT com o número obtido
            PreparedStatement insertStmt = connection.prepareStatement(insertSql);
            insertStmt.setInt(1, nextCracha);
            insertStmt.setString(2, funcionario.getNome());
            insertStmt.setString(3, funcionario.getSobrenome());
            insertStmt.setString(4, funcionario.getEmail());
            insertStmt.setString(5, funcionario.getSenha());
            insertStmt.setBoolean(6, funcionario.isGestor());
            insertStmt.setLong(7, funcionario.getIdCargo());
            insertStmt.setLong(8, funcionario.getIdLocalizacao());
            insertStmt.executeUpdate();
            insertStmt.close();

            // Atualiza o objeto funcionario com o numero_cracha gerado
            funcionario.setNumeroCracha(nextCracha);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar funcionário: " + e.getMessage(), e);
        }
    }

    // 🔹 LISTAR TODOS OS FUNCIONÁRIOS (com JOIN igual ao EmpresaDAO)
    public List<Funcionario> findAll() {
        List<Funcionario> funcionarios = new ArrayList<>();

        String sql = """
            SELECT 
                f.numero_cracha,
                f.nome,
                f.sobrenome,
                f.email,
                f.senha,
                f.is_gestor,
                f.id_cargo,
                f.id_localizacao,
                c.nome AS cargo_nome,
                s.nome AS setor_nome,
                l.estado,
                l.cidade
            FROM funcionario f
            JOIN cargo c ON c.id = f.id_cargo
            JOIN setor s ON s.id = c.id_setor
            JOIN localizacao l ON l.id = f.id_localizacao
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setNumeroCracha(rs.getInt("numero_cracha"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setSobrenome(rs.getString("sobrenome"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setSenha(rs.getString("senha"));
                funcionario.setGestor(rs.getBoolean("is_gestor"));
                funcionario.setIdCargo(rs.getLong("id_cargo"));
                funcionario.setIdLocalizacao(rs.getLong("id_localizacao"));
                funcionarios.add(funcionario);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar funcionários: " + e.getMessage(), e);
        }

        return funcionarios;
    }

    // 🔹 ATUALIZAR FUNCIONÁRIO
    public void update(Funcionario funcionario) {
        String sql = """
            UPDATE funcionario
            SET nome = ?, sobrenome = ?, email = ?, senha = ?, is_gestor = ?, id_cargo = ?, id_localizacao = ?
            WHERE numero_cracha = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getSobrenome());
            stmt.setString(3, funcionario.getEmail());
            stmt.setString(4, funcionario.getSenha());
            stmt.setBoolean(5, funcionario.isGestor());
            stmt.setLong(6, funcionario.getIdCargo());
            stmt.setLong(7, funcionario.getIdLocalizacao());
            stmt.setInt(8, funcionario.getNumeroCracha());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar funcionário: " + e.getMessage(), e);
        }
    }

    // 🔹 EXCLUIR FUNCIONÁRIO
    public void delete(int numeroCracha) {
        String sql = "DELETE FROM funcionario WHERE numero_cracha = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numeroCracha);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir funcionário: " + e.getMessage(), e);
        }
    }

    // 🔹 BUSCAR FUNCIONÁRIO POR ID
    public Optional<Funcionario> findById(int numeroCracha) {
        String sql = """
            SELECT 
                f.numero_cracha,
                f.nome,
                f.sobrenome,
                f.email,
                f.senha,
                f.is_gestor,
                f.id_cargo,
                f.id_localizacao
            FROM funcionario f
            WHERE f.numero_cracha = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numeroCracha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setNumeroCracha(rs.getInt("numero_cracha"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setSobrenome(rs.getString("sobrenome"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setSenha(rs.getString("senha"));
                funcionario.setGestor(rs.getBoolean("is_gestor"));
                funcionario.setIdCargo(rs.getLong("id_cargo"));
                funcionario.setIdLocalizacao(rs.getLong("id_localizacao"));
                return Optional.of(funcionario);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    // 🔹 BUSCAR FUNCIONÁRIO POR EMAIL
    public Optional<Funcionario> findByEmail(String email) {
        String sql = """
            select f.numero_cracha, f.nome, f.sobrenome, f.email, f.senha, f.is_gestor, c.nome, s.nome, e.nome, l.estado, l.cidade
            from funcionario f
            join cargo c on c.id = f.id_cargo
            join setor s on s.id = c.id_setor
            join empresa e on e.id = s.id_empresa
            join localizacao l on l.id = f.id_localizacao
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setNumeroCracha(rs.getInt("numero_cracha"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setSobrenome(rs.getString("sobrenome"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setSenha(rs.getString("senha"));
                funcionario.setGestor(rs.getBoolean("is_gestor"));
                funcionario.setIdCargo(rs.getLong("id_cargo"));
                funcionario.setIdLocalizacao(rs.getLong("id_localizacao"));
                return Optional.of(funcionario);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário por email: " + e.getMessage(), e);
        }

        return Optional.empty();
    }
}