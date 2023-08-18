package fr.diginamic.gestit_back.utils;

import fr.diginamic.gestit_back.dto.MessageDto;
import lombok.Getter;

@Getter
public class NotFoundOrValidException extends RuntimeException{
    private MessageDto messageDto;

    public NotFoundOrValidException(MessageDto messageDto){
        this.messageDto = messageDto;
    }

}
