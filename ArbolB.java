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
}