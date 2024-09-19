package com.gg.filemanager.controller;

import com.gg.filemanager.dto.DiretorioDTO;
import com.gg.filemanager.service.DiretorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(("/api/diretorios"))
public class DiretorioController {

    @Autowired
    private DiretorioService diretorioService;

    @GetMapping
    public ResponseEntity<List<DiretorioDTO>> listarDiretorios() {
        List<DiretorioDTO> diretorios = diretorioService.listarTodos();
        return ResponseEntity.ok().body(diretorios);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<DiretorioDTO> listarDiretorioPorId(@PathVariable Long id) {
        DiretorioDTO diretorio = diretorioService.listarUm(id);
        return ResponseEntity.ok().body(diretorio);
    }

    @PostMapping
    public ResponseEntity<Void> inserirDiretorio(@RequestBody DiretorioDTO diretorio) {
        DiretorioDTO diretorioDTO = diretorioService.criar(diretorio);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(diretorioDTO.id()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarDiretorio(@RequestBody DiretorioDTO diretorio, @PathVariable Long id) {
        diretorioService.atualizar(id, diretorio);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarDiretorio(@PathVariable Long id) {
        diretorioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
