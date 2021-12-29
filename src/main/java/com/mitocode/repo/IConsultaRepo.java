package com.mitocode.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mitocode.model.Consulta;

public interface IConsultaRepo extends IGenericRepo<Consulta, Integer>{
	
	//JPQL Java Persistence Query Language
	@Query("FROM Consulta c WHERE c.paciente.dni = :dni OR LOWER(c.paciente.nombres) LIKE %:nombreCompleto% OR LOWER(c.paciente.apellidos) LIKE %:nombreCompleto%")
	List<Consulta> buscar(@Param("dni") String dni, @Param("nombreCompleto") String nombreCompleto);

	//>=09-10-2021 // <10-10-2021
	@Query("FROM Consulta c WHERE c.fecha BETWEEN :fechaConsulta AND :fechaSgte")
	List<Consulta> buscarFecha(@Param("fechaConsulta") LocalDateTime fechaConsulta, @Param("fechaSgte") LocalDateTime fechaSgte);

	@Query(value = "select * from fn_listarResumen()", nativeQuery = true)
	List<Object[]> listaResumen();
	
	//cantidad	fecha
	//[1	,	"01/10/2021"]
	//[2	,	"09/10/2021"]
	//[5	,	"18/09/2021"]
}
