package org.example.model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ControladorArchivoTest {


    @Test
    void testGuardarYCargarTareas() {

        String archivo = "test_tareas.txt";
        ControladorArchivo controladorDePrueba = new ControladorArchivo();
        List<Tarea> tareasOriginal = new ArrayList<>();
        tareasOriginal.add(new Tarea(1, "Comprar comida", LocalDateTime.now().plusDays(1)));
        tareasOriginal.add(new Tarea(2, "Estudiar", LocalDateTime.now().plusDays(2)));

        controladorDePrueba.guardarEnTxt(archivo, tareasOriginal);

        List<Tarea> tareasCargadas = new ArrayList<>();
        tareasCargadas = controladorDePrueba.cargarDesdeTxt(archivo, 1, tareasCargadas);

        assertEquals(2, tareasCargadas.size());
        assertEquals("Comprar comida", tareasCargadas.get(0).getDescripcion());
        assertEquals("Estudiar", tareasCargadas.get(1).getDescripcion());

        // borrar archivo de prueba
        new File(archivo).delete();
    }


}

