# Respuestas del TP - Ordenamiento de Objetos

**Pregunta 1: ¿Por qué `Collections.sort()` no compila cuando le pasamos una `List<Estudiante>`? ¿Qué contrato exige Java que nuestra clase no está cumpliendo?**
`Collections.sort()` no compila porque Java no sabe por defecto cómo comparar dos objetos de nuestra clase `Estudiante` (no tienen un "orden natural" inherente como los números o las letras). Para que compile, Java exige que la clase de los objetos contenidos en la lista cumpla con el contrato de la interfaz `Comparable<T>`, es decir, que implemente el método `compareTo(T o)`.

**Pregunta 2: ¿Por qué elegiste el atributo `promedio` como orden natural? ¿Qué pasaría si mañana un nuevo requisito pide ordenar por `cantidadMateriasAprobadas`? ¿Modificarías `compareTo`? ¿Qué consecuencias tendría?**
Se eligió `promedio` porque en un contexto académico suele ser el criterio de mérito por defecto. Si el requisito cambia, modificar directamente el `compareTo` alteraría el "orden natural" de toda la aplicación. Esto podría romper otras partes del sistema que dependan de este ordenamiento implícito (por ejemplo, si los estudiantes están guardados en un `TreeSet`), generando efectos secundarios indeseados.

**Pregunta 3: `Comparable` nos ata a un único criterio de ordenamiento. ¿Qué problemas de diseño introduce esto si nuestro sistema necesitara ordenar la misma lista de estudiantes de 4 formas distintas según el contexto? Relacioná tu respuesta con los principios SRP y OCP.**
Atarse solo a `Comparable` viola el Principio de Responsabilidad Única (SRP), ya que la clase `Estudiante` pasaría a tener la responsabilidad de su propio dominio más la lógica de cómo debe ser ordenada. También viola el Principio Abierto/Cerrado (OCP), porque si necesitamos un nuevo criterio (por ejemplo, por edad), estaríamos obligados a modificar el código fuente de la clase `Estudiante` en lugar de simplemente extender el comportamiento mediante nuevas clases o funciones.

**Pregunta 4: Explicá con tus palabras qué es un overflow de enteros, por qué el "truco de la resta" lo provoca, qué parte del contrato de `Comparator` rompe, y por qué `Integer.compare()` no sufre este problema.**
El *overflow* ocurre cuando una operación matemática excede el límite máximo (o mínimo) que puede almacenar un tipo de dato en memoria (en Java, un `int` llega hasta ~2.14 mil millones). Si restamos un número negativo muy grande a un número positivo muy grande, el resultado real superará ese límite y Java "dará la vuelta", devolviendo un número con el signo incorrecto. Esto rompe el contrato del `Comparator` porque arroja un resultado inconsistente, arruinando el orden. `Integer.compare(x, y)` evita esto porque no realiza restas matemáticas; simplemente usa operadores lógicos (`<`, `>`, `==`) para devolver `-1`, `0` o `1`.

**Pregunta 5: ¿Qué patrón de diseño estás aplicando al usar un `Map<String, Comparator<T>>` en lugar de un `switch`? Explicá cómo se relaciona este patrón con el polimorfismo y por qué es preferible a la alternativa procedural.**
Estamos aplicando el **Patrón Strategy**. En lugar de tener una estructura procedural estática como un `switch` o `if-else`, encapsulamos cada algoritmo de ordenamiento (`Comparator`) como una estrategia intercambiable y las registramos en un mapa. Esto explota el polimorfismo porque el servicio llama al método `compare` sin importarle qué implementación concreta se está ejecutando en tiempo de ejecución. Es preferible porque respeta el principio OCP: si queremos agregar un nuevo criterio de ordenamiento, simplemente agregamos una nueva entrada al `Map`, sin necesidad de modificar (y potencialmente romper) la lógica principal de ruteo.


## Parte 2 — Comparable: el orden natural

**Pregunta 2: ¿Por qué elegiste el atributo `promedio` como orden natural? ¿Qué pasaría si mañana un nuevo requisito pide ordenar por `cantidadMateriasAprobadas`? ¿Modificarías `compareTo`? ¿Qué consecuencias tendría?**

Se eligió el `promedio` porque en un dominio de estudiantes universitarios, el mérito académico suele ser la forma más intuitiva u obvia de ordenarlos por defecto. 
Si el requisito cambiara a `cantidadMateriasAprobadas`, **no sería recomendable modificar el `compareTo`**. Al hacerlo, alteraríamos el orden natural de la clase en todo el sistema. Cualquier otra parte de la aplicación que dependiera de este ordenamiento implícito (por ejemplo, si los estudiantes estuvieran guardados en un `TreeSet` o se ordenaran en otro módulo) sufriría efectos secundarios y su comportamiento se rompería silenciosamente.

**Pregunta 3: `Comparable` nos ata a un único criterio de ordenamiento. ¿Qué problemas de diseño introduce esto si nuestro sistema necesitara ordenar la misma lista de estudiantes de 4 formas distintas según el contexto? Relacioná tu respuesta con los principios de responsabilidad única (SRP) y abierto/cerrado (OCP).**

Atarse exclusivamente a `Comparable` genera varios problemas de diseño:
1. **Viola el Principio de Responsabilidad Única (SRP):** La clase `Estudiante` debería tener la única responsabilidad de modelar el estado y comportamiento de un estudiante. Al meterle la lógica de cómo debe ser ordenado, le estamos asignando una responsabilidad extra que pertenece más bien a la presentación o a la lógica de negocio.
2. **Viola el Principio Abierto/Cerrado (OCP):** Este principio dicta que las clases deben estar abiertas a la extensión pero cerradas a la modificación. Si necesitamos 4 formas distintas de ordenar a los estudiantes y solo usamos `Comparable`, estaríamos obligados a entrar a la clase `Estudiante` y modificar su código cada vez que el contexto cambie, lo cual es inviable e inestable.


## Parte 3 — Comparator: estrategias externas

**Pregunta 4: Explicá con tus palabras qué es un overflow de enteros, por qué el "truco de la resta" lo provoca, qué parte del contrato de `Comparator` rompe, y por qué `Integer.compare()` no sufre este problema.**

Un *overflow* (desbordamiento) ocurre cuando una operación matemática excede el límite de capacidad del tipo de dato en memoria. En Java, un `int` tiene un valor máximo de `2,147,483,647`.

El "truco de la resta" `(e1, e2) -> e1.getEdad() - e2.getEdad()` es peligroso porque, si restamos un número negativo a un número positivo muy grande (por ejemplo, `2147483647 - (-1)`), el resultado matemático real es `2147483648`. Como este número no cabe en un `int`, Java sufre un overflow, "da la vuelta" hacia los números negativos y devuelve `-2147483648`.

Esto **rompe el contrato de `Comparator`**, que exige que si `a > b` devuelva un número positivo. Al devolver un número negativo por culpa del overflow, el método de ordenamiento posiciona los elementos al revés de lo que debería.

`Integer.compare()` soluciona esto porque no utiliza restas matemáticas bajo el capó. En su lugar, usa operadores lógicos (`<`, `>`, `==`) para evaluar las magnitudes de forma segura y siempre devuelve explícitamente `-1`, `0` o `1`.

## Parte 4 — Integración con Spring Boot

**Pregunta 5: ¿Qué patrón de diseño estás aplicando al usar un `Map<String, Comparator<T>>` en lugar de un `switch`? Explicá cómo se relaciona este patrón con el polimorfismo y por qué es preferible a la alternativa procedural.**

La solución implementa el **Patrón Strategy**. Encapsulamos las diferentes lógicas de comparación (`Comparators`) como estrategias intercambiables y las registramos en un mapa usando su nombre de criterio como clave.
Se relaciona íntimamente con el polimorfismo porque el `EstudianteService` extrae la estrategia correspondiente y ejecuta el método `.sort(comparator)` sin necesidad de conocer cuál es la implementación concreta detrás de esa interfaz. El comportamiento cambia en tiempo de ejecución de acuerdo al objeto recuperado.
Es ampliamente preferible a un `switch` o múltiples `if-else` (alternativa procedural) porque respeta el principio **OCP**. Si a futuro se agrega un nuevo campo (por ejemplo, `fechaIngreso`), simplemente se añade una nueva entrada al mapa durante la inicialización, sin necesidad de tocar la lógica central del enrutamiento ni agregar ramas a una estructura de control estática.