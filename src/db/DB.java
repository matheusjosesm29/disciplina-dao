package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection conexao = null;

    private static Properties getPropriedades() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new DbException("Erro ao carregar db.properties: " + e.getMessage());
        }
    }

    public static Connection getConexao() {
        if (conexao == null) {
            try {
                Properties props = getPropriedades();
                String url = props.getProperty("dburl");
                conexao = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                throw new DbException("Erro ao conectar ao banco: " + e.getMessage());
            }
        }
        return conexao;
    }

    public static void closeConnection() {
        if (conexao != null) {
            try {
                conexao.close();
                conexao = null;
            } catch (SQLException e) {
                throw new DbException("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException("Erro ao fechar Statement: " + e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException("Erro ao fechar ResultSet: " + e.getMessage());
            }
        }
    }
}
