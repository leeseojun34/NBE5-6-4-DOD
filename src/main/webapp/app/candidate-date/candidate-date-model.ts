export class CandidateDateDTO {

  constructor(data:Partial<CandidateDateDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  startTime?: string|null;
  endTime?: string|null;
  event?: number|null;

}
