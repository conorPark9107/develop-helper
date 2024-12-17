package com.albionhelper.helper.service;

import com.albionhelper.helper.domain.GuildDTO;
import com.albionhelper.helper.domain.battle.*;
import com.albionhelper.helper.domain.killboard.Killer;
import com.albionhelper.helper.domain.killboard.Victim;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class BattlesService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String EAST = "https://gameinfo-sgp.albiononline.com/api/gameinfo";
    private final String WEST = "https://gameinfo.albiononline.com/api/gameinfo";
    private final String EUROPE = "https://gameinfo-ams.albiononline.com/api/gameinfo";

    // 길드의 ID값을 알아내기위한 URI
    private final String GET_ID_URL = "/search?q=";

    // 최근 전투 목록을 위한 URI
    private final String DEFAULT_BATTLES_URL = "/battles?&sort=recent";

    // 전투에 참가한 길드, 동맹, 인원을 알기 위한 URI URI /battles/<battleId>
    private final String GET_BATTLE = "/battles/";

    // 특정 전투의 모든 정보를 조회하기위한 URI
    // offset을 0부터 더이상 정보가 안나올때까지 데이터를 모아서 취합해야한다.
    private final String GET_ALL_INFO = "/events/battle/";

    @Autowired
    private WebClient webClient;

    private String getLocation(String location) {
        switch (location) {
            case "east" : return EAST;
            case "west" : return WEST;
            case "europe" : return EUROPE;
        }
        return EAST;
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

    public List<Battle> getBattleList(String url, int offset, int limit, String id) throws JsonProcessingException {

        String requestUrl = getLocation(url) + DEFAULT_BATTLES_URL + "&offset=" + offset + "&limit=" + limit;
        if(!id.equals("")){
            requestUrl += "&guildId=" + id;
        }

        String response = getResponse(requestUrl);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);

        List<Battle> list = new ArrayList<>();
        for(int i = 0; i < rootNode.size(); i++){
            JsonNode node = rootNode.get(i);
            list.add(getBattle(node));
        }

        log.info("url is {}", requestUrl);
        return list;
    }

    // Guild ID값(Primary Key)을 API로부터 가져옴.
    public List<GuildDTO> getGuildId(String id, String server) throws JsonProcessingException {
        String requestUrl = getLocation(server) + GET_ID_URL + id;
        log.info("getGuildId() request url is : {} ", requestUrl);
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
        b.setEndTime(String.valueOf(node.get("startTime")));
        b.setEndTime(String.valueOf(node.get("endTime")));
        b.setTotalFame(String.valueOf(node.get("totalFame")));
        b.setTotalKills(String.valueOf(node.get("totalKills")));

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

        players.forEach(p -> {
                    guildcountMap.put(p.getGuildName(), guildcountMap.getOrDefault(p.getGuildName(), 0) + 1);
                    allycountMap.put(p.getAllianceName(), allycountMap.getOrDefault(p.getAllianceName(), 0) + 1);
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
            Killer killer = e.getKiller();
            Victim victim = e.getVictim();
            List<EventPlayer> groupMembers = e.getGroupMembers();
            groupMembers.add(killer.convert());
            groupMembers.add(victim.convert());
            List<EventPlayer> participants = e.getParticipants();
            participants.addAll(groupMembers);

            // TODO : 한 파이트에 여러번 죽은 경우 biggest인 인벤토리만 가져올 수 있도록 해줘야할듯.
            participants.forEach(p -> {
                if(map.containsKey(p.getName())){
                    EventPlayer eventPlayer = map.get(p.getName());
                    eventPlayer.setDeathFame(Math.max(eventPlayer.getDeathFame(), p.getDeathFame()));
                    eventPlayer.setDamageDone(eventPlayer.getDamageDone() + p.getDamageDone());
                    eventPlayer.setSupportHealingDone(eventPlayer.getSupportHealingDone() + p.getSupportHealingDone());
                    if(eventPlayer.getInventory() == null &&  p.getInventory() != null){
                        eventPlayer.setInventory(p.getInventory());
                    }
                    map.put(p.getName(), eventPlayer);
                }else{
                    map.put(p.getName(), p);
                }
            });

        });

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
                .max(Comparator.comparing(Player::getKills)).get();
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
}

