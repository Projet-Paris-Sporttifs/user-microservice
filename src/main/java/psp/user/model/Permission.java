package psp.user.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Data
@Entity
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 70)
    private EPermission name;

    @ManyToMany(mappedBy = "permissions")
    private Collection<Role> roles;

    public Permission(EPermission name) {
        this.name = name;
    }

}
