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
import java.util.List;

/**
 * view item object
 */
public class ItemServlet extends HttpServlet {

    private final HbmTodo database = HbmTodo.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Item> itemList;
        String done = req.getParameter("done");
        if (done.equals("done")) {
            itemList = database.findItemDone("done");
        } else if (done.equals("undone")) {
            itemList = database.findItemDone("undone");
        } else {
            itemList = database.findAllItem();
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int size = itemList.size();
        for (int i = 0; i < size; i++) {
            builder.append(itemList.get(i).toJsonString());
            if (i == size - 1) {
                break;
            }
            builder.append(",");
        }
        builder.append("]");
        PrintWriter writer = resp.getWriter();
        writer.println(builder);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder builder = new StringBuilder();
        reader.lines().forEach(builder::append);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(builder.toString());
        int id = node.get("id").asInt();
        String done = node.get("done").asText();
        ObjectNode responseNode = mapper.createObjectNode();
        if (database.setDone(id, done)) {
            responseNode.put("success", true).put("id", id).put("done", done);
        } else {
            responseNode.put("success", false);
        }
        PrintWriter writer = resp.getWriter();
        writer.append(mapper.writeValueAsString(responseNode));
        writer.flush();
    }
}
