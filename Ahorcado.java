// Ahorcado.java
import java.util.ArrayList; // Para listas dinámicas (letras usadas)
import java.util.Arrays;    // Para manipular arrays fácilmente
import java.util.HashSet;   // Para almacenar letras adivinadas y no adivinadas (más eficiente para búsquedas)
import java.util.List;      // Interfaz para listas
import java.util.Random;    // Para seleccionar una palabra aleatoria
import java.util.Scanner;   // Para leer la entrada del usuario

public class Ahorcado {

    private static final String[] PALABRAS = {
        "programacion", "computadora", "desarrollo", "algoritmo", "teclado",
        "internet", "aplicacion", "lenguaje", "python", "java", "github",
        "codigo", "consola", "variable", "funcion"
    };
    private static final int INTENTOS_MAXIMOS = 7; // Número de errores permitidos

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Objeto para leer la entrada del usuario
        Random random = new Random();             // Objeto para generar números aleatorios

        // Seleccionar una palabra aleatoria y convertirla a minúsculas
        String palabraSecreta = PALABRAS[random.nextInt(PALABRAS.length)].toLowerCase();
        int intentosRestantes = INTENTOS_MAXIMOS;
        
        // HashSet para guardar las letras adivinadas correctamente (evita duplicados y búsqueda rápida)
        HashSet<Character> letrasAdivinadas = new HashSet<>(); 
        // ArrayList para guardar las letras que el usuario ya ha intentado (correctas o incorrectas)
        List<Character> letrasIntentadas = new ArrayList<>(); 

        System.out.println("¡Bienvenido al Juego del Ahorcado!");
        System.out.println("Adivina la palabra secreta. Tienes " + INTENTOS_MAXIMOS + " intentos.");

        // Bucle principal del juego
        while (intentosRestantes > 0) {
            System.out.println("\n--------------------");
            System.out.println("Intentos restantes: " + intentosRestantes);
            System.out.println("Letras intentadas: " + letrasIntentadas);
            System.out.println("Palabra: " + obtenerPalabraActual(palabraSecreta, letrasAdivinadas));

            System.out.print("Introduce una letra (o la palabra completa para adivinar): ");
            String entradaUsuario = scanner.nextLine().toLowerCase(); // Leer entrada y convertir a minúsculas

            // Validar la entrada del usuario
            if (entradaUsuario.length() == 1 && Character.isLetter(entradaUsuario.charAt(0))) {
                char letra = entradaUsuario.charAt(0);

                if (letrasIntentadas.contains(letra)) {
                    System.out.println("Ya has intentado esa letra: '" + letra + "'. Prueba con otra.");
                } else {
                    letrasIntentadas.add(letra); // Añadir la letra a la lista de intentadas

                    if (palabraSecreta.contains(String.valueOf(letra))) {
                        System.out.println("¡Correcto! La letra '" + letra + "' está en la palabra.");
                        letrasAdivinadas.add(letra); // Añadir a las letras correctamente adivinadas
                    } else {
                        System.out.println("Incorrecto. La letra '" + letra + "' no está en la palabra.");
                        intentosRestantes--; // Restar un intento si es incorrecta
                    }
                }
            } else if (entradaUsuario.length() == palabraSecreta.length() && entradaUsuario.matches("[a-z]+")) {
                // Si el usuario intenta adivinar la palabra completa
                if (entradaUsuario.equals(palabraSecreta)) {
                    System.out.println("\n¡FELICIDADES! Has adivinado la palabra: " + palabraSecreta);
                    break; // Salir del bucle, el juego ha terminado
                } else {
                    System.out.println("Incorrecto. Esa no es la palabra.");
                    intentosRestantes--; // Restar un intento si la palabra es incorrecta
                }
            } else {
                System.out.println("Entrada inválida. Por favor, introduce una sola letra o la palabra completa.");
            }

            // Comprobar si todas las letras han sido adivinadas individualmente
            if (todasLasLetrasAdivinadas(palabraSecreta, letrasAdivinadas)) {
                System.out.println("\n¡FELICIDADES! Has adivinado la palabra: " + palabraSecreta);
                break; // Salir del bucle, el juego ha terminado
            }
        }

        // Fuera del bucle: si los intentos llegaron a 0 o la palabra fue adivinada
        if (intentosRestantes == 0) {
            System.out.println("\n--------------------");
            System.out.println("¡OH NO! Te has quedado sin intentos.");
            System.out.println("La palabra secreta era: " + palabraSecreta);
            System.out.println("¡Más suerte la próxima vez!");
        }

        scanner.close(); // Cerrar el scanner para liberar recursos
    }

    /**
     * Genera la representación actual de la palabra con guiones y letras adivinadas.
     * Ejemplo: palabraSecreta="java", letrasAdivinadas={'j', 'a'} -> "ja_a"
     */
    private static String obtenerPalabraActual(String palabraSecreta, HashSet<Character> letrasAdivinadas) {
        StringBuilder palabraMostrada = new StringBuilder();
        for (char letra : palabraSecreta.toCharArray()) {
            if (letrasAdivinadas.contains(letra)) {
                palabraMostrada.append(letra);
            } else {
                palabraMostrada.append("_");
            }
        }
        return palabraMostrada.toString();
    }

    /**
     * Comprueba si todas las letras de la palabra secreta han sido adivinadas.
     */
    private static boolean todasLasLetrasAdivinadas(String palabraSecreta, HashSet<Character> letrasAdivinadas) {
        for (char letra : palabraSecreta.toCharArray()) {
            if (!letrasAdivinadas.contains(letra)) {
                return false; // Si encuentra una letra no adivinada, devuelve false
            }
        }
        return true; // Si recorrió todas las letras y todas están adivinadas, devuelve true
    }
}