package com.pada.safe_sphere.service;

import com.pada.safe_sphere.commons.AppConstants;
import com.pada.safe_sphere.entity.ReportAbuse;
import com.pada.safe_sphere.repository.CounsellingRepository;
import com.pada.safe_sphere.repository.UserSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CounsellingChatService {

    private final CounsellingRepository counsellingRepository;
    private final SessionManager userSessionService;

    public String processLoanApplication(String userId, String input) {
        UserSession session = userSessionService.getSession(userId);

        if (session == null || !AppConstants.REPORT_ABUSE.equals(session.getCurrentFeature())) {
            session = new UserSession();
            session.setUserId(userId);
            session.setStep(1);
            session.setReportAbuse(new ReportAbuse());
            session.setCurrentFeature(AppConstants.REPORT_ABUSE);
            userSessionService.saveSession(userId, session);
            return "Welcome! Please enter your National ID to begin the loan application.";
        }

        ReportAbuse reportAbuse = session.getReportAbuse();

        switch (session.getStep()) {
            case 1:
                reportAbuse.setNationalId(input);
                session.setStep(2);
                userSessionService.saveSession(userId, session);
                return "Please enter your full name.";
            case 2:
                reportAbuse.setApplicantEmail(input);
                session.setStep(4);
                userSessionService.saveSession(userId, session);
                return "Please enter your age:";
            case 3:
                reportAbuse.setApplicantName(input);
                session.setStep(3);
                userSessionService.saveSession(userId, session);
                return "Please enter your email address.";
            case 4:
                reportAbuse.setApplicantEmail(input);
                session.setStep(4);
                userSessionService.saveSession(userId, session);
                return "Please enter your faculty:";
            case 5:
                reportAbuse.setApplicantEmail(input);
                session.setStep(4);
                userSessionService.saveSession(userId, session);
                return "Please enter description:";

            case 6:
                    counsellingRepository.save(reportAbuse);
                    userSessionService.clearSession(userId);
                    return "Your report has been logged!";

            default:
                return "Invalid step. Please start again.";
        }
    }
}
