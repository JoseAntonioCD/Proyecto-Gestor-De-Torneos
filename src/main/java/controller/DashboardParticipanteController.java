package controller;

import DAO.EventoDAO;
import DAO.ParticipacionDAO;

import javafx.fxml.FXML;

import javafx.scene.control.ListView;

import model.Evento;

public class DashboardParticipanteController {

    /*
     * Lista de eventos participados
     */

    @FXML
    private ListView<Evento> listParticipados;

    /*
     * Lista de eventos activos
     */

    @FXML
    private ListView<Evento> listActivos;

    /*
     * DAOs
     */

    private EventoDAO eventoDAO;

    private ParticipacionDAO participacionDAO;

    /*
     * Se ejecuta automáticamente
     * al abrir el dashboard
     */

    public void initialize() {

        eventoDAO =
                new EventoDAO();

        participacionDAO =
                new ParticipacionDAO();

        cargarEventos();
    }

    /*
     * Carga información
     * en las listas visuales
     */

    private void cargarEventos() {

        /*
         * Eventos activos
         */

        listActivos
                .getItems()
                .addAll(

                        eventoDAO
                                .getEventosActivos()
                );

        /*
         * Eventos participados
         */

        listParticipados
                .getItems()
                .addAll(

                        participacionDAO
                                .getUltimosEventosParticipados(1)
                );
    }
}