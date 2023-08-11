package org.hospital.service;

import com.microsoft.cognitiveservices.speech.*;
import org.hospital.domain.entity.Consultant;
import org.hospital.mapper.PatientMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
@Transactional
public class TradutorService {

    @Inject
    ConsultantService service;

    @Inject
    PatientMapper mapper;


    public AtomicReference<String> getCallPatient(Consultant consult) throws ExecutionException, InterruptedException {
        return this.getTextTranslate(consult);
    }


    public AtomicReference<String> getTextTranslate(Consultant consult) throws ExecutionException, InterruptedException {

        var serviceTranslate = getConsultOpen(consult);

        var speechKey = "9d08e802a0c444639b5ce0090a02870e";
        var speechRegion = "eastus";

        SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion);
        speechConfig.setSpeechSynthesisVoiceName("pt-BR-BrendaNeural");

        SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig);

        SpeechSynthesisResult speechSynthesisResult = speechSynthesizer.SpeakTextAsync(serviceTranslate.toString()).get();

        if (speechSynthesisResult.getReason() == ResultReason.SynthesizingAudioCompleted) {
            System.out.println("Speech synthesized to speaker for text [" + serviceTranslate + "]");
        } else if (speechSynthesisResult.getReason() == ResultReason.Canceled) {
            SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails.fromResult(speechSynthesisResult);
            System.out.println("CANCELED: Reason=" + cancellation.getReason());

            if (cancellation.getReason() == CancellationReason.Error) {
                System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                System.out.println("CANCELED: Did you set the speech resource key and region values?");
            }
        }

        return serviceTranslate;

    }

    public AtomicReference<String> getConsultOpen(Consultant consult) {
        //var consultResult = service.getConsultantById(consult.getId());
        AtomicReference<String> tradutorText = new AtomicReference<>("");


        if (consult.getIsPatientToken() == true) {
            var audioTranslate = "Paciente: " + consult.getPatient().getName() + consult.getPatient().getLastName() + "comparecer a sala de Triagem ";
            tradutorText.set(audioTranslate);
        }
        if(consult.getIsPatientRoomClinic() == true){
            var audioTranslate = "Paciente: " + consult.getPatient().getName() + consult.getPatient().getLastName() + "comparecer ao consultório " + consult.getRoom().getNumberRoom();
            tradutorText.set(audioTranslate);
        }

        return tradutorText;

    }


}
