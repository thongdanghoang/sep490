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

  selectTheme(theme: Theme): void {
    const appliedTheme = this.setAppTheme(theme);
    if (appliedTheme) {
      localStorage.setItem(this.themeNameConfigKey, theme);
    }
  }

  setAppTheme(theme: Theme): Theme | null {
    if (!this.supportedThemes.includes(theme)) {
      theme = this.defaultTheme;
    }
    let themeLink = this.document.getElementById(this.appThemeElementId) as HTMLLinkElement;
    if (themeLink) {
      themeLink.href = theme + '.css';
    }
    return theme;
  }

  useSystemTheme(): void {
    localStorage.removeItem(this.themeNameConfigKey);
  }

  getLocalStorageTheme(): Theme | null {
    const theme = localStorage.getItem(this.themeNameConfigKey);
    return this.supportedThemes.find(t => t === theme) || null
  }

  isDarkMode(): boolean {
    let themeLink = this.document.getElementById(this.appThemeElementId) as HTMLLinkElement;
    const currentTheme = this.getLocalStorageTheme();
    return currentTheme === Theme.AURA_DARK_CYAN;
  }
}
