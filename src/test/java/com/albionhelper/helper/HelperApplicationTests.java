package com.albionhelper.helper;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

//@SpringBootTest
class HelperApplicationTests {


	@Test
	void 파일_이름_변경(){
		String path = "D:\\springboot\\Albionhelper\\src\\main\\resources\\static";
		File dir = new File(path);

		File[] files = dir.listFiles();
		System.out.println(files.length);
		for(File file : files){
			if(file.getName().contains("image")){
				File newFile = new File(path + "\\image\\" + file.getName().replace("image", ""));
				file.renameTo(newFile);
			}
		}


	}

	@Test
	void 한국어와영어만_남겨놓고_파일새로_작성(){
		// JSON 파일 경로
		String inputFile = "D:/springboot/Albionhelper/src/main/resources/static/jsonData/items.json";
		String outputFile = "D:/springboot/Albionhelper/src/main/resources/static/jsonData/filtered_items.json";

		try {
			// ObjectMapper 생성
			ObjectMapper objectMapper = new ObjectMapper();

			// JSON 파일을 리스트로 읽기
			List<JsonNode> items = objectMapper.readValue(new File(inputFile), new TypeReference<>() {});

			// 필터링된 리스트
			List<JsonNode> filteredItems = new ArrayList<>();

			for (JsonNode item : items) {
				// 새 객체 생성
				ObjectMapper mapper = new ObjectMapper();
				ObjectNode newItem = mapper.createObjectNode();

				// 기본 필드 추가
				newItem.put("Index", item.get("Index").asText());
				newItem.put("UniqueName", item.get("UniqueName").asText());

				// LocalizedNames에서 KO-KR과 EN-US만 남기기
				JsonNode localizedNames = item.get("LocalizedNames");
				if (localizedNames != null) {
					if(localizedNames.isNull()){
						newItem.set("LocalizedNames", null);
					}else{
						ObjectNode node = mapper.createObjectNode();
						String EN_US = localizedNames.has("EN-US")? localizedNames.get("EN-US").asText() : null;
						String KO_KR = localizedNames.has("KO-KR")? localizedNames.get("KO-KR").asText() : null;
						node.put("EN-US", EN_US);
						node.put("KO-KR", KO_KR);
						newItem.set("LocalizedNames", node);
					}
				}

				// LocalizedDescriptions에서 KO-KR과 EN-US만 남기기
				JsonNode localizedDescriptions = item.get("LocalizedDescriptions");
				if (localizedDescriptions != null) {
					if(localizedDescriptions.isNull()){
						newItem.set("LocalizedDescriptions", null);
					}else{
						ObjectNode node = mapper.createObjectNode();
						String EN_US = localizedDescriptions.has("EN-US")? localizedDescriptions.get("EN-US").asText() : null;
						String KO_KR = localizedDescriptions.has("KO-KR")? localizedDescriptions.get("KO-KR").asText() : null;
						node.put("EN-US", EN_US);
						node.put("KO-KR", KO_KR);
						newItem.set("LocalizedDescriptions", node);
					}
				}
				filteredItems.add(newItem);
			}

			// 결과를 새로운 JSON 파일로 저장
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFile), filteredItems);

			System.out.println("필터링된 " + filteredItems.size() + "개의 항목이 " + outputFile + "에 저장되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }


	@Test
	void contextLoads() {

		String filePath = "D:/springboot/Albionhelper/src/main/resources/static/jsonData/items.json";
		String nodeNames = "LocalizedDescriptions";

		try (FileReader fileReader = new FileReader(filePath)) {
			JSONParser jsonParser = new JSONParser();
			JSONArray jsonObject = (JSONArray) jsonParser.parse(fileReader);
			System.out.println(jsonObject.size());
			for (int i = 0; i < jsonObject.size(); i++) {
				JSONObject a = (JSONObject) jsonObject.get(i);
				Stream<String> nodeStream = Stream.of(nodeNames);
				nodeStream.forEach(a::remove);
			}




			ObjectMapper objectMapper = new ObjectMapper();
			String jsonObjectPrettified = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);

			FileWriter fileWriter = new FileWriter(filePath.split("\\.")[0] + "_modified04.json");
			fileWriter.write(jsonObjectPrettified);
			fileWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
