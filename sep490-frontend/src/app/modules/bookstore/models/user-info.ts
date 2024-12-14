export interface UserInfo {
  id: string;
  username: string;
  attributes: {
    email: string;
    email_verified: boolean;
    sub: string;
  };
}
