package org.sejong.sulgamewiki.fss;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FCMInitializer {

  @Value("${fcm.firebase_config_path}")
  private String firebaseConfigPath;

  @PostConstruct
  public void initialize() {
    try {
      GoogleCredentials googleCredentials = GoogleCredentials
          .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream());
      FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(googleCredentials)
          .build();
      if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options);
        log.info("Firebase application has been initialized");
      }
    } catch (IOException e) {
      log.error(e.getMessage());
    }

  }
}
