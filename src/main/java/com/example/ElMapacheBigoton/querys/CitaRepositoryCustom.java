package com.example.ElMapacheBigoton.querys;

import java.util.List;

import com.example.ElMapacheBigoton.model.Cita;

public interface CitaRepositoryCustom {
    List<Cita> findByBarberoId(Long idBarbero);
}
