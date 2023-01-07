package si.rso.skupina10.converters;

import si.rso.skupina10.dtos.UserDto;
import si.rso.skupina10.entities.UserEntity;

public class UserConverter {

    public static UserDto toDto(UserEntity entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getUserId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        dto.setLat(entity.getLat());
        dto.setLng(entity.getLng());
        return dto;
    }

    public static UserEntity toEntity(UserDto dto) {
        UserEntity entity = new UserEntity();

        entity.setUserId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        entity.setLat(dto.getLat());
        entity.setLng(dto.getLng());
        return entity;
    }
}
