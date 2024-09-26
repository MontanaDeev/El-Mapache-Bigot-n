package com.example.ElMapacheBigoton.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ElMapacheBigoton.model.Sucursal;

@Repository
public interface SucursalRepository extends CrudRepository<Sucursal, Integer>{
}