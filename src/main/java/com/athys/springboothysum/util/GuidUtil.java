package com.athys.springboothysum.util;

import java.util.UUID;

public class GuidUtil {

    public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }
}
