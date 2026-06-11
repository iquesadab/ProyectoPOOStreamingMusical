import java.time.LocalDate;

public class UsuarioFinal {

    private String nombreCompleto;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private String cedula;
    private String avatar;
    private String correoElectronico;
    private String nombreUsuario;
    private String contrasena;
    private float saldo;

    private Cancion[] cancionesCompradas;
    private ListaReproduccion[] listasReproduccion;
    private ColaReproduccion colaReproduccion;

    private int cantidadCancionesCompradas;
    private int cantidadListasReproduccion;

    public UsuarioFinal(String nombreCompleto, LocalDate fechaNacimiento, String nacionalidad,
                        String cedula, String avatar, String correoElectronico,
                        String nombreUsuario, String contrasena, float saldo,
                        byte cantidadMaximaCanciones, byte cantidadMaximaListas) {

        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.cedula = cedula;
        this.avatar = avatar;
        this.correoElectronico = correoElectronico;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.saldo = saldo;

        this.cancionesCompradas = new Cancion[cantidadMaximaCanciones];
        this.listasReproduccion = new ListaReproduccion[cantidadMaximaListas];
        this.colaReproduccion = new ColaReproduccion(cantidadMaximaCanciones);

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

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
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

    public ColaReproduccion getColaReproduccion() {
        return colaReproduccion;
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

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public void comprarCancion(Cancion cancion) {
        if (cancion == null) {
            System.out.println("La canción no existe.");
            return;
        }

        if (cantidadCancionesCompradas >= cancionesCompradas.length) {
            System.out.println("No se pueden comprar más canciones.");
            return;
        }

        if (saldo >= cancion.getPrecio()) {
            cancionesCompradas[cantidadCancionesCompradas] = cancion;
            cantidadCancionesCompradas++;

            saldo = saldo - cancion.getPrecio();

            System.out.println("Canción comprada correctamente.");
        } else {
            System.out.println("Saldo insuficiente para comprar la canción.");
        }
    }

    public void crearListaReproduccion(String nombre) {
        if (cantidadListasReproduccion >= listasReproduccion.length) {
            System.out.println("No se pueden crear más listas de reproducción.");
            return;
        }

        ListaReproduccion nuevaLista = new ListaReproduccion(nombre, LocalDate.now(), 0, (byte) 10);

        listasReproduccion[cantidadListasReproduccion] = nuevaLista;
        cantidadListasReproduccion++;

        System.out.println("Lista de reproducción creada correctamente.");
    }

    public void agregarCancionALista(Cancion cancion, ListaReproduccion listaReproduccion) {
        if (cancion == null) {
            System.out.println("La canción no existe.");
            return;
        }

        if (listaReproduccion == null) {
            System.out.println("La lista de reproducción no existe.");
            return;
        }

        listaReproduccion.agregarCancion(cancion);
    }

    public void agregarCancionACola(Cancion cancion) {
        if (cancion == null) {
            System.out.println("La canción no existe.");
            return;
        }

        colaReproduccion.agregarCancion(cancion);
    }

    public String toString() {
        return "Usuario: " + nombreCompleto +
                "\nNombre de usuario: " + nombreUsuario +
                "\nCorreo electrónico: " + correoElectronico +
                "\nNacionalidad: " + nacionalidad +
                "\nSaldo: " + saldo;
    }
}