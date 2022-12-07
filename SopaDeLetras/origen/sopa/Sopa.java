
package Sopa;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

/*
Clase Sopa
Crea un objeto que contiene la sopa de letras
Utiliza las palabras en el string lineaTexto para formar la sopa de letras
 */

public class Sopa {
    //  Atributos publicos
    public String lineaTexto;
    public Palabra[] Palabras; // Array de objetos Palabra
    public  char[][] matriz; // Matriz que contiene la sopa de letras
    public boolean[][] libre; // Matriz que contiene marcadas las posiciones ocupadas
    public int posx; // Posicion X de la palabra a insertar
    public int posy; // Posicion Y de la palabra a insertar
    public boolean sentidoH; // true si el sentido es horizontal, false si es vertical
    public String letras = "abcdefghijklmnopqrstuvwxyz";
    Random aleatorio; // variable aleatoria para inicializar la sopa
    
    
    // Constructor
    public Sopa(String lineaTexto) {
        this.lineaTexto = lineaTexto;
        
        // inicializamos el array de objetos Palabras con las palabras de lineaTexto
        this.inicializarPalabras();
        
        // Creamos la sopa de letras
        this.crearSopaLetras();
        
        // Imprimimos la sopa de letras
        this.imprimirSopa();
    }
    
    /*
    Metodo crearSopaLetras
    Crea la sopa de letras con las palabras ingresadas
    */
    
    public void crearSopaLetras() {
        // Inicializamos el array la sopa con letras aleatorias
        // y el array libre con valores true
        this.inicializarSopa();
        
        // Insertamos las palabras en la sopa de letras
        System.out.println("Creando la sopa de letras...");
        for(int i=0; i < Palabras.length; i++) {
            // Comprobamos que la palabra no supere los 15 caracteres
            if(Palabras[i].palabra.length()<15) {
                
                // Intentamos hasta 10 veces ingresar la palabra a la sopa
                boolean resultado;
                for(int j=0; j<10; j++) {
                    generarPosicionAleatoria(Palabras[i].palabra.length());
                    
                    // Verificamos si la posicion se puede utilizar
                    resultado = validarPosicion(Palabras[i].palabra);
                    if(resultado == true) {
                        
                        // Guardamos la posicion aleatoria en el objeto Palabra
                        Palabras[i].posx = this.posx;
                        Palabras[i].posy = this.posy;
                        Palabras[i].sentidoH = this.sentidoH;
                        
                        // Insertamos la palabra en el array sopa.
                        // Y marcamos las posiciones en el array libre con false
                        insertarPalabra(Palabras[i].palabra);
                        break;
                    }     
                }   
            }
            else // Superó los 15 caracteres
                System.out.println("Palabra " +Palabras[i]+"tiene mas de 15 caracteres de longitud");
        }
    }
    
    
    /*
    Metodo validar posicion
    Valida si en la posicion aleatoria se puede ingresar la palabra
    Para ello utiliza la matriz libre
    */
    public boolean validarPosicion(String palabra) {
        
       if(sentidoH == true) {
            for(int i=0; i<palabra.length(); i++) {
                if(libre[posx][posy+i] == false || matriz[posx][posy+i] == palabra.charAt(i))
                    return false;
            }
        }
        else {
           for (int i=0; i<palabra.length(); i++) {
               if(libre[posx+i][posy] == false || matriz[posx+i][posy] == palabra.charAt(i))
                   return false;
           }
        }
       return true;
    }
    
    /*
    Metodo insertarPalabra
    */
    public void insertarPalabra(String palabra) {
        if(this.sentidoH == true) {
            for(int i=0; i<palabra.length(); i++) {
                libre[posx][posy+i] = false; 
                matriz[posx][posy+i] = palabra.charAt(i);
            }
        }
        else {
           for (int i=0; i<palabra.length(); i++) {
               libre[posx+i][posy] = false; 
               matriz[posx+i][posy] = palabra.charAt(i);
           }
        }
    }
    
    
    /*
    Metodo generarPosicionAleatoria
    Genera una posicion aleatoria donde se intentará insertar una palabra
    */
    public void generarPosicionAleatoria(int longitud) {
        aleatorio = new Random();
        this.sentidoH = aleatorio.nextBoolean();
        if(sentidoH == true) {
            this.posx = aleatorio.nextInt(15);
            this.posy = aleatorio.nextInt(16-longitud); 
        }
        else {
            this.posx = aleatorio.nextInt(16-longitud);
            this.posy = aleatorio.nextInt(15); 
        }
            
    }

    /*
    Metodo inicializarPalabras
    Inicializa el array de objetos Palabras
    a partir de las palabras contenidas en el string lineaTexto
    */
    public void inicializarPalabras() {
        // Convertimos a minuscula
        this.lineaTexto = this.lineaTexto.toLowerCase();
        
        // Eliminamos espacios en blanco
        this.lineaTexto = this.lineaTexto.replaceAll("\\s*", "");
        
        // Separamos las palabras ingresadas
        String[] strPalabras = this.lineaTexto.split(",", 15);
        
        // Ordenamos de mayor a menor longitud
        // para simplificar la insersion
        Arrays.sort(strPalabras, Comparator.comparingInt(String::length).reversed());
        
        // Instanciamos los objetos Palabra
        Palabras = new Palabra[strPalabras.length];
        for (int i = 0; i < strPalabras.length; i++) {
            Palabras[i] = new Palabra(strPalabras[i]);
        }
        
    }
        
    
    /*
    Metodo inicializarSopa
    Inicializa al array de la sopa de letras con letras aleatorias
    y el array libre con valores true
    */
    public void inicializarSopa() {
        this.matriz = new char [15][15];
        this.libre = new boolean [15][15];
        this.aleatorio = new Random();
        
        for (int i=0; i<matriz.length; i++)
            for(int j=0; j<matriz[0].length; j++) {
                // matriz[i][j] = '.'; // para depurar la sopa de letras
                matriz[i][j] = this.letras.charAt(aleatorio.nextInt(this.letras.length()));
                libre[i][j] = true;
            }
    }
    
    
    /*
    Metodo imprimirSopa
    Imprime la sopa de letras
    */
    public void imprimirSopa() {
        for(int i=0; i<matriz.length; i++) {
            for(int j=0; j<matriz[0].length; j++){
                System.out.print(matriz[i][j]+"  ");
            }
            System.out.println("\n");
        }
    }
    
    /*
    Metodo imprimirPalabras
    Imprime las palabras de la sopa de letras
    */
    public void imprimirPalabras() {
          for(int i=0; i<this.Palabras.length; i++) {
            System.out.println("Palabra: "+this.Palabras[i].palabra+" Posicion: "+this.Palabras[i].posx+","+this.Palabras[i].posy+" SentidoH: "+this.Palabras[i].sentidoH);
        }
    }
    
    // Metodo: banner
    // Imprime la pantalla inicial del programa
    static void banner(Scanner Entrada) {
        System.out.println("____ ____ ___  ____    ___  ____");
        System.out.println("[__  |  | |__] |__|    |  \\ |___");
        System.out.println("___] |__| |    |  |    |__/ |___");
        System.out.println("");
        System.out.println("_    ____ ___ ____ ____ ____");
        System.out.println("|    |___  |  |__/ |__| [__");
        System.out.println("|___ |___  |  |  \\ |  | ___]");
        System.out.println("");
        System.out.println("Crea tu sopa de letras!");
        System.out.println("Presiona ENTER para continuar");
        Entrada.nextLine();
    }
    
    public static void main(String[] args) {
        String lineaTexto; 
        Scanner Entrada = new Scanner(System.in);
        String palabraEncontrada;
                
        // Imprimimos la pantalla inicial
        banner(Entrada);
        
        // Solicitamos las palabras para armar la sopa de letras
        System.out.println("Ingresa las palabras de tu sopa de letras");
        System.out.println("Podes ingresar hasta 15 palabras, separadas por comas");
        System.out.println("Cada palabra puede tener una longitud máxima de 14 caracteres");
        lineaTexto = Entrada.nextLine();
        
        // Creamos la sopa de letras
        Sopa sopa = new Sopa(lineaTexto);
        
        // Creamos el juego
        Jugar jugar = new Jugar(sopa);
        
        // El segundo jugador ingresa las palabras encontradas y se contabilizan las correctas
        // El juego continua mientras aun no se hayan encontrado todas las palabras
        System.out.println(Colores.RED+"¡COMENCEMOS A JUGAR!"+Colores.RESET);
        while(jugar.cantEncontradas() != sopa.Palabras.length) {
            System.out.println("Ingresa la palabra encontrada: ");
            palabraEncontrada = Entrada.nextLine();
            
            if(jugar.verificarPalabra(palabraEncontrada) == true) {
                System.out.println("¡Palabra correcta!");  
            }
            else {
                System.out.println(Colores.RED+"¡Palabra incorrecta!"+Colores.RESET);
            }
        }
        
        System.out.println(Colores.RED+"¡SOPA DE LETRAS RESUELTA!"+Colores.RESET); // Con esta instrucción se imprime el texto especificado dentro de los paréntesis.
    }
}
