package no.bachelorgroup13.backend.features.user.mapper;

import no.bachelorgroup13.backend.features.user.dto.UserDto;
import no.bachelorgroup13.backend.features.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setLicensePlate(user.getLicensePlate());
        dto.setSecondLicensePlate(user.getSecondLicensePlate());
        dto.setRole(user.getRole());
        return dto;
    }

    public User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setLicensePlate(dto.getLicensePlate());
        user.setSecondLicensePlate(dto.getSecondLicensePlate());
        user.setRole(dto.getRole());
        return user;
    }
}
