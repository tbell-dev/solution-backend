package kr.co.tbell.labeling.solutionbackend.user.entity;

import kr.co.tbell.labeling.solutionbackend.common.entity.BaseTime;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "User")
public class Users extends BaseTime implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_idx")
    private Long id;

    // 회원 이메일
    @Column(name = "user_email")
    private String email;

    // 회원 비밀번호
    @Column(name = "user_password")
    private String password;

    // 회원 이름
    @Column(name = "user_name")
    private String name;

    // 가입 경로
    @Column(name = "user_join_path")
    private String joinPath;

    // 가입 사유
    @Column(name = "user_join_reason")
    private String joinReason;

    // 이용약관 동의 여부
    @Column(name = "user_term_yn")
    private Boolean termYn;

    // 개인정보처리방침 동의 여부
    @Column(name = "user_privacy_yn")
    private Boolean privacyYn;

    // 개인정보 수집 및 이용 동의 여부
    @Column(name = "user_collection_yn")
    private Boolean collectionYn;

    @Column(name = "user_roles")
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Column(name = "user_state")
    private String state;

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
