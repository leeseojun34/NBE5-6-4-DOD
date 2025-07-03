export class LocationDTO {

  constructor(data:Partial<LocationDTO>) {
    Object.assign(this, data);
  }

  locationId?: number|null;
  latitude?: number|null;
  longitude?: number|null;
  locationName?: string|null;
  middleRegion?: number|null;

}
