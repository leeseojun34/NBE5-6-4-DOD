export class CandidateDateDTO {

  constructor(data:Partial<CandidateDateDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  date?: string|null;
  event?: number|null;

}
