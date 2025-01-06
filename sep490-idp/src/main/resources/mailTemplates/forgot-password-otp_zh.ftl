<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>重置密码</title>
</head>
<body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
<div style="max-width: 600px; margin: 20px auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px;">
    <h2 style="text-align: center; color: #0056b3;">重置您的密码</h2>

    <p>尊敬的 <strong>${userEmail}</strong>，</p>

    <p>我们收到了重置您密码的请求。请使用以下验证码进行操作：</p>

    <p style="text-align: center; font-size: 24px; font-weight: bold; color: #0056b3;">${otpCode}</p>

    <p>此验证码将在 <strong>10分钟</strong> 内有效。如果您没有请求重置密码，请忽略此邮件。</p>

    <p>谢谢！</p>

    <p style="margin-top: 20px; font-weight: bold;">此致，</p>
    <p>客服团队</p>
</div>
</body>
</html>