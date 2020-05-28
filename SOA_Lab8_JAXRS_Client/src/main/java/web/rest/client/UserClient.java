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
        List<UserEntity> users = Arrays.asList(response.readEntity(UserEntity[].class));
        response.close();

        return users;
    }

    public UserEntity getUser(int id) {

        final ResteasyClient client = new ResteasyClientBuilder().build();
        final ResteasyWebTarget target = client
                .target(FULL_PATH + "/" + id);
        Response response = target.request().accept("application/json").get();
        UserEntity user = response.readEntity(UserEntity.class);
        response.close();

        return user;
    }

    public UserEntity saveUser(UserEntity user) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(FULL_PATH);
        Response response = target.request().accept("application/json")
                .post(Entity.entity(user, "application/json"));
        UserEntity result = response.readEntity(UserEntity.class);
        response.close();

        return result;
    }

    public UserEntity updateUser(UserEntity user) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(FULL_PATH + "/" + user.getId());
        Response response = target.request().accept("application/json")
                .method("PATCH", Entity.entity(user, "application/json"));
        UserEntity result = response.readEntity(UserEntity.class);
        response.close();

        return result;
    }

    public void deleteUser(UserEntity user) throws Exception{
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(FULL_PATH + "/" + user.getId());
        Response response = target.request().accept("application/json")
                .delete();
        if(response.getStatus() != 200){
            response.close();
            throw new Exception(response.getStatusInfo().toString());
        }
        response.close();
    }

}
