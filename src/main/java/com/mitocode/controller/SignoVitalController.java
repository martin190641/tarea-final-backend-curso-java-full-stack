package com.mitocode.controller;

import com.mitocode.dto.SignoVitalDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.SignoVital;
import com.mitocode.service.ISignoVitalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/signoVital")
public class SignoVitalController {

    @Autowired
    private ISignoVitalService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<SignoVitalDTO>> listar() throws Exception{
        List<SignoVitalDTO> lista = service.listar().stream().map(p -> mapper.map(p, SignoVitalDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SignoVitalDTO> listarPorId(@PathVariable("id") Integer id) throws Exception{
        SignoVitalDTO dtoResponse;
        SignoVital obj = service.listarPorId(id);

        if(obj == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }else {
            dtoResponse = mapper.map(obj, SignoVitalDTO.class);
        }

        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> registrar(@Valid @RequestBody SignoVitalDTO dtoRequest) throws Exception{
        SignoVital p = mapper.map(dtoRequest, SignoVital.class);
        SignoVital obj = service.registrar(p);
        SignoVitalDTO dtoRespuesta = mapper.map(obj, SignoVitalDTO.class);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getIdSignoVital()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<SignoVitalDTO> modificar(@RequestBody SignoVitalDTO dtoRequest) throws Exception {
        SignoVital pac = service.listarPorId(dtoRequest.getIdSignoVital());

        if(pac == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + dtoRequest.getIdSignoVital());
        }

        SignoVital p = mapper.map(dtoRequest, SignoVital.class);

        SignoVital obj = service.modificar(p);

        SignoVitalDTO dtoResponse = mapper.map(obj, SignoVitalDTO.class);

        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
        SignoVital pac = service.listarPorId(id);

        if(pac == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }

        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<SignoVitalDTO>> listarPageable(Pageable page) throws Exception{
        Page<SignoVitalDTO> signoVital = service.listarPageable(page).map(p -> mapper.map(p, SignoVitalDTO.class));

        return new ResponseEntity<>(signoVital, HttpStatus.OK);
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<SignoVitalDTO> listarHateoasPorId(@PathVariable("id") Integer id) throws Exception{
        SignoVital obj = service.listarPorId(id);

        if(obj == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }

        SignoVitalDTO dto = mapper.map(obj, SignoVitalDTO.class);

        EntityModel<SignoVitalDTO> recurso = EntityModel.of(dto);
        //localhost:8080/signoVital/1
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).listarPorId(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).listarHateoasPorId(id));
        recurso.add(link1.withRel("signo-vital-recurso1"));
        recurso.add(link2.withRel("signo-vital-recurso2"));

        return recurso;
    }
}
