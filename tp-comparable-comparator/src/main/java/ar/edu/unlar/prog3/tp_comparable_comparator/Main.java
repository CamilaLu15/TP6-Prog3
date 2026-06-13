package ar.edu.unlar.prog3.tp_comparable_comparator;

import java.util.Comparator;
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


        // ==========================================
        // PARTE 3 - COMPARATOR
        // ==========================================
        System.out.println("\n--- EJERCICIO 4: COMPARATORS SIMPLES ---");

        // 1. Lambda explícita para cantidad de materias (usando Integer.compare)
        Comparator<Estudiante> porMateriasAsc = (e1, e2) -> Integer.compare(e1.getCantidadMateriasAprobadas(), e2.getCantidadMateriasAprobadas());
        lista.sort(porMateriasAsc);
        System.out.println("\nOrdenado por Materias Aprobadas (ASC):");
        lista.forEach(e -> System.out.println(e.getNombre() + " - Materias: " + e.getCantidadMateriasAprobadas()));

        // 2. Method reference para nombre (alfabético)
        Comparator<Estudiante> porNombreAsc = Comparator.comparing(Estudiante::getNombre);
        lista.sort(porNombreAsc);
        System.out.println("\nOrdenado por Nombre (Alfabético):");
        lista.forEach(e -> System.out.println(e.getNombre()));

        // 3. Method reference para edad
        Comparator<Estudiante> porEdadAsc = Comparator.comparingInt(Estudiante::getEdad);
        lista.sort(porEdadAsc);
        System.out.println("\nOrdenado por Edad (ASC):");
        lista.forEach(e -> System.out.println(e.getNombre() + " - Edad: " + e.getEdad()));
        
        System.out.println("\n--- EJERCICIO 5: CRITERIOS COMPUESTOS ---");

        // 1. Desempate con thenComparing()
        Comparator<Estudiante> porPromedioDescYNombreAsc = Comparator
                .comparingDouble(Estudiante::getPromedio).reversed()
                .thenComparing(Estudiante::getNombre);
        lista.sort(porPromedioDescYNombreAsc);
        System.out.println("\nOrdenado por Promedio (DESC) y luego Nombre (ASC):");
        // Fijate en Camila y Lucas (ambos con 9.1). Camila debería salir antes que Lucas.
        lista.forEach(e -> System.out.println(e.getNombre() + " - Promedio: " + e.getPromedio()));

        // 3. Combinar materias y nombre
        Comparator<Estudiante> porMateriasDescYNombreAsc = Comparator
                .comparingInt(Estudiante::getCantidadMateriasAprobadas).reversed()
                .thenComparing(Estudiante::getNombre);

            lista.sort(porMateriasDescYNombreAsc);
        System.out.println("\nOrdenado por Materias (DESC) y luego Nombre (ASC):");
        lista.forEach(e -> System.out.println(e.getNombre() + " - Materias: " + e.getCantidadMateriasAprobadas()));
        
        System.out.println("\n--- EJERCICIO 6: EL ANTI-PATRÓN DE LA RESTA ---");

        // Creamos dos estudiantes extremos
        Estudiante muyViejo = new Estudiante("L99", "Matusalén", 5.0, Integer.MAX_VALUE, 10);
        Estudiante porNacer = new Estudiante("L00", "Bebé", 5.0, -1, 0);

        // Comparator con el truco malicioso de la resta
        Comparator<Estudiante> restaTramposa = (e1, e2) -> e1.getEdad() - e2.getEdad();

        System.out.println("Comparando alguien con edad Integer.MAX_VALUE contra alguien con edad -1:");
        
        // La resta falla y da negativo en vez de positivo
        int resultadoMalo = restaTramposa.compare(muyViejo, porNacer);
        System.out.println("Resultado con resta (INCORRECTO): " + resultadoMalo); 
        
        // Integer.compare funciona perfectamente
        int resultadoBueno = Integer.compare(muyViejo.getEdad(), porNacer.getEdad());
        System.out.println("Resultado con Integer.compare (CORRECTO): " + resultadoBueno);
    }
}
