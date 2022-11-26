package com.example.demoah.dao;

import com.example.demoah.configuration.ApplicationContextProvider;
import com.example.demoah.entity.AbstractEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public abstract class AbstractDAO<T extends AbstractEntity<KEY>, KEY extends Serializable> {
    private final Class<T> entityClass;
    private final Session session;


    public AbstractDAO() {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        var entityManager = applicationContext.getBean(EntityManager.class);
        this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.session = entityManager.unwrap(Session.class);
    }

    public KEY save(T entity) {
        return (KEY) session.save(entity);
    }

    public void update(T entity) {
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
        session.close();
    }

    public List<T> list() {
        return session.createCriteria(entityClass).list();
    }

    public Optional<T> get(KEY id) {
        return session.createQuery("FROM " + entityClass.getSimpleName() + " WHERE id = :id")
                .setParameter("id", id)
                .list()
                .stream()
                .findFirst();
    }

    public Optional<T> get(String uuid) {
        return session.createQuery("FROM " + entityClass.getSimpleName() + " WHERE uuid = :uuid")
                .setParameter("uuid", uuid)
                .list()
                .stream()
                .findFirst();
    }

    public List<T> listByCriteria(Criterion... criteria) {
        Criteria criteria1 = session.createCriteria(entityClass);
        for (Criterion criterion : criteria) {
            criteria1.add(criterion);
        }
        return criteria1.list();
    }

    public List<T> listByCriteria(Integer firstIndex, Integer pageSize, List<Order> orders) {
        Criteria criteria1 = session.createCriteria(entityClass);
        for (Order order1 : orders) {
            criteria1.addOrder(order1);
        }
        if (firstIndex != null)
            criteria1.setFirstResult(firstIndex.intValue());
        if (pageSize != null)
            criteria1.setMaxResults(pageSize.intValue());

        return criteria1.list();
    }

    public List<T> listByCriteria(Map<String, String> aliases, List<Order> order, Long limit, Criterion... criteria) {
        Criteria criteria1 = session.createCriteria(entityClass);
        if (aliases != null)
            for (Map.Entry<String, String> entry : aliases.entrySet()) {
                criteria1.createAlias(entry.getKey(), entry.getValue());
            }
        if (limit != null)
            criteria1.setMaxResults(limit.intValue());

        if (order != null)
            for (Order order1 : order) {
                criteria1.addOrder(order1);
            }

        for (Criterion criterion : criteria) {
            criteria1.add(criterion);
        }
        return criteria1.list();
    }

    public void delete(Long id) {
        Transaction transaction = session.beginTransaction();
        session.createQuery("FROM " + entityClass.getSimpleName() + " WHERE id = :id")
                .setParameter("id", id)
                .list()
                .stream()
                .findFirst()
                .ifPresent(session::delete);
        transaction.commit();
        session.close();
    }

    public void delete(String uuid) {
        Transaction transaction = session.beginTransaction();
        session.createQuery("FROM " + entityClass.getSimpleName() + " WHERE uuid = :uuid")
                .setParameter("uuid", uuid)
                .list()
                .stream()
                .findFirst()
                .ifPresent(session::delete);
        transaction.commit();
        session.close();
    }

}
