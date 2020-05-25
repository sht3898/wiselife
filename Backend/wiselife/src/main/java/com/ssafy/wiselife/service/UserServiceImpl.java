 package com.ssafy.wiselife.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.wiselife.domain.Survey;
import com.ssafy.wiselife.domain.User;
import com.ssafy.wiselife.dto.SurveyDTO;
import com.ssafy.wiselife.dto.UserDTO;
import com.ssafy.wiselife.mapper.EntityMapper;
import com.ssafy.wiselife.repository.SurveyRepository;
import com.ssafy.wiselife.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private SurveyRepository surveyrepo;

	@Autowired
	private ModelMapper modelMapper; // DTO를 Entity타입으로 mapping할때 사용

	@Autowired
	private EntityMapper entityMapper; // Entity를 DTO타입으로 mapping할때 사용

	
	private String IP = "http://localhost:8080";

	// uid, username, profile_image, is_inst, gender, year, ages, area1, area2
	@Override
	public UserDTO signUp(UserDTO user) {
		System.out.println("들어옴?");
		User userentity = null;
		// 현재 user는 UserDTO타입이기 때문에 repository 즉 sql문 실행을 위한 mapping을 해줘야함
		try {
			userentity = modelMapper.map(user, User.class);// user(UserDTO)를 User(Entity)타입으로 매핑시킨다
		} catch (Exception e) {
			System.out.println("에러남");
		}

		try {
			System.out.println("엔티티:"+userentity.toString());
//			Survey newsurvey=new Survey();
//			userentity.setSurvey(newsurvey);
			
			userrepo.save(userentity); // 지금은 response해줄 값이 없지만 만약 있을 때에는 처음 req받을때와 똑같이 DTO타입으로 mapping해주고
										// return해줘야한다.
			User userEntity = userrepo.findById((long)1363992434).get();
			System.out.println("찾아봐:"+userEntity.toString());
			// mapping 예시
			/*
			 * List<ReviewDTO> reviewList =
			 * reviewrepo.findAllByLanguageId(language_id).stream() .map(e ->
			 * entityMapper.convertToDomain(e,
			 * ReviewDTO.class)).collect(Collectors.toList());
			 */
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return user;
			
		}
	}

	@Override
	public boolean uidDuplicateCheck(long uid) {
		// TODO Auto-generated method stub		
		int check = userrepo.countByuid(uid);
		if (check == 0)
			return false;
		else
			return true;
	}

	@Override
	public SurveyDTO survey(SurveyDTO survey) {
		// TODO Auto-generated method stub
		Survey surveyentity = modelMapper.map(survey, Survey.class);// user(UserDTO)를 User(Entity)타입으로 매핑시킨다

		try {
			surveyrepo.save(surveyentity); 
			return survey;
		} catch (Exception e) {
			return survey;
		}
	}
}
