package com.example.ElMapacheBigoton.repository;
import com.example.ElMapacheBigoton.model.Barbero;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarberoRepository extends CrudRepository<Barbero, Long> {

}
