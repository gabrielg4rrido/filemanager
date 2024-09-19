package com.gg.filemanager.service;

import com.gg.filemanager.dto.ArquivoDTO;
import com.gg.filemanager.dto.DiretorioDTO;
import com.gg.filemanager.model.Diretorio;
import com.gg.filemanager.model.Arquivo;
import com.gg.filemanager.repository.DiretorioRepository;
import com.gg.filemanager.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiretorioService {

    @Autowired
    private DiretorioRepository diretorioRepository;

    public List<DiretorioDTO> listarTodos() {
        return diretorioRepository.findDiretoriosNivelSuperior().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DiretorioDTO listarUm(Long id) {
        Diretorio diretorio = diretorioRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id));
        return toDTO(diretorio);
    }

    public DiretorioDTO criar(DiretorioDTO diretorioDTO) {
        Diretorio novoDiretorio = new Diretorio();
        novoDiretorio.setNome(diretorioDTO.nome());

        if (diretorioDTO.diretorioPaiId() != null) {
            Diretorio diretorioPai = diretorioRepository.findById(diretorioDTO.diretorioPaiId())
                    .orElseThrow(() -> new ObjectNotFoundException(diretorioDTO.diretorioPaiId()));
            novoDiretorio.setDiretorioPai(diretorioPai);
        } else {
            novoDiretorio.setDiretorioPai(null);
        }

        Diretorio diretorioSalvo = diretorioRepository.save(novoDiretorio);
        return toDTO(diretorioSalvo);
    }

    public DiretorioDTO atualizar(Long id, DiretorioDTO diretorioDTO) {
        Diretorio diretorioExistente = diretorioRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id));

        diretorioExistente.setNome(diretorioDTO.nome());

        if (diretorioDTO.diretorioPaiId() != null) {
            Diretorio diretorioPai = diretorioRepository.findById(diretorioDTO.diretorioPaiId())
                    .orElseThrow(() -> new ObjectNotFoundException(diretorioDTO.diretorioPaiId()));
            diretorioExistente.setDiretorioPai(diretorioPai);
        }

        Diretorio diretorioAtualizado = diretorioRepository.save(diretorioExistente);
        return toDTO(diretorioAtualizado);
    }

    public void deletar(Long id) {
        if (!diretorioRepository.existsById(id)) {
            throw new ObjectNotFoundException(id);
        }
        diretorioRepository.deleteById(id);
    }

    private DiretorioDTO toDTO(Diretorio diretorio) {
        List<ArquivoDTO> arquivos = (diretorio.getArquivos() != null) ?
                diretorio.getArquivos().stream()
                        .map(this::convertArquivoToDto)
                        .collect(Collectors.toList())
                : List.of();

        List<DiretorioDTO> diretoriosFilhos = (diretorio.getDiretoriosFilhos() != null) ?
                diretorio.getDiretoriosFilhos().stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList())
                : List.of();

        Long diretorioPaiId = (diretorio.getDiretorioPai() != null)
                ? diretorio.getDiretorioPai().getId()
                : null;

        return new DiretorioDTO(diretorio.getId(), diretorio.getNome(), diretorioPaiId, diretoriosFilhos, arquivos);
    }

    private ArquivoDTO convertArquivoToDto(Arquivo arquivo) {
        DiretorioDTO diretorioDTO = (arquivo.getDiretorio() != null) ? toDTO(arquivo.getDiretorio()) : null;
        return new ArquivoDTO(arquivo.getId(), arquivo.getNome(), arquivo.getExtensao(), diretorioDTO);
    }
}