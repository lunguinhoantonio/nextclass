package edu.technosplay.NextClass.model;

import edu.technosplay.NextClass.model.enums.StatusAtendimento;
import edu.technosplay.NextClass.model.enums.TipoAtendimento;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "atendimentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Atendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitante_id")
    private Usuario solicitante;

    @Column(nullable = false)
    private String nomeCompleto;

    @Column(nullable = false, length = 11)
    private String cpf;

    @Column(nullable = false)
    private LocalDateTime dataNascimento;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendente_id")
    private Usuario atendente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAtendimento tipo;

    @Column(nullable = false, length = 200)
    private String assunto;

    @Column(nullable = false, length = 2000)
    private String descricao;

    @Column(name = "data_agendamento", nullable = false)
    private LocalDateTime dataAgendamento;

    @Column(name = "horario_agendamento", nullable = false)
    private LocalTime horaAgendamento;

    // Preenchido automaticamente quando status muda para REALIZADO
    @Column(name = "data_realizacao")
    private LocalDateTime dataRealizacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatusAtendimento status = StatusAtendimento.AGENDADO;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime criadoEm;
}
