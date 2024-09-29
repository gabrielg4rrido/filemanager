import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface DiretorioDTO {
  id: number;
  nome: string;
  diretorioPai?: DiretorioDTO;
  diretoriosFilhos?: DiretorioDTO[];
  arquivos?: ArquivoDTO[];
}

export interface ArquivoDTO {
  id: number;
  nome: string;
  extensao: string;
}

@Injectable({
  providedIn: 'root'
})
export class DiretorioService {
  private diretorioUrl = 'http://localhost:8080/api/diretorios';
  private arquivoUrl = 'http://localhost:8080/api/arquivos'

  constructor(private http: HttpClient) {}

  listarDiretorios(idPai?: number): Observable<DiretorioDTO[]> {
    let url = this.diretorioUrl;

    if (idPai) {
      url += `/${idPai}/filhos`;
    }
    return this.http.get<DiretorioDTO[]>(url);
  }

  listarArquivosPorDiretorio(idDiretorio?: number): Observable<ArquivoDTO[]> {
    let url = `${this.diretorioUrl}/${idDiretorio}/arquivos`;

    return this.http.get<ArquivoDTO[]>(url);
  }

  criarDiretorio(diretorio: DiretorioDTO): Observable<DiretorioDTO> {
    return this.http.post<DiretorioDTO>(this.diretorioUrl, diretorio);
  }

  atualizarDiretorio(diretorio: DiretorioDTO): Observable<DiretorioDTO> {
    return this.http.put<DiretorioDTO>(`${this.diretorioUrl}/${diretorio.id}`, diretorio);
  }

  excluirDiretorio(id: number): Observable<void> {
    return this.http.delete<void>(`${this.diretorioUrl}/${id}`);
  }

  criarArquivo(idDiretorio: number, arquivo: ArquivoDTO): Observable<ArquivoDTO> {
    return this.http.post<ArquivoDTO>(`${this.arquivoUrl}/${idDiretorio}/arquivos`, arquivo);
  }

  atualizarArquivo(idDiretorio: number, arquivo: ArquivoDTO): Observable<ArquivoDTO> {
    return this.http.put<ArquivoDTO>(`${this.arquivoUrl}/${idDiretorio}/arquivos/${arquivo.id}`, arquivo);
  }

  excluirArquivo(idDiretorio: number, idArquivo: number): Observable<void> {
    return this.http.delete<void>(`${this.arquivoUrl}/${idDiretorio}/arquivos/${idArquivo}`);
  }
}
