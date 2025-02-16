import {AfterViewInit, Component} from '@angular/core';
import {Router} from '@angular/router';
import * as geojson from 'geojson';
import * as L from 'leaflet';
import {SelectButtonChangeEvent} from 'primeng/selectbutton';
import {takeUntil} from 'rxjs';
import {UUID} from '../../../../../types/uuid';
import {AppRoutingConstants} from '../../../../app-routing.constant';
import {BuildingService} from '../../../../services/building.service';
import {SubscriptionAwareComponent} from '../../../core/subscription-aware.component';
import {Building} from '../../models/enterprise.dto';
import {MarkerService} from '../../services/marker.service';
import {RegionService} from '../../services/region.service';

const iconRetinaUrl = 'assets/marker-icon-2x.png';
const iconUrl = 'assets/marker-icon.png';
const shadowUrl = 'assets/marker-shadow.png';
const iconDefault = L.icon({
  iconRetinaUrl,
  iconUrl,
  shadowUrl,
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  tooltipAnchor: [16, -28],
  shadowSize: [41, 41]
});
L.Marker.prototype.options.icon = iconDefault;

export enum ViewMode {
  LIST = 'list',
  MAP = 'map'
}

@Component({
  selector: 'app-building',
  templateUrl: './buildings.component.html',
  styleUrl: './buildings.component.css'
})
export class BuildingsComponent
  extends SubscriptionAwareComponent
  implements AfterViewInit
{
  viewMode: ViewMode = ViewMode.MAP;
  buildings: Building[] = [];

  justifyOptions: any[] = [
    {icon: 'pi pi-map', value: ViewMode.MAP},
    {icon: 'pi pi-list', value: ViewMode.LIST}
  ];

  private map!: L.Map;
  private states!: geojson.GeoJsonObject | geojson.GeoJsonObject[] | null;

  constructor(
    private readonly router: Router,
    private readonly buildingService: BuildingService,
    private readonly markerService: MarkerService,
    private readonly shapeService: RegionService
  ) {
    super();
  }

  ngAfterViewInit(): void {
    this.initMap();
    this.markerService.makeCapitalMarkers(this.map);
    this.map.on('click', e => {
      const marker = L.marker([e.latlng.lat, e.latlng.lng]);
      marker.addTo(this.map);
    });
    this.shapeService.getStateShapes().subscribe(states => {
      this.states = states;
      this.initStatesLayer();
    });
  }

  onViewModeChanged(event: SelectButtonChangeEvent): void {
    if (event.value === ViewMode.LIST) {
      this.buildingService
        .searchBuildings({
          page: {
            pageNumber: 0,
            pageSize: 100
          }
        })
        .pipe(takeUntil(this.destroy$))
        .subscribe(buildings => (this.buildings = buildings.results));
    }
  }

  addBuilding(): void {
    void this.router.navigate([
      '/',
      AppRoutingConstants.ENTERPRISE_PATH,
      AppRoutingConstants.BUILDING_PATH,
      'create'
    ]);
  }

  viewBuildingDetails(id: UUID): void {
    void this.router.navigate([
      '/',
      AppRoutingConstants.ENTERPRISE_PATH,
      AppRoutingConstants.BUILDING_PATH,
      id
    ]);
  }

  get mapView(): boolean {
    return this.viewMode === ViewMode.MAP;
  }

  get listView(): boolean {
    return this.viewMode === ViewMode.LIST;
  }

  private initMap(): void {
    if (document.getElementById('map')) {
      this.map = L.map('map', {
        center: [10.841394, 106.810052],
        zoom: 20
      });
    } else {
      throw new Error(
        'Map element not found, should set id="map" to the map element'
      );
    }

    const tiles = L.tileLayer(
      'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      {
        maxZoom: 18,
        minZoom: 3
      }
    );

    tiles.addTo(this.map);
  }

  private initStatesLayer(): void {
    const stateLayer = L.geoJSON(this.states, {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      style: feature => ({
        weight: 3,
        opacity: 0.5,
        color: '#008f68',
        fillOpacity: 0.8,
        fillColor: '#6DB65B'
      }),
      onEachFeature: (feature, layer) =>
        layer.on({
          mouseover: e => this.highlightFeature(e),
          mouseout: e => this.resetFeature(e)
        })
    });

    this.map.addLayer(stateLayer);
    stateLayer.bringToBack();
  }

  private highlightFeature(e: L.LeafletMouseEvent): void {
    const layer = e.target;

    layer.setStyle({
      weight: 10,
      opacity: 1.0,
      color: '#DFA612',
      fillOpacity: 1.0,
      fillColor: '#FAE042'
    });
  }

  private resetFeature(e: L.LeafletMouseEvent): void {
    const layer = e.target;

    layer.setStyle({
      weight: 3,
      opacity: 0.5,
      color: '#008f68',
      fillOpacity: 0.8,
      fillColor: '#6DB65B'
    });
  }
}
