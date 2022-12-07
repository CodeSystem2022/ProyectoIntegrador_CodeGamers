
package Sopa;


public class Jugar {
    public Sopa sopa;
    
    Jugar(Sopa sopa) {
        this.sopa = sopa;
    }
    
    /*
    Metodo cantEncontradas
    Contabiliza la cantidad de palabras de la sopa de letras
    que aun no fueron encontradas en el juego
    */
    public int cantEncontradas() {
        
        int cantEncontradas = 0;
        for (int i = 0; i < sopa.Palabras.length; i++) { // Bucle for nos permite realizar una y otra acción, siempre y cuando se cumpla una condición booleana.
            if(sopa.Palabras[i].encontrada == true)
                cantEncontradas ++;
        }
        return cantEncontradas;
    }

    boolean verificarPalabra(String palabraEncontrada) {
        for (int i = 0; i < sopa.Palabras.length; i++) {
            if(sopa.Palabras[i].palabra.equals(palabraEncontrada)) {
                sopa.Palabras[i].encontrada = true;
                return true;
            }
        }
        return false;
    }
}
