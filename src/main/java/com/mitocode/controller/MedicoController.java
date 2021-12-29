package com.mitocode.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.dto.MedicoDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Medico;
import com.mitocode.service.IMedicoService;


@RestController
@RequestMapping("/medicos")
public class MedicoController {

	@Autowired
	private IMedicoService service;
	
	@Autowired
	private ModelMapper mapper;
	
	@PreAuthorize("@authServiceImpl.tieneAcceso('listar')")
	//@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	@GetMapping
	public ResponseEntity<List<MedicoDTO>> listar() throws Exception{				
		List<MedicoDTO> lista = service.listar().stream().map(p -> mapper.map(p, MedicoDTO.class)).collect(Collectors.toList());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MedicoDTO> listarPorId(@PathVariable("id") Integer id) throws Exception{
		MedicoDTO dtoResponse;
		Medico obj = service.listarPorId(id); //Medico		

		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}else {
			dtoResponse = mapper.map(obj, MedicoDTO.class); //MedicoDTO
		}
		
		return new ResponseEntity<>(dtoResponse, HttpStatus.OK); 		
	}
	
	/*@PostMapping
	public ResponseEntity<MedicoDTO> registrar(@Valid @RequestBody MedicoDTO dtoRequest) throws Exception{
		Medico p = mapper.map(dtoRequest, Medico.class);
		Medico obj = service.registrar(p);
		MedicoDTO dtoResponse = mapper.map(obj, MedicoDTO.class);
		return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED); 		
	}*/
	
	@PostMapping
	public ResponseEntity<Void> registrar(@Valid @RequestBody MedicoDTO dtoRequest) throws Exception{
		Medico p = mapper.map(dtoRequest, Medico.class);
		Medico obj = service.registrar(p);
		MedicoDTO dtoResponse = mapper.map(obj, MedicoDTO.class);
		//localhost:8080/medicos/1
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdMedico()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<MedicoDTO> modificar(@RequestBody MedicoDTO dtoRequest) throws Exception {
		Medico pac = service.listarPorId(dtoRequest.getIdMedico());
		
		if(pac == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + dtoRequest.getIdMedico());	
		}
		
		Medico p = mapper.map(dtoRequest, Medico.class);
		 
		Medico obj = service.modificar(p);
		
		MedicoDTO dtoResponse = mapper.map(obj, MedicoDTO.class);
		
		return new ResponseEntity<>(dtoResponse, HttpStatus.OK);		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Medico pac = service.listarPorId(id);
		
		if(pac == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		
		service.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/hateoas/{id}")
	public EntityModel<MedicoDTO> listarHateoasPorId(@PathVariable("id") Integer id) throws Exception{
		Medico obj = service.listarPorId(id);
		
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		
		MedicoDTO dto = mapper.map(obj, MedicoDTO.class);
		
		EntityModel<MedicoDTO> recurso = EntityModel.of(dto);
		//localhost:8080/pacientes/1
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).listarPorId(id));
		WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).listarHateoasPorId(id));
		recurso.add(link1.withRel("medico-recurso1"));
		recurso.add(link2.withRel("medico-recurso2"));
		
		return recurso;
	}
}
