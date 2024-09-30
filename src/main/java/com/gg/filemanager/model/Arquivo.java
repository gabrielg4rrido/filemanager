package com.gg.filemanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "ARQUIVOS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Arquivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private long id;

    @Column(name = "NOME")
    @NotNull
    private String nome;

    @Column(name = "EXTENSAO")
    @NotNull
    private String extensao;

    @ManyToOne
    @JoinColumn(name = "DIRETORIO_ID", referencedColumnName = "ID")
    @NotNull
    private Diretorio diretorio;
}