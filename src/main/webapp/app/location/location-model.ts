export class LocationDTO {

  constructor(data:Partial<LocationDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  latitude?: number|null;
  longitude?: number|null;
  name?: string|null;
  suggestedMemberId?: string|null;
  voteCount?: number|null;
  status?: string|null;
  middleRegion?: number|null;

}
