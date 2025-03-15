package com.pada.safe_sphere.model;

import com.pada.safe_sphere.entity.ReportAbuse;
import lombok.Data;


@Data
public class UserSession {

    private String userId; // WhatsApp number
    private String accountNumber; //Loan account number
    private int step; // Current step in the flow
    private ReportAbuse reportAbuse;
    private boolean authenticated;
    private String currentFeature; // LOAN_APPLICATION or LOAN_INQUIRY
    private String nationalId;
}
