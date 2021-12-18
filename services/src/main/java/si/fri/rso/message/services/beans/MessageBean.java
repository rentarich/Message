package si.fri.rso.message.services.beans;

import org.json.JSONObject;
import si.fri.rso.message.models.Borrow;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;


@ApplicationScoped
public class MessageBean {
    private Logger log = Logger.getLogger(MessageBean.class.getName());
    private String idBean;

    @Inject
    private BorrowBean borrowBean;

    @PostConstruct
    private void init(){
        idBean = UUID.randomUUID().toString();
        log.info("Init bean: " + MessageBean.class.getSimpleName() + " idBean: " + idBean);
    }

    @PreDestroy
    private void destroy(){
        log.info("Deinit bean: " + MessageBean.class.getSimpleName() + " idBean: " + idBean);
    }

    @PersistenceContext(unitName = "item-jpa")
    private EntityManager em;

    public boolean sendMessage(Integer borrowId) throws UnirestException {
        Borrow borrow=borrowBean.getBorrow(borrowId);

        JSONObject obj = new JSONObject();
        JSONObject from = new JSONObject();
        obj.put("from", from);
        from.put("name",borrow.getPerson().getUsername());

        JSONObject to = new JSONObject();
        obj.put("to",to);
        to.put("name",borrow.getPerson().getUsername());
        to.put("address",borrow.getPerson().getEmail());

        obj.put("subject","Rentarich: borrow confirmation!");
        obj.put("message","Dear "+borrow.getPerson().getUsername()+"!\nYou have successfully borrowed item "+borrow.getItem().getTitle()+".\nMake sure to return it by "+borrow.getTo_date()+"\n\nThank you for using Rentarich!");

        try {

            HttpResponse<String> response = Unirest.post("https://easymail.p.rapidapi.com/send")
                    .header("content-type", "application/json")
                    .header("x-rapidapi-host", "easymail.p.rapidapi.com")
                    .header("x-rapidapi-key", "85bf59bff4msh767705b762b415bp15a380jsn9dfd0e4d08ea")
                    .body(obj.toString())
                    .asString();
            return true;
        }
        catch (Exception e){
            log.info(e.toString());
            return false;
        }

    }
}

