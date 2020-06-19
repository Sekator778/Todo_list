package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class UserBeanTest {
    @Test
    public void whenJsonGetString() throws JsonProcessingException {
        UserBean bean = new UserBean(1, "Alex");

        String resultJson = new ObjectMapper().writeValueAsString(bean);
        System.out.println(resultJson);
        assertThat(resultJson, containsString("id"));
        assertThat(resultJson, containsString("surname"));
    }

}