import {Injectable} from '@angular/core';
import {definePreset} from '@primeng/themes';
import Material from '@primeng/themes/material';
import {PrimeNG, ThemeType} from 'primeng/config';
import {BehaviorSubject, Observable, of} from 'rxjs';

const MyPreset = definePreset(Material, {
  semantic: {
    primary: {
      50: '#f2f6f5',
      100: '#c2d3d0',
      200: '#92b1aa',
      300: '#628e85',
      400: '#316c5f',
      500: '#01493a',
      600: '#013e31',
      700: '#013329',
      800: '#012820',
      900: '#001d17',
      950: '#00120f'
    },
    colorScheme: {
      light: {
        surface: {
          0: '#ffffff',
          50: '{neutral.50}',
          100: '{neutral.100}',
          200: '{neutral.200}',
          300: '{neutral.300}',
          400: '{neutral.400}',
          500: '{neutral.500}',
          600: '{neutral.600}',
          700: '{neutral.700}',
          800: '{neutral.800}',
          900: '{neutral.900}',
          950: '{neutral.950}'
        }
      },
      dark: {
        surface: {
          0: '#ffffff',
          50: '{neutral.50}',
          100: '{neutral.100}',
          200: '{neutral.200}',
          300: '{neutral.300}',
          400: '{neutral.400}',
          500: '{neutral.500}',
          600: '{neutral.600}',
          700: '{neutral.700}',
          800: '{neutral.800}',
          900: '{neutral.900}',
          950: '{neutral.950}'
        }
      }
    }
  }
});

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
      preset: MyPreset,
      options: themeOptions
    };
    this.userPreferredColorTheme = {
      preset: MyPreset,
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
