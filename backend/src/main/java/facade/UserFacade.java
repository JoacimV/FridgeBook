package facade;

import entity.Ingredient;
import entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

public class UserFacade {

    private EntityManagerFactory emf;

    public UserFacade(String persistenceUnit) {
        this.emf = Persistence.createEntityManagerFactory(persistenceUnit);
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void main(String[] args) {
        new UserFacade("PU").starter();
    }

    private void starter() {
        User user = new User("Lars", "1234");
        user.addIngredient(new IngredientFacade("PU").getIngredientByName("Mælk"));
        createUser(user);
    }

    public User getUserById(String username) {
        return getEntityManager().find(User.class, username);
    }

    public List<User> getUsers() {
        return getEntityManager().createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public User createUser(User user) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (RollbackException r) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return getUserById(user.getUserName());
    }

    public User updateUser(User user) {
        EntityManager em = getEntityManager();
        User userInDB = getUserById(user.getUserName());
        try {
            em.getTransaction().begin();
            userInDB = em.merge(user);
            em.getTransaction().commit();
        } catch (RollbackException r) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return userInDB;
    }

    public boolean deleteUser(String username) {
        EntityManager em = getEntityManager();
        User user = getUserById(username);
        try {
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
        } catch (RollbackException r) {
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
        return true;
    }
}
