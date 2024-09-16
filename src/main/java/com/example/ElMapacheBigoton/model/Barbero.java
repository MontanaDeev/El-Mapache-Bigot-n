package com.example.ElMapacheBigoton.model;

import jakarta.persistence.*;

@Entity
@Table (name = "barbero")
public class Barbero {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long idBarbero;
   private String nombre;
    
   public Barbero(String nombre){
    this.nombre = nombre;
   }

   public Barbero(){
   }
   public Long getIdBarbero(){
        return idBarbero;
   }
    public void setIdBarbero(Long idBarbero){
          this.idBarbero = idBarbero;
    }
    public String getNombre(){
        return nombre;
    }
    @Override
    public String toString (){
        return "Barbero"+
        "idBarbero" + idBarbero +
        "\nnombre"+ nombre ;
    }

}
