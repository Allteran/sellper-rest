package com.allteran.sellper.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Next class uses only for proper deserialize of GrantedAuthority from User
 * As User uses {@link com.allteran.sellper.domain.Role} that implement {@link GrantedAuthority}
 * Jakson build wrong type of entity with both fields: authorities and roles, but should consist only one
 */
public class UserAuthorityDeserializer extends JsonDeserializer {

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode jsonNode = mapper.readTree(jsonParser);
        LinkedList<GrantedAuthority> grantedAuthorities = new LinkedList<>();
        Iterator<JsonNode> elements = jsonNode.elements();
        while (elements.hasNext()) {
            JsonNode next = elements.next();;
            // Put the obtained value in the linked list eventually returned to the list
            grantedAuthorities.add(new SimpleGrantedAuthority(next.asText()));
        }
        return grantedAuthorities;
    }
}
