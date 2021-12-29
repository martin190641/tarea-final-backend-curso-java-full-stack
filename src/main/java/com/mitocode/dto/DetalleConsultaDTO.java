package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DetalleConsultaDTO {

	private Integer idDetalle;
	@JsonIgnore
	private ConsultaDTO consultaDTO;
	private String diagnostico;
	private String tratamiento;

	public Integer getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
	}

	public ConsultaDTO getConsultaDTO() {
		return consultaDTO;
	}

	public void setConsultaDTO(ConsultaDTO consultaDTO) {
		this.consultaDTO = consultaDTO;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

}
