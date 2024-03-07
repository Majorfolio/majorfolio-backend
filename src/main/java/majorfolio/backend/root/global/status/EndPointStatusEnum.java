package majorfolio.backend.root.global.status;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EndPointStatusEnum implements EndPointStatus{
    ADMIN("admin", "운영서버 엔드포인트"),
    ASSIGNMENT("assignment", "assignment 도메인"),
    MY("my", "my 도메인"),
    LIBRARY("library", "library 도메인"),
    PAYMENTS("payments", "payments 도메인"),
    TRANSACTION("transaction", "transaction 도메인"),
    DETAIL("detail", "과제 상세페이지(구매자 입장)의 엔드포인트");


    private final String endPoint;
    private final String description;
    @Override
    public String getDomain() {
        return endPoint;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
