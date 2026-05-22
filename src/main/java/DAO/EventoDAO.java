package DAO;

import dataAccess.ConnectionBD;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {

    private Connection conn;

    public EventoDAO() {

        conn = ConnectionBD.getConnection();
    }

    public List<Evento> getEventosActivos() {

        List<Evento> eventos =
                new ArrayList<>();

        String sql = """
                SELECT *
                FROM eventos
                WHERE estado = 'ACTIVO'
                LIMIT 3
                """;

        try(PreparedStatement stmt =
                    conn.prepareStatement(sql)) {

            ResultSet rs =
                    stmt.executeQuery();

            while(rs.next()) {

                Evento evento =
                        construirEvento(rs);

                eventos.add(evento);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return eventos;
    }

    public List<Evento> getEventosRecientes() {

        List<Evento> eventos =
                new ArrayList<>();

        String sql = """
                SELECT *
                FROM eventos
                ORDER BY fechaEvento DESC
                LIMIT 3
                """;

        try(PreparedStatement stmt =
                    conn.prepareStatement(sql)) {

            ResultSet rs =
                    stmt.executeQuery();

            while(rs.next()) {

                Evento evento =
                        construirEvento(rs);

                eventos.add(evento);
            }

        } catch(SQLException e) {
            System.out.println(
                    e.getMessage()
            );

            e.printStackTrace();
        }

        return eventos;
    }

    private Evento construirEvento(
            ResultSet rs
    ) throws SQLException {

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

        return evento;
    }

    public boolean insertarEvento(
            Evento evento,
            int idEntidad
    ) {

        String sql = """
        INSERT INTO eventos(
            nombre,
            descripcion,
            estado,
            fechaEvento,
            idEntidad
        )
        VALUES(?,?,?,?,?)
        """;

        try(

                PreparedStatement stmt =
                        conn.prepareStatement(sql)

        ) {

            stmt.setString(
                    1,
                    evento.getNombre()
            );

            stmt.setString(
                    2,
                    evento.getDescripcion()
            );

            stmt.setString(
                    3,
                    evento.getEstado()
            );

            stmt.setDate(
                    4,
                    Date.valueOf(
                            evento.getFechaEvento()
                    )
            );

            stmt.setInt(
                    5,
                    idEntidad
            );

            stmt.executeUpdate();

            return true;

        }

        catch(SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    public boolean actualizarEvento(Evento evento) {

        String sql = """
            UPDATE eventos
            SET nombre=?,
                descripcion=?,
                estado=?,
                fechaEvento=?
            WHERE idEvento=?
            """;

        try(PreparedStatement stmt =
                    conn.prepareStatement(sql)) {

            stmt.setString(1, evento.getNombre());
            stmt.setString(2, evento.getDescripcion());
            stmt.setString(3, evento.getEstado());

            stmt.setDate(
                    4,
                    Date.valueOf(evento.getFechaEvento())
            );

            stmt.setInt(5, evento.getIdEvento());

            stmt.executeUpdate();

            return true;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean eliminarEvento(int idEvento) {

        String sql = "DELETE FROM eventos WHERE idEvento=?";

        try(PreparedStatement stmt =
                    conn.prepareStatement(sql)) {

            stmt.setInt(1, idEvento);

            stmt.executeUpdate();

            return true;

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Evento>
    getEventosPromotor(
            int idEntidad
    ) {

        List<Evento> eventos =
                new ArrayList<>();

        String sql = """
            SELECT *
            FROM eventos
            WHERE idEntidad = ?
            ORDER BY fechaEvento DESC
            """;

        try(

                PreparedStatement stmt =
                        conn.prepareStatement(sql)

        ) {

            stmt.setInt(
                    1,
                    idEntidad
            );

            ResultSet rs =
                    stmt.executeQuery();

            while(rs.next()) {

                Evento evento =
                        construirEvento(rs);

                eventos.add(evento);
            }

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return eventos;
    }

    public List<Evento>
    getEventosActivosPromotor(
            int idEntidad
    ) {

        List<Evento> eventos =
                new ArrayList<>();

        String sql = """
            SELECT *
            FROM eventos
            WHERE idEntidad = ?
            AND estado = 'ACTIVO'
            LIMIT 3
            """;

        try(

                PreparedStatement stmt =
                        conn.prepareStatement(sql)

        ) {

            stmt.setInt(
                    1,
                    idEntidad
            );

            ResultSet rs =
                    stmt.executeQuery();

            while(rs.next()) {

                Evento evento =
                        construirEvento(rs);

                eventos.add(evento);
            }

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return eventos;
    }

}