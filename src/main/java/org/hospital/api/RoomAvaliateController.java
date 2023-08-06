package org.hospital.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.hospital.domain.entity.Room;
import org.hospital.domain.entity.RoomAvaliate;
import org.hospital.dto.RoomAvaliateRequest;
import org.hospital.dto.RoomRequest;
import org.hospital.service.RoomAvaliateService;
import org.hospital.service.RoomService;
import org.hospital.usecase.RoomAvaliateIncludeUseCase;
import org.hospital.usecase.RoomIncludeUseCase;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/pacientes/avaliacao")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class RoomAvaliateController {

    private final RoomAvaliateService service;

    private final RoomAvaliateIncludeUseCase roomIncludeUseCase;

    @POST
    @Operation(summary = "Create Room")
    public Response createRoom(RoomAvaliateRequest request) {
        if (request == null) {
            return Response.status(404).entity("Por favor passar dados correto :(").build();
        }
        log.info("RoomController --save");
        return Response.status(201).entity(roomIncludeUseCase.save(request)).build();
    }

    @GET
    @Operation(summary = "Get All Rooms")
    public Response getAllAvaliates() {
        return Response.status(200).entity(service.getAllAvaliates()).build();
    }

    @GET
    @Path("{id}")
    public Response getAvaliateById(@PathParam("id") Long id){
        RoomAvaliate avaliatetId = service.getAvaliateById(id);
        return Response.status(200).entity(avaliatetId).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteAvaliate(@PathParam("id")Long id){
        RoomAvaliate roomId = service.getAvaliateById(id);
        service.deleteAvaliate(id);
        return Response.ok(roomId).status(200).build();
    }

    @PUT
    @Path("{id}")
    public Response updatePatient(@PathParam("id") Long id, RoomAvaliate room){
        RoomAvaliate avaliateModel = service.updateAvaliate(id, room);
        return Response.ok(avaliateModel).status(200).build();
    }
}
