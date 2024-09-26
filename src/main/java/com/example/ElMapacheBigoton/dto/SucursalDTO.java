package com.example.ElMapacheBigoton.dto;

import java.util.List;

import lombok.Data;

@Data
public class SucursalDTO {
    private Integer idSucursal;
    private String direccion;
    private List<BarberoDTO> barberos;
}

