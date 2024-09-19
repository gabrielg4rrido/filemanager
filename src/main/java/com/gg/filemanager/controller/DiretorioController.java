package com.gg.filemanager.controller;

import com.gg.filemanager.dto.DiretorioDTO;
import com.gg.filemanager.service.DiretorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(("/api/diretorios"))
public class DiretorioController {

    @Autowired
    private DiretorioService diretorioService;

    @GetMapping
    public ResponseEntity<List<DiretorioDTO>> getDiretorios() {
        List<DiretorioDTO> diretorios = diretorioService.listarTodos();
        return ResponseEntity.ok().body(diretorios);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<DiretorioDTO> getDiretorioById(Long id) {
        DiretorioDTO diretorio = diretorioService.listarUm(id);
        return ResponseEntity.ok().body(diretorio);
    }
}
