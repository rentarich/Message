package si.fri.rso.message.services.beans;

import si.fri.rso.message.models.Borrow;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;


@ApplicationScoped
public class BorrowBean {
    private Logger log = Logger.getLogger(BorrowBean.class.getName());
    private String idBean;

    @PostConstruct
    private void init() {
        idBean = UUID.randomUUID().toString();
        log.info("Init bean: " + BorrowBean.class.getSimpleName() + " idBean: " + idBean);
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinit bean: " + BorrowBean.class.getSimpleName() + " idBean: " + idBean);
    }

    @PersistenceContext(unitName = "item-jpa")
    private EntityManager em;

    public Borrow getBorrow(Integer borrowId) {
        Borrow borrow = em.find(Borrow.class, borrowId);
        return borrow;
    }
}

