import {DOCUMENT} from '@angular/common';
import {Inject, Injectable} from '@angular/core';

@Injectable()
export class ThemeService {

  constructor(@Inject(DOCUMENT) private readonly document: Document) {
  }

  switchTheme(theme: string) {
    let themeLink = this.document.getElementById('app-theme') as HTMLLinkElement;
    if (themeLink) {
      themeLink.href = theme + '.css';
    }
  }

  isDarkMode(): boolean {
    let themeLink = this.document.getElementById('app-theme') as HTMLLinkElement;
    return themeLink.href.includes('theme-aura-dark-cyan');
  }
}
