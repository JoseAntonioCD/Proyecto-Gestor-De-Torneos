package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBD {


    private static Connection conn;


    public static Connection getConnection() {


        if(conn == null) {

            try {


                Class.forName(
                        "com.mysql.cj.jdbc.Driver"
                );

                conn =
                        DriverManager.getConnection(

                                "jdbc:mysql://localhost:3307/gestor_de_torneos",

                                "root",

                                "1029384756aSdW"
                        );

                System.out.println(
                        "Conexión establecida correctamente"
                );

            }

            catch(ClassNotFoundException e) {

                System.out.println(
                        "Driver MySQL no encontrado"
                );

                e.printStackTrace();
            }

            catch(SQLException e) {

                System.out.println(
                        "Error al conectar con MySQL"
                );

                e.printStackTrace();
            }
        }

        return conn;
    }
}