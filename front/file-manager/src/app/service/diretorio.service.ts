import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, of } from 'rxjs';

export interface DiretorioDTO {
  id: number;
  nome: string;
  diretorioPaiId?: number;
  diretoriosFilhos?: DiretorioDTO[];
  arquivos?: ArquivoDTO[];
}

export interface ArquivoDTO {
  id: number;
  nome: string;
  extensao: string;
  diretorioId?: number;
}

@Injectable({
  providedIn: 'root'
})
export class DiretorioService {
  private diretorioUrl = 'http://localhost:8080/api/diretorios';
  private arquivoUrl = 'http://localhost:8080/api/arquivos';

  constructor(private http: HttpClient) {}

  /**
   * Lista os diretórios filhos de um diretório pai.
   * @param diretorioPaiId O ID do diretório pai.
   * @returns Um Observable contendo uma lista de DiretórioDTO.
   */
  listarDiretorios(diretorioPaiId?: number): Observable<DiretorioDTO[]> {
    let url = this.diretorioUrl;

    if (diretorioPaiId) {
      url += `/${diretorioPaiId}/filhos`;
    }
    return this.http.get<DiretorioDTO[]>(url).pipe(
      catchError(this.handleError<DiretorioDTO[]>('listarDiretorios', []))
    );
  }

  /**
   * Lista os arquivos de um diretório.
   * @param idDiretorio O ID do diretório.
   * @returns Um Observable contendo uma lista de ArquivoDTO.
   */
  listarArquivosPorDiretorio(idDiretorio?: number): Observable<ArquivoDTO[]> {
    let url = `${this.diretorioUrl}/${idDiretorio}/arquivos`;

    return this.http.get<ArquivoDTO[]>(url).pipe(
      catchError(this.handleError<ArquivoDTO[]>('listarArquivosPorDiretorio', []))
    );
  }

  /**
   * Cria um novo diretório.
   * @param diretorio O objeto DiretorioDTO contendo os dados do novo diretório.
   * @returns Um Observable contendo o DiretórioDTO criado.
   */
  criarDiretorio(diretorio: DiretorioDTO): Observable<DiretorioDTO> {
    return this.http.post<DiretorioDTO>(this.diretorioUrl, diretorio).pipe(
      catchError(this.handleError<DiretorioDTO>('criarDiretorio'))
    );
  }

  /**
   * Exclui um diretório.
   * @param id O ID do diretório a ser excluído.
   * @returns Um Observable vazio.
   */
  excluirDiretorio(id: number): Observable<void> {
    return this.http.delete<void>(`${this.diretorioUrl}/${id}`).pipe(
      catchError(this.handleError<void>('excluirDiretorio'))
    );
  }

  /**
   * Cria um novo arquivo.
   * @param arquivo O objeto ArquivoDTO contendo os dados do novo arquivo.
   * @returns Um Observable contendo o ArquivoDTO criado.
   */
  criarArquivo(arquivo: ArquivoDTO): Observable<ArquivoDTO> {
    return this.http.post<ArquivoDTO>(this.arquivoUrl, arquivo).pipe(
      catchError(this.handleError<ArquivoDTO>('criarArquivo'))
    );
  }

  /**
   * Exclui um arquivo.
   * @param idArquivo O ID do arquivo a ser excluído.
   * @returns Um Observable vazio.
   */
  excluirArquivo(idArquivo: number): Observable<void> {
    return this.http.delete<void>(`${this.arquivoUrl}/${idArquivo}`).pipe(
      catchError(this.handleError<void>('excluirArquivo'))
    );
  }

  /**
   * Atualiza o nome de um diretório.
   * @param id O ID do diretório a ser atualizado.
   * @param nome O novo nome do diretório.
   * @returns Um Observable contendo o DiretórioDTO atualizado.
   */
  atualizarNomeDiretorio(id: number, nome: string): Observable<DiretorioDTO> {
    return this.http.patch<DiretorioDTO>(`${this.diretorioUrl}/${id}/nome`, { nome }).pipe(
      catchError(this.handleError<DiretorioDTO>('atualizarNomeDiretorio'))
    );
  }

  /**
   * Atualiza o nome de um arquivo.
   * @param id O ID do arquivo a ser atualizado.
   * @param nome O novo nome do arquivo.
   * @returns Um Observable contendo o ArquivoDTO atualizado.
   */
  atualizarNomeArquivo(id: number, nome: string): Observable<ArquivoDTO> {
    return this.http.patch<ArquivoDTO>(`${this.arquivoUrl}/${id}/nome`, { nome }).pipe(
      catchError(this.handleError<ArquivoDTO>('atualizarNomeArquivo'))
    );
  }

  /**
   * Manipula erros de operações HTTP.
   * @param operation - Nome da operação que falhou.
   * @param result - Valor opcional a ser retornado como resultado observável.
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(`${operation} falhou: ${error.message}`);
      return of(result as T);
    };
  }
}
