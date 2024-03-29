package com.dnd.namuiwiki.domain.user.entity;

import com.dnd.namuiwiki.common.model.BaseTimeEntity;
import com.dnd.namuiwiki.domain.oauth.type.OAuthProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Builder
@Document(collection = "users")
public class User extends BaseTimeEntity {
    @Id
    private String id;
    private String wikiId;
    private String nickname;
    private OAuthProvider oauthProvider;
    private String oauthId;
    private String refreshToken;

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(wikiId, user.wikiId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wikiId);
    }
}
