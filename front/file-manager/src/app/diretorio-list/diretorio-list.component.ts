import { Component, OnInit } from '@angular/core';
import { DiretorioService, DiretorioDTO, ArquivoDTO } from '../service/diretorio.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-diretorio-list',
  templateUrl: './diretorio-list.component.html',
  styleUrls: ['./diretorio-list.component.css'],
  standalone: true,
  imports: [CommonModule]
})
export class DiretorioListComponent implements OnInit {
  diretorios: DiretorioDTO[] = [];
  arquivos: ArquivoDTO[] = [];
  diretorioAtualId?: number;

  constructor(
    private diretorioService: DiretorioService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.diretorioAtualId = params['id'] ? +params['id'] : undefined;
      this.listarDiretorios();
    });
  }

  listarDiretorios(): void {
    this.diretorioService.listarDiretorios(this.diretorioAtualId).subscribe(data => {
      this.diretorios = data;
      if (data.length > 0) {
        this.arquivos = data[0].arquivos || [];
      }
    });
  }

  navegarParaSubdiretorio(id: number): void {
    this.router.navigate(['/diretorios', id]);
  }

  voltar(): void {
    if (this.diretorioAtualId) {
      this.router.navigate(['/diretorios']);
    }
  }
}
