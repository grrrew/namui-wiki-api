package com.dnd.namuiwiki.domain.survey.model.entity;

import com.dnd.namuiwiki.common.model.BaseTimeEntity;
import com.dnd.namuiwiki.domain.question.entity.Question;
import com.dnd.namuiwiki.domain.survey.type.AnswerType;
import com.dnd.namuiwiki.domain.survey.type.Period;
import com.dnd.namuiwiki.domain.survey.type.Relation;
import com.dnd.namuiwiki.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Builder
@Document(collection = "surveys")
public class Survey extends BaseTimeEntity {

    @Id
    private String id;

    @DocumentReference
    private User owner;

    @DocumentReference
    private User sender;

    private String senderName;

    private Period period;

    private Relation relation;

    private List<Answer> answers;

    @Getter
    @Builder
    public static class Answer {

        @DocumentReference(collection = "questions", lazy = true)
        private Question question;
        private AnswerType type;
        private Object answer;
        private String reason;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Survey survey = (Survey) o;
        return Objects.equals(id, survey.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public LocalDateTime getWrittenAt() {
        return this.createdAt;
    }
}
