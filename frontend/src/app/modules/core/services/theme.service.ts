import {DOCUMENT} from '@angular/common';
import {Inject, Injectable} from '@angular/core';

@Injectable()
export class ThemeService {
  supportedThemes: string[] = ['theme-aura-dark-cyan', 'theme-aura-light-cyan'];
  appThemeElementId = 'app-theme';

  constructor(@Inject(DOCUMENT) private readonly document: Document) {
  }

  switchTheme(theme: string): void {
    if (!this.supportedThemes.includes(theme)) {
      theme = 'theme-aura-light-cyan';
    }
    let themeLink = this.document.getElementById(this.appThemeElementId) as HTMLLinkElement;
    if (themeLink) {
      themeLink.href = theme + '.css';
    }
  }

  isDarkMode(): boolean {
    let themeLink = this.document.getElementById(this.appThemeElementId) as HTMLLinkElement;
    return themeLink.href.includes('theme-aura-dark-cyan');
  }
}
