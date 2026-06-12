package edu.technosplay.NextClass.model;

import edu.technosplay.NextClass.model.enums.TipoRemetente;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "mensagens_atendimento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensagemAtendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "atendimento_id", nullable = false)
    private Atendimento atendimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remetente_id")
    private Usuario remetente;

    @Column(nullable = false, length = 100)
    private String nomeRemetente;

    @Column(nullable = false, length = 2000)
    private String conteudo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRemetente tipoRemetente;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime enviadoEm;
}