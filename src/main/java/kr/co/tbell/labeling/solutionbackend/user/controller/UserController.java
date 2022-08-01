package kr.co.tbell.labeling.solutionbackend.user.controller;

import kr.co.tbell.labeling.solutionbackend.common.dto.Response;
import kr.co.tbell.labeling.solutionbackend.common.helper.ErrorHelper;
import kr.co.tbell.labeling.solutionbackend.user.dto.request.UserRequestDto;
import kr.co.tbell.labeling.solutionbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;
    private final Response response;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Validated UserRequestDto.SignUp signUp, Errors errors) {
        if (errors.hasErrors()) {
            return response.invalidFields(ErrorHelper.refineErrors(errors));
        }

        return userService.signUp(signUp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {

        return userService.getUser(id);
    }

    @GetMapping("")
    public ResponseEntity<?> getUsers(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String state,
            @RequestParam int page) {

        return userService.getUserList(name, email, state, page);
    }

    @PatchMapping("")
    public ResponseEntity<?> update(@RequestBody @Validated UserRequestDto.Update update, Errors errors) {

        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(ErrorHelper.refineErrors(errors));
        }

        return userService.updateUser(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }
}
