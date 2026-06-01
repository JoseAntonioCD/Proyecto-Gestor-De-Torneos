package util;

import model.Usuario;

public class ManejoSesion {

    private static Usuario usuarioActual;

    public static void login(
            Usuario usuario
    ) {

        usuarioActual = usuario;
    }

    public static void logout() {

        usuarioActual = null;
    }

    public static Usuario getUsuarioActual() {

        return usuarioActual;
    }
}