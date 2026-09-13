# Práctica 2

## Implementación de un Árbol B

> ### Integrantes del Equipo:
> * García Herrera Valeria
> * Grajeda Palacios Dulce Abril
> * Pérez Megchun Pablo de Jesús

---

> ### Breve descripción de la práctica:
>> Esta práctica consiste en la implementación de la estructura de datos **Árbol B de orden $m = 4$** en Java. La estructura permite organizar y gestionar datos de manera balanceada mediante operaciones eficientes de búsqueda, inserción (con manejo de splits y promociones) y eliminación (con redistribución y fusión).
> 
> La implementación incluye un menú interactivo en terminal y dos modos de visualización: recorrido por niveles y una representación gráfica textual con conectores (`/`, `|`, `\`).

---

### 1. Lenguaje utilizado:
Utilizamos Java.

---

### 2. Instrucciones para ejecutar el programa:

* Clonar el repositorio remoto e ingresar a la carpeta del proyecto:

   `git clone https://github.com/valeriagh-star/Practica-2.git`

   `cd Practica-2`

* Compilar todos los archivos `.java` ubicados dentro de la carpeta `src`:

   `javac src/ArbolB/*.java`

* Ejecutar la clase principal especificando el classpath:

   `java -cp src ArbolB.Main`

---

### 3. Explicación de cómo ejecutar los casos de prueba:

Para validar todas las operaciones fundamentales del Árbol B ($m = 4$) requeridas en la rúbrica (splits, promociones, búsquedas, redistribución y fusiones), sigue la secuencia descrita a continuación utilizando el menú interactivo del programa:

---

#### Caso 1: Inserción Básica y División (Split con Promoción)
* **Objetivo:** Verificar el desbordamiento cuando un nodo excede el límite de $m - 1 = 3$ llaves y comprobar la promoción de la tercera llave (índice 2).
* **Pasos:**
  1. Seleccionar la **Opción 1** (Insertar).
  2. Ingresar la secuencia de llaves: `10 20 30 40`
* **Resultado Esperado:** 
  * Al ingresar `10 20 30`, las llaves se almacenan en el nodo raíz `[10, 20, 30]`.
  * Al ingresar `40`, el nodo se desborda ($4$ llaves). Se realiza el split y la llave `30` es promovida como nueva raíz.
  * **Estructura final:** Raíz `[30]`, Hijo Izquierdo `[10, 20]`, Hijo Derecho `[40]`.

---

#### Caso 2: Propagación de Splits hasta la Raíz
* **Objetivo:** Comprobar que los splits se propaguen hacia los niveles superiores cuando los nodos internos se llenan.
* **Pasos:**
  1. Seleccionar la **Opción 1** (Insertar).
  2. Ingresar la secuencia completa: `50 60 70`
* **Resultado Esperado:**
  * El árbol realiza splits adicionales y aumenta su altura de forma uniforme.
  * La raíz principal pasa a ser `[40]`, manteniendo subárboles totalmente balanceados.

---

#### Caso 3: Búsqueda de Llaves (Éxito y Fallo)
* **Objetivo:** Confirmar la navegación por los intervalos correctos del subárbol sin recorrer nodos innecesarios.
* **Pasos:**
  1. Seleccionar la **Opción 2** (Buscar).
  2. Probar la búsqueda de la llave `30` (existente) y `99` (inexistente).
* **Resultado Esperado:**
  * Para `30` -> Muestra el resultado `FOUND`.
  * Para `99` -> Muestra el resultado `NOT_FOUND` tras recorrer la rama correspondiente hasta la hoja.

---

#### Caso 4: Eliminación con Redistribución
* **Objetivo:** Validar que al quedar un nodo subocupado ($< 1$ llave), este pida prestada una llave a su hermano adyacente a través del nodo padre.
* **Pasos:**
  1. Seleccionar la **Opción 3** (Eliminar).
  2. Eliminar la llave: `60`
* **Resultado Esperado:** 
  * El nodo afectado se llena mediante una rotación de llaves con el hermano y el padre, conservando la estructura sin reducir la altura.

---

#### Caso 5: Eliminación con Fusión
* **Objetivo:** Verificar la fusión de nodos y la reducción de altura cuando ningún hermano dispone de llaves de sobra para prestar.
* **Pasos:**
  1. Seleccionar la **Opción 3** (Eliminar).
  2. Eliminar consecutivamente las llaves: `70` y luego `50`
* **Resultado Esperado:**
  * Ante la falta de llaves en los hermanos, se ejecuta la fusión entre el nodo subocupado, su hermano y la llave separadora del padre.
  * La raíz se contrae y el árbol reduce su altura de forma balanceada.

---

#### Caso 6: Verificación de Invariantes (Reto Opcional)
* **Objetivo:** Comprobar automáticamente la integridad de las reglas del Árbol B (capacidad, ordenamiento, balanceo de hojas y número de hijos).
* **Pasos:**
  1. Seleccionar la **Opción 7** (Validar árbol) en cualquier momento del recorrido.
* **Resultado Esperado:**
  * Muestra en consola el mensaje de confirmación:
    > `¡VALIDACIÓN EXITOSA! El árbol cumple con todas las invariantes de un Árbol B (m=4).`

---

### 4. Explicación breve de la representación de un nodo:

En nuestra clase `NodoArbolB`, la estructura interna de cada nodo se define mediante los siguientes componentes principales:

* **`List<Integer> llaves`**: Almacena las claves del nodo en orden ascendente (máximo $m - 1 = 3$ llaves para un árbol de orden $m = 4$).
* **`List<NodoArbolB> hijos`**: Contiene los punteros hacia los nodos hijos. En nodos internos, la cantidad de hijos siempre cumple la condición $\text{hijos} = \text{llaves} + 1$.
* **`boolean esHoja`**: Indica si el nodo carece de hijos (`true`) o si es un nodo interno con descendientes (`false`).

Creamos la clase `ResultadoDivision` como una estructura auxiliar para empaquetar la información tras una división de nodo (*split*):

* **`int llavePromovida`**: La clave que asciende al nodo padre (la tercera llave, índice 2).
* **`NodoArbolB hijoIzquierdo`**: La referencia al nuevo nodo que almacena las claves menores (`[0, 1]`).
* **`NodoArbolB hijoDerecho`**: La referencia al nuevo nodo que almacena las claves mayores (`[3]`).

> **Utilidad:** Esta clase permite que el método recursivo de inserción devuelva al nivel superior la llave que debe subir junto con las dos nuevas referencias de subárboles en un solo paso.

---

### 5. Explicación de qué significa m = 4 y por qué cada nodo admite máximo tres llaves:

El parámetro $m$ en un Árbol B no indica cuántos datos caben, sino **cuántas ramas** pueden salir de un nodo:

* **¿Qué significa $m = 4$?**  
  Especifica el **orden** del árbol, es decir, el número máximo de hijos (subárboles) que un nodo puede conectarse hacia el siguiente nivel.

* **¿Por qué el máximo son 3 llaves? ($m - 1$)**  
  Las llaves dentro de un nodo funcionan como **barreras o puntos de corte** para dividir los valores al buscar:
  * $1$ llave divide el espacio en **2** caminos (menores y mayores).
  * $2$ llaves dividen el espacio en **3** caminos.
  * $3$ llaves dividen el espacio en **4** caminos.
  
  Por esta regla matemática, para tener $m$ caminos siempre se necesitan como máximo $m - 1$ llaves. En $m = 4$, el límite exacto es $4 - 1 = 3$ llaves.

* **El proceso de *Split* (División) y Promoción:**  
  Al intentar insertar una $4^{\text{ta}}$ llave, el nodo se desborda (*overflow*). Para solucionar esto y recuperar las invariantes, se realiza una división (*split*):
  * Siguiendo el criterio revisado en clase, al desbordarse el nodo con $4$ llaves ordenadas (índices 0, 1, 2 y 3), **se promueve hacia el padre la tercera llave (índice 2)**.
  * Las llaves restantes se dividen entre dos nuevos nodos hermanos: las primeras dos llaves (índices 0 y 1) quedan en el hermano izquierdo y la última llave (índice 3) queda en el hermano derecho.

---

### 6. Explicación de cómo se decide qué hijo seguir durante una búsqueda:

En el método `buscarRecursivo` de la clase `ArbolB`, la elección del hijo hacia el cual descender se realiza mediante un recorrido secuencial de la lista ordenada `llaves` del nodo actual:

* **Algoritmo de Navegación (`while`):**
  Se incrementa un índice `i` mientras no hayamos superado la cantidad de llaves del nodo (`i < nodo.llaves.size()`) y el valor buscado sea estrictamente mayor que la llave actual (`llave > nodo.llaves.get(i)`).

```java
int i = 0;
while (i < nodo.llaves.size() && llave > nodo.llaves.get(i)) {
    i++;
}
```

* **Criterio de Selección de Puntero (`i`):**

Una vez finalizado el bucle, el índice `i` determina la posición exacta dentro de la lista de punteros `nodo.hijos.get(i)` según las siguientes reglas de intervalo:

* **Límite Izquierdo (`i = 0`):** Si la `llave` buscada es menor que la primera llave del nodo ($\text{llave} < K_0$), descendemos a `hijos.get(0)`.

* **Intervalos Intermedios ($0 < i < n$):** Si la `llave` se encuentra entre dos llaves del nodo ($K_{i-1} < \text{llave} < K_i$), descendemos a `hijos.get(i)`.

* **Límite Derecho (`i = n`):** Si la `llave` es mayor que todas las llaves almacenadas en el nodo ($\text{llave} > K_{n-1}$), el bucle avanza hasta el final y seleccionamos el último hijo, `hijos.get(n)`.

---

* **Coincidencia Exacta o Descenso:**

Si tras el bucle resulta que `llave == nodo.llaves.get(i)`, se retorna `true` inmediatamente (llave encontrada). Si no hay coincidencia y el nodo no es una hoja, el método realiza la llamada recursiva evaluando la rama calculada:

```java
return buscarRecursivo(nodo.hijos.get(i), llave);
```

---

### 7. Explicación de qué ocurre cuando un nodo alcanza cuatro llaves:

* **Manejo de Desbordamiento (*Overflow*) y Split:**

Cuando un nodo dentro del Árbol B ($m = 4$) recibe una $4^{\text{ta}}$ llave, viola la regla de capacidad máxima ($m - 1 = 3$). En ese momento, el método `insertarRecursivo` ejecuta el algoritmo de división (`dividirNodo`):

* **Identificación de la Llave a Promover:**  
  Con las $4$ llaves ordenadas en el nodo desbordado (índices `0`, `1`, `2` y `3`), el sistema toma **la tercera llave (índice 2)** como la `llavePromovida`.

* **División del Nodo (*Split*):**  
  El nodo original se fragmenta en dos nuevos nodos hermanos:
  * **Hermano Izquierdo:** Almacena las dos primeras llaves (índices `0` y `1`). Si no es una hoja, conserva los primeros tres hijos (índices `0`, `1` y `2`).
  * **Hermano Derecho:** Almacena la última llave (índice `3`). Si no es una hoja, conserva los últimos dos hijos (índices `3` y `4`).

* **Reconexión en el Nodo Padre:**  
  La `llavePromovida` sube al nodo padre y se inserta en su lista de llaves. A su vez, el puntero al nodo anterior se reemplaza por el `hijoIzquierdo` y se inserta el `hijoDerecho` en la posición contigua.

* **Propagación Ascendente:**
  Si el nodo padre ya contaba con $3$ llaves, la inserción de la llave promovida causará un nuevo desbordamiento en él, propagando el proceso de *split* hacia arriba. Si el desbordamiento llega a la raíz, se crea una nueva raíz con la llave promovida, **haciendo crecer la altura del árbol en 1 nivel**.

---

### 8. Explicación de la convención de promoción usada en la práctica:

Durante el proceso de división (*split*) de un nodo desbordado, la selección de la llave que asciende al nodo padre sigue una convención específica basada en la estructura del nodo y las reglas vistas en clase:

* **Estado de Desbordamiento (*Overflow*):**  
  Al intentar insertar un elemento en un nodo que ya contiene $3$ llaves, el nodo almacena temporalmente **$4$ llaves ordenadas** (ubicadas en los índices `0`, `1`, `2` y `3`).

* **Criterio de Selección (Tercera Llave / Índice 2):**  
  En lugar de promover la mediana matemática tradicional entre dos opciones centrales, la convención adoptada fija la **tercera llave (índice 2)** como la `llavePromovida`.

* **Estructura Resultante del *Split*:**
  * **Promoción:** La llave en la posición `2` sube al nodo padre.
  * **Subárbol Izquierdo:** Conserva las llaves en los índices `0` y `1` (las $2$ llaves menores).
  * **Subárbol Derecho:** Conserva la llave en el índice `3` (la última llave restante).

* **Propósito de la Convención:**  
  Esta regla garantiza un comportamiento determinista en la reestructuración del árbol, asegurando que todos los splits mantengan el balance de hojas y respeten los límites de capacidad permitidos ($m - 1 = 3$) en cada nivel.

---

### 9. Explicación breve de redistribución y fusión:

Cuando el método `eliminarRecursivo` detecta que un nodo descendiente quedará subocupado (`hijo.llaves.size() == MIN_LLAVES`, es decir, con solo $1$ llave antes de eliminar), se invoca al método auxiliar `repararSubocupacion(nodo, indice)`. Este método decide qué estrategia aplicar evaluando el estado de sus hermanos:

---

* **Redistribución / Préstamo:**

Se ejecuta cuando al menos uno de los hermanos adyacentes tiene llaves de sobra (`size() > MIN_LLAVES`, es decir, 2 o más llaves).

* **Pedir al Hermano Izquierdo (`pedirPrestadoIzquierdo`):**
  * **Paso 1:** La llave del `padre` que separa a ambos hermanos baja al inicio del nodo afectado: `hijo.llaves.add(0, padre.llaves.get(indiceHijo - 1))`.
  * **Paso 2:** La última llave del hermano izquierdo sube a reemplazar la posición en el padre: `padre.llaves.set(..., hermanoIzquierdo.llaves.remove(...))`.
  * **Paso 3:** Si no son hojas, el último hijo del hermano izquierdo pasa a ser el primer hijo del nodo afectado: `hijo.hijos.add(0, hermanoIzquierdo.hijos.remove(...))`.

* **Pedir al Hermano Derecho (`pedirPrestadoDerecho`):**
  * **Paso 1:** La llave del `padre` baja al final del nodo afectado: `hijo.llaves.add(padre.llaves.get(indiceHijo))`.
  * **Paso 2:** La primera llave del hermano derecho sube a tomar el lugar en el padre: `padre.llaves.set(..., hermanoDerecho.llaves.remove(0))`.
  * **Paso 3:** Si no son hojas, el primer hijo del hermano derecho se traslada al final del nodo afectado: `hijo.hijos.add(hermanoDerecho.hijos.remove(0))`.

> **Impacto:** Conserva intacta la altura del árbol y ajusta la distribución de claves en un solo nivel.

---

* **Fusión (Unión de Nodos):**

Se ejecuta cuando ningún hermano adyacente tiene llaves de sobra para prestar (ambos tienen exactamente $1$ llave).

* **Mecanismo del método `fusionar(padre, indice)`:**
  * **Paso 1 (Bajar separador):** Se remueve la llave separadora del `padre` y se agrega al `hijoIzquierdo`: `hijoIzquierdo.llaves.add(padre.llaves.remove(indice))`.
  * **Paso 2 (Unir llaves):** Se traspasan todas las llaves del `hijoDerecho` hacia el `hijoIzquierdo`: `hijoIzquierdo.llaves.addAll(hijoDerecho.llaves)`.
  * **Paso 3 (Unir hijos):** Si no son hojas, se anexan las referencias de los hijos del `hijoDerecho` al `hijoIzquierdo`: `hijoIzquierdo.hijos.addAll(hijoDerecho.hijos)`.
  * **Paso 4 (Limpieza):** Se elimina el puntero al `hijoDerecho` en la lista del padre: `padre.hijos.remove(indice + 1)`.

> **Impacto:** Resta una llave al nodo padre. Si el padre era la raíz y se queda sin llaves (`raiz.llaves.isEmpty()`), el código ejecuta la **reducción de altura**: la raíz original se destruye y el `hijoIzquierdo` recién fusionado pasa a ser la nueva raíz (`raiz = raiz.hijos.get(0)`).

---

### 10. ¿Por qué una búsqueda no debe recorrer todos los hijos de un nodo?

En un Árbol B, la búsqueda no necesita inspeccionar cada subárbol porque la estructura mantiene sus llaves estrictamente **ordenadas**, funcionando como un índice por intervalos que descarta ramas de forma determinista.

---

* **Propiedad de Orden por Intervalos:**  
  Las llaves dentro de un nodo actúan como **puntos de corte o delimitadores**. Por ejemplo, si un nodo almacena las llaves $[10, 30, 50]$, se establecen automáticamente rangos exclusivos para sus $4$ hijos:
  * **Hijo 0 (`hijos.get(0)`):** Valores estrictamente menores que $10$ ($x < 10$).
  * **Hijo 1 (`hijos.get(1)`):** Valores comprendidos entre $10$ y $30$ ($10 < x < 30$).
  * **Hijo 2 (`hijos.get(2)`):** Valores comprendidos entre $30$ y $50$ ($30 < x < 50$).
  * **Hijo 3 (`hijos.get(3)`):** Valores estrictamente mayores que $50$ ($x > 50$).

---

* **Descarte Directo de Subárboles:**  
  Al realizar la comparación dentro del bucle de búsqueda (`llave > nodo.llaves.get(i)`), el algoritmo identifica un **único camino válido**. Si se busca la llave `25`, se determina directamente que cae en el rango del **Hijo 1**, permitiendo ignorar por completo los subárboles 0, 2 y 3.

---

* **Eficiencia Algorítmica $\mathcal{O}(\log n)$:**  
  * Si se recorrieran todos los hijos en cada paso, la complejidad se convierte en un recorrido exhaustivo lineal $\mathcal{O}(n)$.
  * Al evaluar únicamente el puntero relevante en cada nivel, el tiempo de ejecución se mantiene en orden logarítmico $\mathcal{O}(\log n)$, minimizando las comparaciones y lecturas en memoria.

---

### 11. ¿Por qué al insertar una llave nueva no podemos decidir el hijo únicamente comparando con la primera llave del nodo?

Comparar la llave a insertar únicamente contra la primera posición (`nodo.llaves.get(0)`) solo permite decidir si el elemento debe ir a la **rama izquierda** (si es menor) o hacia la **derecha general** (si es mayor). Esto invalida la estructura de un Árbol B por las siguientes razones:

---

* **Pérdida de la Estructura Multi-Rama ($m = 4$):**  
  Un nodo con $3$ llaves ($K_0, K_1, K_2$) posee $4$ subárboles de destino. Si solo comparamos contra $K_0$, el algoritmo no puede distinguir si un elemento mayor debe ingresar en el subárbol intermedio 1 ($K_0 < x < K_1$), en el subárbol intermedio 2 ($K_1 < x < K_2$) o en el subárbol derecho ($x > K_2$).

---

* **Violación de las Invariantes de Orden:**  
  Si colocamos una llave en la rama equivocada por tomar decisiones parciales, romperemos el ordenamiento global del árbol. Esto provocaría que las búsquedas futuras fallen al seguir rutas incorrectas o que las llaves queden traspuestas.

---

* **Búsqueda del Rango Exacto mediante el Bucle:**  
  En el código del método `insertarRecursivo`, el bucle `while` recorre todas las llaves del nodo para encontrar la posición precisa (`i`) del intervalo correspondiente antes de descender:

```java
int i = 0;
while (i < nodo.llaves.size() && llave > nodo.llaves.get(i)) {
    i++;
}
// 'i' almacena la rama correcta (0, 1, 2 o 3)
```