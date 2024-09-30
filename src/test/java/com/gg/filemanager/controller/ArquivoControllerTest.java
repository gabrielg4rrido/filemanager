package com.gg.filemanager.controller;

import com.gg.filemanager.dto.ArquivoDTO;
import com.gg.filemanager.service.ArquivoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ArquivoControllerTest {

    @Mock
    private ArquivoService arquivoService;

    @InjectMocks
    private ArquivoController arquivoController;

    @Test
    public void deveRetornarListaVaziaQuandoNaoExistemArquivos() {
        given(arquivoService.listarTodos()).willReturn(Collections.emptyList());

        ResponseEntity<List<ArquivoDTO>> responseEntity = arquivoController.listarArquivos();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEmpty();
    }

    @Test
    public void deveRetornarListaDeArquivos() {
        List<ArquivoDTO> arquivos = List.of(new ArquivoDTO(1L, "Arquivo 1", "pdf", 1L));
        given(arquivoService.listarTodos()).willReturn(arquivos);

        ResponseEntity<List<ArquivoDTO>> responseEntity = arquivoController.listarArquivos();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(arquivos);
    }

    @Test
    public void deveRetornarArquivoPorId() {
        ArquivoDTO arquivo = new ArquivoDTO(1L, "Arquivo 1", "pdf", 1L);
        given(arquivoService.listarUm(1L)).willReturn(arquivo);

        ResponseEntity<ArquivoDTO> responseEntity = arquivoController.listarArquivoPorId(1L);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(arquivo);
    }

    @Test
    public void deveCriarArquivo() {
        ArquivoDTO arquivo = new ArquivoDTO(null, "Novo Arquivo", "pdf", 1L);
        ArquivoDTO createdArquivo = new ArquivoDTO(1L, "Novo Arquivo", "pdf", 1L);
        given(arquivoService.criar(arquivo)).willReturn(createdArquivo);

        ResponseEntity<Void> responseEntity = arquivoController.inserirArquivo(arquivo);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void deveAtualizarArquivo() {
        ArquivoDTO arquivo = new ArquivoDTO(1L, "Arquivo Atualizado", "pdf", 1L);
        doNothing().when(arquivoService).atualizar(1L, arquivo);

        ResponseEntity<Void> responseEntity = arquivoController.atualizarArquivo(arquivo, 1L);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(arquivoService).atualizar(1L, arquivo);
    }

    @Test
    public void deveDeletarArquivo() {
        doNothing().when(arquivoService).deletar(1L);

        ResponseEntity<Void> responseEntity = arquivoController.deletarArquivo(1L);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(arquivoService).deletar(1L);
    }
}