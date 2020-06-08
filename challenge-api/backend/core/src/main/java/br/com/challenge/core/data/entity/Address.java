package br.com.challenge.core.data.entity;

import br.com.challenge.core.data.enumeration.State;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "address")
@Data
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_address")
    @SequenceGenerator(allocationSize = 1, name = "seq_address", sequenceName = "seq_address")
    @Column(name = "id")
    private Long id;

    @Length(max = 200)
    @Column(name = "description", length = 200)
    private String description;

    @Length(max = 100)
    @Column(name = "complement", length = 100)
    private String complement;

    @Column(name = "neighborhood", length = 72)
    private String neighborhood;

    @Column(name = "number", length = 9)
    private String number;

    @Length(max = 100)
    @Column(name = "city", length = 100)
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", length = 2)
    private State state;

    @Length(max = 100)
    @Column(name = "zip_code", length = 100)
    private String zipCode;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @JoinColumn(name = "user_id",updatable = false, insertable = false)
    @JsonBackReference
    private User user;
}
