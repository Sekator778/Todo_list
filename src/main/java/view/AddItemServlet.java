package view;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Item;
import persistence.HbmTodo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

/**
 *
 */

public class AddItemServlet extends HttpServlet {
    private final HbmTodo database = HbmTodo.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder builder = new StringBuilder();
        reader.lines().forEach(builder::append);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(builder.toString());
        String description = jsonNode.get("description").asText();
        ObjectNode responseNode = mapper.createObjectNode();
        LocalDate date = LocalDate.now();
        Item saveItem = new Item(description, date, "undone");
        if (database.save(saveItem)) {
            responseNode.put("id", saveItem.getId())
                    .put("description", description)
                    .put("created", date.toString())
                    .put("done", "undone");
        } else {
            responseNode.put("success", false);
        }
        PrintWriter writer = resp.getWriter();
        writer.append(mapper.writeValueAsString(responseNode));
        writer.flush();
    }
}
