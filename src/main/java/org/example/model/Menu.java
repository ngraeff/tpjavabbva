package org.example.model;

import org.example.excepcion.TareaInexistenteException;

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

    /**
     * Loop principal del programa.
     * Ejecuta la funcion para ver las opciones y
     * evaluar el ingreso del usuario.
     *
     */
    public void start() {
        int opcion;
        do {
            mostrarOpciones();
            opcion = solicitarNumero("Ingrese una opcion del menu: ");
            evaluarOpcion(opcion);
        } while (opcion != 0);
    }

    /**
     * Imprime por pantalla todas las funciones del menu.
     *
     */
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
        System.out.println("9\uFE0F⃣ Cargar tareas de archivo");
        System.out.println("🔟 Grabar tareas de archivo");
        System.out.println("0️⃣ Salir");
    }

    /**
     * Evalua el ingreso del usuario y llama a la
     * funcion correspondiente.
     * @param opcion opcion ingresada por el usuario
     */
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

    /**
     * Solicita al usuario la descripcion de la tarea y la
     * agrega a la lista.
     */
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


        //limpiarConsolaFor();
        limpiarConsola();

        System.out.println("Agregando tarea...");
        tiempoEspera(1000);
        System.out.println("Tarea agregada.");
        tiempoEspera(1000);
        //limpiarConsolaFor();
        limpiarConsola();

    }

    /**
     * Lista todas las tareas cargadas.
     */
    public void listarTodasLasTareas() {
        //limpiarConsolaFor();
        limpiarConsola();
        if (this.tareas.isEmpty()) {
            System.out.println("Listado de tareas vacio.");
        }else {
            for (Tarea tarea : tareas) {
                System.out.println(tarea.toString());
            }
        }
        tiempoEspera(2000);
    }

    /**
     * Lista las tareas que ya fueron completadas.
     */
    public void listarTareasCompletas() {
        long cantidadDeTareasCompletas = this.tareas.stream().filter(tarea -> tarea.isCompletada()).count();
        //limpiarConsolaFor();
        limpiarConsola();
        if (cantidadDeTareasCompletas == 0) {
            System.out.println("No hay tareas completas");
        }else{
            for (Tarea tarea : tareas) {
                if (tarea.isCompletada()) {
                    System.out.println(tarea.toString());
                }
            }
        }
        tiempoEspera(2000);

    }

    /**
     * Lista las tareas que todavia no fueron completadas.
     */
    public void listarTareasPendientes() {
        long cantidadDeTareasPendientes = this.tareas.stream().filter(tarea -> !tarea.isCompletada()).count();

        //limpiarConsolaFor();
        limpiarConsola();
        if (cantidadDeTareasPendientes == 0){
            System.out.println("No hay tareas pendientes");
        } else{
            for (Tarea tarea : tareas) {
                if (!tarea.isCompletada()) {
                    System.out.println(tarea.toString());
                }
            }
        }
        tiempoEspera(2000);
    }

    /**
     * Solicita al usuario ID y elimina la tarea con el ID ingresado.
     */
    public void eliminarTareaPorId() {
        int id = solicitarNumero("Ingrese el id de la tarea: ");
        try {
            sacarTarea(id);
            System.out.println("Tarea eliminada.");

        }catch (TareaInexistenteException e){
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    /**
     * Solicita al usuario ID y marca como completada la tarea con el ID ingresado.
     */
    public void marcarTareaCompletada() {

        int id = solicitarNumero("Ingrese el ID de la tarea a completar: ");
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

    /**
     * Lista las tareas ordenadas por fecha de vencimiento de menor a mayor
     */
    public void ordenarPorFechaDeVencimientoDeMenorAMayor(){
        //limpiarConsolaFor();
        limpiarConsola();
        if (tareas.isEmpty()){
            System.out.println("No hay tareas listadas.");
        }else{
            this.tareas.sort(Comparator.comparing(Tarea::getFechaDeVencimiento));
            System.out.println("Tareas ordenadas por fecha de vencimiento:");
            tareas.forEach(System.out::println);
        }
        tiempoEspera(2000);
    }

    /**
     * Lista las tareas ordenadas por fecha de vencimiento de mayor a menor
     */
    public void ordenarPorFechaDeVencimientoDeMayorAMenor(){
        //limpiarConsolaFor();
        limpiarConsola();
        if (tareas.isEmpty()) {
            System.out.println("No hay tareas listadas.");
        }
        else {
            this.tareas.sort(Comparator.comparing(Tarea::getFechaDeVencimiento).reversed());
            System.out.println("Tareas ordenadas por fecha de vencimiento:");
            tareas.forEach(System.out::println);
        }
        tiempoEspera(2000);
    }

    /**
     * Carga las tareas que se encuentran en el archivo
     */
    public void cargarTareasDeArchivo() {
        int cantidadDeTareasInicial = this.tareas.size();
        String archivo = "src/main/resources/tareas.txt";
        //C:\Users\Nicolas Daniel G\IdeaProjects\tpjavabbva\src\main\resources\tareas.txt
        ControladorArchivo controlador = new ControladorArchivo();
        if (tareas.isEmpty()){
            this.tareas = controlador.cargarDesdeTxt(archivo, 1,tareas);

        }else {
            this.tareas = controlador.cargarDesdeTxt(archivo,tareas.get(tareas.size()-1).getId() + 1,tareas);
        }

        //limpiarConsolaFor();
        limpiarConsola();
        System.out.println("Cargando tareas...");
        tiempoEspera(1000);
        if (this.tareas.isEmpty()){
            System.out.println("No hay tareas para cargar.");
        }else{
            int cantidadDeTareasFinal = this.tareas.size();
            System.out.println("Se cargaron " + (cantidadDeTareasFinal-cantidadDeTareasInicial) + " tareas");
        }
        tiempoEspera(1000);
        //limpiarConsolaFor();
        limpiarConsola();
    }

    /**
     * Graba las tareas cargadas a un archivo txt
     */
    public void grabarTareasAArchivo() {
        String archivo = "src/main/resources/tareas.txt";
        ControladorArchivo controlador = new ControladorArchivo();
        controlador.guardarEnTxt(archivo,tareas);

        //limpiarConsolaFor();
        limpiarConsola();
        System.out.println("grabando tareas...");
        tiempoEspera(1000);
        if (this.tareas.isEmpty()){
            System.out.println("No hay tareas para grabar.");
        }else{
            int cantidadDeTareas = this.tareas.size();
            System.out.println("Se grabaron " + cantidadDeTareas + " tareas");
        }
        tiempoEspera(1000);
        //limpiarConsolaFor();
        limpiarConsola();
    }

    /**
     * Elimina una tarea de la lista según su ID.
     *
     * @param idTarea ID de la tarea que se desea eliminar
     * @throws TareaInexistenteException si no se encuentra ninguna tarea con el ID ingresado
     */
    private void sacarTarea(int idTarea) throws TareaInexistenteException {
        //limpiarConsolaFor();
        limpiarConsola();
        System.out.println("Sacando tarea " + idTarea + "...");
        tiempoEspera(1000);
        if (!tareas.removeIf(t -> t.getId() == idTarea)){
            throw new TareaInexistenteException("La tarea es inexistente");
        }
        System.out.println("La tarea " + idTarea + " fue eliminada con exito");
        tiempoEspera(1000);
        //limpiarConsolaFor();
        limpiarConsola();


    }

    /**
     * Limpia la consola imprimiendo 50 espacios
     */
    public void limpiarConsolaFor(){
        for (int i = 0; i<50;i++){
            System.out.println(" ");
        }
    }

    /**
     * Limpia la consola usando ProcessBuilder (solo por terminal)
     */
    // solo funciona en la consola (no en Inteliji)
    private void limpiarConsola() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("No se pudo limpiar la consola.");
        }
    }

    /**
     * Genera un delay en la ejecucion
     * @param milisegundos cantidad de milisegundos para la espera
     */
    private void tiempoEspera(int milisegundos){
        try {
            Thread.sleep(milisegundos); //
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Solicita un ingreso al usuario y valida que este mismo cumpla condiciones para que
     * sea correcto.
     *
     * @param textoAPedir texto que se muestra al usuario a la hora de hacer el ingreso.
     * @return numero ingresado
     */
    private int solicitarNumero(String textoAPedir) {
        int opcion = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            System.out.print(textoAPedir);
            String linea = escaner.nextLine().trim();

            if (linea.isEmpty()) {
                System.out.println("Error: no se ingresó ningún valor.");
                continue;
            }
            try {
                opcion = Integer.parseInt(linea);
                entradaValida = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: solo se permiten números.");
            }
        }
        return opcion;
    }
}
