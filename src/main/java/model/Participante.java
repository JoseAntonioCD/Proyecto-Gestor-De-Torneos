package model;

public class Participante extends Usuario {

    public Participante() {

        super();
    }

    public Participante(
            int id,
            String nombre,
            String email,
            String password
    ) {

        super(
                id,
                nombre,
                email,
                password,
                "PARTICIPANTE"
        );
    }
}