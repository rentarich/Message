package si.fri.rso.message.api.v1.resources;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.cdi.Log;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.eclipse.microprofile.metrics.annotation.Counted;
import si.fri.rso.message.services.beans.ItemBean;
import si.fri.rso.message.services.beans.MessageBean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;

@ApplicationScoped
@Path("borrow")
@Log
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BorrowResource {

    private Client httpClient;
    private String baseUrl;

    @Inject
    private MessageBean messageBean;

    private com.kumuluz.ee.logs.Logger logger = LogManager.getLogger(BorrowResource.class.getName());

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
    }


    @POST
    @Operation(description = "Send message/notification to user email for borrow {borrowId} confirmation.", summary = "Send email confirmation", tags = "message", responses = {
            @ApiResponse(responseCode = "201",
                    description = "Email sent to user's email address."
            ),
            @ApiResponse(responseCode = "400",
                    description = "Email wasn't send to user's email address."
            )})
    @Path("{borrowId}/message")
    @Counted
    public Response sendMessage(@PathParam("borrowId") int borrowId) throws UnirestException {

        boolean sent = messageBean.sendMessage(borrowId);

        if (sent) {
            logger.info("Email successfully sent");
            return Response.status(Response.Status.CREATED).build();
        } else {
            logger.info("Email could not be sent.");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @POST
    @Operation(description = "ASYNC Send message/notification to user email for borrow {borrowId} confirmation.", summary = "Send email confirmation", tags = "message", responses = {
            @ApiResponse(responseCode = "201",
                    description = "Email sent to user's email address."
            ),
            @ApiResponse(responseCode = "400",
                    description = "Email wasn't send to user's email address."
            )})
    @Path("{borrowId}/async")
    @Counted
    public CompletionStage<Void> sendMessageAsync(@PathParam("borrowId") int borrowId) throws UnirestException {

        boolean sent = messageBean.sendMessage(borrowId);

        if (sent) {
            logger.info("Email successfully sent");
            return (CompletionStage<Void>) Response.status(Response.Status.CREATED).build();
        } else {
            logger.info("Email could not be sent.");
            return (CompletionStage<Void>) Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
