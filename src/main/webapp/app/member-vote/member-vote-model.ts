export class MemberVoteDTO {

  constructor(data:Partial<MemberVoteDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  voter?: number|null;
  location?: number|null;

}
