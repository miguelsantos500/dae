package ejbs;

import entities.publictest.PublicTest;
import entities.users.CCPUser;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class PublicTestBean {

    @PersistenceContext
    private EntityManager em;

    public void create(int code, String title, Date testDate,
            String testHour, String place, String link, String username) throws
            EntityAlreadyExistsException, EntityDoesNotExistException {
        try {
            if (em.find(PublicTest.class, code) != null) {
                throw new EntityAlreadyExistsException(
                        "Uma prova publica já existe com esse código.");
            }
            CCPUser ccpUser = em.find(CCPUser.class, username);
            if (ccpUser == null) {
                throw new EntityDoesNotExistException(
                        "Não existe nenhum ccpuser com esse username");
            }

            em.persist(new PublicTest(code, title, testDate, testHour,
                    place, link, ccpUser));
        } catch (EntityAlreadyExistsException | EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(int code, String title, Date testDate, String testHour,
            String place, String link) throws EntityDoesNotExistException {
        try {
            PublicTest publicTest = em.find(PublicTest.class, code);
            if (publicTest == null) {
                throw new EntityDoesNotExistException(
                        "Não existe nenhuma prova publica com esse código.");
            }

            publicTest.setTitle(title);
            publicTest.setTestDate(testDate);
            publicTest.setTestHour(testHour);
            publicTest.setPlace(place);
            publicTest.setLink(link);

            em.merge(publicTest);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(int code) throws EntityDoesNotExistException {
        try {
            PublicTest publicTest = em.find(PublicTest.class, code);
            if (publicTest == null) {
                throw new EntityDoesNotExistException(
                        "Não existe nenhuma prova publica com esse código.");
            }

            em.remove(publicTest);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<PublicTest> getAll() {
        try {
            List<PublicTest> publicTests = (List<PublicTest>) em.createNamedQuery("getAllPublicTests").getResultList();
            return publicTests;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
