package persistence;


import model.Item;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

public class HbmTodoTest {
    @Test
    public void testSaveItem() {
        HbmTodo todo = HbmTodo.getInstance();
        todo.save(new Item("when i go fishing ?", LocalDate.now(), "undone"));
    }
    @Test
    public void testGetItemIsUndone() {
        HbmTodo todo = HbmTodo.getInstance();
        Item item = todo.findItemDone("undone").get(0);
        System.out.println(item);
    }

    @Test
    public void testFindAllItem() {
        HbmTodo todo = HbmTodo.getInstance();
        List<Item> itemList = todo.findAllItem();
        itemList.forEach(System.out::println);
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
