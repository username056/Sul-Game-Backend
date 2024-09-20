package org.sejong.sulgamewiki.object;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.sejong.sulgamewiki.util.exception.CustomException;
import org.sejong.sulgamewiki.util.exception.ErrorCode;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false, length = 500)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    private BasePost basePost;

    @Builder.Default
    private int likeCount = 0;

    @Builder.Default
    @ElementCollection
    private Set<Long> likedMemberIds = new HashSet<>();

    @Builder.Default
    private int reportedCount = 0;

    public void increaseReportedCount() {this.reportedCount++;}

    public void cancelLike(Long memberId) {
        if(!this.likedMemberIds.contains(memberId)) {
            throw new CustomException(ErrorCode.NO_LIKE_TO_CANCEL);
        }
        if (likeCount > 0) {
            this.likeCount--;
            this.likedMemberIds.remove(memberId);
        } else if(likeCount <= 0) {
            throw new CustomException(ErrorCode.LIKE_CANNOT_BE_UNDER_ZERO);
        }
    }

    public void upLike(Long memberId) {
        if(this.likedMemberIds.contains(memberId)) {
            throw new CustomException(ErrorCode.ALREADY_LIKED);
        }
        likeCount++;
        this.likedMemberIds.add(memberId);
    }
}
