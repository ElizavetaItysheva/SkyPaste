package com.example.myawesomepastebin.dto;

import com.example.myawesomepastebin.model.Pasta;
import lombok.Data;
@Data
public class UrlDTO {
    private String url;
    public static  UrlDTO createUrl( Pasta pasta){
        UrlDTO url = new UrlDTO();
        url.setUrl("http://my-awesome-pastebin.tld/" + pasta.getHash());
        return url;
    }
}
