package view;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.HbmTodo;

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

    /**
     * list to jsonArray
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        List<Task> taskList;
        String done = req.getParameter("done");
        if (done.equals("done")) {
            taskList = database.findItemDone("done");
        } else if (done.equals("undone")) {
            taskList = database.findItemDone("undone");
        } else {
            taskList = database.findAllItem();
        }
        JSONArray jsArray = new JSONArray(taskList);
        PrintWriter writer = resp.getWriter();
        writer.println(jsArray.toString());
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
