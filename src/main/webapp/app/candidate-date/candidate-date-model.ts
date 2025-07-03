export class CandidateDateDTO {

  constructor(data:Partial<CandidateDateDTO>) {
    Object.assign(this, data);
  }

  candidateDateId?: number|null;
  date?: string|null;
  event?: number|null;

}
