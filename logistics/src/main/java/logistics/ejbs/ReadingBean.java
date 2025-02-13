package logistics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import logistics.entities.Reading;
import logistics.entities.Sensor;
import logistics.enums.SensorType;
import logistics.enums.VolumeStatus;
import logistics.exceptions.MyConstraintViolationException;
import logistics.exceptions.MyEntityExistsException;
import logistics.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ReadingBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private EmailBean EmailBean;

    public boolean exists(Long id) {
        Query query = entityManager.createQuery("SELECT COUNT(r.id) FROM Reading r WHERE r.id = :id", Long.class);
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public Reading create(Long sensorId, double valueOne, double valueTwo) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        try {
            var sensor = sensorBean.find(sensorId);
            Reading reading = new Reading(sensor, valueOne, valueTwo);
            sensor.addReading(reading);
            if (sensor.getType()!= SensorType.GPS && (valueOne > sensor.getMaxThreshold() || valueOne < sensor.getMinThreshold())) {
                sensor.getVolume().setStatus(VolumeStatus.DAMAGED);
                sensor.getVolume().getSensors().forEach(s -> s.setActive(false));
                EmailBean.send(sensor.getVolume().getOrder().getCustomer().getEmail(), "Volume: " + sensor.getVolume().getVolumeCode() + " - Status: " + sensor.getVolume().getStatus().toString(), "Hello " + sensor.getVolume().getOrder().getCustomer().getName() + "\nYour volume " + sensor.getVolume().getVolumeCode() + " from your order " + sensor.getVolume().getOrder().getId().toString() + " with " + sensor.getVolume().getProduct().getName() + " changed to " + sensor.getVolume().getStatus().toString() + "\nThank you for your purchase.");
                EmailBean.send(sensor.getVolume().getDispatchedBy().getEmail(), "Volume: " + sensor.getVolume().getVolumeCode() + " - Status: " + sensor.getVolume().getStatus().toString(), "Hello " + sensor.getVolume().getDispatchedBy() + "\nThe volume " + sensor.getVolume().getVolumeCode() + " from order " + sensor.getVolume().getOrder().getId().toString() + " with " + sensor.getVolume().getProduct().getName() + " changed to " + sensor.getVolume().getStatus().toString() + "\nPlease contact the transportation company.");
            }
            entityManager.persist(reading);
            entityManager.flush();
            return reading;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public List<Reading> findAll() {
        return entityManager.createNamedQuery("getAllReadings", Reading.class).getResultList();
    }

    public Reading find(long id) throws MyEntityNotFoundException {
        var reading = entityManager.find(Reading.class, id);
        if (reading == null) {
            throw new MyEntityNotFoundException("Reading not found");
        }
        return reading;
    }

    public List<Reading> findBySensor(long sensorId) {
        var query = entityManager.createNamedQuery("getReadingsBySensor", Reading.class);
        query.setParameter("sensor", entityManager.find(Sensor.class, sensorId));
        return query.getResultList();
    }

    public Reading update(long id, double valueOne, double valueTwo) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            var reading = find(id);
            reading.setValueOne(valueOne);
            reading.setValueTwo(valueTwo);
            entityManager.merge(reading);
            entityManager.flush();
            return reading;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void delete(long id) throws MyEntityNotFoundException, MyConstraintViolationException {
        try {
            var reading = find(id);
            if (!entityManager.contains(reading)) {
                reading = entityManager.merge(reading); // Ensure it's managed
            }
            entityManager.remove(reading);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }
}
