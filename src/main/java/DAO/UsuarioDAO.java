package DAO;

import model.Usuario;
import java.sql.*;

public class UsuarioDAO {

    private Connection conn;

    public UsuarioDAO(Connection conn) {
        this.conn = conn;
    }

    public Usuario login(String email, String password) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND password = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("password")
                ) {};
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}