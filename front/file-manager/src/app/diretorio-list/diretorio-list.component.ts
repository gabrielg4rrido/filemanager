import { Component, OnInit } from '@angular/core';
import { DiretorioService } from '../service/diretorio.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule, Location } from '@angular/common';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';

@Component({
  selector: 'app-diretorio-list',
  templateUrl: './diretorio-list.component.html',
  styleUrls: ['./diretorio-list.component.css'],
  standalone: true,
  imports: [CommonModule, MatListModule, MatButtonModule, MatDividerModule]
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
    this.location.back();
  }
}
