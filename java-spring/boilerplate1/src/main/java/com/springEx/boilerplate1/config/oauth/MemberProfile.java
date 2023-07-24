package com.springEx.boilerplate1.config.oauth;


import com.springEx.boilerplate1.model.Member;
import com.springEx.boilerplate1.model.dto.JwtTokenDto;
import com.springEx.boilerplate1.model.enums.Authority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberProfile {
    private String email;
    private String provider;
    private String nickname;

    public Member toMember() {
        return Member.builder()
//                .name(name)
                .email(email)
                .provider(provider)
                .nickname(nickname)
                .authority(Authority.ROLE_USER)
                .build();
    }

    public Member toMember(JwtTokenDto jwtTokenDto) {
        return Member.builder()
//                .name(name)
                .email(email)
                .provider(provider)
                .nickname(nickname)
                .accessToken(jwtTokenDto.getAccessToken())
                .accessTokenExpireIn(jwtTokenDto.getTokenExpiresIn())
                .authority(Authority.ROLE_USER)
                .build();
    }
}
