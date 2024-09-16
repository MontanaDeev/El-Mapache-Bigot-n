package com.example.ElMapacheBigoton.model;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Servicio")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Long idServicio;
    @Column(nullable = false, length = 100)
    private String Descripcion;
    @Column(nullable = false)
    private double costo;

    @ManyToMany(cascade= CascadeType.ALL)
    @JoinTable(
        name= "cita_servicio",
        joinColumns= @JoinColumn(name = "id_servicio", referencedColumnName= "id_servicio"),
        inverseJoinColumns = @JoinColumn(name= "id_cita", referencedColumnName= "id_cita")
    )
    private List<Cita> citas;

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

    public List<Cita> getCitas() {
        return citas;
    }
    
    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }    


    @Override
    public String toString() {
        return "Servicio [Descripcion=" + Descripcion + ", costo=" + costo + ", idServicio=" + idServicio + "]";
    }
}
 
