package org.hospital.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.hospital.domain.entity.PatientHistory;
import org.hospital.dto.PatientHistoryRequest;
import org.hospital.service.PatientHistoryService;
import org.hospital.usecase.PatientHistoryIncludeUseCase;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/pacientes/historico")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class PatientHistoryController {

    private final PatientHistoryService service;

    private final PatientHistoryIncludeUseCase patientHistoryIncludeUseCase;

    @POST
    @Transactional
    @Operation(summary = "Create Patient")
    public Response createPatient(PatientHistoryRequest request) {
        if (request == null) {
            return Response.status(404).entity("Por favor passar dados correto :(").build();
        }
        log.info("PatientController --save");
        return Response.status(201).entity(patientHistoryIncludeUseCase.save(request)).build();
    }

    @GET
    public Response getAllHistorityPatients() {
        return Response.status(200).entity(service.getAllHistoryPatients()).build();
    }

    @GET
    @Path("{id}")
    public Response getPatientById(@PathParam("id") Long id) {
        PatientHistory patientHistorityId = service.getHistoryPatientById(id);
        return Response.status(200).entity(patientHistorityId).build();
    }

    @DELETE
    @Path("{id}")
    public Response deletePatient(@PathParam("id") Long id) {
        PatientHistory patientId = service.getHistoryPatientById(id);
        service.deleteHistoryPatient(id);
        return Response.ok(patientId).status(200).build();
    }

    @PUT
    @Path("{id}")
    public Response updatePatient(@PathParam("id") Long id, PatientHistory history) {
        PatientHistory patientModel = service.updateHistoryPatient(id, history);
        return Response.ok(patientModel).status(200).build();
    }
}
