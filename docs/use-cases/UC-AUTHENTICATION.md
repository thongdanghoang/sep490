# User Authentication

**Status**: Ready for UI/UX design

**Priority / Business Value**: High

# 1. Content

1. Content
2. Objective
3. Overview / Background
    1. Process Overview
4. Requirements
    1. User Story: Browser Login
    2. User Story: Registration
    3. User Story: Reset Credentials

# 2. Objective

Enable secure access using OIDC standards.

# 3. Overview / Background

## 3.1 Process Overview

```mermaid
flowchart TD

%% Start
    start([Start])
%% Secure Login
    check_cookie{Session ID Cookie Found?}
    session_valid[Access Granted by Session]
    login_prompt[Provide Username and Password Form]
    rate_limit_check{Rate Limit Exceeded?}
    rate_limit_block[Login Temporarily Blocked]
    login_decision{Valid Credentials?}
    login_continue[MFA Process]
    login_captcha{Complete reCAPTCHA}
%% Multi-Factor Authentication (Expanded)
    mfa_flow(MFA Options)
    mfa_passkey[Passkeys]
    mfa_email_otp[Email OTP]
    mfa_authenticator[Authenticator App]
    mfa_validate{Any Successful?}
    mfa_success[MFA Complete]
    mfa_fail[MFA Failed - Retry]
%% Registration
    reg_flow(Registration Process)
    reg_profile[User Profile Creation]
    reg_validate[Profile Validation]
    reg_captcha[Complete reCAPTCHA]
    reg_tnc[Accept Terms and Conditions]
    reg_mfa[Setup MFA]
    reg_complete[Account Created]
%% Reset Credentials
    reset_flow(Reset Credentials Process)
    reset_choose[Choose User]
    reset_send[Send Reset Email]
    reset_resend[Resend OTP]
    reset_update[Update Password]
    reset_done[Password Reset Successful]
%% Connections
    start --> check_cookie
    check_cookie -- Yes --> session_valid --> END([End])
    check_cookie -- No --> login_prompt --> login_captcha
    login_captcha -- No --> login_prompt
    login_captcha -- Yes --> rate_limit_check
    rate_limit_check -- Yes --> rate_limit_block --> END
    rate_limit_check -- No --> login_decision
    login_decision -- Yes --> login_continue
    login_decision -- No --> login_prompt
    login_continue --> mfa_flow
    mfa_flow --> mfa_passkey
    mfa_flow --> mfa_email_otp
    mfa_flow --> mfa_authenticator
    mfa_passkey --> mfa_validate
    mfa_email_otp --> mfa_validate
    mfa_authenticator --> mfa_validate
    mfa_validate -- Yes --> mfa_success --> END
    mfa_validate -- No --> mfa_fail --> login_continue
    start --> reg_flow
    reg_flow --> reg_profile --> reg_captcha --> reg_validate --> reg_tnc --> reg_mfa --> reg_complete --> END
    start --> reset_flow
    reset_flow --> reset_choose --> reset_send --> reset_update --> reset_done --> END
    reset_send --> reset_resend --> reset_send
```

# 4. Requirements

## 4.1 User story: Browser Login

**Objective**: **As a** user, **I want to** securely log in to the application using a web browser with MFA

**Context**: User navigates to the login page and enters their credentials

**Precondition**: None

**User Interface**: [Figma]()

**Translations**:

EN:

```json
{
  "IDP": {
    "login": {
      "title": "Sign in to your account",
      "label": {
        "usernameOrEmail": "Username or email",
        "password": "Password",
        "newUser": "New user?"
      },
      "btn": {
        "login": "Sign in",
        "forgotPassword": "Forgot password",
        "register": "Register",
        "signInByPasskey": "Sign in with a passkey"
      },
      "error": {
        "badCredentials": "Invalid username or password.",
        "rateLimited": "Too many login attempts. Please try again later.",
        "recaptchaRequired": "Please complete the reCAPTCHA challenge."
      }
    }
  }
}
```

VI:

```json
{
  "IDP": {
    "login": {
      "title": "Đăng nhập vào tài khoản của bạn",
      "label": {
        "usernameOrEmail": "Tên người dùng hoặc email",
        "password": "Mật khẩu",
        "newUser": "Người dùng mới?"
      },
      "btn": {
        "login": "Đăng nhập",
        "forgotPassword": "Quên mật khẩu",
        "register": "Đăng ký",
        "signInByPasskey": "Đăng nhập bằng passkey"
      },
      "error": {
        "badCredentials": "Tên người dùng hoặc mật khẩu không hợp lệ.",
        "rateLimited": "Quá nhiều lần đăng nhập. Vui lòng thử lại sau.",
        "recaptchaRequired": "Vui lòng hoàn thành thử thách reCAPTCHA."
      }
    }
  }
}
```

### Business rules

| ID                | Name                                              | Description                                                                       |
|-------------------|---------------------------------------------------|-----------------------------------------------------------------------------------|
| **AUTH_LOGIN_01** | **SCENARIO: User is in valid session**            | Direct user to the application dashboard                                          |
| **AUTH_LOGIN_02** | **SCENARIO: Invalid session**                     | Prompt user to log page                                                           |
| **AUTH_LOGIN_03** | **SCENARIO: Invalid credentials**                 | Prompt user to re-enter credentials                                               |
| **AUTH_LOGIN_04** | **SCENARIO: Rate limit exceeded**                 | Block user from logging in                                                        |
| **AUTH_LOGIN_05** | **SCENARIO: Valid credentials <br> MFA required** | We provide MFA options: <br> - Passkeys <br> - Email OTP <br> - Authenticator App |
| **AUTH_LOGIN_06** | **SCENARIO: MFA successful**                      | Direct user to the application dashboard                                          |
| **AUTH_LOGIN_07** | **SCENARIO: MFA failed**                          | Prompt user to retry MFA process                                                  |
| **AUTH_LOGIN_08** | **SCENARIO: reCAPTCHA required**                  | Prompt user to complete reCAPTCHA before proceeding                               |

## 4.2 User story: Registration

**Objective**: **As a** enterprise owner, **I want to** create an enterprise account

**Context**: User navigates to the registration page and enters their details

**Precondition**: Non-existent user profile

**User Interface**: [Figma]()

**Translations**:

EN:

```json
{
  "IDP": {
    "registration": {
      "title": "Register",
      "label": {
        "username": "Username",
        "password": "Password",
        "confirmPassword": "Confirm password",
        "email": "Email",
        "firstName": "First name",
        "lastName": "Last name"
      },
      "btn": {
        "register": "Register",
        "back": "Back to Login"
      },
      "error": {
        "emailExists": "Email already exists.",
        "usernameExists": "Username already exists.",
        "passwordMismatch": "Password confirmation doesn't match.",
        "passwordWeak": "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number,and one special character.",
        "recaptchaRequired": "Please complete the reCAPTCHA challenge."
      }
    }
  },
  "validation": {
    "required": "This field is required.",
    "email": "Invalid email address."
  }
}
```

VI:

```json
{
  "IDP": {
    "registration": {
      "title": "Đăng ký",
      "label": {
        "username": "Tên người dùng",
        "password": "Mật khẩu",
        "confirmPassword": "Xác nhận mật khẩu",
        "email": "Email",
        "firstName": "Tên",
        "lastName": "Họ"
      },
      "btn": {
        "register": "Đăng ký",
        "back": "Quay lại trang đăng nhập"
      },
      "error": {
        "emailExists": "Email đã tồn tại.",
        "usernameExists": "Tên người dùng đã tồn tại.",
        "passwordMismatch": "Mật khẩu xác nhận không khớp.",
        "passwordWeak": "Mật khẩu phải chứa ít nhất 8 ký tự, một chữ cái viết hoa, một chữ cái viết thường, một số và một kýtự đặc biệt.",
        "recaptchaRequired": "Vui lòng hoàn thành thử thách reCAPTCHA."
      }
    }
  },
  "validation": {
    "required": "Trường này là bắt buộc.",
    "email": "Địa chỉ email không hợp lệ."
  }
}
```

### Business rules

| ID              | Name                                                                   | Description                                                                   |
|-----------------|------------------------------------------------------------------------|-------------------------------------------------------------------------------|
| **AUTH_REG_01** | **SCENARIO: Happy path <br> Profile created successfully**             | Redirect user to login page                                                   |
| **AUTH_REG_02** | **SCENARIO: Username exists**                                          | Show error message below username field                                       |
| **AUTH_REG_03** | **SCENARIO: Email exists**                                             | Show error message below email field                                          |
| **AUTH_REG_04** | **SCENARIO: Password mismatch**                                        | Show error message below confirm password                                     |
| **AUTH_REG_05** | **SCENARIO: Weak password**                                            | Show error message below password field                                       |
| **AUTH_REG_06** | **SCENARIO: reCAPTCHA required**                                       | Show error message below reCAPTCHA checkbox                                   |
| **AUTH_REG_07** | **SCENARIO: Profile created but MFA setup failed or T&C not accepted** | **Remember user's profile and prompt to retry at step MFA or T&C acceptance** |

## 4.3 User story: Reset Credentials

**Objective**: **As a** enterprise owner, **I want to** reset my password

**Context**: User navigates to the password reset page and enters their email

**Precondition**: User profile exists

**User Interface**: [Figma]()

**Translations**:

EN:

```json
{
  "IDP": {
    "reset": {
      "title": "Reset Password",
      "label": {
        "email": "Email",
        "otp": "One-time password",
        "password": "Password",
        "confirmPassword": "Confirm password"
      },
      "btn": {
        "sendOtp": "Send OTP",
        "reset": "Reset Password"
      },
      "error": {
        "userNotFound": "User not found.",
        "otpExpired": "OTP expired or invalid.",
        "passwordSame": "Password must be different from the previous one.",
        "passwordWeak": "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number, and one special character.",
        "otpExceedLimit": "You have exceeded the OTP send limit for today.",
        "otpInvalid": "Invalid OTP.",
        "otpInvalidExceed": "You have exceeded the OTP validation for this OTP. Please request a new one."
      }
    }
  }
}   
```

VI:

```json
{
  "IDP": {
    "reset": {
      "title": "Đặt lại mật khẩu",
      "label": {
        "email": "Email",
        "otp": "Mã OTP",
        "password": "Mật khẩu",
        "confirmPassword": "Xác nhận mật khẩu"
      },
      "btn": {
        "sendOtp": "Gửi OTP",
        "reset": "Đặt lại mật khẩu"
      },
      "error": {
        "userNotFound": "Người dùng không tồn tại.",
        "otpExpired": "OTP hết hạn hoặc không hợp lệ.",
        "passwordSame": "Mật khẩu phải khác với mật khẩu trước.",
        "passwordWeak": "Mật khẩu phải chứa ít nhất 8 ký tự, một chữ cái viết hoa, một chữ cái viết thường, một số và một ký tự đặc biệt.",
        "otpExceedLimit": "Bạn đã vượt quá số lần gửi OTP cho hôm nay.",
        "otpInvalid": "OTP không hợp lệ.",
        "otpInvalidExceed": "Bạn đã vượt quá số lần kiểm tra OTP cho mã OTP này. Vui lòng yêu cầu mã mới."
      }
    }
  }
}
```

### Business rules

| ID                | Name                                                    | Description                                                                 |
|-------------------|---------------------------------------------------------|-----------------------------------------------------------------------------|
| **AUTH_RESET_01** | **SCENARIO: Happy path <br> Password reset successful** | Redirect user to login page                                                 |
| **AUTH_RESET_02** | **SCENARIO: User not found**                            | Show error message below username or email field                            |
| **AUTH_RESET_03** | **SCENARIO: OTP expired or invalid**                    | Show error message below OTP field                                          |
| **AUTH_RESET_04** | **SCENARIO: Password was the same as previous**         | Show error message below password field                                     |
| **AUTH_RESET_05** | **SCENARIO: Password too weak**                         | Show error message below password field                                     |
| **AUTH_RESET_06** | **SCENARIO: OTP send exceed limit in a day**            | Show error message below OTP field. Each user only send 3 OTPs per day      |
| **AUTH_RESET_07** | **SCENARIO: OTP invalid too much**                      | Show error message below OTP field. Each user only try 3 times for each OTP |
