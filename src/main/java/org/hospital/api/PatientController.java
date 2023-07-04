package org.hospital.api;

import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.hospital.domain.entity.Patient;
import org.hospital.dto.PatientRequest;
import org.hospital.dto.PatientResponse;
import org.hospital.service.PatientService;
import org.hospital.usecase.PatientIncludeUseCase;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/pacientes")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class PatientController {

    private final PatientService service;

    private final PatientIncludeUseCase patientIncludeUseCase;

    @POST
    @Operation(summary = "Create Patient")
    public Response createPatient(PatientRequest request) {
        if (request == null) {
            return Response.status(404).entity("Por favor passar dados correto :(").build();
        }
        log.info("PatientController --save");
        return Response.status(201).entity(patientIncludeUseCase.save(request)).build();
    }

    @GET
    public Response getAllPatients() {
        return Response.status(200).entity(service.getAllPatients()).build();
    }

    @GET
    @Path("{id}")
    public Response getPatientById(@PathParam("id") Long id){
        Patient patientId = service.getPatientById(id);
        return Response.status(200).entity(patientId).build();
    }

    @DELETE
    @Path("{id}")
    public Response deletePatient(@PathParam("id")Long id){
        Patient patientId = service.getPatientById(id);
        service.deletePatient(id);
        return Response.ok(patientId).status(200).build();
    }

    @PUT
    @Path("{id}")
    public Response updatePatient(@PathParam("id") Long id, Patient patient){
        Patient patientModel = service.updatePatient(id, patient);
        return Response.ok(patientModel).status(200).build();
    }
}
