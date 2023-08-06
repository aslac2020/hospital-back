package org.hospital.usecase;


import io.smallrye.mutiny.Uni;
import org.hospital.dto.ConsultantRequest;
import org.hospital.dto.ConsultantResponse;

import javax.validation.Valid;
import java.util.List;

public interface ConsultantListUseCase {

    Uni<List<ConsultantResponse>> getList(@Valid ConsultantRequest request);
}
