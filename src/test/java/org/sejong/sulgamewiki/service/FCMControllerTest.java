//package org.sejong.sulgamewiki.service;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class FCMControllerTest {
//
//  @Autowired
//  private MockMvc mockMvc;
//
//  @Test
//  public void testSendMessageToken() throws Exception {
//    String jsonRequest = "{ \"token\": \"YOUR_DEVICE_FCM_TOKEN\", \"title\": \"Test\", \"body\": \"Test message\" }";
//
//    mockMvc.perform(post("/api/notification/token")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(jsonRequest))
//        .andExpect(status().isOk());
//  }
//
//  @Test
//  public void testSendMessageTopic() throws Exception {
//    String jsonRequest = "{ \"topic\": \"test-topic\", \"title\": \"Test\", \"body\": \"Test message\" }";
//
//    mockMvc.perform(post("/api/notification/topic")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(jsonRequest))
//        .andExpect(status().isOk());
//  }
//
//  @Test
//  public void testUpdateFcmToken() throws Exception {
//    String jsonRequest = "{ \"fcmToken\": \"NEW_DEVICE_FCM_TOKEN\" }";
//
//    mockMvc.perform(post("/api/update-token")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(jsonRequest))
//        .andExpect(status().isOk());
//  }
//}
