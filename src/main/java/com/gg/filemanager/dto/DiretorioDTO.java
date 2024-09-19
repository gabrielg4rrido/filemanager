package com.gg.filemanager.dto;

import java.util.List;

public record DiretorioDTO(Long id, String nome, List<DiretorioDTO> diretoriosFilhos, List<ArquivoDTO> arquivos) {}
