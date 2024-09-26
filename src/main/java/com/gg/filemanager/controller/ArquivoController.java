package com.gg.filemanager.controller;

import com.gg.filemanager.dto.ArquivoDTO;
import com.gg.filemanager.service.ArquivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/arquivos")
public class ArquivoController {

    @Autowired
    private ArquivoService arquivoService;

    @GetMapping
    public ResponseEntity<List<ArquivoDTO>> listarArquivos() {
        List<ArquivoDTO> arquivos = arquivoService.listarTodos();
        return ResponseEntity.ok().body(arquivos);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<ArquivoDTO> listarArquivoPorId(@PathVariable Long id) {
        ArquivoDTO arquivo = arquivoService.listarUm(id);
        return ResponseEntity.ok().body(arquivo);
    }

    @PostMapping
    public ResponseEntity<Void> inserirArquivo(@RequestBody ArquivoDTO arquivo) {
        ArquivoDTO arquivoDTO = arquivoService.criar(arquivo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(arquivoDTO.id()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarArquivo(@RequestBody ArquivoDTO arquivo, @PathVariable Long id) {
        arquivoService.atualizar(id, arquivo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarArquivo(@PathVariable Long id) {
        arquivoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
