package sajeevan.task_track.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sajeevan.task_track.Entity.User;
import sajeevan.task_track.Entity.UserPrinciple;
import sajeevan.task_track.Repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            System.out.println(
                    "User Name not persent in the database_______________________________________________________");
            throw new UsernameNotFoundException(
                    "User not found ________________________________________________________________________________");
        }
        return new UserPrinciple(user);
    }
}
