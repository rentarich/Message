package si.fri.rso.message.api.v1.resources;


/*import com.kumuluz.ee.cors.annotations.CrossOrigin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;*/


import com.mashape.unirest.http.exceptions.UnirestException;
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


    /*@Operation(description = "Vrni vse sezname", summary = "Vrni seznam vseh nakupovalnih seznamov.", tags = "seznami", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Vrnjen seznam nakupovalnih seznamov.",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = NakupovalniSeznam.class))),
                    headers = {@Header(name = "X-Total-Count", schema = @Schema(type = "integer"))}
            )})*/

    @POST
    @Path("{id}/message")
    public Response sendMessage(@PathParam("id") int borrowId) throws UnirestException {

        boolean sent = messageBean.sendMessage(borrowId);

        if (sent) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
