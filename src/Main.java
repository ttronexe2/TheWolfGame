import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    enum Rol {
        HOMBRE_LOBO, ALDEANO,VIDENTE
    }

    // VIDENTE
    public static void faseVidente(List<Jugador> jugadores) {
        Jugador vidente = encontrarVidente(jugadores);
        if (vidente != null) {
            System.out.println("La vidente es: " + vidente.nombre);
            System.out.println("Se despierta la vidente");
            System.out.println(vidente.nombre + ", elige a un jugador para revelar su rol:");
            for (int i = 0; i < jugadores.size(); i++) {
                if (!jugadores.get(i).nombre.equals(vidente.nombre)) {
                    System.out.println((i + 1) + ". " + jugadores.get(i).nombre);
                }
            }
            revelarRolJugador(jugadores, vidente.nombre);
        } else {
            System.out.println("No hay una vidente en este juego.");
        }
    }

    private static Jugador encontrarVidente(List<Jugador> jugadores) {
        for (Jugador jugador : jugadores) {
            if (jugador.rol == Rol.VIDENTE) {
                return jugador;
            }
        }
        return null;
    }

    private static void revelarRolJugador(List<Jugador> jugadores, String nombreVidente) {

        System.out.print("Selecciona el número del jugador: ");
        int eleccion = leerInt(" ");
        Jugador elegido = jugadores.get(eleccion - 1);

        if (!elegido.nombre.equals(nombreVidente)) {
            System.out.println("El rol de " + elegido.nombre + " es: " + elegido.rol);
        } else {
            System.out.println("La vidente no puede elegirse a sí misma. Por favor, elige a otro jugador.");
        }
    }

    // VIDENTE FIN

    //HOMBRES LOBO
    public static void faseHombresLobo(List<Jugador> jugadores) {
        List<Jugador> hombresLobo = encontrarHombresLobo(jugadores);
            System.out.println("Estos son los hombres lobo:");
            for (Jugador hombrLobo : hombresLobo) {
                System.out.println(hombrLobo.nombre);
            }
            System.out.println("Los hombres lobo se despiertan. Elegir a vuestra víctima:");
            for (int i = 0; i < jugadores.size(); i++) {
                if (!jugadores.get(i).rol.equals(Rol.HOMBRE_LOBO)) {
                    System.out.println((i + 1) + ". " + jugadores.get(i).nombre);
                }
            }
            Jugador victima = elegirVictima(jugadores);
            System.out.println("Los hombres lobo han elegido a " + victima.nombre + " como su víctima.");
            eliminarJugador(jugadores, victima.nombre);


    }

    private static List<Jugador> encontrarHombresLobo(List<Jugador> jugadores) {
        List<Jugador> hombresLobo = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            if (jugador.rol == Rol.HOMBRE_LOBO) {
                hombresLobo.add(jugador);
            }
        }
        return hombresLobo;
    }
    // HOMBRES LOBO FIN
    private static Jugador elegirVictima(List<Jugador> jugadores) {
        System.out.print("Selecciona el número de la víctima: ");
        int eleccion = leerInt("");
        return jugadores.get(eleccion - 1);
    }

    public static void faseDia(List<Jugador> jugadores) {
        System.out.println("Buenos dias, os notifico que...");
        System.out.println(LastDeath);
        printarJugadores(jugadores);
        int option = leerInt("¿Desean eliminar a alguien hoy? Si es así, pulsar 1. Si no, introduzca 0 para no eliminar a nadie: ");
        printarJugadores(jugadores);
        if (option == 0){
            System.out.println("Skipeando...");
        }
        else if (option == 1){
            Jugador victima = elegirVictima(jugadores);
            System.out.println("Habeis " + victima.nombre + " como vuestra víctima.");
            System.out.println("Su rol era " + victima.rol);
            eliminarJugador(jugadores, victima.nombre);
        }
    }

    private static void faseDormir() {
        System.out.println(" ");
        System.out.println("Se hace de noche, la aldea duerme, los jugadores cierran los ojos");
    }

    public static void printarJugadores(List<Jugador> jugadores){
        System.out.println("Lista de Jugadores:");
        int i = 0;
        for (Jugador jugador : jugadores) {
            System.out.print("-"+ (i + 1) + " "+ jugador.getNombre() + " ");
            i++;
        }
        System.out.println(" ");
    }



    static class Jugador {
        String nombre;
        Rol rol;

        Jugador(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre(){
            return this.nombre;
        }

        public void setRol(Rol rol) {
            this.rol = rol;
        }
    }

    private static List<Jugador> configurarJugadores() {
        List<Jugador> jugadores = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            System.out.print("Introduzca el nombre del jugador " + (i + 1) + ": ");
            String nombre = leerString("");
            jugadores.add(new Jugador(nombre));
        }
        return jugadores;
    }

    private static String LastDeath = "";
    private static Integer deathLobo = 0;
    private static Integer deathInocent = 0;

    public static void eliminarJugador(List<Jugador> jugadores, String nombreJugador) {
        Jugador jugadorAEliminar = jugadores.stream()
                .filter(jugador -> jugador.nombre.equals(nombreJugador))
                .findFirst()
                .orElse(null);

        if (jugadorAEliminar != null) {
            if (jugadorAEliminar.rol == Rol.HOMBRE_LOBO) {
                deathLobo++;
            }
            else {
                deathInocent++;
            }
            jugadores.remove(jugadorAEliminar);
            System.out.println(nombreJugador + " ha sido eliminado de la partida.");
        }
        String JugadorElim = nombreJugador + " ha sido eliminado de la partida.";
        LastDeath = JugadorElim;
    }

    public static void asignarRoles(List<Jugador> jugadores) {
        Collections.shuffle(jugadores);
        int totalJugadores = jugadores.size();
        int hombresLobo = totalJugadores / 3;

        ArrayList<Rol> roles = new ArrayList<>();
        roles.add(Rol.VIDENTE);
        for (int i = 0; i < hombresLobo; i++) {
            roles.add(Rol.HOMBRE_LOBO);
        }
        while (roles.size() < totalJugadores) {
            roles.add(Rol.ALDEANO);
        }

        Collections.shuffle(roles);

        for (int i = 0; i < totalJugadores; i++) {
            jugadores.get(i).setRol(roles.get(i));
        }
    }

    public static String leerString(String mensaje) {
        Scanner input = new Scanner(System.in);
        String entrada;

        while (true) {
            System.out.print(mensaje);
            entrada = input.nextLine();
            if (!entrada.isEmpty()) {
                return entrada;
            } else {
                System.out.println("Por favor, no deje el campo vacío.");
            }
        }
    }

    public static int leerInt(String mensaje) {
        Scanner input = new Scanner(System.in);
        System.out.print(mensaje);
        while (!input.hasNextInt()) {
            System.out.println("Entrada no válida. Por favor, introduzca un número.");
            input.next();
            System.out.print(mensaje);
        }
        return input.nextInt();
    }

    public static boolean leerBoolean(String mensaje) {
        Scanner input = new Scanner(System.in);
        System.out.print(mensaje + " (true/false): ");
        while (!input.hasNextBoolean()) {
            System.out.println("Entrada no válida. Por favor, ingrese 'true' o 'false'.");
            input.next();
            System.out.print(mensaje + " (true/false): ");
        }
        return input.nextBoolean();
    }

    public static void iniciarJuego() {
        List<Jugador> jugadores = configurarJugadores();
        asignarRoles(jugadores);
        printarJugadores(jugadores);
        boolean finalJuego = false;
        do {
            faseDormir();
            faseVidente(jugadores);
            faseHombresLobo(jugadores);
            faseDia(jugadores);
            finalJuego = finJuego();
        }while (!finalJuego);

    }

    public static boolean finJuego(){
        boolean fin = false;
        if (deathLobo == 2){
            System.out.println("¡Todos los lobos han sido eliminados, los Aldeanos ganan!");
            System.out.println("Fin de la partida...");
            fin = true;
            return fin;
        }
        else if (deathInocent == 8){
            System.out.println("¡Todos los aldeanos han sido eliminados, los Lobos ganan!");
            System.out.println("Fin de la partida...");
            fin = true;
        }
        return fin;
    }

    public static void main(String[] args) {
        iniciarJuego();
    }
}
