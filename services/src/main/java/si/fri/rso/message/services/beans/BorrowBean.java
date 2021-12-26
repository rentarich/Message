package si.fri.rso.message.services.beans;

import com.kumuluz.ee.logs.LogManager;
import si.fri.rso.message.models.Borrow;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;


@ApplicationScoped
public class BorrowBean {
    private com.kumuluz.ee.logs.Logger logger = LogManager.getLogger(BorrowBean.class.getName());
    private String idBean;

    @PostConstruct
    private void init() {
        idBean = UUID.randomUUID().toString();
        logger.info("Init bean: " + BorrowBean.class.getSimpleName() + " idBean: " + idBean);
    }

    @PreDestroy
    private void destroy() {
        logger.info("Deinit bean: " + BorrowBean.class.getSimpleName() + " idBean: " + idBean);
    }

    @PersistenceContext(unitName = "item-jpa")
    private EntityManager em;

    public Borrow getBorrow(Integer borrowId) {
        Borrow borrow = em.find(Borrow.class, borrowId);
        return borrow;
    }
}

