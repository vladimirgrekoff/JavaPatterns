package com.grekoff.market.auth.services;


import com.grekoff.market.api.auth.UserDto;
import com.grekoff.market.api.auth.UserRegistrationDto;
import com.grekoff.market.auth.converters.RoleConverter;
import com.grekoff.market.auth.entities.User;
import com.grekoff.market.auth.exceptions.ResourceNotFoundException;
import com.grekoff.market.auth.exceptions.EmailExistsException;
import com.grekoff.market.auth.exceptions.UserAlreadyExistException;
import com.grekoff.market.auth.repositories.UsersRepository;
import com.grekoff.market.auth.repositories.RoleRepository;
import com.grekoff.market.auth.converters.UserConverter;
import com.grekoff.market.auth.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final UserConverter userConverter;
    private final RoleConverter roleConverter;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<UserDto> findAll() {
        List<UserDto> userDtoList = new ArrayList<>();
        List<User> userList = usersRepository.findAll();
        for (User u: userList) {
//            if(u)
            UserDto userDto = userConverter.entityToDto(u);
            userDtoList.add(userDto);
        }

        return userDtoList;
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(usersRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Пользователь отсутствует в списке")));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public Optional<User> findById(Long id) {
        return usersRepository.findById(id);
    }

    @Transactional
    public User registerNewUserAccount(UserRegistrationDto userRegistrationDto) throws EmailExistsException, UserAlreadyExistException {
        if (userNameExist(userRegistrationDto.getUsername())){
            throw new UserAlreadyExistException("Пользователь с таким именем уже существует");
        }
        if (emailExists(userRegistrationDto.getEmail())) {
            throw new EmailExistsException("Существует учетная запись с этим адресом электронной почты: " + userRegistrationDto.getEmail());
        }
        User user = new User();
        user.setFirstname(userRegistrationDto.getFirstname());
        user.setLastname(userRegistrationDto.getLastname());
        user.setUsername(userRegistrationDto.getUsername());

        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));///>---PASSWORD ENCODE

        user.setEmail(userRegistrationDto.getEmail());
        Role role = new Role();
        role.setId(roleRepository.findByName("ROLE_USER").get().getId());
        role.setName("ROLE_USER");
        user.setRoles(List.of(role));
        return usersRepository.save(user);
    }

    private boolean emailExists(String email) {
        return usersRepository.findByEmail(email).isPresent();
    }

    private boolean userNameExist(String username) {
        return usersRepository.findByUsername(username).isPresent();
    }

    @Transactional
    public User save(User user) throws UserAlreadyExistException, EmailExistsException {
        if (userNameExist(user.getUsername())) {
            throw new UserAlreadyExistException("Пользователь с таким именем уже существует");
        }
        if (emailExists(user.getEmail())) {
            throw new EmailExistsException("Существует учетная запись с этим адресом электронной почты");
        }
        List<Role> roles = user.getRoles().stream().toList();
        for(Role role : roles){
            if(role.getId() == null) {
                role.setId(roleRepository.findByName(role.getName()).get().getId());
            }
        }
        user.setRoles(roles);
        if(!(user.getPassword().equals("PROTECTED"))) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return usersRepository.save(user);
    }

    @Transactional
    public User update(UserDto userDto) {
        User user = usersRepository.findById(userDto.getId()).orElseThrow(()-> new ResourceNotFoundException("Пользователь отсутствует в списке, id: " + userDto.getId()));
        user.setUsername(userDto.getUsername());
        if(!(userDto.getPassword().equals("PROTECTED"))) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        user.setEmail(userDto.getEmail());
        user.setRoles(userDto.getRoles().stream().map(roleConverter::dtoToEntity).collect(Collectors.toList()));
        return usersRepository.save(user);
    }

    public void deleteById(Long id) {
        usersRepository.deleteById(id);
    }
}
