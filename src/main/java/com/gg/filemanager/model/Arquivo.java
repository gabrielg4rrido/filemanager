package com.gg.filemanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ARQUIVOS")
@Getter
@Setter
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "EXTENSAO")
    private String extensao;

    @ManyToOne
    @JoinColumn(name = "DIRETORIO_ID", referencedColumnName = "ID")
    private Diretorio diretorio;
}
