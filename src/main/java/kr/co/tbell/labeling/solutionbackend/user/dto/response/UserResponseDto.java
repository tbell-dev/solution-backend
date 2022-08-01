package kr.co.tbell.labeling.solutionbackend.user.dto.response;

import kr.co.tbell.labeling.solutionbackend.common.dto.Paging;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class UserResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class UserData {
        private Long id;
        private String email;
        private String name;
        private String joinPath;
        private String joinReason;
        private String state;
        private String role;
        private Boolean termYn;
        private Boolean privacyYn;
        private Boolean collectionYn;
        private LocalDateTime createDate;
        private LocalDateTime modifiedDate;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class UserListData {
        private Long id;
        private String email;
        private String name;
        private String state;
        private LocalDateTime createDate;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class User {
        private List<UserListData> userData;
        private Paging paging;
    }
}
