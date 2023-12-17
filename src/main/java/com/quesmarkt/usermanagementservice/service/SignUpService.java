package com.quesmarkt.usermanagementservice.service;

import com.quesmarkt.usermanagementservice.data.entity.User;
import com.quesmarkt.usermanagementservice.data.request.SignUpRequest;
import com.quesmarkt.usermanagementservice.data.response.SignUpResponse;
import com.quesmarkt.usermanagementservice.manager.UserManager;
import com.quesmarkt.usermanagementservice.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.quesmarkt.usermanagementservice.util.UserUtils.createInitialUser;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class SignUpService extends BaseService {

    private final UserManager userManager;

    public ResponseEntity<SignUpResponse> basicSignUp(SignUpRequest signUpRequest) {
        try {
            if (!isRequestValid(signUpRequest)) {
                return ResponseEntity.badRequest().body(SignUpResponse.builder().message("Invalid Request.").build());
            }
            Boolean srExistByMail = userManager.isExistByEmail(signUpRequest.getMail());
            if (BooleanUtils.isTrue(srExistByMail)) {
                return ResponseEntity.ok().body(SignUpResponse.builder().message("User has registered already.").status(-1).build());
            }
            User savedUser = userManager.insert(createInitialUser(signUpRequest));
            if (Objects.nonNull(savedUser)) {
                String jwt = JwtUtil.createJWT(savedUser.getId(), savedUser.getUsername(), 7L);
                return ResponseEntity.ok(SignUpResponse.builder().jwt(jwt).build());
            } else {
                return ResponseEntity.internalServerError().build();
            }
        } catch (Exception e) {
            logger.error("basicSignUp got exception", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    private boolean isRequestValid(SignUpRequest signUpRequest) {
        return true; //todo validation
    }


}
