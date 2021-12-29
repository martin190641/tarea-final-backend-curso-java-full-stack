package com.mitocode.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "signo_vital")
public class SignoVital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSignoVital;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "temperatura", nullable = false)
    private String temperatura;

    @Column(name = "pulso", nullable = false)
    private String pulso;

    @Column(name = "ritmo_respiratorio", nullable = false)
    private String ritmoRespiratorio;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false, foreignKey = @ForeignKey(name = "FK_signovital_paciente"))
    private Paciente paciente;

    public Integer getIdSignoVital() {
        return idSignoVital;
    }

    public void setIdSignoVital(Integer idSignoVital) {
        this.idSignoVital = idSignoVital;
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

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
