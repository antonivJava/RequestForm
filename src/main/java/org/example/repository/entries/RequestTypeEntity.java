package org.example.repository.entries;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "REQUEST_TYPE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String requestType;

    public RequestTypeEntity(String requestType) {
        this.requestType = requestType;
    }
}


