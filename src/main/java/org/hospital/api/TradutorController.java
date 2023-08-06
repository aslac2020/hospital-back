package org.hospital.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.hospital.domain.entity.Consultant;
import org.hospital.service.TradutorService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;

@Path("/api/translate")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class TradutorController {

    private final TradutorService tradutorService;


    @POST
    @Operation(summary = "Traduzir texto")
    public Response callPatient(Consultant consults) throws ExecutionException, InterruptedException {
        var result = tradutorService.getCallPatient(consults);
        return Response.ok(result).build();

    }
}

