package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import logistics.entities.User;
import logistics.security.Hasher;

@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Hasher hasher;

    private SecurityContext securityContext;

    public User findOrFail(String email) {
        var query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        return query.getResultStream().findFirst().orElse(null); // Return null if no result
    }


    public boolean canLogin(String email, String password) {
        var user = findOrFail(email);
        return user != null && user.getPassword().equals(hasher.hash(password));
    }

}
