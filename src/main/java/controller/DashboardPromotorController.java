package controller;

import DAO.EventoDAO;

import javafx.fxml.FXML;

import javafx.scene.control.ListView;

import model.Evento;

public class DashboardPromotorController {


    @FXML
    private ListView<Evento> listTodos;



    @FXML
    private ListView<Evento> listActivos;


    private EventoDAO eventoDAO;



    public void initialize() {

        eventoDAO =
                new EventoDAO();

        cargarEventos();
    }



    private void cargarEventos() {

        /*
         * Todos los eventos
         */

        listTodos
                .getItems()
                .addAll(

                        eventoDAO
                                .getEventosPromotor(1)
                );



        listActivos
                .getItems()
                .addAll(

                        eventoDAO
                                .getEventosActivosPromotor(1)
                );
    }
}