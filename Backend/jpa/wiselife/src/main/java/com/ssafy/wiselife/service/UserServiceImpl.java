package com.ssafy.wiselife.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.wiselife.domain.User;
import com.ssafy.wiselife.dto.UserDTO;
import com.ssafy.wiselife.dto.UserDTO.UserLogin;
import com.ssafy.wiselife.mapper.EntityMapper;
import com.ssafy.wiselife.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private ModelMapper modelMapper; //DTO를 Entity타입으로 mapping할때 사용
	
	@Autowired
	private EntityMapper entityMapper; //Entity를 DTO타입으로 mapping할때 사용
	
	//uid, username, profile_image, is_inst, gender, year, ages, area1, area2
	@Override
	public int signUp(UserDTO user) {
		//현재 user 는 UserDTO타입이기 때문에 repository 즉 sql문 실행을 위한 mapping을 해줘야함
		User userentity = modelMapper.map(user, User.class);//user(UserDTO)를 User(Entity)타입으로 매핑시킨다
		
		try {
			userrepo.save(userentity); //지금은 response해줄 값이 없지만 만약 있을 때에는 처음 req받을때와 똑같이 DTO타입으로 mapping해주고 return해줘야한다.
			//mapping 예시
			/*
			 * List<ReviewDTO> reviewList = reviewrepo.findAllByLanguageId(language_id).stream()
				.map(e -> entityMapper.convertToDomain(e, ReviewDTO.class)).collect(Collectors.toList());
			 */
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}
}
