package org.hospital.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.hospital.domain.entity.Doctor;
import org.hospital.domain.entity.Patient;
import org.hospital.dto.DoctorRequest;
import org.hospital.dto.DoctorResponse;
import org.hospital.dto.PatientRequest;
import org.hospital.service.DoctorService;
import org.hospital.service.PatientService;
import org.hospital.usecase.DoctorIncludeUseCase;
import org.hospital.usecase.PatientIncludeUseCase;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/medicos")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class DoctorController {

    private final DoctorService service;

    private final DoctorIncludeUseCase doctorIncludeUseCase;

    @POST
    @Operation(summary = "Create Patient")
    public Response createPatient(DoctorRequest request) {
        if (request == null) {
            return Response.status(404).entity("Por favor passar dados correto :(").build();
        }
        log.info("PatientController --save");
        return Response.status(201).entity(doctorIncludeUseCase.save(request)).build();
    }

    @GET
    public Response getAllPatients() {
        return Response.status(200).entity(service.getAllDoctors()).build();
    }

    @GET
    @Path("{id}")
    public Response getPatientById(@PathParam("id") Long id){
        Doctor doctorId = service.getDoctorById(id);
        return Response.status(200).entity(doctorId).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteDoctor(@PathParam("id")Long id){
        Doctor doctorId = service.getDoctorById(id);
        service.deleteDoctor(id);
        return Response.ok(doctorId).status(200).build();
    }

    @PUT
    @Path("{id}")
    public Response updateDoctor(@PathParam("id") Long id, Doctor doctor){
        Doctor doctortModel = service.updateDoctor(id, doctor);
        return Response.ok(doctortModel).status(200).build();
    }

    @GET
    @Path("{specialties}")
    public Response getDoctorBySpecialties(@PathParam("specialties") String specialties){
        List<Doctor> doctorSpecialties =  service.getDoctorBySpecialities(specialties);
        if(doctorSpecialties.size() < 1){
            return Response.status(400).entity("Especialiada não encontrada :(").build();
        }
        return Response.status(200).entity(doctorSpecialties).build();
    }

//    @GET
//    @Path("crm/{crm}")
//    public Response getDoctorByCrm(@PathParam("crm") String crm){
//        List<DoctorResponse> doctorCrm =  service.getDoctorByCrm(crm);
//        return Response.status(200).entity(doctorCrm).build();
//    }


    @GET
    @Path("name/{name}")
    public Response getDoctorByName(@PathParam("name") String name){
        List<DoctorResponse> doctorCrm =  service.getDoctorByName(name);
        return Response.status(200).entity(doctorCrm).build();
    }


}
