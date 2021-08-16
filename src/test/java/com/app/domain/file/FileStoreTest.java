package com.app.domain.file;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class FileStoreTest {

    @Autowired
    private FileStore fileStore;

    @Value("${file.dir}")
    private String fileDir;

    @Test
    void fullPathTest() throws Exception {
        // given
        String fileName = "test";
        String contentType = "png";
        String filePath = "D:/groupmgmt/file/test.png";
        MockMultipartFile multipartFile = getMockMultipartFile(fileName, contentType, filePath);

        UploadFile uploadFile = fileStore.storeFile(multipartFile);

        // when
        String fullPath = fileStore.getFullPath(uploadFile.getUploadFileName());

        // then
        assertThat(fullPath).isEqualTo(fileDir + uploadFile.getUploadFileName());
    }

    private MockMultipartFile getMockMultipartFile(String fileName, String contentType, String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        return new MockMultipartFile(
                fileName,
                fileName + "." + contentType,
                contentType,
                fileInputStream
        );
    }

}