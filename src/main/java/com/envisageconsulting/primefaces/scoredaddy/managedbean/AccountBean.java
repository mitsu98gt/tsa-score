package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.dao.AccountDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Account;
import com.envisageconsulting.primefaces.scoredaddy.domain.Address;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.util.Properties;

@ManagedBean(name="accountBean")
@SessionScoped
public class AccountBean implements Serializable {

    Account account = new Account();
    Address address = new Address();

    @ManagedProperty("#{accountDAO}")
    private AccountDAO dao;

    @PostConstruct
    public void init() {
        account.setAddress(address);
    }

    public void addAccount() {
        dao.addAccount(getAccount());
    }

    public void sendMail() {

        String to = "vinh_q_dang@yahoo.com";
        String from = "vinh@envisageconsulting.com";
        String host = "mail.scoredaddy.net";

        //Get the session object
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);

        //compose the message
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("Test");
            message.setText("Hello, this is example of sending email.");

            // Send message
            Transport.send(message);
            System.out.println("message sent successfully....");

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        
    }

    public void setDao(AccountDAO dao) {
        this.dao = dao;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
