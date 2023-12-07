package com.shop.adapter.out.persistence;

import com.shop.adapter.out.constant.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="member")
@Getter @Setter
@ToString
@NoArgsConstructor
public class MemberEntity extends BaseEntity {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public MemberEntity(Long id, String name, String email, String password, String address, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
    }

    public void update(MemberEntity entity) {
        this.name = entity.getName();
        this.address = entity.getAddress();
        this.role = entity.getRole();
    }

}
