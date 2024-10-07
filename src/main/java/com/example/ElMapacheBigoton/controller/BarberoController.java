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

import com.example.ElMapacheBigoton.model.Barbero;
import com.example.ElMapacheBigoton.repository.BarberoRepository;

@RestController
@RequestMapping("/barbero")
@CrossOrigin(origins = "http://localhost:3000")
public class BarberoController {
    @Autowired
    BarberoRepository barberoRepository;

    @GetMapping()
    public ResponseEntity<Iterable<Barbero>> findAll(){
        return ResponseEntity.ok(barberoRepository.findAll());
    } 
    @GetMapping("/{idBarbero}")
    public ResponseEntity<Barbero> findById(@PathVariable Long idBarbero){
    Optional<Barbero> barberoOptional = barberoRepository.findById(idBarbero);
        if(barberoOptional.isPresent()){
            return ResponseEntity.ok(barberoOptional.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Barbero newBarbero, UriComponentsBuilder ucb){
        Barbero savedBarbero = barberoRepository.save(newBarbero);
        URI uri = ucb
        .path("/barbero/{idBarbero}")
        .buildAndExpand(savedBarbero.getIdBarbero())
        .toUri();
        return ResponseEntity.created(uri).build();  
    }
    @PutMapping("/{idBarbero}")
    public ResponseEntity<Void> update(@PathVariable Long idBarbero, @RequestBody Barbero barberoActual){
        Barbero barberoant = barberoRepository.findById(idBarbero).get();
        if(barberoant != null){
           barberoRepository.save(barberoActual);
              return ResponseEntity.noContent().build();
      
    }
    return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{idBarbero}")
    public ResponseEntity<Void> delete(@PathVariable Long idBarbero){
        if(barberoRepository.findById(idBarbero).get() != null){
            barberoRepository.deleteById(idBarbero);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
