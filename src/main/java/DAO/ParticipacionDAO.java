package DAO;

import dataAccess.ConnectionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParticipacionDAO {

    private static Connection conn;

    public ParticipacionDAO() {

        conn = ConnectionBD.getConnection();
    }

    /*
     * PARTICIPAR
     */

    public static boolean participar(
            int idUsuario,
            int idEvento
    ) {

        if(estaApuntado(idUsuario, idEvento)) {

            return false;
        }

        String sql = """
                INSERT INTO participaciones
                (idUsuario, idEvento, fechaInscripcion)
                VALUES (?, ?, CURDATE())
                """;

        try(
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, idUsuario);

            stmt.setInt(2, idEvento);

            return stmt.executeUpdate() > 0;

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    /*
     * DESAPUNTARSE
     */

    public static boolean desapuntarse(
            int idUsuario,
            int idEvento
    ) {

        String sql = """
                DELETE FROM participaciones
                WHERE idUsuario = ?
                AND idEvento = ?
                """;

        try(
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, idUsuario);

            stmt.setInt(2, idEvento);

            return stmt.executeUpdate() > 0;

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    /*
     * COMPROBAR SI YA ESTÁ APUNTADO
     */

    public static boolean estaApuntado(
            int idUsuario,
            int idEvento
    ) {

        String sql = """
                SELECT *
                FROM participaciones
                WHERE idUsuario = ?
                AND idEvento = ?
                """;

        try(
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, idUsuario);

            stmt.setInt(2, idEvento);

            ResultSet rs =
                    stmt.executeQuery();

            return rs.next();

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

}