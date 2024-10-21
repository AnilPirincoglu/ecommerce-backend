package dev.anilp.ecommerce_backend.mapper;

import dev.anilp.ecommerce_backend.dto.user.CreateUserRequestDTO;
import dev.anilp.ecommerce_backend.dto.user.UpdateUserRequestDTO;
import dev.anilp.ecommerce_backend.dto.user.UserResponseDTO;
import dev.anilp.ecommerce_backend.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO userToResponse(User user);

    List<UserResponseDTO> usersToResponsesList(List<User> users);

    User requestToUser(CreateUserRequestDTO createUser);

    void updateUserFromDto(@MappingTarget User user, UpdateUserRequestDTO updateRequest);

}