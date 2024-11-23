import {Component, OnDestroy, OnInit} from '@angular/core';
import {Theme, ThemeService} from './modules/core/services/theme.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit, OnDestroy {
  private systemThemeMediaQuery?: MediaQueryList;

  constructor(private readonly themeService: ThemeService) {}

  ngOnInit(): void {
    const localStorageTheme = this.themeService.getLocalStorageTheme();
    if (localStorageTheme) {
      this.themeService.setAppTheme(localStorageTheme);
    } else if (window.matchMedia?.('(prefers-color-scheme: dark)')?.matches) {
      // detect system dark mode
      this.themeService.setAppTheme(Theme.AURA_DARK_CYAN);
      // Listen for system theme changes
      this.systemThemeMediaQuery = window.matchMedia(
        '(prefers-color-scheme: dark)'
      );
      this.systemThemeMediaQuery.addEventListener('change', e => {
        this.themeService.setAppTheme(
          e.matches ? Theme.AURA_DARK_CYAN : Theme.AURA_LIGHT_CYAN
        );
      });
    }
  }

  ngOnDestroy(): void {
    this.systemThemeMediaQuery?.removeEventListener('change', (e): void => {
      this.themeService.setAppTheme(
        e.matches ? Theme.AURA_DARK_CYAN : Theme.AURA_LIGHT_CYAN
      );
    });
  }
}
