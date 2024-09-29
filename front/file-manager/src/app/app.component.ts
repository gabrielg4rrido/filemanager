import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DiretorioListComponent } from './diretorio-list/diretorio-list.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, DiretorioListComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'file-manager';
}
