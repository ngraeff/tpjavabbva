package org.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tarea {
    private int id;
    private String descripcion;
    private boolean completada;
    private LocalDateTime fechaDeVencimiento;

    public Tarea(int id, String descripcion, LocalDateTime fechaDeVencimiento) {
        this.id = id;
        this.descripcion = descripcion;
        this.completada = false;
        this.fechaDeVencimiento = fechaDeVencimiento;
    }

    public Tarea(int id, String descripcion) {
        this(id, descripcion, LocalDateTime.now().plusDays(3));
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public boolean isCompletada() {
        return completada;
    }

    public void marcarCompletada() {
        this.completada = !this.completada;
    }

    public LocalDateTime getFechaDeVencimiento() {
        return fechaDeVencimiento;
    }


    @Override
    public String toString() {
        String estado = completada ? "Tarea completada" : "Tarea pendiente";
        String resultado = "Tarea " + id + ",Descripción: " + descripcion + "," + estado;

        if (!completada) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'a las' HH:mm:ss");
            resultado += ",Vence el " + fechaDeVencimiento.format(formatter);
        }
        return resultado;
    }

}
