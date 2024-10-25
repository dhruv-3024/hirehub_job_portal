package com.hirehub.utility;

public class Data {
    public static String getOtpMsgBody(String otp ,String name){
        return "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <meta http-equiv='X-UA-Compatible' content='IE=edge'>" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "    <title>OTP Verification</title>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }" +
                "        .container { max-width: 600px; margin: 0 auto; background-color: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
                "        .header { background-color: #007BFF; padding: 10px; text-align: center; color: #fff; font-size: 24px; font-weight: bold; }" +
                "        .content { padding: 20px; text-align: center; }" +
                "        .otp { font-size: 36px; font-weight: bold; margin: 20px 0; color: #007BFF; }" +
                "        .footer { margin-top: 20px; text-align: center; font-size: 14px; color: #888; }" +
                "        .button { background-color: #007BFF; color: #fff; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block; margin-top: 20px; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='container'>" +
                "        <div class='header'>Hirehub : Your OTP Code</div>" +
                "        <div class='content'>" +
                "            <p>Hello, "+name+"</p>" +
                "            <p>We received a request to authenticate your account. Use the following OTP (One Time Password) to complete your process:</p>" +
                "            <div class='otp'>"+otp+"</div>" +
                "            <p>This OTP is valid for 10 minutes. Please do not share this code with anyone.</p>" +
                "            <p>If you didn't request this OTP, please ignore this email or contact support.</p>" +
                "            <a href='#' class='button'>Go to Website</a>" +
                "        </div>" +
                "        <div class='footer'>" +
                "            <p>&copy; 2024 Hirehub. All rights reserved.</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";

    }
}
