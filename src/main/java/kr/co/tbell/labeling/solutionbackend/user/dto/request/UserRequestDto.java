package kr.co.tbell.labeling.solutionbackend.user.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class UserRequestDto {

    @Getter
    @Setter
    public static class SignUp {

        @NotEmpty(message = "이메일은 필수 입력값입니다.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @NotEmpty(message = "이름은 필수 입력값입니다.")
        private String name;

        @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String password;

        @NotEmpty(message = "회사명은 필수 입력값입니다.")
        private String joinPath;

        @NotEmpty(message = "연락처는 필수 입력값입니다.")
        private String joinReason;

        @NotEmpty(message = "역할은 필수 입력값입니다.")
        private String role;

        @NotNull
        private boolean termYn;

        @NotNull
        private boolean privacyYn;

        @NotNull
        private boolean collectionYn;

    }

    @Getter
    @Setter
    public static class Update {

        @NotNull
        private Long id;

        @NotEmpty(message = "이메일은 필수 입력값입니다.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @NotEmpty(message = "이름은 필수 입력값입니다.")
        private String name;

        private String oldPassword;

        private String newPassword;

        private String state;

        private List<String> roles;
    }
}
