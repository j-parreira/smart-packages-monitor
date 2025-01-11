package logistics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import logistics.entities.Sensor;
import logistics.entities.Volume;
import logistics.enums.SensorType;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;
import org.hibernate.Hibernate;

import java.util.List;

@Stateless
public class SensorBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private VolumeBean volumeBean;

    public boolean exists(Long id) {
        Query query = entityManager.createQuery("SELECT COUNT(s.id) FROM Sensor s WHERE s.id = :id", Long.class);
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public Sensor create(SensorType type, boolean isActive, float maxThreshold, float minThreshold, long timeInterval) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        try {
            Sensor sensor = new Sensor(type, isActive, maxThreshold, minThreshold, timeInterval);
            entityManager.persist(sensor);
            entityManager.flush();
            return sensor;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Sensor> findAll() {
        return entityManager.createNamedQuery("getAllSensors", Sensor.class).getResultList();
    }

    public Sensor find(long id) throws MyEntityNotFoundException {
        var sensor = entityManager.find(Sensor.class, id);
        if (sensor == null) {
            throw new MyEntityNotFoundException("Sensor not found");
        }
        return sensor;
    }

    public Sensor findWithReadings(long id) throws MyEntityNotFoundException {
        var sensor = this.find(id);
        Hibernate.initialize(sensor.getReadings());
        return sensor;
    }

    public Sensor update(Long id, Long volumeId, boolean isActive) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            Sensor sensor = find(id);
            Volume volume = volumeBean.find(volumeId);
            sensor.setVolume(volume);
            sensor.setActive(isActive);
            entityManager.merge(sensor);
            entityManager.flush();
            return sensor;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void delete(Long id) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            var sensor = find(id);
            if (!entityManager.contains(sensor)) {
                sensor = entityManager.merge(sensor);
            }
            entityManager.remove(sensor);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Sensor> findActive() {
        return entityManager.createNamedQuery("getActiveSensors", Sensor.class).getResultList();
    }
}
