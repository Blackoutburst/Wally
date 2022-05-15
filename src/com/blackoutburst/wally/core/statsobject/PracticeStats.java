package com.blackoutburst.wally.core.statsobject;

import com.blackoutburst.wally.utils.APIUtils;

public class PracticeStats {

    public final String uuid;
    public final String name;
    public final int qualification;
    public final int finals;
    public final int lobby;
    public final int wideQualification;
    public final int wideFinals;
    public final int total;

    public PracticeStats(String data) {
        uuid = APIUtils.getValuesFromAPI(data, "uuid", "unknown");
        name = APIUtils.getValuesFromAPI(data,"name", "Unknown");
        qualification = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.qualification", "0"));
        finals = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.finals", "0"));
        lobby = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.lobby", "0"));
        wideQualification = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.wide_qualification", "0"));
        wideFinals = Integer.parseInt(APIUtils.getValuesFromAPI(data,"scores.wide_finals", "0"));
        total = qualification + finals + lobby + wideQualification + wideFinals;
    }

}
