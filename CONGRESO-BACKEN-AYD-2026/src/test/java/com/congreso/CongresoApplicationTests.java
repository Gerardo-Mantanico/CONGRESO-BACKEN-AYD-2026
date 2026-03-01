package com.congreso;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest(properties = {
        "aws.s3.bucketName=test-bucket",
        "aws.accessKeyId=test-access-key",
        "aws.secretKey=test-secret-key",
        "aws.s3.region=us-east-1",
        "security.jwt.secret-key=ZmFrZXNlY3JldA==",
        "security.jwt.expiration-time=3600000"
})
class CongresoApplicationTests {

    @MockBean
    private JavaMailSender javaMailSender;

    @Test
    void contextLoads() {
    }

}
