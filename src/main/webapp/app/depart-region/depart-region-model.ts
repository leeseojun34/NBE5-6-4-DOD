export class DepartRegionDTO {

  constructor(data:Partial<DepartRegionDTO>) {
    Object.assign(this, data);
  }

  departRegionId?: number|null;
  dapartLocationName?: string|null;
  latitude?: number|null;
  longitude?: number|null;
  meeting?: number|null;
  middleRegion?: number|null;

}
