package lk.ijse.gdse.smhtc.bo.custom.impl;

import lk.ijse.gdse.smhtc.bo.custom.UserBO;
import lk.ijse.gdse.smhtc.config.FactoryConfiguration;
import lk.ijse.gdse.smhtc.dao.custom.UserDAO;
import lk.ijse.gdse.smhtc.dao.custom.impl.UserDAOImpl;
import lk.ijse.gdse.smhtc.dto.UserDTO;
import lk.ijse.gdse.smhtc.entity.User;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = new UserDAOImpl();

    @Override
    public String getNextUserId() {
        return userDAO.getNextId();
    }

    @Override
    public boolean saveUser(UserDTO userDTO) {
        // Encrypt the password
        //String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());

        User user = new User(
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getRole(),
                userDTO.getEmail()
        );
        return userDAO.save(user);
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        User user = new User(
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getRole(),
                userDTO.getEmail()
        );
        return userDAO.update(user);
    }

    @Override
    public boolean deleteUser(String pk) {
        return userDAO.delete(pk);
    }

//    @Override
//    public List<UserDTO> getAll() {
//        List<User> userList = userDAO.getAll(); // DAO එකෙන් entity list එක ගන්නවා.
//        List<UserDTO> dtoList = new ArrayList<>(); // Empty DTO list එකක් initialize කරනවා.
//
//        for (User user : userList) {
//            UserDTO dto = new UserDTO(
//                    user.getId(),
//                    user.getUsername(),
//                    user.getPassword(),
//                    user.getRole(),
//                    user.getEmail()
//            );
//            dtoList.add(dto); // Mapping කරපු DTO එක list එකට add කරනවා.
//        }
//
//        return dtoList; // Mapping කරපු List එක return වෙනවා.
//    }

    @Override
    public List<UserDTO> getAllUser() {
        // Fetch the list of User entities from the database using the userDAO
        List<User> userList = userDAO.getAll();

        // Convert the list of User entities to a list of UserDTOs using stream()
        return userList.stream()
                // Map each User entity to a UserDTO
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getRole(),
                        user.getEmail()
                ))
                // Collect the mapped UserDTO objects into a list and return it
                .toList();
    }


//    @Override
//    public Optional<UserDTO> findById(String pk) {
//        Optional<User> user = userDAO.findById(pk); // userDAO එකෙන් user get කරනවා
//
//        if (user.isPresent()) { // If the user exists
//            User u = user.get(); // Get the User object from Optional
//            UserDTO userDTO = new UserDTO( // Convert it to UserDTO
//                    u.getId(),
//                    u.getUsername(),
//                    u.getPassword(),
//                    u.getRole(),
//                    u.getEmail()
//            );
//            return Optional.of(userDTO); // Wrap the DTO in an Optional and return
//        } else {
//            return Optional.empty(); // If user not found, return empty Optional
//        }
//    }

    @Override
    public Optional<UserDTO> findByUserId(String pk) {
        // Fetch a User entity from the database using the userDAO and primary key (pk)
        Optional<User> user = userDAO.findById(pk);

        // If the User entity is present, map it to a UserDTO and return it inside an Optional
        return user.map(u -> new UserDTO(
                u.getId(),
                u.getUsername(),
                u.getPassword(),
                u.getRole(),
                u.getEmail()
        ));
    }

    @Override
    public boolean updateUserPassword(String username, String newPassword) {
        return userDAO.updatePasswordByUsername(username, newPassword);
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        return userDAO.findByName(username)
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getRole(),
                        user.getEmail()
                ));
    }



    @Override
    public Optional<String> getLastUserPK() {
        return userDAO.getLastPK();
    }

    @Override
    public Optional<String> checklogin(String userName, String password) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Optional<String> role = Optional.empty();

        try {
            session.beginTransaction();

            role = userDAO.checkLogin(userName, password);

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) session.getTransaction().rollback();
            //e.printStackTrace();
            throw new RuntimeException("Login failed at BO layer", e); // pass to controller
        } finally {
            session.close();
        }

        return role;
    }
    



}
