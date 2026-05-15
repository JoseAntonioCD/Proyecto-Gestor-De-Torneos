package DAO;

import dataAccess.ConnectionBD;
import model.EntidadPromotora;
import model.Participante;
import model.Usuario;
import java.sql.*;

public class UsuarioDAO {

    private Connection conn;

    public UsuarioDAO() {
        conn = ConnectionBD.getConnection();
    }

    public Usuario login(String email, String password) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND password = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                String tipo =
                        rs.getString("tipo");

                /*
                 * Si es participante
                 */

                if (tipo.equals("PARTICIPANTE")) {

                    Participante participante =
                            new Participante();

                    participante.setId(
                            rs.getInt("id")
                    );

                    participante.setNombre(
                            rs.getString("nombre")
                    );

                    participante.setEmail(
                            rs.getString("email")
                    );

                    participante.setPassword(
                            rs.getString("password")
                    );

                    participante.setTipo(tipo);

                    return participante;
                }

                /*
                 * Si es promotor
                 */

                else {

                    EntidadPromotora promotor =
                            new EntidadPromotora();

                    promotor.setId(
                            rs.getInt("id")
                    );

                    promotor.setNombre(
                            rs.getString("nombre")
                    );

                    promotor.setEmail(
                            rs.getString("email")
                    );

                    promotor.setPassword(
                            rs.getString("password")
                    );

                    promotor.setTipo(tipo);

                    return promotor;
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    /*
     * REGISTRO
     *
     * Inserta usuarios nuevos
     */

    public boolean registrarUsuario(Usuario usuario) {

        String sql = """
                INSERT INTO usuarios(
                    nombre,
                    email,
                    password,
                    tipo
                )
                VALUES(?,?,?,?)
                """;

        try (PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setString(
                    1,
                    usuario.getNombre()
            );

            stmt.setString(
                    2,
                    usuario.getEmail()
            );

            stmt.setString(
                    3,
                    usuario.getPassword()
            );

            stmt.setString(
                    4,
                    usuario.getTipo()
            );

            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }
}