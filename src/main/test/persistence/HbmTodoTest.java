package persistence;


import model.Task;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

public class HbmTodoTest {
    @Test
    public void testSaveItem() {
        HbmTodo todo = HbmTodo.getInstance();
        todo.save(new Task("when i go fishing ?", LocalDate.now(), "undone"));
    }
    @Test
    public void testGetItemIsUndone() {
        HbmTodo todo = HbmTodo.getInstance();
        Task task = todo.findItemDone("undone").get(0);
        System.out.println(task);
    }

    @Test
    public void testFindAllItem() {
        HbmTodo todo = HbmTodo.getInstance();
        List<Task> taskList = todo.findAllItem();
        taskList.forEach(System.out::println);
    }
    @Test
    public void testChangeStatusTask() {
        HbmTodo todo = HbmTodo.getInstance();
        todo.setDone(2, "undone");
    }
    @Test
    public void testChangeDoneItem() {
        HbmTodo todo = HbmTodo.getInstance();
        todo.setDone(1, "done");
    }
}
