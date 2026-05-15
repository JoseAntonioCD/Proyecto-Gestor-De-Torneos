package DAO;

import dataAccess.ConnectionBD;
import model.Evento;

import dataAccess.ConnectionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class ParticipacionDAO {

    /*
     * Conexión MySQL
     */

    private Connection conn;

    /*
     * Constructor
     */

    public ParticipacionDAO() {

        conn =
                ConnectionBD.getConnection();
    }

    /*
     * PARTICIPAR EN EVENTO
     *
     * Inserta relación N:M
     * entre participante y evento
     */

    public boolean participar(
            int idParticipante,
            int idEvento
    ) {

        String sql = """
                INSERT INTO participacion(
                    idParticipante,
                    idEvento
                )
                VALUES(?,?)
                """;

        try(

                PreparedStatement stmt =
                        conn.prepareStatement(sql)

        ) {

            stmt.setInt(
                    1,
                    idParticipante
            );

            stmt.setInt(
                    2,
                    idEvento
            );

            stmt.executeUpdate();

            return true;

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    /*
     * Obtiene los últimos
     * eventos participados
     */

    public List<Evento>
    getUltimosEventosParticipados(
            int idParticipante
    ) {

        List<Evento> eventos =
                new ArrayList<>();

        String sql = """
                SELECT e.*
                FROM eventos e
                JOIN participacion p
                ON e.idEvento = p.idEvento
                WHERE p.idParticipante = ?
                ORDER BY e.fechaEvento DESC
                LIMIT 3
                """;

        try(

                PreparedStatement stmt =
                        conn.prepareStatement(sql)

        ) {

            stmt.setInt(
                    1,
                    idParticipante
            );

            ResultSet rs =
                    stmt.executeQuery();

            while(rs.next()) {

                Evento evento =
                        new Evento();

                evento.setIdEvento(
                        rs.getInt("idEvento")
                );

                evento.setNombre(
                        rs.getString("nombre")
                );

                evento.setDescripcion(
                        rs.getString("descripcion")
                );

                evento.setEstado(
                        rs.getString("estado")
                );

                evento.setFechaEvento(
                        rs.getDate("fechaEvento")
                                .toLocalDate()
                );

                eventos.add(evento);
            }

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return eventos;
    }

    /*
     * Historial completo
     */

    public List<Evento>
    getHistorialCompleto(
            int idParticipante
    ) {

        List<Evento> eventos =
                new ArrayList<>();

        String sql = """
                SELECT e.*
                FROM eventos e
                JOIN participacion p
                ON e.idEvento = p.idEvento
                WHERE p.idParticipante = ?
                ORDER BY e.fechaEvento DESC
                """;

        try(

                PreparedStatement stmt =
                        conn.prepareStatement(sql)

        ) {

            stmt.setInt(
                    1,
                    idParticipante
            );

            ResultSet rs =
                    stmt.executeQuery();

            while(rs.next()) {

                Evento evento =
                        new Evento();

                evento.setIdEvento(
                        rs.getInt("idEvento")
                );

                evento.setNombre(
                        rs.getString("nombre")
                );

                evento.setDescripcion(
                        rs.getString("descripcion")
                );

                evento.setEstado(
                        rs.getString("estado")
                );

                evento.setFechaEvento(
                        rs.getDate("fechaEvento")
                                .toLocalDate()
                );

                eventos.add(evento);
            }

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return eventos;
    }
}
