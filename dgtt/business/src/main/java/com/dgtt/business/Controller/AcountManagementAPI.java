package com.dgtt.business.Controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import lombok.extern.slf4j.Slf4j;
import com.dgtt.business.authencation.JwtAuthenticationResponse;
import com.dgtt.business.authencation.JwtTokenProvider;
import com.dgtt.core.config.ApplicationConfig.MessageSourceVi;
import com.dgtt.core.constants.PermissionEnum;
import com.dgtt.business.services.AcountManagementService;
import com.dgtt.business.services.UserSessionService;
import com.dgtt.core.entity.User;
import com.dgtt.core.exception.BadRequestException;
import com.dgtt.core.exception.PermissionException;
import com.dgtt.core.exception.UnauthorizedException;
import com.dgtt.business.model.JwtAuthDto;
import com.dgtt.business.model.ResponseBody;
import com.dgtt.business.model.account.UserBasicDto;
import com.dgtt.business.model.account.UserLoginDto;
import com.dgtt.business.model.account.UserRegisterDto;
import com.dgtt.business.model.account.UserSearchDto;
import com.dgtt.business.model.account.UserUpdateDto;
import com.dgtt.business.utils.ApplicationUtils;
import com.dgtt.business.utils.EncryptUtils;
import com.dgtt.core.utils.StringUtils;

/**
 *
 * @author Nguyen_Toan
 */
@RestController
@RequestMapping("/account-managers")
@Api(basePath = "/account-managers", description = "This API for account management.")
@Validated
@Slf4j
public class AcountManagementAPI {

    @Autowired
    @Qualifier("AcountManagementService_Main")
    private AcountManagementService acountManagement;

    @Autowired
    private UserSessionService userSessionService;

    @Autowired
    private MessageSourceVi messageSourceVi;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final String OK002 = "OK002";

    private List<String> allRole;

    public AcountManagementAPI() {
        allRole = new ArrayList<>();
        allRole.add(PermissionEnum.ADMIN.getRoleCode());
    }

    /**
     * (Account Management Page) API Get Information A User.
     *
     * @param token
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(notes = "API sẽ được gọi trong trường hợp load thông tin 1 user để sửa tài khoản",
            value = "(Account Management Page) API Get Information A User ",
            authorizations = {
                    @Authorization(value = StringUtils.API_KEY)})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Data Response Retrieved.",
                    response = UserUpdateDto.class),
            @ApiResponse(code = 500, message = "Internal Server Error."),
            @ApiResponse(code = 400, message = "Bad Request cause data input."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 403, message = "Access Denied Or Any More."),
            @ApiResponse(code = 401, message = "Unauthorized.")})
    public ResponseEntity<?> getUserbyId(
            @ApiParam(value = "User Id", defaultValue = "0") @PathVariable(value = "userId",
                    required = true) Long userId) {

        return ResponseEntity.ok(ResponseBody.builder().message(messageSourceVi.getMessageVi(OK002))
                .body(acountManagement.getUserById(userId)).build());
    }

    @GetMapping("/profile")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(notes = "API sẽ được gọi trong trường hợp load page profile",
            value = "(Account Management Page) API Get Information Profile ",
            authorizations = {
                    @Authorization(value = StringUtils.API_KEY)})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Data Response Retrieved.",
                    response = UserUpdateDto.class),
            @ApiResponse(code = 500, message = "Internal Server Error."),
            @ApiResponse(code = 400, message = "Bad Request cause data input."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 403, message = "Access Denied Or Any More."),
            @ApiResponse(code = 401, message = "Unauthorized.")})
    public ResponseEntity<?> getProfile() {

        return ResponseEntity.ok(ResponseBody.builder().message(messageSourceVi.getMessageVi(OK002))
                .body(acountManagement.getUserById(ApplicationUtils.getCurrentUser().getUserId())).build());
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(notes = "API sẽ được gọi trong trường hợp tạo mới account ",
            value = "(Account Management Page) API Register Account",
            authorizations = {
                    @Authorization(value = StringUtils.API_KEY)})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Data Response Retrieved.", response = UserBasicDto.class),
            @ApiResponse(code = 500, message = "Internal Server Error."),
            @ApiResponse(code = 400, message = "Bad Request cause data input."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 403, message = "Access Denied Or Any More."),
            @ApiResponse(code = 401, message = "Unauthorized.")})
    public synchronized ResponseEntity<?> create(
            @ApiParam(value = "JSon Object để tạo tài khoản") @Valid @RequestBody UserRegisterDto userDto)
            throws BadRequestException, PermissionException {
        ApplicationUtils.checkHasRole(PermissionEnum.ADMIN.getRoleCode());
        return ResponseEntity
                .ok(ResponseBody.builder().body(acountManagement.createNewAccount(userDto)).message(messageSourceVi.getMessageVi("OK001")).build());

    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(notes = "API sẽ được gọi trong trường hợp update account ",
            value = "(Account Management Page) API Update Account",
            authorizations = {
                    @Authorization(value = StringUtils.API_KEY)})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Data Response Retrieved.", response = UserBasicDto.class),
            @ApiResponse(code = 500, message = "Internal Server Error."),
            @ApiResponse(code = 400, message = "Bad Request cause data input."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 403, message = "Access Denied Or Any More."),
            @ApiResponse(code = 401, message = "Unauthorized.")})
    @Transactional
    public synchronized ResponseEntity<?> update(@ApiParam(
            value = "JSon Object để cập nhật tài khoản") @Valid @RequestBody UserUpdateDto userUpdateDto)
            throws PermissionException, BadRequestException {

        ApplicationUtils.checkHasRole(PermissionEnum.ADMIN.getRoleCode());
        return ResponseEntity
                .ok(ResponseBody.builder().body(acountManagement.updateAccount(userUpdateDto)).message(messageSourceVi.getMessageVi("OK007")).build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(notes = "API sẽ được gọi trong trường hợp delete account ",
            value = "(Account Management Page) API Delete Account",
            authorizations = {
                    @Authorization(value = StringUtils.API_KEY)})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Data Response Retrieved.", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error."),
            @ApiResponse(code = 400, message = "Bad Request cause data input."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 403, message = "Access Denied Or Any More."),
            @ApiResponse(code = 401, message = "Unauthorized.")})
    public ResponseEntity<?> deleteAccount(
            @PathVariable Long id)
            throws PermissionException {

        ApplicationUtils.checkHasRole(PermissionEnum.ADMIN.getRoleCode());
        User user = acountManagement.deleteAccount(id);

        return ResponseEntity
                .ok(ResponseBody.builder().body(user).message(messageSourceVi.getMessageVi("OK003")).build());
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(notes = "API sẽ được gọi trong trường hợp login ",
            value = "(Account Management Page) API Login")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Data Response Retrieved.",
                    response = JwtAuthenticationResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error."),
            @ApiResponse(code = 400, message = "Bad Request cause data input."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 403, message = "Access Denied Or Any More."),
            @ApiResponse(code = 401, message = "Unauthorized.")})
    public ResponseEntity<?> login(
            @ApiParam(value = "JSon object để đăng nhập") @Valid @RequestBody UserLoginDto userLoginDto, HttpServletRequest request)
            throws UnauthorizedException {

        userLoginDto.setIp(request.getRemoteAddr());
        userLoginDto.setUserAgent(request.getHeader("User-Agent"));

        log.info(userLoginDto.getUname() + "|login");
        return ResponseEntity.ok(ResponseBody.builder().body(acountManagement.login(userLoginDto))
                .message(messageSourceVi.getMessageVi(OK002)).build());
    }

    @GetMapping("/logout")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(notes = "API sẽ được gọi trong trường hợp Logout ",
            value = "(Account Management Page) API Logout",
            authorizations = {
                    @Authorization(value = StringUtils.API_KEY)})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Data Response Retrieved.", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server Error."),
            @ApiResponse(code = 400, message = "Bad Request cause data input."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 403, message = "Access Denied Or Any More."),
            @ApiResponse(code = 401, message = "Unauthorized.")})
    public ResponseEntity<?> logout() {

        log.info(ApplicationUtils.getCurrentUser().getUserName() + "|logout");
        userSessionService.delete(ApplicationUtils.getCurrentUser().getUserId());
        return ResponseEntity.ok(messageSourceVi.getMessageVi(OK002));
    }

    @PostMapping("/search")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(notes = "API sẽ được gọi trong trường hợp Search account ",
            value = "(Account Management Page) API Search account ",
            authorizations = {
                    @Authorization(value = StringUtils.API_KEY)})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Data Response Retrieved.", response = UserBasicDto.class),
            @ApiResponse(code = 500, message = "Internal Server Error."),
            @ApiResponse(code = 400, message = "Bad Request cause data input."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 403, message = "Access Denied Or Any More."),
            @ApiResponse(code = 401, message = "Unauthorized.")})
    public ResponseEntity<?> search(
            @ApiParam(value = "Số trang", defaultValue = "0") @RequestParam(name = "page",
                    defaultValue = "0") int page,
            @ApiParam(value = "Số bản ghi / trang", defaultValue = "10") @RequestParam(name = "size",
                    defaultValue = "10") int size,
            @ApiParam(value = "Sắp xếp theo các thuộc tính", defaultValue = "userId") @RequestParam(
                    name = "properties", defaultValue = "userId", required = true) String sortByProperties,
            @ApiParam(value = "Loại sắp xếp ", defaultValue = "ASC") @RequestParam(name = "sortBy",
                    defaultValue = "ASC") String sortBy,
            @ApiParam(value = "Từ khóa tìm kiếm: có thể tìm theo userName, fullName,email,phoneNumber.") @RequestParam(name = "keyword",
                    required = false) String keyword,
            @ApiParam(
                    value = "Tìm kiếm theo các tiêu chí ") @Valid @RequestBody UserSearchDto userSearchDto) throws PermissionException {
//        ApplicationUtils.checkHasRole(PermissionEnum.ADMIN.getRoleCode());
        return ResponseEntity.ok(ResponseBody.builder().message(messageSourceVi.getMessageVi(OK002))
                .body(acountManagement.searchAccount(keyword, page, size, sortByProperties, sortBy, userSearchDto))
                .build());
    }

    @GetMapping(value = "/refresh-token")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(notes = "API sẽ được gọi trong trường hợp refresh token. ",
            value = "(Account Management Page) API refresh token.",
            authorizations = {
                    @Authorization(value = StringUtils.API_KEY)})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Data Response Retrieved.",
                    response = JwtAuthenticationResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error."),
            @ApiResponse(code = 400, message = "Bad Request cause data input."),
            @ApiResponse(code = 404, message = "Not found."),
            @ApiResponse(code = 403, message = "Access Denied Or Any More."),
            @ApiResponse(code = 401, message = "Unauthorized.")})
    public ResponseEntity<?> refreshToken(
            @ApiParam(value = "token") @RequestParam(name = "token", required = true) String token
    ) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String userToken = jwtTokenProvider.getJwtFromHeader(token);
        JwtAuthDto jwtAuthDto = jwtTokenProvider.validateToken(userToken) ? jwtTokenProvider.getJWTInfor(userToken) : new JwtAuthDto();
        return ResponseEntity.ok(
                ResponseBody.builder().body(acountManagement.refreshToken(ApplicationUtils.getPrincipal()))
                        .message(messageSourceVi.getMessageVi(OK002)).build());
    }
}