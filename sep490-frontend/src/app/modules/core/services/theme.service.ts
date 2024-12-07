import {Injectable} from '@angular/core';
import Material from '@primeng/themes/material';
import {PrimeNG, ThemeType} from 'primeng/config';
import {BehaviorSubject, Observable, of} from 'rxjs';

@Injectable()
export class ThemeService {
  readonly LOCAL_STORAGE_KEY = 'prefers-color-scheme';
  readonly TOKEN = 'my-app-dark';
  systemPreferredColorThemeChanged: BehaviorSubject<boolean>;
  systemPreferredColorTheme: ThemeType;
  userPreferredColorTheme: ThemeType;

  constructor(private readonly config: PrimeNG) {
    const themeOptions = {
      prefix: 'p',
      darkModeSelector: 'system',
      cssLayer: {
        name: 'primeng',
        order: 'tailwind-base, primeng, tailwind-utilities'
      }
    };
    this.systemPreferredColorTheme = {
      preset: Material,
      options: themeOptions
    };
    this.userPreferredColorTheme = {
      preset: Material,
      options: {...themeOptions, darkModeSelector: `.${this.TOKEN}`}
    };
    this.systemPreferredColorThemeChanged = new BehaviorSubject<boolean>(
      window.matchMedia('(prefers-color-scheme: dark)').matches
    );
  }

  initTheme(): void {
    if (this.isThemeConfigured()) {
      this.config.theme.set(this.userPreferredColorTheme);
      if (localStorage.getItem('prefers-color-scheme') === 'dark') {
        document.querySelector('html')?.classList.add('my-app-dark');
      }
      return;
    }
    this.config.theme.set(this.systemPreferredColorTheme);
    if (window.matchMedia('(prefers-color-scheme: dark)').matches) {
      document.querySelector('html')?.classList.toggle(this.TOKEN);
    }
  }

  isDarkMode(): Observable<boolean> {
    if (this.isThemeConfigured()) {
      return of(localStorage.getItem(this.LOCAL_STORAGE_KEY) === 'dark');
    }
    return this.systemPreferredColorThemeChanged;
  }

  toggleLightDark(): void {
    this.config.theme.set(this.userPreferredColorTheme);
    if (document.querySelector('html')?.classList.contains(this.TOKEN)) {
      localStorage.setItem(this.LOCAL_STORAGE_KEY, 'light');
    } else {
      localStorage.setItem(this.LOCAL_STORAGE_KEY, 'dark');
    }
    document.querySelector('html')?.classList.toggle(this.TOKEN);
  }

  private isThemeConfigured(): boolean {
    return !!localStorage.getItem(this.LOCAL_STORAGE_KEY);
  }
}
