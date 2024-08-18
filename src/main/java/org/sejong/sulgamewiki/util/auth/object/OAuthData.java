package org.sejong.sulgamewiki.util.auth.object;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OAuthData{
    OAuthToken token;
    OAuthResource resource;

    public static OAuthData of(OAuthToken token, OAuthResource resource) {
        return OAuthData.builder()
            .token(token)
            .resource(resource)
            .build();
    }
}
