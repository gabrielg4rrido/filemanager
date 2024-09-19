package com.gg.filemanager.repository;

import com.gg.filemanager.model.Diretorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiretorioRepository extends JpaRepository<Diretorio, Long> {

    @Query("SELECT d FROM Diretorio d WHERE d.diretorioPai IS NULL")
    List<Diretorio> findDiretoriosNivelSuperior();
}
