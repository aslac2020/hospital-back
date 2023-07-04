package org.hospital.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.hospital.domain.entity.Consultant;
import org.hospital.domain.entity.Room;
import org.hospital.dto.ConsultantRequest;
import org.hospital.dto.ConsultantStatusRequest;
import org.hospital.dto.RoomRequest;
import org.hospital.mapper.ConsultantMapper;
import org.hospital.service.ConsultantService;
import org.hospital.service.RoomService;
import org.hospital.service.TradutorService;
import org.hospital.usecase.ConsultantIncludeUseCase;
import org.hospital.usecase.RoomIncludeUseCase;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;

@Path("/api/consultas")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class ConsultantController {

    private final ConsultantService service;
    private final TradutorService tradutorService;
    private final ConsultantIncludeUseCase consultantIncludeUseCase;

    private final ConsultantMapper consultantMapper;

    @POST
    @Operation(summary = "Create Consultant")
    public Response createConsultant(ConsultantRequest request) {
        if (request == null) {
            return Response.status(404).entity("Por favor passar dados correto :(").build();
        }
        log.info("RoomController --save");
        return Response.status(201).entity(consultantIncludeUseCase.save(request)).build();
    }

    @GET
    @Operation(summary = "Get All Consultants")
    public Response getAllConsultants() {
        return Response.status(200).entity(service.getAllConsultants()).build();
    }

    @GET
    @Path("{id}")
    public Response getConsultantById(@PathParam("id") Long id){
        Consultant consultantId = service.getConsultantById(id);
        return Response.status(200).entity(consultantId).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteConsultant(@PathParam("id")Long id){
        Consultant consultantId = service.getConsultantById(id);
        service.deleteConsultant(id);
        return Response.ok(consultantId).status(200).build();
    }

    @PUT
    @Path("/status/{id}")
    public Response updateStatus(@PathParam("id") Long id, ConsultantStatusRequest request){
        service.updateStatus(id, request);
        return Response.ok(request).status(200).build();
    }
    @PUT
    @Path("{id}")
    public Response updateConsultant(@PathParam("id") Long id, Consultant consultant){
        Consultant consultantModel = service.updateConsultant(id, consultant);
        return Response.ok(consultantModel).status(200).build();
    }
}
