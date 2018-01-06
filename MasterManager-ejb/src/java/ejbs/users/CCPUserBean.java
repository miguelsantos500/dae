/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs.users;

import exceptions.EntityDoesNotExistException;
import exceptions.EntityAlreadyExistsException;
import entities.users.CCPUser;
import entities.users.User;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Soraia <soraiabasso@outlook.pt>
 */
@Stateless
public class CCPUserBean {

    @PersistenceContext
    private EntityManager em;

    public void create(String username, String password, String name, String email)
            throws EntityAlreadyExistsException {
        try {
            if (em.find(User.class, username) != null) {
                throw new EntityAlreadyExistsException(
                        "Um utilizador já existe com esse username.");
            }
            em.persist(new CCPUser(username, password, name, email));
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(String username, String password, String name, String email)
            throws EntityDoesNotExistException, MyConstraintViolationException,
            MyConstraintViolationException {
        try {
            CCPUser ccpuser = em.find(CCPUser.class, username);
            if (ccpuser == null) {
                throw new EntityDoesNotExistException(
                        "Não existe um utilizador CCP com esse username");
            }
            ccpuser.setName(name);
            ccpuser.setEmail(email);

            em.merge(ccpuser);

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(
                    Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(String username) throws EntityDoesNotExistException {
        try {
            CCPUser ccpuser = em.find(CCPUser.class, username);
            if (ccpuser == null) {
                throw new EntityDoesNotExistException(
                        "There is no CCP user with thar username");
            }

            em.remove(ccpuser);

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public boolean isCCPUser(String email) {
        try {
            List<CCPUser> ccpUsers = em.createNamedQuery("getAllCCPUsers").getResultList();
            for (CCPUser ccpUser : ccpUsers) {
                if (ccpUser.getEmail().equals(email)) {
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

}
