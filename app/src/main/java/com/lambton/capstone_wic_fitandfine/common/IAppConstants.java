package com.lambton.capstone_wic_fitandfine.common;


public interface IAppConstants {

    int MAX_SCORE_PAIN=20;
    int MAX_SCORE_PC=50;
    int MAX_SCORE_PI=20;
    int MAX_SCORE_PSY=20;
    int MAX_SCORE_ES=40;
    String KEY_USER_ID = "user_id";
    String USER_TYPE = "PAT";

    String SUCCESS_RESPONSE_CODE = "200";
    int SESSION_TIMEOUT_RESPONSE_CODE = 408;
    String BASE_CHAT_URL = "BASE_CHAT_URL";
    String SUPPORT_MAIL = "SUPPORT_MAIL";
    String SERVER_DATE_FORMAT = "MM-dd-yyyy";
    String SERVER_DATE_FORMAT_V1 = "MM/dd/yyyy";
    String UI_DATE_FORMAT = "MM/dd/yy";
    String ALT_DATE_FORMAT = "MMMM dd, yyyy";
    String ALARM_TIME_FORMAT = "EEE, MM/dd/yy, h:mm a";
    String TIME_FORMAT = "h:mm a";
    String MIME_TYPE_IMAGE = "image/*";
    String MIME_TYPE_PDF = "application/pdf";
    String MIME_TYPE_MS_WORD = "application/msword";
    String MIME_TYPE_DEFAULT = "application/*";

    String KEY_SELECTED_PAIN_AREA="SelectedPainArea";
    String KEY_SELECTED_PAIN_AREA_NAME="SelectedPainAreaName";
    String KEY_SELECTED_WEEK="SelectedWeek";
    String KEY_SELECTED_MONTH="SelectedMonth";
    String KEY_SELECTED_YEAR="SelectedYear";
    String KEY_SELECTED_TAB="SelectedTab";
    String KEY_MAIN_SELECTED_TAB="MainSelectedTab";
    String KEY_IS_FROM_OVERALL_TREND="isOverAllTrend";

    int INDEX_SELECTED_YEAR=2;
    int INDEX_SELECTED_MONTH=1;
    int INDEX_SELECTED_WEEK=0;

    String INTENT_ACTION_LOCAL="LOCAL_BROADCAST_HANDLE";
    String INTENT_TYPE="IntentType";
    int INTENT_TYPE_SYMPTOMS_DATA=0;
    int INTENT_TYPE_PAIN_AREA_DATA=1;
    String KEY_SYMPTOMS_LIST="SymptomsList";
    String KEY_PAIN_AREA_CHAT="SELECTED_PAIN_AREA";

    String KEY_LOCATES_TYPE="locatesType";
    String KEY_IS_FROM_THERAPY="IsFromTherapy";

    int USER_WATSON=0;
    int USER_PATIENT=1;
    int USER_PROGRESS=2;

    String CLICKABLE="clickable";
    String NON_CLICKABLE="nonclickable";
    String TEXT="text";
    String TYPE="type";
    String TARGET="target";
    String ID="id";

    String KEY_TITLE="Title";
    String KEY_SUB_TITLE="SubTitle";
    String KEY_DESC="Description";
    String KEY_THER_DIAG_NAME="TherapyOrDiagnosisName";


    String KEY_IS_FROM_THEAPY_TAB="isFromTherapyTab";
    String THERAPY_TYPE="TherapyType";

    interface GSLTargetAction {
        String GSL_TARGET_ACTION_ADD_PAIN = "addPain";
        String GSL_TARGET_ACTION_LOG_PAIN = "logPain";
        String GSL_TARGET_ACTION_ADD_NOTE = "addNote";
        String GSL_TARGET_ACTION_PAIN_AREA = "painArea";
        String GSL_TARGET_ACTION_PAIN_SYMPTOMS = "Symptoms";
        String GSL_TARGET_ACTION_PAIN_CHARACTERISTICS = "Characterstics";
        String GSL_TARGET_ACTION_PAIN_AGG_FACTORS = "Aggravating";
        String GSL_TARGET_ACTION_PAIN_ALL_FACTORS = "Alleviating";
        String GSL_TARGET_ACTION_PAIN_TIMINGS = "Timings";
        String GSL_TARGET_ACTION_DIAGNOSIS = "diagnosisList";
    }

    interface GSLTargetActionType {
        String GSL_TARGET_ACTION_TYPE_UI = "UI";
        String GSL_TARGET_ACTION_CHAT_UI= "chatUI";
    }

    interface DiagnosisCategory {
        String CATEGORY_POSSIBLE = "Possible";
        String CATEGORY_LIKELY= "Likely";
    }

    interface ThearapyCategory {
        int CAT_CONVENTIONAL = 0;
        int CAT_ALTERNATIVE= 1;
        int CAT_PHYSICAL= 2;

        String CAT_CONVENTIONAL_CODE = "CONV";
        String CAT_ALTERNATIVE_CODE = "ALT";
        String CAT_PHYSICAL_CODE = "PHY";
    }

    interface WellBeingType {
        int TYPE_PHYSICAL_ID = 2;
        int TYPE_MENTAL_ID= 1;
    }

    interface Classification_Physical {
        String INDEPENDENCE = "Physical Independence";
        String COMFORT = "Physical Comfort";
        String PAIN = "Pain";
    }

    interface Classification_Mental {
        String EMOTIONAL_STATE = "Emotional State";
        String PSY_SUPPORT = "Psychological Support";
    }

    interface Medical_profile {
        int ALLERGIES = 1;
        int EXISTING_CONDITION = 2;
        int MEDICATIONS = 3;
        int SUPPLEMENT_VITAMIN = 4;
        int MY_PHYSICIAN=5;
        int PERSONAL_INFORMATION = 6;

    }


}
