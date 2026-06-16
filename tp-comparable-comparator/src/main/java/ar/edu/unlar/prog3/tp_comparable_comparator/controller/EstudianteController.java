package ar.edu.unlar.prog3.tp_comparable_comparator.controller;

import ar.edu.unlar.prog3.tp_comparable_comparator.domain.Estudiante;
import ar.edu.unlar.prog3.tp_comparable_comparator.service.EstudianteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

   
    @GetMapping
    public ResponseEntity<List<Estudiante>> obtenerEstudiantes(
            @RequestParam(defaultValue = "promedio") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        
       
        List<Estudiante> listaBase = estudianteService.obtenerTodos();
        List<Estudiante> estudiantesOrdenados = estudianteService.ordenar(listaBase, sortBy, order);
        return ResponseEntity.ok(estudiantesOrdenados);
    }

    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> manejarErrorDeCriterio(IllegalArgumentException ex) {
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        
    
        errorResponse.put("error", "Criterio de ordenamiento no válido");
        
        String criterioRecibido = ex.getMessage().replace("Criterio de ordenamiento no válido: ", "");
        errorResponse.put("criterioRecibido", criterioRecibido);
        
        errorResponse.put("criteriosAceptados", List.of("promedio", "edad", "nombre", "materiasAprobadas", "legajo"));
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
}

