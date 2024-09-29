import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { DiretorioService, DiretorioDTO, ArquivoDTO } from '../service/diretorio.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule, Location } from '@angular/common';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-diretorio-list',
  templateUrl: './diretorio-list.component.html',
  styleUrls: ['./diretorio-list.component.css'],
  encapsulation: ViewEncapsulation.None,
  standalone: true,
  imports: [CommonModule, MatListModule, MatButtonModule, MatDividerModule, MatCardModule, MatInputModule, FormsModule]
})
export class DiretorioListComponent implements OnInit {
  diretorioAtualId: number | undefined;
  diretorios: DiretorioDTO[] = [];
  arquivos: ArquivoDTO[] = [];
  novoDiretorio: DiretorioDTO = { id: 0, nome: '' };
  novoArquivo: ArquivoDTO = { id: 0, nome: '', extensao: '' };

  constructor(
    private diretorioService: DiretorioService,
    private route: ActivatedRoute,
    private router: Router,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.diretorioAtualId = params['id'] ? +params['id'] : undefined;
      this.listarDiretorios();
      this.listarArquivos();
    });
  }

  listarDiretorios(): void {
    this.diretorioService.listarDiretorios(this.diretorioAtualId).subscribe(data => {
      this.diretorios = data;
    });
  }

  listarArquivos(): void {
    if (this.diretorioAtualId) {
      this.diretorioService.listarArquivosPorDiretorio(this.diretorioAtualId).subscribe(data => {
        this.arquivos = data || [];
      });
    }
  }

  criarDiretorio(): void {
    if (this.novoDiretorio.nome.trim() === '') {
      alert('O nome do diretório não pode estar vazio.');
      return;
    }
    this.novoDiretorio.diretorioPaiId = this.diretorioAtualId;
    this.diretorioService.criarDiretorio(this.novoDiretorio).subscribe(() => {
      this.listarDiretorios();
      this.novoDiretorio = { id: 0, nome: '' };
    });
  }

  atualizarDiretorio(diretorio: DiretorioDTO): void {
    this.diretorioService.atualizarDiretorio(diretorio).subscribe(() => {
      this.listarDiretorios();
    });
  }

  excluirDiretorio(id: number): void {
    this.diretorioService.excluirDiretorio(id).subscribe(() => {
      this.listarDiretorios();
    });
  }

  criarArquivo(): void {
    if (this.novoArquivo.nome.trim() === '') {
      alert('O nome do arquivo não pode estar vazio.');
      return;
    }
    if (this.diretorioAtualId) {
      this.novoArquivo.diretorioId = this.diretorioAtualId;
      this.diretorioService.criarArquivo(this.novoArquivo).subscribe(() => {
        this.listarArquivos();
        this.novoArquivo = { id: 0, nome: '', extensao: '' };
      });
    }
  }

  atualizarArquivo(arquivo: ArquivoDTO): void {
    if (this.diretorioAtualId) {
      this.diretorioService.atualizarArquivo(arquivo).subscribe(() => {
        this.listarArquivos();
      });
    }
  }

  excluirArquivo(id: number): void {
    if (this.diretorioAtualId) {
      this.diretorioService.excluirArquivo(id).subscribe(() => {
        this.listarArquivos();
      });
    }
  }

  navegarParaSubdiretorio(id: number): void {
    this.router.navigate(['/diretorios', id]);
  }

  voltar(): void {
    this.location.back();
  }
}
