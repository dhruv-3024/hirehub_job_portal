package com.hirehub.entity;

import com.hirehub.dto.Certification;
import com.hirehub.dto.Experience;
import com.hirehub.dto.ProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Base64;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "profiles")
public class Profile {
    @Id
    private Long id;
    private String email;
    private String jobTitle;
    private String company;
    private String location;
    private String about;
    private byte[] picture; //byte[] to store image
    private List<String> skills;
    private List<Experience> experiences;
    private List<Certification> certifications;
    private List<Long> savedJobs;
    public ProfileDTO toDTO() {
        return new ProfileDTO(this.id, this.email, this.jobTitle,
                this.company, this.location, this.about, this.picture != null ? Base64.getEncoder().encodeToString(this.picture) : null, this.skills, this.experiences,
                this.certifications,this.savedJobs);
    }
}
