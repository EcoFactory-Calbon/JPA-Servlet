package com.example.servletcalbon.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class ConnectionFactory {

    private static final Dotenv dotenv = initDotenv();

    private static final String url = getEnv("DB_URL");
    private static final String user = getEnv("DB_USER");
    private static final String password = getEnv("DB_SENHA");

    // Inicializa o Dotenv de forma segura
    private static Dotenv initDotenv() {
        try {
            // Tenta carregar do classpath (resources/.env)
            return Dotenv.configure()
                    .ignoreIfMissing() // não lança exceção se não encontrar
                    .load();
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private static String getEnv(String key) {
        // Primeiro tenta Dotenv
        if (dotenv != null && dotenv.get(key) != null) {
            return dotenv.get(key);
        }
        // Depois tenta variável de ambiente do sistema
        String env = System.getenv(key);
        if (env != null) {
            return env;
        }
        throw new RuntimeException("Variável de ambiente ou .env '" + key + "' não encontrada.");
    }

    public static Connection getConnection() {
        try {
            // Força carregar o driver do PostgreSQL
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException("Erro ao estabelecer a conexão com o banco de dados.", ex);
        }
    }

    public static void fechar(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
