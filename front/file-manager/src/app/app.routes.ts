import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DiretorioListComponent } from './diretorio-list/diretorio-list.component';

export const routes: Routes = [
  { path: 'diretorios', component: DiretorioListComponent },
  { path: 'diretorios/:id', component: DiretorioListComponent },
  { path: '', redirectTo: '/diretorios', pathMatch: 'full' },
  { path: '**', redirectTo: '/diretorios' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
