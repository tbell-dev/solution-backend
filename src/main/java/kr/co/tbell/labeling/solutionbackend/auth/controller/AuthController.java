package kr.co.tbell.labeling.solutionbackend.auth.controller;

import kr.co.tbell.labeling.solutionbackend.auth.dto.request.AuthRequestDto;
import kr.co.tbell.labeling.solutionbackend.auth.service.AuthService;
import kr.co.tbell.labeling.solutionbackend.common.dto.Response;
import kr.co.tbell.labeling.solutionbackend.common.helper.ErrorHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;
    private final Response response;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated AuthRequestDto.Login login, Errors errors) {
        if (errors.hasErrors()) {
            return response.invalidFields(ErrorHelper.refineErrors(errors));
        }
        return authService.login(login);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@Validated AuthRequestDto.Reissue reissue, Errors errors) {
        if (errors.hasErrors()) {
            return response.invalidFields(ErrorHelper.refineErrors(errors));
        }
        return authService.reissue(reissue);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Validated AuthRequestDto.Logout logout, Errors errors) {
        if (errors.hasErrors()) {
            return response.invalidFields(ErrorHelper.refineErrors(errors));
        }
        return authService.logout(logout);
    }
}
