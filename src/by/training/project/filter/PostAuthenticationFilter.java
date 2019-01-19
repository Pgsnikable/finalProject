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
import java.util.Objects;

@WebFilter(urlPatterns = {"/jsp/login.jsp", "/jsp/registration.jsp", "/index.jsp"})
public class PostAuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if (!isUserAuthorized(httpServletRequest)){
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            UserWithoutPassword user = (UserWithoutPassword) httpServletRequest.getSession().getAttribute("user");
            String pageURI;
            switch (user.getRole()){
                case USER:
                    pageURI = PageManager.getPageURI(PageMappingConstant.USER_MAIN_PAGE_KEY);
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + pageURI);
                    break;
                case ADMIN:
                    pageURI = PageManager.getPageURI(PageMappingConstant.ADMIN_MAIN_PAGE_KEY);
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + pageURI);
                    break;
                case DOCTOR:
                    pageURI = PageManager.getPageURI(PageMappingConstant.DOCTOR_MAIN_PAGE_KEY);
                    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + pageURI);
                    break;
                default:
                    throw new EnumConstantNotPresentException(UserRole.class, user.getRole().name());
            }
        }
    }

    private boolean isUserAuthorized(HttpServletRequest httpServletRequest) {
        UserWithoutPassword user = (UserWithoutPassword) httpServletRequest.getSession().getAttribute("user");
        return Objects.nonNull(user);
    }


//    private boolean isRegistrationPage(HttpServletRequest httpServletRequest) {
//        String requestURI = httpServletRequest.getRequestURI();
//        String pageURI = PageManager.getPageURI(PageMappingConstant.REGISTRATION_PAGE_KEY);
//        return pageURI.equals(requestURI);
//    }
//
//    private boolean isLoginPage(HttpServletRequest httpServletRequest) {
//        String requestURI = httpServletRequest.getRequestURI();
//        String pageURI = PageManager.getPageURI(PageMappingConstant.LOGIN_PAGE_KEY);
//        return pageURI.equals(requestURI);
//    }
//
//    private void sendAtLoginPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
//        String loginPageURI = PageManager.getPageURI(PageMappingConstant.LOGIN_PAGE_KEY);
//        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + loginPageURI);
//    }


}
