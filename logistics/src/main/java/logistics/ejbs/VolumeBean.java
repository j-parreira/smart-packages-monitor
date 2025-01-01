package logistics.ejbs;

import jakarta.ejb.EJB;
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
import java.util.LinkedList;
import java.util.List;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private ProductBean productBean;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private EmployeeBean employeeBean;
    @EJB
    private OrderBean orderBean;

    public boolean exists(Long id) {
        Query query = entityManager.createQuery("SELECT COUNT(v.id) FROM Volume v WHERE v.id = :id", Long.class);
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }

    public Volume create(VolumeType type, Long productId, List<Long> sensorIds, Long dispatchedByEmployeeId, VolumeStatus status, Long orderId) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        try {
            Product product = productBean.find(productId);
            List<Sensor> sensors = new LinkedList<>();
            for (Long sensorId : sensorIds) {
                var sensor = sensorBean.find(sensorId);
                sensors.add(sensor);
            }
            Employee dispatchedBy = employeeBean.find(dispatchedByEmployeeId);
            Order order = orderBean.find(orderId);
            Volume volume = new Volume(type, product, sensors, dispatchedBy, status, order);
            product.addVolume(volume);
            order.addVolume(volume);
            dispatchedBy.addVolume(volume);
            for (Long sensorId : sensorIds) {
                var sensor = sensorBean.find(sensorId);
                sensor.setVolume(volume);
            }
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

    public Volume findWithSensors(long id) throws MyEntityNotFoundException {
        var volume = this.find(id);
        Hibernate.initialize(volume.getSensors());
        return volume;
    }

    public Volume update(long id, VolumeStatus status) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            Volume volume = find(id);
            volume.setStatus(status);
            volume.setUpdatedAt(new Date());
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
