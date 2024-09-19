package org.sejong.sulgamewiki.object;

import com.github.javafaker.Book;
import com.google.api.client.util.Key;
import com.google.auto.value.AutoValue.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//
//import jakarta.persistence.*;
//import lombok.*;
//import org.sejong.sulgamewiki.object.constants.NotificationType;
//
//@Entity
//@Table(name = "notifications")
//@Getter
//@Setter
//@ToString(callSuper = true)
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Notification extends BaseTimeEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private NotificationType notificationType;
//
//    @Column(nullable = false)
//    private String message;
//
//    @JoinColumn(nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Member member;
//
//}
