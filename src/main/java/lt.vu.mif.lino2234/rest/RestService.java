package lt.vu.mif.lino2234.rest;

import lt.vu.mif.lino2234.bo.OrderBo;
import lt.vu.mif.lino2234.views.OrderView;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@ApplicationScoped
@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
public class RestService {

    @Inject
    OrderBo orderBo;

    @GET
    @Path("/{orderId}")
    public OrderView find(@PathParam("orderId") Long orderId) {
        return orderBo.findOne(orderId);
    }

    @POST
    @Path("/save")
    public OrderView save( OrderView order) {
        return orderBo.saveToEntity(order);
    }

    @PUT
    @Path("/create/{author}/{title}")
    public OrderView create( @PathParam("author") String author, @PathParam("title") String title) {
        return orderBo.createEntity(author, title);
    }
}
