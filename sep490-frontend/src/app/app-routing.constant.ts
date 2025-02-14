import {environment} from '../environments/environment';

export class AppRoutingConstants {
  public static readonly IDP_API_URL = environment.idpApiUrl;
  public static readonly ENTERPRISE_API_URL = environment.enterpriseUrl;

  // To be remove
  public static readonly DEV_PATH = 'dev';

  public static readonly AUTH_PATH = 'authorization';
  public static readonly USERS_PATH = 'users';
  public static readonly USER_DETAILS = 'user-details';

  // Enterprise Module
  public static readonly ENTERPRISE_PATH = 'enterprise';
  public static readonly BUILDING_PATH = 'buildings';
  public static readonly PLAN_PATH = 'plan';
  public static readonly PAYMENT_PATH = 'payment';

  // Emissions Module
  public static readonly EMISSIONS_PATH = 'emissions';

  // App Routing
  public static readonly DASHBOARD_PATH = 'dashboard';
  public static readonly FORBIDDEN = 'forbidden';
  public static readonly UNAUTHORIZED = 'unauthorized';
}
