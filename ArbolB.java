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
}