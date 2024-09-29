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
  private apiUrl = 'http://localhost:8080/api/diretorios';

  constructor(private http: HttpClient) {}

  listarDiretorios(idPai?: number): Observable<DiretorioDTO[]> {
    let url = this.apiUrl;

    if (idPai) {
      url += `/${idPai}/filhos`;
    }
    return this.http.get<DiretorioDTO[]>(url);
  }

  listarArquivosPorDiretorio(idDiretorio?: number): Observable<ArquivoDTO[]> {
    let url = this.apiUrl += `/${idDiretorio}/arquivos`;

    return this.http.get<ArquivoDTO[]>(url);
  }
}
