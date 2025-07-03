export class MiddleRegionDTO {

  constructor(data:Partial<MiddleRegionDTO>) {
    Object.assign(this, data);
  }

  middleRegionId?: number|null;
  latitude?: number|null;
  longitude?: number|null;

}
