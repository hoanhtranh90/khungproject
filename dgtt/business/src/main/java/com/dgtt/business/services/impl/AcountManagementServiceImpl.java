package com.dgtt.business.services.impl;

import com.dgtt.business.authencation.CustomUserDetails;
import com.dgtt.business.authencation.JwtAuthenticationResponse;
import com.dgtt.business.authencation.JwtTokenProvider;
import com.dgtt.business.mapper.MapperObject;
import com.dgtt.business.model.account.*;
import com.dgtt.business.services.AcountManagementService;
import com.dgtt.business.services.ActionLogService;
import com.dgtt.business.utils.ApplicationUtils;
import com.dgtt.core.config.ApplicationConfig.MessageSourceVi;
import com.dgtt.core.constants.ActionLogEnum;
import com.dgtt.core.constants.PermissionEnum;
import com.dgtt.core.constants.StatusEnum;
import com.dgtt.core.constants.TypeLogEnum;
import com.dgtt.core.entity.Role;
import com.dgtt.core.entity.User;
import com.dgtt.core.entity.UserRole;
import com.dgtt.core.entity.UserSession;
import com.dgtt.core.exception.BadRequestException;
import com.dgtt.core.exception.PermissionException;
import com.dgtt.core.exception.UnauthorizedException;
import com.dgtt.core.repository.*;
import com.dgtt.core.utils.Constants;
import com.dgtt.core.utils.DateUtils;
import com.dgtt.core.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyen_Toan
 */
@Service
@Primary
@Slf4j
@Qualifier("AcountManagementService_Main")
@Transactional
public class AcountManagementServiceImpl implements AcountManagementService {

    @Value(value = "${account.management}")
    private String accountManager;

    @Value(value = "${password.management}")
    private String passwordManager;

    @Value(value = "${email.management}")
    private String emailManager;

    @Value(value = "${firstname.management}")
    private String firstNameManager;

    @Value(value = "${lastname.management}")
    private String lastNameManager;

    @Value(value = "${organization.management}")
    private String organizationManager;

    @Value(value = "${phonenumber.management}")
    private String phoneNumberManager;

    @Autowired
    private MessageSourceVi messageSourceVi;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ActionLogService actionLogService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepositoryCustom userRepositoryCustom;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    private final long dateNow = DateUtils.getDateAllEndDate(new Date()).getTime();


    //tao tai khoan admin mac dinh
    @Bean
    public CommandLineRunner initialAccountAdminAndApplication() {

        return (args) -> {
            if (Optional.empty().equals(userRepository.findById(Long.valueOf(1)))) {
                Role role = roleRepository.findByRoleCode(PermissionEnum.ADMIN.getRoleCode());
                User user = User.builder().email(emailManager).fullName(firstNameManager)
                        .phoneNumber(phoneNumberManager)
                        .isDelete(Constants.DELETE.NORMAL)
                        .status(StatusEnum.ACTIVE.getStatus())
                        .createByUserId(Long.valueOf(0))
                        .status(StatusEnum.ACTIVE.getStatus()).userName(accountManager)
                        .userId(Long.valueOf(1)).build();

                UserRole userRole = UserRole.builder().role(role).user(userRepository.save(user))
                        .userRoleId(Long.valueOf(1)).build();

                userRoleRepository.save(userRole);
                log.info("Created account management.");
            } else {
                log.info("Account management already exsit.");
            }

        };
    }

    /**
     * @param session
     * @param userLogin
     */
    private synchronized UserSession getUserSession(String session, User userLogin) {
        UserSession userSession = userSessionRepository.findAllByUserUserId(userLogin.getUserId());
        if (null != userSession) {
            userSession.setSession(session);
        } else {
            userSession = UserSession.builder().session(session).user(userLogin).build();
        }
        return userSession;

    }

    @Override
    public synchronized UserBasicDto createNewAccount(UserRegisterDto userBasicDto) throws BadRequestException {
        try {
            // Check Username
            List<User> userCheckUsernames
                    = checkAlreadyUsernameExist(userBasicDto.getUserName());
            if (!userCheckUsernames.isEmpty()) {
                throw new BadRequestException(messageSourceVi.getMessageVi("ER002", new Object[]{userBasicDto.getUserName()}));
            }

//            if (!ObjectUtils.isEmpty(userBasicDto.getEmail())) {
//                // Check email
//                List<User> userCheckEmails
//                        = checkAlreadyEmailExist(userBasicDto.getEmail());
//
//                if (!userCheckEmails.isEmpty()) {
//                    throw new BadRequestException(messageSourceVi.getMessageVi("ER002", new Object[]{userBasicDto.getEmail()}));
//                }
//            }
//            // Check Phone
//            if (!ObjectUtils.isEmpty(userBasicDto.getPhoneNumber())) {
//                List<User> userCheckPhoness
//                        = checkAlreadyPhoneExist(userBasicDto.getPhoneNumber());
//                if (!userCheckPhoness.isEmpty()) {
//                    throw new BadRequestException(messageSourceVi.getMessageVi("ER002", new Object[]{userBasicDto.getPhoneNumber()}));
//                }
//            }
            User userRegister = checkAndUpdateBranches(userBasicDto);

            userRepository.save(userRegister);
            return getUserByUser(userRegister);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }

//        userDeptRoleRepository.save(UserDeptRole.builder().user(newUserSaved)
//                .role(roleRepository.findByRoleCode(userBasicDto.getRoleCode().trim())).build());
    }

    /**
     * @param userBasicDto
     * @return
     * @throws PermissionException
     */
    private User checkAndUpdateBranches(UserRegisterDto userBasicDto) {
        User userRegister = MapperObject.userBuilder(userBasicDto);
        userRegister.setStatus(userBasicDto.getStatus() != null ? userBasicDto.getStatus() : StatusEnum.ACTIVE.getStatus());
        userRegister.setCreateByUserId(ApplicationUtils.getCurrentUser().getUserId());

        return userRegister;
    }

    @Override
    public User deleteAccount(Long id) {
        User user = userRepository.findByIdCustom(id, StatusEnum.getStatusIsDelete(), Constants.DELETE.NORMAL);
        if (user != null) {
            try {
                user.setIsDelete(Constants.DELETE.NORMAL);
                User user1 = userRepository.save(user);
                return user1;
            } catch (Exception ex) {
                Logger.getLogger(AcountManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return null;
        //userRepository.deleteByUserIdAndUsername(userBasicDto.getUserId(), userBasicDto.getUsername());
    }

    @Override
    public Page<UserBasicDto> searchAccount(String keyword, int pageNumber, int size, String sortByProperties, String sortBy,
            UserSearchDto userSearchDto) {
        Sort sort = ApplicationUtils.getSort(sortByProperties, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, size, sort);
        keyword = StringUtils.buildLikeExp(keyword);
        if (checkUserSearch(userSearchDto)) {
            keyword = null;
        }
        List<UserBasicDto> userBasicDtos = new ArrayList<>();
        Page<User> page = userRepository.searchAllUser(Constants.DELETE.NORMAL, StatusEnum.getStatusIsDelete(), keyword, StringUtils.buildLikeExp(userSearchDto.getUserName()),
                StringUtils.buildLikeExp(userSearchDto.getFullName()), StringUtils.buildLikeExp(userSearchDto.getPhoneNumber()), StringUtils.buildLikeExp(userSearchDto.getEmail()), pageable);
        page.getContent().stream().forEach(u -> {
            userBasicDtos.add(getUserByUser(u));
        });
        return new PageImpl<>(userBasicDtos, pageable, page.getTotalElements());

    }

    public boolean checkUserSearch(UserSearchDto userSearchDto) {
        return StringUtils.isTrue(userSearchDto.getEmail()) || StringUtils.isTrue(userSearchDto.getDeptId())
                || StringUtils.isTrue(userSearchDto.getFullName()) || StringUtils.isTrue(userSearchDto.getPhoneNumber())
                || StringUtils.isTrue(userSearchDto.getUserName());
    }

    @Override
    public synchronized UserBasicDto updateAccount(UserUpdateDto userUpdateDto)
            throws BadRequestException {
        try {
            User userOld = findUserById(userUpdateDto.getUserId());

            if (null == userOld) {
                throw new BadRequestException(messageSourceVi.getMessageVi("USERNAME_NOT_FOUND_MSG"));
            }
            if (!ObjectUtils.isEmpty(userUpdateDto.getUserName()) && (null != userOld.getUserName()
                    && !userOld.getUserName().equalsIgnoreCase(userUpdateDto.getUserName()))) {
                // Check Username
                List<User> userCheckUsernames
                        = checkAlreadyUsernameExist(userUpdateDto.getUserName());
                if (!userCheckUsernames.isEmpty()) {
                    throw new BadRequestException(messageSourceVi.getMessageVi("ER002", new Object[]{userUpdateDto.getUserName()}));
                }
            }

//            // Validate
//            if (!ObjectUtils.isEmpty(userUpdateDto.getEmail()) && (null != userOld.getEmail()
//                    && !userOld.getEmail().equalsIgnoreCase(userUpdateDto.getEmail()))) {
//                // Check email
//                List<User> userCheckEmail
//                        = checkAlreadyEmailExist(userUpdateDto.getEmail().toLowerCase());
//
//                if (!userCheckEmail.isEmpty()) {
//                    throw new BadRequestException(messageSourceVi.getMessageVi("ER002", new Object[]{userUpdateDto.getEmail()}));
//                }
//            }
//            // Check Phone
//            if (!ObjectUtils.isEmpty(userUpdateDto.getPhoneNumber()) && (null != userOld.getPhoneNumber()
//                    && !userOld.getPhoneNumber().equalsIgnoreCase(userUpdateDto.getPhoneNumber()))) {
//
//                List<User> userCheckPhone
//                        = checkAlreadyPhoneExist(userUpdateDto.getPhoneNumber());
//                if (!userCheckPhone.isEmpty()) {
//                    throw new BadRequestException(messageSourceVi.getMessageVi("ER002", new Object[]{userUpdateDto.getPhoneNumber()}));
//                }
//            }
            userOld.setFullName(userUpdateDto.getFullName());
            userOld.setUserName(userUpdateDto.getUserName());
            userOld.setEmail(userUpdateDto.getEmail());
            userOld.setPhoneNumber(userUpdateDto.getPhoneNumber());
            userOld.setSex(userUpdateDto.getSex());
            userOld.setAddress(userUpdateDto.getAddress());
            userOld.setDateOfBirth(userUpdateDto.getDateOfBirth());
            userOld.setAvatar(userUpdateDto.getAvatar());
            userOld.setStatus(userUpdateDto.getStatus() != null ? userUpdateDto.getStatus() : userOld.getStatus());
            userRepository.save(userOld);
            return getUserByUser(userOld);

        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findByIdCustom(userId, StatusEnum.getStatusIsDelete(), Constants.DELETE.NORMAL);

    }

    @Override
    public UserBasicDto getUserById(Long userId) {
        User userForUpdate = userRepository.findByIdCustom(userId, StatusEnum.getStatusIsDelete(), Constants.DELETE.NORMAL);
        return getUserByUser(userForUpdate);
    }

    public UserBasicDto getUserByUser(User userForUpdate) {
        if (null != userForUpdate) {
            UserBasicDto basicDto = modelMapper.map(userForUpdate, UserBasicDto.class);
            basicDto.setStatusStr(StatusEnum.getStatusDescription(basicDto.getStatus()));
            return basicDto;
        }
        return null;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUserName(username, StatusEnum.getStatusIsDelete(), Constants.DELETE.NORMAL);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> checkAlreadyEmailExist(String email) {
        return userRepository.checkAlreadyEmailExsit(email, StatusEnum.getStatusIsDelete(), Constants.DELETE.NORMAL);
    }

    @Override
    public List<User> checkAlreadyPhoneExist(String phoneNumber) {
        return userRepository.checkAlreadyPhoneExsit(phoneNumber, StatusEnum.getStatusIsDelete(), Constants.DELETE.NORMAL);
    }

    @Override
    public List<User> checkAlreadyUsernameExist(String username) {
        return userRepository.checkAlreadyUsernameExsit(username, StatusEnum.getStatusIsDelete(), Constants.DELETE.NORMAL);
    }

    @Override
    public JwtAuthenticationResponse refreshToken(CustomUserDetails customUserDetails) {
        // Kiểm tra nếu user tồn tại
        customUserDetails.setUser(userRepository.findByIdCustom(customUserDetails.getUser().getUserId(), StatusEnum.getStatusIsDelete(), Constants.DELETE.NORMAL));
        // Kiểm tra nếu session tồn tại
        customUserDetails.setSession(userSessionRepository.findAllByUserUserId(customUserDetails.getUser().getUserId()).getSession());

        try {
            return JwtAuthenticationResponse.builder().accessToken(tokenProvider.generateToken(customUserDetails)).build();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AcountManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AcountManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(AcountManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = UnauthorizedException.class)
    public JwtAuthenticationResponse login(UserLoginDto userLoginDto) throws UnauthorizedException {
        Authentication authentication = null;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userLoginDto.getUname(), userLoginDto.getPwd()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                    .currentRequestAttributes();
            String session = servletRequestAttributes.getSessionId();

            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            User userLogin = customUserDetails.getUser();
            customUserDetails.setSession(session);

            UserSession userSession = getUserSession(session, userLogin);
            userSessionRepository.save(userSession);
            JwtAuthenticationResponse authenticationResponse = JwtAuthenticationResponse.builder().accessToken(tokenProvider.generateToken(customUserDetails)).build();
            actionLogService.addLog(null, ActionLogEnum.DANG_NHAP.getAction(), TypeLogEnum.NOIBO.getType(), userLogin.getUserId());
            return authenticationResponse;
        } catch (BadCredentialsException e) {
            log.error(userLoginDto.getUname() + "|" + e.getMessage());
            throw new UnauthorizedException(messageSourceVi.getMessageVi("MSG_LOGIN_FAILED"));
        } catch (AccessDeniedException ex1) {
            log.error(userLoginDto.getUname() + "|" + ex1.getMessage());
            throw new AccessDeniedException(ex1.getMessage());
        } catch (LockedException ex) {
            log.error(userLoginDto.getUname() + "|" + ex.getMessage());
            throw new UnauthorizedException(messageSourceVi.getMessageVi("MSG_LOCKED_ACCOUNT"));
        } catch (DisabledException exx) {
            log.error(userLoginDto.getUname() + "|" + exx.getMessage());
            throw new UnauthorizedException(messageSourceVi.getMessageVi("MSG_DISABLE_ACCOUNT"));
        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException ex) {
            throw new UnauthorizedException(ex.getMessage());
        }
    }
}
