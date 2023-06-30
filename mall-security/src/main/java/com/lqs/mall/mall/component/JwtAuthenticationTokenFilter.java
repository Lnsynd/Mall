package com.lqs.mall.mall.component;

import com.lqs.mall.mall.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT登录授权过滤器
 * Created by 刘千山 on 2023/6/30/030-10:07
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取authHeader 认证信息
        String authHeader = request.getHeader(this.tokenHeader);

        // 如果authHeader 不为空 而且 以 Bearer 开头 则继续
        if(authHeader !=null && authHeader.startsWith(this.tokenHead)){
            // 获取真正的token(tokenHead后存放的是token)
            String token = authHeader.substring(this.tokenHead.length());
            //  从token中获取用户名
            String username = jwtTokenUtil.getUserNameFromToken(token);
            LOGGER.info("checking username:{}",username);

            // 如果存在该用户名 并且验证信息为空
            if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null){
                // 验证用户并返回
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            }
        }
        filterChain.doFilter(request,response);
    }
}
