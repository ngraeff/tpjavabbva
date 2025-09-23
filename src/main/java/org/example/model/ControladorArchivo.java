package org.example.model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class ControladorArchivo {

    public void guardarEnTxt(String archivo,List<Tarea> tareas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo)))  {
            for (Tarea t : tareas) {
                writer.write(t.toString());
                writer.newLine();
            }
            System.out.println("Tareas guardadas en " + archivo);
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    public List<Tarea> cargarDesdeTxt(String archivo,Integer siguienteId) {
        List<Tarea> tareas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {

                String[] partes = linea.split(",");
                int id = Integer.parseInt(partes[0].split(" ")[1]);
                String descripcion = partes[1].split(" ")[1];
                boolean completada = partes[2].split(" ")[1].equalsIgnoreCase("COMPLETADA");

                if (!completada){
                    String texto = "Vence el 25/09/2025 a las 20:26:21";
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Vence el' dd/MM/yyyy 'a las' HH:mm:ss");
                    LocalDateTime fecha = LocalDateTime.parse(texto, formatter);
                    Tarea tarea = new Tarea(id, descripcion, fecha);
                    tareas.add(tarea);
                }else{
                    Tarea tarea = new Tarea(id, descripcion);
                    tarea.marcarCompletada();
                    tareas.add(tarea);
                }
                siguienteId = Math.max(siguienteId, id + 1);
            }
            System.out.println("Tareas cargadas desde " + archivo);
        } catch (IOException e) {
            System.out.println("No se pudo cargar: " + e.getMessage());
        }
        return tareas;
    }
}
