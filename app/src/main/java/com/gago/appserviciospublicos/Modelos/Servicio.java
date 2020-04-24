package com.gago.appserviciospublicos.Modelos;

import java.util.Calendar;

public class Servicio {
    private long id;
    private String direccion;
    private Calendar fecha;
    private int medida;
    private int tipoServicio;

    public Servicio(String direccion, Calendar fecha, int medida, int tipoServicio) {
        this.direccion = direccion;
        this.fecha = fecha;
        this.medida = medida;
        this.tipoServicio = tipoServicio;
    }

    public Servicio(long id, String direccion, Calendar fecha, int medida, int tipoServicio) {
        this.id = id;
        this.direccion = direccion;
        this.fecha = fecha;
        this.medida = medida;
        this.tipoServicio = tipoServicio;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public int getMedida() {
        return medida;
    }

    public void setMedida(int medida) {
        this.medida = medida;
    }

    public int getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(int tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String stingFecha() {
        return this.fecha.get(Calendar.DAY_OF_MONTH) + "-" + (this.fecha.get(Calendar.MONTH) + 1) + "-" +
                this.fecha.get(Calendar.YEAR) + " " + this.fecha.get(Calendar.HOUR_OF_DAY) + ":" +
                this.fecha.get(Calendar.MINUTE);
    }
}
