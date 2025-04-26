package lk.ijse.gdse.smhtc.bo.custom;

import lk.ijse.gdse.smhtc.bo.SuperBO;
import lk.ijse.gdse.smhtc.dto.PaymentDTO;
import lk.ijse.gdse.smhtc.entity.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentBO extends SuperBO {
    public String getNextPaymentId();
    public boolean save(PaymentDTO paymentDTO);
    public boolean update(PaymentDTO paymentDTO);
    public boolean delete(String pk);
    public List<PaymentDTO> getAll();
    public Optional<PaymentDTO> findById(String pk);

}
