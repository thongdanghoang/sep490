import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class RegionService {

  constructor(private http: HttpClient) {
  }

  getStateShapes() {
    return this.http.get('/assets/data/gz_2010_us_050_00_5m.json');
  }
}
