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

import com.example.ElMapacheBigoton.model.Cliente;
import com.example.ElMapacheBigoton.repository.ClienteRepository;

@RestController
@RequestMapping("/cliente")
@CrossOrigin(origins = "http://localhost:3000")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @GetMapping()
    public ResponseEntity<Iterable<Cliente>> findAll() {
        return ResponseEntity.ok(clienteRepository.findAll());
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> findById(@PathVariable Long idCliente) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(idCliente);
        if (clienteOptional.isPresent()) {
            return ResponseEntity.ok(clienteOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Cliente nuevoCliente, UriComponentsBuilder ucb) {
        Cliente guardarCliente = clienteRepository.save(nuevoCliente);  // Aquí guarda el cliente
        URI uri = ucb.path("/cliente/{idCliente}").buildAndExpand(guardarCliente.getIdCliente()).toUri();  // Aquí construye la URI
        return ResponseEntity.created(uri).build();  // Aquí devuelve el status 201 Created
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<Void> update(@PathVariable Long idCliente, @RequestBody Cliente clienteAct) {
        Optional<Cliente> clienteAnt = clienteRepository.findById(idCliente);
        if (clienteAnt.isPresent()) {
            clienteRepository.save(clienteAct);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> delete(@PathVariable Long idCliente) {
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        if (cliente.isPresent()) {
            clienteRepository.deleteById(idCliente);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
