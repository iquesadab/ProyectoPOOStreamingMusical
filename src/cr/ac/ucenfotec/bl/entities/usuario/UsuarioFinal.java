package cr.ac.ucenfotec.bl.entities.usuario;

import cr.ac.ucenfotec.bl.entities.cancion.Cancion;
import cr.ac.ucenfotec.bl.entities.listaReproduccion.ListaReproduccion;

import java.time.LocalDate;
import java.time.Period;

public class UsuarioFinal extends Usuario {

    private static final float BONO_INICIAL = 4.99f;

    private String nombreCompleto;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private String cedula;
    private String avatar;
    private float saldo;

    private Cancion[] cancionesCompradas;
    private ListaReproduccion[] listasReproduccion;

    private int cantidadCancionesCompradas;
    private int cantidadListasReproduccion;

    public UsuarioFinal(
            String nombreCompleto,
            LocalDate fechaNacimiento,
            String nacionalidad,
            String cedula,
            String avatar,
            String correoElectronico,
            String nombreUsuario,
            String contrasenia,
            byte cantidadMaximaCanciones,
            byte cantidadMaximaListas) {

        this(
                0,
                nombreCompleto,
                fechaNacimiento,
                nacionalidad,
                cedula,
                avatar,
                BONO_INICIAL,
                correoElectronico,
                nombreUsuario,
                contrasenia,
                cantidadMaximaCanciones,
                cantidadMaximaListas
        );
    }

    public UsuarioFinal(
            int id,
            String nombreCompleto,
            LocalDate fechaNacimiento,
            String nacionalidad,
            String cedula,
            String avatar,
            float saldo,
            String correoElectronico,
            String nombreUsuario,
            String contrasenia,
            byte cantidadMaximaCanciones,
            byte cantidadMaximaListas) {

        super(
                id,
                correoElectronico,
                nombreUsuario,
                contrasenia
        );

        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.cedula = cedula;

        this.avatar =
                (avatar == null || avatar.trim().isEmpty())
                        ? "avatar_default.png"
                        : avatar;

        this.saldo = saldo;

        this.cancionesCompradas =
                new Cancion[cantidadMaximaCanciones];

        this.listasReproduccion =
                new ListaReproduccion[cantidadMaximaListas];

        this.cantidadCancionesCompradas = 0;
        this.cantidadListasReproduccion = 0;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getCedula() {
        return cedula;
    }

    public String getAvatar() {
        return avatar;
    }

    public float getSaldo() {
        return saldo;
    }

    public Cancion[] getCancionesCompradas() {
        return cancionesCompradas;
    }

    public ListaReproduccion[] getListasReproduccion() {
        return listasReproduccion;
    }

    public int getCantidadCancionesCompradas() {
        return cantidadCancionesCompradas;
    }

    public int getCantidadListasReproduccion() {
        return cantidadListasReproduccion;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public boolean esMayorDeEdad() {

        if (fechaNacimiento == null) {
            return false;
        }

        return Period.between(
                fechaNacimiento,
                LocalDate.now()
        ).getYears() >= 18;
    }

    public boolean tieneCancionComprada(
            Cancion cancion) {

        for (int i = 0;
             i < cantidadCancionesCompradas;
             i++) {

            Cancion comprada = cancionesCompradas[i];

            // Cuando los objetos vienen de MySQL pueden ser instancias
            // distintas, por eso se comparan por id y no solo por referencia.
            if (comprada != null && cancion != null &&
                    ((comprada.getId() > 0 && comprada.getId() == cancion.getId()) ||
                            comprada == cancion)) {
                return true;
            }
        }

        return false;
    }

    // Carga una canción que ya había sido comprada y está guardada en MySQL.
    // No descuenta saldo ni aumenta estadísticas porque no es una compra nueva.
    public void cargarCancionComprada(Cancion cancion) {

        if (cancion == null || tieneCancionComprada(cancion)) {
            return;
        }

        if (cantidadCancionesCompradas >= cancionesCompradas.length) {
            return;
        }

        cancionesCompradas[cantidadCancionesCompradas] = cancion;
        cantidadCancionesCompradas++;
    }

    public void comprarCancion(
            Cancion cancion) {

        if (cancion == null) {
            System.out.println(
                    "La canción no existe."
            );
            return;
        }

        if (tieneCancionComprada(cancion)) {
            System.out.println(
                    "La canción ya fue comprada anteriormente."
            );
            return;
        }

        if (cantidadCancionesCompradas
                >= cancionesCompradas.length) {

            System.out.println(
                    "No se pueden comprar más canciones."
            );
            return;
        }

        if (saldo < cancion.getPrecio()) {

            System.out.println(
                    "Saldo insuficiente para comprar la canción."
            );
            return;
        }

        cancionesCompradas[
                cantidadCancionesCompradas
                ] = cancion;

        cantidadCancionesCompradas++;

        saldo =
                saldo - cancion.getPrecio();

        cancion.aumentarVecesComprada();

        System.out.println(
                "Canción comprada correctamente."
        );
    }

    public void recargarSaldo(float monto) {

        if (monto <= 0) {

            System.out.println(
                    "El monto de la recarga debe ser mayor que cero."
            );

            return;
        }

        saldo = saldo + monto;

        System.out.println(
                "Recarga realizada correctamente."
        );

        System.out.println(
                "Nuevo saldo: $" + saldo
        );
    }

    public void crearListaReproduccion(
            String nombre) {

        if (nombre == null ||
                nombre.trim().isEmpty()) {

            System.out.println(
                    "El nombre de la lista no puede estar vacío."
            );

            return;
        }

        if (cantidadListasReproduccion
                >= listasReproduccion.length) {

            System.out.println(
                    "No se pueden crear más listas de reproducción."
            );

            return;
        }

        ListaReproduccion nuevaLista =
                new ListaReproduccion(
                        nombre,
                        LocalDate.now()
                );

        listasReproduccion[
                cantidadListasReproduccion
                ] = nuevaLista;

        cantidadListasReproduccion++;

        System.out.println(
                "Lista de reproducción creada correctamente."
        );
    }

    public void calificarCancion(
            Cancion cancion,
            float calificacion) {

        if (cancion == null) {

            System.out.println(
                    "La canción no existe."
            );

            return;
        }

        if (!tieneCancionComprada(cancion)) {

            System.out.println(
                    "Solo se pueden calificar canciones compradas."
            );

            return;
        }

        if (calificacion < 0.0f ||
                calificacion > 5.0f) {

            System.out.println(
                    "La calificación debe estar entre 0.0 y 5.0."
            );

            return;
        }

        cancion.agregarCalificacion(
                calificacion
        );

        System.out.println(
                "Canción calificada correctamente."
        );

        System.out.println(
                "Nueva calificación promedio: "
                        + cancion.getCalificacion()
        );
    }

    public boolean tieneListaReproduccion(
            ListaReproduccion listaReproduccion) {

        for (int i = 0;
             i < cantidadListasReproduccion;
             i++) {

            if (listasReproduccion[i]
                    == listaReproduccion) {

                return true;
            }
        }

        return false;
    }

    public void agregarCancionALista(
            Cancion cancion,
            ListaReproduccion listaReproduccion) {

        if (cancion == null) {

            System.out.println(
                    "La canción no existe."
            );

            return;
        }

        if (listaReproduccion == null) {

            System.out.println(
                    "La lista de reproducción no existe."
            );

            return;
        }

        if (!tieneCancionComprada(cancion)) {

            System.out.println(
                    "La canción debe haber sido comprada " +
                            "antes de agregarla a una lista."
            );

            return;
        }

        if (!tieneListaReproduccion(
                listaReproduccion)) {

            System.out.println(
                    "La lista de reproducción no pertenece al usuario."
            );

            return;
        }

        listaReproduccion.agregarCancion(
                cancion
        );
    }

    public void agregarCancionACola(
            Cancion cancion) {

        if (cancion == null) {

            System.out.println(
                    "La canción no existe."
            );

            return;
        }

        if (!tieneCancionComprada(cancion)) {

            System.out.println(
                    "Debe comprar la canción antes de agregarla a la cola."
            );

            return;
        }

        getColaReproduccion()
                .agregarCancion(cancion);
    }

    @Override
    public String toString() {

        return "Usuario: "
                + nombreCompleto
                + "\nNombre de usuario: "
                + getNombreUsuario()
                + "\nCorreo electrónico: "
                + getCorreoElectronico()
                + "\nNacionalidad: "
                + nacionalidad
                + "\nSaldo: "
                + saldo;
    }
}