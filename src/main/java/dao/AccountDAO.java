package dao;

import model.Account;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

/**
 * Created by Anton on 12/01/2016.
 */
public interface AccountDAO {

    @SqlQuery("SELECT account_password,"
                  + " account_mail, account_role FROM `account` "
                  + "WHERE acount_email = :email")
    Account getCredentials(@Bind("email") String username);
}
