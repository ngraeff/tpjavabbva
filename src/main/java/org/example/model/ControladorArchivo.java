package org.example.model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class ControladorArchivo {

    /**
     * Graba el txt con las tareas cargadas en el programa.
     *
     * @param archivo direccion del archivo a grabar.
     * @param tareas tareas cargadas en el programa.
     */
    public void guardarEnTxt(String archivo,List<Tarea> tareas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo)))  {
            for (Tarea t : tareas) {
                writer.write(t.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    /**
     * Carga al programa las tareas ya cargadas que están en un txt.
     *
     * @param archivo direccion del archivo a cargar.
     * @param siguienteId id de la siguiente tarea para cargar.
     * @param tareas tareas ya cargadas en el programa.
     * @return lista de la Tareas cargadas.
     */
    public List<Tarea> cargarDesdeTxt(String archivo,Integer siguienteId, List<Tarea> tareas) {

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {

                String[] partes = linea.split(",");
                //int id = Integer.parseInt(partes[0].split(" ")[1]);
                int id = siguienteId;
                String descripcion = partes[1].split(" ")[1];
                boolean completada = partes[2].split(" ")[1].equalsIgnoreCase("COMPLETADA");

                if (!completada){
                    String texto = partes[3];
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Vence el' dd/MM/yyyy 'a las' HH:mm:ss");
                    LocalDateTime fecha = LocalDateTime.parse(texto, formatter);
                    Tarea tarea = new Tarea(id, descripcion, fecha);
                    tareas.add(tarea);
                }else{
                    Tarea tarea = new Tarea(id, descripcion);
                    tarea.marcarCompletada();
                    tareas.add(tarea);
                }
                siguienteId++;
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar: " + e.getMessage());
        }
        return tareas;
    }
}
