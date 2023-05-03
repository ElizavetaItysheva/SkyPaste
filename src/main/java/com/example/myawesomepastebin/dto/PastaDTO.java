package com.example.myawesomepastebin.dto;

import com.example.myawesomepastebin.model.Pasta;
import lombok.Data;
@Data
public class PastaDTO {
    private String title;
    private String pasta;

    public static PastaDTO fromModel( Pasta pasta ){
        PastaDTO dto = new PastaDTO();
        dto.setTitle(pasta.getTitle());
        dto.setPasta(pasta.getPasta());
        return dto;
    }

}
