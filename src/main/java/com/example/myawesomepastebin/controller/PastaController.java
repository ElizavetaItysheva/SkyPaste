package com.example.myawesomepastebin.controller;

import com.example.myawesomepastebin.dto.CreatePastaDTO;
import com.example.myawesomepastebin.dto.PastaDTO;
import com.example.myawesomepastebin.dto.UrlDTO;
import com.example.myawesomepastebin.service.PastaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/my-awesome-pastebin.tld")
public class PastaController {
    private final PastaService pastaService;
    public PastaController( PastaService pastaService ) {
        this.pastaService = pastaService;
    }
@PostMapping
    public UrlDTO addPasta(@RequestBody CreatePastaDTO createPastaDTO){
        return pastaService.createPasta(createPastaDTO);
    }
    @GetMapping("/{hash}")
    public PastaDTO getPastaByHash(@PathVariable(name = "hash") String hash){
        return pastaService.getPastaById(hash);
    }
    @GetMapping("/last")
    public List<PastaDTO> getTenLastPastas(){
        return pastaService.getTenLastPastas();
    }
    @GetMapping()
    public List<PastaDTO> getPastaByTitleOrPasta(@RequestParam(name = "title", required = false) String title,
                                                 @RequestParam(name = "body", required = false) String body){
        return pastaService.getPastaByTitleOrPasta(title, body);
    }
}
