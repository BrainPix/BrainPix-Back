package com.brainpix.security.authorization;

import java.lang.annotation.Annotation;
import java.util.List;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.brainpix.api.code.error.AuthorityErrorCode;
import com.brainpix.api.exception.BrainPixException;
import com.brainpix.security.authority.BrainpixAuthority;
import com.brainpix.security.contextholder.GetAuthenticationInfo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class AbstractAuthInterceptor<A extends Annotation, Y extends BrainpixAuthority> implements
	HandlerInterceptor {

	private final Class<A> checkAnnotation;
	private final List<Y> checkAuthority;

	protected AbstractAuthInterceptor(Class<A> checkAnnotation, List<Y> checkAuthority) {
		this.checkAnnotation = checkAnnotation;
		this.checkAuthority = checkAuthority;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
		Object handler) throws Exception {

		// 해당 함수가 컨트롤러 메소드가 아닌 경우 다음 인터셉터로 넘어감
		HandlerMethod handlerMethod = checkHandlerMethod(handler);
		if (handlerMethod == null) {
			return true;
		}

		// 해당 함수에 어노테이션이 없는 경우 다음 인터셉터로 넘어감
		if (checkAnnotation(handlerMethod) == null) {
			return true;
		}

		// 권한 확인
		checkAuthority();
		return true;
	}

	private HandlerMethod checkHandlerMethod(Object handler) {
		if (!(handler instanceof HandlerMethod handlerMethod)) {
			return null;
		}
		return handlerMethod;
	}

	private A checkAnnotation(HandlerMethod handlerMethod) {
		return handlerMethod.getMethodAnnotation(checkAnnotation);
	}

	private void checkAuthority() {
		for (BrainpixAuthority authority : checkAuthority) {
			if (GetAuthenticationInfo.getAuthority() == authority) {
				return;
			}
		}
		throw new BrainPixException(AuthorityErrorCode.AUTHORITY_ERROR_CODE);
	}
}

