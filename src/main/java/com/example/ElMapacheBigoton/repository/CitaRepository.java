package com.example.ElMapacheBigoton.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.ElMapacheBigoton.model.Cita;
import com.example.ElMapacheBigoton.querys.CitaRepositoryCustom;

@Repository
public interface CitaRepository extends CrudRepository<Cita, Long>, CitaRepositoryCustom {
    // No es necesario definir findByBarberoId aquí, ya que se implementará en
    // CitaRepositoryImpl
}
