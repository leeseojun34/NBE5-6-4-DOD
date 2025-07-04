export class MemberDTO {

  constructor(data:Partial<MemberDTO>) {
    Object.assign(this, data);
  }

  id?: string|null;
  password?: string|null;
  provider?: string|null;
  role?: string|null;
  email?: string|null;
  name?: string|null;
  profileImageNumber?: number|null;
  tel?: string|null;

}
