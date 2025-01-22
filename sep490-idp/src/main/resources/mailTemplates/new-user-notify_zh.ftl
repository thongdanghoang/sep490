<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>账户创建成功</title>
</head>
<body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
<table width="100%" cellpadding="0" cellspacing="0" style="max-width: 600px; margin: 0 auto; border: 1px solid #ddd; border-radius: 8px; padding: 20px;">
    <tr>
        <td style="text-align: center;">
            <h2 style="color: #4CAF50;">欢迎来到 Green Building！</h2>
        </td>
    </tr>
    <tr>
        <td>
            <p>尊敬的 <b>${userEmail}</b>，</p>
            <p>我们很高兴地通知您，您的账户已成功创建。请使用以下临时密码开始登录：</p>
            <p style="font-size: 18px; font-weight: bold; text-align: center; background-color: #f4f4f4; padding: 10px; border: 1px solid #ddd; border-radius: 4px;">
                ${password}
            </p>
            <p><b>重要提示：</b>请不要将此密码透露给任何人，以确保您的账户安全。</p>
            <p>要更新密码并开始使用您的账户，请访问我们的网站：</p>
            <p style="text-align: center;">
                <a href="${homepage}" target="_blank" style="background-color: #4CAF50; color: #fff; text-decoration: none;
                padding: 10px 20px; border-radius: 4px; font-size: 16px;">
                    更新密码
                </a>
            </p>
        </td>
    </tr>
    <tr>
        <td style="text-align: center; padding-top: 20px; color: #666;">
            <p>谢谢，<br>Green Building 团队</p>
        </td>
    </tr>
</table>
</body>
</html>
