package majorfolio.backend.root.domain.university.service;

import lombok.RequiredArgsConstructor;
import majorfolio.backend.root.domain.university.entity.University;
import majorfolio.backend.root.domain.university.repository.UniversityRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UnivService {
    private final UniversityRepository universityRepository;


    public String fillUniv(MultipartFile file) {
        //여기서 엑셀파일 읽어와서 univ 만들고 db에 넣기
        try {
            List<University> universities = parseExcel(file.getInputStream());
            universityRepository.saveAll(universities);
            return "대학을 모두 채웠습니다.";
        } catch (IOException e) {
            e.printStackTrace();
            return "엑셀 파일을 읽는 중 오류가 발생했습니다.";
        }
    }

    private List<University> parseExcel(InputStream inputStream) throws IOException {
        List<University> universities = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            // 첫 행은 헤더이므로 넘기고 데이터부터 읽기
            iterator.next();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                University university = new University();
                university.setUniversityName(currentRow.getCell(0).getStringCellValue());
                university.setDomain(currentRow.getCell(1).getStringCellValue());
                universities.add(university);
            }
        }
        return universities;
    }
}
