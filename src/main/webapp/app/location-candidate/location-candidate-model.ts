export class LocationCandidateDTO {

  constructor(data:Partial<LocationCandidateDTO>) {
    Object.assign(this, data);
  }

  locationCandidateId?: number|null;
  suggestUserId?: string|null;
  locationName?: string|null;
  latitude?: number|null;
  longitude?: number|null;
  voteCount?: number|null;
  status?: string|null;
  detail?: number|null;

}
