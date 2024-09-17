package com.example.ElMapacheBigoton.controller;
import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.ElMapacheBigoton.model.Cita;
import com.example.ElMapacheBigoton.model.Servicio;
import com.example.ElMapacheBigoton.repository.ServicioRepository;
@RestController
@RequestMapping("/servicio")
@CrossOrigin(origins = "http://localhost:3000")
public class ServicioController {
    @Autowired
    ServicioRepository servicioRepository;

    @GetMapping()
    public ResponseEntity<Iterable<Servicio>> findAll() {
        return ResponseEntity.ok(servicioRepository.findAll());
    }

    @GetMapping("/{idServicio}")
    public ResponseEntity<Servicio> findById(@PathVariable Long idServicio) {
        Optional<Servicio> servicioOptional = servicioRepository.findById(idServicio);
        if (servicioOptional.isPresent()) {
            return ResponseEntity.ok(servicioOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Servicio nuevoServicio, UriComponentsBuilder ucb) {
        Servicio guardarServicio = servicioRepository.save(nuevoServicio);  
        URI uri = ucb.path("/servicio/{idServicio}").buildAndExpand(guardarServicio.getIdServicio()).toUri();
        return ResponseEntity.created(uri).build();  
    }

    @PutMapping("/{idServicio}")
    public ResponseEntity<Void> update(@PathVariable Long idServicio, @RequestBody Servicio servicioAct) {
        Optional<Servicio> servicioAnt = servicioRepository.findById(idServicio);
        if (servicioAnt.isPresent()) {
            servicioRepository.save(servicioAct);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idServicio}")
    public ResponseEntity<Void> delete(@PathVariable Long idServicio){
        Optional<Servicio> servicio = servicioRepository.findById(idServicio);
        if (servicio.isPresent()) {
            servicioRepository.deleteById(idServicio);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Obtener las citas de un servicio
    @GetMapping("/{idServicio}/citas")
    public ResponseEntity<Iterable<Cita>> getServicioCitas(@PathVariable Long idServicio){
        Optional<Servicio> servicioOptional = servicioRepository.findById(idServicio);
        if (!servicioOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(servicioOptional.get().getCitas());
    }
    
}
