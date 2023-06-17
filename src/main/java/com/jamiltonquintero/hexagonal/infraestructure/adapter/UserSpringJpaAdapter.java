package com.jamiltonquintero.hexagonal.infraestructure.adapter;

import com.jamiltonquintero.hexagonal.domain.model.User;
import com.jamiltonquintero.hexagonal.domain.model.constant.UserConstant;
import com.jamiltonquintero.hexagonal.domain.port.UserPersistencePort;
import com.jamiltonquintero.hexagonal.infraestructure.adapter.exception.UserException;
import com.jamiltonquintero.hexagonal.infraestructure.adapter.mapper.UserDboMapper;
import com.jamiltonquintero.hexagonal.infraestructure.adapter.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserSpringJpaAdapter implements UserPersistencePort {
    private final UserRepository userRepository;
    private final UserDboMapper userDboMapper;

    public UserSpringJpaAdapter(UserRepository userRepository, UserDboMapper userDboMapper) {
        this.userRepository = userRepository;
        this.userDboMapper = userDboMapper;
    }


    /**
     * aqui se usa AWS DynamoDB
     * @param user
     * @return
     */
    @Override
    public User create(User user) {

        var userToSave = userDboMapper.toDbo(user);
        var userSaved = userRepository.save(userToSave);

        return userDboMapper.toDomain(userSaved);
    }

    @Override
    public User getById(Long id) {

        var optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()){
            throw new UserException(HttpStatus.NOT_FOUND,
                    String.format(UserConstant.TASK_NOT_FOUND_MESSAGE_ERROR, id));
        }

        return userDboMapper.toDomain(optionalUser.get());
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userDboMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(User user) {

        var userToUpdate = userDboMapper.toDbo(user);
        var userUpdated = userRepository.save(userToUpdate);

        return userDboMapper.toDomain(userUpdated);
    }

}
