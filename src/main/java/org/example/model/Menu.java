package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private List<Tarea> tareas;
    private Scanner escaner;
    private int siguienteId;

    public Menu() {
        this.tareas = new ArrayList<>();
        this.escaner = new Scanner(System.in);
        this.siguienteId = 1;
    }

    public void start() {
        int opcion;
        do {
            mostrarOpciones();
            opcion = escaner.nextInt();
            escaner.nextLine(); // limpiar buffer
            evaluarOpcion(opcion);
        } while (opcion != 0);
    }

    public void mostrarOpciones() {
        System.out.println("MENÚ:");
        System.out.println("1️⃣ Agregar tarea");
        System.out.println("2️⃣ Listar todas las tareas");
        System.out.println("3️⃣ Listar solo tareas completadas");
        System.out.println("4️⃣ Listar solo tareas pendientes");
        System.out.println("5️⃣ Eliminar tarea por ID");
        System.out.println("6️⃣ Marcar tarea como completada");
        System.out.println("0️⃣ Salir");
        System.out.print("Seleccione una opción: ");
    }

    public void evaluarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> agregarTarea();
            case 2 -> listarTodasLasTareas();
            case 3 -> listarTareasCompletas();
            case 4 -> listarTareasPendientes();
            case 5 -> eliminarTareaPorId();
            case 6 -> marcarTareaCompletada();
            case 0 -> System.out.println("Saliendo...");
            default -> System.out.println("Opción inválida.");
        }
    }
    public void agregarTarea() {
        String descripcion;
        do {
            System.out.print("Ingrese la descripción de la tarea: ");
            descripcion = escaner.nextLine().trim();
            if (descripcion.isEmpty()) {
                System.out.println("La descripción no puede estar vacía.");
            }
        } while (descripcion.isEmpty());
        this.tareas.add(new Tarea(siguienteId, descripcion));
        this.siguienteId++;
        System.out.println("Tarea agregada.");
    }

    public void listarTodasLasTareas() {
        if (this.tareas.isEmpty()) {
            System.out.println("Listado de tareas vacio.");
        }else {
            for (Tarea tarea : tareas) {
                System.out.println(tarea.toString());
            }
        }
    }

    public void listarTareasCompletas() {
        long cantidadDeTareasCompletas = this.tareas.stream().filter(tarea -> tarea.isCompletada()).count();

        if (cantidadDeTareasCompletas == 0) {
            System.out.println("No hay tareas completas");
        }else{
            for (Tarea tarea : tareas) {
                if (tarea.isCompletada()) {
                    System.out.println(tarea.toString());
                }
            }
        }

    }
    public void listarTareasPendientes() {
        long cantidadDeTareasPendientes = this.tareas.stream().filter(tarea -> !tarea.isCompletada()).count();

        if (cantidadDeTareasPendientes == 0){
            System.out.println("No hay tareas pendientes");
        } else{
            for (Tarea tarea : tareas) {
                if (!tarea.isCompletada()) {
                    System.out.println(tarea.toString());
                }
            }
        }
    }

    public void eliminarTareaPorId() {
        System.out.print("Ingrese el id de la tarea: ");
        int id = escaner.nextInt();
        if (tareas.removeIf(t -> t.getId() == id)) {
            System.out.println("Tarea eliminada.");
        } else {
            System.out.println("No existe una tarea con ese ID.");
        }
    }

    public void marcarTareaCompletada() {
        System.out.print("Ingrese el ID de la tarea a completar: ");
        int id = escaner.nextInt();
        if (tareas.isEmpty()) {
            System.out.println("No hay tareas listadas.");
        }else  {
            if (id < 0 ||  id > tareas.size()) {
                System.out.println("El ID de la tarea no existe");
            }
            else {
                for (Tarea tarea : tareas) {
                    if (tarea.getId() == id) {
                        if (tarea.isCompletada()) {
                            System.out.print("La tarea ya está completada.");
                        } else{
                            tarea.marcarCompletada();
                            System.out.print("La tarea se marcó como completada");
                        }
                    }
                }
            }
        }
    }
}
