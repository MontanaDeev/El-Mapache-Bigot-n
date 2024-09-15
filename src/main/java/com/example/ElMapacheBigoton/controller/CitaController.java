package com.example.ElMapacheBigoton.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.ElMapacheBigoton.model.Barbero;
import com.example.ElMapacheBigoton.model.Cita;
import com.example.ElMapacheBigoton.model.Cliente;
import com.example.ElMapacheBigoton.model.Servicio;
import com.example.ElMapacheBigoton.repository.BarberoRepository;
import com.example.ElMapacheBigoton.repository.CitaRepository;
import com.example.ElMapacheBigoton.repository.ClienteRepository;

@RestController
@RequestMapping("/cita")
public class CitaController {

    @Autowired
    CitaRepository citaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    BarberoRepository barberoRepository;

    @GetMapping()
    public ResponseEntity<Iterable<Cita>> findAll() {
        return ResponseEntity.ok(citaRepository.findAll());
    }

    @GetMapping("/{idCita}")
    public ResponseEntity<Cita> findByID(@PathVariable Long idCita) {
        Optional<Cita> citaOptional = citaRepository.findById(idCita);
        if (citaOptional.isPresent()) {
            return ResponseEntity.ok(citaOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Cita cita, UriComponentsBuilder ucb) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(cita.getCliente().getIdCliente());
        if (!clienteOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Optional<Barbero> barberoOptional = barberoRepository.findById(cita.getBarbero().getIdBarbero());
        if (!barberoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        cita.setCliente(clienteOptional.get());
        cita.setBarbero(barberoOptional.get());
        Cita savedCita = citaRepository.save(cita);
        URI uri = ucb
                .path("cita/{idCita}")
                .buildAndExpand(savedCita.getIdCita())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{idCita}")
    public ResponseEntity<Void> update(@PathVariable Long idCita, @RequestBody Cita cita){
        Optional<Cliente> clienteOptional = clienteRepository.findById(cita.getCliente().getIdCliente());
        if (!clienteOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Optional<Barbero> barberoOptional = barberoRepository.findById(cita.getBarbero().getIdBarbero());
        if (!barberoOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Cita> citaOptional = citaRepository.findById(idCita);
        if(!citaOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }
        cita.setCliente(clienteOptional.get());
        cita.setBarbero(barberoOptional.get());
        cita.setIdCita(citaOptional.get().getIdCita());
        citaRepository.save(cita);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idCita}")
    public ResponseEntity<Void> delete(@PathVariable Long idCita) {
        Optional<Cita> citaOptional = citaRepository.findById(idCita);
        if (citaOptional.isPresent()) {
            citaRepository.deleteById(idCita);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    //Obtener los servicios de una cita
    @GetMapping("/{idCita}/servicios")
    public ResponseEntity<Iterable<Servicio>> getCitaServicios(@PathVariable Long idCita){
        Optional<Cita> citaOptional = citaRepository.findById(idCita);
        if (!citaOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(citaOptional.get().getServicios());
    }
}
