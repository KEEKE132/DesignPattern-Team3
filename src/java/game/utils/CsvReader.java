package game.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

//CSV 파일 읽기를 관리하기 위한 클래스
public class CsvReader {
    //level.csv 맵 파일을 읽어서, 자바가 이해할 수 있는 2차원 리스트로 변환
    public List<List<String>> parseCsv(URI file) {
        List<List<String>> data = new ArrayList<>();
        try {
            InputStreamReader reader =  new InputStreamReader(file.toURL().openStream());
            BufferedReader br = new BufferedReader(reader);

            String line = br.readLine();
            while(line != null) {
                List<String> lineData = Arrays.asList(line.split(";"));
                data.add(lineData);
                line = br.readLine();
            }
            br.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}