package com.example.ElMapacheBigoton.model;

import java.util.ArrayList;
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
@Table(name="Cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long idCliente;

    @Column(nullable = false, length =50)
    private String nombre;
    @Column(nullable = false, length =10)
    private String telefono;
    //RELACIONES
    //Un cliente puede tener muchas citas
    //la clase cita deberia tener una relacion de MUCHOS a UNO
    @OneToMany(mappedBy="cliente", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Cita> citas = new ArrayList<>();

    public Cliente(long idCliente, String nombre, String telefono) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public Cliente() {
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Cliente [idCliente=" + idCliente + ", nombre=" + nombre + ", telefono=" + telefono + "]";
    }
    
}
