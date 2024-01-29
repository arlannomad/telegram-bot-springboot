package kz.almaty.telegrambotspringboot.mapper;

import kz.almaty.telegrambotspringboot.dto.AppUserDto;
import kz.almaty.telegrambotspringboot.model.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapStructUserMapper {
    MapStructUserMapper MAPPER = Mappers.getMapper(MapStructUserMapper.class);
    AppUserDto mapStrictUserToUserDto(AppUser appUser);
    AppUser mapStrictUserDtoToUser(AppUserDto appUserDto);
}
