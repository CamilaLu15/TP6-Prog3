package ar.edu.unlar.prog3.tp_comparable_comparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ar.edu.unlar.prog3.tp_comparable_comparator.domain.Estudiante;

public class PruebaComparators {
    public static void main(String[] args) {
        
   
        List<Estudiante> lista = new ArrayList<>();
        lista.add(new Estudiante("LU-001", "Martín Quiroga", 8.5, 22, 18));
        lista.add(new Estudiante("LU-004", "Camila Torres", 9.1, 21, 24));
        lista.add(new Estudiante("LU-006", "Agustina López", 6.8, 19, 10));

        System.out.println("--- Lista Original ---");
        lista.forEach(System.out::println);

    
        Comparator<Estudiante> compMaterias = 
            (e1, e2) -> Integer.compare(e1.getCantidadMateriasAprobadas(), e2.getCantidadMateriasAprobadas());

      
        Comparator<Estudiante> compNombre = Comparator.comparing(Estudiante::getNombre);

        
        Comparator<Estudiante> compEdad = Comparator.comparingInt(Estudiante::getEdad);

        
        
        System.out.println("\n--- Ordenado por Materias Aprobadas (Ascendente) ---");
        lista.sort(compMaterias);
        lista.forEach(System.out::println);

        System.out.println("\n--- Ordenado por Nombre (Alfabético) ---");
        lista.sort(compNombre);
        lista.forEach(System.out::println);

        System.out.println("\n--- Ordenado por Edad (Ascendente) ---");
        lista.sort(compEdad);
        lista.forEach(System.out::println);

        System.out.println("\n*** EJERCICIO 5: Criterios compuestos ***");

        
        Comparator<Estudiante> compPromedioYNombre = Comparator
            .comparingDouble(Estudiante::getPromedio).reversed() 
            .thenComparing(Estudiante::getNombre); 

        System.out.println("\n--- Ordenado por Promedio Descendente, desempata por Nombre ---");
        lista.sort(compPromedioYNombre);
        lista.forEach(System.out::println);

        
        Comparator<Estudiante> compMateriasYNombre = Comparator
            .comparingInt(Estudiante::getCantidadMateriasAprobadas).reversed()
            .thenComparing(Estudiante::getNombre);

        System.out.println("\n--- Ordenado por Materias Descendente, desempata por Nombre ---");
        lista.sort(compMateriasYNombre);
        lista.forEach(System.out::println);

        System.out.println("\n*** EJERCICIO 6: El anti-patrón de la resta ***");

       
        Comparator<Estudiante> restaTramposa = (e1, e2) -> e1.getEdad() - e2.getEdad(); 

        
        Estudiante viejo = new Estudiante("LU-998", "Extremo Viejo", 9.0, Integer.MAX_VALUE, 20); 
        Estudiante joven = new Estudiante("LU-999", "Extremo Joven", 9.0, -1, 20); 

        
        System.out.println("\nProbando restaTramposa entre Edad Maxima y Edad -1:");
        int resultadoMalo = restaTramposa.compare(viejo, joven);
        System.out.println("Resultado de la resta: " + resultadoMalo); 
        System.out.println("¿Es negativo? " + (resultadoMalo < 0) + " -> ¡ESTO ESTÁ MAL! Significa que el código cree que Integer.MAX_VALUE es menor que -1.");

        System.out.println("\nProbando la forma correcta con Integer.compare():");
        int resultadoBueno = Integer.compare(viejo.getEdad(), joven.getEdad());
        System.out.println("Resultado con Integer.compare: " + resultadoBueno);
        System.out.println("¿Es positivo? " + (resultadoBueno > 0) + " -> ¡CORRECTO! Respeta el contrato del Comparator sin sufrir overflow.");

    }
}
