package si.rso.skupina10.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.rso.skupina10.converters.UserConverter;
import si.rso.skupina10.dtos.UserDto;
import si.rso.skupina10.entities.UserEntity;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ApplicationScoped
public class AuthBean {

    @PersistenceContext(unitName = "auth-service-jpa")
    private EntityManager em;

    private static final Logger log = Logger.getLogger(AuthBean.class.getName());

    @PostConstruct
    private void init() {
        log.info("Initialization of the " + AuthBean.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Destroy " + AuthBean.class.getSimpleName());
    }

    public List<UserDto> getUsers() {
        try {
            Query q = em.createNamedQuery("User.getAll");
            List<UserEntity> resultList = (List<UserEntity>) q.getResultList();
            return resultList.stream().map(UserConverter::toDto).collect(Collectors.toList());
        } catch (Exception e) {
            log.severe("Error at getUsers: " + e);
            return null;
        }
    }

    public List<UserDto> getUsers(UriInfo uriInfo) {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();
        String u = uriInfo.getQueryParameters().getFirst("username");

        return JPAUtils.queryEntities(em, UserEntity.class, queryParameters).stream()
                .map(UserConverter::toDto)
                .filter(s -> {
                    if (u != null) {
                        return s.getUsername().equals(u);
                    } else {
                        return true;
                    }
                })
                .collect(Collectors.toList());
    }

    public UserDto getUser(Integer userId) {
        try {
            Query q = em.createNamedQuery("User.getUserById");
            q.setParameter("userId", userId);
            return UserConverter.toDto((UserEntity) q.getSingleResult());
        } catch (Exception e) {
            log.severe("Error at getUser by id: " + e);
            return null;
        }
    }

    @Transactional
    public UserDto addUser(UserDto u) {
        UserEntity entity = UserConverter.toEntity(u);

        try {
            beginTx();
            em.persist(entity);
            em.flush();
            commitTx();
            log.info("User " + u.getId() + " was added");
            if (entity.getUserId() == null){
                rollbackTx();
                throw new RuntimeException("Entity was not persisted");
            }
            return UserConverter.toDto(entity);
        } catch (Exception e) {
            rollbackTx();
            log.severe("Error at addUser " + e);
            return null;
        }
    }

    @Transactional
    public boolean removeUser(Integer orderId) {
        UserEntity order = em.find(UserEntity.class, orderId);

        if (order != null) {
            try{
                beginTx();
                em.remove(order);
                em.flush();
                commitTx();
            }catch ( Exception e){
                rollbackTx();
            }
            return true;
        }
        return false;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
