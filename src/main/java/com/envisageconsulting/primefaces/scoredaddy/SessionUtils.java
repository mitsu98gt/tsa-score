package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.domain.Account;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class SessionUtils {

    public static int getAccountId() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Account account = (Account) sessionMap.get(Constants.SESSION_ACCOUNT);
        return null == account.getId() ? 0 : Integer.valueOf(account.getId());
    }

    public static String getAccountName() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        Account account = (Account) sessionMap.get(Constants.SESSION_ACCOUNT);
        return account.getName();
    }

    public static void refreshPage() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
}
