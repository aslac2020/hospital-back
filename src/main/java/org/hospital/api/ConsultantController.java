package org.hospital.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.hospital.domain.entity.Consultant;
import org.hospital.dto.ConsultantRequest;
import org.hospital.dto.ConsultantStatusRequest;
import org.hospital.mapper.ConsultantMapper;
import org.hospital.service.ConsultantService;
import org.hospital.service.TradutorService;
import org.hospital.usecase.ConsultantIncludeUseCase;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

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
    @Transactional
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

    @GET
    @Path("/datas")
    public Response getOrderdates(){
        LocalDateTime consultantId = service.orderDates();
        return Response.status(200).entity(consultantId).build();
    }

    @GET
    @Path("/paciente")
    public Response getConsultByOrdemAvaliate(){
        Consultant consulta = service.orderConsultByTimeRoomAvaliate();
        return Response.status(200).entity(consulta).build();
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
       var result = service.updateStatus(id, request);
        return Response.ok(request).status(200).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateConsultant(@PathParam("id") Long id, ConsultantRequest request){
         var result = service.updateConsultant(id, request);
        return Response.ok(result).status(200).build();
    }

    @GET
    @Path("/consultorio/paciente")
    public Response getConsultByOrdemDoctor(){
        Consultant consulta = service.orderConsultByDoctorPatient();
        return Response.status(200).entity(consulta).build();
    }


}
