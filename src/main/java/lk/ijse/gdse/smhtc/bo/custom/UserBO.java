package lk.ijse.gdse.smhtc.bo.custom;

import lk.ijse.gdse.smhtc.dto.UserDTO;
import lk.ijse.gdse.smhtc.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserBO {
    public String getNextUserId();
    public boolean saveUser(UserDTO userDTO);
    public boolean updateUser(UserDTO userDTO);
    public boolean deleteUser(String pk);
    public List<UserDTO> getAllUser();
    public Optional<UserDTO> findByUserId(String pk);
    public Optional<String> getLastUserPK();

    public Optional<String> checklogin(String userName, String password) ;
}
