package org.example.repository.entries;

import lombok.*;
import org.example.model.Request;

import javax.persistence.*;

@Entity
@Table(name = "REQUEST")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String requestType;
    private String policyNumber;
    private String name;
    private String surname;
    private String note;

    public RequestEntity(Request request) {
        requestType = request.getRequestType();
        policyNumber = request.getPolicyNumber();
        name = request.getName();
        surname = request.getSurname();
        note = request.getNote();
    }
}


