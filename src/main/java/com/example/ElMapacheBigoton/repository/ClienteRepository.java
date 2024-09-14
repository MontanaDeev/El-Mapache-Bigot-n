package com.example.ElMapacheBigoton.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ElMapacheBigoton.model.Cliente;
@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long>{

}
