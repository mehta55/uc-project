package com.uc.common.interceptor;

import static com.uc.common.Constants.AUTHORIZATION;
import static com.uc.common.Constants.APIKEY_HEADER;

import static com.uc.common.Constants.Regex.VALID_ATTRIBUTE_REGEX;
import static com.uc.common.Constants.X_;


import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.uc.common.config.CommonFileProperties;
import com.uc.common.exception.AuthenticationError;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class AuthInterceptor implements HandlerInterceptor {
	
	public static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	@Autowired
	private CommonFileProperties commonFileProps;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(!commonFileProps.isEnableAuth()) {
			return true;
		}

		String requestURL = request.getRequestURI();
		String apiKey = request.getHeader(APIKEY_HEADER);
		
		// for inter-service calls
		if(Objects.nonNull(apiKey) && apiKey.equals(commonFileProps.getApiKey())) {
			logger.info("AuthInterceptor.preHandle(): bypassing internal call");
			return true;
		}
		
		String requestMethod = request.getMethod().toUpperCase();
		if (!commonFileProps.getWhiteListedURLs().contains(requestMethod + ":" + requestURL)) {
			String token = request.getHeader(AUTHORIZATION);
			boolean authenticated = false;
			try {
				Claims claims = null;
				if (!ObjectUtils.isEmpty(token)) {

					claims = Jwts.parser()
							.setSigningKey(DatatypeConverter.parseBase64Binary(commonFileProps.getJwtSecret()))
							.parseClaimsJws(token).getBody();
					authenticated = !claims.getExpiration().before(new Date());
					
				}

				if (!authenticated) {
					throw new AuthenticationError();
				}

				claims.entrySet().stream().filter(claim -> claim.getKey().matches(VALID_ATTRIBUTE_REGEX)).forEach(claim -> {
					request.setAttribute(new StringBuilder().append(X_).append(claim.getKey()).toString(),
							claim.getValue());
				});
			} catch (Exception e) {
				logger.info("AuthInterceptor.preHandle() error", e);
				throw new AuthenticationError();
			}

		}
		return true;
	}
}
