package com.udacity.part3.repository;

import com.udacity.part3.data.delivery.Delivery;
import com.udacity.part3.data.inventory.Plant;
import com.udacity.part3.projection.RecipientAndPrice;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class DeliveryRepository {
    @PersistenceContext
    EntityManager entityManager;

    public void persist(Delivery delivery) {
        entityManager.persist(delivery);
    }

    public Delivery find(Long id) {
        return entityManager.find(Delivery.class, id);
    }

    public Delivery merge(Delivery delivery) {
        return entityManager.merge(delivery);
    }

    public void delete(Long id) {
        Delivery delivery = entityManager.find(Delivery.class, id);
        entityManager.remove(delivery);
    }

    public List<Delivery> findDeliveriesByName(String name) {
        TypedQuery<Delivery> query = entityManager.createNamedQuery("Delivery.findByName", Delivery.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    // query a list of Plants with deliveryId matching the one provided and sum their prices.
    public RecipientAndPrice getBill(Long deliveryId) { // (***)
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<RecipientAndPrice> query = cb.createQuery(RecipientAndPrice.class);
        Root<Plant> root = query.from(Plant.class);
        query.select(
                        cb.construct(
                                RecipientAndPrice.class,
                                root.get("delivery").get("name"),
                                cb.sum(root.get("price"))
                        )
                )
                .where(cb.equal(root.get("delivery").get("id"), deliveryId));
        return entityManager.createQuery(query).getSingleResult();
    }
}


/*
(***) Resultant Hibernate-generated Query from CriteriaBuilder:
Hibernate:
    select
        delivery1_.name as col_0_0_,
        sum(plant0_.price) as col_1_0_
    from
        plant plant0_ cross
    join
        delivery delivery1_
    where
        plant0_.delivery_id=delivery1_.id
        and plant0_.delivery_id=3
 */
