package majorfolio.backend.root.global.util;

import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.internal.ServiceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import static majorfolio.backend.root.global.status.S3DirectoryEnum.EVENTS3;
import static majorfolio.backend.root.global.status.S3DirectoryEnum.NOTICES3;

@Slf4j
@RequiredArgsConstructor
public class S3Util {


    public static String makeSignedUrl(String s3FileName, String bucketName,  Long memberId, Long materialId, String type, String privateKeyFilePath,
                                       String distributionDomain, String keyPairId, AmazonS3Client amazonS3) throws InvalidKeySpecException, IOException {
        InputStream inputStream = new ClassPathResource(privateKeyFilePath).getInputStream();
        String[] privateKeyFileNameArray = privateKeyFilePath.split("/");
        String privateKeyFileFullName = privateKeyFileNameArray[privateKeyFileNameArray.length-1];
        String privateKeyFileName = privateKeyFileFullName.split("\\.")[0];
        String privateKeyFileType = privateKeyFileFullName.split("\\.")[1];
        File privateKeyFile = File.createTempFile(privateKeyFileName, "." + privateKeyFileType);

        try {
            FileUtils.copyInputStreamToFile(inputStream, privateKeyFile);
        } finally {
            IOUtils.closeQuietly(inputStream);
            inputStream.close();
        }
        String signedURL = "";

        String policyResourcePath = "";
        log.info(s3FileName);

        if(type.equals("Previews") || type.equals("originalFile")){
            policyResourcePath = "https://" + distributionDomain + "/" + bucketName + "/" + memberId + "/"
                    + materialId + "/" + type + "/" + s3FileName;
        }
        else if(type.equals("Downloads") || type.equals("TempStorage")){
            policyResourcePath = "https://" + distributionDomain + "/" + bucketName + "/" + memberId + "/"
                    + type + "/" + materialId + "/" + s3FileName;
        }
        else if(type.equals(NOTICES3.getS3DirectoryName()) || type.equals(EVENTS3.getS3DirectoryName())){
            policyResourcePath = "https://" + distributionDomain + "/" + s3FileName;
        }


        // 현재 시각을 가져옵니다.
        Date now = new Date();

        // 10분 후의 시각을 계산합니다.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, 10);
        Date expirationDate = calendar.getTime();

        // ISO 8601 형식으로 변환합니다.
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String iso8601Expiration = isoFormat.format(expirationDate);
        Date dateLessThan = ServiceUtils.parseIso8601Date(iso8601Expiration);
        log.info(dateLessThan.toString());

        String policy = CloudFrontUrlSigner.buildCustomPolicyForSignedUrl(
                policyResourcePath,
                dateLessThan,
                "0.0.0.0/0",
                null

        );

        PrivateKey privateKey = SignerUtils.loadPrivateKey(privateKeyFile);

        signedURL = CloudFrontUrlSigner.getSignedURLWithCustomPolicy(
                // Resource URL or Path
                policyResourcePath,
                // Certificate identifier, an active trusted signer for the distribution
                keyPairId,
                // DER Private key data
                privateKey,
                // Access control policy
                policy
        );
        log.info(signedURL);
        return signedURL;
    }

    /**
     * S3에 들어갈 파일 이름 정의해주는 메소드
     * @param file
     * @return
     */
    public static String generateFileName(MultipartFile file) {
        String originName = file.getOriginalFilename();
        originName = originName.replace(" ", "");
        return UUID.randomUUID() + "-" + originName;
    }
}
