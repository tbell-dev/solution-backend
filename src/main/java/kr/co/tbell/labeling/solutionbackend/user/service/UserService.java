package kr.co.tbell.labeling.solutionbackend.user.service;

import kr.co.tbell.labeling.solutionbackend.common.dto.Paging;
import kr.co.tbell.labeling.solutionbackend.common.dto.Response;
import kr.co.tbell.labeling.solutionbackend.common.enums.Authority;
import kr.co.tbell.labeling.solutionbackend.user.dto.request.UserRequestDto;
import kr.co.tbell.labeling.solutionbackend.user.dto.response.UserResponseDto;
import kr.co.tbell.labeling.solutionbackend.user.entity.Users;
import kr.co.tbell.labeling.solutionbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final Response response;
    private final PasswordEncoder passwordEncoder;


    public ResponseEntity<?> signUp(UserRequestDto.SignUp signUp) {

        if (userRepository.existsByEmail(signUp.getEmail())) {
            return response.fail("이미 회원가입된 이메일입니다.", HttpStatus.BAD_REQUEST);
        }

        if(signUp.getRole().equals("ADMIN")) {
            Users user = Users.builder()
                    .email(signUp.getEmail())
                    .password(passwordEncoder.encode(signUp.getPassword()))
                    .name(signUp.getName())
                    .joinPath(signUp.getJoinPath())
                    .joinReason(signUp.getJoinReason())
                    .termYn(signUp.isTermYn())
                    .privacyYn(signUp.isPrivacyYn())
                    .collectionYn(signUp.isCollectionYn())
                    .roles(Collections.singletonList(Authority.ROLE_ADMIN.name()))
                    .state("특별회원")
                    .build();
            userRepository.save(user);
        } else {
            Users user = Users.builder()
                    .email(signUp.getEmail())
                    .password(passwordEncoder.encode(signUp.getPassword()))
                    .name(signUp.getName())
                    .joinPath(signUp.getJoinPath())
                    .joinReason(signUp.getJoinReason())
                    .termYn(signUp.isTermYn())
                    .privacyYn(signUp.isPrivacyYn())
                    .collectionYn(signUp.isCollectionYn())
                    .roles(Collections.singletonList(Authority.ROLE_USER.name()))
                    .state("기본회원")
                    .build();
            userRepository.save(user);
        }


        return response.success("회원가입에 성공했습니다.");
    }

    public ResponseEntity<?> getUserList(String name, String email, String state, int page) {

        Page<Users> users = userRepository.findAllSearch(name, email, state, PageRequest.of(page, 10, Sort.by("create_date").descending()));
        return getResponseEntity(users);
    }

    private ResponseEntity<?> getResponseEntity(Page<Users> users) {
        List<UserResponseDto.UserListData> userList = new ArrayList<>();

        for(Users user : users) {
            UserResponseDto.UserListData dto = UserResponseDto.UserListData.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .state(user.getState())
                    .createDate(user.getCreateDate())
                    .build();

            userList.add(dto);
        }

        Paging page = Paging.builder()
                .pageNumber(users.getNumber())
                .pageSize(users.getSize())
                .totalPages(users.getTotalPages())
                .numberOfElements(users.getNumberOfElements())
                .prev(users.hasPrevious())
                .next(users.hasNext())
                .last(users.isLast())
                .first(users.isFirst())
                .empty(users.isEmpty())
                .build();

        UserResponseDto.User data = UserResponseDto.User.builder().userData(userList).paging(page).build();

        return response.success(data);
    }

    public ResponseEntity<?> getUser(Long id) {
        Optional<Users> item = userRepository.findById(id);

        if(item.isPresent()) {
            Users user = item.get();

            UserResponseDto.UserData dto = UserResponseDto.UserData.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .joinPath(user.getJoinPath())
                    .joinReason(user.getJoinReason())
                    .state(user.getState())
                    .role(user.getRoles().get(0))
                    .termYn(user.getTermYn())
                    .privacyYn(user.getPrivacyYn())
                    .collectionYn(user.getCollectionYn())
                    .createDate(user.getCreateDate())
                    .modifiedDate(user.getModifiedDate())
                    .build();

            return response.success(dto);
        }

        return null;
    }

    public ResponseEntity<?> updateUser(UserRequestDto.Update data) {

        Optional<Users> item = userRepository.findByEmail(data.getEmail());

        if(data.getNewPassword().isEmpty() || data.getOldPassword().isEmpty()) {
            if(item.isPresent()) {
                Users user = item.get();

                Users update = Users.builder()
                        .id(data.getId())
                        .email(data.getEmail())
                        .password(user.getPassword())
                        .name(data.getName())
                        .state(data.getState().isEmpty() ? user.getState() : data.getState())
                        .roles(data.getRoles().isEmpty() ? user.getRoles() : data.getRoles())
                        .build();

                userRepository.save(update);
            }
        } else {
            if(item.isPresent()) {
                String pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$";

                if(Pattern.matches(pattern, data.getOldPassword()) && Pattern.matches(pattern, data.getNewPassword())) {
                    Users user = item.get();

                    if(passwordEncoder.matches(data.getOldPassword(), user.getPassword())) {
                        Users update = Users.builder()
                                .id(data.getId())
                                .email(data.getEmail())
                                .password(passwordEncoder.encode(data.getNewPassword()))
                                .name(data.getName())
                                .state(data.getState().isEmpty() ? user.getState() : data.getState())
                                .roles(data.getRoles().isEmpty() ? user.getRoles() : data.getRoles())
                                .build();

                        userRepository.save(update);
                    } else {
                        return response.fail("현재 비밀번호가 맞지 않습니다.", HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return response.fail("비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.", HttpStatus.BAD_REQUEST);
                }

            }
        }

        return response.success("사용자 정보 수정에 성공하였습니다.");
    }

    public ResponseEntity<?> deleteUser(Long id) {
        userRepository.deleteById(id);

        return response.success("사용자 삭제에 성공하였습니다.");
    }
}
