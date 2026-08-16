package cr.ac.ucenfotec.dl;

import java.io.FileInputStream;
import java.util.Properties;

public class Connector {

    // Guarda una única instancia de la conexión a la base de datos
    private static DBAccess connection;

    // Obtiene la conexión a la base de datos
    public static DBAccess getConnection() throws Exception {

        // Si todavía no existe una conexión, se crea
        if (connection == null) {

            // Se crea un objeto Properties para leer el archivo db.properties
            Properties properties = new Properties();

            // Se abre el archivo que contiene los datos de conexión
            FileInputStream archivo =
                    new FileInputStream("src/cr/ac/ucenfotec/db.properties");

            // Se cargan las propiedades del archivo
            properties.load(archivo);

            // Se obtienen los datos guardados en db.properties
            String driver = properties.getProperty("driver");
            String server = properties.getProperty("server");
            String dataBase = properties.getProperty("dataBase");
            String usuario = properties.getProperty("user");
            String contrasenia = properties.getProperty("password");

            // Se construye la dirección completa de la base de datos
            String direccion = driver + "//" + server + "/" + dataBase;

            // Se crea la conexión utilizando DBAccess
            connection = new DBAccess(
                    direccion,
                    usuario,
                    contrasenia
            );

            // Se cierra el archivo db.properties
            archivo.close();
        }

        // Se devuelve la conexión existente
        return connection;
    }
}
