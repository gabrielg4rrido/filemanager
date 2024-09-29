import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DiretorioListComponent } from './diretorio-list/diretorio-list.component';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, DiretorioListComponent, FormsModule, MatInputModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'file-manager';
}
