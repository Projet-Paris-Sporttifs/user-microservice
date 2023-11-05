package psp.user.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "roles")
public class Role {

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    Set<User> users;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 30)
    private ERole name;

    public Role() {

    }

    public Role(ERole name) {
        this.name = name;
    }

}
