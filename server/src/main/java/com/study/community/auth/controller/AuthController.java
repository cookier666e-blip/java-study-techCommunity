package com.study.community.auth.controller;

import com.study.community.auth.dto.RegisterRequest;
import com.study.community.auth.dto.RegisterResponse;
import com.study.community.auth.service.AuthService;
import com.study.community.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication") // 将当前控制器的所有接口归类到 Swagger 文档的 Authentication 标签组中。仅影响 API 文档生成
@RestController     // 标记此类为 Spring MVC 控制器（处理 HTTP 请求）
@RequestMapping("/api/auth")    //  本身不限定 HTTP 方法，而 @GetMapping 等衍生注解强制限定单一方法类型，二者需根据场景选择。
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register a new user") // 为 register 方法生成 Swagger 文档摘要。
    @PostMapping("/register")   // 等价写法：@RequestMapping(value = "/register", method = RequestMethod.POST) 
    public ResponseEntity<ApiResponse<RegisterResponse>> register( // 返回 ApiResponse 包装的 RegisterResponse 响应体。
            @Valid @RequestBody RegisterRequest request // 使用 @Valid 注解进行参数验证，确保请求体符合 RegisterRequest 规范。
    ) {
        RegisterResponse registeredUser = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("注册成功", registeredUser));
    }
}

