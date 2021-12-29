package com.mitocode.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Size;

import com.mitocode.model.Paciente;

public class SignoVitalDTO {

    private Integer idSignoVital;

    private Paciente paciente;

    private LocalDateTime fecha;

    @Size(min = 3, max = 150, message = "Temperatura debe tener minimo 3 caracteres")
    private String temperatura;

    @Size(min = 3, max = 150, message = "Pulso debe tener minimo 3 caracteres")
    private String pulso;

    @Size(min = 3, max = 150, message = "Ritmo Respiratorio debe tener minimo 3 caracteres")
    private String ritmoRespiratorio;

    public Integer getIdSignoVital() {
        return idSignoVital;
    }

    public void setIdSignoVital(Integer idSignoVital) {
        this.idSignoVital = idSignoVital;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getPulso() {
        return pulso;
    }

    public void setPulso(String pulso) {
        this.pulso = pulso;
    }

    public String getRitmoRespiratorio() {
        return ritmoRespiratorio;
    }

    public void setRitmoRespiratorio(String ritmoRespiratorio) {
        this.ritmoRespiratorio = ritmoRespiratorio;
    }

}
