package com.example.myawesomepastebin.dto;

import com.example.myawesomepastebin.model.Pasta;
import com.example.myawesomepastebin.model.enums.ExpirationTime;
import com.example.myawesomepastebin.model.enums.Status;
import lombok.Data;
@Data
public class CreatePastaDTO {
    private String title;
    private String pasta;
    private ExpirationTime expirationTime;
    private Status status;
    public Pasta toModel(){
        Pasta newPasta = new Pasta();
        newPasta.setTitle(this.getTitle());
        newPasta.setPasta(this.getPasta());
        newPasta.setStatus(this.getStatus());
        return newPasta;
    }
}
