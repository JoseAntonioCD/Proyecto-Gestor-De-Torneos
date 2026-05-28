package DAO;

import dataAccess.ConnectionBD;
import model.EntidadPromotora;
import model.Participante;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO() {

        conn = ConnectionBD.getConnection();
    }

    /*
     * LOGIN
     */

    public Usuario login(
            String email,
            String password
    ) {

        String sql = """
                SELECT *
                FROM usuarios
                WHERE email = ?
                AND password = ?
                """;

        try(

                PreparedStatement stmt =
                        conn.prepareStatement(sql)

        ) {

            stmt.setString(1, email);

            stmt.setString(2, password);

            ResultSet rs =
                    stmt.executeQuery();

            if(rs.next()) {

                String tipo =
                        rs.getString("tipo");

                Usuario usuario;

                /*
                 * PARTICIPANTE
                 */

                if(tipo.equalsIgnoreCase(
                        "PARTICIPANTE"
                )) {

                    usuario =
                            new Participante();

                } else {

                    /*
                     * PROMOTOR
                     */

                    usuario =
                            new EntidadPromotora();
                }

                usuario.setId(
                        rs.getInt("id")
                );

                usuario.setNombre(
                        rs.getString("nombre")
                );

                usuario.setEmail(
                        rs.getString("email")
                );

                usuario.setPassword(
                        rs.getString("password")
                );

                usuario.setTipo(
                        tipo
                );

                return usuario;
            }

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    /*
     * REGISTRO
     */

    public boolean registrarUsuario(
            Usuario usuario
    ) {

        String sql = """
                INSERT INTO usuarios(
                    nombre,
                    email,
                    password,
                    tipo
                )
                VALUES(?,?,?,?)
                """;

        try(

                PreparedStatement stmt =
                        conn.prepareStatement(sql)

        ) {

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

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    /*
     * OBTENER USUARIO POR ID
     */

    public Usuario getUsuarioById(
            int id
    ) {

        String sql = """
        SELECT *
        FROM usuarios
        WHERE id = ?
        """;

        try(
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            ResultSet rs =
                    stmt.executeQuery();

            if(rs.next()) {

                String tipo =
                        rs.getString("tipo");

                /*
                 * PROMOTOR
                 */

                if(
                        tipo.equalsIgnoreCase(
                                "PROMOTOR"
                        )
                ) {

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

                    return promotor;
                }

                /*
                 * PARTICIPANTE
                 */

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

                return participante;
            }

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return null;
    }
}