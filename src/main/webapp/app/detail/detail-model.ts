export class DetailDTO {

  constructor(data:Partial<DetailDTO>) {
    Object.assign(this, data);
  }

  detailId?: number|null;
  location?: string|null;
  schedule?: number|null;

}
