package ec.edu.espol.Clases;

// Importes:
import java.util.ArrayList;

public class Jugador {

    // Variables de instancia:
    private ArrayList<Carta> mano;
    
    // Constructores:
    public Jugador(){ 
        //Creación de la mano.
        //La clase Juego se encarga de repartir las cartas.
        this.mano = new ArrayList<>();
    }

    // Getters
    public ArrayList<Carta> getMano(){
        return mano;
    }

    // Setters:
    public void setMano(ArrayList<Carta> mano){
        this.mano = mano;
    }

    // Métodos

    // Roba una carta de la baraja (la primera) si la baraja no está vacía
    public void robarCartaBaraja(Baraja b) {
        if (!b.esBarajaVacía())
            mano.add(b.getBaraja().remove(0));
    }

    // Remueve una carta de la mano de jugador.
    public void removerCartaMano(int posicion) {
        if (posicion >= 1 && posicion <= mano.size())
            mano.remove(posicion - 1); //trabajar con posiciones que ingresa el jugador
    }

    // Agrega una carta a la mano de jugador.
    public void agregarCartaMano(Carta c) {
        mano.add(c);
    }

    // Obtiene una carta de la mano del jugador.
    public Carta getCarta(int posicion) {

        // Valida que la posición exista.
        if (posicion >= 1 && posicion <= mano.size())
            return mano.get(posicion - 1);
        else
            return null;
    }

    // Imprime la mano de un jugador.
    public void imprimirMano(){
        for (int i = 0; i < mano.size(); i++) {

            // Imprime todas las cartas, excepto la última.
            if (i < mano.size() - 1)
                System.out.print(mano.get(i) + " - ");

            // Imprime la última carta, sin el guiión del caso anterior.
            else
                System.out.println(mano.get(i));
        } 
    }

}
