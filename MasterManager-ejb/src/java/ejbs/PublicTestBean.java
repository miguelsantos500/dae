package ejbs;

import dtos.PublicTestDTO;
import entities.publictest.PublicTest;
import entities.users.Student;
import entities.users.Teacher;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import java.util.ArrayList;
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

    public void create(int code, String title, Date testDateTime,
            String place, String link, String teacherJuryUsername,
            String advisorUsername, String outsideTeacherName, String outsideTeacherEmail,
            String studentUsername) throws
            EntityAlreadyExistsException, EntityDoesNotExistException {
        try {
            if (em.find(PublicTest.class, code) != null) {
                throw new EntityAlreadyExistsException(
                        "Uma prova publica já existe com esse código.");
            }

            Teacher juryPresident = em.find(Teacher.class, teacherJuryUsername);
            if (juryPresident == null) {
                throw new EntityDoesNotExistException(
                        "Não existe nenhum professore com esse username");
            }

            Teacher advisor = em.find(Teacher.class, advisorUsername);
            if (advisor == null) {
                throw new EntityDoesNotExistException(
                        "Não existe nenhum professore com esse username");
            }

            Student student = em.find(Student.class, studentUsername);
            if (student == null) {
                throw new EntityDoesNotExistException(
                        "Não existe nenhum estudante com esse username");
            }
            em.persist(new PublicTest(code, title, testDateTime,
                    place, link, juryPresident, advisor, outsideTeacherName,
                    outsideTeacherEmail, student));
        } catch (EntityAlreadyExistsException | EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void update(int code, String title, Date testDateTime,
            String place, String link, String teacherJuryUsername,
            String outideTeacherName, String outsideTeacherEmail) throws EntityDoesNotExistException {
        try {
            PublicTest publicTest = em.find(PublicTest.class, code);
            if (publicTest == null) {
                throw new EntityDoesNotExistException(
                        "Não existe nenhuma prova publica com esse código.");
            }

            Teacher juryPresident = em.find(Teacher.class, teacherJuryUsername);
            if (juryPresident == null) {
                throw new EntityDoesNotExistException(
                        "Não existe nenhum professore com esse username.");
            } else {
                publicTest.setJuryPresident(juryPresident);
            }

            publicTest.setTitle(title);
            publicTest.setTestDateTime(testDateTime);
            publicTest.setPlace(place);
            publicTest.setLink(link);
            publicTest.setOutsideTeacherName(outideTeacherName);
            publicTest.setOutsideTeacherEmail(outsideTeacherEmail);

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

    public List<PublicTestDTO> getAll() {
        try {
            List<PublicTest> publicTests = (List<PublicTest>) em.createNamedQuery("getAllPublicTests").getResultList();
            return publicTestsToDTOs(publicTests);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private List<PublicTestDTO> publicTestsToDTOs(List<PublicTest> publicTests) {
        List<PublicTestDTO> dtos = new ArrayList<>();
        for (PublicTest s : publicTests) {
            dtos.add(publicTestToDTO(s));
        }
        return dtos;
    }

    private PublicTestDTO publicTestToDTO(PublicTest publicTest) {
        return new PublicTestDTO(
                publicTest.getCode(),
                publicTest.getTitle(),
                publicTest.getTestDateTime(),
                publicTest.getPlace(),
                publicTest.getLink(),
                publicTest.getJuryPresident().getUsername(),
                publicTest.getJuryPresident().getName(),
                publicTest.getAdvisor().getUsername(),
                publicTest.getAdvisor().getName(),
                publicTest.getOutsideTeacherName(),
                publicTest.getOutsideTeacherEmail(),
                publicTest.getStudent().getUsername(),
                publicTest.getStudent().getName(),
                publicTest.getFileRecord(),
                publicTest.getTestDateTimeString());
    }
}
