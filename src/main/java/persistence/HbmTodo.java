package persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import model.Item;

import org.hibernate.query.Query;

import java.util.List;

public class HbmTodo {
    private static final HbmTodo INSTANCE = new HbmTodo();

    /**
     * for release singleton
     */
    private HbmTodo() {
    }

    /**
     * return our singleton
     *
     * @return instance class HbmTodo
     */
    public static HbmTodo getInstance() {
        return INSTANCE;
    }

    /**
     * factory for session hibernate
     * https://ru.stackoverflow.com/questions/480188/sessionfactory-%D0%B2-hibernate-4-%D0%B4%D0%BB%D1%8F-%D1%87%D0%B0%D0%B9%D0%BD%D0%B8%D0%BA%D0%BE%D0%B2
     */
    private SessionFactory createSessionFactory() {
        return new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Item.class)
                .buildSessionFactory();
    }

    /**
     * add task to DB
     *
     * @param item - task
     * @return result true or false
     */
    public boolean save(Item item) {
        boolean result;
        try (Session session = createSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
            session.close();
            result = true;
        }
        return result;
    }

    /**
     * !!! зачем закрывать ???
     *
     * @return - list all tasks
     */
    public List<Item> findAllItem() {
        List<Item> result;
        try (Session session = createSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            result = session.createQuery("from model.Item").list();
            session.getTransaction().commit();
            session.close();
        }
        return result;
    }

    /**
     * лист задач в которых указанный в параметре статус
     *
     * @param isDone - статус
     * @return лист
     */
    public List<Item> findItemDone(String isDone) {
        try (Session session = createSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from model.Item where done = :param");
            query.setParameter("param", isDone);
            return (List<Item>) query.list();
        }
    }

    /**
     * set param done to item when id ==
     * @param id find this item
     * @param isDone - param for set
     * @return - true or false
     */
    public boolean setDone(int id, String isDone) {
        boolean result;
        try (Session session = createSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            Item item = session.get(Item.class, id);
            item.setDone(isDone);
            session.save(item);
            session.getTransaction().commit();
            result = true;
        }
        return result;
    }
}