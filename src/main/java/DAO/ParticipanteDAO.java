package DAO;

import model.Participante;
import java.sql.*;
import java.util.*;

public class ParticipanteDAO implements DAOGenerico<Participante> {

    private Connection conn;

    public ParticipanteDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void create(Participante p) {
        String sql = "INSERT INTO participantes (nombre, email, telefono) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getEmail());
            stmt.setString(3, p.getTelefono());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Participante getById(int id) {
        String sql = "SELECT * FROM participantes WHERE idParticipante = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Participante(
                        rs.getInt("idParticipante"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        "",
                        rs.getString("telefono")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Participante> getAll() {
        List<Participante> lista = new ArrayList<>();
        String sql = "SELECT * FROM participantes";

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                lista.add(new Participante(
                        rs.getInt("idParticipante"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        "",
                        rs.getString("telefono")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public void update(Participante p) {}

    @Override
    public void delete(int id) {}
}