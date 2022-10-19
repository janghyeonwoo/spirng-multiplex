package com.example.multiplex.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResIconDeliveryInfo {
    private ResIconDeliInfo info;
    private ResIconDeliItemInfo items;

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResIconDeliInfo {

        private String barcode;

        private String branchStart;

        private String branchEnd;

        private Integer cnt;

        private String pdDt;

        private String prod;

        private String reName;

        private String sendName;

        private String recDt;
        private String relation;

    }

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResIconDeliItemInfo {
        private List<ResDeliStatusInfo> listData;
    }


    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResDeliStatusInfo {

        private String regDate;

        private String location;

        private String scStat;

        private String scNm;

        private String tel;

        private String stat;
    }
}
