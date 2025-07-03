export class UserVoteDTO {

  constructor(data:Partial<UserVoteDTO>) {
    Object.assign(this, data);
  }

  userVoteId?: number|null;
  userId?: number|null;
  locationCandidate?: number|null;

}
