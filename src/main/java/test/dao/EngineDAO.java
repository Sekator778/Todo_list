package test.dao;

import com.sun.istack.NotNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import test.Engine;

public class EngineDAO implements DAO<Engine, Integer> {
    /**
     * Connection factory to database.
     */
    private final SessionFactory factory;

    public EngineDAO(@NotNull final SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Engine read(@NotNull final Integer id) {
        try (final Session session = factory.openSession()) {
            return session.get(Engine.class, id);
        }
    }
}