package com.example.ElMapacheBigoton.controller;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

import com.example.ElMapacheBigoton.dto.BarberoDTO;
import com.example.ElMapacheBigoton.dto.CitaDTO;
import com.example.ElMapacheBigoton.dto.ClienteDTO;
import com.example.ElMapacheBigoton.dto.ServicioDTO;
import com.example.ElMapacheBigoton.model.Barbero;
import com.example.ElMapacheBigoton.model.Cita;
import com.example.ElMapacheBigoton.model.Cliente;
import com.example.ElMapacheBigoton.model.Servicio;
import com.example.ElMapacheBigoton.repository.BarberoRepository;
import com.example.ElMapacheBigoton.repository.CitaRepository;
import com.example.ElMapacheBigoton.repository.ClienteRepository;
import com.example.ElMapacheBigoton.repository.ServicioRepository;

@RestController
@RequestMapping("/cita")
@CrossOrigin(origins = "http://localhost:3000")
public class CitaController {

    @Autowired
    CitaRepository citaRepository;
    @Autowired
    ServicioRepository servicioRepository;
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
            Cita cita = citaOptional.get();
            return ResponseEntity.ok(cita);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/consulta")
public ResponseEntity<Iterable<CitaDTO>> findAllCitas() {
    Iterable<Cita> citas = citaRepository.findAll();

    List<CitaDTO> citasDTO = StreamSupport.stream(citas.spliterator(), false)
        .map(cita -> {
            CitaDTO citaDTO = new CitaDTO();
            citaDTO.setIdCita(cita.getIdCita());
            citaDTO.setFecha(cita.getFecha());
            citaDTO.setHora(cita.getHora());

            // Mapea Cliente
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setIdCliente(cita.getCliente().getIdCliente());
            clienteDTO.setNombre(cita.getCliente().getNombre());
            clienteDTO.setTelefono(cita.getCliente().getTelefono());
            citaDTO.setCliente(clienteDTO);

            // Mapea Barbero
            BarberoDTO barberoDTO = new BarberoDTO();
            barberoDTO.setIdBarbero(cita.getBarbero().getIdBarbero());
            barberoDTO.setNombre(cita.getBarbero().getNombre());
            citaDTO.setBarbero(barberoDTO);

            // Mapea Servicios
            List<ServicioDTO> serviciosDTO = cita.getServicios().stream().map(servicio -> {
                ServicioDTO servicioDTO = new ServicioDTO();
                servicioDTO.setIdServicio(servicio.getIdServicio());
                servicioDTO.setCosto(servicio.getCosto());
                servicioDTO.setDescripcion(servicio.getDescripcion());
                return servicioDTO;
            }).collect(Collectors.toList());
            citaDTO.setServicios(serviciosDTO);

            return citaDTO;
        }).collect(Collectors.toList());

    return ResponseEntity.ok(citasDTO);
}



    @GetMapping("/consulta/{idCita}")
    public ResponseEntity<CitaDTO> findByIdQuery(@PathVariable Long idCita) {
        Optional<Cita> citaOptional = citaRepository.findById(idCita);
        if (citaOptional.isPresent()) {
            Cita cita = citaOptional.get();

            // Mapea la entidad Cita al DTO
            CitaDTO citaDTO = new CitaDTO();
            citaDTO.setIdCita(cita.getIdCita());
            citaDTO.setFecha(cita.getFecha());
            citaDTO.setHora(cita.getHora());

            // Mapea Cliente y Barbero al DTO
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setIdCliente(cita.getCliente().getIdCliente());
            clienteDTO.setNombre(cita.getCliente().getNombre());
            clienteDTO.setTelefono(cita.getCliente().getTelefono());

            BarberoDTO barberoDTO = new BarberoDTO();
            barberoDTO.setIdBarbero(cita.getBarbero().getIdBarbero());
            barberoDTO.setNombre(cita.getBarbero().getNombre());

            citaDTO.setCliente(clienteDTO);
            citaDTO.setBarbero(barberoDTO);

            // Mapea la lista de servicios
            List<ServicioDTO> serviciosDTO = cita.getServicios().stream().map(servicio -> {
                ServicioDTO servicioDTO = new ServicioDTO();
                servicioDTO.setIdServicio(servicio.getIdServicio());
                servicioDTO.setCosto(servicio.getCosto());
                servicioDTO.setDescripcion(servicio.getDescripcion());
                return servicioDTO;
            }).collect(Collectors.toList());

            citaDTO.setServicios(serviciosDTO);

            return ResponseEntity.ok(citaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Cita cita, UriComponentsBuilder ucb) {
        try {
            // Validar si la cita está vacía o nula
            if (cita == null || cita.getCliente() == null || cita.getBarbero() == null || cita.getServicios() == null) {
                return ResponseEntity.badRequest().body("Invalid data: Missing required fields");
            }

            // Validar cliente
            Optional<Cliente> clienteOptional = clienteRepository.findById(cita.getCliente().getIdCliente());
            if (!clienteOptional.isPresent()) {
                return ResponseEntity.unprocessableEntity().body("Cliente not found");
            }

            // Validar barbero
            Optional<Barbero> barberoOptional = barberoRepository.findById(cita.getBarbero().getIdBarbero());
            if (!barberoOptional.isPresent()) {
                return ResponseEntity.unprocessableEntity().body("Barbero not found");
            }

            // Validar servicios y asegurarse de que están gestionados
            List<Servicio> serviciosValidos = cita.getServicios().stream()
                    .map(servicio -> servicioRepository.findById(servicio.getIdServicio())
                    .orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (serviciosValidos.size() != cita.getServicios().size()) {
                return ResponseEntity.unprocessableEntity().body("Some services not found");
            }

            // Configurar la cita con las entidades gestionadas
            cita.setCliente(clienteOptional.get());
            cita.setBarbero(barberoOptional.get());
            cita.setServicios(serviciosValidos);

            // Guardar la cita en la base de datos
            Cita savedCita = citaRepository.save(cita);
            URI uri = ucb
                    .path("/citas/{idCita}")
                    .buildAndExpand(savedCita.getIdCita())
                    .toUri();
            return ResponseEntity.created(uri).build();
        } catch (Exception e) {
            // Log the error and return an appropriate response
            e.printStackTrace(); // or use a logger
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating cita: " + e.getMessage());
        }
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
    public ResponseEntity<Iterable<Servicio>> getCitaServicios(@PathVariable Long idCita) {
        Optional<Cita> citaOptional = citaRepository.findById(idCita);
        if (!citaOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(citaOptional.get().getServicios());
    }
}
