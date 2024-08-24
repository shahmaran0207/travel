package com.travel.travel.Config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

public class AuditorAwarelmpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor(){ //Optional: 값이 있을수도 있고 없을수도 있는 상황을 안전하게 처리할 수 있는 컨테이너 객체를 제공
        Authentication authentication=
                SecurityContextHolder.getContext().getAuthentication();

        String userId="";

        if(authentication !=null) userId=authentication.getName();

        return Optional.of(userId);
    }

}
