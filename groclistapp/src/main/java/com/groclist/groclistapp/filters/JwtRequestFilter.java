package com.groclist.groclistapp.filters;

import com.groclist.groclistapp.service.SecurityUserDetailService;
import com.groclist.groclistapp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private SecurityUserDetailService myUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String jwtToken = request.getHeader("Authorization");

        if (jwtToken == null || (!jwtToken.startsWith("Bearer ")))
            throw new ServletException("The header is incorrect format or not present");

        String uName = jwtUtil.extractUsername(jwtToken);
        UserDetails userDetails = this.myUserDetailService.loadUserByUsername(uName);
        if (jwtUtil.validateToken(jwtToken, userDetails)){

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } else{
            throw new ServletException(("Invalid Token has been sent"));
        }
        filterChain.doFilter(request, response);



    }
}
