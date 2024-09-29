import { Component, OnInit } from '@angular/core';
import { DiretorioService } from '../service/diretorio.service';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { CommonModule, Location } from '@angular/common';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatCardModule } from '@angular/material/card';
import { animate, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-diretorio-list',
  templateUrl: './diretorio-list.component.html',
  styleUrls: ['./diretorio-list.component.css'],
  standalone: true,
  imports: [CommonModule, MatListModule, MatButtonModule, MatDividerModule, MatCardModule],
  animations: [
    trigger('routeAnimations', [
      transition('* <=> *', [
        style({ opacity: 0, transform: 'translateY(-20px)' }),
        animate('300ms ease-out', style({ opacity: 1, transform: 'translateY(0)' }))
      ])
    ])
  ]
})
export class DiretorioListComponent implements OnInit {
  diretorioAtualId: number | undefined;
  diretorios: any[] = [];
  arquivos: any[] = [];
  urlAnterior: string | undefined;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private location: Location,
    private diretorioService: DiretorioService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.diretorioAtualId = params['id'] ? +params['id'] : undefined;
      this.listarDiretorios();
      this.listarArquivos();
    });

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.urlAnterior = event.url;
      }
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

  navegarParaSubdiretorio(id: number): void {
    this.urlAnterior = this.router.url; // Armazena a URL atual antes de navegar
    this.router.navigate(['/diretorios', id]);
  }

  voltar(): void {
    if (this.urlAnterior) {
      this.router.navigateByUrl(this.urlAnterior);
    } else {
      this.location.back();
    }
  }
}
