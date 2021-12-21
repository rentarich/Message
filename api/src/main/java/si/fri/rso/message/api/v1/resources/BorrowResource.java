package si.fri.rso.message.api.v1.resources;

import com.mashape.unirest.http.exceptions.UnirestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import si.fri.rso.message.services.beans.MessageBean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@ApplicationScoped
@Path("borrow")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BorrowResource {

    private Client httpClient;
    private String baseUrl;

    @Inject
    private MessageBean messageBean;

    private Logger logger = Logger.getLogger(BorrowResource.class.getName());

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
    public Response sendMessage(@PathParam("borrowId") int borrowId) throws UnirestException {

        boolean sent = messageBean.sendMessage(borrowId);

        if (sent) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
