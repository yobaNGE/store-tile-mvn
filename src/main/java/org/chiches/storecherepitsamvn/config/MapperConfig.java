package org.chiches.storecherepitsamvn.config;

import org.chiches.storecherepitsamvn.dto.OrderDTO;
import org.chiches.storecherepitsamvn.dto.OrderItemDTO;
import org.chiches.storecherepitsamvn.dto.TileDTO;
import org.chiches.storecherepitsamvn.dto.UserDTO;
import org.chiches.storecherepitsamvn.entity.OrderEntity;
import org.chiches.storecherepitsamvn.entity.OrderItemEntity;
import org.chiches.storecherepitsamvn.entity.TileEntity;
import org.chiches.storecherepitsamvn.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(TileEntity.class, TileDTO.class).addMappings(mapper ->
                mapper.map(TileEntity::getCategory, TileDTO::setTileCategoryDTO));
        modelMapper.typeMap(OrderItemEntity.class, OrderItemDTO.class)
                .addMapping(OrderItemEntity::getTile, OrderItemDTO::setTileDTO);
        modelMapper.typeMap(OrderEntity.class, OrderDTO.class).addMappings(mapper -> {
            mapper.map(OrderEntity::getUser, OrderDTO::setUserDTO);
        });
        modelMapper.typeMap(UserEntity.class, UserDTO.class).addMappings(mapper -> {
            mapper.map(UserEntity::getUsername, UserDTO::setUsername);
            mapper.map(UserEntity::getEmail, UserDTO::setEmail);
            mapper.map(UserEntity::getName, UserDTO::setName);
            mapper.map(UserEntity::getSurname, UserDTO::setSurname);
        });
        return modelMapper;
    }
}