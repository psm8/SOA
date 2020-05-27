package web.rest.client;

import domain.UserEntity;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.enterprise.context.SessionScoped;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@SessionScoped
public class UserClient implements Serializable {
    final static String FULL_PATH = "http://127.0.0.1:8080/SOA_Lab8_JAXRS_war_exploded/api/user";

    public List<UserEntity> getAllUsers() {

        final ResteasyClient client = new ResteasyClientBuilder().build();
        final ResteasyWebTarget target = client
                .target(FULL_PATH);
        Response response = target.request().accept("application/json").get();
        List<UserEntity> Users = Arrays.asList(response.readEntity(UserEntity[].class));
        response.close();

        return Users;
    }

    public UserEntity getUser(int id) {

        final ResteasyClient client = new ResteasyClientBuilder().build();
        final ResteasyWebTarget target = client
                .target(FULL_PATH + "/" + id);
        Response response = target.request().accept("application/json").get();
        UserEntity User = response.readEntity(UserEntity.class);
        response.close();

        return User;
    }

    public UserEntity saveUser(UserEntity User) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(FULL_PATH);
        Response response = target.request().accept("application/json")
                .post(Entity.entity(User, "application/json"));
        UserEntity result = response.readEntity(UserEntity.class);
        response.close();

        return result;
    }

    public UserEntity updateUser(UserEntity User) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(FULL_PATH + "/" + User.getId());
        Response response = target.request().accept("application/json")
                .method("PATCH", Entity.entity(User, "application/json"));
        UserEntity result = response.readEntity(UserEntity.class);
        response.close();

        return result;
    }

    public void deleteUser(UserEntity User) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(FULL_PATH + "/" + User.getId());
        Response response = target.request().accept("application/json")
                .delete();
        response.close();
    }

}
