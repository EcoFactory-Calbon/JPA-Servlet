package calbon.infra;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private ConnectionFactory() {}

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(
                    "jdbc:postgresql://pg-2ad89544-germinare-a6bf.b.aivencloud.com:17646/inter?sslmode=require",
                    "avnadmin",
                    "AVNS_w955jhXXlsSYmpde_f-"
            );
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
