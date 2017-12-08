package ejbs.users;

import dtos.InstitutionDTO;
import entities.users.Institution;
import entities.users.User;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/institutions")
public class InstitutionBean {

    @PersistenceContext
    private EntityManager em;

    public void create(String username, String password, String name, String email)
            throws EntityAlreadyExistsException {
        try {
            if (em.find(User.class, username) != null) {
                throw new EntityAlreadyExistsException(
                        "Um utilizador já existe com esse username.");
            }
            em.persist(new Institution(username, password, name, email));
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
            Institution institution = em.find(Institution.class, username);
            if (institution == null) {
                throw new EntityDoesNotExistException(
                        "Não existe um utilizador Instituição com esse username");
            }
            institution.setName(name);
            institution.setEmail(email);

            em.merge(institution);

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
            Institution institution = em.find(Institution.class, username);
            if (institution == null) {
                throw new EntityDoesNotExistException(
                        "Não existe um utilizador Instituição com esse username.");
            }

            em.remove(institution);

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<InstitutionDTO> getAll() {
        try {
            List<Institution> institutions = em.createNamedQuery("getAllInstitutions").getResultList();
            return institutionsToDTOs(institutions);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException(e.getMessage());
        }
    }

    public InstitutionDTO institutionToDTO(Institution institution) {
        return new InstitutionDTO(
                institution.getUsername(),
                null,
                institution.getName(),
                institution.getEmail());
    }

    public List<InstitutionDTO> institutionsToDTOs(List<Institution> institutions) {
        List<InstitutionDTO> institutiondtos = new ArrayList<>();

        for (Institution i : institutions) {
            institutiondtos.add(institutionToDTO(i));
        }
        return institutiondtos;
    }

    @PUT
    @Path("/update")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateInstitution(InstitutionDTO institutionDTO)
            throws MyConstraintViolationException {

        try {
            Institution institution = em.find(Institution.class, institutionDTO.getUsername());
            if (institution == null) {
                //  throw new EntityDoesNotExistsException("There is no student with that username.");
            }

            institution.setPassword(institutionDTO.getPassword());
            institution.setName(institutionDTO.getName());
            institution.setEmail(institutionDTO.getEmail());
            em.merge(institution);

        } catch (Exception e) {
            throw e;
        }

    }

}
