package com.gg.filemanager.service;

import com.gg.filemanager.dto.ArquivoDTO;
import com.gg.filemanager.dto.DiretorioDTO;
import com.gg.filemanager.model.Arquivo;
import com.gg.filemanager.model.Diretorio;
import com.gg.filemanager.repository.DiretorioRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiretorioService {

    @Autowired
    private DiretorioRepository diretorioRepository;

    public DiretorioDTO listarUm(Long id) {
        Diretorio diretorioExistente = diretorioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diretório não encontrado"));

        return toDTO(diretorioExistente);
    }

    public List<DiretorioDTO> listarTodos() {
        return diretorioRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DiretorioDTO criar(DiretorioDTO diretorioDTO) {
        Diretorio diretorio = toEntity(diretorioDTO);
        Diretorio diretorioSalvo = diretorioRepository.save(diretorio);
        return toDTO(diretorioSalvo);
    }

    public DiretorioDTO atualizar(Long id, DiretorioDTO diretorioDTO) {
        Diretorio diretorioExistente = diretorioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diretório não encontrado"));

        diretorioExistente.setNome(diretorioDTO.nome());
        diretorioExistente.setDiretoriosFilhos(diretorioDTO.diretoriosFilhos().stream()
                .map(this::toEntity)
                .collect(Collectors.toList()));

        diretorioExistente.setArquivos(diretorioDTO.arquivos().stream()
                .map(arquivoDTO -> new Arquivo(arquivoDTO.id(), arquivoDTO.nome(), arquivoDTO.extensao(), diretorioExistente))
                .collect(Collectors.toList()));

        Diretorio atualizado = diretorioRepository.save(diretorioExistente);
        return toDTO(atualizado);
    }

    public void deletar(Long id) {
        diretorioRepository.deleteById(id);
    }

    private Diretorio toEntity(DiretorioDTO diretorioDTO) {
        Diretorio diretorio = new Diretorio();
        diretorio.setNome(diretorioDTO.nome());

        diretorio.setDiretoriosFilhos(diretorioDTO.diretoriosFilhos().stream()
                .map(this::toEntity)
                .collect(Collectors.toList()));

        diretorio.setArquivos(diretorioDTO.arquivos().stream()
                .map(arquivoDTO -> new Arquivo(arquivoDTO.id(), arquivoDTO.nome(), arquivoDTO.extensao(), diretorio))
                .collect(Collectors.toList()));

        return diretorio;
    }

    private DiretorioDTO toDTO(Diretorio diretorio) {
        List<DiretorioDTO> diretoriosFilhos = diretorio.getDiretoriosFilhos().stream()
                .map(this::toDTO)
                .toList();

        List<ArquivoDTO> arquivos = diretorio.getArquivos().stream()
                .map(arquivo -> new ArquivoDTO(arquivo.getId(), arquivo.getNome(), arquivo.getExtensao(), toDTO(diretorio)))
                .collect(Collectors.toList());

        return new DiretorioDTO(diretorio.getId(), diretorio.getNome(), diretoriosFilhos, arquivos);
    }
}
