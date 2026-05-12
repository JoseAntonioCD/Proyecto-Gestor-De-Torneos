package DAO;

import model.Evento;
import java.sql.*;
import java.util.*;

public class EventoDAO implements DAOGenerico<Evento> {

    private Connection conn;

    public EventoDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void create(Evento evento) {

    }

    @Override
    public Evento getById(int id) {
        return null;
    }

    @Override
    public List<Evento> getAll() {
        List<Evento> lista = new ArrayList<>();
        String sql = "SELECT * FROM eventos";

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                lista.add(new Evento(
                        rs.getInt("idEvento"),
                        rs.getDate("fechaEvento").toLocalDate(),
                        null,
                        null
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public void update(Evento evento) {

    }

    @Override
    public void delete(int id) {

    }

    public List<Evento> getEventosActivosLimit(int limit) {
        List<Evento> lista = new ArrayList<>();
        String sql = "SELECT * FROM eventos WHERE fechaEvento >= CURDATE() LIMIT ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new Evento(
                        rs.getInt("idEvento"),
                        rs.getDate("fechaEvento").toLocalDate(),
                        null,
                        null
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}