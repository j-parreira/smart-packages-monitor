package logistics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import logistics.entities.*;
import logistics.enums.VolumeStatus;
import logistics.enums.VolumeType;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.List;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(Long id) {
        Query query = entityManager.createQuery("SELECT COUNT(v.id) FROM Volume v WHERE v.id = :id", Long.class);
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }

    public Volume create(VolumeType type, long volumeNumber, Product product, List<Sensor> sensors, Employee dispatchedBy, VolumeStatus status, Order order) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        Volume existingVolume = findByOrderAndVolumeNumber(order.getId(), volumeNumber);
        if (existingVolume != null) {
            throw new MyEntityExistsException("Volume already exists");
        }
        try {
            Volume volume = new Volume(type, volumeNumber, product, sensors, dispatchedBy, status, order);
            entityManager.persist(volume);
            entityManager.flush();
            return volume;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Volume> findAll() {
        return entityManager.createNamedQuery("getAllVolumes", Volume.class).getResultList();
    }

    public Volume find(long id) throws MyEntityNotFoundException {
        var volume = entityManager.find(Volume.class, id);
        if (volume == null) {
            throw new MyEntityNotFoundException("Volume not found");
        }
        return volume;
    }

    public Volume findByOrderAndVolumeNumber(long orderId, long volumeNumber) throws MyEntityNotFoundException {
        var query = entityManager.createNamedQuery("getVolumeByOrderAndVolumeNumber", Volume.class);
        query.setParameter("orderId", orderId);
        query.setParameter("volumeNumber", volumeNumber);
        List<Volume> volumes = query.getResultList();
        return volumes.isEmpty() ? null : volumes.get(0);
    }

    public List<Volume> findByOrder(long orderId) {
        var query = entityManager.createNamedQuery("getVolumeByOrder", Volume.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

    public Volume findWithProduct(long id) throws MyEntityNotFoundException {
        var volume = this.find(id);
        Hibernate.initialize(volume.getProduct());
        return volume;
    }

    public Volume findWithSensors(long id) throws MyEntityNotFoundException {
        var volume = this.find(id);
        Hibernate.initialize(volume.getSensors());
        return volume;
    }

    public Volume update(long id, VolumeStatus status, Date arrivedAt) throws MyEntityNotFoundException, MyConstraintViolationException {
        if (!exists(id)) {
            throw new MyEntityNotFoundException("Volume not found");
        }
        try {
            Volume volume = find(id);
            volume.setStatus(status);
            volume.setArrivedAt(arrivedAt);
            entityManager.merge(volume);
            entityManager.flush();
            return volume;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void delete(long id) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            var volume = find(id);
            if (!entityManager.contains(volume)) {
                volume = entityManager.merge(volume);
            }
            entityManager.remove(volume);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }
}
