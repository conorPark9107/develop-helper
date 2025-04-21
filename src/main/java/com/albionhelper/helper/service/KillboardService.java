package com.albionhelper.helper.service;

import com.albionhelper.helper.domain.battle.Event;
import com.albionhelper.helper.domain.killboard.DeathBoard;
import com.albionhelper.helper.domain.killboard.KillBoard;
import com.albionhelper.helper.domain.Player;
import com.albionhelper.helper.domain.killboard.PlayerLog;
import com.albionhelper.helper.domain.killboard.PlayerLogDTO;
import com.albionhelper.helper.domain.playerinfo.PlayerInfoDetail;
import com.albionhelper.helper.enums.ServerRegion;
import com.albionhelper.helper.repository.KillboardRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class KillboardService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final WebClient webClient;
    private final KillboardRepository killboardRepository;

    // search?q={id}
    private final String GET_ID_URL = "/search?q=";

    // 킬
    private final String GET_KILLBOARD = "/players/<ID>/kills";

    // 데스
    private final String GET_DEATHBOARD = "/players/<ID>/deaths";;

    // 플레이어 정보
    private final String GET_PLAYERINFO = "/players/";

    // 가장 큰 킬을 발생한 정보들.
    private final String GET_BIGGEST = "/events/killfame?";

    public KillboardService(WebClient webClient, KillboardRepository killboardRepository) {
        this.webClient = webClient;
        this.killboardRepository = killboardRepository;
    }

    // getDetail()메서드로부터 호출되며, 1 v 1 이벤트(킬상세)로그를 파싱하여 리턴.
    private Event[] getResponseEvent(String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Event[].class)
                .block();
    }

    private String getResponseForBoard(String requestUrl) {
        String urlWithTimestamp = requestUrl + "?timestamp=" + Instant.now().getEpochSecond();
        return  webClient.get()
                .uri(urlWithTimestamp)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String getResponse(String url) {
        return  webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
//                .block(Duration.ofMillis(10_000));
    }

    // 플레이어 ID값을 알아내기위한.
    // https://gameinfo-sgp.albiononline.com/api/gameinfo/search?q=Metzzi
    public List<Player> getPlayersInfo(String id, String location) throws JsonProcessingException {
        ArrayList<Player> list = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();

        location = getLocation(location);

        stringBuilder.append(location).append(GET_ID_URL).append(id);

        log.info("request url is {}", stringBuilder.toString());

        String response = getResponse(stringBuilder.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode playersNode = rootNode.get("players");

        for (JsonNode node : playersNode) {
            Player p = objectMapper.treeToValue(node, Player.class);
            list.add(p);
        }


        return list;
    }

    private String getLocation(String location) {
        return ServerRegion.from(location).getUrl();
    }


    // https://gameinfo-sgp.albiononline.com/api/gameinfo/players/PE5yINJmTSKfHUz05tLrbA/kills
    // 최근 킬기록 10회 정보를 리턴
    public List<KillBoard> getKillBoard(String id, String location) throws JsonProcessingException {
        location = getLocation(location);
        StringBuilder killBuilder = new StringBuilder(location);
        String killUrl = killBuilder.append(GET_KILLBOARD).toString().replace("<ID>", id);
        log.info("request url is {}", killUrl);
        String responseKillData = getResponseForBoard(killUrl);
        return getKillLog(responseKillData);
    }

    // https://gameinfo-sgp.albiononline.com/api/gameinfo/players/PE5yINJmTSKfHUz05tLrbA/death
    // 최근 데스기록 10회 정보를 리턴
    public List<DeathBoard> getDeathBoard(String id, String location) throws JsonProcessingException {
        location = getLocation(location);
        StringBuilder deathBuilder = new StringBuilder(location);
        String deathUrl = deathBuilder.append(GET_DEATHBOARD).toString().replace("<ID>", id);
        log.info("request url is {}", deathUrl);
        String responseDeathData = getResponseForBoard(deathUrl);
        return getDeathLog(responseDeathData);
    }

    //    https://gameinfo-sgp.albiononline.com/api/gameinfo/events/Falu3eR4SRyGVZR9g-_XVw/history/_SjHw45FQtGAlbhnjlCjKg
    // 킬보드 상세 페이지 정보를 리턴하는 메서드.
    public Event getDetail(String server, String killerId, String victimId) {
        server = getLocation(server);
        StringBuilder detailBuilder = new StringBuilder(server);
        String url = detailBuilder.append("/events/").append(killerId).append("/history/").append(victimId).toString();
        log.info("getDetail() request url is {}", url);
        Event[] e = getResponseEvent(url);
        return e[0];
    }

    // 플레이어 정보를 담아 리턴하는 메서드.
    public PlayerInfoDetail getPlayerDetailInfo(String id, String server) throws JsonProcessingException {
        server = getLocation(server);
        StringBuilder detailBuilder = new StringBuilder(server);
        String url = detailBuilder.append(GET_PLAYERINFO).append(id).toString();
        log.info("getPlayerDetailInfo() request url is {}", url);
        String response = getResponseForBoard(url);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);

        PlayerInfoDetail p = objectMapper.treeToValue(rootNode, PlayerInfoDetail.class);
        return p;
    }


    private List<DeathBoard> getDeathLog(String responseDeathData) throws JsonProcessingException{
        List<DeathBoard> list = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseDeathData);

        for (JsonNode node : rootNode) {
            DeathBoard board = objectMapper.treeToValue(node, DeathBoard.class);
            list.add(board);
        }

        return list;
    }

    private List<KillBoard> getKillLog(String responseKillData) throws JsonProcessingException {
        List<KillBoard> list = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseKillData);

        for (JsonNode node : rootNode) {
            KillBoard board = objectMapper.treeToValue(node, KillBoard.class);
            list.add(board);
        }
        return list;
    }

    // 가장 큰 데스페임을 기록한 킬 보드(week, month, lastweek, lastMonth)
    public List<Event> getBoardBiggest(String server, String range) {
        StringBuilder url = new StringBuilder(getLocation(server));
        url.append(GET_BIGGEST)
                .append("range=")
                .append(range)
                .append("&offset=0&limit=5");
        Event[] event = getResponseEvent(url.toString());
        return Arrays.stream(event).toList();
    }

    @Transactional
    public void addCountUser(PlayerLogDTO pl){
        Optional<PlayerLog> o = killboardRepository.findByUserId(pl.getUserId());
        if(o.isPresent()){
            PlayerLog playerLog = o.get();
            playerLog.setCount(playerLog.getCount() + 1);
        }else{
            killboardRepository.save(pl.toEntity());
        }
    }

    public List<PlayerLog> getCounts() {
        return killboardRepository.findAllTop10(PageRequest.of(0, 10));
    }
}
