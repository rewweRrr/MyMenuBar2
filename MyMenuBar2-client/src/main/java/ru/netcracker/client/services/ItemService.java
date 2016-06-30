package ru.netcracker.client.services;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import ru.netcracker.shared.Item;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by nivo0616 on 28.06.2016
 */
@Path("/ItemService")
public interface ItemService extends RestService {

    @GET
    @Path("/getItems")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    void getItems(MethodCallback<List<Item>> callback);


}
