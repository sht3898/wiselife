package com.ssafy.wiselife.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class EntityMapperImpl implements EntityMapper {
	
	@Autowired
	private ModelMapper modelMapper;

	
	@Override
	public <D, E> D convertToDomain(E source, Class<? extends D> classLiteral) {
		return modelMapper.map(source, classLiteral);
	}

}
