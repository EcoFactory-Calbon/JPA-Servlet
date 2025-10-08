package calbon.infra;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;


public class ConnectionFactory {

    private ConnectionFactory() {}

    private static final Dotenv dotenv = Dotenv.load();

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");

            String url = dotenv.get("DB_URL");
            String user = dotenv.get("DB_USER");
            String password = dotenv.get("DB_PASSWORD");

            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", ex);
        }
    }
}
