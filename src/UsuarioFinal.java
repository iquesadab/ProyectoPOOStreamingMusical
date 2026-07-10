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
    private ColaReproduccion colaReproduccion;

    private int cantidadCancionesCompradas;
    private int cantidadListasReproduccion;

    // Constructor
    public UsuarioFinal(String nombreCompleto, LocalDate fechaNacimiento, String nacionalidad,
                        String cedula, String avatar, String correoElectronico,
                        String nombreUsuario, String contrasenia,
                        byte cantidadMaximaCanciones, byte cantidadMaximaListas) {

        // Se llama al constructor de Usuario
        super(correoElectronico, nombreUsuario, contrasenia);

        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.cedula = cedula;
        this.avatar = avatar;
        this.saldo = BONO_INICIAL;

        this.cancionesCompradas = new Cancion[cantidadMaximaCanciones];
        this.listasReproduccion = new ListaReproduccion[cantidadMaximaListas];
        this.colaReproduccion = new ColaReproduccion(cantidadMaximaCanciones);

        this.cantidadCancionesCompradas = 0;
        this.cantidadListasReproduccion = 0;
    }

    // Getters y Setters
    public String getNombreCompleto()
    {return nombreCompleto; }

    public LocalDate getFechaNacimiento()
    { return fechaNacimiento; }

    public String getNacionalidad()
    { return nacionalidad; }

    public String getCedula()
    { return cedula; }

    public String getAvatar()
    { return avatar; }

    public float getSaldo()
    { return saldo; }

    public Cancion[] getCancionesCompradas()
    { return cancionesCompradas; }

    public ListaReproduccion[] getListasReproduccion()
    { return listasReproduccion; }

    public ColaReproduccion getColaReproduccion()
    { return colaReproduccion; }


    public void setNombreCompleto(String nombreCompleto)
    { this.nombreCompleto = nombreCompleto; }

    public void setFechaNacimiento(LocalDate fechaNacimiento)
    { this.fechaNacimiento = fechaNacimiento; }

    public void setNacionalidad(String nacionalidad)
    { this.nacionalidad = nacionalidad; }

    public void setCedula(String cedula)
    { this.cedula = cedula; }

    public void setAvatar(String avatar)
    { this.avatar = avatar; }

    public void setSaldo(float saldo)
    { this.saldo = saldo; }

    // Validar mayoría de edad
    public boolean esMayorDeEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears() >= 18;
    }

    // Cambio de contraseña usando la validación de la clase padre
    public boolean cambiarContrasenia(String contraseniaActual, String nuevaContrasenia) {
        if (!this.contrasenia.equals(contraseniaActual)) {
            System.out.println("La contraseña actual es incorrecta.");
            return false;
        }

        if (!Usuario.esContraseniaValida(nuevaContrasenia)) {
            System.out.println("La nueva contraseña no cumple los requisitos:" +
                    "\n- Mínimo 8 caracteres." +
                    "\n- Al menos una mayúscula." +
                    "\n- Al menos un número." +
                    "\n- Al menos un carácter especial.");
            return false;
        }

        if (nuevaContrasenia.equals(contraseniaActual)) {
            System.out.println("La nueva contraseña no puede ser igual a la actual.");
            return false;
        }

        this.contrasenia = nuevaContrasenia;
        System.out.println("Contraseña actualizada correctamente.");
        return true;
    }

    // Compra y gestión de música
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

    @Override
    public String toString() {
        return "Usuario: " + nombreCompleto +
                "\nNombre de usuario: " + nombreUsuario +
                "\nCorreo electrónico: " + correoElectronico +
                "\nNacionalidad: " + nacionalidad +
                "\nSaldo: " + saldo;
    }
}