export class ScheduleMemberDTO {

  constructor(data:Partial<ScheduleMemberDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  role?: string|null;
  departLocationName?: string|null;
  latitude?: number|null;
  longitude?: number|null;
  member?: string|null;
  schedule?: number|null;
  middleRegion?: number|null;

}
