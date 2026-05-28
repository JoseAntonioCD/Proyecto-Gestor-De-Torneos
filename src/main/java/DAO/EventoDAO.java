package DAO;

import dataAccess.ConnectionBD;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {

    private Connection conn;

    private UsuarioDAO usuarioDAO;

    public EventoDAO() {

        conn = ConnectionBD.getConnection();

        usuarioDAO = new UsuarioDAO();
    }

    /**
     * Método que recibe los eventos activos desde la BD
     * @return Devuelve los eventos activos disponibles
     */

    public List<Evento> getEventosActivos() {

        List<Evento> eventos =
                new ArrayList<>();

        String sql = """
        SELECT *
        FROM eventos
        WHERE estado = 'ACTIVO'
        ORDER BY fechaEvento
        """;

        try(
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            ResultSet rs =
                    stmt.executeQuery();

            while(rs.next()) {

                eventos.add(
                        construirEvento(rs)
                );
            }

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return eventos;
    }

    /**
     * Método que recibe los eventos inactivos de la BD
     * @return Devuelve los eventos inactivos disponibles
     */
    public List<Evento> getEventosInactivos() {

        List<Evento> eventos =
                new ArrayList<>();

        String sql = """
        SELECT *
        FROM eventos
        WHERE estado = 'INACTIVO'
        ORDER BY fechaEvento
        """;

        try(
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            ResultSet rs =
                    stmt.executeQuery();

            while(rs.next()) {

                eventos.add(
                        construirEvento(rs)
                );
            }

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return eventos;
    }

    /**
     * Método que recibe los eventos creados por un promotor
     * @param idEntidad Identificador de la entidad promotora creadora del evento
     * @return Eventos del promotor
     */

    public List<Evento> getEventosPromotor(
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

            stmt.setInt(1, idEntidad);

            ResultSet rs =
                    stmt.executeQuery();

            while(rs.next()) {

                eventos.add(
                        construirEvento(rs)
                );
            }

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return eventos;
    }

    /**
     * Método que maneja la inserción de un evento creado en la BD
     * @param evento Evento creado
     * @param idEntidad Identificador de una entidad Promotora
     * @return Si el proceso ha sido exitoso o no
     */

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
                    evento.getEstado().name()
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

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    /**
     * Método con el que actualizamos los datos de un evento
     * @param evento evento a editar
     * @param idEntidad identificador del promotor responsable
     * @return si el proceso ha sido exitoso o no
     */

    public boolean actualizarEvento(
            Evento evento,
            int idEntidad
    ) {

        String sql = """
        UPDATE eventos
        SET nombre = ?,
            descripcion = ?,
            fechaEvento = ?,
            estado = ?
        WHERE idEvento = ?
        AND idEntidad = ?
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

            stmt.setDate(
                    3,
                    Date.valueOf(
                            evento.getFechaEvento()
                    )
            );

            stmt.setString(
                    4,
                    evento.getEstado().name()
            );

            stmt.setInt(
                    5,
                    evento.getIdEvento()
            );

            stmt.setInt(
                    6,
                    idEntidad
            );

            return stmt.executeUpdate() > 0;

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    /**
     * Método que maneja el proceso de apuntarse a un evento
     * @param idUsuario identificador de usuario participante
     * @param idEvento identificador de evento a participar
     * @return si el proceso ha sido exitoso o no
     */

    public boolean apuntarseEvento(
            int idUsuario,
            int idEvento
    ) {

        String sql = """
                INSERT INTO participaciones(
                    idUsuario,
                    idEvento
                )
                VALUES(?,?)
                """;

        try(
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, idUsuario);

            stmt.setInt(2, idEvento);

            stmt.executeUpdate();

            return true;

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    /**
     * Método que maneja el proceso de despuntarse de un evento
     * @param idUsuario id de usuario a desapuntar
     * @param idEvento id evento a modificar
     * @return si el proceso ha sido exitoso o no
     */

    public boolean desapuntarseEvento(
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

            stmt.executeUpdate();

            return true;

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    /**
     * Método que comprueba si el usuario actual participa en el evento seleccionado
     * @param idUsuario id usuario a comprobar su participación
     * @param idEvento id evento del que comprobar si participa el usuario
     * @return si el proceso ha sido exitoso o no
     */

    public boolean participa(
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

    /**
     * Método que devuelve todos los participantes de un evento
     * @param idEvento id de evento a comprobar participantes
     * @return si el proceso ha sido exitoso o no
     */

    public List<Usuario> getParticipantesEvento(
            int idEvento
    ) {

        List<Usuario> participantes =
                new ArrayList<>();

        String sql = """
            SELECT idUsuario
            FROM participaciones
            WHERE idEvento = ?
            """;

        try(
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, idEvento);

            ResultSet rs =
                    stmt.executeQuery();

            while(rs.next()) {

                int idUsuario =
                        rs.getInt("idUsuario");

                Usuario usuario =
                        usuarioDAO.getUsuarioById(
                                idUsuario
                        );

                if(usuario != null) {

                    participantes.add(usuario);
                }
            }

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return participantes;
    }

    /**
     * Método que recibe un usuario y comprueba los eventos en los que está participando
     * @param idUsuario id usuario a comprobar sus eventos en los que participa
     * @return si el proceso ha sido exitoso
     */

    public List<Evento> getEventosParticipados(
            int idUsuario
    ) {

        List<Evento> eventos =
                new ArrayList<>();

        String sql = """
            SELECT idEvento
            FROM participaciones
            WHERE idUsuario = ?
            """;

        try(
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, idUsuario);

            ResultSet rs =
                    stmt.executeQuery();

            while(rs.next()) {

                int idEvento =
                        rs.getInt("idEvento");

                Evento evento =
                        getEventoById(idEvento);

                if(evento != null) {

                    eventos.add(evento);
                }
            }

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return eventos;
    }

    /**
     * Recibe un evento basándose en su id
     * @param idEvento id de evento a recibir de la bd
     * @return Si el proceso ha sido exitoso o no
     */

    public Evento getEventoById(
            int idEvento
    ) {

        String sql = """
            SELECT *
            FROM eventos
            WHERE idEvento = ?
            """;

        try(
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, idEvento);

            ResultSet rs =
                    stmt.executeQuery();

            if(rs.next()) {

                return construirEvento(rs);
            }

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    /**
     * Método con el que se borra un evento basándose en su id
     * @param idEvento id evento a borrar
     * @return si el proceso ha sido exitoso o no
     */

    public boolean borrarEvento(
            int idEvento
    ) {

        String borrarParticipaciones = """
            DELETE FROM participaciones
            WHERE idEvento = ?
            """;

        String borrarEvento = """
            DELETE FROM eventos
            WHERE idEvento = ?
            """;

        try {

            PreparedStatement stmt1 =
                    conn.prepareStatement(
                            borrarParticipaciones
                    );

            stmt1.setInt(1, idEvento);

            stmt1.executeUpdate();

            PreparedStatement stmt2 =
                    conn.prepareStatement(
                            borrarEvento
                    );

            stmt2.setInt(1, idEvento);

            return stmt2.executeUpdate() > 0;

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    /**
     * Método que es usado para montar un evento con los datos revibidos
     * @param rs Resultado que setear
     * @return Evento construido
     * @throws SQLException
     */

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

        evento.setFechaEvento(
                rs.getDate("fechaEvento").toLocalDate()
        );
        evento.setEstado(

                EstadoEvento.valueOf(
                        rs.getString("estado")
                )
        );

        int idEntidad =
                rs.getInt("idEntidad");

        EntidadPromotora entidad =
                obtenerEntidadPromotora(idEntidad);

        evento.setEntidad(
                entidad
        );

        return evento;
    }

    /**
     * Método con el que obtenemos un promotor basándose en su id
     * @param idEntidad id de promotor a recibir
     * @return la entidad
     */

    private EntidadPromotora obtenerEntidadPromotora(
            int idEntidad
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

            stmt.setInt(1, idEntidad);

            ResultSet rs =
                    stmt.executeQuery();

            if(rs.next()) {

                EntidadPromotora entidad =
                        new EntidadPromotora();

                entidad.setId(
                        rs.getInt("id")
                );

                entidad.setNombre(
                        rs.getString("nombre")
                );

                entidad.setEmail(
                        rs.getString("email")
                );

                return entidad;
            }

        } catch(SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

}