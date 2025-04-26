package lk.ijse.gdse.smhtc.dao.custom.impl;

import lk.ijse.gdse.smhtc.config.FactoryConfiguration;
import lk.ijse.gdse.smhtc.dao.custom.TherapyProgrammeDAO;
import lk.ijse.gdse.smhtc.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class TherapyProgrammeDAOImpl implements TherapyProgrammeDAO {
    @Override
    public String getNextId() {
        Optional<String> lastPk = getLastPK();
        if (lastPk.isPresent()) {
            String id = lastPk.get();
            int num = Integer.parseInt(id.replace("TP-", "")) + 1;
            return String.format("TP-%03d", num);
        } else {
            return "TP-001";
        }
    }

    @Override
    public boolean save(TherapyProgram entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(TherapyProgram entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String pk) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            TherapyProgram tp = session.get(TherapyProgram.class, pk);
            if (tp != null) {
                session.remove(tp);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<TherapyProgram> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<TherapyProgram> list = session.createQuery("FROM TherapyProgram", TherapyProgram.class).list();
        session.close();
        return list;
    }

    @Override
    public Optional<TherapyProgram> findById(String pk) {
        Session session = FactoryConfiguration.getInstance().getSession();
        TherapyProgram tp = session.get(TherapyProgram.class, pk);
        session.close();
        return Optional.ofNullable(tp);
    }

    @Override
    public Optional<TherapyProgram> findByName(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPK() {
        Session session = FactoryConfiguration.getInstance().getSession();
        String lastPk = session.createQuery("SELECT id FROM TherapyProgram ORDER BY id DESC", String.class)
                .setMaxResults(1)
                .uniqueResult();
        session.close();
        return Optional.ofNullable(lastPk);
    }

    @Override
    public List<TherapyProgram> findByNameList(String name) {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<TherapyProgram> list = session.createQuery("FROM TherapyProgram WHERE name LIKE :name", TherapyProgram.class)
                .setParameter("name", "%" + name + "%")
                .list();
        session.close();
        return list;
    }
}
