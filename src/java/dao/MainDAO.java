
package dao;

import model.User;

public interface MainDAO {
    
    public int createUser(User user);
    public int updateUser(User user);
    public int authenticate(User user);
    
}
