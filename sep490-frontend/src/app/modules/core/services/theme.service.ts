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
    const themeElement = this.document.getElementById(this.appThemeElementId);
    if (!themeElement) {
      console.error(`Theme element with id '${this.appThemeElementId}' not found`);
      return null;
    }
    const themeLink = themeElement as HTMLLinkElement;
    if (!(themeLink instanceof HTMLLinkElement)) {
      console.error(`Element with id '${this.appThemeElementId}' is not a link element`);
      return null;
    }
    try {
      themeLink.href = theme + '.css';
    } catch (error) {
      console.error('Failed to set theme:', error);
      return null;
    }
    return theme;
  }

  useSystemTheme(): void {
    localStorage.removeItem(this.themeNameConfigKey);
  }

  getLocalStorageTheme(): Theme | null {
    const theme = localStorage.getItem(this.themeNameConfigKey);
    return this.supportedThemes.find(t => t === theme) ?? null
  }

  isDarkMode(): boolean {
    const currentTheme = this.getLocalStorageTheme();
    return currentTheme === Theme.AURA_DARK_CYAN;
  }
}
