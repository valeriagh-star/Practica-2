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
}