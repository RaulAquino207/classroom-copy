package br.com.raul.classroomcopy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_user_to_classroom")
@NoArgsConstructor
@AllArgsConstructor
public class UserToClassroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    @Column(name = "ds_role")
    private String role;

    @Getter
    @Setter
    @ManyToOne
    private User user;

    @Getter
    @Setter
    @ManyToOne
    private Classroom classroom;

}
