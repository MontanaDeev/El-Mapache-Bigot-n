package com.example.ElMapacheBigoton.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;

@Entity
@Table(name = "Servicio")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;
    @Column(nullable = false, length = 100)
    private String Descripcion;
    @Column(nullable = false)
    private double costo;

    public Servicio() {
    }

    public long getIdServicio() {
        return idServicio;
    }
    
    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "Servicio [Descripcion=" + Descripcion + ", costo=" + costo + ", idServicio=" + idServicio + "]";
    }
}
 
