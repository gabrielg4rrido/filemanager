package com.gg.filemanager.controller;

import com.gg.filemanager.dto.ArquivoDTO;
import com.gg.filemanager.dto.DiretorioDTO;
import com.gg.filemanager.service.ArquivoService;
import com.gg.filemanager.service.DiretorioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DiretorioControllerTest {

    @Mock
    private DiretorioService diretorioService;

    @Mock
    private ArquivoService arquivoService;

    @InjectMocks
    private DiretorioController diretorioController;

    @Test
    public void deveRetornarListaDeDiretorios() {
        List<DiretorioDTO> diretorios = List.of(new DiretorioDTO(1L, "Diretorio 1", null, Collections.emptyList(), Collections.emptyList()));
        given(diretorioService.listarTodos()).willReturn(diretorios);

        ResponseEntity<List<DiretorioDTO>> responseEntity = diretorioController.listarDiretorios();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(diretorios);
    }

    @Test
    public void deveRetornarDiretorioPorId() {
        DiretorioDTO diretorio = new DiretorioDTO(1L, "Diretorio 1", null, Collections.emptyList(), Collections.emptyList());
        given(diretorioService.listarUm(1L)).willReturn(diretorio);

        ResponseEntity<DiretorioDTO> responseEntity = diretorioController.listarDiretorioPorId(1L);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(diretorio);
    }

    @Test
    public void deveCriarDiretorio() {
        DiretorioDTO diretorio = new DiretorioDTO(null, "Novo Diretorio", null, Collections.emptyList(), Collections.emptyList());
        DiretorioDTO createdDiretorio = new DiretorioDTO(1L, "Novo Diretorio", null, Collections.emptyList(), Collections.emptyList());
        given(diretorioService.criar(diretorio)).willReturn(createdDiretorio);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<Void> responseEntity = diretorioController.inserirDiretorio(diretorio);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }


    @Test
    public void deveAtualizarDiretorio() {
        DiretorioDTO diretorio = new DiretorioDTO(1L, "Diretorio Atualizado", null, Collections.emptyList(), Collections.emptyList());
        doAnswer(invocation -> null).when(diretorioService).atualizar(1L, diretorio);

        ResponseEntity<Void> responseEntity = diretorioController.atualizarDiretorio(diretorio, 1L);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(diretorioService).atualizar(1L, diretorio);
    }

    @Test
    public void deveDeletarDiretorio() {
        doNothing().when(diretorioService).deletar(1L);

        ResponseEntity<Void> responseEntity = diretorioController.deletarDiretorio(1L);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(diretorioService).deletar(1L);
    }

        @Test
    public void shouldReturnListOfArquivosByDiretorio() {
        DiretorioDTO diretorio = new DiretorioDTO(1L, "Diretorio 1", null, Collections.emptyList(), Collections.emptyList());
        List<ArquivoDTO> arquivos = List.of(new ArquivoDTO(1L, "Arquivo 1", "pdf", 1L));

        given(arquivoService.listarPorDiretorio(1L)).willReturn(arquivos);
        
        ResponseEntity<List<ArquivoDTO>> responseEntity = diretorioController.listarArquivosPorDiretorio(1L);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(arquivos);
    }


    @Test
    public void deveRetornarListaDeDiretoriosFilhos() {
        List<DiretorioDTO> filhos = List.of(new DiretorioDTO(2L, "Diretorio Filho", 1L, Collections.emptyList(), Collections.emptyList()));
        given(diretorioService.listarDiretoriosFilhos(1L)).willReturn(filhos);

        ResponseEntity<List<DiretorioDTO>> responseEntity = diretorioController.listarDiretoriosFilhos(1L);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(filhos);
    }
}