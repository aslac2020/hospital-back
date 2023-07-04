package org.hospital.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.hospital.domain.entity.Room;
import org.hospital.dto.RoomRequest;
import org.hospital.service.RoomService;
import org.hospital.service.TradutorService;
import org.hospital.usecase.RoomIncludeUseCase;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.http.HttpClient;
import java.util.concurrent.ExecutionException;

@Path("/api/translate")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class TradutorController {

    private final TradutorService tradutorService;

    @GET
    @Operation(summary = "Traduzir texto")
    public Response tradutText() throws ExecutionException, InterruptedException {
        var result = tradutorService.getTextTranslate();
        return Response.ok(result).build();

    }
}

