package view;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Task;
import persistence.HbmTodo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;

public class AddItemServlet extends HttpServlet {
    private final HbmTodo database = HbmTodo.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder builder = new StringBuilder();
        reader.lines().forEach(builder::append);
        ObjectMapper mapper = new ObjectMapper();
        String json = builder.toString();
        HashMap map = mapper.readValue(json, HashMap.class);
        String description = (String) map.get("description");
        ObjectNode responseNode = mapper.createObjectNode();
        LocalDate date = LocalDate.now();
        Task saveTask = new Task(description, date, "undone");
        if (database.save(saveTask)) {
            responseNode.put("id", saveTask.getId())
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
