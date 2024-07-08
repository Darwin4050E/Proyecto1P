package ec.edu.espol.Clases;

// Importes:
import java.util.ArrayList;
import java.util.Collections;
import ec.edu.espol.Enums.*;

public class Baraja {

    // Variables de instancia:
    private ArrayList<Carta> baraja;

    //Constructores:
    public Baraja() {
        this.baraja = crearBaraja();
        desordenarBaraja();
    }

    // Getters:
    public ArrayList<Carta> getBaraja() {
        return baraja;
    }

    // Setters:
    public void setBaraja(ArrayList<Carta> baraja) {
        this.baraja = baraja;
    }

    // Métodos:
    public ArrayList<Carta> crearBaraja(){
        ArrayList<Carta> cartas = new ArrayList<>();

        // Recorre una lista de colores [ColorCarta.values()].
        for(ColorCarta c : ColorCarta.values()){

            // Para cartas de colores (No de color negro).
            if(c != ColorCarta.N){

                // Agrega 10 cartas de "x" color [0-9].
                for(int i = 0; i < 10; i++){
                    cartas.add(new Carta(c, i));
                }
                
                // Recorre una lista de comodines [TipoComodin.values()].
                for(TipoComodin tipo: TipoComodin.values()){

                    // Agrega 2 cartas de "x" comodin [Excluye a cambio de color] de color.
                    if (tipo != TipoComodin.CAMBIO_COLOR) {
                        cartas.add(new CartaComodin(c,tipo));
                        cartas.add(new CartaComodin(c,tipo));
                                                    
                    }  
                }
            }
            
            // Para cartas de color negro.
            else {

                // Recorre una lista de comodines [TipoComodin.values()].
                for(TipoComodin tipo: TipoComodin.values()){

                    // Agrega 2 cartas negras de "x" comodin [Excluye a las restantes].
                    if (tipo != TipoComodin.REVERSE && tipo != TipoComodin.BLOQUEO) {
                        cartas.add(new CartaComodin(c,tipo));
                        cartas.add(new CartaComodin(c,tipo));                                
                    }
                }
            }
            
        }

        // Retorna la lista de cartas.
        return cartas;
    }

    // Desordena la baraja.
    public void desordenarBaraja() {
        Collections.shuffle(baraja);
    }

    // Agrega cartas a la mano de "x" jugador.
    public void repartirCartas(Jugador j, int cantidad) {

        // Agrega cartas a la mano del jugador y las elimina de la baraja.
        for(int i = 0; i < cantidad; i++) {
            j.getMano().add(baraja.remove(0)); 
        }
        
    }

    // Obtiene una carta con números (Carta Normal).
    public Carta getCartaNormalAleatoria() {
        //Desordena la baraja.
        desordenarBaraja();

        //Retorna la primera carta que no sea comodin y la elimina de la baraja.
        for (int i = 0; i < baraja.size(); i++) {
            Carta c = baraja.get(i);
            if (!(c instanceof CartaComodin)) {
                return baraja.remove(i); 
            }
        }

        //Caso default - Ya no existen cartas normales.  
        //Retorna la primera carta de la baraja.
        return baraja.remove(0); 
    }

    //Comprobar si hay cartas en la baraja
    public boolean esBarajaVacía() {
        return baraja.isEmpty();
    }
}

