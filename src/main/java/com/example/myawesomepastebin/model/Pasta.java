package com.example.myawesomepastebin.model;

import com.example.myawesomepastebin.model.enums.Status;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.Instant;
@Entity
@Getter
@Setter
public class Pasta {
    private String title;
    private String pasta;
    @Id
    private String hash;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Instant publishedDate;
    private Instant expirationDate;

}
