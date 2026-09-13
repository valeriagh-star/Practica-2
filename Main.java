import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner escaner = new Scanner(System.in);
        
        System.out.println("=============================================");
        System.out.println(" INICIALIZANDO ESTRUCTURA: ÁRBOL B VACÍO ");
        System.out.println("=============================================");
        ArbolB arbol = new ArbolB();
        System.out.println("Árbol inicial recién creado:");
        arbol.imprimirArbol();

        solicitarInsercionInicial(escaner, arbol);

        while (true) {
            System.out.println("\n=============================================");
            System.out.println("       MENÚ ÁRBOL B (ORDEN m = 4)            ");
            System.out.println("=============================================");
            System.out.println("1. Insertar llave(s)");
            System.out.println("2. Buscar llave");
            System.out.println("3. Eliminar llave(s)");
            System.out.println("4. Mostrar árbol actual (Visual)");
            System.out.println("5. Cargar la secuencia de prueba oficial (Guía)");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción (1-6): ");

            String entradaOpcion = escaner.nextLine().trim();

            int opcion;
            try {
                opcion = Integer.parseInt(entradaOpcion);
            } catch (NumberFormatException e) {
                System.out.println("\n[ERROR]: Opción no válida. Por favor, ingrese únicamente un número entre 1 y 6.");
                continue;
            }

            switch (opcion) {
                case 1:
                    procesarInsercion(escaner, arbol);
                    break;
                case 2:
                    procesarBusqueda(escaner, arbol);
                    break;
                case 3:
                    procesarEliminacion(escaner, arbol);
                    break;
                case 4:
                    System.out.println("\n---------------------------------------------");
                    System.out.println(">>> ÁRBOL ACTUAL:");
                    arbol.imprimirArbol();
                    break;
                case 5:
                    System.out.println("\n---------------------------------------------");
                    System.out.println(">>> EJECUTANDO SECUENCIA COMPLETA DE LA GUÍA (Puntos 5.1 y 5.3)...");
                    arbol = new ArbolB();
                    int[] llavesPrueba = {20, 40, 10, 30, 50, 60, 70, 5, 15, 25, 35, 45};
                    
                    System.out.println("\n1. Insertando llaves: 20, 40, 10, 30, 50, 60, 70, 5, 15, 25, 35, 45...");
                    for (int k : llavesPrueba) {
                        arbol.insertar(k);
                    }

                    System.out.println("\n--- ESTADO DEL ÁRBOL TRAS INSERCIONES COMPLETAS ---");
                    arbol.imprimirArbol();

                    System.out.println("\n2. Búsquedas de verificación:");
                    System.out.println("   buscar(35) -> " + (arbol.buscar(35) ? "FOUND" : "NOT_FOUND"));
                    System.out.println("   buscar(99) -> " + (arbol.buscar(99) ? "FOUND" : "NOT_FOUND"));

                    System.out.println("\n3. Eliminaciones obligatorias (25, 10, 70, 5)...");
                    int[] llavesEliminar = {25, 10, 70, 5};
                    for (int k : llavesEliminar) {
                        System.out.println("\n>>> ELIMINANDO " + k + "...");
                        arbol.eliminar(k);
                    }

                    System.out.println("\n--- ESTADO FINAL DEL ÁRBOL TRAS ELIMINACIONES ---");
                    arbol.imprimirArbol();

                    System.out.println("\n4. Búsquedas finales de verificación:");
                    System.out.println("   buscar(25) -> " + (arbol.buscar(25) ? "FOUND" : "NOT_FOUND"));
                    System.out.println("   buscar(35) -> " + (arbol.buscar(35) ? "FOUND" : "NOT_FOUND"));
                    break;
                case 6:
                    System.out.println("\nSaliendo del programa...");
                    escaner.close();
                    return;
                default:
                    System.out.println("\n[ERROR]: Opción fuera de rango. Seleccione un número entre 1 y 6.");
            }
        }
    }

    private static void solicitarInsercionInicial(Scanner escaner, ArbolB arbol) {
        System.out.println("\n---------------------------------------------");
        System.out.print("Ingrese las llaves iniciales a insertar (separadas por espacio): ");
        String linea = escaner.nextLine().trim();
        
        if (linea.isEmpty()) {
            System.out.println("No ingresó llaves iniciales. El árbol permanece vacío.");
            return;
        }

        insertarValoresDesdeCadena(linea, arbol);
    }

    private static void procesarInsercion(Scanner escaner, ArbolB arbol) {
        System.out.print("\nIngrese la(s) llave(s) a insertar (separadas por espacio): ");
        String linea = escaner.nextLine().trim();

        if (linea.isEmpty()) {
            System.out.println("[AVISO]: No ingresó ningún valor.");
            return;
        }

        insertarValoresDesdeCadena(linea, arbol);
    }

    private static void insertarValoresDesdeCadena(String cadena, ArbolB arbol) {
        String[] partes = cadena.split("\\s+");
        for (String parte : partes) {
            try {
                int val = Integer.parseInt(parte);
                System.out.println("\n---------------------------------------------");
                System.out.println(">>> OPERACIÓN: INSERTAR (" + val + ")");
                System.out.println("--- ÁRBOL INICIAL ---");
                arbol.imprimirArbol();

                boolean insertado = arbol.insertar(val);

                if (insertado) {
                    System.out.println("\n--- ÁRBOL FINAL ---");
                    arbol.imprimirArbol();
                }
            } catch (NumberFormatException e) {
                System.out.println("\n[ERROR IGNORADO]: '" + parte + "' no es un número entero válido. Se omitió esta entrada.");
            }
        }
    }

    private static void procesarBusqueda(Scanner escaner, ArbolB arbol) {
        System.out.print("\nIngrese la llave a buscar: ");
        String entrada = escaner.nextLine().trim();

        try {
            int valorBuscar = Integer.parseInt(entrada);
            System.out.println("\n---------------------------------------------");
            System.out.println(">>> OPERACIÓN: BUSCAR (" + valorBuscar + ")");
            boolean encontrado = arbol.buscar(valorBuscar);
            if (encontrado) {
                System.out.println("Resultado: FOUND (La llave " + valorBuscar + " existe en el árbol).");
            } else {
                System.out.println("Resultado: NOT_FOUND (La llave " + valorBuscar + " NO existe en el árbol).");
            }
        } catch (NumberFormatException e) {
            System.out.println("\n[ERROR]: '" + entrada + "' no es un número válido. Operación cancelada.");
        }
    }

    private static void procesarEliminacion(Scanner escaner, ArbolB arbol) {
        System.out.print("\nIngrese la(s) llave(s) a eliminar (separadas por espacio): ");
        String linea = escaner.nextLine().trim();

        if (linea.isEmpty()) {
            System.out.println("[AVISO]: No ingresó ningún valor.");
            return;
        }

        String[] partes = linea.split("\\s+");
        for (String parte : partes) {
            try {
                int val = Integer.parseInt(parte);
                System.out.println("\n---------------------------------------------");
                System.out.println(">>> OPERACIÓN: ELIMINAR (" + val + ")");
                System.out.println("--- ÁRBOL INICIAL ---");
                arbol.imprimirArbol();

                boolean eliminado = arbol.eliminar(val);

                if (eliminado) {
                    System.out.println("\n--- ÁRBOL FINAL ---");
                    arbol.imprimirArbol();
                }
            } catch (NumberFormatException e) {
                System.out.println("\n[ERROR IGNORADO]: '" + parte + "' no es un número entero válido. Se omitió esta entrada.");
            }
        }
    }
}