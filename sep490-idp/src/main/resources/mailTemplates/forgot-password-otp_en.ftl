<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password</title>
</head>
<body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
<div style="max-width: 600px; margin: 20px auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px;">
    <h2 style="text-align: center; color: #0056b3;">Reset Your Password</h2>

    <p>Dear <strong>${userEmail}</strong>,</p>

    <p>We received a request to reset your password. Please use the following OTP code to proceed:</p>

    <p style="text-align: center; font-size: 24px; font-weight: bold; color: #0056b3;">${otpCode}</p>

    <p>This code is valid for <strong>10 minutes</strong>. If you did not request a password reset, you can safely ignore this email.</p>

    <p>Thank you,</p>

    <p style="margin-top: 20px; font-weight: bold;">Regards,</p>
    <p>The Support Team</p>
</div>
</body>
</html>
