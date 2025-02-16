import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {AppRoutingConstants} from '../../../app-routing.constant';
import {CreditPackage} from '../models/enterprise.dto';

@Injectable()
export class CreditPackageService {
  constructor(private readonly httpClient: HttpClient) {}

  public getAllCreditPackages(): Observable<CreditPackage[]> {
    return this.httpClient.get<CreditPackage[]>(
      `${AppRoutingConstants.ENTERPRISE_API_URL}/credit-package`
    );
  }
}
