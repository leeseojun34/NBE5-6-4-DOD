export class SocialAuthTokensDTO {

  constructor(data:Partial<SocialAuthTokensDTO>) {
    Object.assign(this, data);
  }

  socialAuthTokensId?: number|null;
  accessToken?: string|null;
  refreshToken?: string|null;
  tokenType?: string|null;
  expiresAt?: string|null;
  provider?: string|null;
  user?: string|null;

}
