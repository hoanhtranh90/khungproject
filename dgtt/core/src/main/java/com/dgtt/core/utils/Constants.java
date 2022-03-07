/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgtt.core.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sadfsafbhsaid
 */
public class Constants {

    public interface REDIS {

        String NAME_GROUP_USER_TOKEN = "user_token";
        String KEY_START_COUNT_INPUT_REMAINING = "user_input_remaining_";
        String SUFFIXES_TOKEN = "_token";
        String PREFIXES_GROUP_USER = "user_";
        String LOCK_USER = "lock_user";
        String USER_DEVICES = "user_devices";
        int jwtTtlInMinutes = 365 * 24 * 60;
        int LIMIT_DEVICE_CURRENT = 2;
        int TIME_COUNT_LOCK = 5;
        Long MAX_DEVICE = 4L;
        Long MAX_INCORRECT = 5L;
        String FIRST_LOGIN = "firstLogin";
    }

    public interface UPLOAD_CONFIG {

        interface TYPE {

            String FILE_SERVICE = "fileService";
            String LOCAL_STORAGE = "localStorage";
        }
    }

    public interface DEPARTMENT_CODE {

        String CUC = "CUC";
    }

    public interface TYPE_LICENSE {

        Long ORGANIZATION = 0L;
        Long BTS = 1L;
    }

    public interface DOWNLOAD_OPTION {

        interface TYPE {

            String ORIGINAL = "original";
            String PDF = "pdf";
            String IMAGE = "image";
            String TRANG_KY_SO = "trangKySo";
        }
    }

    public interface ATTACHMENT_IS_SIGNED {

        Long CHƯA_KY = 0L;
        Long DA_KY = 1L;
    }

    public interface DELETE {

        Long DELETED = 1L;
        Long NORMAL = 0L;
    }

    public interface CONTENT_TYPE {

        String DOC = "application/msword";
        String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        String PDF = "application/pdf";
    }

    public interface ATTACHMENT_OBJECT_TYPE {

        Long VB_DI = -1L;
        Long VB_KHAC = 0L;
        Long VB_INTERF = 1L;
        Long VB_INTERF_LIEN_QUAN = 2L;
        
        Long VB_PLAN = 3L;
        Long VB_OUT_GOING_DOC = 4L;

    }

    public static List<Long> ATTACHMENT_RELATION_TYPES = new ArrayList<>();

    static {
        ATTACHMENT_RELATION_TYPES.add(ATTACHMENT_RELATION_TYPE.PHU_LUC_DINH_KEM);
        ATTACHMENT_RELATION_TYPES.add(ATTACHMENT_RELATION_TYPE.BAN_DO);
        ATTACHMENT_RELATION_TYPES.add(ATTACHMENT_RELATION_TYPE.GHI_AM);
        ATTACHMENT_RELATION_TYPES.add(ATTACHMENT_RELATION_TYPE.KHAC);
    }

    public interface ATTACHMENT_RELATION_TYPE {

        Long CONG_VAN_GOC = 0L;
        Long PHU_LUC_DINH_KEM = 1L;
        Long BAN_DO = 2L;
        Long GHI_AM = 3L;
        Long KHAC = 4L;

    }

    public interface ATTACHMENT_RELATION_OBJECT_TYPE {

        Long INTERFERENCE = 0L;
    }
}
