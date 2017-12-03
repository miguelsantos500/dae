package ejbs;

import ejbs.users.CourseBean;
import ejbs.users.StudentBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;


@Singleton
@Startup
public class ConfigBean {

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");    
    
    @EJB
    private CourseBean courseBean;
    @EJB
    private StudentBean studentBean;
    @EJB
    private ProjectProposalBean projectProposalBean;
    
    @PostConstruct
    public void populateBD() {

        try {

            courseBean.create(1, "EI");
            courseBean.create(2, "IS");
            courseBean.create(3, "JDM");
            courseBean.create(4, "SIS");
            courseBean.create(5, "MEI-CM");
            courseBean.create(6, "MGSIM");

            studentBean.create("1111111", "Manuel", "Manuel", "dae.ei.ipleiria@gmail.com", 1);
            studentBean.create("2222222", "Antonio", "António", "dae.ei.ipleiria@gmail.com", 1);
            studentBean.create("3333333", "Ana", "Ana", "dae.ei.ipleiria@gmail.com", 2);
            studentBean.create("4444444", "Jose", "José", "dae.ei.ipleiria@gmail.com", 2);
            studentBean.create("5555555", "Maria", "Maria", "dae.ei.ipleiria@gmail.com", 3);
            studentBean.create("6666666", "Joaquim", "Joaquim", "dae.ei.ipleiria@gmail.com", 3);
            studentBean.create("7777777", "Alzira", "Alzira", "dae.ei.ipleiria@gmail.com", 4);
            studentBean.create("8888888", "Pedro", "Pedro", "dae.ei.ipleiria@gmail.com", 4);

            logger.info("Creating");
            projectProposalBean.create(1, "PROJECT", "My First Project",
                    new LinkedList<>(Arrays.asList("Programming", "Management")),
                    "1111111", "This is my first abstract", 
                    new LinkedList<>(Arrays.asList("Pass", "Have more than 12")),
                    new ArrayList<>(Arrays.asList("Wikipedia", "Google")),
                    "Do it the day before", "My Basement", 
                    new LinkedList<>(Arrays.asList("Pass", "Have more than 12")),
                    "Literaly Zero Euros", 
                    new LinkedList<>(Arrays.asList("Mum", "Pops")));
            
            
        } catch(Exception e){
            logger.warning(e.getMessage());
        }
    }
}
