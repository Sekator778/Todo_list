package view;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Task;
import org.json.JSONObject;
import persistence.HbmTodo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

/**
 * view item object
 */
public class LoadItemServlet extends HttpServlet {

    private final HbmTodo database = HbmTodo.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Task> taskList;
        String done = req.getParameter("done");
        if (done.equals("done")) {
            taskList = database.findItemDone("done");
        } else if (done.equals("undone")) {
            taskList = database.findItemDone("undone");
        } else {
            taskList = database.findAllItem();
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int size = taskList.size();
        for (int i = 0; i < size; i++) {
            builder.append(taskList.get(i).toJsonString());
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
        StringBuilder sb = new StringBuilder();
        reader.lines().forEach(sb::append);
        ObjectMapper mapper = new ObjectMapper();
        String json = sb.toString();
        HashMap map = mapper.readValue(json, HashMap.class);
        int id = Integer.parseInt((String) map.get("id"));
        String done = (String) map.get("done");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        JSONObject status = new JSONObject();
        if (database.setDone(id, done)) {
            status.put("success", true);
            status.put("id", id);
            status.put("done", done);
        } else {
            status.put("success", false);
        }
        writer.append(status.toString());
        writer.flush();
    }
}
