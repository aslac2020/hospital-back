package org.hospital.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.hospital.domain.entity.Patient;
import org.hospital.domain.entity.Room;
import org.hospital.dto.PatientRequest;
import org.hospital.dto.RoomRequest;
import org.hospital.service.PatientService;
import org.hospital.service.RoomService;
import org.hospital.usecase.PatientIncludeUseCase;
import org.hospital.usecase.RoomIncludeUseCase;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/salas")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class RoomController {

    private final RoomService service;

    private final RoomIncludeUseCase roomIncludeUseCase;

    @POST
    @Operation(summary = "Create Room")
    public Response createRoom(RoomRequest request) {
        if (request == null) {
            return Response.status(404).entity("Por favor passar dados correto :(").build();
        }
        log.info("RoomController --save");
        return Response.status(201).entity(roomIncludeUseCase.save(request)).build();
    }

    @GET
    @Operation(summary = "Get All Rooms")
    public Response getAllRooms() {
        return Response.status(200).entity(service.getAllRooms()).build();
    }

    @GET
    @Path("{id}")
    public Response getRoomById(@PathParam("id") Long id){
        Room patientId = service.getRoomById(id);
        return Response.status(200).entity(patientId).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteRoom(@PathParam("id")Long id){
        Room roomId = service.getRoomById(id);
        service.deleteRoom(id);
        return Response.ok(roomId).status(200).build();
    }

    @PUT
    @Path("{id}")
    public Response updatePatient(@PathParam("id") Long id, Room room){
        Room patientModel = service.updateRoom(id, room);
        return Response.ok(patientModel).status(200).build();
    }
}
