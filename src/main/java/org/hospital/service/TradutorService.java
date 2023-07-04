package org.hospital.service;

import com.microsoft.cognitiveservices.speech.*;

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

    public AtomicReference<String> getTextTranslate() throws ExecutionException, InterruptedException {

        var serviceTranslate = getConsultOpen(service);

        var speechKey = "9d08e802a0c444639b5ce0090a02870e";
        var speechRegion = "eastus";

        SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion);
        speechConfig.setSpeechSynthesisVoiceName("pt-BR-BrendaNeural");

        SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig);

        SpeechSynthesisResult speechSynthesisResult = speechSynthesizer.SpeakTextAsync(serviceTranslate.toString()).get();

        if (speechSynthesisResult.getReason() == ResultReason.SynthesizingAudioCompleted) {
            System.out.println("Speech synthesized to speaker for text [" + serviceTranslate + "]");
        }
        else if (speechSynthesisResult.getReason() == ResultReason.Canceled) {
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
    public AtomicReference<String> getConsultOpen(ConsultantService service){
        var listConsults = service.getAllConsultants();
        AtomicReference<String> tradutorText = new AtomicReference<>("");
        listConsults.forEach(data -> {
            if(data.getIsPatientToken() == true && data.getPatient().getIsPreferential() == true){
                var textDigited = "Paciente: " + data.getPatient().getName() + data.getPatient().getLastName() + "comparecer a sala de Triagem na sala:" + data.getRoom().getNumberRoom();
               tradutorText.set(textDigited);
            }
            if(data.getIsPatientToken() == true){
                var textDigited = "Paciente: " + data.getPatient().getName() + data.getPatient().getLastName() + " comparecer a sala de Triagem na sala:" + data.getRoom().getNumberRoom();
                tradutorText.set(textDigited);
            }
        });

        return tradutorText;

    }



}
