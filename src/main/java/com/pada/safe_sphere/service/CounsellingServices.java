package com.pada.safe_sphere.service;

import com.pada.safe_sphere.commons.AppConstants;
import com.pada.safe_sphere.repository.UserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CounsellingServices {

    private final SessionManager sessionManager;
    private final CounsellingChatService counsellingChatService;

    // Main method that processes user input
    public String processChat(String userId, String input) {
        UserSession session = sessionManager.getSession(userId);

        // If no session exists, show menu
        if (session == null) {
            return showMenu(userId); // Show main menu if no session
        }

        // If the user has selected a feature (LOAN_APPLICATION or LOAN_INQUIRY), delegate processing
        switch (session.getCurrentFeature()) {
            case AppConstants.REPORT_ABUSE:
                return counsellingChatService.processLoanApplication(userId, input);
            case AppConstants.MENU:
                return processMenuSelection(userId, input); // Handle menu selection
            default:
                return "Invalid session state. Please start again.";
        }
    }

    // Show the main menu with available options
    private String showMenu(String userId) {
        UserSession session = new UserSession();
        session.setUserId(userId);
        session.setStep(1);  // Initial step for menu
        session.setCurrentFeature(AppConstants.MENU);  // Set to menu feature
        sessionManager.saveSession(userId, session);

        // Return the main menu options
        return "Welcome! Please choose an option:\n1. Online Counselling \n2. Report Abuse\nPlease type '1' or '2'.";
    }

    // Process the user selection from the menu
    public String processMenuSelection(String userId, String input) {
        UserSession session = sessionManager.getSession(userId);

        if (session == null || !AppConstants.MENU.equals(session.getCurrentFeature())) {
            return "Session expired. Please start over.";
        }

        // Handle user input for menu selection
        switch (input) {
            case "1":
                session.setCurrentFeature(AppConstants.REPORT_ABUSE);  // Set to Loan Application
                session.setStep(1);  // Reset to step 1 for loan application
                sessionManager.saveSession(userId, session);
                return counsellingChatService.processLoanApplication(userId, input); // Start loan application
            default:
                return "Invalid selection. Please type '1' for Report Abuse  or '2' for Online Counselling.";
        }
    }
}
