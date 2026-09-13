import java.util.ArrayList;
import java.util.List;

public class NodoArbolB {
    List<Integer> llaves;
    List<NodoArbolB> hijos;
    boolean esHoja;

    public NodoArbolB(boolean esHoja) {
        this.esHoja = esHoja;
        this.llaves = new ArrayList<>();
        this.hijos = new ArrayList<>();
    }

    public int cantidadLlaves() {
        return llaves.size();
    }
}