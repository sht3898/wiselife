package com.ssafy.wiselife.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.wiselife.domain.Category;
import com.ssafy.wiselife.domain.InterestCategory;
import com.ssafy.wiselife.domain.Survey;
import com.ssafy.wiselife.domain.User;
import com.ssafy.wiselife.dto.AreaDTO;
import com.ssafy.wiselife.dto.SurveyDTO;
import com.ssafy.wiselife.dto.UserDTO;
import com.ssafy.wiselife.mapper.EntityMapper;
import com.ssafy.wiselife.repository.AreaRepository;
import com.ssafy.wiselife.repository.CategoryRepository;
import com.ssafy.wiselife.repository.InterestCategoryRepository;
import com.ssafy.wiselife.repository.SurveyRepository;
import com.ssafy.wiselife.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userrepo;

	@Autowired
	private SurveyRepository surveyrepo;

	@Autowired
	private AreaRepository arearepo;

	@Autowired
	private CategoryRepository categoryrepo;

	@Autowired
	private InterestCategoryRepository interestcategoryrepo;

	@Autowired
	private ModelMapper modelMapper; // DTO를 Entity타입으로 mapping할때 사용

	@Autowired
	private EntityMapper entityMapper; // Entity를 DTO타입으로 mapping할때 사용

	private String IP = "http://localhost:8080";

	// uid, username, profile_image, is_inst, gender, year, ages, area1, area2
	@Override
	public void signUp(List<Integer> interest_category, UserDTO user) {
		User userentity = null;
		try {
			userentity = modelMapper.map(user, User.class);// user(UserDTO)를 User(Entity)타입으로 매핑시킨다
		} catch (Exception e) {
			System.out.println("message:" + e.getMessage());
		}

		userrepo.save(userentity);

		InterestCategory interestcategoryentity = new InterestCategory();

		interestcategoryentity.setUser(userentity);

		try {
			for (int i = 0; i < interest_category.size(); i++) {

//				System.out.println("관심카테고리 번호: " + interest_category.get(i));

				Category category = categoryrepo.findBycategoryId(interest_category.get(i));

//				System.out.println(category.toString());

				interestcategoryentity.setCategory(category);

				interestcategoryrepo.save(interestcategoryentity);
			}

		} catch (Exception e) {
			e.printStackTrace();

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
	public SurveyDTO survey(SurveyDTO survey, long uid) {
		// TODO Auto-generated method stub
		Survey surveyentity = modelMapper.map(survey, Survey.class);// user(UserDTO)를 User(Entity)타입으로 매핑시킨다

		User user = userrepo.findByUid(uid);

		surveyentity.setUser(user);

		try {
			surveyrepo.save(surveyentity);
			return survey;
		} catch (Exception e) {
			return survey;
		}
	}

	@Override
	public List<String> area(String area) {
		// TODO Auto-generated method stub
		List<AreaDTO> arealist = arearepo.findByfirstArea(area).stream()
				.map(e -> entityMapper.convertToDomain(e, AreaDTO.class)).collect(Collectors.toList());

		List<String> secondarea = new ArrayList<>();
		for (int i = 0; i < arealist.size(); i++) {
			secondarea.add(arealist.get(i).getSecondArea().toString());
		}

		return secondarea;
	}

	@Override
	public Map<String, Object> getUserInfo(long uid) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		User user = userrepo.findByUid(uid);
		
		List<InterestCategory> interest_category = interestcategoryrepo.findByUser(user);
		List<Category> category = new ArrayList<Category>();
		for (int i = 0; i < interest_category.size(); i++) {
			category.add(categoryrepo.findBycategoryId(interest_category.get(i).getCategory().getCategoryId()));
		}
		
		Survey survey = surveyrepo.findByUser(user);
		
		resultMap.put("userinfo", user);
		resultMap.put("interest_category", category);
		resultMap.put("survey",survey);
		
		return resultMap;
	}

}
