package com.cxq.springweb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@WebFilter(urlPatterns = {"/*"})
@Order(value = 1)
public class WebAnnotationFilter implements Filter{
	
	private static Logger logger = LoggerFactory.getLogger(WebAnnotationFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) srequest;
		logger.info("请求url为：{}", request.getRequestURL());
		chain.doFilter(srequest, sresponse);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	
}
