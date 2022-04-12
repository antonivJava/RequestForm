package org.example.repository.entries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String requestType;
    private String policyNumber;
    private String name;
    private String surname;
    private String note;

    public UserEntity(User user) {
        requestType = getRequestType();
        policyNumber = user.getPolicyNumber();
        name = user.getName();
        surname = user.getSurname();
        note = user.getNote();
    }
}


