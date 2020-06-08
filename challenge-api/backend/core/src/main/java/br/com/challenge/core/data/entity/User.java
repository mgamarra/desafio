package br.com.challenge.core.data.entity;

import br.com.challenge.core.data.enumeration.Group;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpf", name = "uk_cpf"),
})
@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
    @SequenceGenerator(allocationSize = 1, name = "seq_user", sequenceName = "seq_user")
    @Column(name = "id")
    private Long id;

    @Length(max = 100)
    @Column(name = "password", length = 100)
    private String password;

    @NotBlank
    @Length(min=3,max = 100)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Length(max = 11)
    @Column(name = "cpf", length = 11)
    private String cpf;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_group", length = 15)
    private Group userGroup;

    @Type(type = "jsonb")
    @Column(name = "emails", nullable = false, columnDefinition = "jsonb")
    private Map<String, String> emails = new HashMap<>();

    @Type(type = "jsonb")
    @Column(name = "phones", nullable = false, columnDefinition = "jsonb")
    private List<Map<String,String>> phones = new ArrayList<Map<String,String>>();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Address address;

    public User() {
        super();
    }


}
