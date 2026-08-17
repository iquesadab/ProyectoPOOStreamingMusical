package cr.ac.ucenfotec;

import cr.ac.ucenfotec.ui.Menu;

public class Main {

    public static void main(String[] args) {

        // Main solamente inicia la interfaz.
        // El resto del flujo se maneja mediante MVC:
        // Menu -> Controller -> Gestores -> DAO -> MySQL.
        Menu menu = new Menu();

        try {
            menu.iniciar();
        } catch (Exception e) {
            System.out.println("Ocurrió un error al iniciar la aplicación: " + e.getMessage());
        }
    }
}