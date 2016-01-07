package petersinspiratiepakket.actorius.service;

import petersinspiratiepakket.actorius.model.User;
import petersinspiratiepakket.actorius.persistence.UserDAO;

import java.util.Collection;

/**
 *
 * @author Peter van Vliet
 */
public class UserService extends BaseService<User>
{
    private final UserDAO dao;
    
    public UserService(UserDAO dao)
    {
        this.dao = dao;
    }
    
    public Collection<User> getAll()
    {
        return dao.getAll();
    }
    
    public User get(int id)
    {
        return requireResult(dao.get(id));
    }
    
    public void add(User user)
    {
        user.setRoles(new String[] { "GUEST" });
        
        dao.add(user);
    }
    
    public void update(User authenticator, int id, User user)
    {
        // Controleren of deze gebruiker wel bestaat
        User oldUser = get(id);
        
        if (!authenticator.hasRole("ADMIN"))
        {
            // Vaststellen dat de geauthenticeerde gebruiker
            // zichzelf aan het aanpassen is
            assertSelf(authenticator, oldUser);
        }
        
        dao.update(id, user);
    }
    
    public void delete(int id)
    {
        // Controleren of deze gebruiker wel bestaat
        User user = get(id);
        
        dao.delete(id);
    }
}
