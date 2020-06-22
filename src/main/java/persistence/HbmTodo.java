package persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import model.Item;

import java.util.function.Function;


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
     * Здесь мы можем применить шаблон проектирования wrapper.
     *
     * тут добавили блок try-catch и в нем rollback
     *
     * @param command command to session
     * @param <T>     param command
     * @return result HQL or boolean
     */
    private <T> T tx(final Function<Session, T> command) {
        final Session session = createSessionFactory().openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * add task to DB
     *
     * @param item - task
     * @return result true or false
     */
    public boolean save(Item item) {
        return tx(
                session -> {
                    session.save(item);
                    return true;
                }
        );
    }

    /**
     * @return - list all tasks
     * with wrapper
     */
    public List<Item> findAllItem() {
        return tx(
                session -> session.createQuery("from " + Item.class.getName(), Item.class).list()
//                session -> session.createQuery("from model.Item").list()
        );
    }

    /**
     * лист задач в которых указанный в параметре статус
     *
     * @param isDone - статус
     * @return лист
     */
    public List<Item> findItemDone(String isDone) {
        return tx(
                session -> {
                    final Query query = session.createQuery("from model.Item where done = :param");
                    query.setParameter("param", isDone);
                    return query.list();
                }
        );
    }

    /**
     * set param done to item when id ==
     *
     * @param id     find this item
     * @param isDone - param for set
     * @return - true or false
     */
    public boolean setDone(int id, String isDone) {
        return tx(
                session -> {
                    Item item = session.get(Item.class, id);
                    item.setDone(isDone);
                    return true;
                }
        );
    }
}