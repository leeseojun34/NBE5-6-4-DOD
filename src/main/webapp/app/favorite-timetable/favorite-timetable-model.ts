export class FavoriteTimetableDTO {

  constructor(data:Partial<FavoriteTimetableDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  startTime?: string|null;
  endTime?: string|null;
  weekday?: string|null;
  member?: string|null;

}
