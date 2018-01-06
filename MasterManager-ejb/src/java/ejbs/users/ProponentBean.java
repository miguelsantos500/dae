package ejbs.users;

import dtos.ProponentDTO;
import ejbs.EmailBean;
import entities.users.Proponent;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/proponents")
public class ProponentBean {

    @PersistenceContext
    private EntityManager em;

    @EJB
    EmailBean emailBean;

    @PUT
    @Path("/update")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void update(ProponentDTO proponentDTO)
            throws MyConstraintViolationException, EntityDoesNotExistException {

        try {
            Proponent proponent = em.find(Proponent.class, proponentDTO.getUsername());
            if (proponent == null) {
                throw new EntityDoesNotExistException("There is no proponent with that username.");
            }

            proponent.setPassword(proponentDTO.getPassword());
            proponent.setName(proponentDTO.getName());
            proponent.setEmail(proponentDTO.getEmail());
            em.merge(proponent);

        } catch (EntityDoesNotExistException e) {
            throw e;
        }

    }

    public void remove(String username) throws EntityDoesNotExistException {
        try {
            Proponent proponent = em.find(Proponent.class, username);
            if (proponent == null) {
                throw new EntityDoesNotExistException(
                        "There is no proponent with that username.");
            }

            em.remove(proponent);

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<ProponentDTO> getAll() {
        try {
            List<Proponent> proponents = 
                    em.createNamedQuery("getAllProponents").getResultList();
            return proponentsToDTOs(proponents);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException(e.getMessage());
        }
    }

    public ProponentDTO proponentToDTO(Proponent proponent) {
        return new ProponentDTO(
                proponent.getUsername(),
                null,
                proponent.getName(),
                proponent.getEmail());
    }

    public List<ProponentDTO> proponentsToDTOs(List<Proponent> proponents) {
        List<ProponentDTO> proponentdtos = new ArrayList<>();

        for (Proponent p : proponents) {
            proponentdtos.add(proponentToDTO(p));
        }
        return proponentdtos;
    }

    public void sendEmailToProponent(String username, String subject,
            String message)
            throws MessagingException, EntityDoesNotExistException {
        try {
            Proponent proponent = em.find(Proponent.class, username);
            if (proponent == null) {
                throw new EntityDoesNotExistException("There is no proponent with that username. (" + username + ")");
            }

            emailBean.send(
                    proponent.getEmail(),
                    subject,
                    "Bom dia " + proponent.getName() + "," + System.lineSeparator()
                    + message);

        } catch (MessagingException | EntityDoesNotExistException e) {
            throw e;
        }
    }

}
