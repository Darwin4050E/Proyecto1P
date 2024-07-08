package ec.edu.espol.Clases;

// Importes:
import java.util.ArrayList;
import java.util.Scanner;
import ec.edu.espol.Enums.*;

public class Juego {

    // Variables de instancia:
    private Baraja baraja;
    private ArrayList<Carta> lineaJuego;
    private Jugador jugador;
    private Jugador maquina;
    private int turno;

    // Constructores:
    public Juego() {
        
        // Crea la baraja y la desordena.
        baraja = new Baraja();

        // Inicializa la línea de juego.
        lineaJuego = new ArrayList<>();

        // Crea e inicia al jugador y la máquina con 7 cartas de la baraja en su mano.
        jugador = new Jugador();
        baraja.repartirCartas(jugador, 7);
        maquina = new Jugador();
        baraja.repartirCartas(maquina, 7);

        // Inicializa el turno en uno (Comienza el jugador).
        turno = 1;
    }

    // Getters:
    
    public Baraja getBaraja() {
        return baraja;
    }

    public ArrayList<Carta> getLineaJuego() {
        return lineaJuego;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Jugador getMaquina() {
        return maquina;
    }

    public int getTurno() {
        return turno;
    }
    
    // Setters:

    public void setBaraja(Baraja baraja) {
        this.baraja = baraja;
    }

    public void setLineaJuego(ArrayList<Carta> lineaJuego) {
        this.lineaJuego = lineaJuego;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public void setMaquina(Jugador maquina) {
        this.maquina = maquina;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }
    
    // Métodos:

    // Sobrecarga. De acuerdo al turno, retorna "x" jugador.
    public Jugador getJugador(int turno) {

        // [1-Humano / 2-Máquina].
        if (turno == 1) 
            return this.jugador;
        else
            return this.maquina;
    }

    // Obtiene la última carta de la línea de juego.
    public Carta getUltimaCartaLinea() {
        return lineaJuego.get(lineaJuego.size() - 1);
    }

    // Cambia el turno alternando entre 1 y 2.
    public void cambiarTurno() {
        turno = (turno % 2) + 1;
    }

    // Establece la condición de juego.
    public boolean condicionDeJuego() {

        // Si algún jugador posee alguna carta.
        if (jugador.getMano().size() == 1 || maquina.getMano().size() == 1) 
            System.out.println("¡UNO!"); //se imprime UNO
        
        // Retorna la condición [True - Mientras ambos tengan cartas que jugar y la baraja no esté vacía].
        return (!(jugador.getMano().isEmpty() || maquina.getMano().isEmpty())) && (!baraja.getBaraja().isEmpty()) ; 
    }

    // Inicializa la línea de juego.
    public void inicializarLineaJuego() {
        lineaJuego.add(baraja.getCartaNormalAleatoria());
    }

    // Imprime la línea de juego.
    public void imprimirUltimaCartaLineaJuego() {
        System.out.println(getUltimaCartaLinea());
        System.out.println();
    }

    // Imprimir las manos de los jugadores.
    public void imprimirManosJugadores() {
        System.out.print("Mano Jugador: ");
        jugador.imprimirMano();
        System.out.print("Mano Máquina: ");
        maquina.imprimirMano();
    }

    // Verifica que la carta ingresada pueda ser usada en la línea de juego.
    public boolean esCartaValida(Carta c, Carta ultimaCarta) {
        
        // Si la carta ingresada no es comodín:
        if (!(c instanceof CartaComodin)) {

            // Si la última carta de la línea no es comodín:
            if (!(ultimaCarta instanceof CartaComodin)) {
                return c.equals(ultimaCarta);

            // Si la última carta de la línea es un comodín:
            } else {
                return c.getColor() == ultimaCarta.getColor();
            }

        // Si la carta ingresada es comodin:
        } else {

            //Si la última carta no es un comodín:
            if (!(ultimaCarta instanceof CartaComodin)) {

                // Si el comodin ingresado es de color: 
                if (c.getColor() != ColorCarta.N) {
                    return ultimaCarta.getColor() == c.getColor();
                
                // Si el comodin ingresado es negro:
                } else {
                    return true;
                }
            // Si la última carta es un comodín [No es posible responder a un comodín conn un comodín].
            } else {
                    return false;
            }
        }    
    }

    // Verifica si el jugador tiene cartas válidas en la mano.
    public boolean jugadorTieneCartasValidas(Jugador jugador) {

        Carta ultimaCarta = getUltimaCartaLinea();
        for (Carta c: jugador.getMano()) {

            // Verifica si existe una carta valida.
            if(esCartaValida(c,ultimaCarta))
                return true;
        }
        return false;
    }

    // Solicita un color al jugador "Humano".
    public ColorCarta pedirColorUsuario(Scanner sc) {   

        // Variables locales:
        String color;

        // Itera hasta recibir un color correcto:
        do {
            System.out.print("Ingrese el color de la carta (R|A|V|Z): ");
            color = sc.nextLine().toUpperCase();

        // Valida que el color ingresado sea correcto
        } while (!(color.equals("R") || color.equals("A") || color.equals("V") || color.equals("Z")));
        return ColorCarta.valueOf(color);
    }
    

    // Cambia el color de la última carta de forma aleatoria.
    // Se usa cuando la máquina elija una carta negra o comodin CAMBIO_COLOR.
    public void cambiarColorUltimaCartaAleatorio() {

        // Indice entre 0 y 3 .
        int indiceAleatorio = (int)(Math.random() * (ColorCarta.values().length - 1)); 

        // El color nunca será Negro (por lo índices).
        ColorCarta colorAleatorio = ColorCarta.values()[indiceAleatorio];
        // Cambia el color de la última carta en la línea.
        getUltimaCartaLinea().setColor(colorAleatorio); 
    }

    //Realiza una función de acuerdo al Tipo de Comodin.
    public void funcionComodin(CartaComodin cc, Scanner sc) {   

        //Elije al siguiente jugador (los comodines afectan al siguiente jugador).
        Jugador siguienteJugador;
        if (turno == 1) 
            siguienteJugador = maquina;
        else 
            siguienteJugador = jugador; 
        
        // Casos de todos los tipos de comodines.
        switch (cc.getTipoComodin()) {

            case REVERSE:
                cambiarTurno();
                break;

            case BLOQUEO:
                cambiarTurno();
                break; 

            case CAMBIO_COLOR:
                // Turno del humano.
                if (turno == 1) {
                    getUltimaCartaLinea().setColor(pedirColorUsuario(sc)); 
                // Turno de la maquina.
                } else {
                    cambiarColorUltimaCartaAleatorio();
                }
                break;

            case MAS4:
                
                //Agrega cartas a la mano del siguiente jugador y cambia el turno.
                for(int i = 0; i < 4; i++) {
                    siguienteJugador.robarCartaBaraja(baraja);
                }
                cambiarTurno();
                break;

            case MAS2:

                //Agrega cartas a la mano del siguiente jugador y cambia el turno.
                for(int i = 0; i < 2; i++) {
                    siguienteJugador.robarCartaBaraja(baraja);
                }
                cambiarTurno();
                break;

            default:
                break;
        }
    }

    // Verifica y efectúa la adición de cartas a la Línea de Juego.
    public boolean agregarCartaLinea(int indiceCartaManoJugador, Scanner sc, Jugador jugador) {  
        
        // Obtiene las cartas que se quieren comparar
        Carta ultimaCarta = getUltimaCartaLinea();
        Carta c = jugador.getCarta(indiceCartaManoJugador); 

        // Si la carta es válida
        if (esCartaValida(c, ultimaCarta)) {

            // Se agrega a la línea de juego y se la remueve de la mano del jugador
            lineaJuego.add(c); 
            jugador.removerCartaMano(indiceCartaManoJugador); 
            
            // En caso de que la carta sea Comodin.
            if (c instanceof CartaComodin) {
                //Downcasting
                CartaComodin cc = (CartaComodin) c;

                // Si la carta es Negra, y no es una carta CAMBIO COLOR (Evitar pedir color dos veces).
                if ((cc.getColor() == ColorCarta.N) && (cc.getTipoComodin() != TipoComodin.CAMBIO_COLOR)) {

                    // Si el turno es del humano, se pide el color de la carta.
                    if (turno == 1) { 
                            ColorCarta siguienteColor = pedirColorUsuario(sc);
                            cc.setColor(siguienteColor);
                    // Si el turno es de la máquina, se cambia el color de la carta aleatoriamente.
                    } else { 
                            cambiarColorUltimaCartaAleatorio();
                    }
                }

                // Se realiza la función del comodín.
                // Se la llama ahora para evitar inconvenientes con los turnos en la verificación anterior.
                funcionComodin(cc, sc);
            }
            return true; //Si se pudo agregar la carta a la línea.
        }
        return false; //Si no se pudo agregar.
    }

    public void imprimirGanador() {
        // Formato
        System.out.println("--------------------------------------------------------------------------");
        System.out.println();
        
        //Si la baraja no está vacía
        if (!baraja.esBarajaVacía()) {

            // Gana el jugador que cambió el turno en la última ronda del juego.
            if (turno == 2) 
                System.out.println("Ganador: ¡HUMANO!");
            else 
                System.out.println("Ganador: ¡MÁQUINA!");

        } else { //En caso de que la baraja esté vacía

            System.out.println("¡Ya no existen cartas en la baraja!");

            // Gana el jugador si al terminarse la baraja este tiene menos cartas que la maquina.

            if(jugador.getMano().size() < maquina.getMano().size()){
                System.out.println("Ganador: ¡HUMANO!");

            // Si ambos poseen la misma cantidad de cartas es empate.
            } else if(jugador.getMano().size() == maquina.getMano().size()) {
                System.out.println("Nadie gana es: ¡EMPATE!");  
                
            //Gana la maquina si tiene menos cartas en su mano que el jugador.
            } else {
                System.out.println("Ganador: ¡MAQUINA!");
            }
        }
    }
}
