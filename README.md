# Trabajo Práctico: Ordenamiento de objetos (Comparable, Comparator y Polimorfismo)

**Universidad Nacional de La Rioja (UNLaR)** **Cátedra:** Programación III 
**Año:** 2026  

---

## 📌 Descripción del Proyecto

Este proyecto es una API REST desarrollada con Spring Boot que demuestra la implementación práctica de las interfaces `Comparable` y `Comparator` en Java. Su objetivo principal es resolver el problema del ordenamiento de objetos de dominio (en este caso, `Estudiante`) utilizando múltiples criterios dinámicos sin modificar la clase original, aplicando para ello el **Patrón de Diseño Strategy**.

## 🚀 Tecnologías Utilizadas

* **Lenguaje:** Java 21
* **Framework:** Spring Boot 3.5.15 (Web)
* **Gestor de dependencias:** Maven
* **Herramientas extra:** Lombok (para reducir el código boilerplate)

## ⚙️ Estructura y Patrones Aplicados

* **Comparable (Orden Natural):** Implementado en la clase `Estudiante` para definir el orden por defecto basado en el mérito académico (promedio descendente).
* **Comparator (Estrategias Externas):** Implementados utilizando Method References y Lambdas para ordenar dinámicamente por edad, nombre, legajo y cantidad de materias aprobadas.
* **Patrón Strategy:** Utilizado en `EstudianteService` mediante un `Map<String, Comparator<Estudiante>>` para evitar estructuras de control acopladas (como `switch` o `if-else`) al momento de seleccionar el criterio de ordenamiento solicitado por el cliente.
* **Manejo de Errores:** Implementación de `@ExceptionHandler` para capturar peticiones inválidas y devolver respuestas HTTP 400 estructuradas y limpias.

## 🛠️ Cómo ejecutar el proyecto

1. Clonar el repositorio.
2. Abrir una terminal en la raíz del proyecto.
3. Ejecutar el siguiente comando de Maven para levantar el servidor embebido de Tomcat:

```bash
mvn spring-boot:run
```

4. La API estará disponible en `http://localhost:8080`.

## 📡 Uso de la API (Endpoints)

El sistema expone un único endpoint para obtener la lista de estudiantes.

**Ruta base:** `GET /api/estudiantes`

### Parámetros de consulta (Query Params) aceptados:

| Parámetro | Tipo | Opciones válidas | Valor por defecto |
| :--- | :--- | :--- | :--- |
| `sortBy` | String | `promedio`, `edad`, `nombre`, `materiasAprobadas`, `legajo` | `promedio` |
| `order` | String | `asc`, `desc` | `asc` |

### Ejemplos de peticiones:

**1. Obtener estudiantes ordenados por edad (ascendente):**
```text
GET /api/estudiantes?sortBy=edad&order=asc
```

**2. Obtener estudiantes ordenados por materias aprobadas (descendente):**
```text
GET /api/estudiantes?sortBy=materiasAprobadas&order=desc
```

**3. Ejemplo de respuesta de error (HTTP 400 Bad Request):**
Si se envía un criterio no registrado (ej: `?sortBy=colorFavorito`), la API responde:

```json
{
  "error": "Criterio de ordenamiento no válido",
  "criterioRecibido": "colorFavorito",
  "criteriosAceptados": [
    "promedio",
    "edad",
    "nombre",
    "materiasAprobadas",
    "legajo"
  ]
}
```

## 📝 Justificación Teórica

Las respuestas a las preguntas conceptuales exigidas por la cátedra se encuentran detalladas en el archivo [`RESPUESTAS.md`](./RESPUESTAS.md) adjunto en la raíz de este repositorio.

## 👨‍💻 Autores

* [Camila Lucero/ CamilaLu15]
* [Joaquin Codocea / codobit]
