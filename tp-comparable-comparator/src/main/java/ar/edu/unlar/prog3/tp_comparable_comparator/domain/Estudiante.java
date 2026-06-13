package ar.edu.unlar.prog3.tp_comparable_comparator.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estudiante implements Comparable<Estudiante> {
    private String legajo;
    private String nombre;
    private double promedio;
    private int edad;
    private int cantidadMateriasAprobadas;

    // Ejercicio 2: Orden natural por promedio descendente
    @Override
    public int compareTo(Estudiante otro) {
        // Double.compare(d1, d2) devuelve negativo si d1 < d2, cero si son iguales, positivo si d1 > d2.
        // Como queremos orden DESCENDENTE, pasamos 'otro' como primer parámetro y 'this' como segundo.
        return Double.compare(otro.getPromedio(), this.getPromedio());
    }
}
