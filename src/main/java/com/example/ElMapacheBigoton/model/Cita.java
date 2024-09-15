package com.example.ElMapacheBigoton.model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cita")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCita;
    @Column(nullable = false)
    private Date fecha;
    @Column(nullable = false)
    private Time hora;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCliente")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cliente cliente;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idBarbero")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Barbero barbero;

    @ManyToMany(cascade= CascadeType.ALL)
    @JsonBackReference
    @JoinTable(
        name= "CitaServicio",
        joinColumns= @JoinColumn(name = "idCita", referencedColumnName= "idCita"),
        inverseJoinColumns= @JoinColumn(name= "idServicio", referencedColumnName= "idServicio")
    )
    private List<Servicio> servicios;

    public Cita(long idCita, Date fecha, Time hora) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Cita() {

    }

    public Long getIdCita() {
        return idCita;
    }

    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Barbero getBarbero() {
        return barbero;
    }

    public void setBarbero(Barbero barbero) {
        this.barbero = barbero;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }
    
    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }    

    @Override
    public String toString() {
        return "Cita \n"
                + "Id: " + idCita
                + "\n Fecha: " + fecha
                + "\n Hora: " + hora
                + "\n Cliente: " + cliente
                + "\n Barbero: " + barbero;
    }

}
