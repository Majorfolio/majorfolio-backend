package majorfolio.backend.root.global.status;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EndPointStatusEnum implements EndPointStatus{
    ADMIN("admin", "운영서버 엔드포인트");


    private final String endPoint;
    private final String description;
    @Override
    public String getEndPoint() {
        return endPoint;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
