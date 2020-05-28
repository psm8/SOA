package util;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class RandomUtil {
    public String getRandomName(String name){
        return name + (int) (10 * Math.random());
    }

    public Integer getRandomAge(){
        return (int) (18 + (100 * Math.random()));
    }

    public byte[] getRandomAvatar() throws IOException {
        InputStream inputStream =
                getClass().getClassLoader().getResourceAsStream("config/fake-data/avatar"
                        + (int) (1 + 5 * Math.random())+".png");

        return IOUtils.toByteArray(inputStream);
    }
}
