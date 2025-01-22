<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tài Khoản Được Tạo Thành Công</title>
</head>
<body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
<table width="100%" cellpadding="0" cellspacing="0" style="max-width: 600px; margin: 0 auto; border: 1px solid #ddd; border-radius: 8px; padding: 20px;">
    <tr>
        <td style="text-align: center;">
            <h2 style="color: #4CAF50;">Chào mừng đến với Green Building!</h2>
        </td>
    </tr>
    <tr>
        <td>
            <p>Xin chào <b>${userEmail}</b>,</p>
            <p>Chúng tôi vui mừng thông báo rằng tài khoản của bạn đã được tạo thành công. Để bắt đầu, vui lòng sử dụng mật khẩu tạm thời sau:</p>
            <p style="font-size: 18px; font-weight: bold; text-align: center; background-color: #f4f4f4; padding: 10px; border: 1px solid #ddd; border-radius: 4px;">
                ${password}
            </p>
            <p><b>Quan trọng:</b> Vui lòng không chia sẻ mật khẩu này với bất kỳ ai để đảm bảo an toàn.</p>
            <p>Để cập nhật mật khẩu và bắt đầu sử dụng tài khoản của bạn, vui lòng đăng nhập vào trang web của chúng tôi:</p>
            <p style="text-align: center;">
                <a href="${homepage}" target="_blank" style="background-color: #4CAF50; color: #fff; text-decoration: none;
                padding: 10px 20px; border-radius: 4px; font-size: 16px;">
                    Cập Nhật Mật Khẩu
                </a>
            </p>
        </td>
    </tr>
    <tr>
        <td style="text-align: center; padding-top: 20px; color: #666;">
            <p>Trân trọng,<br>Đội ngũ Green Building</p>
        </td>
    </tr>
</table>
</body>
</html>
