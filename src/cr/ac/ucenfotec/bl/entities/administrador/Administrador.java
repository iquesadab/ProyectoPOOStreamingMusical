package cr.ac.ucenfotec.bl.entities.administrador;

import cr.ac.ucenfotec.bl.entities.cancion.Cancion;
import cr.ac.ucenfotec.bl.entities.usuario.Usuario;

public class Administrador extends Usuario {

    public Administrador(
            String correoElectronico,
            String nombreUsuario,
            String contrasenia) {

        super(
                correoElectronico,
                nombreUsuario,
                contrasenia
        );
    }

    public Administrador(
            int id,
            String correoElectronico,
            String nombreUsuario,
            String contrasenia) {

        super(
                id,
                correoElectronico,
                nombreUsuario,
                contrasenia
        );
    }

    public void registrarCancion(Cancion cancion) {

        if (cancion != null) {

            System.out.println(
                    "El administrador "
                            + getNombreUsuario()
                            + " registró la canción "
                            + cancion.getNombre()
            );
        }
    }

    public void agregarCancionACola(Cancion cancion) {

        if (cancion != null) {

            getColaReproduccion()
                    .agregarCancion(cancion);

            System.out.println(
                    "La canción "
                            + cancion.getNombre()
                            + " fue agregada a la cola de reproducción."
            );
        }
    }

    @Override
    public String toString() {

        return "Administrador{" +
                "id=" + getId() +
                ", correoElectronico='" +
                getCorreoElectronico() + '\'' +
                ", nombreUsuario='" +
                getNombreUsuario() + '\'' +
                '}';
    }
}