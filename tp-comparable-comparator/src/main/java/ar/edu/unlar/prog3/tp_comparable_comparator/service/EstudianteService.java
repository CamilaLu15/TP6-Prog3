package ar.edu.unlar.prog3.tp_comparable_comparator.service;

import ar.edu.unlar.prog3.tp_comparable_comparator.domain.Estudiante;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EstudianteService {

    
    private final List<Estudiante> estudiantes = new ArrayList<>();

   
    private final Map<String, Comparator<Estudiante>> estrategiasDeOrdenamiento = new HashMap<>();

    @PostConstruct
    public void inicializar() {
        estudiantes.add(new Estudiante("LU-2024-001", "Martín Quiroga", 8.5, 22, 18));
        estudiantes.add(new Estudiante("LU-2024-002", "Valeria Díaz", 8.5, 20, 15));
        estudiantes.add(new Estudiante("LU-2024-003", "Facundo Castro", 7.2, 24, 22));
        estudiantes.add(new Estudiante("LU-2024-004", "Camila Torres", 9.1, 21, 24));
        estudiantes.add(new Estudiante("LU-2024-005", "Lucas González", 9.1, 23, 24));
        estudiantes.add(new Estudiante("LU-2024-006", "Agustina López", 6.8, 19, 10));
        estudiantes.add(new Estudiante("LU-2024-007", "Nahuel Herrera", 7.5, 22, 14));
        estudiantes.add(new Estudiante("LU-2024-008", "Florencia Ríos", 8.9, 25, 20));
        estudiantes.add(new Estudiante("LU-2024-009", "Tomás Sosa", 6.5, 20, 12));
        estudiantes.add(new Estudiante("LU-2024-010", "Lucía Fernández", 7.8, 21, 16));

       
        estrategiasDeOrdenamiento.put("promedio", Comparator.comparingDouble(Estudiante::getPromedio).reversed());
        estrategiasDeOrdenamiento.put("edad", Comparator.comparingInt(Estudiante::getEdad));
        estrategiasDeOrdenamiento.put("nombre", Comparator.comparing(Estudiante::getNombre));
        estrategiasDeOrdenamiento.put("materiasAprobadas", Comparator.comparingInt(Estudiante::getCantidadMateriasAprobadas));
        estrategiasDeOrdenamiento.put("legajo", Comparator.comparing(Estudiante::getLegajo));
    }

   
    public List<Estudiante> ordenar(List<Estudiante> listaAOrdenar, String sortBy, String order) {
        
        
        Comparator<Estudiante> comparator = estrategiasDeOrdenamiento.get(sortBy);

       
        if (comparator == null) {
            throw new IllegalArgumentException("Criterio de ordenamiento no válido: " + sortBy);
        }

       
        comparator = comparator.thenComparing(Estudiante::getLegajo);

       
        if ("desc".equalsIgnoreCase(order)) { 
      
            comparator = comparator.reversed();
        }
        return listaAOrdenar.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

     public List<Estudiante> obtenerTodos() {
        return this.estudiantes;
    }
}
