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
            System.out.println("2. Buscar llave(s)");
            System.out.println("3. Eliminar llave(s)");
            System.out.println("4. Mostrar árbol actual");
            System.out.println("5. Imprimir árbol por niveles");
            System.out.println("6. Crear un nuevo árbol");
            System.out.println("7. Validar árbol (Reto Opcional)");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción (1-8): ");

            String entradaOpcion = escaner.nextLine().trim();

            int opcion;
            try {
                opcion = Integer.parseInt(entradaOpcion);
            } catch (NumberFormatException e) {
                System.out.println("\n[ERROR]: Opción inválida. Por favor, ingrese un número entre 1 y 8.");
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
                    System.out.println(">>> RECORRIDO POR NIVELES:");
                    arbol.imprimirPorNiveles();
                    break;
                case 6:
                    System.out.println("\n=============================================");
                    System.out.println("       REINICIANDO: CREANDO NUEVO ÁRBOL      ");
                    System.out.println("=============================================");
                    arbol = new ArbolB();
                    System.out.println("Nuevo árbol vacío creado:");
                    arbol.imprimirArbol();
                    solicitarInsercionInicial(escaner, arbol);
                    break;
                case 7:
                    System.out.println("\n---------------------------------------------");
                    System.out.println(">>> VALIDACIÓN DE INVARIANTES (RETO OPCIONAL):");
                    arbol.validarArbol();
                    break;
                case 8:
                    System.out.println("\nSaliendo del programa...");
                    escaner.close();
                    return;
                default:
                    System.out.println("\n[ERROR]: Opción fuera de rango. Seleccione un número entre 1 y 8.");
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
                System.out.println("\n[ERROR]: '" + parte + "' no es un número entero válido. Se omitió esta entrada.");
            }
        }
    }

    private static void procesarBusqueda(Scanner escaner, ArbolB arbol) {
        System.out.print("\nIngrese la(s) llave(s) a buscar (separadas por espacio): ");
        String linea = escaner.nextLine().trim();

        if (linea.isEmpty()) {
            System.out.println("[AVISO]: No ingresó ningún valor.");
            return;
        }

        String[] partes = linea.split("\\s+");
        for (String parte : partes) {
            try {
                int valorBuscar = Integer.parseInt(parte);
                System.out.println("\n---------------------------------------------");
                System.out.println(">>> OPERACIÓN: BUSCAR (" + valorBuscar + ")");
                boolean encontrado = arbol.buscar(valorBuscar);
                if (encontrado) {
                    System.out.println("Resultado: FOUND (La llave " + valorBuscar + " SÍ existe en el árbol).");
                } else {
                    System.out.println("Resultado: NOT_FOUND (La llave " + valorBuscar + " NO existe en el árbol).");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n[ERROR]: '" + parte + "' no es un número entero válido. Se omitió esta entrada.");
            }
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
                System.out.println("\n[ERROR]: '" + parte + "' no es un número entero válido. Se omitió esta entrada.");
            }
        }
    }
}