package com.example.demoah.manager;

import com.example.demoah.dao.AbstractDAO;
import com.example.demoah.dto.AbstractDTO;
import com.example.demoah.entity.AbstractEntity;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

import static com.example.demoah.utility.CustomMapper.map;
import static com.example.demoah.utility.CustomMapper.mapList;


@Service
public abstract class AbstractManager<T extends AbstractEntity<KEY>, TDTO extends AbstractDTO<KEY>, KEY extends Serializable> {

    @Autowired
    private AbstractDAO<T, KEY> abstractDAO;
    private final Class<T> entityClass;
    private final Class<TDTO> dtoClass;

    private final Logger logger;

    public AbstractManager() {
        this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.dtoClass = (Class<TDTO>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        this.logger = LoggerFactory.getLogger(AbstractManager.class);
    }

    public TDTO get(KEY id) {
        Optional<T> entity = abstractDAO.get(id);
        return entity.isPresent() ? (TDTO) map(entity.get(), dtoClass) : null;
    }

    public TDTO get(String uuid) {
        Optional<T> entity = abstractDAO.get(uuid);
        return entity.isPresent() ? (TDTO) map(entity.get(), dtoClass) : null;
    }

    public TDTO save(TDTO dto) {
        try {
            Field uuid = dto.getClass().getDeclaredField("uuid");
            uuid.setAccessible(true);
            if (uuid.get(dto) == null)
                uuid.set(dto, UUID.randomUUID().toString());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error(e.getMessage());
        }
        T entity = (T) map(dto, entityClass);
        KEY id = abstractDAO.save(entity);
        return (TDTO) map(abstractDAO.get(id).get(), dtoClass);
    }

    public TDTO update(TDTO dto) {
        T entity = (T) map(dto, entityClass);
        abstractDAO.update(entity);
        return (TDTO) map(abstractDAO.get(entity.getId()).get(), dtoClass);
    }

    public List<TDTO> list() {
        return (List<TDTO>) mapList(abstractDAO.list().toArray(), dtoClass);
    }

    public List<TDTO> list(Integer pageNumber, Integer pageSize, String sortBy, Boolean asc) {
        Integer firstIndex = (pageNumber - 1) * pageSize;
        List<Order> order = new ArrayList<>();
        if (asc)
            order.add(Order.asc(sortBy));
        else
            order.add(Order.desc(sortBy));

        return (List<TDTO>) mapList(abstractDAO.listByCriteria(firstIndex, pageSize, order).toArray(), dtoClass);
    }

    public List<TDTO> listByCriteria(Criterion... criteria) {
        return (List<TDTO>) mapList(abstractDAO.listByCriteria(criteria).toArray(), dtoClass);
    }

    public List<TDTO> listByCriteria(Map<String, String> aliases, List<Order> order, Long limit, Criterion... criteria) {
        return (List<TDTO>) mapList(abstractDAO.listByCriteria(aliases, order, limit, criteria).toArray(), dtoClass);
    }


    public void delete(Long id) {
        abstractDAO.delete(id);
    }

    public void delete(String uuid) {
        abstractDAO.delete(uuid);
    }


}


