export class LikeTimetableDTO {

  constructor(data:Partial<LikeTimetableDTO>) {
    Object.assign(this, data);
  }

  likeTimetableId?: number|null;
  startTime?: string|null;
  endTime?: string|null;
  weekday?: string|null;
  user?: string|null;

}
