package com.example.ElMapacheBigoton.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class CitaDTO {
    private Long idCita;
    private Date fecha;
    private Date hora;
    private ClienteDTO cliente;
    private BarberoDTO barbero;
    private List<ServicioDTO> servicios;
}
