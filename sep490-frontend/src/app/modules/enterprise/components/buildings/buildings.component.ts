import {AfterViewInit, Component, ComponentRef} from '@angular/core';
import {Router} from '@angular/router';
import * as L from 'leaflet';
import {takeUntil} from 'rxjs';
import {UUID} from '../../../../../types/uuid';
import {AppRoutingConstants} from '../../../../app-routing.constant';
import {BuildingService} from '../../../../services/building.service';
import {SubscriptionAwareComponent} from '../../../core/subscription-aware.component';
import {Building} from '../../models/enterprise.dto';
import {PopupService} from '../../services/popup.service';
import {BuildingPopupMarkerComponent} from '../building-popup-marker/building-popup-marker.component';

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

export interface MapLocation {
  latitude: number;
  longitude: number;
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
  addBuildingLocation: boolean = false;
  buildingLocation: MapLocation | null = null;
  viewMode: ViewMode = ViewMode.MAP;
  buildings: Building[] = [];

  justifyOptions: any[] = [
    {icon: 'pi pi-map', value: ViewMode.MAP},
    {icon: 'pi pi-list', value: ViewMode.LIST}
  ];

  private map!: L.Map;

  constructor(
    private readonly router: Router,
    private readonly buildingService: BuildingService,
    private readonly popupService: PopupService
  ) {
    super();
  }

  ngAfterViewInit(): void {
    this.initMap();
    this.fetchBuilding();
    this.map.on('click', e => {
      const marker = L.marker([e.latlng.lat, e.latlng.lng]);
      if (this.addBuildingLocation && this.buildingLocation === null) {
        marker.addTo(this.map);
        this.buildingLocation = {
          latitude: e.latlng.lat,
          longitude: e.latlng.lng
        };
      }
    });
  }

  get mapView(): boolean {
    return this.viewMode === ViewMode.MAP;
  }

  get listView(): boolean {
    return this.viewMode === ViewMode.LIST;
  }

  onViewModeChanged(): void {
    this.fetchBuilding();
  }

  turnOnSelectBuildingLocation(): void {
    this.viewMode = ViewMode.MAP;
    this.addBuildingLocation = true;
  }

  addBuilding(): void {
    if (this.buildingLocation) {
      void this.router.navigate(
        [
          '/',
          AppRoutingConstants.ENTERPRISE_PATH,
          AppRoutingConstants.BUILDING_PATH,
          'create'
        ],
        {
          queryParams: this.buildingLocation
        }
      );
    }
  }

  cancelAddBuilding(): void {
    this.addBuildingLocation = false;
    this.map.eachLayer(layer => {
      if (
        layer instanceof L.Marker &&
        layer.getLatLng().lat === this.buildingLocation?.latitude &&
        layer.getLatLng().lng === this.buildingLocation?.longitude
      ) {
        this.map.removeLayer(layer);
      }
    });
    this.buildingLocation = null;
  }

  viewBuildingDetails(id: UUID): void {
    void this.router.navigate([
      '/',
      AppRoutingConstants.ENTERPRISE_PATH,
      AppRoutingConstants.BUILDING_PATH,
      id
    ]);
  }

  fetchBuilding(): void {
    this.buildingService
      .searchBuildings({
        page: {
          pageNumber: 0,
          pageSize: 100
        }
      })
      .pipe(takeUntil(this.destroy$))
      .subscribe(buildings => {
        this.buildings = buildings.results;
        buildings.results.forEach(building => {
          const marker = L.marker([building.latitude, building.longitude]);
          marker.addTo(this.map);
          const markerPopup: HTMLDivElement = this.popupService.compilePopup(
            BuildingPopupMarkerComponent,
            (c: ComponentRef<BuildingPopupMarkerComponent>): void => {
              c.instance.building = building;
            }
          );
          marker.bindPopup(markerPopup);
        });
        const latLngs = buildings.results.map(building =>
          L.latLng(building.latitude, building.longitude)
        );
        const bounds = L.latLngBounds(latLngs);
        this.map.fitBounds(bounds);
      });
  }

  zoomTo(latitude: number, longitude: number): void {
    if (this.map) {
      this.viewMode = ViewMode.MAP;
      this.map.setView([latitude, longitude], 16);
    }
  }

  private initMap(): void {
    if (document.getElementById('map')) {
      this.map = L.map('map', {
        center: [10.841394, 106.810052],
        zoom: 16
      });
    } else {
      throw new Error(
        'Map element not found, should set id="map" to the map element'
      );
    }

    const tiles = L.tileLayer(
      'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
      {
        maxZoom: 16,
        minZoom: 1
      }
    );

    tiles.addTo(this.map);
  }
}
