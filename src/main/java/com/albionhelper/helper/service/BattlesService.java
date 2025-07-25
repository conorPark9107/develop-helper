package com.albionhelper.helper.service;

import com.albionhelper.helper.domain.GuildDTO;
import com.albionhelper.helper.domain.battle.*;
import com.albionhelper.helper.enums.ServerRegion;
import com.albionhelper.helper.repository.BattlesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BattlesService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // 길드의 ID값을 알아내기위한 URI
    private final String GET_ID_URL = "/search?q=";

    // 최근 전투 목록을 위한 URI
    private final String DEFAULT_BATTLES_URL = "/battles?";

    // 전투에 참가한 길드, 동맹, 인원을 알기 위한 URI URI /battles/<battleId>
    private final String GET_BATTLE = "/battles/";

    // 특정 전투의 모든 정보를 조회하기위한 URI
    // offset을 0부터 더이상 정보가 안나올때까지 데이터를 모아서 취합해야한다.
    private final String GET_ALL_INFO = "/events/battle/";

    private final WebClient webClient;
    private final BattlesRepository battlesRepository;
    public BattlesService(WebClient webClient, BattlesRepository battlesRepository) {
        this.webClient = webClient;
        this.battlesRepository = battlesRepository;
    }

    private String getLocation(String location) {
        return ServerRegion.from(location).getUrl();
    }

    private String getResponseNoCache(String requestUrl) {
        String urlWithTimestamp = requestUrl + "&timestamp=" + Instant.now().getEpochSecond();
        return webClient.get()
                .uri(urlWithTimestamp)
                .header("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0")
                .header("Pragma", "no-cache")
                .header("Expires", "0")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }


    private String getResponse(String requestUrl) {
        return webClient.get()
                .uri(requestUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private Object[] getResponseForEvent(String requestUrl) {
        return webClient.get()
                .uri(requestUrl)
                .retrieve()
                .bodyToMono(Object[].class)
                .block();
    }

    // 베틀리스트를 테이블에 보여주기위한 메서드.
    public List<Battle> getBattleList(String server, int offset, int limit, String id) throws JsonProcessingException {
        String requestUrl = getLocation(server) + DEFAULT_BATTLES_URL + "sort=recent&offset=" + offset + "&limit=" + limit + getIdURI(id);

        String response = getResponseNoCache(requestUrl);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);

        List<Battle> list = new ArrayList<>();
        for(int i = 0; i < rootNode.size(); i++){
            JsonNode node = rootNode.get(i);
            list.add(getBattle(node));
        }

        log.info("url was {}", requestUrl);
        return list;
    }

    // 새로고침되어 append될 데이터를 뽑는 메서드
    public List<Battle> getBattleListForRefresh(String server, String id, long recentId) throws JsonProcessingException {
        String requestUrl = getLocation(server) + DEFAULT_BATTLES_URL + "sort=recent&range=day&offset=0" + getIdURI(id);

        String response = getResponseNoCache(requestUrl);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);

        List<Battle> list = new ArrayList<>();
        for(int i = 0; i < rootNode.size(); i++){
            JsonNode node = rootNode.get(i);
            list.add(getBattle(node));
        }

//        list.forEach(battle -> System.out.println(battle.getStartTime()));
//        System.out.println("---------------");

        list = list.stream()
                .filter(battle -> Long.parseLong(battle.getId()) > recentId)
                .collect(Collectors.toList());

        log.info("refresh url was {}", requestUrl);
        return list;
    }

    // Guild ID값(Primary Key)을 API로부터 가져옴.
    public List<GuildDTO> getGuildId(String id, String server) throws JsonProcessingException {
        String requestUrl = getLocation(server) + GET_ID_URL + id;
        log.info("getGuildId() request url was : {} ", requestUrl);
        String response = getResponse(requestUrl);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode guildsNode = objectMapper.readTree(response).get("guilds");

        // 완전히 같은 이름의 길드가 존재한다면 그 길드의 ID값을 리턴
        List<GuildDTO> list = new ArrayList<>();
        for (JsonNode node : guildsNode) {
            if(!node.isEmpty()){
                GuildDTO dto = objectMapper.treeToValue(node, GuildDTO.class);
                list.add(dto);
            }
        }

        return list;
    }

    private Battle getBattle(JsonNode node) throws JsonProcessingException {
        Battle b = new Battle();
        ObjectMapper objMapper = new ObjectMapper();

        b.setId(String.valueOf(node.get("id")));
        b.setStartTime(String.valueOf(node.get("startTime")));
        b.setEndTime(String.valueOf(node.get("endTime")));
        b.setTotalFame(node.get("totalFame").intValue());
        b.setTotalKills(node.get("totalKills").intValue());

        List<Player> playerList = new ArrayList<>();
        for(JsonNode player : node.get("players")){
            playerList.add(objMapper.treeToValue(player, Player.class));
        }

        List<Guild> guildList = new ArrayList<>();
        for(JsonNode guild : node.get("guilds")){
            guildList.add(objMapper.treeToValue(guild, Guild.class));
        }

        List<Alliance> allianceList = new ArrayList<>();
        for(JsonNode alliance : node.get("alliances")){
            allianceList.add(objMapper.treeToValue(alliance, Alliance.class));
        }

        b.setPlayers(playerList);
        b.setGuilds(guildList);
        b.setAlliances(allianceList);
        return b;
    }

    public Battle getBattleListById(String id, String server) throws JsonProcessingException {
        String requestUrl = getLocation(server) + GET_BATTLE + id;
        log.info("getBattleListById() url is {}", requestUrl);
        String response = getResponse(requestUrl);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        return getBattle(rootNode);
    }

    public Battle getPlayers(Battle battle) {

        List<Player> players = battle.getPlayers();
        Map<String, Integer> guildcountMap = new HashMap<>();
        Map<String, Integer> allycountMap = new HashMap<>();

        // 카운팅, merge함수 => merge(키, 값, 함수) => 키에 해당하는 값이 이미 존재하면 함수를 실행하여 대처. 아니라면 그냥 키에 대한 값을 저장.
        players.forEach(p -> {
            guildcountMap.merge(p.getGuildName(), 1, Integer::sum);
            allycountMap.merge(p.getAllianceName(), 1, Integer::sum);
        });

        List<Alliance> alliances = battle.getAlliances();
        List<Guild> guilds = battle.getGuilds();

        for (Alliance ally : alliances) {
            ally.setPlayerCount(allycountMap.get(ally.getName()));
        }

        for (Guild guild : guilds) {
            guild.setPlayerCount(guildcountMap.get(guild.getName()));
        }

        Collections.sort(alliances);
        Collections.sort(guilds);
        battle.setAlliances(alliances);
        battle.setGuilds(guilds);

        return battle;
    }


    // event로 평균 IP를 구하고,
    public List<Event> getEventList(String id, String server, int kill) {
        int offset = 0;
        int limit = Math.min(kill, 51);

        List<Event> list = new ArrayList<>();
        List<Event> responseList;
        do{
            String requestUrl = getLocation(server) + GET_ALL_INFO + id + "/?offset=" + offset + "&limit=" + limit;
            offset += limit;
            Object[] response  = getResponseForEvent(requestUrl);
            log.info("event request url : {}", requestUrl);
            ObjectMapper mapper = new ObjectMapper();
            responseList = Arrays.stream(response)
                                 .map(o -> mapper.convertValue(o, Event.class))
                                 .toList();
            list.addAll(responseList);
        }while(!responseList.isEmpty() && limit == 51);

        return list;
    }

    // 데미지와 힐량을 누적하며 특정 이벤트에 참가한 플레이어들의 리스트를 생성.
    public Map<String, EventPlayer> getPlayerList(List<Event> eventList, Battle battle) {
        Map<String, EventPlayer> map = new HashMap<>();

        eventList.forEach(e -> {
            List<EventPlayer> participants = e.getParticipants();
            participants.add(e.getKiller().convert());
            participants.add(e.getVictim().convert());

            participants.forEach(p -> {
                if(map.containsKey(p.getName())){
                    EventPlayer eventPlayer = map.get(p.getName());
                    eventPlayer.setDamageDone(eventPlayer.getDamageDone() + p.getDamageDone());
                    eventPlayer.setSupportHealingDone(eventPlayer.getSupportHealingDone() + p.getSupportHealingDone());

                    // 플레이어의 데스 명성이 더 높다면 교체. (빅 도네이션 유저를 뽑기위한.)
                    if(p.getDeathFame() > eventPlayer.getDeathFame()){
                        eventPlayer.setDeathFame(eventPlayer.getDeathFame() + p.getDeathFame());
                        eventPlayer.setInventory(p.getInventory());
                    }

                    map.put(p.getName(), eventPlayer);
                }else{
                    map.put(p.getName(), p);
                }
            });

        });

        // 플레이어의 킬, 데스가 몇인지 찾아 맵에 put
        battle.getPlayers()
            .forEach(p -> {
                if(map.containsKey(p.getName())){
                    EventPlayer eventPlayer = map.get(p.getName());
                    eventPlayer.setKills(p.getKills());
                    eventPlayer.setDeaths(p.getDeaths());
                    map.put(p.getName(), eventPlayer);
                }
            });

        return map;
    }

    // 가장 킬을 많이한 플레이어.
    public Player getMostKillingPlayer(Battle battle) {
        List<Player> players = battle.getPlayers();
        return players.stream()
                .max(Comparator.comparing(Player::getKills))
                .orElse(null);
    }


    // 모스트 딜, 힐 플레이어를 찾아서 리턴.
    public EventPlayer[] getMostDpsAndHeal(Map<String, EventPlayer> eventPMap) {
        EventPlayer[] arr = new EventPlayer[3];
        arr[0] = eventPMap.values()
                .stream()
                .max(Comparator.comparing(EventPlayer::getDamageDone))
                .get();
        arr[1] = eventPMap.values()
                .stream()
                .max(Comparator.comparing(EventPlayer::getSupportHealingDone))
                .get();
        arr[2] = eventPMap.values()
                .stream()
                .max(Comparator.comparing(EventPlayer::getDeathFame))
                .get();
        return arr;
    }

    // 평균 IP를 구함.
    public Battle getAverageIp(Battle battle, Map<String, EventPlayer> playerList) {
        Map<String, Integer> guildMap = new HashMap<>();
        Map<String, Integer> allianceMap = new HashMap<>();
        Map<String, Integer> guildCount = new HashMap<>();
        Map<String, Integer> allianceCount = new HashMap<>();

        playerList.forEach((k, v) -> {
            String guildName = v.getGuildName();
            String allianceName = v.getAllianceName();

            int ip = v.getAverageItemPower();
            if(ip > 0){
                guildMap.put(guildName, guildMap.getOrDefault(guildName, 0) + ip);
                allianceMap.put(allianceName, allianceMap.getOrDefault(allianceName, 0) + ip);
                guildCount.put(guildName, guildCount.getOrDefault(guildName, 0) + 1);
                allianceCount.put(allianceName, allianceCount.getOrDefault(allianceName, 0) + 1);
            }
        });

        List<Guild> g = battle.getGuilds().stream().map(guild -> {
            if(guildMap.containsKey(guild.getName())){
                guild.setAverageIp(guildMap.get(guild.getName()) / guildCount.get(guild.getName()));
            }
            return guild;
        }).toList();

        List<Alliance> a = battle.getAlliances().stream().map(alliance -> {
            if(allianceMap.containsKey(alliance.getName())){
                alliance.setAverageIp(allianceMap.get(alliance.getName()) / allianceCount.get(alliance.getName()));
            }
            return alliance;
        }).toList();

        battle.setGuilds(g);
        battle.setAlliances(a);

        return battle;
    }

    // list로 convert
    public List<EventPlayer> sortAndConvertMapToList(Map<String, EventPlayer> map){
        return map.values()
                .stream()
                .sorted((o1, o2) -> o2.getAverageItemPower() - o1.getAverageItemPower())
                .toList();
    }

    // id 값이있다면 URI 추가.
    private String getIdURI(String id) {
        if(!id.isEmpty()){
            return "&guildId=" + id;
        }
        return "";
    }

    @Transactional
    public void addGuildCount(String guildId, String server, String guildName) {
        BattleCountLog battleLog = BattleCountLog.builder()
                .guildId(guildId)
                .server(server)
                .guildName(guildName)
                .build();
        Optional<BattleCountLog> op = battlesRepository.findByServerAndGuildId(server, guildId);
        if(op.isPresent()){
            BattleCountLog b = op.get();
            b.setCount(b.getCount() + 1);
        }else{
            battlesRepository.save(battleLog);
        }
    }

    public List<BattleCountLogDTO> getCount(String server) {
        return battlesRepository.findAllByTop5AndServer(server)
                .stream()
                .map(BattleCountLog::toDto)
                .collect(Collectors.toList());
    }
}

