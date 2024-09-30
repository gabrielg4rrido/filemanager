package com.gg.filemanager.repository;

import com.gg.filemanager.model.Arquivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {
    List<Arquivo> findArquivosByDiretorioId(Long diretorioId);
}