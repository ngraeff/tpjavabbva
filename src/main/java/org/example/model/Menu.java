package org.example.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private List<Tarea> tareas;
    private Scanner escaner;

    public Menu() {
        this.tareas = new ArrayList<>();
        this.escaner = new Scanner(System.in);
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
        System.out.println("7️⃣ Ordenar tareas por fecha de vencimiento de menor a mayor");
        System.out.println("8️⃣ Ordenar tareas por fecha de vencimiento de mayor a menor");
        System.out.println("9\uFE0F⃣Cargar tareas de archivo");
        System.out.println("🔟Grabar tareas de archivo");
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
            case 7 -> ordenarPorFechaDeVencimientoDeMayorAMenor();
            case 8 -> ordenarPorFechaDeVencimientoDeMenorAMayor();
            case 9 -> cargarTareasDeArchivo();
            case 10 -> grabarTareasAArchivo();
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
        if (tareas.isEmpty()){
            this.tareas.add(new Tarea(1, descripcion));
        }else{
            int numero = tareas.get(tareas.size()-1).getId() + 1;
            tareas.add(new Tarea(numero, descripcion));
        }
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
                            System.out.println("La tarea ya está completada.");
                        } else{
                            tarea.marcarCompletada();
                            System.out.println("La tarea se marcó como completada");
                        }
                    }
                }
            }
        }
    }

    public void ordenarPorFechaDeVencimientoDeMenorAMayor(){

        if (tareas.isEmpty()){
            System.out.println("No hay tareas listadas.");
        }else{
            this.tareas.sort(Comparator.comparing(Tarea::getFechaDeVencimiento));
            System.out.println("Tareas ordenadas por fecha de vencimiento:");
            tareas.forEach(System.out::println);
        }
    }

    public void ordenarPorFechaDeVencimientoDeMayorAMenor(){

        if (tareas.isEmpty()) {
            System.out.println("No hay tareas listadas.");
        }
        else {
            this.tareas.sort(Comparator.comparing(Tarea::getFechaDeVencimiento).reversed());
            System.out.println("Tareas ordenadas por fecha de vencimiento:");
            tareas.forEach(System.out::println);
        }
    }

    public void cargarTareasDeArchivo() {

        String archivo = "src/main/resources/tareas.txt";
        ControladorArchivo controlador = new ControladorArchivo();
        if (tareas.isEmpty()){
            this.tareas = controlador.cargarDesdeTxt(archivo, 1);

        }else {
            this.tareas = controlador.cargarDesdeTxt(archivo,tareas.get(tareas.size()-1).getId() + 1);
        }
    }

    public void grabarTareasAArchivo() {
        String archivo = "src/main/resources/tareas.txt";
        ControladorArchivo controlador = new ControladorArchivo();
        controlador.guardarEnTxt(archivo,tareas);
    }
}
