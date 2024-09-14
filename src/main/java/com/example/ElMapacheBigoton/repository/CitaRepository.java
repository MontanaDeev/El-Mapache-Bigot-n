package com.example.ElMapacheBigoton.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.ElMapacheBigoton.model.Cita;

@Repository
public interface CitaRepository extends CrudRepository<Cita, Long>{

}
