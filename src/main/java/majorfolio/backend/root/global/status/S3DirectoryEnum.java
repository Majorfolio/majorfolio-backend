package majorfolio.backend.root.global.status;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum S3DirectoryEnum implements S3Directory{
    EVENTS3("event", "이벤트 디렉토리"),
    NOTICES3("notice", "공지사항 디렉토리");

    private final String S3DirectoryName;
    private final String description;
    @Override
    public String getS3DirectoryName() {
        return S3DirectoryName;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
