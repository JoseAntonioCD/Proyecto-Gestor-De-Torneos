package DAO;

import model.Premio;
import java.sql.*;
import java.util.*;

public class PremioDAO implements DAOGenerico<Premio> {

    private Connection conn;

    public PremioDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void create(Premio premio) {

    }

    @Override
    public Premio getById(int id) {
        return null;
    }

    @Override
    public List<Premio> getAll() {
        List<Premio> lista = new ArrayList<>();
        String sql = "SELECT * FROM premios";

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                lista.add(new Premio(
                        rs.getInt("idPremio"),
                        rs.getString("nombrePremio"),
                        rs.getInt("nivelMinimo")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public void update(Premio premio) {

    }

    @Override
    public void delete(int id) {

    }
}
