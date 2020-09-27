package test.json;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;

/**
 *
 */

public class WriterJsonExample {
    public static void main(String[] args) throws IOException {
        //создание объекта для сериализации в JSON
        User user = new User();
        user.setName("Murka");
        user.setId(5);
        //писать результат сериализации будем во Writer(StringWriter)
        StringWriter writer = new StringWriter();
        //это объект Jackson, который выполняет сериализацию
        ObjectMapper mapper = new ObjectMapper();
        // сама сериализация: 1-куда, 2-что
        mapper.writeValue(writer, user);
        //преобразовываем все записанное во StringWriter в строку
        System.out.println("================= object -> json ==================");

        String result = writer.toString();
        System.out.println(result);
        System.out.println("================= json -> object =================");
        String jsonString = "{\"id\":5,\"name\":\"Murka after deserializable\",\"role\":null}";
        StringReader reader = new StringReader(jsonString);

        ObjectMapper mapper2 = new ObjectMapper();

        User cat = mapper2.readValue(reader, User.class);

        System.out.println(cat);

    }
}
