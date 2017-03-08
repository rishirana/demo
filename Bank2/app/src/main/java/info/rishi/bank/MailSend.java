package info.rishi.bank;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSend extends  javax.mail.Authenticator{


    private String user="ritesh.rana53@gmail.com";
    private String password="swtuchoco08";

    static {
        Security.addProvider(new JSSEProvider());
    }
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }
    public void send(String email,int accountno,String pass,String name,int i)
    {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host","smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.quitwait", "false");

        Session session = Session.getDefaultInstance(props,this);
        if(i==1) {
            String msgBody = "Hello User: "
                    + name
                    + ".\nYour account has been successfully created.\n\nThe Details Given are:\n\nEmail: "
                    + email + "\nPassword: " + pass + "\nAccount No :" + accountno;
            Message msg = new MimeMessage(session);
            try {
                msg.setFrom(new InternetAddress("ritesh.rana53@gmail.com",
                        "ADMIN Rishi"));
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
                        email, name));
                msg.setSubject("Registration Successful");
                msg.setText(msgBody);
                Transport.send(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(i==2) {
            String msgBody = "Hello Admin : "
                    + name
                    + ".\nHere is your Password :\n\nThe Details Given are:\n\nEmail: "
                    + email + "\nPassword: " + pass + "\nAccount No :" + accountno;
            Message msg = new MimeMessage(session);
            try {
                msg.setFrom(new InternetAddress("ritesh.rana53@gmail.com",
                        " Rishi m-VnK"));
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
                        email, name));
                msg.setSubject("For Forgot Password");
                msg.setText(msgBody);
                Transport.send(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(i==0) {
            String msgBody = "Hello User : "
                    + name
                    + ".\nHere is your Password:\n\nThe Details Given are:\n\nEmail: "
                    + email + "\nPassword: " + pass + "\nAccount No :" + accountno;
            Message msg = new MimeMessage(session);
            try {
                msg.setFrom(new InternetAddress("ritesh.rana53@gmail.com",
                        " Rishi m-VnK"));
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
                        email, name));
                msg.setSubject(" For Forgot password");
                msg.setText(msgBody);
                Transport.send(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(i==3) {
            String msgBody = "Hello Admin : "
                    + ".\nFor creating a new account, an application has been sent by :"+name
                    +"\n Please check. ";
            Message msg = new MimeMessage(session);
            try {
                msg.setFrom(new InternetAddress("ritesh.rana53@gmail.com",
                        " Rishi m-VnK"));
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
                        email, name));
                msg.setSubject("New Application Received");
                msg.setText(msgBody);
                Transport.send(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        public ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }

}
