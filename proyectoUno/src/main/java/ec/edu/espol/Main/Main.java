package ec.edu.espol.Main;

// Importes:
import ec.edu.espol.Clases.*;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Instanciando y configurando Scanner.
        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US);
        
        // Creando una instancia de juego.
        Juego j = new Juego();

        System.out.println("\n¡Bienvenido al Juego del UNO!\n");
        mostrarReglasJuego();

        // Inicializando línea de juego.
        j.inicializarLineaJuego();
        
        // Ejecutando el juego en base a una condición.
        while (j.condicionDeJuego()) {
            // Formato
            System.out.println("--------------------------------------------------------------------------");
            System.out.println();
            
            // Almacena jugador O máquina de acuerdo al turno.            
            Jugador jugadorActual = j.getJugador(j.getTurno());

            // Imprime la línea de juego y las manos de los jugadores en cada ronda
            System.out.print("|Linea de juego|: ");
            j.imprimirUltimaCartaLineaJuego();
            j.imprimirManosJugadores();
            
            // Verifica que el jugador tenga cartas válidas con las que jugar.
            if (j.jugadorTieneCartasValidas(jugadorActual)) {

                System.out.println();
                if (j.getTurno() == 1) {
                    System.out.println("**Turno del jugador**");
                } else {
                    System.out.println("**Turno de la máquina**");
                }

                // Turno del jugador humano.
                if (j.getTurno() == 1){

                    boolean jugarCartaValida = false;
                    while (!jugarCartaValida) {
                        int indice = pedirIndiceUsuario(jugadorActual, sc);
                        jugarCartaValida = j.agregarCartaLinea(indice, sc, jugadorActual);
                    }
                
                } else { //Turno de la maquina 
    
                    int i = 1;
                    boolean cartaValida = false; 
                    
                    // La maquina juega la primera opcion posible
                    while (i <= jugadorActual.getMano().size() && !cartaValida) {
                        Carta c = jugadorActual.getCarta(i);
                        if (j.agregarCartaLinea(i, sc, jugadorActual)) {
                            System.out.println("La maquina juega la carta numero: " + (i) + " - " + c);
                            cartaValida = true;
                        }
                        i++;
                    } 
                } 

            } else {

                System.out.println();
                if (j.getTurno() == 1) 
                    System.out.println("**Turno del jugador**");
                else 
                    System.out.println("**Turno de la máquina**");

                System.out.println("Jugador Actual no tiene cartas válidas - Roba una carta de la baraja");
                jugadorActual.robarCartaBaraja(j.getBaraja());

            }

            j.cambiarTurno();
            System.out.println();
        }

        j.imprimirGanador();
    }

    // Pide el índice al usuario 
    public static int pedirIndiceUsuario(Jugador j, Scanner sc) {
        int indice;
        do {
            System.out.print("Ingrese la posición de una carta válida en su mano: ");
            indice = sc.nextInt();
        } while (!(indice >= 1 && indice <= j.getMano().size()));
        sc.nextLine(); // Limpieza del buffer (luego de pedir números)
        return indice;
    }  

    // Muestra las reglas del juego.
    public static void mostrarReglasJuego() {
        String reglas = """
        En esta implementación del juego, tu oponente es la máquina.
            1. Colores de cartas: R (Rojo), A (Amarillo), V (Verde), Z (Azul).
            2. Comodines: (Reverse), (Bloqueo), (Cambio_Color), (MAS4), (MAS2).
            3. Existen cartas del 0 al 9 por cada color (R, A, V, Z).
            5. Coloca una carta del mismo color o número que la carta en juego.
            6. Comodines de color deben coincidir con el color de la última carta.
            7. Comodines negros siempre se pueden jugar.
            9. Comodines negros y Cambio_Color permiten elegir el color para el siguiente turno.
            8. Reverse y Bloqueo hacen perder el turno al siguiente jugador.
            9. MAS2 Y MAS4 hacen perder el turno al siguiente jugador y tiene que robar cartas.
            10. No se puede responder a un comodín con otro comodín.
            11. El juego termina cuando un jugador se queda sin cartas (Ganador).
            12. Si ya no existen cartas en la baraja el juego termina.
        """;
        System.out.println("Ten en cuenta las siguientes reglas: ");
        System.out.println(reglas);
    }
} 