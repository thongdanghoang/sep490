import {Component} from '@angular/core';
import Material from '@primeng/themes/material';
import {PrimeNG} from 'primeng/config';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  constructor(private readonly config: PrimeNG) {
    this.config.theme.set({
      preset: Material,
      options: {
        prefix: 'p',
        darkModeSelector: 'system',
        cssLayer: {
          name: 'primeng',
          order: 'tailwind-base, primeng, tailwind-utilities'
        }
      }
    });
  }
}
