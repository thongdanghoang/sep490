import {Injectable} from '@angular/core';
import {DomSanitizer, SafeHtml} from '@angular/platform-browser';

@Injectable()
export class PopupService {

  constructor(private sanitizer: DomSanitizer) {
  }

  makeCapitalPopup(data: any): SafeHtml {
    if (!data?.name || !data?.state || !data?.population) {
      throw new Error('Invalid capital data provided');
    }
    const html = `
      <div>Capital: ${data.name}</div>
      <div>State: ${data.state}</div>
      <div>Population: ${data.population.toLocaleString()}</div>
    `;
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }
}
