package com.example.ElMapacheBigoton.querys;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import com.example.ElMapacheBigoton.model.Cita;

import java.util.List;

@Repository
public class CitaRepositoryImpl implements CitaRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Cita> findByBarberoId(Long idBarbero) {
        String jpql = "SELECT c FROM Cita c WHERE c.barbero.idBarbero = :idBarbero";
        TypedQuery<Cita> query = entityManager.createQuery(jpql, Cita.class);
        query.setParameter("idBarbero", idBarbero);
        return query.getResultList();
    }
}
