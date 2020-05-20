package com.ssafy.wiselife.mapper;


public interface EntityMapper {
	public <D,E> D convertToDomain(E source,Class<? extends D> classLiteral);
}
