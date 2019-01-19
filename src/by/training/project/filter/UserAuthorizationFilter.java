package by.training.project.filter;

import by.training.project.entity.UserRole;
import by.training.project.entity.UserWithoutPassword;
import by.training.project.manager.PageManager;
import by.training.project.manager.PageMappingConstant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/jsp/user/*")
public class UserAuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if (isUserAuthorized(httpServletRequest)){
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            sendAtLoginPage(httpServletRequest, httpServletResponse);
        }
    }

    private void sendAtLoginPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String loginPageURI = PageManager.getPageURI(PageMappingConstant.LOGIN_PAGE_KEY);
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + loginPageURI);
    }

    private boolean isUserAuthorized(HttpServletRequest httpServletRequest) {
        boolean result = false;
        UserWithoutPassword user = (UserWithoutPassword) httpServletRequest.getSession().getAttribute("user");
        if (user != null && user.getRole() == UserRole.USER){
            result = true;
        }
        return result;
    }
}
