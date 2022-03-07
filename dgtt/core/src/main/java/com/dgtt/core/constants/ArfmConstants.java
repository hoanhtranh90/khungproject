package com.dgtt.core.constants;

public class ArfmConstants {

//    public static final String DATE_FIX = "1/8/2021";
    public static final String DATE_FIX = "3/9/2021";
//    public static final String DATE_FIX = "3/15/2021";
//    public static final String DATE_FIX = "3/15/2021";
//    public static final String DATE_FIX = "3/15/2021";
//    public static final String DATE_FIX = "3/15/2021";
//    public static final String DATE_FIX = "3/7/2021";
//    public static final String DATE_FIX = "3/7/2021";
//    public static final String DATE_FIX = "3/7/2021";
    public static final int HOUR_FIX = 0;

    public static final Double DISTANCE_NEARLY = Double.valueOf(2000); //met
    public static final Double RTWP_NOISE_STANDARD = Double.valueOf(-95); //RTWP
    public static final Double RTWP_NOISE_3G_4G = Double.valueOf(-108); //RTWP
    public static final int MAX_NEAR_POINT = 7;
    public static final Double UL_RSSI_SIGMA = 13.0;

    public static String FILE_SPLIT = ";";

    public static class CellNoise{
        public static String STATUS_NOISE = "NOISE";
    }


    public static class COLOR{

        public static int GREEN = 1;
        public static int ORANGE = 2;
        public static int RED = 3;
    }

    public static class LicenseCodeRes{

        public static int VALID = 1;
        public static int SYS_ERR = 2;
        public static int EXPIRED = 3;
        public static int NOT_FOUND = 4;
    }

    public static class Map_Tech {
        public static int TECH_2G = 1;
        public static int TECH_3G = 2;
        public static int TECH_4G = 3;
        public static int TECH_5G = 4;
    }

    public static class Map_Telco {
        public static int VIETTEL = 1;
        public static int MOBIFONE = 2;
        public static int VINAPHONE = 3;
        public static int VIETNAM_MOBILE = 4;
        public static int ARFM = 5;
    }

    public static class Map_Telco_str {
        public static String VIETTEL = "VIETTEL";
        public static String MOBIFONE = "MOBIFONE";
        public static String VNPT = "VNPT";
        public static String VIETNAMMOBILE = "VIETNAMMOBILE";
        public static String ARFM = "ARFM";
    }


    public static class Map_Status {
        public static int NORMAL = 1;
        public static int INFERENCE = 2;
        public static int INFERENCE_AUTO = 3;
    }

    public static class Map_System {
        public static int SYS_5G_NR_2100 = 1;
        public static int INFERENCE = 2;
        public static int INFERENCE_AUTO = 3;
        public static int VIETNAM_MOBILE = 4;
        public static int ARFM = 5;
    }
}
