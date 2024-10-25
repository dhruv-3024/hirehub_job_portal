package com.hirehub.entity;

import com.hirehub.dto.AccountType;
import com.hirehub.dto.UserDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

//@Getter
//@Setter
//@ToString
@Data
@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id //for id
    private Long id;
    private String name;
    @Indexed(unique = true) //no duplicate data
    private String email;
    private String password;
    private AccountType accountType;
    private Long profileId;
    public UserDTO toDTO(){
        return new UserDTO(this.id,this.name
        ,this.email,this.password,this.accountType,this.profileId);
    }

}
