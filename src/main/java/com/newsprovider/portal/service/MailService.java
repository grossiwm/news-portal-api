package com.newsprovider.portal.service;

import com.newsprovider.portal.model.Subscription;
import com.newsprovider.portal.model.User;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;

@Service
public class MailService {

    @Value("${sendgrid.verifiedSenderIdentity}")
    private String verifiedSenderIdentity;

    public void notifyUserOfSubscription(Subscription subscription) throws IOException {

        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        User user = subscription.getUser();
        String subject = "PREMIUM ACCOUNT ACTIVATED";
        Email to = new Email(user.getEmail());

        String contentPayload = "Hi " + user.getName() + ",\nYour are now a member of our " +
                "Premium Account.\nYou subscription started at " +
                "" + simpleDateFormat.format(subscription.getInitialDate().getTime()) + "" +
                " and ends at " + simpleDateFormat.format(subscription.getEndDate().getTime()) + "" +
                "\nRegards.";

        Content content = new Content("text/plain", contentPayload);
        Mail mail = new Mail(null, subject, to, content);
        send(mail);
    }

    private void send(Mail mail) throws IOException {
        Email from = new Email(verifiedSenderIdentity);
        mail.setFrom(from);
        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}
