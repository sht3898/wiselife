package com.ssafy.wiselife.service;

import java.util.HashMap;

public interface IKakaoService {
	String getAccessToken(String authorize_code);

	HashMap<String, Object> getUserInfo(String access_Token);

	void profile(String access_Token);

	void deleteUserInfo(String access_token);

}
