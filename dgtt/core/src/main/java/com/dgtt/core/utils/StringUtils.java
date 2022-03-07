package com.dgtt.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sadfsafbhsaid
 */
public class StringUtils {

    private StringUtils() {

    }

    /* Character */
    public static final String EQUAL = "=";

    public static final String COMMA = ",";

    public static final String SPACE = " ";

    public static final String FIVE_STAR = "*****";

    public static final String MINUS = "-";

    public static final String COLON = ":";

    public static final String GZ = "gz";

    public static final String DOT = ".";

    public static final String AND = "&";

    public static final String SLASH_RIGHT = "/";

    public static final String SLASH_LEFT = "\\";

    public static final String EMPTY = "";

    public static final String ORDER_MODIFIED_DATE = "modifiedDate";

    public static final String ORDER_PARENT_ID = "parentId";

    public static final String ORDER_LEVEL = "level";

    public static final String XLSX = ".xlsx";

    public static final String NEWLINE_CHARACTER = "<br/>";

    public static final String APPLICATION = "application";

    /* MESSAGE */
    public static final String TO = "tới";

    public static final String ABOUT = "về";

    public static final String COMPANY_NAME = "CPN";

    public static final String UNAUTHORIZED = "Unauthorized";

    public static final String ACCESS_DENIED = "Access denied";

    public static final String U_TIMEZONE = "user.timezone";

    public static final String GMT_7 = "GMT+7";

    public static final String SOCKET_END = "Websocket is disconnected.";

    public static final String GET = "get";
    public static final String GET_LOGIN = "getlogin";

    public static final String SUBFIX = "A01";

    public static final String APPLICATION_EXCEL
            = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static final String INTERNAL_SERVER_WEBSOCKET = "Error: ";

    public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";

    public static final String DF_YYYY_MM_DD_HH_MM_SS_2 = "yyyy_MM_dd_HH_mm_ss";

    public static final String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static final String DF_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String DF_DD_MM_YYYY = "dd/MM/yyyy";

    public static final String API_KEY = "JWT Authentication";

    public static final String AUTHORIZATION = "Authorization";

    public static final String HEADER = "header";

    public static final String MSG_NOT_ENOUGH_PARAMS = "NOT_ENOUGH_PARAMS";

    public static final String MSG_OVER_QUOTA = "OVER_QUOTA";

    public static final String MSG_SUCCESS = "SUCCESS";

    public static final String MSG_ERROR = "FAILED";

    public static final String TOPIC_CODE = "topic_code";

    public static final String USER_NAME = "username";

    public static final String FAV_DURATION_THRESHOLD = "favorite_duration_threshold";

    public static final String STT = "STT";

    public static final String TIME_ZONE = "Asia/Ho_Chi_Minh";

    public static final String USERNAME = "Tên đăng Nhập";
    public static final String CREATED_DATE = "Ngày Khởi Tạo";
    public static final String AVAILABLE_RESOURCES = "Tài Nguyên Khả Dụng";
    public static final String EXPIRATION_DATE = "Ngày Hết Hạn";
    public static final String STATUS_ = "Trạng Thái";
    public static final String ROLE = "Vai trò";

    /* OTHER */
    public static final String ACCOUNT_NOT_CREATE_BY_ADM = "Tài khoản không tạo bởi Admin. ";

    public static final String ACCOUNT_CREATE_BY_ADM = "Tài khoản tạo bởi Admin. ";

    public static final String ACCOUNT_CREATED = "Tài khoản đã tạo. ";

    public static final String ACCOUNT_MANAGEMENT = "Account_Management_";

    public static final String TIME_NEW_ROMAN = "Times New Roman";

    public static final String QLTK = "QUẢN LÍ TÀI KHOẢN";


    /* REGEX */
    public static final String REGEX_CHARACTER_VN = "^[^~!@#$%^&*()_+|\\/\\\"',.:;\\d<>?`\\\\=-]+$";

    public static final String REGEX_FULLNAME_01 = "^[^\\s]*$";
    public static final String REGEX_FREQ = "[0-9]*$";

    public static final String REGEX_FULLNAME = "^([^ 0-9~!@#$%^&*()_+|\\/\\\"',.:;<>?`=-]+\\s){1,5}[^ 0-9~!@#$%^&*()_+|\\/\\\"',.:;<>?`=-]+$";

    public static final String REGEX_USERNAME = "^([a-zA-Z0-9]*[a-zA-Z])[a-zA-Z0-9_]*$";

    public static final String REGEX_DEPT_CODE = "^([a-zA-Z0-9]*[a-zA-Z])[a-zA-Z0-9_-]*$";

    public static final String REGEX_NUMBER = "^[0-9]+$";

    public static final String REGEX_PHONE = "^(0|\\+84|84)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)?(\\d{3})?(\\d{3})$";

    public static final String REGEX_NUMBER_BRANCH = "^[1-9]\\d*$";

//    public static final String REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String REGEX_EMAIL = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    public static final String HASH_SALT = "Adf1lex!@#";

    private static final String[] VIETNAMESE_SIGNS = {
        "aAeEoOuUiIdDyY",
        "áàạảãâấầậẩẫăắằặẳẵ",
        "ÁÀẠẢÃÂẤẦẬẨẪĂẮẰẶẲẴ",
        "éèẹẻẽêếềệểễ",
        "ÉÈẸẺẼÊẾỀỆỂỄ",
        "óòọỏõôốồộổỗơớờợởỡ",
        "ÓÒỌỎÕÔỐỒỘỔỖƠỚỜỢỞỠ",
        "úùụủũưứừựửữ",
        "ÚÙỤỦŨƯỨỪỰỬỮ",
        "íìịỉĩ",
        "ÍÌỊỈĨ",
        "đ",
        "Đ",
        "ýỳỵỷỹ",
        "ÝỲỴỶỸ"
    };

    public static <T> List<T> isList(List<T> listNew, List<T> listOld, int i) {
        List<T> list = new ArrayList<T>();
        //0 - lấy list thêm mới
        //1 - lấy list đã xóa
        if (i == 0) {
            listNew.forEach(s -> {
                if (listOld.isEmpty() || !listOld.contains(s)) {
                    list.add(s);
                }
            });
        } else if (i == 1) {
            listOld.forEach(s -> {
                if (listNew.isEmpty() || !listNew.contains(s)) {
                    list.add(s);
                }
            });
        }
        return list;
    }

    public static <T> void each(List<T> list, Each<T> each) throws RuntimeException {
        if (!isTrue(list)) {
            return;
        }
        for (int index = 0; index < list.size(); index++) {
            each.do_(index, list.get(index));
        }
    }

    public interface Each<T> {

        void do_(int index, T item) throws RuntimeException;
    }

    public static String removeSign4VietnameseString(String str) {
        for (int i = 1; i < VIETNAMESE_SIGNS.length; i++) {
            for (int j = 0; j < VIETNAMESE_SIGNS[i].length(); j++) {
                str = str.replace(VIETNAMESE_SIGNS[i].charAt(j), VIETNAMESE_SIGNS[0].charAt(i - 1));
            }
        }
        return str;
    }

    public static String removeSign4VietnameseStringNotSpace(String str) {
        str = str.replaceAll(" ", "").toLowerCase();
        return removeSign4VietnameseString(str);
    }

    public static Boolean isTrue(Object value) {

        if (value == null) {
            return false;
        }

        if (value instanceof String) {
            return !((String) value).trim().isEmpty();
        }

        if (value instanceof List) {
            return !((List) value).isEmpty();
        }

        if (value instanceof Number) {
//            return !((Number) value).equals(Long.valueOf(0));
            return ((Number) value).intValue() > 0;
        }

        if (value instanceof Boolean) {
            return (Boolean) value;
        }

        if (value instanceof Collection) {
            return !((Collection) value).isEmpty();
        }

        if (value instanceof Object[]) {
            return ((Object[]) value).length > 0;
        }

        return true;
    }

    public static String buildLikeExp(String query) {
        if (query == null || !isTrue(query.trim())) {
            return null;
        }
        return "%" + query.trim().toLowerCase().replaceAll("\\s+", "%") + "%";
    }

    public static String buildLike(String query) {
        if (query == null || !isTrue(query.trim())) {
            return null;
        }
        return "%" + query.trim().replaceAll("\\s+", "%") + "%";
    }

    public static String stringNotTrim(String query) {
        if (query == null || !isTrue(query.trim())) {
            return null;
        }
        return query.trim();
    }

    public static String stringLowerCaseNotTrim(String query) {
        if (query == null || !isTrue(query.trim())) {
            return null;
        }
        return query.trim().toLowerCase();
    }

    public static String stringUpperCaseNotTrim(String query) {
        if (query == null || !isTrue(query.trim())) {
            return null;
        }
        return query.trim().toUpperCase();
    }

    public static boolean validateEmail(Object value) {
        String email = value.toString();
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = pattern.matcher(email);
        boolean check = matcher.matches();
        return check;
    }

    public static boolean validateFullName(Object value) {
        String fullname = value.toString();
        Pattern pattern = Pattern.compile(REGEX_FULLNAME);
        Matcher matcher = pattern.matcher(fullname);
        boolean check = matcher.matches();
        return check;
    }

    public static boolean validateSdt(Object value) {
        String sdt = value.toString();
        Pattern pattern = Pattern.compile(REGEX_PHONE);
        Matcher matcher = pattern.matcher(sdt);
        boolean check = matcher.matches();
        return check;
    }

    public static String setNumberToString(Object object) {
        String value = stringNotTrim((String) object);
        if (!isTrue(object)) {
            return null;
        }
        Pattern pattern = Pattern.compile(REGEX_FREQ);
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public static boolean validate(Object object, String regex) {
        String value = stringNotTrim((String) object);
        if (!isTrue(object)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        boolean check = matcher.matches();
        return check;
    }

    /* FUNCTION */
    public static final String reverseString(String input) {
        return new StringBuffer(input).reverse().toString();
    }

    public static final String generatedString(int lenght, String prefix, String suffixe) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(isTrue(prefix) ? prefix : "")
                .append(UUID.randomUUID().toString().substring(0, lenght)).append(isTrue(suffixe) ? suffixe : "")
                .toString().toUpperCase();
    }

    public static final String generatedRequestCode(String prefix, String append, String num) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(isTrue(prefix) ? prefix : "")
                .append(append).append(num).toString().toUpperCase();
    }

    public static String getMethodName(String fieldName) {
        return new StringBuilder(GET + fieldName.substring(0, 1).toUpperCase())
                .append(fieldName.substring(1)).toString();
    }

    /**
     * Get time allow From String format 8h -> 21h
     *
     * @param string
     * @return
     */
    public static Integer getTimeAllow(String string, int index) {
        return Integer.valueOf(string.split("->")[index].trim().split("h")[0].trim());
    }

    //Convert Date to String
    public static String date2str(Date input, String oFormat) {
        String result = "";
        if (input != null) {
            try {
                DateFormat df = new SimpleDateFormat(oFormat);
                result = df.format(input);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    //Convert String to Date
    public static Date str2date(String input, String format) throws java.text.ParseException {
        Date result = null;
        if (!input.isEmpty()) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                result = formatter.parse(input);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
