package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import logistics.entities.User;
import logistics.security.Hasher;
import org.hibernate.Hibernate;

@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private Hasher hasher;

    public User findOrFail(String email) {
        var user = entityManager.getReference(User.class, email);
        Hibernate.initialize(user);
        return user;
    }

    public boolean canLogin(String email, String password) {
        var user = findOrFail(email);
        return user != null && user.getPassword().equals(hasher.hash(password));
    }
}
