package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.User;
import facade.UserFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import jsonMapper.UserJson;

@Path("user")
public class UserResource {

    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final UserFacade USERFACADE = new UserFacade("PU");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsers() {
        List<User> users = USERFACADE.getUsers();
        List<UserJson> usersJson = new ArrayList();
        users.forEach(user -> {
            usersJson.add(new UserJson(user));
        });
        return GSON.toJson(usersJson);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserById(@PathParam("id") String username) {
        return GSON.toJson(new UserJson(USERFACADE.getUserById(username)));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createUser(String content) {
        User user = GSON.fromJson(content, User.class);
        USERFACADE.createUser(user);
        return "Created";
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateUser(String content) {
        User user = GSON.fromJson(content, User.class);
        USERFACADE.updateUser(user);
        return "Updated";
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteUser(@PathParam("id") String username) {
        USERFACADE.deleteUser(username);
        return "Deleted";
    }
}
