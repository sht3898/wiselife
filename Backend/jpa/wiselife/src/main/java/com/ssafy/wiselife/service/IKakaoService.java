package com.ssafy.wiselife.service;

import java.util.HashMap;

public interface IKakaoService {
	String getAccessToken(String authorize_code);

	HashMap<String, Object> getUserInfo(String access_Token);

	void secession(String access_Token);

	void profile(String access_Token);

}
