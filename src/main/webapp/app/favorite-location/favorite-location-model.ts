export class FavoriteLocationDTO {

  constructor(data:Partial<FavoriteLocationDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  longitude?: number|null;
  latitude?: number|null;
  name?: string|null;
  member?: string|null;

}
