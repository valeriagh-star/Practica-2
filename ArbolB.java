import java.util.LinkedList;
import java.util.Queue;

public class ArbolB {
    private NodoArbolB raiz;
    private final int M = 4;
    private final int MAX_LLAVES = M - 1;
    private final int MIN_LLAVES = (int) Math.ceil((double) M / 2) - 1;

    public ArbolB() {
        this.raiz = null;
    }

    public NodoArbolB obtenerRaiz() {
        return raiz;
    }

    // =========================================================================
    // BÚSQUEDA
    // =========================================================================
    public boolean buscar(int llave) {
        return buscarRecursivo(raiz, llave);
    }

    private boolean buscarRecursivo(NodoArbolB nodo, int llave) {
        if (nodo == null) return false;

        int i = 0;
        while (i < nodo.llaves.size() && llave > nodo.llaves.get(i)) {
            i++;
        }

        if (i < nodo.llaves.size() && llave == nodo.llaves.get(i)) {
            return true;
        }

        if (nodo.esHoja) {
            return false;
        }

        return buscarRecursivo(nodo.hijos.get(i), llave);
    }

    // =========================================================================
    // INSERCIÓN
    // =========================================================================
    public boolean insertar(int llave) {
        if (buscar(llave)) {
            System.out.println("-> La llave " + llave + " ya existe en el árbol. No se admiten duplicados.");
            return false;
        }

        if (raiz == null) {
            raiz = new NodoArbolB(true);
            raiz.llaves.add(llave);
            return true;
        }

        ResultadoDivision rd = insertarRecursivo(raiz, llave);

        if (rd != null) {
            System.out.println("\n [FASE DE SPLIT EN RAÍZ]");
            System.out.println("   Se desbordó la raíz. Se crea nueva raíz promoviendo la llave: " + rd.llavePromovida);
            
            NodoArbolB nuevaRaiz = new NodoArbolB(false);
            nuevaRaiz.llaves.add(rd.llavePromovida);
            nuevaRaiz.hijos.add(rd.hijoIzquierdo);
            nuevaRaiz.hijos.add(rd.hijoDerecho);
            this.raiz = nuevaRaiz;

            System.out.println("   Estado del árbol tras split de raíz:");
            imprimirArbol();
        }

        return true;
    }

    private ResultadoDivision insertarRecursivo(NodoArbolB nodo, int llave) {
        int i = 0;
        while (i < nodo.llaves.size() && llave > nodo.llaves.get(i)) {
            i++;
        }

        if (nodo.esHoja) {
            nodo.llaves.add(i, llave);

            if (nodo.llaves.size() > MAX_LLAVES) {
                return dividirNodo(nodo);
            }
            return null;
        } else {
            ResultadoDivision rd = insertarRecursivo(nodo.hijos.get(i), llave);

            if (rd != null) {
                nodo.llaves.add(i, rd.llavePromovida);
                nodo.hijos.set(i, rd.hijoIzquierdo);
                nodo.hijos.add(i + 1, rd.hijoDerecho);

                System.out.println("\n [FASE DE SPLIT / PROMOCIÓN INTERMEDIA]");
                System.out.println("   Se promovió " + rd.llavePromovida + " al nodo padre.");
                System.out.println("   Estado del árbol en esta fase:");
                imprimirArbol();

                if (nodo.llaves.size() > MAX_LLAVES) {
                    return dividirNodo(nodo);
                }
            }
            return null;
        }
    }

    private ResultadoDivision dividirNodo(NodoArbolB nodo) {
        int llavePromovida = nodo.llaves.get(2);

        NodoArbolB izquierdo = new NodoArbolB(nodo.esHoja);
        NodoArbolB derecho = new NodoArbolB(nodo.esHoja);

        izquierdo.llaves.add(nodo.llaves.get(0));
        izquierdo.llaves.add(nodo.llaves.get(1));

        derecho.llaves.add(nodo.llaves.get(3));

        if (!nodo.esHoja) {
            izquierdo.hijos.add(nodo.hijos.get(0));
            izquierdo.hijos.add(nodo.hijos.get(1));
            izquierdo.hijos.add(nodo.hijos.get(2));

            derecho.hijos.add(nodo.hijos.get(3));
            derecho.hijos.add(nodo.hijos.get(4));
        }

        return new ResultadoDivision(llavePromovida, izquierdo, derecho);
    }

    // =========================================================================
    // ELIMINACIÓN
    // =========================================================================
    public boolean eliminar(int llave) {
        if (!buscar(llave)) {
            System.out.println("-> La llave " + llave + " no existe en el árbol.");
            return false;
        }

        eliminarRecursivo(raiz, llave);

        if (raiz != null && raiz.llaves.isEmpty()) {
            if (raiz.esHoja) {
                raiz = null;
            } else {
                System.out.println("\n [REDUCCIÓN DE ALTURA]");
                System.out.println("   La raíz quedó vacía con un solo hijo. El hijo se convierte en la nueva raíz.");
                raiz = raiz.hijos.get(0);
            }
        }
        return true;
    }

    private void eliminarRecursivo(NodoArbolB nodo, int llave) {
        int indice = 0;
        while (indice < nodo.llaves.size() && llave > nodo.llaves.get(indice)) {
            indice++;
        }

        if (indice < nodo.llaves.size() && nodo.llaves.get(indice) == llave) {
            if (nodo.esHoja) {
                nodo.llaves.remove(indice);
            } else {
                NodoArbolB hijoIzquierdo = nodo.hijos.get(indice);
                NodoArbolB hijoDerecho = nodo.hijos.get(indice + 1);

                if (hijoIzquierdo.llaves.size() > MIN_LLAVES) {
                    int predecesor = obtenerPredecesor(hijoIzquierdo);
                    System.out.println("\n [FASE DE REEMPLAZO POR PREDECESOR]");
                    System.out.println("   Reemplazando la llave interna " + llave + " con el predecesor " + predecesor);
                    nodo.llaves.set(indice, predecesor);
                    eliminarRecursivo(hijoIzquierdo, predecesor);
                } else if (hijoDerecho.llaves.size() > MIN_LLAVES) {
                    int sucesor = obtenerSucesor(hijoDerecho);
                    System.out.println("\n [FASE DE REEMPLAZO POR SUCESOR]");
                    System.out.println("   Reemplazando la llave interna " + llave + " con el sucesor " + sucesor);
                    nodo.llaves.set(indice, sucesor);
                    eliminarRecursivo(hijoDerecho, sucesor);
                } else {
                    System.out.println("\n [FASE DE FUSIÓN POR ELIMINACIÓN INTERNA]");
                    System.out.println("   Ambos hijos tienen 1 llave. Fusionando para bajar la llave " + llave);
                    fusionar(nodo, indice);
                    eliminarRecursivo(hijoIzquierdo, llave);
                }
            }
        } else {
            if (nodo.esHoja) {
                return;
            }

            boolean esUltimoHijo = (indice == nodo.llaves.size());
            NodoArbolB hijo = nodo.hijos.get(indice);

            if (hijo.llaves.size() == MIN_LLAVES) {
                repararSubocupacion(nodo, indice);
            }

            if (esUltimoHijo && indice > nodo.llaves.size()) {
                eliminarRecursivo(nodo.hijos.get(indice - 1), llave);
            } else {
                eliminarRecursivo(nodo.hijos.get(indice), llave);
            }
        }
    }

    private int obtenerPredecesor(NodoArbolB nodo) {
        NodoArbolB actual = nodo;
        while (!actual.esHoja) {
            actual = actual.hijos.get(actual.hijos.size() - 1);
        }
        return actual.llaves.get(actual.llaves.size() - 1);
    }

    private int obtenerSucesor(NodoArbolB nodo) {
        NodoArbolB actual = nodo;
        while (!actual.esHoja) {
            actual = actual.hijos.get(0);
        }
        return actual.llaves.get(0);
    }

    private void repararSubocupacion(NodoArbolB padre, int indiceHijo) {
        if (indiceHijo > 0 && padre.hijos.get(indiceHijo - 1).llaves.size() > MIN_LLAVES) {
            pedirPrestadoIzquierdo(padre, indiceHijo);
        } else if (indiceHijo < padre.hijos.size() - 1 && padre.hijos.get(indiceHijo + 1).llaves.size() > MIN_LLAVES) {
            pedirPrestadoDerecho(padre, indiceHijo);
        } else {
            if (indiceHijo > 0) {
                fusionar(padre, indiceHijo - 1);
            } else {
                fusionar(padre, indiceHijo);
            }
        }
    }

    private void pedirPrestadoIzquierdo(NodoArbolB padre, int indiceHijo) {
        NodoArbolB hijo = padre.hijos.get(indiceHijo);
        NodoArbolB hermanoIzquierdo = padre.hijos.get(indiceHijo - 1);

        System.out.println("\n [FASE DE REDISTRIBUCIÓN (PEDIR A HERMANO IZQUIERDO)]");
        System.out.println("   Hermano izquierdo presta una llave a través del padre.");

        hijo.llaves.add(0, padre.llaves.get(indiceHijo - 1));
        padre.llaves.set(indiceHijo - 1, hermanoIzquierdo.llaves.remove(hermanoIzquierdo.llaves.size() - 1));

        if (!hijo.esHoja) {
            hijo.hijos.add(0, hermanoIzquierdo.hijos.remove(hermanoIzquierdo.hijos.size() - 1));
        }

        System.out.println("   Estado del árbol tras redistribución:");
        imprimirArbol();
    }

    private void pedirPrestadoDerecho(NodoArbolB padre, int indiceHijo) {
        NodoArbolB hijo = padre.hijos.get(indiceHijo);
        NodoArbolB hermanoDerecho = padre.hijos.get(indiceHijo + 1);

        System.out.println("\n [FASE DE REDISTRIBUCIÓN (PEDIR A HERMANO DERECHO)]");
        System.out.println("   Hermano derecho presta una llave a través del padre.");

        hijo.llaves.add(padre.llaves.get(indiceHijo));
        padre.llaves.set(indiceHijo, hermanoDerecho.llaves.remove(0));

        if (!hijo.esHoja) {
            hijo.hijos.add(hermanoDerecho.hijos.remove(0));
        }

        System.out.println("   Estado del árbol tras redistribución:");
        imprimirArbol();
    }

    private void fusionar(NodoArbolB padre, int indice) {
        NodoArbolB hijoIzquierdo = padre.hijos.get(indice);
        NodoArbolB hijoDerecho = padre.hijos.get(indice + 1);

        System.out.println("\n [FASE DE FUSIÓN DE NODOS]");
        System.out.println("   Fusionando nodo con su hermano y la llave separadora del padre: " + padre.llaves.get(indice));

        hijoIzquierdo.llaves.add(padre.llaves.remove(indice));
        hijoIzquierdo.llaves.addAll(hijoDerecho.llaves);

        if (!hijoIzquierdo.esHoja) {
            hijoIzquierdo.hijos.addAll(hijoDerecho.hijos);
        }

        padre.hijos.remove(indice + 1);

        System.out.println("   Estado del árbol tras fusión:");
        imprimirArbol();
    }

    // =========================================================================
    // IMPRESIÓN POR NIVELES
    // =========================================================================
    public void imprimirPorNiveles() {
        if (raiz == null || raiz.llaves.isEmpty()) {
            System.out.println("   (Árbol vacío)");
            return;
        }

        Queue<NodoArbolB> cola = new LinkedList<>();
        cola.add(raiz);
        int nivel = 0;

        while (!cola.isEmpty()) {
            int nodosEnNivel = cola.size();
            StringBuilder sb = new StringBuilder();
            sb.append("   Nivel ").append(nivel).append(": ");

            for (int i = 0; i < nodosEnNivel; i++) {
                NodoArbolB nodo = cola.poll();
                
                sb.append("[");
                for (int j = 0; j < nodo.llaves.size(); j++) {
                    sb.append(nodo.llaves.get(j));
                    if (j < nodo.llaves.size() - 1) sb.append(" | ");
                }
                sb.append("] ");

                if (!nodo.esHoja) {
                    for (NodoArbolB hijo : nodo.hijos) {
                        if (hijo != null) cola.add(hijo);
                    }
                }
            }
            System.out.println(sb.toString());
            nivel++;
        }
    }
}