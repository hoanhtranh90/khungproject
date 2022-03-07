package com.dgtt.business.utils;

import com.dgtt.business.authencation.CustomUserDetails;
import com.dgtt.core.entity.Role;
import com.dgtt.core.entity.User;
import com.dgtt.core.exception.PermissionException;
import com.dgtt.core.model.Actor;
import com.dgtt.core.model.UserTokenInfo;
import com.dgtt.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ApplicationUtils {

    private ApplicationUtils() {

    }

    public static User getCurrentUser() {
        try {
            return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal()).getUser();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }

    }

    public static UserTokenInfo getUserTokenInfo() {
        User user = getCurrentUser();
        Role role = getCurrentRole();
        Actor actor = Actor.builder().fullName(user.getFullName()).type(1L).username(user.getUserName()).roleId(role != null ? role.getRoleId() : null).userId(user.getUserId()).build();
        return UserTokenInfo.builder().actor(actor).user(user).role(role).build();
    }

    public static Role getCurrentRole() {
        try {
            CustomUserDetails customUserDetails = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal());
            List<Role> roles = new ArrayList<>(customUserDetails.getRoleOfUser());
            return StringUtils.isTrue(roles) ? roles.get(0) : null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }

    }

    public static CustomUserDetails getPrincipal() {
        try {
            return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal());
        } catch (Exception e) {
            log.error("Here get null: " + e.getMessage(), e);
            return null;
        }

    }

    public static List<String> getRoleCurrentUser() {
        List<String> roles = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            roles.add(auth.getAuthority());
        }
        return roles;
    }

    public static Sort getSort(String sortByProperties, String sortBy) {
        Sort sort = null;
        if (Direction.ASC.toString().equals(sortBy)) {
            sort = Sort.by(sortByProperties.split(StringUtils.COMMA)).ascending();
        } else {
            sort = Sort.by(sortByProperties.split(StringUtils.COMMA)).descending();
        }
        return sort;
    }

    public static Pageable getPageableNotAddSort(int pageNumber, int size, String sortByProperties, String sortBy) {
        Sort sort = ApplicationUtils.getSort(sortByProperties, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, size, sort);
        return pageable;
    }

    public static Pageable getPageable(int pageNumber, int size, String sortByProperties, String sortBy) {
        Sort sort = getSort(sortByProperties, sortBy);
        Sort sort1 = getSort(StringUtils.ORDER_MODIFIED_DATE, "DESC");
        Pageable pageable = PageRequest.of(pageNumber, size, sort);
        if (!sortByProperties.contains(StringUtils.ORDER_MODIFIED_DATE)) {
            pageable = sortAgain(pageable, sort1);
        }
        return pageable;
    }

    public static Pageable sortAgain(Pageable pageable, Sort sort) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().and(sort));
    }

    public static void checkHasRole(final String roleCode) throws PermissionException {
        if (ApplicationUtils.getRoleCurrentUser().isEmpty() || (null != roleCode
                && !roleCode.equalsIgnoreCase(ApplicationUtils.getRoleCurrentUser().get(0)))) {
            throw new PermissionException("Xin lỗi bạn không có quyền.");
        }
    }

    public static void checkHasRole(final List<String> roleCode) throws PermissionException {
        if (ApplicationUtils.getRoleCurrentUser().isEmpty() || (null != roleCode
                && !roleCode.contains(ApplicationUtils.getRoleCurrentUser().get(0)))) {
            throw new PermissionException("Xin lỗi bạn không có quyền.");
        }
    }

    private static final String[] HEADERS_TO_TRY = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP",
        "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_VIA",
        "REMOTE_ADDR"};

    public static String getAPI() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String api = new StringBuilder(request.getMethod() + " " + request.getRemoteAddr() + ":"
                + request.getLocalPort() + request.getRequestURI()).toString();
        return api;
    }

    public static String getIPAddress() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        String ipAddress = "";
        for (String header : HEADERS_TO_TRY) {
            ipAddress = request.getHeader(header);
            if (ipAddress != null && ipAddress.length() != 0 && !"unknown".equalsIgnoreCase(ipAddress)) {
                return ipAddress;
            }
        }

        return request.getRemoteAddr();
    }

    public static Map getCurrentMACAddress() {
        Map<String, String> map = new ConcurrentHashMap<>();
        InetAddress ip;
        StringBuilder sb = new StringBuilder();
        try {
            ip = InetAddress.getLocalHost();
            map.put("ipAddress", ip.getHostAddress());
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            map.put("ipMacAddress", sb.toString());
        } catch (UnknownHostException | SocketException e) {

            e.printStackTrace();

        }
        return map;
    }

    public static String getClientMACAddress(String clientIp) {
        String str = "";
        String macAddress = null;
        try {
            if (StringUtils.isTrue(clientIp)) {
                Process p = Runtime.getRuntime().exec("nbtstat -A " + clientIp);
                InputStreamReader ir = new InputStreamReader(p.getInputStream());
                LineNumberReader input = new LineNumberReader(ir);
                for (int i = 1; i < 100; i++) {
                    str = input.readLine();
                    if (str != null) {
                        if (str.indexOf("MAC Address") > 1) {
                            macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return macAddress;
    }

    public static String getUserAgent() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getHeader("User-Agent");
    }

}
