package com.example.ElMapacheBigoton.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
@Table(name = "barbero")
public class Barbero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBarbero;
    private String nombre;
    @OneToMany(mappedBy = "barbero", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cita> citas = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "idSucursal")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Sucursal sucursal;

    public Barbero(String nombre) {
        this.nombre = nombre;
    }

    public Barbero() {
    }

    public Long getIdBarbero() {
        return idBarbero;
    }

    public void setIdBarbero(Long idBarbero) {
        this.idBarbero = idBarbero;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public String toString() {
        return "Barbero" +
                "idBarbero" + idBarbero +
                "\nnombre" + nombre;
    }

}
