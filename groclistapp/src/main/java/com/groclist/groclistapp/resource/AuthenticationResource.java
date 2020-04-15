package com.groclist.groclistapp.resource;


import com.groclist.groclistapp.dto.AuthRequestDto;
import com.groclist.groclistapp.dto.AuthResponseDto;
import com.groclist.groclistapp.service.SecurityUserDetailService;
import com.groclist.groclistapp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.ws.rs.POST;


@RestController
@RequestMapping("/authenticate")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityUserDetailService userDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @POST
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AuthResponseDto> authenticateUser(@RequestHeader(name = "x-request-id", required = false)
                                                                        String requestId,
                                                            @RequestBody AuthRequestDto payload) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getUname(), payload.getPassword()));
        } catch (BadCredentialsException e){
            throw new Exception("Incorrect username and password", e);
        }

        final UserDetails userDetails = userDetailService.loadUserByUsername(payload.getUname());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponseDto(jwt));
    }
}
