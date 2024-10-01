package com.example.ElMapacheBigoton.controller;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.ElMapacheBigoton.model.Barbero;
import com.example.ElMapacheBigoton.model.Sucursal;
import com.example.ElMapacheBigoton.repository.BarberoRepository;
import com.example.ElMapacheBigoton.repository.SucursalRepository;

@RestController
@RequestMapping("/sucursal")
@CrossOrigin(origins = "http://localhost:3000")
public class SucursalController {

    @Autowired
    SucursalRepository sucursalRepository;

    @Autowired
    BarberoRepository barberoRepository;

    @GetMapping()
    public ResponseEntity<Iterable<Sucursal>> findAll() {
        return ResponseEntity.ok(sucursalRepository.findAll());
    }

    @GetMapping("/{idSucursal}")
    public ResponseEntity<Sucursal> findById(@PathVariable Integer idSucursal) {
        Optional<Sucursal> sucursalOptional = sucursalRepository.findById(idSucursal);
        if (sucursalOptional.isPresent()) {
            return ResponseEntity.ok(sucursalOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Sucursal sucursal, UriComponentsBuilder ucb) {
        if (sucursal == null || sucursal.getBarberos() == null) {
            return ResponseEntity.badRequest().body("Invalid data: Missing required fields");
        }
    
        // Validar barberos y asegurarse de que están gestionados
        List<Barbero> barberosValidados = sucursal.getBarberos().stream()
                .map(barbero -> barberoRepository.findById(barbero.getIdBarbero())
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    
        if (barberosValidados.size() != sucursal.getBarberos().size()) {
            return ResponseEntity.unprocessableEntity().body("Some barbers not found");
        }
    
        // Configurar la sucursal con las entidades gestionadas
        sucursal.setBarberos(barberosValidados);
        
        try {
            // Guardar la sucursal en la base de datos
            Sucursal savedSucursal = sucursalRepository.save(sucursal);
            URI uri = ucb
                    .path("/sucursal/{idSucursal}")
                    .buildAndExpand(savedSucursal.getIdSucursal())
                    .toUri();
            return ResponseEntity.created(uri).build();
        } catch (Exception e) {
            // Log the error and return an appropriate response
            e.printStackTrace(); // Consider using a logger instead
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating sucursal: " + e.getMessage());
        }
    }
    

    @DeleteMapping("/{idSucursal}")
    public ResponseEntity<Void> delete(@PathVariable Integer idSucursal) {
        Optional<Sucursal> sucursalOptional = sucursalRepository.findById(idSucursal);
        if (sucursalOptional.isPresent()) {
            sucursalRepository.deleteById(idSucursal);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
