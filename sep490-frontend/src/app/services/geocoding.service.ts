import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AppRoutingConstants} from '../app-routing.constant';

export interface AddressSuggestion {
  displayName: string;
  road: string;
  quarter: string;
  suburb: string;
  city: string;
  postcode: string;
  country: string;
  countryCode: string;
}

@Injectable({
  providedIn: 'root'
})
export class GeocodingService {
  private readonly GEOCODING_API_URL: string = `${AppRoutingConstants.ENTERPRISE_API_URL}/${AppRoutingConstants.GEOCODING_PATH}`;

  constructor(private readonly httpClient: HttpClient) {}

  reverse(latitude: number, longitude: number): Observable<AddressSuggestion> {
    return this.httpClient.get<AddressSuggestion>(
      `${this.GEOCODING_API_URL}/reverse?latitude=${latitude}&longitude=${longitude}`
    );
  }
}
