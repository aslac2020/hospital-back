package org.hospital.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import lombok.extern.slf4j.Slf4j;
import org.hospital.domain.entity.Room;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
@Slf4j
public class RoomRepository implements PanacheRepositoryBase<Room, Long> {

    @Inject
    private EntityManager entityManager;

    public void save(Room entity){
    log.info("PatientRepository - save....");
    persist(entity);
    }
}
