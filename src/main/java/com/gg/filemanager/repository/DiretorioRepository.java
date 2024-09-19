package com.gg.filemanager.repository;

import com.gg.filemanager.model.Diretorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiretorioRepository extends JpaRepository<Diretorio, Long> {
}
