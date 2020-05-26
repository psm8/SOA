package web.rest.client;

import domain.UserEntity;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Entity;

import javax.ws.rs.core.Response;
import javax.xml.registry.infomodel.User;

public class MovieClient {
    final String FULL_PATH = "http://127.0.0.1:8080/SOA_Lab8_JAXRS_war_exploded/api/movie";

    public void getAllUsers() {

        final ResteasyClient client = new ResteasyClientBuilder().build();
        final ResteasyWebTarget target = client
                .target(FULL_PATH);
        String response = target.request().get(String.class);
        System.out.println(response);
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

    public UserEntity createUser(UserEntity user) {
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

    public void deleteUser() {
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
