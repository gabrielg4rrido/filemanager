package com.gg.filemanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "DIRETORIOS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Diretorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private long id;

    @Column(name = "NOME")
    @NotNull
    private String nome;

    @ManyToOne
    @JoinColumn(name = "DIRETORIO_PAI_ID", referencedColumnName = "ID")
    private Diretorio diretorioPai;

    @OneToMany(mappedBy = "diretorioPai", cascade = CascadeType.ALL)
    private List<Diretorio> diretoriosFilhos;

    @OneToMany(mappedBy = "diretorio", cascade = CascadeType.ALL)
    private List<Arquivo> arquivos;
}