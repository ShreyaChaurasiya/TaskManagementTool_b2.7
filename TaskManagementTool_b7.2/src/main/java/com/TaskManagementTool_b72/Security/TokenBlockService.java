package com.TaskManagementTool_b72.Security;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class TokenBlockService {

    private final Set<String>blockTokens= Collections.newSetFromMap(new ConcurrentHashMap<String,Boolean>());

    public void blockListToken(String token) {

        blockTokens.add(token);
    }

    public boolean isBlockToken(String token) {
        return blockTokens.contains(token);
    }

}
