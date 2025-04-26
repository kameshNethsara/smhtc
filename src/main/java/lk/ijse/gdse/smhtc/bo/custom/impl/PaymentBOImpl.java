package lk.ijse.gdse.smhtc.bo.custom.impl;

import lk.ijse.gdse.smhtc.bo.custom.PatientProgrammeBO;
import lk.ijse.gdse.smhtc.bo.custom.PaymentBO;
import lk.ijse.gdse.smhtc.dao.DAOFactory;
import lk.ijse.gdse.smhtc.dao.custom.*;
import lk.ijse.gdse.smhtc.dto.PaymentDTO;
import lk.ijse.gdse.smhtc.entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENT);
    PatientDAO patientDAO = (PatientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PATIENT);
    TherapyProgrammeDAO therapyProgramDAO = (TherapyProgrammeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPY_PROGRAM);
    TherapySessionDAO therapySessionDAO = (TherapySessionDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPY_SESSION);
    PatientProgramDAO patientPDAO = (PatientProgramDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PATIENT_PROGRAM);
    @Override
    public String getNextPaymentId() {
        return paymentDAO.getNextId();
    }

    @Override
    public boolean save(PaymentDTO paymentDTO) {
        Patient patient = patientDAO.findById(paymentDTO.getPatientId()).orElse(null);
        TherapyProgram program = therapyProgramDAO.findById(paymentDTO.getTherapyProgramId()).orElse(null);

        TherapySession session = null;

        if (paymentDTO.getTherapySessionId() != null){
            session = therapySessionDAO.findById(paymentDTO.getTherapySessionId()).orElse(null);
        }
        Payment entity = new Payment(
                paymentDTO.getPaymentId(),
                patient,
                program,
                session,
                paymentDTO.getAmount(),
                paymentDTO.getPaymentType(),
                paymentDTO.getPaymentDate()
        );

//                private String patientId;
//                private String therapyProgramId;
//                private String therapySessionId; // Nullable for upfront payments
//                private BigDecimal amount;
//                private String paymentType;
//                private LocalDate paymentDate;

//            private String patientId;
//            private String programId;
//            private String paymentId;
//            private BigDecimal fee;
//            private BigDecimal balance;
//            private String registrationDate;

        if(paymentDAO.save(entity)){
            Optional<PatientProgram> patientProgram = patientPDAO.findById(
                    new PatientProgramId(
                            paymentDTO.getPatientId(),
                            paymentDTO.getTherapyProgramId()
                    )
            );
            PatientProgram pp = patientProgram.get();
            boolean isPPUpdate = patientPDAO.update(
                    new PatientProgram(
                            pp.getId(),
                            pp.getBalance().subtract(paymentDTO.getAmount()),
                            pp.getPatient(),
                            pp.getTherapyProgram(),
                            pp.getRegistrationDate(),
                            pp.getPayment()

                    )
            );
            if (isPPUpdate){
                return true;

            }else {
              return false;
            }

        } else {
            return false;
        }
    }

    @Override
    public boolean update(PaymentDTO paymentDTO) {
        Optional<Payment> optionalOldPayment = paymentDAO.findById(paymentDTO.getPaymentId());

        if (optionalOldPayment.isPresent()) {
            Payment oldPayment = optionalOldPayment.get();

            Patient patient = patientDAO.findById(paymentDTO.getPatientId()).orElse(null);
            TherapyProgram program = therapyProgramDAO.findById(paymentDTO.getTherapyProgramId()).orElse(null);
            TherapySession session = null;

            if (paymentDTO.getTherapySessionId() != null) {
                session = therapySessionDAO.findById(paymentDTO.getTherapySessionId()).orElse(null);
            }

            Payment newPayment = new Payment(
                    paymentDTO.getPaymentId(),
                    patient,
                    program,
                    session,
                    paymentDTO.getAmount(),
                    paymentDTO.getPaymentType(),
                    paymentDTO.getPaymentDate()
            );

            // Balance update
            String patientId = paymentDTO.getPatientId();
            String programId = paymentDTO.getTherapyProgramId();

            Optional<PatientProgram> optionalPP = patientPDAO.findById(
                    new PatientProgramId(patientId, programId)
            );

            if (optionalPP.isPresent()) {
                PatientProgram pp = optionalPP.get();

                BigDecimal balanceAdjustment = oldPayment.getAmount().subtract(paymentDTO.getAmount());
                BigDecimal newBalance = pp.getBalance().add(balanceAdjustment);

                PatientProgram updatedPP = new PatientProgram(
                        pp.getId(),
                        newBalance,
                        pp.getPatient(),
                        pp.getTherapyProgram(),
                        pp.getRegistrationDate(),
                        pp.getPayment()
                );

                boolean isPPUpdated = patientPDAO.update(updatedPP);
                if (!isPPUpdated) return false;

                return paymentDAO.update(newPayment);
            }
        }

        return false;
    }


    @Override
    public boolean delete(String pk) {
        Optional<Payment> optionalPayment = paymentDAO.findById(pk);

        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();

            // Restore the balance in PatientProgram
            String patientId = payment.getPatient().getPatientId();
            String programId = payment.getTherapyProgram().getProgramId();

            Optional<PatientProgram> optionalPP = patientPDAO.findById(
                    new PatientProgramId(patientId, programId)
            );

            if (optionalPP.isPresent()) {
                PatientProgram pp = optionalPP.get();
                BigDecimal restoredBalance = pp.getBalance().add(payment.getAmount());

                PatientProgram updatedPP = new PatientProgram(
                        pp.getId(),
                        restoredBalance,
                        pp.getPatient(),
                        pp.getTherapyProgram(),
                        pp.getRegistrationDate(),
                        pp.getPayment()
                );

                boolean isPPUpdated = patientPDAO.update(updatedPP);
                if (!isPPUpdated) return false;

                // Now delete the payment
                return paymentDAO.delete(pk);
            }
        }
        return false;
    }

    @Override
    public List<PaymentDTO> getAll() {
        List<Payment> paymentList = paymentDAO.getAll();
        List<PaymentDTO> dtoList = new ArrayList<>();

        for (Payment payment : paymentList) {
            String patientId = payment.getPatient() != null ? payment.getPatient().getPatientId() : "N/A";
            String programId = payment.getTherapyProgram() != null ? payment.getTherapyProgram().getProgramId() : "N/A";
            String sessionId = payment.getTherapySession() != null ? payment.getTherapySession().getSessionId() : "N/A";

            dtoList.add(new PaymentDTO(
                    payment.getPaymentId(),
                    patientId,
                    programId,
                    sessionId,
                    payment.getAmount(),
                    payment.getPaymentType(),
                    payment.getPaymentDate()
            ));
        }
        return dtoList;
    }

    @Override
    public Optional<PaymentDTO> findById(String pk) {
        Optional<Payment> optionalPayment = paymentDAO.findById(pk);

        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            PaymentDTO dto = new PaymentDTO(
                    payment.getPaymentId(),
                    payment.getPatient() != null ? payment.getPatient().getPatientId() : null,
                    payment.getTherapyProgram() != null ? payment.getTherapyProgram().getProgramId() : null,
                    payment.getTherapySession() != null ? payment.getTherapySession().getSessionId() : null,
                    payment.getAmount(),
                    payment.getPaymentType(),
                    payment.getPaymentDate()
            );
            return Optional.of(dto);
        }
        return Optional.empty();
    }
}
