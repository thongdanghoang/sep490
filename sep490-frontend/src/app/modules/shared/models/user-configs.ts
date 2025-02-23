import {UserLanguage} from '../enums/user-language.enum';

export interface UserConfigs {
  language: UserLanguage;
  theme: keyof typeof Theme;
}

export enum Theme {
  DARK,
  LIGHT,
  SYSTEM
}
