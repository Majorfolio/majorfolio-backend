package majorfolio.backend.root.global.util;

import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils;
import com.amazonaws.services.s3.internal.ServiceUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
public class MakeSignedUrlUtil {


    public static String makeSignedUrl(String s3FileName, String bucketName,  Long memberId, Long materialId, String type, String privateKeyFilePath,
                                       String distributionDomain, String keyPairId) throws InvalidKeySpecException, IOException {
        File privateKeyFile = new File(privateKeyFilePath);
        String signedURL = "";

        log.info(s3FileName);
        String policyResourcePath = "https://" + distributionDomain + "/" + bucketName + "/" + memberId + "/"
                + materialId + "/" + type + "/" + s3FileName;

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



}
