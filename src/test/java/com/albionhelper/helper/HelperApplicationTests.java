package com.albionhelper.helper;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.stream.Stream;

@SpringBootTest
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
