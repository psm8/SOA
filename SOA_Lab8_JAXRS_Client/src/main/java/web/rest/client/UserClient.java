package web.rest.client;

import domain.UserEntity;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.enterprise.context.SessionScoped;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.xml.registry.infomodel.User;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@SessionScoped
public class UserClient implements Serializable {
    final String FULL_PATH = "http://127.0.0.1:8080/SOA_Lab8_JAXRS_war_exploded/api/user";

    public List<UserEntity> getAllUsers() {

        final ResteasyClient client = new ResteasyClientBuilder().build();
        final ResteasyWebTarget target = client
                .target(FULL_PATH);
        Response response = target.request().get();
        List<UserEntity> movies = Arrays.asList(response.readEntity(UserEntity[].class));
        response.close();

        return movies;
    }

    public void getUser() {

        final ResteasyClient client = new ResteasyClientBuilder().build();
        final ResteasyWebTarget target = client
                .target(FULL_PATH + "/100");
        Response response = target.request().get();
        User user = response.readEntity(User.class);
        System.out.println(user.toString());
        response.close();
    }

    public UserEntity saveUser(UserEntity user) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(FULL_PATH);
        Response response = target.request()
                .post(Entity.entity(user, "application/json"));
        System.out.println(response.getStatus());
        response.close();
        return new UserEntity();
    }

    public UserEntity updateUser(UserEntity user) {
        user.setName("Ram");
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(FULL_PATH + "/100");
        Response response = target.request()
                .put(Entity.entity(user, "application/json"));
        System.out.println(response.getStatus());
        response.close();
        return new UserEntity();
    }

    public void deleteUser(UserEntity user) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(FULL_PATH + "/101");
        Response response = target.request()
                .delete();
        System.out.println(response.getStatus());
        response.close();

        final ResteasyWebTarget target1 = client
                .target(FULL_PATH);
        String response1 = target1.request().get(String.class);
        System.out.println(response1);
    }
}
