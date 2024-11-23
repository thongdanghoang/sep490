import {Injectable} from '@angular/core';
import {SafeHtml} from '@angular/platform-browser';

@Injectable()
export class PopupService {

  constructor() {
  }

  makeCapitalPopup(data: any): SafeHtml {
    if (!data?.name || !data?.state || !data?.population) {
      throw new Error('Invalid capital data provided');
    }
    return `
      <div>Capital: ${data.name}</div>
      <div>State: ${data.state}</div>
      <div>Population: ${data.population.toLocaleString()}</div>
    `;
  }
}
