package com.dgtt.core.repository.impl;

import com.dgtt.core.entity.User;
import com.dgtt.core.entity.UserRole;
import com.dgtt.core.repository.UserRepositoryCustom;
import com.dgtt.core.utils.AppendData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.management.relation.Role;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;
import org.modelmapper.ModelMapper;

/**
 * 
 * @author DELL
 */
@Repository
public class UserRepositoryCustomImpl extends AppendData implements UserRepositoryCustom {

    @Autowired
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<User> searchAllUserByRoleCodeAndCreateBy(List<String> usernameList, Integer status,
            Date createdDateFrom, Date createdDateTo, String roleCode, Long createByUserId,
            Pageable pageable) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> root = criteriaQuery.from(User.class);
        Join<User, UserRole> userRole = root.join("userRoleList", JoinType.INNER);
        Join<UserRole, Role> role = userRole.join("role", JoinType.INNER);

        // Join<User, TopicUser> topicUser = root.join("topicUserList", JoinType.INNER);
        // Join<TopicUser, Topic> topic = topicUser.join("topic", JoinType.INNER);
        List<Predicate> criteriaList = new ArrayList<>();
        if (!usernameList.isEmpty()) {
            Predicate userNameCondition = root.get("username").in(usernameList);
            criteriaList.add(userNameCondition);
        }

        if (null != status) {

            Predicate statusCondition = criteriaBuilder.equal(root.get("status"), status);
            criteriaList.add(statusCondition);
        }

        if (null != createdDateFrom && null != createdDateTo) {
            Predicate createdDateCondition
                    = criteriaBuilder.between(root.get("createdDate"), createdDateFrom, createdDateTo);
            criteriaList.add(createdDateCondition);
        }

        if (null != roleCode) {
            Predicate roleCodeCondition = criteriaBuilder.equal(role.get("roleCode"), roleCode);
            criteriaList.add(roleCodeCondition);
        }

        if (createByUserId != 0) {
            Predicate createByUserIdCondition
                    = criteriaBuilder.equal(root.get("createByUserId"), createByUserId);
            criteriaList.add(createByUserIdCondition);
        }

        criteriaQuery.where(criteriaBuilder.and(criteriaList.toArray(new Predicate[]{})));

        // Order
        List<Order> orderList = new ArrayList<>();
        for (org.springframework.data.domain.Sort.Order order : pageable.getSort()) {
            if (order.getDirection().equals(Direction.ASC)) {
                orderList.add(em.getCriteriaBuilder().asc(root.get(order.getProperty())));
            } else {
                orderList.add(em.getCriteriaBuilder().desc(root.get(order.getProperty())));
            }
        }
        criteriaQuery.orderBy(orderList);
        criteriaQuery.select(root);

        Query<User> query = (Query<User>) em.createQuery(criteriaQuery);
        int totalRows = query.getResultList().size();
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        return new PageImpl<>(query.getResultList().stream().map(u -> modelMapper.map(u, User.class))
                .collect(Collectors.toList()), pageable, totalRows);
    }

    @Override
    public int editUserFree(User user) {
        em.createQuery("UPDATE User u set u.createByUserId=:createBy WHERE u.userId =:id ")
                .setParameter("createBy", user.getUserId())
                .setParameter("id", user.getUserId())
                .executeUpdate();
        return 0;
    }

    @Override
    public String getIsdn(String q) {
        String isdn = (String) em.createNativeQuery(q).getSingleResult();
        return isdn;
    }

}
