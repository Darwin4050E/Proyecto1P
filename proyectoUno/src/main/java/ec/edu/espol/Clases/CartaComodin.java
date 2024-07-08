package ec.edu.espol.Clases;

// Importes:
import ec.edu.espol.Enums.TipoComodin;
import ec.edu.espol.Enums.ColorCarta;

public class CartaComodin extends Carta {

    // Variables de instancia:
    private TipoComodin tipoComodin;

    // Constructores:
    public CartaComodin(ColorCarta color, TipoComodin tipoComodin){
        super(color, 0);
        this.tipoComodin = tipoComodin;
    }

    // Getters:
    public TipoComodin getTipoComodin(){
        return tipoComodin;
    }

    // Sobreescritura toString.
    @Override
    public String toString(){
        return "" + tipoComodin + "|" + super.getColor();
    }

    //Sobreescritua Equals 
    //En caso de que se desee implementar la opción de responder un comodín a otro comodín.
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

        CartaComodin cc = (CartaComodin)obj;
        //Dos cartas comodines son iguales cuando sus colores son iguales
        return (this.getColor() == cc.getColor()); 
    }
}
