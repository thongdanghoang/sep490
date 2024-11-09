import {Component, OnInit} from '@angular/core';
import {Theme, ThemeService} from './modules/core/services/theme.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  constructor(
    private readonly themeService: ThemeService
  ) {

  }

  ngOnInit(): void {
    const localStorageTheme = this.themeService.getLocalStorageTheme();
    if (localStorageTheme) {
      this.themeService.switchTheme(localStorageTheme);
    } else if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
      // detect system dark mode
      this.themeService.switchTheme(Theme.AURA_DARK_CYAN);
    }
  }

}
