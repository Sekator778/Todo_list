package test.main;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import test.Car;
import test.Engine;
import test.dao.CarDAO;
import test.dao.DAO;
import test.dao.EngineDAO;

public class App {

    public static void main(String[] args) {

        SessionFactory factory = null;

        try {

            factory = new Configuration().configure().buildSessionFactory();

            DAO<Engine, Integer> engineDao = new EngineDAO(factory);
            DAO<Car, Integer> carDao = new CarDAO<>(factory);

            /**
             * Раскоментируя интересующий метод помните что обращение к данным происходит по id.
             * Убедитесь что данные для методов create update и delete существуют.
             */

            readEngine(engineDao);

//            readCar(carDao);

        } finally {
            if (factory != null) {
                factory.close();
            }
        }
    }

    private static void readEngine(DAO<Engine, Integer> engineDao) {
        final Engine result = engineDao.read(1);
        System.out.println("Read: " + result);
    }

    private static void readCar(DAO<Car, Integer> carDao) {
        final Car result = carDao.read(1);
        System.out.println("Read: " + result);
    }

}