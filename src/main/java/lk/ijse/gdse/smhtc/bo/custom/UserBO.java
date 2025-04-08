package lk.ijse.gdse.smhtc.bo.custom;

import java.util.Optional;

public interface UserBO {
    public Optional<String> checklogin(String userName, String password) ;
}
