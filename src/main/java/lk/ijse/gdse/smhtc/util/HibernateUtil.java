//package lk.ijse.gdse.smhtc.util;
//
//import lombok.Getter;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//
//public class HibernateUtil {
//    @Getter
//    private static final SessionFactory sessionFactory = buildSessionFactory();
//
//    private static SessionFactory buildSessionFactory() {
//        try {
//            return new Configuration().configure().buildSessionFactory();
//        } catch (Throwable ex) {
//            throw new ExceptionInInitializerError("SessionFactory creation failed: " + ex);
//        }
//    }
//
////    public static SessionFactory getSessionFactory() {
////        return sessionFactory;
////    }
//}
