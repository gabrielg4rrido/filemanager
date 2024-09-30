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

    @Autowired
    private ArquivoService arquivoService;

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

    public List<DiretorioDTO> listarDiretoriosFilhos(Long diretorioPaiId) {
        return diretorioRepository.findByDiretorioPaiId(diretorioPaiId).stream()
                .map(this::toDTO)
                .toList();
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

    public void atualizarNome(Long id, String nome) {
        Diretorio diretorio = diretorioRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id));
        diretorio.setNome(nome);
        diretorioRepository.save(diretorio);
    }

    private DiretorioDTO toDTO(Diretorio diretorio) {
        List<ArquivoDTO> arquivos = (diretorio.getArquivos() != null) ?
                diretorio.getArquivos().stream()
                        .map(arquivo -> arquivoService.toDTO(arquivo))
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
}