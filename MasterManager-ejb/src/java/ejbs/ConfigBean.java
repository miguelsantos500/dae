package ejbs;

import ejbs.users.CCPUserBean;
import ejbs.users.CourseBean;
import ejbs.users.InstitutionBean;
import ejbs.users.StudentBean;
import ejbs.users.TeacherBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    private CCPUserBean ccpUserBean;
    @EJB
    private CourseBean courseBean;
    @EJB
    private StudentBean studentBean;
    @EJB
    private TeacherBean teacherBean;
    @EJB
    private ProjectProposalBean projectProposalBean;
    @EJB
    private PublicTestBean publicTestBean;
     @EJB
    private InstitutionBean institutionBean;
      @EJB
    private ApplicationBean applicationBean;

    @PostConstruct
    public void populateBD() {

        try {

            ccpUserBean.create("c1", "c1", "Ricardo", "dae.ricardo.ipleiria@gmail.com");
            ccpUserBean.create("c2", "c2", "Tati", "dae.tati.ipleiria@gmail.com");

            courseBean.create(1, "EI");
            courseBean.create(2, "IS");
            courseBean.create(3, "JDM");
            courseBean.create(4, "SIS");
            courseBean.create(5, "MEI-CM");
            courseBean.create(6, "MGSIM");

            studentBean.create("s1", "s1", "s1", "s1.ei.ipleiria@gmail.com", 1);
            studentBean.create("1111111", "Manuel", "Manuel", "dae.ei.ipleiria@gmail.com", 1);
            studentBean.create("2222222", "Antonio", "Antonio", "dae.ei.ipleiria@gmail.com", 1);
            studentBean.create("3333333", "Ana", "Ana", "dae.ei.ipleiria@gmail.com", 2);
            studentBean.create("4444444", "Jose", "Jose", "dae.ei.ipleiria@gmail.com", 2);
            studentBean.create("5555555", "Maria", "Maria", "dae.ei.ipleiria@gmail.com", 3);
            studentBean.create("6666666", "Joaquim", "Joaquim", "dae.ei.ipleiria@gmail.com", 3);
            studentBean.create("7777777", "Alzira", "Alzira", "dae.ei.ipleiria@gmail.com", 4);
            studentBean.create("8888888", "Pedro", "Pedro", "dae.ei.ipleiria@gmail.com", 4);
            
            teacherBean.create("t1", "t1", "t1", "t1.jose.ipleiria@gmail.com");
            teacherBean.create("3243243", "José", "José", "dae.jose.ipleiria@gmail.com");
            teacherBean.create("2446546", "Tati", "Tati", "dae.tati.ipleiria@gmail.com");
            teacherBean.create("9473829", "Marco", "Marco", "dae.marco.ipleiria@gmail.com");
            teacherBean.create("3243244", "Carlos", "Carlos", "dae.carlos.ipleiria@gmail.com");
            teacherBean.create("3243245", "Leonel", "Leonel", "dae.leonel.ipleiria@gmail.com");
            teacherBean.create("3243247", "Ricardo", "Ricardo", "dae.ricardo.ipleiria@gmail.com");

            projectProposalBean.create(1, "PROJECT", "My First Project",
                    new LinkedList<>(Arrays.asList("Programming", "Management")),
                    "3243243", "This is my first abstract",
                    new LinkedList<>(Arrays.asList("Pass", "Have more than 12")),
                    new ArrayList<>(Arrays.asList("Wikipedia", "Google")),
                    "Do it the day before", "My Basement",
                    new LinkedList<>(Arrays.asList("Pass", "Have more than 12")),
                    "Literaly Zero Euros",
                    new LinkedList<>(Arrays.asList("Mum", "Pops")));
            
           projectProposalBean.create(2, "PROJECT", "My First",
                    new LinkedList<>(Arrays.asList("Programming", "estion")),
                    "2446546", "This is my second abstract",
                    new LinkedList<>(Arrays.asList("Pass", "Have more than 12")),
                    new ArrayList<>(Arrays.asList("Wikipedia", "Google")),
                    "Do it the day before", "My Basement",
                    new LinkedList<>(Arrays.asList("Pass", "Have more than 12")),
                    "Literaly Zero Euros",
                    new LinkedList<>(Arrays.asList("Mum", "Pops")));
            
            projectProposalBean.create(3, "PROJECT", "My Project",
                    new LinkedList<>(Arrays.asList("Alias", "Management")),
                    "9473829", "This is my first abstract",
                    new LinkedList<>(Arrays.asList("Pass", "Have more than 12")),
                    new ArrayList<>(Arrays.asList("Wikipedia", "Google")),
                    "Do it the day before", "My Basement",
                    new LinkedList<>(Arrays.asList("Pass", "Have more than 12")),
                    "Literaly Zero Euros",
                    new LinkedList<>(Arrays.asList("Mum", "Pops")));
            
            projectProposalBean.create(4, "PROJECT", "Project XPTO",
                    new LinkedList<>(Arrays.asList("Programming", "Management")),
                    "3243244", "This is my first abstract",
                    new LinkedList<>(Arrays.asList("Pass", "Have more than 12")),
                    new ArrayList<>(Arrays.asList("Wikipedia", "Google")),
                    "Do it the day before", "My Basement",
                    new LinkedList<>(Arrays.asList("Pass", "Have more than 12")),
                    "Literaly Zero Euros",
                    new LinkedList<>(Arrays.asList("Mum", "Pops")));
            
            projectProposalBean.create(5, "PROJECT", "My  God",
                    new LinkedList<>(Arrays.asList("Programming", "Management")),
                    "3243245", "This is my first abstract",
                    new LinkedList<>(Arrays.asList("Pass", "Have more than 12")),
                    new ArrayList<>(Arrays.asList("Wikipedia", "Google")),
                    "Do it the day before", "My Basement",
                    new LinkedList<>(Arrays.asList("Pass", "Have more than 12")),
                    "Literaly Zero Euros",
                    new LinkedList<>(Arrays.asList("Mum", "Pops")));
            
            projectProposalBean.create(6, "PROJECT", "Project from hell",
                    new LinkedList<>(Arrays.asList("Programming", "Management")),
                    "3243245", "This is my first abstract",
                    new LinkedList<>(Arrays.asList("Pass", "Have more than 12")),
                    new ArrayList<>(Arrays.asList("Wikipedia", "Google")),
                    "Do it the day before", "My Basement",
                    new LinkedList<>(Arrays.asList("Pass", "Have more than 12")),
                    "Literaly Zero Euros",
                    new LinkedList<>(Arrays.asList("Mum", "Pops")));
            
            projectProposalBean.create(7, "PROJECT", "Project from heaven",
                    new LinkedList<>(Arrays.asList("Programming", "Management")),
                    "3243245", "This is my first abstract",
                    new LinkedList<>(Arrays.asList("Pass", "Have more than 12")),
                    new ArrayList<>(Arrays.asList("Wikipedia", "Google")),
                    "Do it the day before", "My Basement",
                    new LinkedList<>(Arrays.asList("Pass", "Have more than 12")),
                    "Literaly Zero Euros",
                    new LinkedList<>(Arrays.asList("Mum", "Pops")));

            
            applicationBean.create("s1", 1, "Quero ser estudante.");
            applicationBean.create("s1", 2, "Quero ser estudante.");
            applicationBean.create("s1", 3, "Quero ser estudante.");
            applicationBean.create("s1", 4, "Quero ser estudante.");
            applicationBean.create("s1", 5, "Quero ser estudante.");

            
            
            publicTestBean.create(1, "Title1", new Date(2000, 1, 1), "Sala yyy", "www.link.com", "2446546", "3243243", "Pedro Oliveia", "yyy@gmail.com", "1111111");
            publicTestBean.create(2, "Title2", new Date(2000, 1, 1), "Sala xxx", "www.link.com", "9473829", "3243244", "Pedro Silva", "xxx@gmail.com", "2222222");
            publicTestBean.create(3, "Title3", new Date(2000, 1, 1), "Sala zzz", "www.link.com", "3243245", "3243247", "Joao Oliveia", "zzz@gmail.com", "3333333");
            publicTestBean.create(4, "Title4", new Date(2000, 1, 1), "Sala aaa", "www.link.com", "3243247", "3243244", "Ricado Tati", "aaa@gmail.com", "4444444");

            institutionBean.create("xxx1", "ESTG", "ESTG", "estg.ipleiria@ipleiria.pt");
            institutionBean.create("xxx2", "ESECS", "ESECS", "esecs.ipleiria@ipleiria.pt");
            institutionBean.create("xxx3", "ARTES", "ARTES", "aartes.ipleiria@ipleiria.pt");
            institutionBean.create("xxx4", "ENFIM", "ENFIM", "enfim.ipleiria@ipleiria.pt");
            institutionBean.create("xxx5", "INSTITUICAO", "INSTITUICAO", "instituicao.ipleiria@ipleiria.pt");
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
        }
    }
}
