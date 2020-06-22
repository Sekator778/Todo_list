package persistence;

import model.Task;

import java.util.List;

/**
 * the interface for the service layer.
 */
public interface Service {

    List<Task> findAllItem();

    boolean setDone(int id, String isDone);

    boolean save(Task task);

    List<Task> findItemDone(String isDone);
}
