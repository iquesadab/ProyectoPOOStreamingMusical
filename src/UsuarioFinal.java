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

        if (avatar == null || avatar.trim().isEmpty()) {
            this.avatar = "avatar_default.png";
        } else {
            this.avatar = avatar;
        }

        this.saldo = BONO_INICIAL;

        this.cancionesCompradas = new Cancion[cantidadMaximaCanciones];
        this.listasReproduccion = new ListaReproduccion[cantidadMaximaListas];

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
    public boolean cambiarContrasenia(String contraseniaActual, String nuevaContrasenia,  String confirmacionNuevaContrasenia) {
        // Verifica que la contraseña actual sea correcta.
        if (contrasenia.equals(contraseniaActual)) {
            System.out.println("La contraseña actual es incorrecta.");
            return false;
        }

        // Verifica que la nueva contraseña cumpla los requisitos.
        if (!Usuario.esContraseniaValida(nuevaContrasenia)) {
            System.out.println("La nueva contraseña no cumple los requisitos:" +
                    "\n- Debe tener entre 8 y 12 caracteres." +
                    "\n- Debe incluir al menos una letra mayúscula." +
                    "\n- Debe incluir al menos una letra minúscula." +
                    "\n- Debe incluir al menos un número." +
                    "\n- Debe incluir al menos un carácter especial.");
            return false;
        }

        // Verifica que la nueva contraseña sea diferente de la actual.
        if (nuevaContrasenia.equals(contraseniaActual)) {
            System.out.println("La nueva contraseña no puede ser igual a la actual.");
            return false;
        }

        // Verifica que la confirmación coincida con la nueva contraseña.
        if (!nuevaContrasenia.equals(confirmacionNuevaContrasenia)) {
            System.out.println("La confirmación de la nueva contraseña no coincide.");
            return false;
        }

        contrasenia = nuevaContrasenia;

        System.out.println("Contraseña actualizada correctamente.");
        return true;
    }

    // Método para verificar si el usuario ya compró una canción
    public boolean tieneCancionComprada(Cancion cancion) {

        for (int i = 0; i < cantidadCancionesCompradas; i++) {

            if (cancionesCompradas[i] == cancion) {
                return true;
            }
        }
        return false;
    }

    // Compra y gestión de música
    public void comprarCancion(Cancion cancion) {

        if (cancion == null) {
            System.out.println("La canción no existe.");
            return;
        }

        if (tieneCancionComprada(cancion)) {
            System.out.println("La canción ya fue comprada anteriormente.");
            return;
        }

        if (cantidadCancionesCompradas >= cancionesCompradas.length) {
            System.out.println("No se pueden comprar más canciones.");
            return;
        }

        if (saldo < cancion.getPrecio()) {
            System.out.println("Saldo insuficiente para comprar la canción.");
            return;
        }

        cancionesCompradas[cantidadCancionesCompradas] = cancion;
        cantidadCancionesCompradas++;

        saldo = saldo - cancion.getPrecio();

        // Se utiliza para generar el Top 3 de canciones más compradas.
        cancion.aumentarVecesComprada();

        System.out.println("Canción comprada correctamente.");
    }

    // Método para recargar el saldo del usuario
    public void recargarSaldo(float monto) {

        if (monto <= 0) {
            System.out.println("El monto de la recarga debe ser mayor que cero.");
            return;
        }

        saldo = saldo + monto;

        System.out.println("Recarga realizada correctamente.");
        System.out.println("Nuevo saldo: $" + saldo);
    }

    public void crearListaReproduccion(String nombre) {
        if (cantidadListasReproduccion >= listasReproduccion.length) {
            System.out.println("No se pueden crear más listas de reproducción.");
            return;
        }

        ListaReproduccion nuevaLista = new ListaReproduccion(nombre, LocalDate.now());
        listasReproduccion[cantidadListasReproduccion] = nuevaLista;
        cantidadListasReproduccion++;

        System.out.println("Lista de reproducción creada correctamente.");
    }

    // Método para calificar una canción comprada
    public void calificarCancion(Cancion cancion, float calificacion) {

        if (cancion == null) {
            System.out.println("La canción no existe.");
            return;
        }

        if (!tieneCancionComprada(cancion)) {
            System.out.println("Solo se pueden calificar canciones compradas.");
            return;
        }

        if (calificacion < 0.0f || calificacion > 5.0f) {
            System.out.println("La calificación debe estar entre 0.0 y 5.0.");
            return;
        }

        cancion.setCalificacion(calificacion);

        System.out.println("Canción calificada correctamente.");
    }

    // Método para verificar si una lista pertenece al usuario
    public boolean tieneListaReproduccion(ListaReproduccion listaReproduccion) {

        for (int i = 0; i < cantidadListasReproduccion; i++) {

            if (listasReproduccion[i] == listaReproduccion) {
                return true;
            }
        }

        return false;
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

        if (!tieneCancionComprada(cancion)) {
            System.out.println(
                    "La canción debe haber sido comprada antes de agregarla a una lista.");
            return;
        }

        if (!tieneListaReproduccion(listaReproduccion)) {
            System.out.println(
                    "La lista de reproducción no pertenece al usuario.");
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