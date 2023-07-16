package org.hospital.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import lombok.extern.slf4j.Slf4j;
import org.hospital.domain.entity.Doctor;
import org.hospital.domain.entity.Patient;
import org.hospital.dto.DoctorResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
@Slf4j
public class DoctorRepository implements PanacheRepositoryBase<Doctor, Long> {

    @Inject
    private EntityManager entityManager;

    public void save(Doctor entity){
    log.info("PatientRepository - save....");
    persist(entity);
    }

    public List<Doctor> findyBySpecialities(String specialties){
        return  entityManager.createQuery("FROM Doctor WHERE specialties = :specialties", Doctor.class).setParameter("specialties", specialties).getResultList();
    }

//    public List<Doctor> findyByCrm(String crm){
//        return  entityManager.createQuery("FROM Doctor WHERE crm = :crm", Doctor.class).setParameter("crm", crm).getResultList();
//    }

    public List<Doctor> findyByName(String name){
        return  entityManager.createQuery("FROM Doctor WHERE name = :name", Doctor.class).setParameter("name", name).getResultList();
    }


}
