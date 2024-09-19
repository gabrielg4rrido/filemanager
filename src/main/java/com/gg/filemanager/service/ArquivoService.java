package com.gg.filemanager.service;

import com.gg.filemanager.dto.ArquivoDTO;
import com.gg.filemanager.model.Arquivo;
import com.gg.filemanager.model.Diretorio;
import com.gg.filemanager.repository.ArquivoRepository;
import com.gg.filemanager.repository.DiretorioRepository;
import com.gg.filemanager.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArquivoService {

    @Autowired
    private ArquivoRepository arquivoRepository;

    @Autowired
    private DiretorioRepository diretorioRepository;

    public List<ArquivoDTO> listarTodos() {
        return arquivoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ArquivoDTO listarUm(Long id) {
        Arquivo arquivo = arquivoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id));
        return toDTO(arquivo);
    }

    public ArquivoDTO criar(ArquivoDTO arquivoDTO) {
        Arquivo novoArquivo = new Arquivo();
        novoArquivo.setNome(arquivoDTO.nome());

        Diretorio diretorio = diretorioRepository.findById(arquivoDTO.diretorioId()).orElseThrow(() -> new ObjectNotFoundException(arquivoDTO.diretorioId()));
        novoArquivo.setDiretorio(diretorio);

        Arquivo arquivoSalvo = arquivoRepository.save(novoArquivo);
        return toDTO(arquivoSalvo);
    }

    public ArquivoDTO atualizar(Long id, ArquivoDTO arquivoDTO) {
        Arquivo arquivoExistente = arquivoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id));

        arquivoExistente.setNome(arquivoDTO.nome());
        arquivoExistente.setExtensao(arquivoDTO.extensao());

        if (arquivoDTO.diretorioId() != null) {
            Diretorio novoDiretorio = diretorioRepository.findById(arquivoDTO.diretorioId())
                    .orElseThrow(() -> new ObjectNotFoundException(arquivoDTO.diretorioId()));
            arquivoExistente.setDiretorio(novoDiretorio);
        } else {
            arquivoExistente.setDiretorio(null);
        }

        Arquivo arquivoAtualizado = arquivoRepository.save(arquivoExistente);
        return toDTO(arquivoAtualizado);
    }

    public void deletar(Long id) {
        arquivoRepository.deleteById(id);
    }

    public ArquivoDTO toDTO(Arquivo arquivo) {
        return new ArquivoDTO(arquivo.getId(), arquivo.getNome(), arquivo.getExtensao(), arquivo.getDiretorio().getId());
    }
}