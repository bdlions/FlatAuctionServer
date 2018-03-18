package org.bdlions.library;

import org.bdlions.email.MailSender;
import org.bdlions.email.MailUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bdlions.auction.util.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nazmul hasan
 */
public class SendMail {
    private final Logger logger = LoggerFactory.getLogger(SendMail.class);
    public boolean sendSignUpMail(String mailTo, String code)
    {
        boolean status = false;
        // outgoing message information
        //mailTo = "nazhasan15@yopmail.com";
        String subject = "Room Bid Rent SignUp Email Verification";

        //generate a random code and store that into the database, deactivate account
        Map<String, String> input = new HashMap<>();
        input.put("email_logo", ServerConfig.getInstance().get(ServerConfig.EMAIL_LOGO_URL));
        input.put("v_link", ServerConfig.getInstance().get(ServerConfig.EMAIL_VERIFICATION_URL) + code);
        input.put("c_link", ServerConfig.getInstance().get(ServerConfig.ACCOUNT_CANCEL_URL) + code);
        input.put("email", mailTo);

        //HTML mail content
        String htmlText = MailUtil.readEmailFromHtml("mail-templates/mail-verification.html", input);

        MailSender mailer = new MailSender();

        try {
            mailer.sendHtmlEmail(mailTo, subject, htmlText);
            status = true;
            //System.out.println("Email sent.");
        } catch (Exception ex) {
            status = false;
            //System.out.println("Failed to sent email.");
            logger.error(ex.toString());
        }
        return status;
    }
    
    public boolean sendForgetPasswordMail(String email, String pwd)
    {
        boolean status = false;
        // outgoing message information
        //mailTo = "nazhasan15@yopmail.com";
        String subject = "Room Bid Rent - Forget Password";

        //generate a random code and store that into the database, deactivate account
        Map<String, String> input = new HashMap<>();
        input.put("email_logo", ServerConfig.getInstance().get(ServerConfig.EMAIL_LOGO_URL));
        input.put("email", email);
        input.put("pwd", pwd);

        //HTML mail content
        String htmlText = MailUtil.readEmailFromHtml("mail-templates/forget-password.html", input);

        MailSender mailer = new MailSender();

        try {
            mailer.sendHtmlEmail(email, subject, htmlText);
            status = true;
            //System.out.println("Email sent.");
        } catch (Exception ex) {
            status = false;
            //System.out.println("Failed to sent email.");
            logger.error(ex.toString());
        }
        return status;
    }
}
