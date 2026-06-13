package ar.edu.unlar.prog3.tp_comparable_comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ar.edu.unlar.prog3.tp_comparable_comparator.domain.Estudiante;

public class Main {

    public static void main(String[] args) {
        List<Estudiante> lista = new ArrayList<>();
        lista.add(new Estudiante("LU-2024-001", "Martín Quiroga", 8.5, 22, 18));
        lista.add(new Estudiante("LU-2024-002", "Valeria Díaz", 8.5, 20, 15));
        lista.add(new Estudiante("LU-2024-003", "Facundo Castro", 7.2, 24, 22));
        lista.add(new Estudiante("LU-2024-004", "Camila Torres", 9.1, 21, 24));
        lista.add(new Estudiante("LU-2024-005", "Lucas González", 9.1, 23, 24));

        System.out.println("--- LISTA ANTES DE ORDENAR ---");
        lista.forEach(System.out::println);

        // Ahora esto compila y funciona perfectamente
        Collections.sort(lista);

        System.out.println("\n--- LISTA DESPUÉS DE ORDENAR (Por promedio DESC) ---");
        lista.forEach(System.out::println);
    }
}
