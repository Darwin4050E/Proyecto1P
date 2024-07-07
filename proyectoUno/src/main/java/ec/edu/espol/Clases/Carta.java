package ec.edu.espol.Clases;

// Importes:
import ec.edu.espol.Enums.ColorCarta;

public class Carta {

    // Variables de instancia:
    private ColorCarta color;
    private int numero;

    // Constructores:
    public Carta(ColorCarta color, int numero){
        
        this.color = color;

        // Verifica que el numero ingresado esté entre 0 y 9.
        if(numero >= 0 && numero <= 9)
            this.numero = numero;
    }

    // Getters:
    public ColorCarta getColor(){
        return this.color;
    }

    public int getNumeroCarta(){
        return this.numero;
    }

    // Setters:
    public void setColor(ColorCarta color){
        this.color = color;
    }

    public void setNumero(int numero){
        this.numero = numero;
    }

    // Sobreescritura to String:
    @Override
    public String toString(){
        return "" + numero + "|" + color;
    }

    //Sobreescritura Equals:
    //Se puede usar para comparar Cartas Normales con Cartas Normales.
    @Override
    public boolean equals(Object obj){

        // Null check.
        if(obj == null)
            return false;
        
        // Self check.
        if(this == obj)
            return true;

        // Class check.
        if(this.getClass() != obj.getClass())
            return false;

        // Compara el color o el nombre de las cartas.
        Carta c = (Carta)obj;
        return ((this.getColor() == c.getColor()) || (this.getNumeroCarta() == c.getNumeroCarta()));
    }
}   
