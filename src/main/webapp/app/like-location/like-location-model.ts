export class LikeLocationDTO {

  constructor(data:Partial<LikeLocationDTO>) {
    Object.assign(this, data);
  }

  likeLocationId?: number|null;
  longitude?: number|null;
  latitude?: number|null;
  locationName?: string|null;
  user?: string|null;

}
