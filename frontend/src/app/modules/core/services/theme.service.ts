import {DOCUMENT} from '@angular/common';
import {Inject, Injectable} from '@angular/core';

export enum Theme {
  AURA_DARK_CYAN = 'theme-aura-dark-cyan',
  AURA_LIGHT_CYAN = 'theme-aura-light-cyan'
}


@Injectable()
export class ThemeService {
  readonly defaultTheme = Theme.AURA_LIGHT_CYAN;
  readonly supportedThemes: Theme[] = Object.values(Theme);
  readonly appThemeElementId = 'app-theme';
  readonly themeNameConfigKey = 'theme';

  constructor(@Inject(DOCUMENT) private readonly document: Document) {
  }

  switchTheme(theme: Theme): void {
    if (!this.supportedThemes.includes(theme)) {
      theme = this.defaultTheme;
    }
    let themeLink = this.document.getElementById(this.appThemeElementId) as HTMLLinkElement;
    if (themeLink) {
      themeLink.href = theme + '.css';
      localStorage.setItem(this.themeNameConfigKey, theme);
    }
  }

  useSystemTheme(): void {
    localStorage.removeItem(this.themeNameConfigKey);
  }

  getLocalStorageTheme(): Theme | null {
    return localStorage.getItem(this.themeNameConfigKey) as Theme;
  }

  isDarkMode(): boolean {
    let themeLink = this.document.getElementById(this.appThemeElementId) as HTMLLinkElement;
    return themeLink.href.includes('dark');
  }
}
