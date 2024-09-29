import { Component, OnInit } from '@angular/core';
import { DiretorioService } from '../service/diretorio.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule, Location } from '@angular/common';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-diretorio-list',
  templateUrl: './diretorio-list.component.html',
  styleUrls: ['./diretorio-list.component.css'],
  standalone: true,
  imports: [CommonModule, MatListModule, MatButtonModule, MatDividerModule, MatCardModule]
})
export class DiretorioListComponent implements OnInit {
  diretorioAtualId: number | undefined;
  diretorios: any[] = [];
  arquivos: any[] = [];

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
    if(this.diretorioAtualId) {
      this.diretorioService.listarArquivosPorDiretorio(this.diretorioAtualId).subscribe(data => {
        this.arquivos = data || [];
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
