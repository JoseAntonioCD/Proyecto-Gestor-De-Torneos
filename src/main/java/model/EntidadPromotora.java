package model;

public class EntidadPromotora extends Usuario {

    public EntidadPromotora() {

        super();
    }

    public EntidadPromotora(
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
                "PROMOTOR"
        );
    }
}