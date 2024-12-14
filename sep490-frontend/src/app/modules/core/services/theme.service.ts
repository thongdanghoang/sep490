import {Injectable} from '@angular/core';
import {definePreset} from '@primeng/themes';
import Material from '@primeng/themes/material';
import {PrimeNG, ThemeType} from 'primeng/config';
import {BehaviorSubject, Observable, of} from 'rxjs';

const MyPreset = definePreset(Material, {
  semantic: {
    primary: {
      50: '{orange.50}',
      100: '{orange.100}',
      200: '{orange.200}',
      300: '{orange.300}',
      400: '{orange.400}',
      500: '#ff9901',
      600: '{orange.600}',
      700: '{orange.700}',
      800: '{orange.800}',
      900: '{orange.900}',
      950: '{orange.960}'
    }
  }
});

@Injectable()
export class ThemeService {
  readonly LOCAL_STORAGE_KEY = 'prefers-color-scheme';
  readonly TOKEN = 'my-app-dark';
  readonly DARK_MODE = 'dark';
  readonly LIGHT_MODE = 'light';
  readonly SYSTEM_COLOR_SCHEME_QUERY = '(prefers-color-scheme: dark)';
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
      preset: MyPreset,
      options: themeOptions
    };
    this.userPreferredColorTheme = {
      preset: MyPreset,
      options: {...themeOptions, darkModeSelector: `.${this.TOKEN}`}
    };
    this.systemPreferredColorThemeChanged = new BehaviorSubject<boolean>(
      window.matchMedia(this.SYSTEM_COLOR_SCHEME_QUERY).matches
    );
  }

  initTheme(): void {
    if (this.isThemeConfigured()) {
      this.config.theme.set(this.userPreferredColorTheme);
      if (localStorage.getItem(this.LOCAL_STORAGE_KEY) === this.DARK_MODE) {
        document.querySelector('html')?.classList.add(this.TOKEN);
      }
      return;
    }
    this.config.theme.set(this.systemPreferredColorTheme);
    if (window.matchMedia(this.SYSTEM_COLOR_SCHEME_QUERY).matches) {
      document.querySelector('html')?.classList.toggle(this.TOKEN);
    }
  }

  isDarkMode(): Observable<boolean> {
    if (this.isThemeConfigured()) {
      return of(
        localStorage.getItem(this.LOCAL_STORAGE_KEY) === this.DARK_MODE
      );
    }
    return this.systemPreferredColorThemeChanged;
  }

  toggleLightDark(): void {
    this.config.theme.set(this.userPreferredColorTheme);
    if (document.querySelector('html')?.classList.contains(this.TOKEN)) {
      localStorage.setItem(this.LOCAL_STORAGE_KEY, this.LIGHT_MODE);
    } else {
      localStorage.setItem(this.LOCAL_STORAGE_KEY, this.DARK_MODE);
    }
    document.querySelector('html')?.classList.toggle(this.TOKEN);
  }

  private isThemeConfigured(): boolean {
    return !!localStorage.getItem(this.LOCAL_STORAGE_KEY);
  }
}
