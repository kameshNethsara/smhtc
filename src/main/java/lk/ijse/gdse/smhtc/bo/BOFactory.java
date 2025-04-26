package lk.ijse.gdse.smhtc.bo;

import lk.ijse.gdse.smhtc.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getInstance() {
        return (boFactory == null) ? (boFactory = new BOFactory()) : boFactory;
    }

    public enum BOType {
        PATIENT,
        PATIENT_PROGRAM,
        PAYMENT,
        THERAPIST,
        THERAPIST_AVAILABILITY,
        THERAPIST_PROGRAM,
        THERAPY_PROGRAM,
        THERAPY_SESSION,
        USER
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case PATIENT:
                return new PatientBOImpl();
            case PATIENT_PROGRAM:
                return new PatientProgrammeBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case THERAPIST:
                return new TherapistBOImpl();
            case THERAPIST_AVAILABILITY:
                return new TherapistAvailabiltyBOImpl();
            case THERAPIST_PROGRAM:
                return new TherapistProgramBOImpl();
            case THERAPY_PROGRAM:
                return new TherapyProgrammeBOImpl();
            case THERAPY_SESSION:
                return new TherapySessionBOImpl();
            case USER:
                return new UserBOImpl();
            default:
                return null;
        }
    }
}
