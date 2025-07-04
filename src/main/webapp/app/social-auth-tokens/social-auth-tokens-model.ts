export class SocialAuthTokensDTO {

  constructor(data:Partial<SocialAuthTokensDTO>) {
    Object.assign(this, data);
  }

  id?: number|null;
  accessToken?: string|null;
  refreshToken?: string|null;
  tokenType?: string|null;
  expiresAt?: string|null;
  provider?: string|null;
  member?: string|null;

}
