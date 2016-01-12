package service;

import dao.AccountDAO;
import dao.KlantDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.auth.basic.BasicCredentials;
import com.google.common.base.Optional;
import model.Account;
import model.Klant;


/**
 * @author Anton Steenvoorden
 */
public class AuthenticationService
    implements Authenticator<BasicCredentials, Account>, Authorizer<Account> {
    private final AccountDAO accountDAO;

    public AuthenticationService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public Optional<Account> authenticate(BasicCredentials credentials) throws AuthenticationException {
        Account account = accountDAO.getCredentials(credentials.getUsername());

        if (account != null && account.getPassword().equals(credentials.getPassword())) {
            return Optional.of(account);
        }

        return Optional.absent();
    }

    @Override
    public boolean authorize(Account account, String roleName) {
        return account.hasRole(roleName);
    }
}
