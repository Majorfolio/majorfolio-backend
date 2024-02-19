/**
 * AssignmentService
 *
 * 2024.02.04
 *
 * 0.0.1
 *
 * Majorfolio
 */
package majorfolio.backend.root.domain.material.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import majorfolio.backend.root.domain.material.dto.request.AssignmentUploadRequest;
import majorfolio.backend.root.domain.material.dto.response.assignment.*;
import majorfolio.backend.root.domain.material.dto.response.assignment.stat.BookmarkStat;
import majorfolio.backend.root.domain.material.dto.response.assignment.stat.MaterialStatsResponse;
import majorfolio.backend.root.domain.material.dto.response.assignment.stat.SaleStat;
import majorfolio.backend.root.domain.material.dto.response.assignment.stat.ViewStat;
import majorfolio.backend.root.domain.material.entity.Material;
import majorfolio.backend.root.domain.material.entity.Preview;
import majorfolio.backend.root.domain.material.entity.PreviewImages;
import majorfolio.backend.root.domain.material.repository.MaterialRepository;
import majorfolio.backend.root.domain.member.entity.BuyList;
import majorfolio.backend.root.domain.member.entity.BuyListItem;
import majorfolio.backend.root.domain.member.entity.Member;
import majorfolio.backend.root.domain.member.entity.View;
import majorfolio.backend.root.domain.member.repository.*;
import majorfolio.backend.root.domain.payments.entity.BuyInfo;
import majorfolio.backend.root.domain.payments.repository.BuyInfoRepository;
import majorfolio.backend.root.global.CustomMultipartFile;
import majorfolio.backend.root.global.exception.JwtInvalidException;
import majorfolio.backend.root.global.exception.NotDownloadAuthorizationException;
import majorfolio.backend.root.global.exception.NotMatchMaterialAndMemberException;
import majorfolio.backend.root.global.util.MakeSignedUrlUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.util.Matrix;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static majorfolio.backend.root.global.response.status.BaseExceptionStatus.*;

/**
 * assignment/** 요청의 서비스 구현
 *
 * @author 김영록
 * @version 0.0.1
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AssignmentService {

    private final MaterialRepository materialRepository;
    private final MemberRepository memberRepository;
    private final SellListItemRepository sellListItemRepository;
    private final FollowerRepository followerRepository;
    private final KakaoSocialLoginRepository kakaoSocialLoginRepository;
    private final BookmarkRepository bookmarkRepository;
    private final LikeRepository likeRepository;
    private final ViewRepository viewRepository;
    private final PreviewRepository previewRepository;
    private final PreviewImagesRepository previewImagesRepository;
    private final BuyListItemRepository buyListItemRepository;
    private final BuyInfoRepository buyInfoRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String s3Bucket;

    @Value("${cloud.aws.cloudFront.distributionDomain}")
    private String distributionDomain;

    @Value("${cloud.aws.path}")
    private String privateKeyFilePath;

    @Value("${cloud.aws.cloudFront.keyPairId}")
    private String keyPairId;

    private final AmazonS3Client amazonS3;

    /**
     * 업로드 API 서비스 구현
     * @param pdfFile
     * @param kakaoId
     * @param assignmentUploadRequest
     * @return
     * @throws IOException
     *
     * Copyright [Majorfolio]
     * SPDX-License-Identifier : Apache-2.0
     */
    public AssignmentUploadResponse uploadPdfFile(Long kakaoId, AssignmentUploadRequest assignmentUploadRequest) throws IOException {
        //업로드 한 사람 조회
        Member member = kakaoSocialLoginRepository.findById(kakaoId).get().getMember();
        Long memberId = member.getId();
        //pdf파일 전처리 과정
        MultipartFile pdfFile = assignmentUploadRequest.getFile();
        PDDocument document = PDDocument.load(pdfFile.getBytes());
        String fileName = generateFileName(pdfFile);
        //파일 부가 정보 저장
        int page = document.getNumberOfPages();
        Preview preview = Preview.builder().build();
        previewRepository.save(preview);
        Material material = getMaterial(assignmentUploadRequest, page, fileName, member, preview);
        materialRepository.save(material);
        Long materialId = material.getId();
        //원본 파일 S3에올리기
        fileSaveToS3(document, fileName, memberId, materialId, "originalFile");
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        //과제 파일의 페이지 정보
        //미리보기 이미지 S3올리기
        imageSaveToS3(pdfRenderer, fileName, page, memberId, materialId, preview);

        //전화번호 업로드 여부
        Boolean isRegisterPhoneNumber = true;

        if(member.getPhoneNumber() == null){
            isRegisterPhoneNumber = false;
        }

        return AssignmentUploadResponse.of(isRegisterPhoneNumber);
    }

    /**
     * 업로드 api 요청으로 부터 material 테이블 채우기
     * @param assignmentUploadRequest
     * @param page
     * @param fileName
     * @param member
     * @param preview
     * @return
     */
    public Material getMaterial(AssignmentUploadRequest assignmentUploadRequest, int page,
                                String fileName, Member member, Preview preview) {
        return Material.of(
                assignmentUploadRequest.getTitle(),
                assignmentUploadRequest.getDescription(),
                "pdf",
                assignmentUploadRequest.getSemester(),
                assignmentUploadRequest.getProfessor(),
                assignmentUploadRequest.getSubjectName(),
                assignmentUploadRequest.getMajor(),
                assignmentUploadRequest.getGrade(),
                assignmentUploadRequest.getScore(),
                assignmentUploadRequest.getFullScore(),
                page,
                fileName,
                member,
                preview
        );
    }

    /**
     * pdf 파일 S3에 저장
     * @param document
     * @param fileName
     * @param memberId
     * @param materialId
     * @throws IOException
     */
    public void fileSaveToS3(PDDocument document, String fileName, Long memberId, Long materialId, String mode) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        byte[] pdfBytes = outputStream.toByteArray();
        MultipartFile multipartFile = convertToMultipartFile(pdfBytes);

        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());

        String fileDirectory = "";
        if(mode.equals("originalFile")){
            fileDirectory = s3Bucket + "/" + memberId + "/" + materialId + "/originalFile";
        }
        else if(mode.equals("Downloads")){
            fileDirectory = s3Bucket + "/" + memberId + "/" + "Downloads" + "/" + materialId;
        }

        try (InputStream fileInputStream = multipartFile.getInputStream()){
            amazonS3.putObject(
                    new PutObjectRequest(fileDirectory, fileName, fileInputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
        }
    }

    /**
     * 미리보기 이미지 S3저장소에 저장
     * @param pdfRenderer
     * @param fileName
     * @param page
     * @param memberId
     * @param materialId
     * @param preview
     * @throws IOException
     */
    public void imageSaveToS3(PDFRenderer pdfRenderer, String fileName,
                              int page, Long memberId, Long materialId,
                              Preview preview) throws IOException {
        log.info(String.valueOf(page));
        int size = 0;
        int lowDpi = 0; // 저화질 처리할 이미지 개수
        if(page <= 2){
            size = 1;
        }
        else if(page <= 4){
            size = 2;
            lowDpi = 1;
        }
        else {
            size = 3;
            lowDpi = 2;
        }
        BufferedImage[] imgObjs = new BufferedImage[size];
        ObjectMetadata[] objectMetadatas = new ObjectMetadata[size];
        String[] imageNames = new String[size];
        MultipartFile[] images = new MultipartFile[size];
        if(size == 1){
            imgObjs[0] = pdfRenderer.renderImageWithDPI(0, 100, ImageType.RGB);
        }
        int mosaicIndex = 0; // 모자이크 처리할 이미지 객체의 시작점
        //저화질 처리
        for(int i=0; i<lowDpi; i++){
            imgObjs[i] = pdfRenderer.renderImageWithDPI(i, 30, ImageType.RGB);
            mosaicIndex++;
        }
        //모자이크 처리
        if(size > 1){
            for(int i=mosaicIndex; i<imgObjs.length; i++){
                imgObjs[i] = pdfRenderer.renderImageWithDPI(i, 10, ImageType.RGB);
            }
        }
        //multipartFile형식으로 변환
        for(int i=0; i<size; i++){
            images[i] = convertBufferedImageToMultipartFile(imgObjs[i], fileName, i+1);
        }
        //이미지 이름 생성
        for(int i=0; i<size; i++){
            imageNames[i] = generateFileName(images[i]);
        }
        //메타데이터 생성
        for(int i=0; i<size; i++){
            objectMetadatas[i] = new ObjectMetadata();
            objectMetadatas[i].setContentType(images[i].getContentType());
            objectMetadatas[i].setContentLength(images[i].getSize());
        }
        //s3로 전송
        for(int i=0; i<size; i++){
            try (InputStream imageInputStream = images[i].getInputStream()) {
                String fileDirectory = s3Bucket + "/" + memberId + "/" + materialId + "/Previews";
                amazonS3.putObject(
                        new PutObjectRequest(fileDirectory, imageNames[i], imageInputStream, objectMetadatas[i])
                                .withCannedAcl(CannedAccessControlList.PublicRead));
                imageNames[i] = fileDirectory + "/" + imageNames[i];
            }
        }
        //DB에 미리보기 이미지 저장
        for(int i=0; i<size; i++){
            PreviewImages previewImages = PreviewImages.of(imageNames[i], i+1, preview);
            previewImagesRepository.save(previewImages);
        }
    }

    /**
     * 이미지 multipartFile형식으로 형변환
     * @param bufferedImage
     * @param fileName
     * @param index
     * @return
     */
    public MultipartFile convertBufferedImageToMultipartFile(BufferedImage bufferedImage, String fileName, int index) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpeg", out);
        } catch (IOException e) {
            log.error("IO Error", e);
            return null;
        }
        byte[] bytes = out.toByteArray();
        String imageName = fileName + "-" +index;
        return new CustomMultipartFile(bytes, imageName, imageName+".jpeg", "jpeg", bytes.length);
    }

    /**
     * pdfFile MultipartFile형식으로 형변환
     * @param pdfBytes
     * @return
     */
    public MultipartFile convertToMultipartFile(byte[] pdfBytes) {
        return new InMemoryMultipartFile("watermarked.pdf", pdfBytes);
    }

    /**
     * 자료 다운로드 api구현
     * @param materialId
     * @param memberId
     * @return
     * @throws InvalidKeySpecException
     * @throws IOException
     */
    public AssignmentDownloadResponse downloadPdfFile(Long materialId, Long memberId) throws InvalidKeySpecException, IOException {
        // 구매자가 구매한 적이 있는지 없는지 검토
        Member member = memberRepository.findById(memberId).get();
        BuyList buyList = member.getBuyList();
        Material material = materialRepository.findById(materialId).get();
        BuyListItem buyListItem = buyListItemRepository.findByBuyListAndMaterial(buyList, material);
        BuyInfo buyInfo = buyListItem.getBuyInfo();
        Member uploader = material.getMember();
        Long uploaderId = uploader.getId();

        if (!buyInfo.getIsPay()) {
            // 예외처리 해주기
            throw new NotDownloadAuthorizationException(NOT_DOWNLOAD_AUTHORIZATION);
        }


        // s3에 다운로드 파일이 있는지 검토 -> 없으면 워터마크 표기해서 pdf파일 만든뒤에 signed url던져주기
        //(구매 파일 s3경로 : majorfolio/{memberId}/{downloads}/{materialId})
        String fileLink = material.getLink();
        fileLink = fileLink.replace(" ", "+");
        String signedUrl = "";
        //여기도 예외처리 해주기

        if(buyListItem.getIsDown()){
            fileLink = "downloadMode" + fileLink;
            signedUrl = MakeSignedUrlUtil.makeSignedUrl(fileLink, s3Bucket, memberId, materialId, "Downloads",
                    privateKeyFilePath, distributionDomain, keyPairId);
            return AssignmentDownloadResponse.of(signedUrl);
        }

        log.info(signedUrl);


        //파일 가져와서 워터마크 표기하기
        log.info(fileLink);
        signedUrl = MakeSignedUrlUtil.makeSignedUrl(fileLink, s3Bucket, uploaderId, materialId, "originalFile",
                privateKeyFilePath, distributionDomain, keyPairId);
        log.info(signedUrl);

        PDDocument document = null;
        try {
            // Open an HTTP connection to the signed URL
            URL url = new URL(signedUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            try (InputStream inputStream = new BufferedInputStream(connection.getInputStream())) {
                // Load the PDF document
                document = PDDocument.load(inputStream);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        //파일가져오기
//        byte[] content = s3Object.getObjectContent().readAllBytes();
//        MultipartFile pdfFile = new InMemoryMultipartFile(s3Object.getObjectMetadata().getContentDisposition(), content);

//        //pdf파일 전처리 과정
//        PDDocument document = PDDocument.load(pdfFile.getBytes());

        //워터마크 표기

        //현재 날짜 가져오기(다운로드 날짜)
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String watermarkText = "Majorfolio" + "/" + member.getNickName() + "/" + memberId + "/" + member.getUniversityName() + "/" + member.getMajor1() + "/" + formattedDate;
        String outputFile = "downloadMode"+fileLink;
        for (PDPage page : document.getPages()) {
            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();

            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
            contentStream.beginText();
            // 사용자 지정 TTF 폰트 로드
            PDType0Font font = PDType0Font.load(document, new File("src\\main\\java\\majorfolio\\backend\\root\\global\\font\\NanumBarunGothic.ttf"));
            contentStream.setFont(font, 20);
            contentStream.setNonStrokingColor(234, 234, 234); // 워터마크의 색상 설정

            // 중앙 위치 계산
            float centerX = (pageWidth / 12);
            float centerY = (pageHeight / 28);

            // 텍스트 위치 및 회전 설정
            Matrix matrix = new Matrix();
            matrix.translate(centerX, centerY); // 중앙으로 이동
            matrix.rotate(Math.toRadians(50)); // 25도 회전

            contentStream.setTextMatrix(matrix);
            // 텍스트 위치 설정
            contentStream.newLineAtOffset(centerX, centerY); // 중앙으로 이동

            contentStream.showText(watermarkText);
            contentStream.endText();
            contentStream.close();
        }

        document.save(outputFile);

        outputFile = outputFile.replace(" ", "+");
        // 워터마크 추가된 파일 S3에 올리기
        fileSaveToS3(document, outputFile, memberId, materialId, "Downloads");

        document.close();
        //다시 signedUrl 가져오기
        signedUrl = MakeSignedUrlUtil.makeSignedUrl(outputFile, s3Bucket, memberId, materialId, "Downloads",
                privateKeyFilePath, distributionDomain, keyPairId);

        //구매완료로 바꾸고 download상태 바꾸기
        buyInfo.setStatus("buyComplete");
        buyListItem.setIsDown(true);


        buyInfoRepository.save(buyInfo);
        buyListItemRepository.save(buyListItem);

        return AssignmentDownloadResponse.of(signedUrl);

    }

    /**
     * 스프링의 MultipartFile 인터페이스를 구현하는 InMemoryMultipartFile 클래스 정의
     */
    private static class InMemoryMultipartFile implements MultipartFile {
        private final String name;
        private final byte[] bytes;

        public InMemoryMultipartFile(String name, byte[] bytes) {
            this.name = name;
            this.bytes = bytes;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getOriginalFilename() {
            return name;
        }

        @Override
        public String getContentType() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public long getSize() {
            return bytes.length;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return bytes;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(bytes);
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
        }
    }

    /**
     * S3에 들어갈 파일 이름 정의해주는 메소드
     * @param file
     * @return
     */
    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID() + "-" + file.getOriginalFilename();
    }


    /**
     * 과제 상세페이지(구매자 입장)의 서비스 메소드
     * @author 김영록
     * @param materialId
     * @return
     */
    public MaterialDetailResponse showDetailMaterial(Long materialId, Long binderMemberId) throws InvalidKeySpecException, IOException {
        //조회수 올리기
        doView(materialId);

        //id, 닉네임, 좋아요수, 북마크수, 제목, 설명, 대학교, 학과, 학기, 과목, 교수명, 학점, 점수, 만점, 페이지수
        Material material = materialRepository.findById(materialId).get();
        Member member = material.getMember();

        String nickname = member.getNickName();
        LocalDateTime updateTime = material.getUpdatedAt();
        int like = material.getTotalRecommend();
        int bookmark = material.getTotalBookmark();
        String title = material.getName();
        String description = material.getDescription();
        String univ = member.getUniversityName();
        String major = material.getMajor();
        String semester = material.getSemester();
        String className = material.getClassName();
        String professor = material.getProfessor();
        String grade = material.getGrade();
        int score = material.getScore();
        int fullscore = material.getFullScore();
        int pages = material.getPage();

        Long memberId = member.getId();
        String image = s3PreviewImageSignedUrl(material, member, materialId);

        // 판매수, 팔로워 수 추출
        Long sellCount = sellListItemRepository.countByMaterialIdAndStatus(material.getId(), "complete");

        //FollowerList followerList = member.getFollowerList();
        Long followerCount = followerRepository.countByMemberAndStatus(memberId, true);

        // 이 수업의 다른 과제(판매순 5개) 추출
        List<Material> materials = materialRepository.findAllByClassNameAndStatus(className, "active");
        log.info(materials.toString());
        Map<Long, Long> materialSellInfo = new HashMap<>();
        for (Material value : materials) {
            // 대학명도 같아야 함
            if(value.getMember().getUniversityName().equals(univ) && !value.getId().equals(materialId)) {
                Long id = value.getId();
                Long sell = sellListItemRepository.countByMaterialIdAndStatus(id, "complete");
                materialSellInfo.put(id, sell);
            }
        }
        log.info(materialSellInfo.toString());

        //판매순 정렬
        List<Long> keySet = new ArrayList<>(materialSellInfo.keySet());
        keySet.sort((o1, o2) -> materialSellInfo.get(o2).compareTo(materialSellInfo.get(o1)));

        //판매순 5개 뽑아내기
        List<OtherAssignment> materialsTop5 = new ArrayList<>();
        for(int i=0; i<5; i++){
            Long id;
            try{
                id = keySet.get(i);
            }catch (IndexOutOfBoundsException e){
                break;
            }
            if(materialRepository.existsById(id)){
                Material m = materialRepository.findById(id).get();
                OtherAssignment o = OtherAssignment.of(id, m.getName(), m.getMember().getNickName(),
                        m.getMember().getUniversityName(), m.getMajor(), m.getSemester(),
                        m.getProfessor(), m.getTotalRecommend());
                materialsTop5.add(i, o);
            }

        }


        //멤버가 좋아요, 북마크 체크 했는지 여부
        Boolean isMemberBookmark = false;
        Boolean isMemberLike = false;
        Member binderMember = null;
        try {
            binderMember = memberRepository.findById(binderMemberId).get();
        }catch (NoSuchElementException e){
            isMemberBookmark = false;
            isMemberLike = false;
        }

        if(binderMember != null){
            isMemberBookmark = isBookmark(material, binderMember);
            isMemberLike = isLike(material, binderMember);
        }


        return MaterialDetailResponse.of(
                materialId,
                image,
                updateTime,
                nickname,
                like,
                bookmark,
                title,
                description,
                sellCount,
                followerCount,
                univ,
                major,
                semester,
                className,
                professor,
                grade,
                score,
                fullscore,
                pages,
                isMemberBookmark,
                isMemberLike,
                materialsTop5
        );
    }

    /**
     * 유저가 해당 과제에 좋아요 눌렀는지 여부
     * @param material
     * @param binderMember
     * @return
     */
    private Boolean isLike(Material material, Member binderMember) {
        try {
            return likeRepository.findByMemberAndMaterial(binderMember, material).getIsCheck();
        }catch (NullPointerException e){
            return false;
        }
    }

    /**
     * 유저가 해당 과제에 북마크 눌렀는지 여부
     * @param material
     * @param binderMember
     * @return
     */
    private Boolean isBookmark(Material material, Member binderMember) {
        try {
            return bookmarkRepository.findByMemberAndMaterial(binderMember, material).getIsCheck();
        }catch (NullPointerException e){
            return false;
        }
    }

    /**
     * 과제 상세페이지(판매자 입장) API 서비스 구현
     * @param kakaoId
     * @param materialId
     * @return
     */
    public MaterialMyDetailResponse showMyDetailMaterial(Long kakaoId, Long materialId) throws InvalidKeySpecException, IOException {

        //판매자 입장 페이지에 들어갈 수 있는지 자격 증명
        validateMaterialAndMember(kakaoId, materialId);

        //조회수 올리기
        doView(materialId);

        Material material = materialRepository.findById(materialId).get();
        Member member = material.getMember();

        //미리보기 이미지 url 추출
        String image = s3PreviewImageSignedUrl(material, member, materialId);


        LocalDateTime updateTime = material.getUpdatedAt();
        String nickname = member.getNickName();
        int like = material.getTotalRecommend();
        int bookmark = material.getTotalBookmark();
        String title = material.getName();
        String description = material.getDescription();
        String university = member.getUniversityName();
        String major = material.getMajor();
        String semester = material.getSemester();
        String subjectTitle = material.getClassName();
        String professor = material.getProfessor();
        String grade = material.getGrade();
        int score = material.getScore();
        int pages = material.getPage();
        String status = material.getStatus();

        //유저 좋아요, 북마크 눌렀는지 여부
        Boolean isMemberBookmark = isBookmark(material, member);
        Boolean isMemberLike = isLike(material, member);

        return MaterialMyDetailResponse.of(
                materialId,
                image,
                updateTime,
                nickname,
                like,
                bookmark,
                title,
                description,
                university,
                major,
                semester,
                subjectTitle,
                professor,
                grade,
                score,
                pages,
                status,
                isMemberBookmark,
                isMemberLike
        );
    }

    /**
     * 미리보기 이미지 S3 signedUrl 생성 메소드
     * @param material
     * @param member
     * @param materialId
     * @return
     * @throws InvalidKeySpecException
     * @throws IOException
     */
    public String s3PreviewImageSignedUrl(Material material, Member member, Long materialId) throws InvalidKeySpecException, IOException {
        //미리보기 이미지 url 추출
        Preview preview = material.getPreview();
        //S3객체의 Url에서 파일명 추출하기
        String image = previewImagesRepository.findByPreviewAndPosition(preview, 1).getImageUrl();
        String[] imageS3UrlPacket = amazonS3.getUrl(s3Bucket, image).toString().split("/");
        String imageS3Name = imageS3UrlPacket[imageS3UrlPacket.length-1];
        Long memberId = member.getId();
        log.info(imageS3Name);
        image = MakeSignedUrlUtil.makeSignedUrl(imageS3Name, s3Bucket, memberId, materialId, "Previews", privateKeyFilePath, distributionDomain, keyPairId);
        return image;
    }

    /**
     * 과제 통계(판매자 입장) API 서비스 구현
     * @param kakaoId
     * @param materialId
     * @return
     */
    public MaterialStatsResponse getMaterialStats(Long kakaoId, Long materialId){
        Material material = materialRepository.findById(materialId).get();
        //판매자 입장 페이지에 들어갈 수 있는지 자격 증명
        validateMaterialAndMember(kakaoId, materialId);



        // 날짜 연산
        LocalDateTime today = LocalDateTime.now();

        LocalDateTime todayStart = today
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        LocalDateTime todayEnd = today.plusDays(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);


        // 이번 주 날짜 간격 구하기
        int day = today.get(DAY_OF_WEEK);
        if(day==7){
            day=0;
        }

        LocalDateTime startDate = today.minusDays(day)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        log.info(startDate.toString());

        LocalDateTime endDate = startDate.plusDays(6)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        log.info(endDate.toString());

        //판매 통계 구하기
        Long totalSale = sellListItemRepository.countByMaterialIdAndStatus(materialId, "complete");
        Long weeklySale = sellListItemRepository
                .countByMaterialIdAndStatusAndDateBetween(materialId, "complete", startDate, endDate);
        Long todaySale = sellListItemRepository
                .countByMaterialIdAndStatusAndDateBetween(materialId, "complete", todayStart, todayEnd);

        //조회 통계 구하기
        Long totalView = viewRepository.countByMaterial(material);
        Long weeklyView = viewRepository
                .countByMaterialAndDateBetween(material, startDate, endDate);
        Long todayView = viewRepository
                .countByMaterialAndDateBetween(material, todayStart, todayEnd);

        //북마크 통계 구하기
        Long totalBookmark = bookmarkRepository.countByMaterialAndIsCheck(material, true);
        Long weeklyBookmark = bookmarkRepository
                .countByMaterialAndIsCheckAndDateBetween(material, true, startDate, endDate);
        Long todayBookmark = bookmarkRepository
                .countByMaterialAndIsCheckAndDateBetween(material, true, todayStart, todayEnd);

        // 응답 객체 생성
        SaleStat saleStat = SaleStat.of(totalSale, weeklySale, todaySale);
        ViewStat viewStat = ViewStat.of(totalView, weeklyView, todayView);
        BookmarkStat bookmarkStat = BookmarkStat.of(totalBookmark, weeklyBookmark, todayBookmark);

        return MaterialStatsResponse.of(saleStat, viewStat, bookmarkStat);
    }

    //조
    public void doView(Long materialId){
        Material material = materialRepository.findById(materialId).get();
        View view = View.of(material);
        viewRepository.save(view);
    }


    /**
     * 판매자 입장에서의 과제 페이지에 들어갈 수 있는지 확인
     * -> 이 메소드 통과하면 자격이 있는 거임
     * @param kakaoId
     * @param materialId
     */
    public void validateMaterialAndMember(Long kakaoId, Long materialId){
        // 요청 사용자의 memberId 추출
        Long memberId = kakaoSocialLoginRepository.findById(kakaoId).get().getMember().getId();
        if(memberId == 0){
            // 인증을 거친 사용자가 아닐 경우
            throw new JwtInvalidException(INVALID_TOKEN);
        }
        //과제 주인 memberId
        Long owner = materialRepository.findById(materialId).get().getMember().getId();

        //과제 주인과 현재 사용자가 다를 때
        if(!Objects.equals(owner, memberId)){
            throw new NotMatchMaterialAndMemberException(NOT_MATCH_MATERIAL_AND_MEMBER);
        }
    }
}
