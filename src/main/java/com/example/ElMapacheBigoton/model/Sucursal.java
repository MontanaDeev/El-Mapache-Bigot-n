package com.example.ElMapacheBigoton.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Sucursal")
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSucursal;

    @Column(nullable = false)
    private String direccion;

    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Barbero> barberos;

    public Sucursal(Integer idSucursal, String direccion) {
        this.idSucursal = idSucursal;
        this.direccion = direccion;
    }

    public Sucursal() {

    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public List<Barbero> getBarberos() {
        return barberos;
    }

    public void setBarberos(List<Barbero> barberos) {
        this.barberos = barberos;
    }

    @Override
    public String toString() {
        return "Sucursal  {idSucursal = " + idSucursal + "Dirección = " + direccion + "}";
    }
}
