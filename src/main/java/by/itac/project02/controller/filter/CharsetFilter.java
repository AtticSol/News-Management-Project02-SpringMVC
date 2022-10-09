package by.itac.project02.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.FilterConfig;
//import jakarta.servlet.ServletContext;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;

public class CharsetFilter implements Filter{
	
	private String encoding;
	private ServletContext context;
	private static final String CHARACTER_ENCODING = "characterEncoding";
	
	public void init(FilterConfig fConfig) throws ServletException{
		encoding = fConfig.getInitParameter(CHARACTER_ENCODING);
		context = fConfig.getServletContext();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		
		context.log("Charset was set");
		
		chain.doFilter(request, response);
	}
	
	public void destroy() {}

}
