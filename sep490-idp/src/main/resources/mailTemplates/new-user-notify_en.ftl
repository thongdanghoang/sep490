<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Account Created Successfully</title>
</head>
<body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
<table width="100%" cellpadding="0" cellspacing="0" style="max-width: 600px; margin: 0 auto; border: 1px solid #ddd; border-radius: 8px; padding: 20px;">
    <tr>
        <td style="text-align: center;">
            <h2 style="color: #4CAF50;">Welcome to Green Building!</h2>
        </td>
    </tr>
    <tr>
        <td>
            <p>Dear <b>${userEmail}</b>,</p>
            <p>We are excited to let you know that your account has been created successfully. To get started, please use the following temporary password:</p>
            <p style="font-size: 18px; font-weight: bold; text-align: center; background-color: #f4f4f4; padding: 10px; border: 1px solid #ddd; border-radius: 4px;">
                ${password}
            </p>
            <p><b>Important:</b> Please do not share this password with anyone for security reasons.</p>
            <p>To update your password and start using your account, please log in to our website:</p>
            <p style="text-align: center;">
                <a href="${homepage}" target="_blank" style="background-color: #4CAF50; color: #fff; text-decoration: none;
                padding: 10px
                 20px; border-radius: 4px; font-size: 16px;">
                    Update Password
                </a>
            </p>
        </td>
    </tr>
    <tr>
        <td style="text-align: center; padding-top: 20px; color: #666;">
            <p>Thank you,<br>The Green Building Team</p>
        </td>
    </tr>
</table>
</body>
</html>
